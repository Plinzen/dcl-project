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

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.google.appengine.api.datastore.Key;

import de.schott.gae.football.dao.IGameDao;
import de.schott.gae.football.dto.Game;
import de.schott.gae.football.dto.League;
import de.schott.gae.football.shared.DatabaseException;

/**
 * Game-DAO implementation for JPA.
 * 
 * @author plinzen
 *
 */
public class GameDao implements IGameDao {
	
	private EntityManager mEntityManager;
	
	GameDao(){
		mEntityManager = EMF.getEntityManager();
	}

	/*
	 * (non-Javadoc)
	 * @see de.schott.gae.football.dao.IGameDao#get(com.google.appengine.api.datastore.Key)
	 */
	@Override
	public Game get(Key id) throws DatabaseException {
		return mEntityManager.find(Game.class, id);	
	}

	/*
	 * (non-Javadoc)
	 * @see de.schott.gae.football.dao.IGameDao#getAll()
	 */
	@Override
	public List<Game> getAll() throws DatabaseException {
		@SuppressWarnings("unchecked")
		List<Game> games = mEntityManager.createQuery("Select a from " + Game.class.getName() + " a").getResultList();
		return games;
	}

	/*
	 * (non-Javadoc)
	 * @see de.schott.gae.football.dao.IGameDao#persist(de.schott.gae.football.dto.Game)
	 */
	@Override
	public Game persist(Game game) throws DatabaseException {
		if (game.getId() == null){
			mEntityManager.persist(game);
			mEntityManager.refresh(game);
			return game;
		} else {
			return mEntityManager.merge(game);	
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see de.schott.gae.football.dao.IGameDao#getByDate(java.util.Date)
	 */
	@Override
	public List<Game> getByDate(Date date) throws DatabaseException {
		Query q = mEntityManager.createQuery("Select a from " + Game.class.getName() + " a where a.date >= ?1");
		q.setParameter(1, date);
		@SuppressWarnings("unchecked")
		List<Game> games = q.getResultList();
		return games;
	}

	/*
	 * (non-Javadoc)
	 * @see de.schott.gae.football.dao.IGameDao#getByDate(de.schott.gae.football.dto.League, java.util.Date)
	 */
	@Override
	public List<Game> getByDate(League league, Date date)
			throws DatabaseException {
		Query q = mEntityManager.createQuery("Select a from " + Game.class.getName() + " a where a.league = ?1 and a.date >= ?2");
		q.setParameter(1, league);
		q.setParameter(2, date);
		@SuppressWarnings("unchecked")
		List<Game> games = q.getResultList();
		return games;
	}

}
