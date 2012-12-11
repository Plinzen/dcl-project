package de.schott.gae.football.dao;

import de.schott.gae.football.dto.Account;
import de.schott.gae.football.dto.Comment;
import de.schott.gae.football.dto.Game;
import de.schott.gae.football.dto.League;
import de.schott.gae.football.dto.Team;


/**
 * Defines the DAO-Factory interface regarding to DAO-Pattern:
 * http://www.oracle.com/technetwork/java/dataaccessobject-138824.html
 * 
 * @author plinzen
 *
 */
public interface DAOFactoryInterface {

	/**
	 * Get DAO for managing {@link Account}.
	 * 
	 * @return
	 */
	IAccountDao getAccountDao();
	
	/**
	 * Get DAO for managing {@link Comment}s.
	 * @return
	 */
	ICommentDao getCommentDao();
	
	/**
	 * Get DAO for managing {@link Game}s.
	 * 
	 * @return
	 */
	IGameDao getGameDao();
	
	/**
	 * Get DAO for managing {@link League}s.
	 * @return
	 */
	ILeagueDao getLeagueDao();
	
	/**
	 * Get DAO for managing {@link Team}s.
	 * @return
	 */
	ITeamDao getTeamDao();
}
