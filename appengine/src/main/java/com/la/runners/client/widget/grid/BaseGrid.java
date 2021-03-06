package com.la.runners.client.widget.grid;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.la.runners.client.Context;
import com.la.runners.client.ServiceAsync;
import com.la.runners.client.res.Resources;
import com.la.runners.client.res.Strings;
import com.la.runners.client.widget.form.field.converter.UnitConverter;
import com.la.runners.client.widget.grid.toolbar.MessageBar;
import com.la.runners.shared.Profile;

public abstract class BaseGrid extends Composite {

    protected FlexTable grid;
    
    private Context context;
    
    protected MessageBar topBar;
    
    protected MessageBar bottomBar;
    
    private FlowPanel container;
    
    public BaseGrid(Context context, String debugId) {
        this.context = context;
        container = new FlowPanel();
        topBar = getTopBar();
        container.add(topBar);

        grid = new FlexTable();
        grid.setStyleName(Resources.INSTANCE.grid().table());
        container.add(grid);
        
        bottomBar = getBottomBar();
        container.add(bottomBar);
        
        initWidget(container);
        ensureDebugId(debugId);
        setMainColorStyle(Resources.INSTANCE.grid().green());
    }
    
    protected Strings strings() {
        return context.strings;
    }
    
    protected ServiceAsync service() {
        return context.getService();
    }
    
    protected HandlerManager eventBus() {
        return context.getEventBus();

    }
    protected UnitConverter unitConverter() {
        return context.getUnitConverter();
    }

    protected Profile profile() {
        return context.getProfile();
    }
    
    protected void showMessage(String message) {
        topBar.showMessage(message);
        bottomBar.showMessage(message);
    }
    
    protected Label createLabel(Object obj, String style) {
        String c = ""; 
        if(obj instanceof String) {
            c = (String)obj;
        } else if(obj instanceof Long) {
            c = ((Long)obj).toString();
        } else if(obj instanceof Integer) {
            c = ((Integer)obj).toString();
        }
        Label l = new Label((String)c);
        l.setStyleName(style);
        return l;
    }
    
    protected MessageBar getBottomBar() {
        return new MessageBar(true);
    }
    
    protected MessageBar getTopBar() {
        return new MessageBar();
    }

    public void setMainColorStyle(String mainColorStyle) {
        setStyleName(Resources.INSTANCE.grid().grid());
        addStyleName(mainColorStyle);
    }

}
