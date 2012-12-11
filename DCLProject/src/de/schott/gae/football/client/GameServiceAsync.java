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
