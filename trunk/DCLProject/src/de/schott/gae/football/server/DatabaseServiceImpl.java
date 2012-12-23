package de.schott.gae.football.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.schott.gae.football.Configuration;
import de.schott.gae.football.client.DatabaseService;
import de.schott.gae.football.client.SerializableWhiteList;
import de.schott.gae.football.dao.DAOFactoryInterface;
import de.schott.gae.football.dao.datastore.DatastoreDaoFactory;
import de.schott.gae.football.dao.jdo.JDODaoFactory;
import de.schott.gae.football.dao.jpa.JPADaoFactory;
import de.schott.gae.football.shared.TransferObject;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class DatabaseServiceImpl extends RemoteServiceServlet implements
		DatabaseService {

	private Configuration mConfiguration;
	
	private Map<Integer, Class<? extends DAOFactoryInterface>> mImplementations;
	
	public DatabaseServiceImpl() {
		mConfiguration = Configuration.getInstance();
		
		mImplementations = new HashMap<Integer, Class<? extends DAOFactoryInterface>>();
		mImplementations.put(0, JPADaoFactory.class);
		mImplementations.put(1, DatastoreDaoFactory.class);
		mImplementations.put(2, JDODaoFactory.class);
	}
	
	/*
	 * (non-Javadoc)
	 * @see de.schott.gae.football.client.DatabaseService#junk(de.schott.gae.football.client.SerializableWhiteList)
	 */
	@Override
	public SerializableWhiteList junk(SerializableWhiteList param) {
		//  Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see de.schott.gae.football.client.DatabaseService#getDatastoreImplementations()
	 */
	@Override
	public List<TransferObject> getDatastoreImplementations() {
		final List<TransferObject> impl = new ArrayList<TransferObject>();		
		final DAOFactoryInterface daoFactory = mConfiguration.getDaoFactory(getThreadLocalRequest().getSession());
		
		TransferObject to = new TransferObject();
		to.put("name", "JPA-Implementation");
		to.put("id", 0);
		to.put("selected", isCurrent(daoFactory, 0));
		impl.add(to);

		to = new TransferObject();
		to.put("name", "Datastore-Implementation");
		to.put("id", 1);
		to.put("selected", isCurrent(daoFactory, 1));
		impl.add(to);

		to = new TransferObject();
		to.put("name", "JDO-Implementation");
		to.put("id", 2);
		to.put("selected", isCurrent(daoFactory, 2));
		impl.add(to);
		
		return impl;
	}
	
	private boolean isCurrent(DAOFactoryInterface instance, int id){
		Class<? extends DAOFactoryInterface> clazz = mImplementations.get(id);		
		if (clazz.isInstance(instance)){
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see de.schott.gae.football.client.DatabaseService#setDatastoreImplementation(de.schott.gae.football.shared.TransferObject)
	 */
	@Override
	public void setDatastoreImplementation(TransferObject selectedImpl) {

		Integer id = (Integer) selectedImpl.get("id");
		Class<? extends DAOFactoryInterface> clazz = mImplementations.get(id);
		mConfiguration.setDaoFactory(getThreadLocalRequest().getSession(), clazz);
		
	}



}
