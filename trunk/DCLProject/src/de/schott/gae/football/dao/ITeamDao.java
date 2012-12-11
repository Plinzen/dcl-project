package de.schott.gae.football.dao;

import java.util.List;

import com.google.appengine.api.datastore.Key;

import de.schott.gae.football.dto.League;
import de.schott.gae.football.dto.Team;
import de.schott.gae.football.shared.DatabaseException;

/**
 * Defines necessary operations for working with {@link Team}s.
 * 
 * @author plinzen
 *
 */
public interface ITeamDao {
	/**
	 * Read {@link Team} with given id from Datastore.
	 * @param id the team-id.
	 * @return Account-Object or <code>null</code> if not found.
	 * @throws DatabaseException in case of error.
	 */
	Team get(Key id) throws DatabaseException;
	
	
	/**
	 * Read all {@link Team}s from one League from Datastore.
	 * 
	 * @return List of all {@link Team}s.
	 * @throws DatabaseException in case of error.
	 */
	List<Team> getAll(League league) throws DatabaseException;
	
	/**
	 * Update an {@link Team}.
	 * @param team
	 * @throws DatabaseException in case of error
	 */
	Team persist(Team team) throws DatabaseException;	
}
