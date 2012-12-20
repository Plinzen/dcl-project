package de.schott.gae.football.dao;

import java.util.List;

import com.google.appengine.api.datastore.Key;

import de.schott.gae.football.dto.Account;
import de.schott.gae.football.dto.Comment;
import de.schott.gae.football.dto.Game;
import de.schott.gae.football.shared.DatabaseException;

/**
 * Defines necessary operations for working with {@link Comment}s.
 * 
 * @author plinzen
 *
 */
public interface ICommentDao {
	/**
	 * Read {@link Comment} with given id from Datastore.
	 * @param id the comment-id.
	 * @return Comment-Object or <code>null</code> if not found.
	 * @throws DatabaseException in case of error.
	 */
	Comment get(Key id) throws DatabaseException;
	
	/**
	 * Read {@link Comment} with given {@link Account}-Object from Datastore.
	 * @param user
	 * @return Comment-Object or <code>null</code> if not found.
	 * @throws DatabaseException in case of error, e.g. if more than one Account is found.
	 */
	List<Comment> getForUser(Account user) throws DatabaseException;
	
	/**
	 * Read all {@link Comment}s from Datastore.
	 * 
	 * @return List of all {@link Comment}s.
	 * @throws DatabaseException in case of error.
	 */
	List<Comment> getAll() throws DatabaseException;
	
	/**
	 * Read all comments for one {@link Game} from Datastore.
	 * 
	 * @param game
	 * @return List of all {@link Comment}s.
	 * @throws DatabaseException
	 */
	List<Comment> getForGame(Game game) throws DatabaseException;
	
	/**
	 * Update an {@link Comment}.
	 * @param comment
	 * @throws DatabaseException in case of error
	 */
	Comment persist(Comment comment) throws DatabaseException;	
	
	/**
	 * Storing a {@link Comment} and create all necessary links to other objects.
	 * 
	 * @param accountId The User {@link Account}.
	 * @param gameId The {@link Game} for which the Comment takes place.
	 * @param minute Input data for comment
	 * @param message INput data for comment.
	 * @return persisted {@link Comment} object
	 */
	Comment persist(Key accountId, Key gameId, Long minute, String message) throws DatabaseException;
}
