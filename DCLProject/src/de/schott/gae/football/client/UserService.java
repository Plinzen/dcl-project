package de.schott.gae.football.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("userservice")
public interface UserService extends RemoteService {
	 String getRegisteredUser();
	 
	 String getLoginUrl(String redirect);
	 
	 String getLogoutUrl(String redirect);
}
