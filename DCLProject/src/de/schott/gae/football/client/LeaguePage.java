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

import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.schott.gae.football.shared.TransferObject;

/**
 * Shows all Leagues with games.
 * 
 * @author Christopher Schott
 *
 */
public class LeaguePage {
	private static final DateTimeFormat DF = DateTimeFormat.getFormat("d.M.y H:mm");
	private UserServiceAsync mUserService;
	private LeagueServiceAsync mLeagueService;	
	private AsyncCallback<String> mUserCallback;
	
	public LeaguePage(){
		
		// Initialize the service proxy.
		if (mUserService == null) {
			mUserService = GWT.create(UserService.class);
		}
		if (mLeagueService == null) {
			mLeagueService = GWT.create(LeagueService.class);
		}
			
		// Callback for checking user.
		mUserCallback = new AbstractAsyncCallback<String>() {

			@Override
			public void onSuccess(String result) {
				if (result == null){
					Panel root = RootPanel.get(FootballEntry.CONTENT_ID);
					root.clear();
					root.add(new Label("You are not logged in. Please log in first."));
				} else {
					// Logged in
					createOverview();
				}
			}
		};
	
	}
	
	public void createLeagueOverview(){
		// Ask for user
		mUserService.getRegisteredUser(mUserCallback);
		
	}
	
	private void createOverview(){
		final FlexTable tableLeague = new FlexTable();
		VerticalPanel panel = new VerticalPanel();
		Label lblHeader = new Label("League Overview");		
		lblHeader.setStyleName("h1");
		Panel root = RootPanel.get(FootballEntry.CONTENT_ID);
		
		// Set up callbacks.
		AsyncCallback<List<TransferObject>> leagueCallback = new AbstractAsyncCallback<List<TransferObject>>() {
			public void onSuccess(List<TransferObject> result) {
				// Create table with game information.
				tableLeague.clear();
				int row = 0;
				for (TransferObject l : result){
					Label lblHeader = new Label((String) l.get("name"));
					lblHeader.setStyleName("h2");
					tableLeague.setWidget(row++, 0, lblHeader);
					tableLeague.setStyleName("league_table");
					@SuppressWarnings("unchecked")
					List<TransferObject> games = (List<TransferObject>) l.get("games");
					for (TransferObject game : games) {
						final String id = (String) game.get("id");
						Button btnShowGame = new Button("Show Game");
						btnShowGame.addClickHandler(new ClickHandler() {
							@Override
							public void onClick(ClickEvent event) {
								new GamePage().showGame(id);
							}
						});
						tableLeague.setWidget(row, 0, btnShowGame);
						tableLeague.setText(row, 1, DF.format((Date) game.get("date")));
						tableLeague.setText(row, 2, (String) game.get("team1_name"));
						tableLeague.setText(row, 3, "-");
						tableLeague.setText(row, 4, (String) game.get("team2_name"));
						tableLeague.setText(row, 5, game.get("goal1") + " : " + game.get("goal2"));
						row++;
					}
					
				}
			}
		};
		mLeagueService.getLeagues(leagueCallback);
		
		panel.add(lblHeader);
		panel.add(tableLeague);
		root.clear();		
		root.add(panel);		
		

		
	}

}
