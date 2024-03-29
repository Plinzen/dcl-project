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

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.users.User;

import de.schott.gae.football.dao.IAccountDao;
import de.schott.gae.football.dto.Account;
import de.schott.gae.football.shared.DatabaseException;

/**
 * Account-DAO implementation for JDO.
 * 
 * @author plinzen
 *
 */
public class AccountDao implements IAccountDao {
	
	private PersistenceManager mPersistenceManager;
	
	AccountDao(){
		mPersistenceManager = PMF.getPersistenceManager();
	}

	/* (non-Javadoc)
	 * @see de.schott.gae.football.dao.IAccountDao#get(com.google.appengine.api.datastore.Key)
	 */
	@Override
	public Account get(Key id) throws DatabaseException {
		return mPersistenceManager.getObjectById(Account.class, id);
	}

	/* (non-Javadoc)
	 * @see de.schott.gae.football.dao.IAccountDao#get(com.google.appengine.api.users.User)
	 */
	@Override
	public Account get(User user) throws DatabaseException {
		Query q = mPersistenceManager.newQuery(Account.class);
		q.setFilter("googleAccount == gAccount");
		q.declareParameters("com.google.appengine.api.users.User gAccount");
		@SuppressWarnings("unchecked")
		List<Account> accounts = (List<Account>) q.execute(user);
		if (accounts == null
				|| accounts.size() < 1){
			return null;
		}
		if (accounts.size() > 1){
			throw new DatabaseException("User not unique!");
		}
		return accounts.get(0);				
	}

	/* (non-Javadoc)
	 * @see de.schott.gae.football.dao.IAccountDao#getAll()
	 */
	@Override
	public List<Account> getAll() throws DatabaseException {
		Query q = mPersistenceManager.newQuery(Account.class);
		@SuppressWarnings("unchecked")
		List<Account> accounts = (List<Account>) q.execute();
		return accounts;
	}

	/* (non-Javadoc)
	 * @see de.schott.gae.football.dao.IAccountDao#persist(de.schott.gae.football.dto.Account)
	 */
	@Override
	public Account persist(Account account) throws DatabaseException {
		PersistenceManager persistenceManager = PMF.getPersistenceManager();
		try {
			persistenceManager.makePersistent(account);
			persistenceManager.refresh(account);
		} finally {
			persistenceManager.close();
		}
		return account;
	}

}
