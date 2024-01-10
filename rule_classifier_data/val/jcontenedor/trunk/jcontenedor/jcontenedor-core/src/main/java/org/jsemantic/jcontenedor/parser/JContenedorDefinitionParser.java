package org.jsemantic.jcontenedor.parser;

import java.util.List;

import org.jsemantic.jcontenedor.impl.JContenedorImpl;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

public class JContenedorDefinitionParser extends AbstractBeanDefinitionParser {

	@SuppressWarnings("unchecked")
	@Override
	protected AbstractBeanDefinition parseInternal(Element element,
			ParserContext parserContext) {
		BeanDefinitionBuilder factory = BeanDefinitionBuilder
				.rootBeanDefinition(JContenedorFactoryBean.class);
		BeanDefinitionBuilder parent = parseComponent(element);

		factory.addPropertyValue("parent", parent.getBeanDefinition());

		List<Element> childElements =  DomUtils
				.getChildElementsByTagName(element, "layer");

		if (childElements != null && childElements.size() > 0) {
			parseChildComponents(childElements, factory);
		}

		return factory.getBeanDefinition();
	}

	private static BeanDefinitionBuilder parseComponent(
			@SuppressWarnings("unused") Element element) {
		BeanDefinitionBuilder layerManager = BeanDefinitionBuilder
				.rootBeanDefinition(JContenedorImpl.class);
		return layerManager;
	}

	@SuppressWarnings("unchecked")
	private static void parseChildComponents(List<Element> childElements,
			BeanDefinitionBuilder factory) {
		ManagedList children = new ManagedList(childElements.size());

		for (Element element : childElements) {
			children.add(element.getTextContent());
		}
		factory.addPropertyValue("layers", children);
	}
}
