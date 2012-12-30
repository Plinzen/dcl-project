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

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry Point for application
 * @author Christopher Schott
 *
 */
public class FootballEntry implements EntryPoint {

	/**
	 * ID of content panel
	 */
	public static final String CONTENT_ID = "content";
	
	private static Menu menu = new Menu();
	
	@Override
	public void onModuleLoad() {

	    menu.createMenu(RootPanel.get("menu"));
	    
	    RootPanel.get(CONTENT_ID);
	}

}
