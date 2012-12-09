package de.schott.gae.football.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Helper class for getting the {@link EntityManagerFactory} 
 * 
 * @author plinzen
 *
 */
public class EMF {

	private static final EntityManagerFactory emfInstance = Persistence.createEntityManagerFactory("transactions-optional");
	
	/**
	 * No instantiation
	 */
	private EMF(){
		
	}
	
	public static EntityManagerFactory get(){
		return emfInstance;
	}

	public static EntityManager getEntityManager(){
		return emfInstance.createEntityManager();
	}
}