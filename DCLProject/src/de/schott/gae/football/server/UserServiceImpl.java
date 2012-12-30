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
package de.schott.gae.football.server;

import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.schott.gae.football.client.UserService;

/**
 * The server side implementation of the {@link UserService}
 */
@SuppressWarnings("serial")
public class UserServiceImpl extends RemoteServiceServlet implements
		UserService {

	/*
	 * (non-Javadoc)
	 * @see de.schott.gae.football.client.UserService#getRegisteredUser()
	 */
	@Override
	public String getRegisteredUser() {				
		com.google.appengine.api.users.UserService userService  = UserServiceFactory.getUserService();		
		return userService.getCurrentUser() != null ? userService.getCurrentUser().getEmail() : null;
	}

	/*
	 * (non-Javadoc)
	 * @see de.schott.gae.football.client.UserService#getLoginUrl(java.lang.String)
	 */
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

	/*
	 * (non-Javadoc)
	 * @see de.schott.gae.football.client.UserService#getLogoutUrl(java.lang.String)
	 */
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
