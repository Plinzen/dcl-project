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
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.schott.gae.football.Configuration;
import de.schott.gae.football.client.GameService;
import de.schott.gae.football.client.SerializableWhiteList;
import de.schott.gae.football.dao.DAOFactoryInterface;
import de.schott.gae.football.dao.IAccountDao;
import de.schott.gae.football.dao.ICommentDao;
import de.schott.gae.football.dao.IGameDao;
import de.schott.gae.football.dao.ITeamDao;
import de.schott.gae.football.dto.Account;
import de.schott.gae.football.dto.Comment;
import de.schott.gae.football.dto.Game;
import de.schott.gae.football.dto.Team;
import de.schott.gae.football.shared.DatabaseException;
import de.schott.gae.football.shared.TransferObject;

/**
 * The server side implementation of the {@link GameService}
 */
@SuppressWarnings("serial")
public class GameServiceImpl extends RemoteServiceServlet implements
		GameService {

	private Configuration mConfiguration;
	
	public GameServiceImpl() {
		mConfiguration = Configuration.getInstance();
	}
	

	/*
	 * (non-Javadoc)
	 * @see de.schott.gae.football.client.GameService#getGame(java.lang.String)
	 */
	@Override
	public TransferObject getGame(String gameId) throws DatabaseException {
		
		DAOFactoryInterface daoFactory = mConfiguration.getDaoFactory(getThreadLocalRequest().getSession());
		IGameDao daoGame = daoFactory.getGameDao();
		ITeamDao daoTeam = daoFactory.getTeamDao();
		Game g = daoGame.get(KeyFactory.stringToKey(gameId));
		
		// Convert to Transferobject.
		TransferObject to = new TransferObject();
		to.put("id", gameId);
		to.put("date", new Date(g.getDate().getTime()));
		to.put("goal1", g.getGoals1());
		to.put("goal2", g.getGoals2());
		
		// Load teams
		Team team1 = daoTeam.get(g.getTeam1());
		Team team2 = daoTeam.get(g.getTeam2());
		
		to.put("team1_name", team1.getName());
		to.put("team1_id", KeyFactory.keyToString(team1.getId()));
		//to.put("team1_logo", team1.getLogo());
		to.put("team2_name", team2.getName());
		to.put("team2_id", KeyFactory.keyToString(team2.getId()));
		//to.put("team2_logo", team2.getLogo());
			
		
		return to;
	}

	/*
	 * (non-Javadoc)
	 * @see de.schott.gae.football.client.GameService#getComments(java.lang.String)
	 */
	@Override
	public List<TransferObject> getComments(String gameId)
			throws DatabaseException {
		DAOFactoryInterface daoFactory = mConfiguration.getDaoFactory(getThreadLocalRequest().getSession());
		ICommentDao daoComment = daoFactory.getCommentDao();
		IGameDao daoGame = daoFactory.getGameDao();
		
		Game game = daoGame.get(KeyFactory.stringToKey(gameId));
		if (game != null){
			List<Comment> comments = daoComment.getForGame(game);
			List<TransferObject> tos = new ArrayList<TransferObject>(comments.size());
			for (Comment c : comments){
				TransferObject to = new TransferObject();
				to.put("id", KeyFactory.keyToString(c.getId()));
				to.put("minute", c.getMinute());
				to.put("message", c.getMessage());
				to.put("account", c.getAccount().getGoogleAccount().getEmail());
				tos.add(to);				
			}
			return tos;			
		} else {
			throw new DatabaseException("Game not found.");
		}
		
	}

	/*
	 * (non-Javadoc)
	 * @see de.schott.gae.football.client.GameService#saveComment(java.lang.String, java.lang.Integer, java.lang.String)
	 */
	@Override
	public void saveComment(String gameId, Integer minute, String message)
			throws DatabaseException {
		DAOFactoryInterface daoFactory = mConfiguration.getDaoFactory(getThreadLocalRequest().getSession());
		ICommentDao daoComment = daoFactory.getCommentDao();
		IAccountDao daoAccount = daoFactory.getAccountDao();
		IGameDao daoGame = daoFactory.getGameDao();
		
		com.google.appengine.api.users.UserService userService  = UserServiceFactory.getUserService();
		User curUser = userService.getCurrentUser();
		
		// Create user account if not already exist.
		Account account = daoAccount.get(curUser);
		if (account == null){
			account = new Account();
			account.setGoogleAccount(curUser);
			account = daoAccount.persist(account); // now with id.
		}
		
		
		Game game = daoGame.get(KeyFactory.stringToKey(gameId));
		if (game != null){
			Long minuteL = minute.longValue();
			daoComment.persist(account.getId(), game.getId(), minuteL, message);
		} else {
			throw new DatabaseException("Game not found.");
		}
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see de.schott.gae.football.client.GameService#junk(de.schott.gae.football.client.SerializableWhiteList)
	 */
	@Override
	public SerializableWhiteList junk(SerializableWhiteList param) {
		// Auto-generated method stub
		return null;
	}


}
