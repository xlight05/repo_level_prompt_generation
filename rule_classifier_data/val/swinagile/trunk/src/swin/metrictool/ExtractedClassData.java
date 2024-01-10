package swin.metrictool;

import java.util.Collections;
import java.util.Vector;

public class ExtractedClassData
{
	// private int fanInCount;
	// private int fanOutCount;
	private int instanceVariables;

	private int classSize;

	private int privateMethodCount;

	private int methodCount;

	private String className;

	private Vector<String> fanInList;

	private Vector<String> fanOutList;

	public ExtractedClassData(int instanceVariables, int classSize, int privateMethodCount,
			int methodCount, String className, Vector<String> fanOutList)
	{
		super();
		this.instanceVariables = instanceVariables;
		this.classSize = classSize;
		this.privateMethodCount = privateMethodCount;
		this.methodCount = methodCount;
		this.className = className;
		this.fanOutList = fanOutList;
		this.fanInList = new Vector<String>();
	}

//	public ExtractedClassData(int instanceVariables, int classSize, int privateMethodCount,
//			int methodCount, String className, Vector<String> fanInList,
//			Vector<String> fanOutList)
//	{
//		this(instanceVariables, classSize, privateMethodCount, methodCount, className,
//				fanOutList);
//		this.fanInList = fanInList;
//	}

	public String getClassName()
	{
		return this.className;
	}

	public void setClassName(String className)
	{
		this.className = className;
	}

	public int getClassSize()
	{
		return this.classSize;
	}

//	public void setClassSize(int classSize)
//	{
//		this.classSize = classSize;
//	}

	public int getFanInCount()
	{
		return this.fanInList.size();
	}

	public int getFanOutCount()
	{
		return this.fanOutList.size();
	}

	public int getInstanceVariables()
	{
		return this.instanceVariables;
	}

//	public void setInstanceVariables(int instanceVariables)
//	{
//		this.instanceVariables = instanceVariables;
//	}

	public int getMethodCount()
	{
		return this.methodCount;
	}

//	public void setMethodCount(int methodCount)
//	{
//		this.methodCount = methodCount;
//	}

	public int getPrivateMethodCount()
	{
		return this.privateMethodCount;
	}

//	public void setPrivateMethodCount(int privateMethodCount)
//	{
//		this.privateMethodCount = privateMethodCount;
//	}

//	public ExtractedClassData(String className)
//	{
//		this.className = className;
//	}

//	public Vector<String> getFanInList()
//	{
//		Collections.sort(fanInList);
//		return fanInList;
//	}

//	public void setFanInList(Vector<String> fanInList)
//	{
//		this.fanInList = fanInList;
//	}

	public Vector<String> getFanOutList()
	{
		Collections.sort(fanOutList);
		return fanOutList;
	}

//	public void setFanOutList(Vector<String> fanOutList)
//	{
//		this.fanOutList = fanOutList;
//	}

	public String getPackageName()
	{
		final int notfound = -1;
		int index = this.className.lastIndexOf("/");
		if (index == notfound) return "default";
		return this.className.substring(0, index);
	}

	public void addFanInClass(String fanInClassName)
	{
		this.fanInList.add(fanInClassName);
	}
}
