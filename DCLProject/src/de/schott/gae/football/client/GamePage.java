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

import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.schott.gae.football.shared.TransferObject;

/**
 * Page showing comments to one game.
 * 
 * @author Christopher Schott
 *
 */
public class GamePage {
	private static final int REFRESH_INTERVALL = 5000;
	private static final DateTimeFormat DF = DateTimeFormat.getFormat("d.M.y H:mm");
	
	private UserServiceAsync mUserService;
	private GameServiceAsync mGameService;
	
	private FlexTable mTableComments;
	private Label mLblHeader;
	private TextBox mTxtMinute;
	private TextBox mTxtComment;
	private Button mBtnSave;
	
	public GamePage(){
		
		mTableComments = new FlexTable();
		mTableComments.setStyleName("game_table");
		mTableComments.setWidth("100%");
		mTableComments.getColumnFormatter().setWidth(0, "15%");
		mTableComments.getColumnFormatter().setWidth(1, "60%");
		mTableComments.getColumnFormatter().setWidth(2, "25%");
		
		mLblHeader = new Label("Team 1 vs. Team 2 (0:0)");
		mLblHeader.setStyleName("h1");
		
		mTxtComment = new TextBox();
		mTxtMinute = new TextBox();
		mTxtMinute.setMaxLength(3);
		mTxtMinute.setWidth("3em");
		mBtnSave = new Button("Add comment");
	
		// Initialize the service proxy.
		mGameService = GWT.create(GameService.class);
		mUserService = GWT.create(UserService.class);
	
	
	}
	
	/**
	 * Show game.
	 * 
	 * @param gameId
	 */
	public void showGame(final String gameId){
		// Ask for user		
		AsyncCallback<String> callback = new AbstractAsyncCallback<String>() {

			@Override
			public void onSuccess(String result) {
				if (result == null){
					Panel root = RootPanel.get(FootballEntry.CONTENT_ID);
					root.clear();
					root.add(new Label("You are not logged in. Please log in first."));
				} else {
					// Logged in
					showGameInternal(gameId);
				}
			}
		};
		mUserService.getRegisteredUser(callback);
	}
	
	private void showGameInternal(final String gameId){
		final VerticalPanel panel = new VerticalPanel();
		final HorizontalPanel panelComment = new HorizontalPanel();
		Panel root = RootPanel.get(FootballEntry.CONTENT_ID);
		
		// Set up callbacks.
		AsyncCallback<TransferObject> gameCallback = new AbstractAsyncCallback<TransferObject>() {
			public void onSuccess(TransferObject to) {
				mLblHeader.setText(DF.format((Date) to.get("date")) + " - "
						+ to.get("team1_name") + " vs. "
						+ to.get("team2_name") + " ("
						+ to.get("goal1") + ":"
						+ to.get("goal2") + ")");
			}
		};
		mGameService.getGame(gameId, gameCallback);
		
		// Load comments
		reloadComments(gameId);
		
		// Setup timer to refresh list automatically.
		Timer refreshTimer = new Timer() {
			@Override
			public void run() {
				reloadComments(gameId);
			}
		};
		refreshTimer.scheduleRepeating(REFRESH_INTERVALL);
		
		final AsyncCallback<Void> saveCallback = new AbstractAsyncCallback<Void>() {
			@Override
			public void onSuccess(Void result) {
				reloadComments(gameId);				
			}			
		};
		mBtnSave.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				try {
					int minute = Integer.parseInt(mTxtMinute.getText());
					String message = mTxtComment.getText();
					if (minute <= 0){
						Window.alert("Please enter a correct minute.");
					} else if (message == null || message.isEmpty()){
						Window.alert("Please enter a correct message.");
					} else {
						mGameService.saveComment(gameId, minute, message, saveCallback);
						mTxtComment.setText("");
						mTxtMinute.setText("");
					}				

				} catch (NumberFormatException e){
					Window.alert("Please enter a correct minute.");					
				}
								
			}
		});
		
		panel.add(mLblHeader);
		panel.add(mTableComments);
		panelComment.add(mTxtMinute);
		panelComment.add(mTxtComment);
		panelComment.add(mBtnSave);
		panel.add(panelComment);
		root.clear();		
		root.add(panel);

		
	}
	
	/**
	 * Reload Comments.
	 * 
	 * @param gameId
	 */
	private void reloadComments(final String gameId){
		AsyncCallback<List<TransferObject>> commentCallback = new AbstractAsyncCallback<List<TransferObject>>() {
			public void onSuccess(List<TransferObject> result) {
				mTableComments.clear();
				int row = 0;
				mTableComments.setText(row, 0, "Minute");
				mTableComments.setText(row, 1, "Message");
				mTableComments.setText(row, 2, "User");
				row++;
				for (TransferObject to : result){
					mTableComments.setText(row, 0, ((Long) to.get("minute")).toString());
					mTableComments.setText(row, 1, (String) to.get("message"));
					mTableComments.setText(row, 2, (String) to.get("account"));
					row++;
				}
			}
		};
		mGameService.getComments(gameId, commentCallback);
	}

}
