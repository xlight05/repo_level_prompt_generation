package swin.metrictool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.swing.event.EventListenerList;

public class ExtractVersionMetrics
{
	private EventListenerList eventListeners = new EventListenerList();

	private ExtractedVersionData theVersion;

	public ExtractVersionMetrics()
	{
		this.theVersion = new ExtractedVersionData();
	}

	// /**
	// * @param args
	// */
	// public static void main(String[] args)
	// {
	// if (args.length == 0) System.out.println("Provede class/jar files paths");
	// try
	// {
	// ExtractVersionMetrics evm = new ExtractVersionMetrics();
	// System.out.println(evm.getMetrics(args));
	// }
	// catch (ExtractVersionException e)
	// {
	// System.out.println(e);
	// }
	// }
	// public static void writeExtractedVersionDataXML(
	// ExtractedVersionData theExtractedVersionData)
	// throws ExtractVersionException {
	// XStream xstream = new XStream(new DomDriver()); // does not require XPP3
	// // library
	// xstream.alias("VersionData", ExtractedVersionData.class);
	// xstream.alias("ClassData", ExtractedClassData.class);
	// PrintWriter output;
	// try {
	// output = new PrintWriter(new BufferedWriter(new FileWriter(
	// (theExtractedVersionData.getSequenceNumber() + ".xml"))));
	// output.write(xstream.toXML(theExtractedVersionData));
	// output.close();
	// } catch (IOException e) {
	// throw new ExtractVersionException(
	// "ERR: couldn't write to xml version data file.");
	// }
	// }
	// public static ExtractedVersionData readExtractedVersionDataXML(
	// String xmlFilePath) throws ExtractVersionException {
	// XStream xstream = new XStream(new DomDriver()); // does not require XPP3
	// // library
	// xstream.alias("VersionData", ExtractedVersionData.class);
	// xstream.alias("ClassData", ExtractedClassData.class);
	// try {
	// return (ExtractedVersionData) xstream.fromXML(new BufferedReader(
	// new FileReader(xmlFilePath)));
	// } catch (FileNotFoundException e) {
	// throw new ExtractVersionException(
	// "ERR: couldn't read from xml version data file :"
	// + xmlFilePath);
	// }
	// }
	//
	// public static void writeVersionConfigXML(VersionConfig
	// theVersionConfigData)
	// throws ExtractVersionException {
	// XStream xstream = new XStream(new DomDriver()); // does not require XPP3
	// // library
	// xstream.alias("VersionConfig", VersionConfig.class);
	// xstream.alias("sourceFilePath", java.lang.String.class);
	// PrintWriter output;
	// try {
	// output = new PrintWriter(new BufferedWriter(new FileWriter(
	// (theVersionConfigData.getSequenceNumber() + ".xml"))));
	// output.write(xstream.toXML(theVersionConfigData));
	// } catch (IOException e) {
	// throw new ExtractVersionException(
	// "ERR: couldn't write to xml version conffiguration.");
	// }
	// }
	//
	// public static VersionConfig readVersionConfigXML(String xmlFilePath)
	// throws ExtractVersionException {
	// XStream xstream = new XStream(new DomDriver()); // does not require XPP3
	// // library
	// xstream.alias("VersionConfig", VersionConfig.class);
	// xstream.alias("sourceFilePath", java.lang.String.class);
	// try {
	// return (VersionConfig) xstream.fromXML(new BufferedReader(
	// new FileReader(xmlFilePath)));
	// } catch (FileNotFoundException e) {
	// throw new ExtractVersionException("ERR: file not found: "
	// + xmlFilePath);
	// }
	// }

	/**
	 * Filenames that do not end with '.class' or '.jar' are silently ignored.
	 * @param files
	 * @return
	 * @throws ExtractVersionException
	 */
	public ExtractedVersionData getMetrics(String[] files) throws ExtractVersionException
	{
		// ExtractVersionMetrics metrics = new ExtractVersionMetrics();
		if (files == null) return this.theVersion;
		for (String file : files)
		{
			if (file.endsWith(".class"))
			{
				this.processClassPath(file);
			}
			else if (file.endsWith(".jar"))
			{
				this.processJar(file);
			}
		}
		this.theVersion.calculateFanIn();
		return this.theVersion;
	}

	/**
	 * Takes class file path and checks it and Pass it to ProcessClass method as a File object
	 * @param filePath
	 * @throws ExtractVersionException if the .class file cannot be processed
	 */
	private void processClassPath(String filePath) throws ExtractVersionException
	{
		if (filePath.length() == 0)
			throw new ExtractVersionException("ERR: No arguments provided");
		File file = new File(filePath);
		if (!file.exists()) throw new ExtractVersionException("ERR: Input file not found.");
		if (!file.canRead())
			throw new ExtractVersionException("ERR: Unable to access input file.");

		processClass(file);
	}

	/**
	 * Processes .class files and adds generated metrics data to the ExtractVersionData 
	 * @param file
	 * @throws ExtractVersionException
	 */
	private void processClass(File file) throws ExtractVersionException
	{
		ClassMetricExtractor cme;
		try
		{
			cme = new ClassMetricExtractor(new FileInputStream(file));
		}
		catch (FileNotFoundException e)
		{
			throw new ExtractVersionException("ERR: Input file not found.");
		}
		catch (IOException e)
		{
			throw new ExtractVersionException("ERR: reading file :" + file.getName());
		}
		Vector<String> tempFanOutList = new Vector<String>();
		for (String fanOut : cme.dependencies)
			tempFanOutList.add(fanOut);

		ExtractedClassData tempClassData = new ExtractedClassData(cme.localVarCount,
				cme.lineOfByteCode, cme.privateMethodCount, cme.methodCount, cme.className,
				tempFanOutList);
		theVersion.addToClassList(tempClassData);

		notifyEventListeners(new MetricsExtractEvent(tempClassData));
	}

	/**
	 * Used to process class file byte[] array data extracted from Jar files.
	 * @param data contains the class file byte[] array data
	 * @throws ExtractVersionException if the class byte[] array cannot be processed
	 */
	private void processByteArray(byte[] data) throws ExtractVersionException
	{
		if (data.length == 0) throw new ExtractVersionException("ERR: data lenth is zero");
		ClassMetricExtractor cme;
		try
		{
			cme = new ClassMetricExtractor(data);
		}
		catch (IOException e)
		{
			throw new ExtractVersionException("ERR: reading data");
		}
		Vector<String> tempFanOutList = new Vector<String>();
		for (String fanOut : cme.dependencies)
			tempFanOutList.add(fanOut);
		ExtractedClassData tempClassData = new ExtractedClassData(cme.localVarCount,
				cme.lineOfByteCode, cme.privateMethodCount, cme.methodCount, cme.className,
				tempFanOutList);

		theVersion.addToClassList(tempClassData);

		notifyEventListeners(new MetricsExtractEvent(tempClassData));
	}

	private void processJar(String file) throws ExtractVersionException
	{
		Hashtable<String, byte[]> classes = extractClassFilesFromJar(file);
		Enumeration<String> e = classes.keys();
		while (e.hasMoreElements())
			processByteArray(classes.get(e.nextElement()));
	}

	private Hashtable<String, byte[]> extractClassFilesFromJar(String jarFilePath)
		throws ExtractVersionException
	{
		Hashtable<String, byte[]> theClassFiles = new Hashtable<String, byte[]>();
		// extract resources and put them into the hashtable.
		try
		{
			ZipInputStream zis = new ZipInputStream(new FileInputStream(jarFilePath));
			ZipEntry ze = null;
			while ((ze = zis.getNextEntry()) != null)
			{
				if (ze.isDirectory()) continue;
				// add to internal resource hashtable
				if (ze.getName().endsWith(".class")) theClassFiles.put(ze.getName(), readBytesaddToHashTable(ze, zis));

			}
		}
		catch (FileNotFoundException e)
		{
			throw new ExtractVersionException("ERR: countn't find the file :" + jarFilePath);
		}
		catch (IOException e)
		{
			throw new ExtractVersionException("ERR: failed processing file :" + jarFilePath);
		}
		return theClassFiles;
	}
	
	private byte[] readBytesaddToHashTable(ZipEntry ze,ZipInputStream zis) throws IOException{
		int size = (int) ze.getSize(),rb = 0,chunk = 0;
		// -1 means unknown size.
		byte[] b = new byte[(int) size];
		
		while (((int) size - rb) > 0)
		{
			chunk = zis.read(b, rb, (int) size - rb);
			if (chunk == -1) break;
			rb += chunk;
		}
		return b;
	}

	synchronized public void addEventListener(MetricsExtractionEventListener listener)
	{
		eventListeners.add(MetricsExtractionEventListener.class, listener);
	}

	// This methods allows classes to unregister for MyEvents
	synchronized public void removeEventListener(MetricsExtractionEventListener listener)
	{
		eventListeners.remove(MetricsExtractionEventListener.class, listener);
	}

	// This private class is used to fire events
	synchronized private void notifyEventListeners(MetricsExtractEvent event)
	{
		MetricsExtractionEventListener[] listeners = eventListeners.getListeners(MetricsExtractionEventListener.class);
		for (MetricsExtractionEventListener listener : listeners)
			listener.metricsExtractionEventOccured(event);
	}
}
