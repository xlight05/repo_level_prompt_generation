package org.seamlets.tags.sl.listnavigation;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.apache.log4j.Logger;

public class UIListNavigationRenderer extends UIOutput {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(UIListNavigationRenderer.class);
	
	private boolean show = true;
	private String navTitle = "";
	private List<HashMap<String, String>> nodeList;	
	private String startlevel = "0";
	private boolean ulopen = false;
	
	@Override
	public void encodeBegin(FacesContext context) throws IOException {		
						
		try {
			setAttributes(context);			
			ResponseWriter writer = context.getResponseWriter();
			if(show){
				writer.startElement("div", this);	
				writer.writeAttribute("id", "listnavi", null);
				if(!navTitle.equals("")){
					writer.startElement("h2", this);
					writer.writeAttribute("class", "navi_title", null);
					writer.write(navTitle);
					writer.endElement("h2");
				}
				
				writer.startElement("ul", this);
				for(HashMap<String, String> valueMap : nodeList) {						
					if(valueMap.containsKey("value") && valueMap.containsKey("url") && valueMap.containsKey("css") && valueMap.containsKey("level")){
						if(!startlevel.equals(valueMap.get("level")) && !ulopen){
							writer.startElement("ul", this);
							startlevel = valueMap.get("level");
							ulopen = true;
						}
						
						if(!startlevel.equals(valueMap.get("level")) && ulopen){
							writer.endElement("ul");
							startlevel = valueMap.get("level");
							ulopen = false;
						}
						
						writer.startElement("li", this);
						writer.writeAttribute("class", valueMap.get("css"), null);
						writer.startElement("a", this);
						writer.writeAttribute("class", valueMap.get("css"), null);
						writer.writeAttribute("href", valueMap.get("url"), null);
						writer.write(valueMap.get("value"));
						writer.endElement("a");
						writer.endElement("li");
					} else {
						writer.startElement("li", this);
						writer.write("You have to put \"value\", \"url\", \"css\" and a ongoing Levelnumber \"level\" for Sublistlevels as keys in the Maps inside the List.");
						writer.endElement("li");
					}
				}
				writer.endElement("ul");
			}
		} catch (Exception e) {
			logger.error("Error in Navigation" + e);
		}	
	}
	
	@Override
	public void encodeEnd(FacesContext context) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		writer.endElement("div");
	}
	
	@SuppressWarnings("unchecked")
	private void setAttributes(FacesContext context){
		if(!this.getAttributes().containsKey("show")){
			if(this.bindings.containsKey("show")){
				this.show = (Boolean) this.getValueExpression("show").getValue(context.getELContext());
			} 
		} else {
			this.show = (Boolean) this.getAttributes().get("show");
		}
		if(!this.getAttributes().containsKey("startlevel")){
			if(this.bindings.containsKey("startlevel")){
				this.startlevel = (String) this.getValueExpression("startlevel").getValue(context.getELContext());
			} 
		} else {
			this.startlevel = (String) this.getAttributes().get("startlevel");
		}
		if(!this.getAttributes().containsKey("navititle")){
			if(this.bindings.containsKey("navititle")){
				this.navTitle = (String) this.getValueExpression("navititle").getValue(context.getELContext());
			} 
		} else {
			this.navTitle = (String) this.getAttributes().get("navititle");
		}
		if(!this.getAttributes().containsKey("nodelist")){
			if(this.bindings.containsKey("nodelist")){
				this.nodeList = (List<HashMap<String, String>>) this.getValueExpression("nodelist").getValue(context.getELContext());
			} 
		} else {
			this.nodeList = (List<HashMap<String, String>>) this.getAttributes().get("nodelist");
		}
	}
}

