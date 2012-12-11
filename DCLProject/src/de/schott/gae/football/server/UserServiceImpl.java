package de.schott.gae.football.server;

import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.schott.gae.football.client.UserService;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class UserServiceImpl extends RemoteServiceServlet implements
		UserService {

	@Override
	public String getRegisteredUser() {				
		com.google.appengine.api.users.UserService userService  = UserServiceFactory.getUserService();		
		return userService.getCurrentUser() != null ? userService.getCurrentUser().getEmail() : null;
	}

	@Override
	public String getLoginUrl(String redirect) {		
		com.google.appengine.api.users.UserService userService  = UserServiceFactory.getUserService();
		String url = null;
		try {
			url = userService.createLoginURL(redirect);
		} catch (Exception ex){
			url = ex.getMessage();
		}
		return url;
	}

	@Override
	public String getLogoutUrl(String redirect) {
		com.google.appengine.api.users.UserService userService  = UserServiceFactory.getUserService();
		String url = null;
		try { 
			url = userService.createLogoutURL(redirect);
		} catch (Exception ex){
			url = ex.getMessage();
		}
		return url;

	}


}
