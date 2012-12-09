package de.schott.gae.football.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.schott.gae.football.dto.League;

public interface LeagueServiceAsync {

	void getLeagues(AsyncCallback<List<League>> callback);	

}
