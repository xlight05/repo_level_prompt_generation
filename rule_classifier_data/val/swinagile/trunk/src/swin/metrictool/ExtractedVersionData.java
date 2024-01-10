package swin.metrictool;

import java.util.HashMap;
import java.util.HashSet;

public class ExtractedVersionData
{
	private int sequenceNumber;

	private String versionName;

	private String versionNumber;

	private String versionDescription;

	private String versionReleaseDate;

	// Counts
	private int totalInstanceVariables;

	private int totalByteCode;

	private int totalPrivateMethodCount;

	private int totalMethodCount;

	private HashMap<Integer, Integer> fanInSummary, fanOutSummmary;

	private HashMap<String, ExtractedClassData> classList;

	private HashSet<String> packageList;

	public ExtractedVersionData()
	{
		this.classList = new HashMap<String, ExtractedClassData>();
		this.fanInSummary = new HashMap<Integer, Integer>();
		this.fanOutSummmary = new HashMap<Integer, Integer>();
		this.packageList = new HashSet<String>();
	}

//	public ExtractedVersionData(String versionName, String versionNumber,
//			String versionDescription, String versionReleaseDate)
//	{
//		this.versionName = versionName;
//		this.versionNumber = versionNumber;
//		this.versionDescription = versionDescription;
//		this.versionReleaseDate = versionReleaseDate;
//		this.classList = new HashMap<String, ExtractedClassData>();
//		this.fanInSummary = new HashMap<Integer, Integer>();
//		this.fanOutSummmary = new HashMap<Integer, Integer>();
//	}

	public void addToClassList(ExtractedClassData classToAdd)
	{
		this.totalInstanceVariables += classToAdd.getInstanceVariables();
		this.totalMethodCount += classToAdd.getMethodCount();
		this.totalPrivateMethodCount += classToAdd.getPrivateMethodCount();
		this.totalByteCode += classToAdd.getClassSize();
		this.incrementFanCount(this.fanOutSummmary, classToAdd.getFanOutCount());
		this.classList.put(classToAdd.getClassName(), classToAdd);
		this.updatePackageCount(classToAdd);
	}

	// verify the accuracy
	private void incrementFanCount(HashMap<Integer, Integer> theMap, Integer key)
	{
		if (theMap.containsKey(key))
		{
			Integer value = theMap.remove(key);
			int valueInt = value.intValue();
			valueInt++;
			theMap.put(key, new Integer(valueInt));
		}
		else
			theMap.put(key, new Integer(1));
	}

	private void updatePackageCount(ExtractedClassData classToAdd)
	{
		String packageName = classToAdd.getPackageName();
		if (!this.packageList.contains(packageName)) // TO DEBUG UNIT TESTS: System.out.println("Package: " +
		// packageName);
		this.packageList.add(packageName);
	}

	public int getSequenceNumber()
	{
		return this.sequenceNumber;
	}

	public void setSequenceNumber(int sequenceNumber)
	{
		this.sequenceNumber = sequenceNumber;
	}

	public String getVersionDescription()
	{
		return this.versionDescription;
	}

	public void setVersionDescription(String versionDescription)
	{
		this.versionDescription = versionDescription;
	}

	public String getVersionName()
	{
		return this.versionName;
	}

	public void setVersionName(String versionName)
	{
		this.versionName = versionName;
	}

	public String getVersionNumber()
	{
		return this.versionNumber;
	}

	public void setVersionNumber(String versionNumber)
	{
		this.versionNumber = versionNumber;
	}

	public String getVersionReleaseDate()
	{
		return this.versionReleaseDate;
	}

	public void setVersionReleaseDate(String versionReleaseDate)
	{
		this.versionReleaseDate = versionReleaseDate;
	}

	public HashMap<Integer, Integer> getFanInSummary()
	{
		return this.fanInSummary;
	}

	public HashMap<Integer, Integer> getFanOutSummmary()
	{
		return this.fanOutSummmary;
	}

	public int getTotalInstanceVariables()
	{
		return this.totalInstanceVariables;
	}

	public int getTotalMethodCount()
	{
		return this.totalMethodCount;
	}

	public int getTotalPrivateMethodCount()
	{
		return this.totalPrivateMethodCount;
	}

	public int getTotalByteCode()
	{
		return this.totalByteCode;
	}

	public int getTotalClasses()
	{
		return this.classList.size();
	}

	public int getTotalPackageCount()
	{
		return this.packageList.size();
	}

	public void calculateFanIn()
	{
		for (String className : this.classList.keySet())
		{
			for (String dependentClassName : this.classList.get(className).getFanOutList())
			{
				ExtractedClassData dependency = this.classList.get(dependentClassName);
				// All of the classes we are interested in exist inside 'classList'.
				// If the dependency does not exist then we ignore it...
				if (dependency != null) dependency.addFanInClass(className);
			}
			// Generate summary fan-in data for Ian
			this.incrementFanCount(this.fanInSummary, this.classList.get(className).getFanInCount());
		}
	}
}
