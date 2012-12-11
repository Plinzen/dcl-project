/**
 * 
 */
package de.schott.gae.football.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.users.User;

import de.schott.gae.football.dao.IAccountDao;
import de.schott.gae.football.dto.Account;
import de.schott.gae.football.shared.DatabaseException;

/**
 * Account-DAO implementation for JPA.
 * 
 * @author plinzen
 *
 */
public class AccountDao implements IAccountDao {
	
	private EntityManager mEntityManager;
	
	AccountDao(){
		mEntityManager = EMF.getEntityManager();
	}

	/* (non-Javadoc)
	 * @see de.schott.gae.football.dao.IAccountDao#get(com.google.appengine.api.datastore.Key)
	 */
	@Override
	public Account get(Key id) throws DatabaseException {
		return mEntityManager.find(Account.class, id);	
	}

	/* (non-Javadoc)
	 * @see de.schott.gae.football.dao.IAccountDao#get(com.google.appengine.api.users.User)
	 */
	@Override
	public Account get(User user) throws DatabaseException {
		Query q = mEntityManager.createQuery("Select a from Account a where a.googleAccount = ?1");
		q.setParameter(1, user);
		try {
			return (Account) q.getSingleResult();	
		} catch (NoResultException e) {
			// Not found
			return null;
		} catch (NonUniqueResultException e){
			throw new DatabaseException("User not unique!", e);
		}
				
	}

	/* (non-Javadoc)
	 * @see de.schott.gae.football.dao.IAccountDao#getAll()
	 */
	@Override
	public List<Account> getAll() throws DatabaseException {
		@SuppressWarnings("unchecked")
		List<Account> accounts = mEntityManager.createQuery("Select a from Account a").getResultList();
		return accounts;
	}

	/* (non-Javadoc)
	 * @see de.schott.gae.football.dao.IAccountDao#persist(de.schott.gae.football.dto.Account)
	 */
	@Override
	public Account persist(Account account) throws DatabaseException {
		if (account.getId() == null){
			mEntityManager.persist(account);
			mEntityManager.refresh(account);
			return account;
		} else {
			return mEntityManager.merge(account);	
		}
	}

}
