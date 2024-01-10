package org.jsemantic.jcontenedor.parser;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class JContenedorNameSpaceHandler extends NamespaceHandlerSupport {

	public void init() {
		registerBeanDefinitionParser("contenedor",
				new JContenedorDefinitionParser());
	}

}
