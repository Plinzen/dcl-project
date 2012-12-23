package de.schott.gae.football.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("userservice")
public interface UserService extends RemoteService {
	/**
	 * Returns the current registered user.
	 * or null if none.
	 * 
	 * @return
	 */
	 String getRegisteredUser();
	 
	 /**
	  * Returns the Login-URL for Google-Login.
	  * 
	  * @param redirect The URL for redirection after login.
	  * @return
	  */
	 String getLoginUrl(String redirect);
	 
	 /**
	  * Returns the Logout-URL for Google-Logout.
	  * @param redirect The URL for redirection after logout
	  * @return
	  */
	 String getLogoutUrl(String redirect);
}
