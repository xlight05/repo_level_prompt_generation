package org.jsemantic.services.embedded;

import org.jsemantic.services.embedded.annotation.EmbeddedjContenedor;
import org.jsemantic.services.embedded.annotation.EmbeddedjContenedorConfiguration;
import org.jsemantic.services.embedded.impl.AbstractEmbeddedJContenedorService;

@EmbeddedjContenedor
public class WebEmbeddedJContenedorService extends
		AbstractEmbeddedJContenedorService {

	@EmbeddedjContenedorConfiguration(port = 9005, root = "/test", application = "src/main/resources/webapp/spring")
	public void configure() {
		// TODO Auto-generated method stub
	}

}
