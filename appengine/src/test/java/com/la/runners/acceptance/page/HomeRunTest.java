package com.la.runners.acceptance.page;

import org.junit.Test;

import com.la.runners.acceptance.Constants;
import com.la.runners.acceptance.UserWithProfileSetPageTest;
import com.la.runners.acceptance.framework.Session;
import com.la.runners.client.widget.form.RunForm;
import com.la.runners.client.widget.grid.RunGrid;

public class HomeRunTest extends UserWithProfileSetPageTest {
    
    public HomeRunTest() {
        super(Constants.Jsp.home);
    }
    
    @Override
    protected String[] getIds() {
        return new String[] {RunForm.ID, RunGrid.ID};
    }

    @Test
    public void shouldInsert() {
        Method.insertSampleRun(session);
    }
    
    @Test
    public void shouldDelete() {
        Method.insertSampleRun(session);
        
    }
    
    @Test
    public void shouldUpdate() {
        Method.insertSampleRun(session);
        
    }

    @Test
    public void shouldGetList() {
        Method.insertSampleRun(session, "run1");
        Method.insertSampleRun(session, "run2");
        
    }
    
    public static final class Method {
        
        public static final void insertSampleRun(Session session, String description) {
            //session.fillInputById(DISTANCE_ID, "12000");
            //session.fillInputById(NOTE_ID, description);
        }
        
        public static final void insertSampleRun(Session session) {
            //insertSampleRun(session, "description");
        }
        
    }

}
