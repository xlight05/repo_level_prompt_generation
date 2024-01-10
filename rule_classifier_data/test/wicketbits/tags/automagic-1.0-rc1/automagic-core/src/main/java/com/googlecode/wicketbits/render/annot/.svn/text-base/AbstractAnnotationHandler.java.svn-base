package com.googlecode.wicketbits.render.annot;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.FormComponent;

import com.googlecode.wicketbits.reflection.MemberInfo;

public abstract class AbstractAnnotationHandler {

	private static AbstractAnnotationHandler currentHandler = null;

	public abstract void checkAnnotations(FormComponent component,
			ComponentTag tag, MemberInfo memberInfo);

	public static AbstractAnnotationHandler getCurrentHandler() {
		return currentHandler;
	}

	public static void setCurrentHandler(
			AbstractAnnotationHandler currentHandler) {
		AbstractAnnotationHandler.currentHandler = currentHandler;
	}
}
