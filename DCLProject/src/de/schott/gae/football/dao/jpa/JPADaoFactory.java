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
	private TeamDao mTeamDao;
	private CommentDao mCommentDao;
	
	public JPADaoFactory(){
		mAccountDao = new AccountDao();
		mLeagueDao = new LeagueDao();
		mGameDao = new GameDao();
		mTeamDao = new TeamDao();
		mCommentDao = new CommentDao();
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
		return mCommentDao;
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
		return mTeamDao;
	}

}
