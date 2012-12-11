/**
 * 
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
import com.google.appengine.api.users.User;

import de.schott.gae.football.dao.IAccountDao;
import de.schott.gae.football.dto.Account;
import de.schott.gae.football.dto.Comment;
import de.schott.gae.football.shared.DatabaseException;

/**
 * Account-DAO implementation for Datastore.
 * 
 * @author plinzen
 *
 */
public class AccountDao implements IAccountDao {
	
	private static final String KIND = "account";
	static final String COMMENTS = "comments";
	private static final String GOOGLE_ACCOUNT = "googleAccount";
	
	private DatastoreService mDatastore;
	
	AccountDao(){
		mDatastore = DatastoreServiceFactory.getDatastoreService();
	}

	/* (non-Javadoc)
	 * @see de.schott.gae.football.dao.IAccountDao#get(com.google.appengine.api.datastore.Key)
	 */
	@Override
	public Account get(Key id) throws DatabaseException {
		try {
			Entity e = mDatastore.get(id);
			return toObject(e);
		} catch (EntityNotFoundException e1) {
			return null;
		}
	}
	
	static Account toObject(Entity entity) throws DatabaseException{
		Account a = new Account();
		a.setId(entity.getKey());
		a.setGoogleAccount((User) entity.getProperty(GOOGLE_ACCOUNT));
		
		CommentDao commentDao = new CommentDao();
		a.setComments(commentDao.getForUser(a));
		
		return a;
	}

	/* (non-Javadoc)
	 * @see de.schott.gae.football.dao.IAccountDao#get(com.google.appengine.api.users.User)
	 */
	@Override
	public Account get(User user) throws DatabaseException {
		Query q = new Query(KIND);
		Filter filter = FilterOperator.EQUAL.of(GOOGLE_ACCOUNT, user);
		q.setFilter(filter);
		Iterable<Entity> iterable = mDatastore.prepare(q).asIterable();
		Iterator<Entity> it = iterable.iterator();
		if (it.hasNext()){
			Entity e = it.next();
			// Not unique
			if (it.hasNext()){
				throw new DatabaseException("User not unique!");
			}
			return toObject(e);
		} else {
			return null;
		}
		
	}

	/* (non-Javadoc)
	 * @see de.schott.gae.football.dao.IAccountDao#getAll()
	 */
	@Override
	public List<Account> getAll() throws DatabaseException {
		Query q = new Query(KIND);
		Iterable<Entity> iterable = mDatastore.prepare(q).asIterable();
		Iterator<Entity> it = iterable.iterator();
		List<Account> accounts = new LinkedList<Account>();
		while (it.hasNext()){
			accounts.add(toObject(it.next()));
		}
		
		return accounts;
	}
	
	static Entity toEntity(Account account) {
		Entity e;
		if (account.getId() == null){
			e = new Entity(KIND);	
		} else {
			e = new Entity(account.getId());
		}
		
		e.setProperty(GOOGLE_ACCOUNT, account.getGoogleAccount());
		Collection<Key> comments = new Vector<Key>(account.getComments().size());
		for (Comment c : account.getComments()){
			comments.add(c.getId());
		}
		e.setProperty(COMMENTS, comments);
		return e;
	}

	/* (non-Javadoc)
	 * @see de.schott.gae.football.dao.IAccountDao#persist(de.schott.gae.football.dto.Account)
	 */
	@Override
	public Account persist(Account account) throws DatabaseException {
		Entity e = toEntity(account);
		mDatastore.put(e);
		
		account.setId(e.getKey());
		
		return account;
	}

}
