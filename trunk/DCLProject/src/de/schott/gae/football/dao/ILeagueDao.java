package de.schott.gae.football.dao;

import java.util.List;

import com.google.appengine.api.datastore.Key;

import de.schott.gae.football.dto.League;
import de.schott.gae.football.shared.DatabaseException;

/**
 * Defines necessary operations for working with {@link League}s.
 * 
 * @author plinzen
 *
 */
public interface ILeagueDao {
	/**
	 * Read {@link League} with given id from Datastore.
	 * @param id the league-id.
	 * @return Account-Object or <code>null</code> if not found.
	 * @throws DatabaseException in case of error.
	 */
	League get(Key id) throws DatabaseException;
	
	
	/**
	 * Read all {@link League}s from Datastore.
	 * 
	 * @return List of all {@link League}s.
	 * @throws DatabaseException in case of error.
	 */
	List<League> getAll() throws DatabaseException;
	
	/**
	 * Update an {@link League}.
	 * @param league
	 * @throws DatabaseException in case of error
	 */
	League persist(League league) throws DatabaseException;	
}
