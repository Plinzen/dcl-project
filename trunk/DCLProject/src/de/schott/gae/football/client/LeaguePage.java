package de.schott.gae.football.client;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.schott.gae.football.dto.League;

public class LeaguePage {
	
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
		AsyncCallback<List<League>> leagueCallback = new AbstractAsyncCallback<List<League>>() {
			public void onSuccess(List<League> result) {
				tableLeague.clear();
				tableLeague.setText(0, 0, "Name");
				int row = 1;
				for (League l : result){
					tableLeague.setText(row++, 0, l.getName());
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
