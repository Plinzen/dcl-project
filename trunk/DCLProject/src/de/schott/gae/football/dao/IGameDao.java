package de.schott.gae.football.dao;

import java.util.Date;
import java.util.List;

import com.google.appengine.api.datastore.Key;

import de.schott.gae.football.dto.Game;
import de.schott.gae.football.dto.League;

/**
 * Defines necessary operations for working with {@link Game}s.
 * 
 * @author plinzen
 *
 */
public interface IGameDao {
	/**
	 * Read {@link Game} with given id from Datastore.
	 * @param id the game-id.
	 * @return Account-Object or <code>null</code> if not found.
	 * @throws DatabaseException in case of error.
	 */
	Game get(Key id) throws DatabaseException;
	
	/**
	 * Read all {@link Game}s with a date greater or equal then the given one. 
	 * @param date 
	 * @return A List of all {@link Game}s.
	 * @throws DatabaseException in case of error
	 */
	List<Game> getByDate(Date date) throws DatabaseException;

	/**
	 * Read all {@link Game}s from a given {@link League} with a date greater or equal then the given one.
	 * @param league  
	 * @param date 
	 * @return A List of all {@link Game}s.
	 * @throws DatabaseException in case of error
	 */
	List<Game> getByDate(League league, Date date) throws DatabaseException;
	
	/**
	 * Read all {@link Game}s from Datastore.
	 * 
	 * @return List of all {@link Game}s.
	 * @throws DatabaseException in case of error.
	 */
	List<Game> getAll() throws DatabaseException;
	
	/**
	 * Update or persist an {@link Game}.
	 * @param game
	 * @throws DatabaseException in case of error
	 */
	Game persist(Game game) throws DatabaseException;
}
