/**
 * 
 */
package de.schott.gae.football.dao.jpa;

import de.schott.gae.football.dao.DAOFactoryInterface;
import de.schott.gae.football.dao.IAccountDao;
import de.schott.gae.football.dao.ICommentDao;
import de.schott.gae.football.dao.IGameDao;
import de.schott.gae.football.dao.ILeagueDao;
import de.schott.gae.football.dao.ITeamDao;

/**
 * Implementation of DAO-Factory for JPA-Implementation.
 * 
 * @author plinzen
 *
 */
public class JPADaoFactory implements DAOFactoryInterface {

	private AccountDao mAccountDao;
	private LeagueDao mLeagueDao;
	private GameDao mGameDao;
	
	public JPADaoFactory(){
		mAccountDao = new AccountDao();
		mLeagueDao = new LeagueDao();
		mGameDao = new GameDao();
	}
	
	/* (non-Javadoc)
	 * @see de.schott.gae.football.dao.DAOFactoryInterface#getAccountDao()
	 */
	@Override
	public IAccountDao getAccountDao() {
		return mAccountDao;
	}

	/* (non-Javadoc)
	 * @see de.schott.gae.football.dao.DAOFactoryInterface#getCommentDao()
	 */
	@Override
	public ICommentDao getCommentDao() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see de.schott.gae.football.dao.DAOFactoryInterface#getGameDao()
	 */
	@Override
	public IGameDao getGameDao() {
		return mGameDao;
	}

	/* (non-Javadoc)
	 * @see de.schott.gae.football.dao.DAOFactoryInterface#getLeagueDao()
	 */
	@Override
	public ILeagueDao getLeagueDao() {
		return mLeagueDao;
	}

	/* (non-Javadoc)
	 * @see de.schott.gae.football.dao.DAOFactoryInterface#getTeamDao()
	 */
	@Override
	public ITeamDao getTeamDao() {
		// TODO Auto-generated method stub
		return null;
	}

}