/**
 * 
 */
package de.schott.gae.football.dao.jdo;

import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

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

	private PersistenceManager mPersistenceManager;

	GameDao() {
		mPersistenceManager = PMF.getPersistenceManager();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.schott.gae.football.dao.IGameDao#get(com.google.appengine.api.datastore
	 * .Key)
	 */
	@Override
	public Game get(Key id) throws DatabaseException {
		return mPersistenceManager.getObjectById(Game.class, id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.schott.gae.football.dao.IGameDao#getAll()
	 */
	@Override
	public List<Game> getAll() throws DatabaseException {
		Query q = mPersistenceManager.newQuery(Game.class);
		@SuppressWarnings("unchecked")
		List<Game> games = (List<Game>) q.execute();
		return games;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.schott.gae.football.dao.IGameDao#persist(de.schott.gae.football.dto
	 * .Game)
	 */
	@Override
	public Game persist(Game game) throws DatabaseException {
		PersistenceManager persistenceManager = PMF.getPersistenceManager();
		try {
			game = persistenceManager.makePersistent(game);	
		} finally {
			persistenceManager.close();
		}
		return game;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.schott.gae.football.dao.IGameDao#getByDate(java.util.Date)
	 */
	@Override
	public List<Game> getByDate(Date date) throws DatabaseException {
		Query q = mPersistenceManager.newQuery(Game.class);
		q.setFilter("date >= paramDate");
		q.declareParameters("Date paramDate");
		@SuppressWarnings("unchecked")
		List<Game> games = (List<Game>) q.execute(date);
		return games;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.schott.gae.football.dao.IGameDao#getByDate(de.schott.gae.football.
	 * dto.League, java.util.Date)
	 */
	@Override
	public List<Game> getByDate(League league, Date date)
			throws DatabaseException {
		Query q = mPersistenceManager.newQuery(Game.class);
		q.setFilter("league == paramLeague and date >= paramDate");
		q.declareParameters("League paramLeague, Date paramDate");
		@SuppressWarnings("unchecked")
		List<Game> games = (List<Game>) q.execute(league, date);
		return games;
	}

}
