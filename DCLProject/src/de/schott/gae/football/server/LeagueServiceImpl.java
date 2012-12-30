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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.appengine.api.datastore.KeyFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.schott.gae.football.Configuration;
import de.schott.gae.football.client.LeagueService;
import de.schott.gae.football.client.SerializableWhiteList;
import de.schott.gae.football.dao.DAOFactoryInterface;
import de.schott.gae.football.dao.ILeagueDao;
import de.schott.gae.football.dao.ITeamDao;
import de.schott.gae.football.dto.Game;
import de.schott.gae.football.dto.League;
import de.schott.gae.football.dto.Team;
import de.schott.gae.football.shared.DatabaseException;
import de.schott.gae.football.shared.TransferObject;

/**
 * The server side implementation of the {@link LeagueService}
 */
@SuppressWarnings("serial")
public class LeagueServiceImpl extends RemoteServiceServlet implements
		LeagueService {

	private Configuration mConfiguration;
	
	public LeagueServiceImpl() {
		mConfiguration = Configuration.getInstance();
	}
	
	@Override
	public List<TransferObject> getLeagues() throws DatabaseException {
		
		DAOFactoryInterface daoFactory = mConfiguration.getDaoFactory(getThreadLocalRequest().getSession());
		ILeagueDao daoLeague = daoFactory.getLeagueDao();
		ITeamDao daoTeam = daoFactory.getTeamDao();
		List<League> leagues = daoLeague.getAll();
		List<TransferObject> tos = new ArrayList<TransferObject>(leagues.size());
		for (League l : daoLeague.getAll()){
			TransferObject to = new TransferObject();
			to.put("key", KeyFactory.keyToString(l.getId()));
			to.put("name", l.getName());
			to.put("games", getGames(l, daoTeam));
			tos.add(to);
		}
		
		return tos;
	}
	
	private List<TransferObject> getGames(League league, ITeamDao daoTeam){
		List<TransferObject> games = new ArrayList<TransferObject>(league.getGames().size());
		for (Game g : league.getGames()){
			TransferObject to = new TransferObject();
			to.put("id", KeyFactory.keyToString(g.getId()));
			long timeStamp = g.getDate().getTime();
			to.put("date", new Date(timeStamp));
			
			// Load teams
			try {
				Team team1 = daoTeam.get(g.getTeam1());
				Team team2 = daoTeam.get(g.getTeam2());
				
				to.put("team1_name", team1.getName());
				to.put("team1_id", KeyFactory.keyToString(team1.getId()));
				//to.put("team1_logo", team1.getLogo());
				to.put("team2_name", team2.getName());
				to.put("team2_id", KeyFactory.keyToString(team2.getId()));
				//to.put("team2_logo", team2.getLogo());
				
			} catch (DatabaseException e) {
				e.printStackTrace();
			}
			
			to.put("goal1", g.getGoals1());
			to.put("goal2",  g.getGoals2());

			
			games.add(to);
		}
		
		return games;
	}

	@Override
	public SerializableWhiteList junk(SerializableWhiteList param) {
		//  Auto-generated method stub
		return null;
	}



}
