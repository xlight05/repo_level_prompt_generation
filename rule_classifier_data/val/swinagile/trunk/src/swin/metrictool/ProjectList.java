package swin.metrictool;

import java.util.Hashtable;

public class ProjectList
{
	private Hashtable<String, Project> projects = null;

	public ProjectList()
	{
		this.projects = new Hashtable<String, Project>();
	}

	public Hashtable<String, Project> getProjects()
	{
		return this.projects;
	}

	public void setProjects(Hashtable<String, Project> projects)
	{
		this.projects = projects;
	}
}
