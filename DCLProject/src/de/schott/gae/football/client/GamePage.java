package de.schott.gae.football.client;

import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
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

public class GamePage {
	
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
		mLblHeader = new Label("Team 1 vs. Team 2 (0:0)");
		mLblHeader.setStyleName("h1");
		
		mTxtComment = new TextBox();
		mTxtMinute = new TextBox();
		mBtnSave = new Button("Add comment");
	
		// Initialize the service proxy.
		mGameService = GWT.create(GameService.class);
		mUserService = GWT.create(UserService.class);
	
	
	}
	
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
		
		reloadComments(gameId);
		
		final AsyncCallback<Void> saveCallback = new AbstractAsyncCallback<Void>() {
			@Override
			public void onSuccess(Void result) {
				reloadComments(gameId);				
			}			
		};
		mBtnSave.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				int minute = Integer.parseInt(mTxtMinute.getText());
				String message = mTxtComment.getText();
				if (minute <= 0){
					
				} else if (message == null || message.isEmpty()){
					
				} else {
					mGameService.saveComment(gameId, minute, message, saveCallback);	
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
				row++;
				for (TransferObject to : result){
					mTableComments.setText(row, 0, ((Integer) to.get("minute")).toString());
					mTableComments.setText(row, 1, (String) to.get("message"));
					row++;
				}
			}
		};
		mGameService.getComments(gameId, commentCallback);
	}

}
