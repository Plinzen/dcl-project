package de.schott.gae.football.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.schott.gae.football.shared.DatabaseException;
import de.schott.gae.football.shared.TransferObject;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("leagueservice")
public interface LeagueService extends RemoteService {
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
