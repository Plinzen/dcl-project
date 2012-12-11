/**
 * 
 */
package de.schott.gae.football;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import de.schott.gae.football.dao.DAOFactoryInterface;



/**
 * Application configuration. Get access to common used classes.
 * 
 * @author plinzen
 *
 */
public class Configuration {
	private static final String PARAM_DAO = "dao.factory.implementation";	
	private static final Configuration instance = new Configuration();
	
	private Map<String, DAOFactoryInterface> mDaoMap;
	private Configuration(){		
		mDaoMap = new HashMap<String, DAOFactoryInterface>();
	}
	
	public static Configuration getInstance(){
		return instance;
	}
	
	/**
	 * Returns the current {@link DAOFactoryInterface} for this session. 
	 * 
	 * @param session The {@link HttpSession} for the current user.
	 * @return
	 */
	public DAOFactoryInterface getDaoFactory(HttpSession session){
		DAOFactoryInterface daoImpl = null;
		String daoImplName = (String) session.getAttribute(PARAM_DAO);
		boolean existInSession = true;

		// Does not exist: Use initial value from context;
		if (daoImplName == null) {
			existInSession = false;
			daoImplName = session.getServletContext().getInitParameter(PARAM_DAO);			
		}
		
		// Get DAO from Map, if exist or create it.
		synchronized (mDaoMap) {
			if (mDaoMap.containsKey(daoImplName)){
				daoImpl = mDaoMap.get(daoImplName);
			} else {
				try {
					daoImpl = (DAOFactoryInterface) Class.forName(daoImplName).newInstance();					
					mDaoMap.put(daoImplName, daoImpl);	
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}				
		
		// Write current implementation in session.
		if (!existInSession && daoImpl != null){
			setDaoFactory(session, daoImpl.getClass());
		}
		
		return daoImpl;
		
	}
	
	/**
	 * Set new {@link DAOFactoryInterface} for this session.
	 * @param session
	 * @param implementation
	 */
	public void setDaoFactory(HttpSession session, Class<? extends DAOFactoryInterface> implementation){
		session.setAttribute(PARAM_DAO, implementation.getCanonicalName());
	}
}
