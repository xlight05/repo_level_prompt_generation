package org.jsemantic.jcontenedor.layer.web;

import javax.servlet.ServletContext;

import org.jsemantic.jcontenedor.layer.Layer;
import org.jsemantic.jcontenedor.layer.standalone.StandAloneLayer;
import org.jsemantic.jservice.core.context.Context;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

public class WebLayer extends StandAloneLayer implements Layer {

	private Context contenedorContext = null;

	public WebLayer(ConfigurableApplicationContext appContext) {
		super(appContext);
	}

	public WebLayer(String id, String file, Context contenedorContext, int order) {
		super(id, file, order);
		this.contenedorContext = contenedorContext;
	}

	public void setContext(Context context) {
		this.contenedorContext = context;
	}
	@Override
	protected void postConstruct() {
		this.applicationContext = new XmlWebApplicationContext();
		((XmlWebApplicationContext) this.applicationContext).setId(file);
		((XmlWebApplicationContext) this.applicationContext).setConfigLocation(file);
		((XmlWebApplicationContext) this.applicationContext)
				.setServletContext((ServletContext) this.contenedorContext
						.getExternal());
	}
	@Override
	public void release() {
		this.applicationContext.close();
	}

	public void refresh(String file) {
		this.file = file;
		((XmlWebApplicationContext) this.applicationContext).setConfigLocation(this.file);
		this.refresh();
	}
}
