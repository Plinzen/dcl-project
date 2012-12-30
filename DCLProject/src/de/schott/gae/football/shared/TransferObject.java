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
package de.schott.gae.football.shared;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Transfer object for communication and data exchange between client and server.
 * 
 * @author Christopher Schott
 *
 */
public class TransferObject implements Serializable{

	private static final long serialVersionUID = 1843441056404942667L;
	
	private Map<String, Object> mContent;
	
	public TransferObject(){
		mContent = new HashMap<String, Object>();
	}
	
	/**
	 * Put value to transfer object.
	 * @param key
	 * @param value
	 * @return
	 */
	public TransferObject put(String key, Object value){
		mContent.put(key, value);
		return this;
	}
	
	/**
	 * Clear whole transfer object.
	 * @return
	 */
	public TransferObject clear(){
		mContent.clear();
		return this;
	}
	
	/**
	 * Get value with given key.
	 * 
	 * @param key
	 * @return
	 */
	public Object get(String key){
		return mContent.get(key);
	}
	
}
