/**
 * 
 */
package de.schott.gae.football.dao.datastore;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;

import de.schott.gae.football.dao.IGameDao;
import de.schott.gae.football.dto.Game;
import de.schott.gae.football.dto.League;
import de.schott.gae.football.shared.DatabaseException;

/**
 * Account-DAO implementation for Datastore.
 * 
 * @author plinzen
 *
 */
public class GameDao implements IGameDao {
	
	static final String KIND = "Game";
	static final String DATE = "date";
	static final String GOALS_1 = "goals1";
	static final String GOALS_2 = "goals2";
	static final String TEAM_1 = "team1";
	static final String TEAM_2 = "team2";
	
	private DatastoreService mDatastore;
	
	GameDao(){
		mDatastore = DatastoreServiceFactory.getDatastoreService();
	}

	@Override
	public Game get(Key id) throws DatabaseException {
		try {
			Entity e = mDatastore.get(id);
			return toObject(e);
		} catch (EntityNotFoundException e1) {
			return null;
		}
	}
	
	private Game toObject(Entity entity) throws EntityNotFoundException, DatabaseException{
		Game g = new Game();
		g.setId(entity.getKey());
		g.setDate((Date) entity.getProperty(DATE));
		g.setGoals1((Long) entity.getProperty(GOALS_1));
		g.setGoals2((Long) entity.getProperty(GOALS_2));
		g.setTeam1((Key) entity.getProperty(TEAM_1));
		g.setTeam2((Key) entity.getProperty(TEAM_2));
		
		Entity parent = mDatastore.get(entity.getParent());
		League l = LeagueDao.toObject(parent, mDatastore);
		g.setLeague(l);
		
		return g;
	}

	@Override
	public List<Game> getByDate(Date date) throws DatabaseException {
		final Query query = new Query(KIND);
		final Filter filter = FilterOperator.GREATER_THAN_OR_EQUAL.of(DATE, date);
		query.setFilter(filter);
		query.addSort(DATE);
		return toGameList(query);
	}
	
	
	private List<Game> toGameList(Query query) throws DatabaseException{
		final Iterator<Entity> it = mDatastore.prepare(query).asIterator();
		final List<Game> games = new LinkedList<Game>();
		while (it.hasNext()){
			try {
				games.add(toObject(it.next()));
			} catch (EntityNotFoundException e) {
				throw new DatabaseException(e);
			}
		}
		return games;
	}

	@Override
	public List<Game> getAll() throws DatabaseException {
		final Query query = new Query(KIND);
		query.addSort(DATE);
		return toGameList(query);
	}
	
	@Override
	public List<Game> getByDate(League league, Date date) throws DatabaseException {
		Query query = new Query(KIND, league.getId());
		final Filter filter = FilterOperator.GREATER_THAN_OR_EQUAL.of(DATE, date);
		query.setFilter(filter);
		query.addSort(DATE);
		return toGameList(query);
	}
	
	private Entity toEntity(Game g){
		Entity e;
		if (g.getId() == null){
			e = new Entity(KIND, g.getLeague().getId());	
		} else {
			e = new Entity(g.getId());
		}
		e.setProperty(DATE, g.getDate());
		e.setProperty(GOALS_1, g.getGoals1());
		e.setProperty(GOALS_2, g.getGoals2());
		e.setProperty(TEAM_1, g.getTeam1());
		e.setProperty(TEAM_2, g.getTeam2());
		
		return e;
	}

	@Override
	public Game persist(Game game) throws DatabaseException {
		
		Entity e = toEntity(game);
		mDatastore.put(e);
		
		game.setId(e.getKey());
		return game;
	}


}
