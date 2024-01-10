package org.seamlets.cms.entities.template;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.seamlets.cms.annotations.Template;

@Entity
@Table(name = "FILE_TEMPLATE")
@Template(dispalyName = "Filetemplate", viewId = "/templates/edit/fileTemplate.xhtml")
@XmlRootElement
@XmlType(propOrder = {"value"})
@XmlAccessorType(XmlAccessType.PROPERTY)
public class FileTemplate extends TemplateEntity {

	private String	value;

	@XmlElement
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
