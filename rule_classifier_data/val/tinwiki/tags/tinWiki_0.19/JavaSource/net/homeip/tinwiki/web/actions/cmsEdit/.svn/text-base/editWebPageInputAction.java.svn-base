package net.homeip.tinwiki.web.actions.cmsEdit;

import java.io.File;
import java.io.StringWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.homeip.tinwiki.web.forms.WebPage;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import orgx.jdom.Document;
import orgx.jdom.Element;
import orgx.jdom.input.SAXBuilder;
import orgx.jdom.output.Format;
import orgx.jdom.output.XMLOutputter;

/**
 * @version: 1.0
 * @author: Tin Pham (www.tin.homeip.net)
 * @category: editor
 * 
 * Parses xhtml file and retrieves the subsection to be edited.
 */
public class editWebPageInputAction extends Action
{
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		ActionErrors errors = new ActionErrors();
		ActionForward forward = new ActionForward(); // return value
		WebPage webPage = (WebPage) form;

		try {

			// -------------------------
			// Retrieve subsection of dom requested for editing.
			// ---------------------
			
			System.out.println("webPage.getSectionName()"+webPage.getSectionName());
			System.out.println("webPage.getFileName()"+webPage.getFileName());
			
			// == Initialize file object.

			// Note, when building filenames, here are two examples of strings that will work:
			//    webPage.setFileName("web\\wiki\\SampleSection\\Overview.jsp");
			//    webPage.setFileName("/web/wiki/SampleSection/Overview.jsp");
			
			// Define the root aspect for example, "/web/wiki".
			String realPath = getServlet().getServletContext().getRealPath("/");
			
			// Define the entire files for example, realPath + "/" + "SampleSection/Overview.jsp".
			String myFile = realPath + File.separatorChar + webPage.getFileName();
			
			File inputFile = new File(myFile);

			// == Load the document in JDOM Document object. 
			
			SAXBuilder builder = new SAXBuilder();
			Document doc = builder.build(inputFile);			
			
			// == Search DOM to find the particular element to to be edited.
			
			List listBodyDivs = doc.getRootElement().getChild("body").getChildren("div");
			Element currentElement = null;
			for (int i=0; i<(listBodyDivs.size()); i++) {
				currentElement = (Element) listBodyDivs.get(i);
				System.out.println(currentElement.getAttributeValue("id"));
				if (webPage.getSectionName().equals(currentElement.getAttributeValue("id"))) {
					System.out.println("first match found at " + i);
					i = listBodyDivs.size();
				}				
			}
			
			// == Write out the identified subtree of the DOM to String.

			// formatting
			Format myFormat = Format.getPrettyFormat();
			myFormat.setIndent("\t");
			myFormat.setEncoding("ISO-8859-1");
			myFormat.setOmitDeclaration(true); // since this is going into Tiny MCE for editing do not render the declarations (encoding)
			
			String contentToEdit = null;
			Element subtree = currentElement;
			
			StringWriter stringWriter = new StringWriter();
			XMLOutputter myXMLOutputter = new XMLOutputter(myFormat);

			myXMLOutputter.output(subtree, stringWriter);
			
			StringBuffer stringBuffer = stringWriter.getBuffer();
			contentToEdit = stringBuffer.toString();
			
			System.out.println ("*****Start of Content to Edit*****");
			System.out.println (contentToEdit);
			System.out.println ("*****End of Content to Edit*****");
			
			webPage.setContent(contentToEdit);
						
			stringWriter.close();	


		} catch (Exception e) {

			System.out.println(e);
			// Report the error using the appropriate name and ID.
			errors.add("name", new ActionError("id"));

		}

		// If a message is required, save the specified key(s) into the request for use by the <struts:errors> tag.
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
		
		// Write logic determining how the user should be forwarded.
		forward = mapping.findForward("success");

		// Finish with
		return (forward);

	}
	
}
