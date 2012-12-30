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

import de.schott.gae.football.dto.Account;
import de.schott.gae.football.dto.Comment;
import de.schott.gae.football.dto.Game;
import de.schott.gae.football.dto.League;
import de.schott.gae.football.dto.Team;


/**
 * Defines the DAO-Factory interface regarding to DAO-Pattern:
 * http://www.oracle.com/technetwork/java/dataaccessobject-138824.html
 * 
 * @author plinzen
 *
 */
public interface DAOFactoryInterface {

	/**
	 * Get DAO for managing {@link Account}.
	 * 
	 * @return
	 */
	IAccountDao getAccountDao();
	
	/**
	 * Get DAO for managing {@link Comment}s.
	 * @return
	 */
	ICommentDao getCommentDao();
	
	/**
	 * Get DAO for managing {@link Game}s.
	 * 
	 * @return
	 */
	IGameDao getGameDao();
	
	/**
	 * Get DAO for managing {@link League}s.
	 * @return
	 */
	ILeagueDao getLeagueDao();
	
	/**
	 * Get DAO for managing {@link Team}s.
	 * @return
	 */
	ITeamDao getTeamDao();
}
