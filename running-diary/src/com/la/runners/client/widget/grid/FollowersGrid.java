package com.la.runners.client.widget.grid;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.la.runners.client.Constants;
import com.la.runners.client.ServiceAsync;
import com.la.runners.shared.Profile;

public class FollowersGrid extends BaseGrid {

    public FollowersGrid(ServiceAsync service) {
        super(service);
        showMessage("Loading...");
        service.getFollowers(new AsyncCallback<List<Profile>>() {
            @Override
            public void onSuccess(List<Profile> result) {
                drawGrid(result);
                showMessage("");
            }
            @Override
            public void onFailure(Throwable caught) {
                showMessage("There was an error while requesting data to the server");
            }
        });
    }
    
    private void drawGrid(List<Profile> result) {
        grid.clear();
        if(result == null || result.isEmpty()) {
            showMessage("No result found");
        } else {
            grid.setWidget(0,0, createLabel("Nickname", Constants.Style.gridHeaderCell));
            grid.setWidget(0,1, createLabel("Remove", Constants.Style.gridHeaderCell));
            grid.setWidget(0,2, createLabel("Profile Page", Constants.Style.gridHeaderCell));
            int index = 1;
            for(Profile profile : result) {
                grid.setWidget(index,0, createLabel(profile.getNickname(), Constants.Style.gridCell));
                final String followerUserId = profile.getUserId();
                Button btnProfile = new Button("Profile Page");
                btnProfile.addClickHandler(new ClickHandler() {
                    @Override
                    public void onClick(ClickEvent event) {
                        showMessage("function not implemented yet");
                    }
                });
                grid.setWidget(index,1, btnProfile);
                final int rowIndex = index;
                Button btnRemove = new Button("Remove");
                btnRemove.addClickHandler(new ClickHandler() {
                    @Override
                    public void onClick(ClickEvent event) {
                        service.removeFollower(followerUserId, new AsyncCallback<Void>() {
                            @Override
                            public void onFailure(Throwable caught) {
                                showMessage("Umm, there was some problem, try to refresh the browser");
                            }
                            @Override
                            public void onSuccess(Void result) {
                                showMessage("Success!");
                                grid.removeRow(rowIndex);
                            }
                        });
                    }
                });
                grid.setWidget(index,2, btnRemove);
                index++;
            }
        }
    }

}
