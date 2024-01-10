package org.jsemantic.services.embedded.annotation;

import java.lang.annotation.Annotation;

import org.jsemantic.jservice.core.service.Service;
import org.jsemantic.jservice.core.service.exception.ServiceException;

import org.jsemantic.services.httpservice.HttpService;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

public class EmbeddedHttpServiceAnnotationProcessor {

	private EmbeddedHttpServiceAnnotationProcessor() {
	}

	public static void processHtppServiceAnnotations(Service httpService,
			Class<?> serviceClazz) {

		try {
			if (httpService != null) {
				boolean processed = processAnnotation(httpService, serviceClazz);
				if (!processed) {
					throw new ServiceException(
							"Annotation not processed: HttpService.");
				}
			}
		} catch (Throwable e) {
			throw new ServiceException(e);
		}
	}

	private static boolean processAnnotation(Service httpService,
			Class<?> service) {
		if (service.isAnnotationPresent(EmbeddedjContenedor.class)) {
			Annotation ann = ReflectionUtils.findMethod(service, "configure")
				.getAnnotation(EmbeddedjContenedorConfiguration.class);

			if (ann != null) {
				getInstance(httpService, ann);
				return true;
			}
		}
		return false;
	}

	private static Service getInstance(Service httpService, Annotation ann) {

		if (ann instanceof EmbeddedjContenedorConfiguration) {

			String root = ((EmbeddedjContenedorConfiguration) ann).root();
			String webApplication = ((EmbeddedjContenedorConfiguration) ann)
					.application();
			int port = ((EmbeddedjContenedorConfiguration) ann).port();

			if (StringUtils.hasText(root)) {
				((HttpService) httpService).setRootContext(root);
			}

			if (StringUtils.hasText(webApplication)) {
				((HttpService) httpService).setWebApplication(webApplication);
			}
			if (port > 0) {
				((HttpService) httpService).setPort(port);
			}

		}

		return httpService;
	}
}
