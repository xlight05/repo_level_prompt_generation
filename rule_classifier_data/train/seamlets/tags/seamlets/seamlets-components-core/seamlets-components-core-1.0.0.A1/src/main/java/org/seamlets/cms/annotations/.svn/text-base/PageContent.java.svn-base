package org.seamlets.cms.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.seamlets.cms.entities.pagecomponent.PageContentType;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface PageContent {
	String displayName();
	String category();
	PageContentType[] types();
	String editViewId();
	String componentImage();
}
