package com.googlecode.wicketbits.render.annot;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.FormComponent;

import com.googlecode.wicketbits.reflection.AnnotationInfo;
import com.googlecode.wicketbits.reflection.MemberInfo;

public class AnnotationHandler extends AbstractAnnotationHandler {

	public void checkAnnotations(FormComponent component, ComponentTag tag,
			MemberInfo memberInfo) {

		AnnotationInfo ai = memberInfo.getAnnotation(Required.class);
		if (ai != null) {
			component.setRequired(true);
		}
	}
}
