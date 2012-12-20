package de.schott.gae.football.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.schott.gae.football.shared.TransferObject;

public interface DatabaseServiceAsync {

	void getDatastoreImplementations(
			AsyncCallback<List<TransferObject>> callback);

	void junk(SerializableWhiteList param,
			AsyncCallback<SerializableWhiteList> callback);

	void setDatastoreImplementation(TransferObject selectedImpl,
			AsyncCallback<Void> callback);

}
