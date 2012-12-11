/**
 * 
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
		Query q = mEntityManager.createQuery("Select a from Team a where a.league = ?1");
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
