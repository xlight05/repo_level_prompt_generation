package edu.spbsu.eshop.business.pdf;

import java.io.IOException;
import java.util.Date;

import javax.faces.FactoryFinder;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextFactory;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

@SuppressWarnings("serial")
public class PdfServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {
	// TODO: Investigate!
	/*
	 * FacesContext facesContext = getFacesContext(request, response); Long
	 * orderId = (Long) facesContext.getExternalContext().getSessionMap()
	 * .get("orderId");
	 */
	Document document = new Document();
	response.setContentType("application/pdf");
	try {
	    PdfWriter.getInstance(document, response.getOutputStream());
	    document.open();
	    // if (orderId != null) {
	    document.add(new Paragraph(
		    "Order confirmation data: your Order id is: test"));
	    // + orderId));
	    // }
	    document.add(new Paragraph(new Date().toString()));
	} catch (DocumentException e) {
	    // TODO: log it!
	    e.printStackTrace();
	} finally {
	    document.close();
	}
    }

    @Override
    protected void doPost(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {
	doGet(request, response);
    }

    /**
     * 
     * You need an inner class to be able to call
     * FacesContext.setCurrentInstance since it's a protected method.
     * 
     * @version $Revision: 1.2 $
     */
    private abstract static class AbstractInnerFacesContext extends
	    FacesContext {
	protected static void setFacesContextAsCurrentInstance(
		final FacesContext facesContext) {
	    FacesContext.setCurrentInstance(facesContext);
	}
    }

    @SuppressWarnings("unused")
    private FacesContext getFacesContext(final ServletRequest request,
	    final ServletResponse response) {

	FacesContext facesContext = FacesContext.getCurrentInstance();
	if (facesContext != null) {
	    return facesContext;
	}

	FacesContextFactory contextFactory = (FacesContextFactory) FactoryFinder
		.getFactory(FactoryFinder.FACES_CONTEXT_FACTORY);
	LifecycleFactory lifecycleFactory = (LifecycleFactory) FactoryFinder
		.getFactory(FactoryFinder.LIFECYCLE_FACTORY);
	Lifecycle lifecycle = lifecycleFactory
		.getLifecycle(LifecycleFactory.DEFAULT_LIFECYCLE);

	facesContext = contextFactory.getFacesContext(this.getServletContext(),
		request, response, lifecycle);

	return facesContext;
    }

}
