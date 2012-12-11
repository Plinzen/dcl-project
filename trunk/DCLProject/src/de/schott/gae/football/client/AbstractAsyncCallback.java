/**
 * 
 */
package de.schott.gae.football.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Abstract {@link AsyncCallback} which provides a generic onFailure method.
 * 
 * @author plinzen
 *
 */
public abstract class AbstractAsyncCallback<T> implements AsyncCallback<T> {

	@Override
	public void onFailure(Throwable caught) {
		Panel error =RootPanel.get("error"); 
		error.clear();
		error.add(new Label(caught.getLocalizedMessage()));			
	}

}
