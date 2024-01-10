package org.jsemantic.services.examples.common;

import org.jsemantic.services.embedded.annotation.EmbeddedjContenedor;
import org.jsemantic.services.embedded.annotation.EmbeddedjContenedorConfiguration;
import org.jsemantic.services.embedded.impl.AbstractEmbeddedJContenedorService;

@EmbeddedjContenedor
public class WebEmbeddedContenedorService extends
		AbstractEmbeddedJContenedorService {

	@EmbeddedjContenedorConfiguration(port = 9006, root = "/test", application = "src/main/webapp/")
	public void configure() {
		//
	}

}
