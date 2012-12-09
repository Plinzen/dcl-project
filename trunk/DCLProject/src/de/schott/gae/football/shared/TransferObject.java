package de.schott.gae.football.shared;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Transfer object for communication and data exchange between client and server.
 * 
 * @author plinzen
 *
 */
public class TransferObject implements Serializable{

	private static final long serialVersionUID = 1843441056404942667L;
	
	private Map<String, Object> mContent;
	
	public TransferObject(){
		mContent = new HashMap<String, Object>();
	}
	
	public TransferObject put(String key, Object value){
		mContent.put(key, value);
		return this;
	}
	
	public TransferObject clear(){
		mContent.clear();
		return this;
	}
	
	public Object get(String key){
		return mContent.get(key);
	}
	
}
