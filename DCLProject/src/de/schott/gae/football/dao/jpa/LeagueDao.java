/**
 * 
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
		List<League> leagues = mEntityManager.createQuery("Select a from League a").getResultList();
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
