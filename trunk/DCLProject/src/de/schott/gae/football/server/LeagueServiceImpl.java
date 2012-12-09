package de.schott.gae.football.server;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.datastore.KeyFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.schott.gae.football.Configuration;
import de.schott.gae.football.client.LeagueService;
import de.schott.gae.football.dao.DAOFactoryInterface;
import de.schott.gae.football.dao.ILeagueDao;
import de.schott.gae.football.dto.League;
import de.schott.gae.football.shared.DatabaseException;
import de.schott.gae.football.shared.TransferObject;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class LeagueServiceImpl extends RemoteServiceServlet implements
		LeagueService {

	@Override
	public List<TransferObject> getLeagues() throws DatabaseException {
		
		Configuration config = Configuration.getInstance();
		DAOFactoryInterface daoFactory = config.getDaoFactory(getThreadLocalRequest().getSession());
		ILeagueDao daoLeague = daoFactory.getLeagueDao();
		List<League> leagues = daoLeague.getAll();
		List<TransferObject> tos = new ArrayList<TransferObject>(leagues.size());
		for (League l : daoLeague.getAll()){
			TransferObject to = new TransferObject();
			to.put("key", KeyFactory.keyToString(l.getId()));
			to.put("name", l.getName());
			
			tos.add(to);
		}
		
		return tos;
	}



}
