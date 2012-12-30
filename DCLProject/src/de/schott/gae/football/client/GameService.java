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
package de.schott.gae.football.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.schott.gae.football.shared.DatabaseException;
import de.schott.gae.football.shared.TransferObject;

/**
 * Server component for GAme and comments.
 * 
 * @author Christopher Schott
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
