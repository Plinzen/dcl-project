package de.schott.gae.football.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.schott.gae.football.dao.DatabaseException;
import de.schott.gae.football.dto.League;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("leagueservice")
public interface LeagueService extends RemoteService {
	 List<League> getLeagues() throws DatabaseException;
	 
}
