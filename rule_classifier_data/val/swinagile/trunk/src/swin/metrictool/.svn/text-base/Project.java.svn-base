package swin.metrictool;

import java.util.Vector;

public class Project
{
	private String shortName = "";

	private String creationDate = null;

	private String description = "";

	private Vector<ExtractedVersionData> versions = null;

	private String directory = "";

	public Project(String _shortName, String _creationDate, String _description)
	{
		this.versions = new Vector<ExtractedVersionData>();
		this.shortName = _shortName;
		this.creationDate = _creationDate;
		this.description = _description;
	}

	public Project()
	{

	}

	public void setDetails(String _shortName, String _description, String _creationDate)
	{
		this.shortName = _shortName;
		this.description = _description;
		this.creationDate = _creationDate;
	}

	public String getDirectory()
	{
		return this.directory;
	}

	public void setDirectory(String directory)
	{
		this.directory = directory;
	}

	public String getCreationDate()
	{
		return this.creationDate;
	}

	public void setCreationDate(String creationDate)
	{
		this.creationDate = creationDate;
	}

	public String getDescription()
	{
		return this.description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getShortName()
	{
		return this.shortName;
	}

	public void setShortName(String shortName)
	{
		this.shortName = shortName;
	}

	public Vector<ExtractedVersionData> getVersions()
	{
		return this.versions;
	}

	public void setVersions(Vector<ExtractedVersionData> versions)
	{
		this.versions = versions;
	}
}
