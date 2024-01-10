package org.seamlets.cms.components.richeditor;

import java.io.InputStream;
import java.net.URL;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.hibernate.validator.NotEmpty;
import org.hibernate.validator.NotNull;
import org.seamlets.cms.annotations.PageContent;
import org.seamlets.cms.entities.pagecomponent.PageContentEntity;
import org.seamlets.cms.entities.pagecomponent.PageContentType;
import org.seamlets.cms.entities.pagecomponent.type.Page;
import org.seamlets.cms.entities.pagecomponent.type.Subpage;
import org.seamlets.cms.entities.pagecomponent.type.handler.PageHandler;
import org.seamlets.cms.entities.pagecomponent.type.handler.SubpageHandler;


@Entity
@Table(name = "HTML_CONTENT")
@PageContent(displayName = "Htmlcontent", category="simple", 
		editViewId="htmlContent.xhtml", 
		types = {PageContentType.PAGE, PageContentType.SUBPAGE},
		componentImage = "axialis.png")
@XmlRootElement
@XmlType(propOrder={"tagId", "styleClass", "style", "html"})
@XmlAccessorType(XmlAccessType.PROPERTY)
public class HtmlContent extends PageContentEntity implements Page, Subpage {

	@Column(name = "TAG_ID")
	private String		tagId;

	@Column(name = "STYLE_CLASS")
	private String		styleClass;

	@Column(name = "style")
	private String		style;

	@Lob
	@NotNull
	@NotEmpty
	@Column(name = "HTML")
	private String		html = "EMPTY RIGHT NOW";

	@XmlAttribute
	public String getTagId() {
		return tagId;
	}

	public void setTagId(String tagId) {
		this.tagId = tagId;
	}

	@XmlAttribute
	public String getStyleClass() {
		return styleClass;
	}

	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	@XmlAttribute
	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	@XmlElement(name="htmlValue")
	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}
	
	@Override
	public InputStream getComponentImage() {
		PageContent pageContentAnnotation = getClass().getAnnotation(PageContent.class);
		return getClass().getResourceAsStream(pageContentAnnotation.componentImage());
	}
	
	@Override
	public URL getComponentDetailPage() {
		PageContent pageContentAnnotation = getClass().getAnnotation(PageContent.class);
		return getClass().getResource(pageContentAnnotation.editViewId());
	}

	@XmlTransient
	public PageHandler getPageHandler() {
		// TODO Auto-generated method stub
		return null;
	}

	@XmlTransient
	public SubpageHandler getSubpageHandler() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
