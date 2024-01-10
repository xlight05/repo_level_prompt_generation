package com.googlecode.wicketbits.render.annot;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.validation.validator.NumberValidator;

import com.googlecode.wicketbits.reflection.AnnotationInfo;
import com.googlecode.wicketbits.reflection.MemberInfo;

public class AnnotationHandler extends AbstractAnnotationHandler {

	public void checkAnnotations(FormComponent component, ComponentTag tag,
			MemberInfo memberInfo) {

		AnnotationInfo ai = memberInfo.getAnnotation(Required.class);
		if (ai != null) {
			component.setRequired(true);
		}

		ai = memberInfo.getAnnotation(Minimum.class);
		if (ai != null) {
			component.add(NumberValidator.minimum(Integer.parseInt(ai
					.getStringValue("value"))));
		}

		ai = memberInfo.getAnnotation(Maximum.class);
		if (ai != null) {
			component.add(NumberValidator.maximum(Integer.parseInt(ai
					.getStringValue("value"))));
		}
	}
}
