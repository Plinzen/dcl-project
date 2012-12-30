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

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.schott.gae.football.shared.TransferObject;

public interface GameServiceAsync {

	void getGame(String gameId, AsyncCallback<TransferObject> callback);

	void getComments(String gameId, AsyncCallback<List<TransferObject>> callback);

	void junk(SerializableWhiteList param,
			AsyncCallback<SerializableWhiteList> callback);

	void saveComment(String gameId, Integer minute, String message,
			AsyncCallback<Void> callback);

}
