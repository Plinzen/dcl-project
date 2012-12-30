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
package de.schott.gae.football.dao;

import java.util.List;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.users.User;

import de.schott.gae.football.dto.Account;
import de.schott.gae.football.shared.DatabaseException;

/**
 * Defines necessary operations for working with {@link Account}s.
 * 
 * @author plinzen
 *
 */
public interface IAccountDao {
	/**
	 * Read {@link Account} with given id from Datastore.
	 * @param id the account-id.
	 * @return Account-Object or <code>null</code> if not found.
	 * @throws DatabaseException in case of error.
	 */
	Account get(Key id) throws DatabaseException;
	
	/**
	 * Read {@link Account} with given {@link User}-Object from Datastore.
	 * @param user
	 * @return Account-Object or <code>null</code> if not found.
	 * @throws DatabaseException in case of error, e.g. if more than one Account is found.
	 */
	Account get(User user) throws DatabaseException;
	
	/**
	 * Read all {@link Account}s from Datastore.
	 * 
	 * @return List of all {@link Account}s.
	 * @throws DatabaseException in case of error.
	 */
	List<Account> getAll() throws DatabaseException;
	
	/**
	 * Update an {@link Account}.
	 * @param account
	 * @throws DatabaseException in case of error
	 */
	Account persist(Account account) throws DatabaseException;
}
