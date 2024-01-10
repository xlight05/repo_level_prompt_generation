package net.homeip.tinwiki.web.actions.cmsEdit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import net.homeip.tinwiki.web.forms.WebPage;

/**
 * @version: 1.0
 * @author: Tin Pham (www.tin.homeip.net)
 * @category: editor
 * 
 * Validate, declare entities, wrap contents and display to user for confirmation.
 */
public class editWebPageConfirmAction extends Action

{

    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        ActionErrors errors = new ActionErrors();
        ActionForward forward = new ActionForward(); // return value
        WebPage webPage = (WebPage) form;
 
        try {

			// -------------------------
			// Wrap contents with entity declaration.
			// ---------------------
        	
        	// == Declare all the entities that needs to be resolved here.

        	// Custom entities not located in file.	
        	String customEntityResolvers = "";
        	customEntityResolvers += "<!ENTITY ldquo \"&#8220;\">"; // left quote
        	customEntityResolvers += "<!ENTITY rdquo \"&#8221;\">"; // right quote
        	customEntityResolvers += "<!ENTITY apos \"&#39;\">"; // apostrophe
        	customEntityResolvers += " %lat1; ]>"; // to do, look up why JinWoo used this
        	        	
        	String serverDNS = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        	
        	// Check for a new version of this file. Why are quotes not handled here?
        	String entityResolver = "<!DOCTYPE submit [<!ENTITY % lat1 SYSTEM \"" + serverDNS + "/web/xhtml-lat1.ent\">" + customEntityResolvers;
        	System.out.println("entityResolver=" + entityResolver);
        	
            // do something here
        	System.out.println("executing editWebPageConfirmAction");
        	
        	String webContent = webPage.getContent();
        	System.out.println(webContent);
        	
        	       	
        	// Name of div id that will be used.
        	String startDivID =  "<div id=\"" + webPage.getSectionName() + "\">";

        	// Determine if div wrapper exists as sometimes tiny mce strips out parent div id.
        	boolean hasDiv;        	
        	if (webContent.indexOf(startDivID) == -1) {
        		hasDiv = false;
        		System.out.println("hasDiv=false");
        	} else {
        		hasDiv = true;
        		System.out.println("hasDiv=true");
        	}
        	if (!hasDiv) {
        		// Wrap the contents in the div id.
            	String endDivID = "</div>";
            	webContent = startDivID + webContent + endDivID;
        	}

        	// Wrap XML content with Doctype including entity declaration ** probably do not need this
        	webContent = entityResolver + webContent;
        	
        	webPage.setContent(webContent);
        	
        	// copy data to dto
        	// submit data to business tier for verification
        
        } catch (Exception e) {

            // Report the error using the appropriate name and ID.
            errors.add("name", new ActionError("id"));

        }

        // If a message is required, save the specified key(s)
        // into the request for use by the <struts:errors> tag.

        if (!errors.isEmpty()) {
            saveErrors(request, errors);
        }
        // Write logic determining how the user should be forwarded.
        forward = mapping.findForward("success");

        // Finish with
        return (forward);

    }
        
}
