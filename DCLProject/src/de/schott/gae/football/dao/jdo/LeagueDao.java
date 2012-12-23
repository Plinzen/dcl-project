/**
 * 
 */
package de.schott.gae.football.dao.jdo;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.appengine.api.datastore.Key;

import de.schott.gae.football.dao.ILeagueDao;
import de.schott.gae.football.dto.League;
import de.schott.gae.football.shared.DatabaseException;

/**
 * League-DAO implementation for JDO.
 * 
 * @author plinzen
 *
 */
public class LeagueDao implements ILeagueDao {
	
	private PersistenceManager mPersistenceManager;
	
	LeagueDao(){
		mPersistenceManager = PMF.getPersistenceManager();
	}
	
	/*
	 * (non-Javadoc)
	 * @see de.schott.gae.football.dao.ILeagueDao#get(com.google.appengine.api.datastore.Key)
	 */
	@Override
	public League get(Key id) throws DatabaseException {
		League l = mPersistenceManager.getObjectById(League.class, id);
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
		Query q = mPersistenceManager.newQuery(League.class);
		@SuppressWarnings("unchecked")
		List<League> leagues = (List<League>) q.execute();
		return leagues;
	}

	/*
	 * (non-Javadoc)
	 * @see de.schott.gae.football.dao.ILeagueDao#persist(de.schott.gae.football.dto.League)
	 */
	@Override
	public League persist(League league) throws DatabaseException {
		PersistenceManager persistenceManager = PMF.getPersistenceManager();
		try {
			league = persistenceManager.makePersistent(league);
		} finally {
			persistenceManager.close();
		}
		return league;
	}

}
