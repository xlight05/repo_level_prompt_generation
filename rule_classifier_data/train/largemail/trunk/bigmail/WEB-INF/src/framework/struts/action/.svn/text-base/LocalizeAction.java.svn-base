/**
 * 
 */
package framework.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.Globals;

import java.util.Locale;

/**
 * @struts.action
 * 			path="/locale"
 *			validate="false"
 *@struts.action-forward
 *			name="Success"
 *			path="/upload_view.jsp"
 *			redirect="false"
 */
public class LocalizeAction extends Action {

	final Log log = LogFactory.getLog(this.getClass());
	
	public ActionForward execute(ActionMapping mapping, ActionForm form	, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {

			Locale preferredLocale= request.getLocale();
			Locale defaultLocale = Locale.getDefault();
			if(log.isDebugEnabled()){
				log.debug("preferred locale="+preferredLocale.getLanguage());
			}
			if(log.isDebugEnabled()){
				log.debug("default locale="+defaultLocale.getLanguage());
			}
			
			String language = request.getParameter("region");
			Locale loc = new Locale(language,"");
			
			setSessionObject(request, loc);
			
		return mapping.findForward("Success"); 
	}
	
	private void setSessionObject(HttpServletRequest request, Locale locale){
		HttpSession session = request.getSession(false);
		
		Locale loc = (Locale)session.getAttribute(Globals.LOCALE_KEY);
		
		if(loc==null){
			session.setAttribute(Globals.LOCALE_KEY, locale);
			if(log.isDebugEnabled()){
				log.debug("locale has been set :"+loc.getLanguage());
			}
			
		}else{
			
			if(loc.getLanguage().equalsIgnoreCase(locale.getLanguage())){
				if(log.isDebugEnabled()){
					log.debug("locale is not changed :"+loc.getLanguage());
				}
			}else{
				session.setAttribute(Globals.LOCALE_KEY, locale);
				if(log.isDebugEnabled()){
					log.debug("locale is changed from "+loc.getLanguage()+" to "+locale.getLanguage());
				}
			}
		}
	}
	
}
