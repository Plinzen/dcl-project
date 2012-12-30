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

import de.schott.gae.football.dao.ITeamDao;
import de.schott.gae.football.dto.League;
import de.schott.gae.football.dto.Team;
import de.schott.gae.football.shared.DatabaseException;

/**
 * Game-DAO implementation for JPA.
 * 
 * @author plinzen
 *
 */
public class TeamDao implements ITeamDao {
	
	private PersistenceManager mPersistenceManager;
	
	TeamDao(){
		mPersistenceManager = PMF.getPersistenceManager();
	}

	/*
	 * (non-Javadoc)
	 * @see de.schott.gae.football.dao.ITeamDao#get(com.google.appengine.api.datastore.Key)
	 */
	@Override
	public Team get(Key id) throws DatabaseException {
		return mPersistenceManager.getObjectById(Team.class, id);	
	}

	/*
	 * (non-Javadoc)
	 * @see de.schott.gae.football.dao.ITeamDao#getAll(de.schott.gae.football.dto.League)
	 */
	@Override
	public List<Team> getAll(League league) throws DatabaseException {
		Query q = mPersistenceManager.newQuery(Team.class);
		q.setFilter("league == paramLeague");
		q.declareParameters("League paramLeague");
		@SuppressWarnings("unchecked")
		List<Team> teams = (List<Team>) q.execute();
		return teams;
	}

	/*
	 * (non-Javadoc)
	 * @see de.schott.gae.football.dao.ITeamDao#persist(de.schott.gae.football.dto.Team)
	 */
	@Override
	public Team persist(Team team) throws DatabaseException {
		PersistenceManager persistenceManager = PMF.getPersistenceManager();
		try {
			team = persistenceManager.makePersistent(team);
		} finally {
			persistenceManager.close();
		}
		return team;
	}

}
