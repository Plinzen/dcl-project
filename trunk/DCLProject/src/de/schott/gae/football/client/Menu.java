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

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.schott.gae.football.Configuration;

/**
 * Building Menu on the left side.
 *  
 * @author Christopher Schott
 *
 */
public class Menu {
	
	private UserServiceAsync mUserService;
	private Label mLblMenu;
	private Label mLblUser;
	private VerticalPanel mPanelMenu;
	private Button mBtnGames;
	private Button mBtnDatabase;
	private Button mBtnLogin;
	private Button mBtnLogout;
	
	private String mLoginUrl;
	private String mLogoutUrl;
	
	public Menu(){
		
		mLblUser = new Label("Hello new User. Please use Login.");
		mLblMenu = new Label("Menu");
		mLblMenu.setStyleName("h1", true);
		
		mPanelMenu = new VerticalPanel();

		mBtnLogin = new Button("Login");
		mBtnLogin.setStyleName("menu_button", true);		
		mBtnLogin.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				Window.Location.assign(mLoginUrl);				
			}
		});
		mPanelMenu.add(mBtnLogin);

		
		mBtnGames = new Button("Games today");
		mBtnGames.setStyleName("menu_button", true);		
		mBtnGames.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				LeaguePage page = new LeaguePage();
				page.createLeagueOverview();				
			}
		});
		mPanelMenu.add(mBtnGames);
		
		mBtnDatabase = new Button("Database implementation");
		mBtnDatabase.setStyleName("menu_button", true);		
		mBtnDatabase.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				DatabasePage page = new DatabasePage();
				page.createView();				
			}
		});
		mPanelMenu.add(mBtnDatabase);
		
		mBtnLogout = new Button("Logout");
		mBtnLogout.setStyleName("menu_button", true);
		mBtnLogout.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
				Window.Location.assign(mLogoutUrl);
			}
		});
		mPanelMenu.add(mBtnLogout);
	}
	
	public void createMenu(Panel menuPanel){
		
		// Initialize the service proxy.
		if (mUserService == null) {
			mUserService = GWT.create(UserService.class);
		}

		// Set up the callback object.
		AsyncCallback<String> callback = new AbstractAsyncCallback<String>() {
			public void onSuccess(String result) {
				if (result != null) 	mLblUser.setText("Hello, " + result);
			}
		};

		// Make the call to the stock price service.
		mUserService.getRegisteredUser(callback);

		// Set up the callback object.
		AsyncCallback<String> cbLoginUrl = new AbstractAsyncCallback<String>() {
			public void onSuccess(String result) {
				mLoginUrl = result;
			}
		};

		// Make the call to the service.		
		String redirect = "";
		if (Configuration.RUN_LOCAL){
			redirect = "/DCLProject.html?gwt.codesvr=127.0.0.1:9997";
		}
		
		mUserService.getLoginUrl(redirect, cbLoginUrl);
		
		// Set up the callback object.
		AsyncCallback<String> cbLogoutUrl = new AbstractAsyncCallback<String>() {

			public void onSuccess(String result) {
				mLogoutUrl = result;
			}
		};

		// Make the call to the service.
		mUserService.getLogoutUrl(redirect, cbLogoutUrl);

		// Add UI-Elements
		menuPanel.add(mLblMenu);
		menuPanel.add(mPanelMenu);
		menuPanel.add(mLblUser);
		
		
	}

}
