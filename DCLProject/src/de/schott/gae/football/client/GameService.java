package de.schott.gae.football.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.schott.gae.football.shared.DatabaseException;
import de.schott.gae.football.shared.TransferObject;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("gameservice")
public interface GameService extends RemoteService {
	
	/**
	 * Get information about the game with the given id.
	 * 
	 * @param gameId
	 * @return
	 * @throws DatabaseException
	 */
	 TransferObject getGame(String gameId) throws DatabaseException;
	 
	 /**
	  * Returns all comments for the given game.
	  * @param gameId
	  * @return
	  * @throws DatabaseException
	  */
	 List<TransferObject> getComments(String gameId) throws DatabaseException;
	 
	 /**
	  * Save a comment to a game.
	  * @param gameId the id of the game.
	  * @param minute the minute
	  * @param message the tickermessage
	  * @throws DatabaseException in case of error.
	  */
	 void saveComment(String gameId, Integer minute, String message) throws DatabaseException;

	 /**
	  * Workaround for some serialization issues:
	  * http://stackoverflow.com/questions/4202964/serializationpolicy-error-when-performing-rpc-from-within-gwt-application
	  * 
	  * @param param
	  * @return
	  */
	 SerializableWhiteList junk(SerializableWhiteList param);
	  
}
