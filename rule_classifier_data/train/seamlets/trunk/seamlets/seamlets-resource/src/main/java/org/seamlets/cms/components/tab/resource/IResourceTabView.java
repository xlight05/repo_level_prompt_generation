package org.seamlets.cms.components.tab.resource;

import java.io.InputStream;
import java.io.Serializable;

import org.richfaces.component.state.TreeStateAdvisor;
import org.richfaces.event.NodeExpandedListener;
import org.richfaces.event.NodeSelectedListener;
import org.seamlets.resource.entity.Directory;
import org.seamlets.resource.entity.File;

public interface IResourceTabView extends Serializable, TreeStateAdvisor, NodeSelectedListener, NodeExpandedListener {

	public void create();

	public void remove();

	public Directory[] getRoots();

	public boolean isRoot();

	public InputStream getRootImage();

	public InputStream getDirectoryImage();

	public InputStream getDeleteImage();

	public InputStream getDeleteImageDis();

	public Directory getSelectedDirectory();

	public File getSelectedFile();

	public void showDirectoryDialog();

	public void deleteItem();

	public void hideDirectoryDialog();

	public void addDirectory();

	public boolean isShowDialog();

	public String getDirectoryName();

	public void setDirectoryName(String directoryName);
}