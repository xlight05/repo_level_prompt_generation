package org.seamlets.cms.entities.pagecomponent;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.jboss.seam.annotations.ReadOnly;
import org.seamlets.cms.annotations.PageContent;


@ReadOnly
public class CMSComponentHelper {

	private final PageContent pageContent;
	private final Class<? extends PageContentEntity> clazz;
	private final List<PageContentType>	types;
	private String	componentImage;
	
	public CMSComponentHelper(PageContent pageContent, Class<? extends PageContentEntity> clazz) {
		this.pageContent = pageContent;
		this.clazz = clazz;
		this.types = Arrays.asList(pageContent.types());
		this.componentImage = pageContent.componentImage();
	}
	
	public String getDisplayName() {
		return pageContent.displayName();
	}
	
	public String getCategory() {
		return pageContent.category();
	}
	
	public PageContentType[] getTypes() {
		return pageContent.types();
	}
	
	public List<PageContentType> getTypesList() {
		return Collections.unmodifiableList(types);
	}

	public boolean isCONTAINER() {
		return types.contains(PageContentType.CONTAINER);
	}
	
	public boolean isPAGE() {
		return types.contains(PageContentType.PAGE);
	}
	
	public boolean isSUBPAGE() {
		return types.contains(PageContentType.SUBPAGE);
	}
	
	public boolean isLIST() {
		return types.contains(PageContentType.LIST);
	}
	
	public boolean isLIST_ITEM() {
		return types.contains(PageContentType.LIST_ITEM);
	}
	
	public String getEditViewId() {
		return pageContent.editViewId();
	}
	
	public PageContentEntity createNewInstance() throws InstantiationException, IllegalAccessException {
		PageContentEntity pageContentEntity = clazz.newInstance();
		return pageContentEntity;
	}
	
	public InputStream getComponentImage() {
		return clazz.getResourceAsStream(componentImage);
	}

}
