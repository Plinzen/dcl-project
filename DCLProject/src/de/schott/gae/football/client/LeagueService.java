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
 * Remote service for Leagues and Games.
 * 
 * @author Christopher Schott
 */
@RemoteServiceRelativePath("leagueservice")
public interface LeagueService extends RemoteService {
	/**
	 * Returns all leagues with games.
	 * 
	 * @return
	 * @throws DatabaseException
	 */
	 List<TransferObject> getLeagues() throws DatabaseException;
	 
	 /**
	  * Workaround for some serialization issues:
	  * http://stackoverflow.com/questions/4202964/serializationpolicy-error-when-performing-rpc-from-within-gwt-application
	  * 
	  * @param param
	  * @return
	  */
	 SerializableWhiteList junk(SerializableWhiteList param);
	 
}
