/**
 * 
 */
package de.schott.gae.football.dao.datastore;

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

import de.schott.gae.football.dao.ITeamDao;
import de.schott.gae.football.dto.League;
import de.schott.gae.football.dto.Team;
import de.schott.gae.football.shared.DatabaseException;

/**
 * Account-DAO implementation for Datastore.
 * 
 * @author plinzen
 *
 */
public class TeamDao implements ITeamDao {
	
	static final String KIND = "Team";
	static final String NAME = "name";
	static final String LOGO = "logo";
	
	private DatastoreService mDatastore;
	
	TeamDao(){
		mDatastore = DatastoreServiceFactory.getDatastoreService();
	}

	@Override
	public Team get(Key id) throws DatabaseException {
		try {
			Entity e = mDatastore.get(id);
			return toObject(e);
		} catch (EntityNotFoundException e1) {
			return null;
		}
	}
	
	private Team toObject(Entity entity) throws EntityNotFoundException, DatabaseException{
		Team team = new Team();
		team.setId(entity.getKey());
		team.setName((String) entity.getProperty(NAME));
		team.setLogo((Blob) entity.getProperty(LOGO));
		
		Entity parent = mDatastore.get(entity.getParent());
		League l = LeagueDao.toObject(parent, mDatastore);
		team.setLeague(l);
		
		return team;
	}
	
	
	private List<Team> toTeamList(Query query) throws DatabaseException{
		final Iterator<Entity> it = mDatastore.prepare(query).asIterator();
		final List<Team> teams = new LinkedList<Team>();
		while (it.hasNext()){
			try {
				teams.add(toObject(it.next()));
			} catch (EntityNotFoundException e) {
				throw new DatabaseException(e);
			}
		}
		return teams;
	}

	@Override
	public List<Team> getAll(League l) throws DatabaseException {
		final Query query = new Query(KIND, l.getId());
		query.addSort(NAME);
		return toTeamList(query);
	}
	
	private Entity toEntity(Team t){
		Entity e;
		if (t.getId() == null){
			e = new Entity(KIND, t.getLeague().getId());	
		} else {
			e = new Entity(t.getId());
		}
		
		e.setProperty(NAME, t.getName());
		e.setProperty(LOGO, t.getLogo());

		return e;
	}

	@Override
	public Team persist(Team team) throws DatabaseException {
		
		Entity e = toEntity(team);
		mDatastore.put(e);
		
		team.setId(e.getKey());
		return team;
	}


}
