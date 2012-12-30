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
package de.schott.gae.football.dao.jdo;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import com.google.appengine.api.datastore.Key;

import de.schott.gae.football.dao.ICommentDao;
import de.schott.gae.football.dto.Account;
import de.schott.gae.football.dto.Comment;
import de.schott.gae.football.dto.Game;
import de.schott.gae.football.shared.DatabaseException;

/**
 * Comment-DAO implementation for JDO.
 * 
 * @author plinzen
 *
 */
public class CommentDao implements ICommentDao {
	
	private PersistenceManager mPersistenceManager;
	
	CommentDao(){
		mPersistenceManager = PMF.getPersistenceManager();
	}

	/*
	 * (non-Javadoc)
	 * @see de.schott.gae.football.dao.ICommentDao#get(com.google.appengine.api.datastore.Key)
	 */
	@Override
	public Comment get(Key id) throws DatabaseException {
		return mPersistenceManager.getObjectById(Comment.class, id);	
	}

	/*
	 * (non-Javadoc)
	 * @see de.schott.gae.football.dao.ICommentDao#getForUser(de.schott.gae.football.dto.Account)
	 */
	@Override
	public List<Comment> getForUser(Account user) throws DatabaseException {
		Query q = mPersistenceManager.newQuery(Comment.class);
		q.setFilter("account == paramAccount");
		q.setOrdering("minute");
		q.declareParameters("Account paramAccount");
		@SuppressWarnings("unchecked")
		List<Comment> comments = (List<Comment>) q.execute(user);
		return comments;
	}
	
	/*
	 * (non-Javadoc)
	 * @see de.schott.gae.football.dao.ICommentDao#getAll()
	 */
	@Override
	public List<Comment> getAll() throws DatabaseException {
		Query q = mPersistenceManager.newQuery(Comment.class);
		@SuppressWarnings("unchecked")
		List<Comment> comments = (List<Comment>) q.execute();
		return comments;
	}
	
	/*
	 * (non-Javadoc)
	 * @see de.schott.gae.football.dao.ICommentDao#persist(de.schott.gae.football.dto.Comment)
	 */
	@Override
	public Comment persist(Comment comment) throws DatabaseException {
		PersistenceManager persistenceManager = PMF.getPersistenceManager();
		try {
			comment = persistenceManager.makePersistent(comment);
		} finally {
			persistenceManager.close();
		}
		return comment;	
	}

	/*
	 * (non-Javadoc)
	 * @see de.schott.gae.football.dao.ICommentDao#getForGame(de.schott.gae.football.dto.Game)
	 */
	@Override
	public List<Comment> getForGame(Game game) throws DatabaseException {
		Query q = mPersistenceManager.newQuery(Comment.class);
		q.setFilter("game == paramGame");
		q.setOrdering("minute");
		q.declareParameters("com.google.appengine.api.datastore.Key paramGame");
		@SuppressWarnings("unchecked")
		List<Comment> comments = (List<Comment>) q.execute(game.getId());
		return comments;
	}

	@Override
	public Comment persist(Key accountKey, Key gameId, Long minute,
			String message) throws DatabaseException {
		
		PersistenceManager persistenceManager = PMF.getPersistenceManager();
		try {
			Transaction txn =  persistenceManager.currentTransaction();
			txn.begin();
			
			// Merge Object, as they become attached to this EntityManager
			Account mergeAccount = persistenceManager.getObjectById(Account.class, accountKey);
			
			Comment comment = new Comment();
			comment.setAccount(mergeAccount);
			comment.setGame(gameId);
			comment.setMinute(minute);
			comment.setMessage(message);
			comment = persistenceManager.makePersistent(comment);
			
			mergeAccount.getComments().add(comment);
			persistenceManager.makePersistent(mergeAccount);
			
			txn.commit();
			return comment;
		} catch (Exception e){
			throw new DatabaseException(e);
		} finally {
			persistenceManager.close();
		}
	}

}
