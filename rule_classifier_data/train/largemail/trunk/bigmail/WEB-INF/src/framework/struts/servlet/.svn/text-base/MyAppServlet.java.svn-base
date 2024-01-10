package framework.struts.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionServlet;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.util.RequestUtils;

/**
 * @web.servlet name="action" 
 * 				display-name="Struts Action Servlet"
 * 				load-on-startup="1" 
 * @web.servlet-init-param 	name="config" 
 * 							value="/WEB-INF/struts-config.xml"
 * @web.servlet-init-param 	name="debug" 
 * 							value="0"
 * 
 * @web.servlet-mapping url-pattern="*.do"
 */
public class MyAppServlet extends ActionServlet {

		protected void process(
		HttpServletRequest request,
		HttpServletResponse response)
		throws IOException, ServletException {
		String uri = request.getRequestURI();
		
		if ((uri.indexOf("reload.do") != (-1))) {
			init();

			RequestUtils.selectModule(request, getServletContext());
			ModuleConfig config = getModuleConfig(request);
			getRequestProcessor(config).init(this, config);

			RequestDispatcher dispatcher =
				request.getRequestDispatcher("/reload.jsp");
			dispatcher.forward(request, response);
		}else {
			super.process(request, response);
		}
	}
}
