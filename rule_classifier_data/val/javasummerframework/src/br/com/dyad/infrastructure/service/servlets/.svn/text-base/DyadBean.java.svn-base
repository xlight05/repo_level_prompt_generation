package br.com.dyad.infrastructure.service.servlets;

import java.util.ArrayList;
import java.util.Iterator;

public class DyadBean {
	private String completeBeanName;
	private String beanName;		
	private Long classId;
	private Long licenseId;
	private ArrayList<DyadBean> children;
	
	public DyadBean(){
		
	}	

	public String getCompleteBeanName() {
		return completeBeanName;
	}

	public void setCompleteBeanName(String completeBeanName) {
		this.completeBeanName = completeBeanName;
	}

	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public Long getClassId() {
		return classId;
	}

	public void setClassId(Long classId) {
		this.classId = classId;
	}

	public Long getLicenseId() {
		return licenseId;
	}

	public void setLicenseId(Long licenseId) {
		this.licenseId = licenseId;
	}

	public ArrayList<DyadBean> getChildren() {
		if ( this.children == null ){
			this.children = new ArrayList<DyadBean>();
		}
		return children;
	}

	public void setChildren(ArrayList<DyadBean> children) {		
		this.children = children;
	}
	
	@Override
	public boolean equals(Object obj) {
		DyadBean nav = (DyadBean)obj;
		
		return nav.getBeanName().equals(this.getBeanName()) &&
		       nav.getCompleteBeanName().equals(this.getCompleteBeanName());
	}
	
	public String toXmlString(){
		String content = "<bean id=\""+ classId +"\">";		
		content += "<beanName><![CDATA[" + (beanName != null ? beanName : "") + "]]></beanName>";
		content += "<completeBeanName><![CDATA[" + (completeBeanName != null ? completeBeanName : "") + "]]></completeBeanName>";
		content += "<id>" + (classId != null ? classId : "") + "</id>";
		content += "<licenseId><![CDATA[" + (licenseId != null ? licenseId : "") + "]]></licenseId>";
		if ( children != null ){
			content += "<children>";
			for (Iterator iterator = children.iterator(); iterator.hasNext();) {
				DyadBean bean = (DyadBean) iterator.next();
				content += (bean != null ? bean.toXmlString() : "");
			}
			content += "</children>";
		}
		content += "</bean>";
		return content;
	}

}
