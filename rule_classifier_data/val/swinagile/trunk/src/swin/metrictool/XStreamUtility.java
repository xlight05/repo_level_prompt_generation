package swin.metrictool;

import com.thoughtworks.xstream.XStream;

public class XStreamUtility
{
	/*public static String getXml(Project project)
	{
		XStream xstream = new XStream();
		xstream.alias("project", Project.class);
		xstream.alias("versiondata", ExtractedVersionData.class);
		return xstream.toXML(project);
	}

	public static Project getProject(String xml)
	{
		XStream xstream = new XStream();
		xstream.alias("project", Project.class);
		xstream.alias("versiondata", ExtractedVersionData.class);
		return (Project) xstream.fromXML(xml);
	}*/

	public static String getXml(ProjectList projectList)
	{
		XStream xstream = new XStream();
		xstream.alias("projectlist", ProjectList.class);
		xstream.alias("project", Project.class);
		xstream.alias("versiondata", ExtractedVersionData.class);
		return xstream.toXML(projectList);
	}

	public static ProjectList getProjectList(String xml)
	{
		XStream xstream = new XStream();
		xstream.alias("projectlist", ProjectList.class);
		xstream.alias("project", Project.class);
		xstream.alias("versiondata", ExtractedVersionData.class);
		return (ProjectList) xstream.fromXML(xml);
	}
}
