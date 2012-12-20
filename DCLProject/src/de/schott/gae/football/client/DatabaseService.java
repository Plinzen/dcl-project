package de.schott.gae.football.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.schott.gae.football.shared.TransferObject;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("databaseservice")
public interface DatabaseService extends RemoteService {
	
	/**
	 * Get all available Datastore implementations.
	 * 
	 * @return
	 */
	 List<TransferObject> getDatastoreImplementations();
	 
	 /**
	  * Set a Datastore Implementation.
	  * 
	  * @param selectedImpl
	  */
	 void setDatastoreImplementation(TransferObject selectedImpl);
	 
	 /**
	  * Workaround for some serialization issues:
	  * http://stackoverflow.com/questions/4202964/serializationpolicy-error-when-performing-rpc-from-within-gwt-application
	  * 
	  * @param param
	  * @return
	  */
	 SerializableWhiteList junk(SerializableWhiteList param);
	 
}
