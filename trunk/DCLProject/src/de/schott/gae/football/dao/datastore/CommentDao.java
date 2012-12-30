/*
Copyright 2012 Christopher Schott

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package de.schott.gae.football.dao.datastore;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Transaction;

import de.schott.gae.football.dao.ICommentDao;
import de.schott.gae.football.dto.Account;
import de.schott.gae.football.dto.Comment;
import de.schott.gae.football.dto.Game;
import de.schott.gae.football.shared.DatabaseException;

/**
 * Account-DAO implementation for Datastore.
 * 
 * @author plinzen
 *
 */
public class CommentDao implements ICommentDao {
	
	static final String KIND = "Comment";
	static final String GAME_ID = "game";
	static final String MESSAGE = "message";
	static final String MINUTE = "minute";
	
	private DatastoreService mDatastore;
	
	CommentDao(){
		mDatastore = DatastoreServiceFactory.getDatastoreService();
	}

	@Override
	public Comment get(Key id) throws DatabaseException {
		try {
			Entity e = mDatastore.get(id);
			return toObject(e);
		} catch (EntityNotFoundException e1) {
			return null;
		}
	}
	
	private Comment toObject(Entity entity) throws EntityNotFoundException, DatabaseException{
		Comment c = new Comment();
		c.setId(entity.getKey());
		c.setMessage((String) entity.getProperty(MESSAGE));
		c.setMinute((Long) entity.getProperty(MINUTE));
		c.setGame((Key) entity.getProperty(GAME_ID));
		
		Entity parent = mDatastore.get(entity.getParent());
		Account account = AccountDao.toObject(parent, mDatastore);
		c.setAccount(account);
		
		return c;
	}

	@Override
	public List<Comment> getForUser(Account user) throws DatabaseException {
		final Key parent = user.getId();
		final Query query = new Query(KIND, parent);
		query.addSort(MINUTE);
		return toCommentList(query);
	}
	
	
	private List<Comment> toCommentList(Query query) throws DatabaseException{
		final Iterator<Entity> it = mDatastore.prepare(query).asIterator();
		final List<Comment> comments = new LinkedList<Comment>();
		while (it.hasNext()){
			try {
				comments.add(toObject(it.next()));
			} catch (EntityNotFoundException e) {
				throw new DatabaseException(e);
			}
		}
		return comments;
	}

	@Override
	public List<Comment> getAll() throws DatabaseException {
		final Query query = new Query(KIND);
		query.addSort(MINUTE);
		return toCommentList(query);
	}

	@Override
	public List<Comment> getForGame(Game game) throws DatabaseException {
		final Query query = new Query(KIND);
		final Filter filter = FilterOperator.EQUAL.of(GAME_ID, game.getId());
		query.setFilter(filter);
		query.addSort(MINUTE);
		
		return toCommentList(query);
	}
	
	private Entity toEntity(Comment comment){
		Entity e;
		if (comment.getId() == null){
			e = new Entity(KIND, comment.getAccount().getId());	
		} else {
			e = new Entity(comment.getId());
		}
		e.setProperty(GAME_ID, comment.getGame().getId());
		e.setProperty(MESSAGE, comment.getMessage());
		e.setProperty(MINUTE, comment.getMinute());
		
		return e;
	}

	@Override
	public Comment persist(Comment comment) throws DatabaseException {
		
		Entity e = toEntity(comment);
		mDatastore.put(e);
		
		comment.setId(e.getKey());
		return comment;
	}

	@Override
	public Comment persist(Key accountId, Key gameId, Long minute,
			String message) throws DatabaseException {
		
		Transaction txn = mDatastore.beginTransaction();
		Entity e = new Entity(KIND, accountId);
		e.setProperty(GAME_ID, gameId);
		e.setProperty(MESSAGE, message);
		e.setProperty(MINUTE, minute);
		
		mDatastore.put(txn, e);
		
		// Update account
		try {
			Entity account = mDatastore.get(accountId);
			@SuppressWarnings("unchecked")
			Collection<Key> comments = (Collection<Key>) account.getProperty(AccountDao.COMMENTS);
			if (comments == null) {
				comments = new Vector<Key>();
			}
			comments.add(e.getKey());
			account.setProperty(AccountDao.COMMENTS, comments);
			mDatastore.put(txn, account);
			
			txn.commit();
			
		} catch (EntityNotFoundException e1) {
			txn.rollback();
			throw new DatabaseException(e1);
		}
		
		return get(e.getKey());
	}


}
