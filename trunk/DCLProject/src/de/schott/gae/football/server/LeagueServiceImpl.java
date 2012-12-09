package de.schott.gae.football.server;

import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.schott.gae.football.Configuration;
import de.schott.gae.football.client.LeagueService;
import de.schott.gae.football.dao.DAOFactoryInterface;
import de.schott.gae.football.dao.DatabaseException;
import de.schott.gae.football.dao.ILeagueDao;
import de.schott.gae.football.dto.League;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class LeagueServiceImpl extends RemoteServiceServlet implements
		LeagueService {

	@Override
	public List<League> getLeagues() throws DatabaseException {
		
		Configuration config = Configuration.getInstance();
		DAOFactoryInterface daoFactory = config.getDaoFactory(getThreadLocalRequest().getSession());
		ILeagueDao daoLeague = daoFactory.getLeagueDao();
		return daoLeague.getAll();		
		
	}



}
