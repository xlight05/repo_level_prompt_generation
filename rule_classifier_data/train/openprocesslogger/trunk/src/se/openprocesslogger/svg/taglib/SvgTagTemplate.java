package se.openprocesslogger.svg.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

public abstract class SvgTagTemplate extends TagSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6632001326004095589L;
	private PageContext pageContext;
	private Tag parent;
	
	protected abstract void buildContent(JspWriter out) throws IOException;
	
	/**
	 * Method called at start of Tag
	 * @return either a EVAL_BODY_INCLUDE or a SKIP_BODY
	 */
	public int doStartTag() throws javax.servlet.jsp.JspTagException {
		try {
			this.buildContent(pageContext.getOut());
		} catch (IOException e) {
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

}
