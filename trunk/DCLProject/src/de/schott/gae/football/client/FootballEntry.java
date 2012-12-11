package de.schott.gae.football.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

public class FootballEntry implements EntryPoint {

	public static final String CONTENT_ID = "content";
	
	private static Menu menu = new Menu();
	
	@Override
	public void onModuleLoad() {

	    menu.createMenu(RootPanel.get("menu"));
	    
	    RootPanel.get(CONTENT_ID);
	}

}
