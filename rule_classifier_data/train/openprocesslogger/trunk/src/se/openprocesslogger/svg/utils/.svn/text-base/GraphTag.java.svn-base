package se.openprocesslogger.svg.utils;

import java.io.IOException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

public class GraphTag extends TagSupport {
	private PageContext pageContext;
	private Tag parent;
	private GraphProxy graphProxy;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4823467954018421103L;


	/**
	 * Method called at start of Tag
	 * @return either a EVAL_BODY_INCLUDE or a SKIP_BODY
	 */
	public int doStartTag() throws javax.servlet.jsp.JspTagException {
		try {
			this.buildContent(pageContext.getOut());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pageContext.getResponse().setContentType("image/svg+xml");
		return SKIP_BODY;
	}
	
	/**
	 * Method Called at end of Tag
	 * @return either EVAL_PAGE or SKIP_PAGE
	 */
	public int doEndTag() throws javax.servlet.jsp.JspTagException {
		return EVAL_PAGE;
	}

	/**
	 * Method called to releases all resources
	 */
	public void release() {
	}

	/** Method used by the JSP container to set the current PageContext
	 * @param pageContext, the current PageContext
	 */
	public void setPageContext(final javax.servlet.jsp.PageContext pageContext) {
		this.pageContext = pageContext;
	}

	/** Method used by the JSP container to set the parent of the Tag
	 * @param parent, the parent Tag
	 */
	public void setParent(final javax.servlet.jsp.tagext.Tag parent) {
		this.parent = parent;
	}

	/** Method for retrieving the parent
	 * @return the parent
	 */
	public javax.servlet.jsp.tagext.Tag getParent() {
		return parent;
	}
	
	private void buildContent(JspWriter jspWriter) throws IOException{		
		jspWriter.write(graphProxy.getSvgBase());
	}

	public Object getGraphProxy() {
		return graphProxy;
	}

	public void setGraphProxy(Object graphProxy) {
		this.graphProxy = (GraphProxy)pageContext.findAttribute(graphProxy.toString());
	}

}
