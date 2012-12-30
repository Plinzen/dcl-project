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
package de.schott.gae.football.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

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
	
	private EntityManager mEntityManager;
	
	TeamDao(){
		mEntityManager = EMF.getEntityManager();
	}

	/*
	 * (non-Javadoc)
	 * @see de.schott.gae.football.dao.ITeamDao#get(com.google.appengine.api.datastore.Key)
	 */
	@Override
	public Team get(Key id) throws DatabaseException {
		return mEntityManager.find(Team.class, id);	
	}

	/*
	 * (non-Javadoc)
	 * @see de.schott.gae.football.dao.ITeamDao#getAll(de.schott.gae.football.dto.League)
	 */
	@Override
	public List<Team> getAll(League league) throws DatabaseException {
		Query q = mEntityManager.createQuery("Select a from " + Team.class.getName() + " a where a.league = ?1");
		q.setParameter(1, league);
		@SuppressWarnings("unchecked")
		List<Team> teams = q.getResultList();
		return teams;
	}

	/*
	 * (non-Javadoc)
	 * @see de.schott.gae.football.dao.ITeamDao#persist(de.schott.gae.football.dto.Team)
	 */
	@Override
	public Team persist(Team team) throws DatabaseException {
		if (team.getId() == null){
			mEntityManager.persist(team);
			mEntityManager.refresh(team);
			return team;
		} else {
			return mEntityManager.merge(team);	
		}
	}

}
