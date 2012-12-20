/**
 * 
 */
package de.schott.gae.football.dao.datastore;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query;

import de.schott.gae.football.dao.ILeagueDao;
import de.schott.gae.football.dto.Game;
import de.schott.gae.football.dto.League;
import de.schott.gae.football.dto.Team;
import de.schott.gae.football.shared.DatabaseException;

/**
 * Account-DAO implementation for Datastore.
 * 
 * @author plinzen
 *
 */
public class LeagueDao implements ILeagueDao {
	
	private static final String KIND = "League";
	private static final String NAME = "name";
	private static final String GAMES = "games";
	private static final String TEAMS = "teams";
	
	private DatastoreService mDatastore;
	
	LeagueDao(){
		mDatastore = DatastoreServiceFactory.getDatastoreService();
	}

	@Override
	public League get(Key id) throws DatabaseException {
		try {
			Entity e = mDatastore.get(id);
			return toObject(e, mDatastore);
		} catch (EntityNotFoundException e1) {
			return null;
		}
	}
	
	static League toObject(Entity entity, DatastoreService dataStore) throws DatabaseException{
		League l = new League();
		l.setId(entity.getKey());
		l.setName((String) entity.getProperty(NAME));

		// Get children
		Query query = new Query(entity.getKey());
		Iterator<Entity> it = dataStore.prepare(query).asIterator();
		while (it.hasNext()){
			Entity child = it.next();
			if (child.getKind().equals(GameDao.KIND)){
				// Parse Game 
				Game g = new Game();
				g.setId(child.getKey());
				g.setDate((Date) child.getProperty(GameDao.DATE));
				g.setGoals1((Long) child.getProperty(GameDao.GOALS_1));
				g.setGoals2((Long) child.getProperty(GameDao.GOALS_2));
				g.setTeam1((Key) child.getProperty(GameDao.TEAM_1));
				g.setTeam2((Key) child.getProperty(GameDao.TEAM_2));
				g.setLeague(l);
				l.getGames().add(g);				
			} else if (child.getKind().equals(TeamDao.KIND)){
				Team team = new Team();
				team.setId(child.getKey());
				team.setName((String) child.getProperty(TeamDao.NAME));
				team.setLogo((Blob) child.getProperty(TeamDao.LOGO));
				team.setLeague(l);
				l.getTeams().add(team);
			}
		}
		return l;
	}

	@Override
	public List<League> getAll() throws DatabaseException {
		final Query query = new Query(KIND);
		query.addSort(NAME);
		Iterator<Entity> it = mDatastore.prepare(query).asIterator();
		List<League> leagues = new LinkedList<League>();
		while (it.hasNext()){
			leagues.add(toObject(it.next(), mDatastore));
		}
		
		return leagues;
	}
	
	
	private Entity toEntity(League l){
		Entity e;
		if (l.getId() == null){
			e = new Entity(KIND);	
		} else {
			e = new Entity(l.getId());
		}
		
		e.setProperty(NAME, l.getName());
		
		List<Key> games = new ArrayList<Key>(l.getGames().size());
		for (Game g : l.getGames()){
			games.add(g.getId());
		}
		e.setProperty(GAMES, games);
		
		List<Key> teams = new ArrayList<Key>(l.getTeams().size());
		for (Team g : l.getTeams()){
			teams.add(g.getId());
		}
		e.setProperty(TEAMS, teams);		
		
		return e;
	}

	@Override
	public League persist(League league) throws DatabaseException {
		
		Entity e = toEntity(league);
		mDatastore.put(e);
		
		league.setId(e.getKey());
		return league;
	}


}
