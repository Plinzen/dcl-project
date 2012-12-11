/**
 * 
 */
package de.schott.gae.football.shared;

import java.io.Serializable;

/**
 * Common database exception.
 * 
 * @author plinzen
 *
 */
public class DatabaseException extends Exception implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4486051670341868203L;

	public DatabaseException(){
		super();
	}
	
	public DatabaseException(String message){
		super(message);
	}
	
	public DatabaseException(Throwable cause){
		super(cause);				
	}
	
	public DatabaseException(String message, Throwable cause){
		super(message, cause);
	}
	
}
