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
package de.schott.gae.football.client;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Workaround for hidden Types in {@link List}s or {@link Map}s. 
 * 
 * @author Christopher Schott
 *
 */
public class SerializableWhiteList implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Integer dummyInt;
	public Long dummyLong;
	public Date dummyDate;
	public Boolean dummyBoolean;
}
