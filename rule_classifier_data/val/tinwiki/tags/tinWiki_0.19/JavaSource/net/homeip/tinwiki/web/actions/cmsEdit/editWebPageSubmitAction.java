package net.homeip.tinwiki.web.actions.cmsEdit;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import org.xml.sax.InputSource;
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
 * Creates a backup of the existing file and writes a new file with the requested changes.
 */
public class editWebPageSubmitAction extends Action {

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
			System.out.println("SAXBuilder builder2 = new SAXBuilder();");
		
			// == Load the user input into a JDOM Document object.
			webPage.setContent("<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>" + webPage.getContent());
			System.out.println(webPage.getContent());
			
			System.out.println("Files Exists.");
			
			// not working in tomcat 5.5, exception = java.net.ConnectException: Connection refused: connect
			
			// Construct a StreamSource from a byte stream. Normally, a stream should be used rather than a reader, 
			// so the XML parser can resolve character encoding specified by the XML declaration.
						
			// Document tempDoc = builder2.build(new StringReader(webPage.getContent()));
			Document tempDoc = builder2.build(new InputSource(new StringReader(webPage.getContent())));
			
			System.out.println("Document tempDoc =");
			Element tempElement = tempDoc.getRootElement();
			System.out.println("Loaded the user input into a JDOM Document object.");

			// == Write out the Element to String.

			// formatting
			Format myFormat = Format.getPrettyFormat();
			myFormat.setIndent("\t");
			myFormat.setEncoding("ISO-8859-1");
			myFormat.setOmitDeclaration(false);
			
			String contentToEdit2 = null;
			Element subtree2 = tempElement;

			StringWriter stringWriter2 = new StringWriter();
			XMLOutputter myXMLOutputter2 = new XMLOutputter(myFormat);
			myXMLOutputter2.output(subtree2, stringWriter2);
			
			StringBuffer stringBuffer2 = stringWriter2.getBuffer();
			contentToEdit2 = stringBuffer2.toString();
			System.out.println("Write out the Element to String.");
			
			// -------------------------
			// Find dom piece to replace.
			// ---------------------

			// == Load the document from file in JDOM Document object. 
			SAXBuilder builder = new SAXBuilder();
			File inputFile = new File(myFile);
			Document doc = builder.build(inputFile);
			System.out.println("Load the document from file in JDOM Document object.");
			
			System.out.println("getSectionName=" + webPage.getSectionName());

			// == Search DOM to find the particular element to to be edited.
			List listBodyDivs = doc.getRootElement().getChild("body").getChildren("div");
			Element currentElement = null;
			for (int i=0; i<(listBodyDivs.size()); i++) {
				currentElement = (Element) listBodyDivs.get(i);
				System.out.println(currentElement.getAttributeValue("id"));

				if (webPage.getSectionName().equals(currentElement.getAttributeValue("id"))) {
					System.out.println("first match found at " + i);
					
					
					listBodyDivs.remove(i);
					// Need to use .clone() or else breaks in TomCat. loook into this more later on.
					listBodyDivs.add(i, tempElement.clone());
					
					// Break now that section has been found.
					i = listBodyDivs.size();
				}
			}

			// == Write out the identified subtree of the DOM to String.
			String contentToEdit = null;
			Element subtree = currentElement;

			StringWriter stringWriter = new StringWriter();
			XMLOutputter myXMLOutputter = new XMLOutputter(myFormat);
			myXMLOutputter.output(subtree, stringWriter);
			
			StringBuffer stringBuffer = stringWriter.getBuffer();
			contentToEdit = stringBuffer.toString();
			System.out.println ("Old Content = \n" + contentToEdit);

			
			// == Stream ENTIRE DOM to String.
			StringWriter in = new StringWriter();			
			XMLOutputter outp = new XMLOutputter(myFormat);
			outp.output(doc, in);

			StringBuffer output = in.getBuffer();
			String entireDocument = output.toString();
			in.close();
			
			// == Replace any existing UTF-8 text to ISO-8859-1 so the document would have ISO-8859-1 encoding.
			// == Limitation: If the user wishes to type in the text "UTF-8" in the content of the file, the system would not allow him/her to do so.
			//  		  It will replace any existing text "UTF-8" and the page would display the text "ISO-8859-1" instead.
			// == Better solution: Find out where the UTF-8 encoding declaration is taking place and fix it to ISO-8859-1
			// int lookupUTF8 = entireDocument.indexOf("UTF-8");
			// System.out.println("lookupUTF8=" + lookupUTF8);			
			// entireDocument = entireDocument.replaceAll("UTF-8", "ISO-8859-1");
			//
			// == Solution found by setting the format.
			
			System.out.println ("New doc = \n" + entireDocument);

			// Put into webPage EXACTLY what is being written to file.
			webPage.setContent(entireDocument);
			
			// -------------------------
			// Write file.
			// ---------------------
			
			BufferedWriter bufferedOut = null;
			FileWriter out = null;
			
			out = new FileWriter(outputFile);
			bufferedOut = new BufferedWriter(out); // wrap for efficient writing

			// Do the file writing.
			bufferedOut.write(entireDocument);
			bufferedOut.close();
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
}
