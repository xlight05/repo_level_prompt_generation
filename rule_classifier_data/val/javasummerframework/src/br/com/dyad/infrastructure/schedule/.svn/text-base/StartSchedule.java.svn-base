package br.com.dyad.infrastructure.schedule;

import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.log4j.Logger;
import br.com.dyad.client.DyadBaseModel;
import br.com.dyad.infrastructure.service.ConnectionService;
import br.com.dyad.infrastructure.service.GenericServiceImpl;

public class StartSchedule implements ServletContextListener  {
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		try {
			GenericServiceImpl.getSystemInfo(sce.getServletContext());			
			ConnectionService service = new ConnectionService();			
			HashMap ret = service.getServiceValue(null);
			
			ArrayList<DyadBaseModel> connections = (ArrayList<DyadBaseModel>)ret.get("connections");
			for (DyadBaseModel dyadBaseModel : connections) {
				SystemScheduler.runScheduledTasks((String)dyadBaseModel.get("connection"));
			}									
		} catch (Exception e) {
			Logger log = Logger.getLogger(this.getClass());
			log.error("Error starting scheduler! " + e.getMessage());
			e.printStackTrace();
		}
	}

}
