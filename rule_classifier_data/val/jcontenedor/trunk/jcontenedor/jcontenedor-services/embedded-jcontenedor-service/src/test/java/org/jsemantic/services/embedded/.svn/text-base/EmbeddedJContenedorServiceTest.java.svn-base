package org.jsemantic.services.embedded;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.jsemantic.services.httpservice.client.HttpTestClient;

import junit.framework.TestCase;

public class EmbeddedJContenedorServiceTest extends TestCase {

	private WebEmbeddedJContenedorService embeddedContenedorService = null;

	private HttpTestClient httpClient = null;

	protected void setUp() throws Exception {
		super.setUp();
		embeddedContenedorService = new WebEmbeddedJContenedorService();
		this.embeddedContenedorService.start();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		this.embeddedContenedorService.stop();
		this.embeddedContenedorService.dispose();
	}

	public void test() {

		httpClient = (HttpTestClient) embeddedContenedorService
				.getComponent("httpClient");
		super.assertNotNull(httpClient);

		HttpResponse response = null;
		try {
			response = httpClient
					.executeHttpGetRequest("http://localhost:9005/test");
			StatusLine status = response.getStatusLine();
			super.assertEquals(200, status.getStatusCode());
		} catch (Throwable e) {
			super.fail(e.getMessage());
		} finally {
			httpClient.consumeContent(response);
		}
	}
}
