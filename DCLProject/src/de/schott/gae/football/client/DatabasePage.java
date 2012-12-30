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

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.schott.gae.football.shared.TransferObject;

/**
 * Page for choosing database implementation
 * 
 * @author Christopher Schott
 *
 */
public class DatabasePage {
	private DatabaseServiceAsync mDatabaseService;	
	private AsyncCallback<List<TransferObject>> mDatabaseCallback;
	
	public DatabasePage(){
		
		// Initialize the service proxy.
		if (mDatabaseService == null) {
			mDatabaseService = GWT.create(DatabaseService.class);
		}
	
		// Setup callback
		mDatabaseCallback = new AbstractAsyncCallback<List<TransferObject>>() {

			@Override
			public void onSuccess(List<TransferObject> result) {
				createPage(result);
			}
		};
	
	}
	
	/**
	 * Create view for choosing implementation
	 */
	public void createView(){
		// Ask for implementations
		mDatabaseService.getDatastoreImplementations(mDatabaseCallback);
		
	}
	
	/**
	 * Create page with possible solutions. async.
	 * 
	 * @param implementations
	 */
	private void createPage(List<TransferObject> implementations){
		Panel root = RootPanel.get(FootballEntry.CONTENT_ID);
		root.clear();
		
		VerticalPanel panel = new VerticalPanel();
		Label lblHeader = new Label("Choose Database implementation");		
		lblHeader.setStyleName("h1");
		
		
		// Make some radio buttons, all in one group.
		VerticalPanel radioPanel = new VerticalPanel();
		final List<RadioButton> radioButtons = new ArrayList<RadioButton>();
		for (TransferObject to : implementations){
			RadioButton radio = new RadioButton("radiogroup", (String) to.get("name"));
			radio.setValue((Boolean)to.get("selected"));
			Integer id = (Integer) to.get("id");
			radio.setFormValue(id.toString());
			radioButtons.add(radio);
			radioPanel.add(radio);
		}
		
		Button btnOk = new Button("Save");
		btnOk.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				TransferObject to = new TransferObject();
				for (RadioButton radio : radioButtons){
					if (radio.getValue()){
						Integer id = Integer.parseInt(radio.getFormValue());
						to.put("id", id);
						mDatabaseService.setDatastoreImplementation(to, new AbstractAsyncCallback<Void>() {
							@Override
							public void onSuccess(Void result) {
								Window.alert("New Implementation saved.");
								createView();
							}
						});
						break;
					}
				}								
			}
		});
	    		
		panel.add(lblHeader);
		panel.add(radioPanel);
		panel.add(btnOk);
		root.add(panel);		
		
	}

}
