/*
 * Created on Mar 26, 2005
 *
 */
package net.homeip.tin.extension.tag;

import java.io.IOException;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Writes out <a href="http://www.webopedia.com/TERM/B/bread_crumbs.html">bread
 * crumbs</a> based upon the browser URI requested by the browser.
 * 
 * www.tin.homeip.net/contextRoot/Administration/Add_User.jsp will generate the bread crumb,
 * Adminitration >> Add_User
 * 
 * @author Tin Pham
 * @version 0.1
 */
public class UrlBasedBreadCrumb extends TagSupport {

	public int doStartTag() throws JspTagException {
		// JSP engine should evaluate the contents and any child tags of this
		// tag.
		return EVAL_BODY_INCLUDE;
	}

	/*
	 * Called when the JSP engine encounters the end of a tag implemented by
	 * this class (non-Javadoc)
	 * 
	 * @see javax.servlet.jsp.tagext.Tag#doEndTag()
	 */
	public int doEndTag() throws JspTagException {

		// To Do: Optimize the user of strings.
		// To Do: Enhance custom tag to recieve parameters. 		
		
		HttpServletRequest request = null;
		String breadCrumb = null;
		String deliminator = null;
		String deliminatorExtension = null;
		String seperator = null;
		String pageURI = null;
		StringTokenizer st = null;

		request = (HttpServletRequest) pageContext.getRequest();

		pageURI = request.getRequestURI();
		deliminator = "/";
		deliminatorExtension = ".";
		seperator = " &rarr; ";
		st = new StringTokenizer(pageURI, deliminator);

		breadCrumb = "";

		// Skip n url parts that are not neccessary.
		for (int i = 0; st.hasMoreTokens() && i < 3; i++) {
			st.nextToken();
		}

		// Get the very first token.
		if (st.hasMoreTokens()) {
			breadCrumb += st.nextToken();
		}

		// Remaining tokens should be preceded by the seperator.
		while (st.hasMoreTokens()) {
			breadCrumb += seperator;
			breadCrumb += st.nextToken();
		}

		// Remove the file extension.
		st = new StringTokenizer(breadCrumb, deliminatorExtension);
		if (st.hasMoreTokens()) {
			breadCrumb = st.nextToken();
		} else {
			// To Do: Log that something is wrong.
			System.out.println("Something went wrong processing UrlBasedBreadCrumb.");
		}

		// Convert _ character to space character.
		breadCrumb = breadCrumb.replaceAll("_", " ");

		try {
			pageContext.getOut().write(breadCrumb);

		} catch (IOException ex) {
			throw new JspTagException(
					"Fatal error: Custom tag could not write to JSP out");
		}

		// The JSP engine should continue to evaluate the rest of this page.
		return EVAL_PAGE;
	}

}