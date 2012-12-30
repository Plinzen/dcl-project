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

import com.google.appengine.api.datastore.Key;

import de.schott.gae.football.dao.ILeagueDao;
import de.schott.gae.football.dto.League;
import de.schott.gae.football.shared.DatabaseException;

/**
 * League-DAO implementation for JPA.
 * 
 * @author plinzen
 *
 */
public class LeagueDao implements ILeagueDao {
	
	private EntityManager mEntityManager;
	
	LeagueDao(){
		mEntityManager = EMF.getEntityManager();
	}
	
	/*
	 * (non-Javadoc)
	 * @see de.schott.gae.football.dao.ILeagueDao#get(com.google.appengine.api.datastore.Key)
	 */
	@Override
	public League get(Key id) throws DatabaseException {
		League l = mEntityManager.find(League.class, id);
		l.getGames();
		l.getTeams();
		return l;
	}

	/*
	 * (non-Javadoc)
	 * @see de.schott.gae.football.dao.ILeagueDao#getAll()
	 */
	@Override
	public List<League> getAll() throws DatabaseException {
		@SuppressWarnings("unchecked")
		List<League> leagues = mEntityManager.createQuery(
				"Select l from " + League.class.getName() + " l").getResultList();
		// Workaround for FetchType.EAGER
		for (League l : leagues){
			l.getGames();
			//l.getTeams();
		}
		return leagues;
	}

	/*
	 * (non-Javadoc)
	 * @see de.schott.gae.football.dao.ILeagueDao#persist(de.schott.gae.football.dto.League)
	 */
	@Override
	public League persist(League league) throws DatabaseException {
		if (league.getId() == null) {
			mEntityManager.persist(league);
			mEntityManager.refresh(league);
			return league;
		} else {
			return mEntityManager.merge(league);	
		}
	}

}
