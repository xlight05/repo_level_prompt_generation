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
			// Wrap contents with so it is a properly formed xhml document.
			// ---------------------
        	
        	// [to do] yes at this point we can start using jdom to format things nicely again... but this works for now
        	String xhtmlStart = "<html xmlns:xhtml=\"http://www.w3.org/1999/xhtml\"><head><title>temp</title><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" /></head><body>";
        	String xhtmlEnd = "</body></html>";
        	
        	
			// -------------------------
			// Wrap contents with entity declaration for the xml parser.
			// ---------------------
        	
        	// == Declare all the entities that needs to be resolved here so it loads properly in JDOM

        	/*
        	// Custom entities not located in file.	
        	String customEntityResolvers = "";
        	customEntityResolvers += "<!ENTITY ldquo \"&#8220;\">"; // left quote
        	customEntityResolvers += "<!ENTITY rdquo \"&#8221;\">"; // right quote
        	customEntityResolvers += "<!ENTITY apos \"&#39;\">"; // apostrophe
        	customEntityResolvers += " %lat1; ]>"; // to do, look up why JinWoo used this
        	        	
        	String serverDNS = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        	
        	// It seems the xhtml doctype from w3c includes all this...
        	// Check for a new version of the xhtml-lat1.ent file. Why are quotes not handled here?
        	String entityResolver = "<!DOCTYPE submit [<!ENTITY % lat1 SYSTEM \"" + serverDNS + "/web/xhtml-lat1.ent\">" + customEntityResolvers;
        	//System.out.println("entityResolver=" + entityResolver);
        	*/
        	        	        	
        	// == Using w3c doctype which should include special entities such as those found in xhtml-lat1.ent.        	

        	// Originally we had to declare custom entities for: left quote, right quote and apostrophe        	
        	
        	String xhtmlHeadDeclarations = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";
        	xhtmlHeadDeclarations += "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">";
        	
        	System.out.println("executing editWebPageConfirmAction");
        	
        	String webContent = webPage.getContent();
        	//System.out.println(webContent);        	
        	       	
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
            	System.out.println("Putting in div wrapper.");
        	}

			// -------------------------
			// Put everything together to form an XHTML string that the JDOM parser will be able to understand 
			// ---------------------        	        	
        	webContent = xhtmlStart + webContent + xhtmlEnd;

        	// Wrap contents into a true xhtml document with Doctypes including entity declaration
        	// webContent = entityResolver + webContent;
        	
        	webContent = xhtmlHeadDeclarations + webContent;

			System.out.println ("*****Start of NEW Content*****");
			System.out.println (webContent);
			System.out.println ("*****End of NEW Content*****");
        	
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
