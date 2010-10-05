package com.la.runners.server.servlet;


import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.la.runners.client.EditorService;
import com.la.runners.server.dao.jdo.JdoRunDao;
import com.la.runners.shared.Run;

public class EditorServiceImpl extends RemoteServiceServlet implements EditorService {
	
	private static final long serialVersionUID = 1L;

	private JdoRunDao dao = new JdoRunDao(Run.class);
	
	@Override
	public void save(Run run) {
	    UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        run.setUserId(user.getUserId());
		dao.save(run);
	}

	@Override
	public Run get(Long id) {
		return dao.get(id);
	}

    @Override
    public List<Run> search(Integer year, Integer month) {
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
    	List<Run> result = dao.search(year, month, user.getUserId());
    	if(result == null) {
    		return null;
    	}
    	List<Run> runs = new ArrayList<Run>();
        for(Run run : result) {
        	Run newRun  = new Run();
        	newRun.setId(run.getId());
        	newRun.setDistance(run.getDistance());
        	newRun.setDate(run.getDate());
        	newRun.setYear(run.getYear());
        	newRun.setMonth(run.getMonth());
        	newRun.setDay(run.getDay());
        	newRun.setTime(run.getTime());
        	newRun.setShare(run.getShare());
        	newRun.setShoes(run.getShoes());
        	newRun.setWeight(run.getWeight());
        	newRun.setNote(run.getNote());
        	newRun.setHeartRate(run.getHeartRate());
        	newRun.setModified(run.getModified());
        	runs.add(newRun);
        }
    	return runs;
    }

}
