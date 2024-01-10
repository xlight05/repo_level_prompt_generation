package net.homeip.tinwiki.web.actions.cmsEdit;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.homeip.tinwiki.web.forms.WebPage;
import net.homeip.tinwiki.web.xml.LocalXHTMLEntityResolver;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.xml.sax.InputSource;
import orgx.jdom.Document;
import orgx.jdom.Element;
import orgx.jdom.Namespace;
import orgx.jdom.input.SAXBuilder;
import orgx.jdom.output.Format;
import orgx.jdom.output.XMLOutputter;

/**
 * @version: 1.0
 * @author: Tin Pham (www.tin.homeip.net)
 * @category: editor
 * 
 * Creates a backup of the existing file and writes a new file with the requested changes.
 */
public class editWebPageSubmitAction extends Action {

	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// Struts specific variables
		ActionErrors errors = new ActionErrors();
		ActionForward forward = new ActionForward(); // return value
		WebPage webPage = (WebPage) form;

		// Construct the full file path.
		String realPath = getServlet().getServletContext().getRealPath("/");
		String myFile = realPath + File.separatorChar + webPage.getFileName();

		// When a child is in a namespace, you must retrieve using a Namespace argument.
		Namespace ns = Namespace.getNamespace("xhtml", "http://www.w3.org/1999/xhtml");
		//Namespace ns = doc.getRootElement().getNamespace();
		
		try {

			System.out.println("executing editWebPageSubmitAction");

			// Store in dto.

			// -------------------------
			// Retrieve file
			// ---------------------

			// == Initialize file object.
			File outputFile = null;
			
			outputFile = new File(myFile);

			// -------------------------
			// Backup file
			// ---------------------

			// == Backup file if it already exists.
			if (outputFile.exists()) {
				System.out.println("Files Exists.");
				backupOriginalFile(myFile);
			}

			// -------------------------
			// Convert user entered information to DOM.
			// ---------------------
			
			System.out.println("Converting user entered information to DOM.");
			
			SAXBuilder builder2 = new SAXBuilder();		
			builder2.setEntityResolver(new LocalXHTMLEntityResolver());
		
			// == Load the user input into a JDOM Document object.			
			webPage.setContent(webPage.getContent());
			
			// Construct a StreamSource from a byte stream. Normally, a stream should be used rather than a reader, 
			// so the XML parser can resolve character encoding specified by the XML declaration.
						
			Document tempDoc = builder2.build(new InputSource(new StringReader(webPage.getContent())));
			
			System.out.println("**** Loaded the user input into a JDOM Document object.");

			System.out.println("tempDoc.getRootElement().setNamespace" + tempDoc.getRootElement().getNamespace());
			xmlWriter (tempDoc.getRootElement(), "User input now inside a JDOM Document" );

			List tempList = tempDoc.getRootElement().getChild("body", ns).getChildren("div", ns);
			Element childElement = (Element)tempList.get(0);
			
			xmlWriter (childElement, "new data now called childElement to move into the main document" );			
						
			// -------------------------
			// Find dom piece to replace.
			// ---------------------

			// == Load the document from file in JDOM Document object. 
			SAXBuilder builder = new SAXBuilder();
			builder.setEntityResolver(new LocalXHTMLEntityResolver());
			
			File inputFile = new File(myFile);
			Document doc = builder.build(inputFile);
			System.out.println("Load the document from file in JDOM Document object.");
			
			System.out.println("getSectionName=" + webPage.getSectionName());
			
			xmlWriter (doc.getRootElement(), "document loaded form file");

			// == Search DOM to find the particular element to to be edited.		

			List listBodyDivs = doc.getRootElement().getChild("body", ns).getChildren("div", ns);
						
			Element currentElement = null;
			int elementLocation = -1;
			
			for (int i=0; i<(listBodyDivs.size()); i++) {
				currentElement = (Element) listBodyDivs.get(i);
				System.out.println("currentElement=" + currentElement.getAttributeValue("id"));

				System.out.println("webPage.getSectionName()=" + webPage.getSectionName());
				System.out.println("currentElement.getAttributeValue(\"id\")=" + currentElement.getAttributeValue("id"));
				if (webPage.getSectionName().equals(currentElement.getAttributeValue("id"))) {
					System.out.println("first match found at " + i);
					
					elementLocation = i;					
					
					// Add the new element.
					listBodyDivs.add(i, childElement.clone());
					
					// Remove the old element.
					listBodyDivs.remove(elementLocation+1);					
					
					// Break now that section has been found.
					i = listBodyDivs.size();
				}
				
			}
						
			// == Write out the identified subtree of the DOM to String.	
			xmlWriter (currentElement, "new data now called childElement to move into the main document" );
						
			String entireDocument = xmlWriter (doc, "ENTIRE NEW document." );
			
			// Put into webPage EXACTLY what is being written to file.
			webPage.setContent(entireDocument);
			
			// -------------------------
			// Write file using the matching encoding defined by the XMLOutputter
			// ---------------------
			
			// Solution for writing out with proper encoding.
			// http://www.cafeconleche.org/books/xmljava/chapters/ch03s03.html
			OutputStream fout = new FileOutputStream(outputFile);
			OutputStream bout = new BufferedOutputStream(fout); // improve performance using buffers
			OutputStreamWriter out = new OutputStreamWriter(bout, "UTF8"); // specify encoding
			
			// this may not be good as it is already a string and characters printed to string 
			// may be converted to the platform's default charcter encoding
			out.write(entireDocument);			
			
			out.flush();
			out.close();
			
			System.out.println("**** File written = " + myFile);

		} catch (Exception e) {

			System.out.println(e);
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

		return (forward);

	}

	private void backupOriginalFile(String myFile) {
		
		// Construct date string.
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd_S");		
		String datePortion = format.format(date);
				
		try {
			FileInputStream fis = new FileInputStream(myFile);
			FileOutputStream fos = new FileOutputStream(myFile + "-" + datePortion + "_UserID" + ".html");
			FileChannel fcin = fis.getChannel();
			FileChannel fcout = fos.getChannel();

			// Do the file copy.
			fcin.transferTo(0, fcin.size(), fcout);

			fcin.close();
			fcout.close();
			fis.close();
			fos.close();

			System.out.println("**** Backup of file made.");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private String xmlWriter (Element xmlElement, String comment) throws Exception {
		
		// formatting
		Format myFormat = Format.getPrettyFormat();
		myFormat.setIndent("\t");
		myFormat.setEncoding("UTF-8");		
		
		String contentToEdit = null;

		Element subtree = xmlElement;
		
		StringWriter stringWriter = new StringWriter();
		XMLOutputter myXMLOutputter = new XMLOutputter(myFormat);

		myXMLOutputter.output(subtree, stringWriter);
		
		// Buffer is supposed to be more efficient..
		StringBuffer stringBuffer = stringWriter.getBuffer();
		contentToEdit = stringBuffer.toString();
		
		System.out.println ("*****Start of xmlWrite:" + comment + "*****");
		System.out.println ("\n" + contentToEdit);
		System.out.println ("*****End of xmlWrite:" + comment + "*****");
		
		stringWriter.close();
		
		return contentToEdit;		
	}
	
	private String xmlWriter (Document doc, String comment) {
		
		String xmlAsString = null;		
		
		// formatting
		Format myFormat = Format.getPrettyFormat();
		myFormat.setIndent("\t");
		myFormat.setEncoding("UTF-8");

		XMLOutputter outputter = new XMLOutputter(myFormat);
		
		// Not using buffer for now... keeping it simple to make sure this is correct as it is going into file.
		xmlAsString = outputter.outputString(doc);

		System.out.println ("*****Start of xmlWrite:" + comment + "*****");
		System.out.println ("\n" + xmlAsString);
		System.out.println ("*****End of xmlWrite:" + comment + "*****");
		
		return xmlAsString;
				
	}

}
