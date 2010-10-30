
package com.la.runners.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.la.runners.client.res.Styles;
import com.la.runners.client.widget.form.SearchForm;
import com.la.runners.client.widget.grid.SearchGrid;

public class SearchEntryPoint implements EntryPoint {

    private static final String GWT_HOOK = "gwtHook";
    
    @Override
    public void onModuleLoad() {
        FlowPanel panel = new FlowPanel();
        panel.setStyleName(Styles.Form.entryPoint);
        RootPanel.get(GWT_HOOK).add(panel);
        Context context = new Context();
        panel.add(new SearchGrid(context));
        panel.add(new SearchForm(context));
    }

}
