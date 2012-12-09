package de.schott.gae.football.client;


import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UserServiceAsync {

	void getRegisteredUser(AsyncCallback<String> callback);

	void getLoginUrl(String redirect, AsyncCallback<String> callback);

	void getLogoutUrl(String redirect, AsyncCallback<String> callback);

}
