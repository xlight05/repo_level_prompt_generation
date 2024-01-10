package swin.metrictool.intentions;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import junit.framework.JUnit4TestAdapter;

import org.junit.Test;

import swin.metrictool.ClassMetricExtractor;

public class ClassMetricExtractorIntention
{
	public static junit.framework.Test suite()
	{
		return new JUnit4TestAdapter(ClassMetricExtractorIntention.class);
	}

	private final static String jarfile = "lib/xpp3_min-1.1.3.4.O.jar";
	private final static String className = "org/xmlpull/mxp1/MXParser.class";
	private final static String metrics = "Super Classes :0"
		+ "Interfaces :1"
		+ "Methods :78"
		+ "Local Variables276"
		+ "Private Method Count :3"
		+ "Fan Out Count :16"
		+ "Class Type :C"
		+ "Class Name :org/xmlpull/mxp1/MXParser"
		+ "Supe Class :java/lang/Object"
		+ "Dependencies:"
		+ "java/io/Reader"
		+ "java/io/InputStreamReader"
		+ "java/lang/Object"
		+ "java/lang/System"
		+ "java/lang/IllegalArgumentException"
		+ "java/lang/Boolean"
		+ "org/xmlpull/v1/XmlPullParserException"
		+ "java/io/InputStream"
		+ "java/io/EOFException"
		+ "java/lang/Runtime"
		+ "java/io/IOException"
		+ "java/lang/String"
		+ "java/lang/Integer"
		+ "org/xmlpull/mxp1/MXParser"
		+ "java/lang/StringBuffer"
		+ "java/lang/IndexOutOfBoundsException";

	@Test
	public void specifyThatWeCanExtractMetricsFromClassFiles() throws Exception
	{
		InputStream classStream = extractMXParserClassFileStream();
		ClassMetricExtractor cme = new ClassMetricExtractor(classStream);
		String expected = metrics.replaceAll("\\s+", ""); // remove whitespace.;
		String actual = cme.toString().replaceAll("\\s+", ""); // remove whitespace.;
		assertEquals("Class metrics did not match.", expected, actual);
	}

	private InputStream extractMXParserClassFileStream() throws Exception
	{
		ZipFile zipArchive = new ZipFile(jarfile);
        Enumeration< ? extends ZipEntry> zipFiles = zipArchive.entries();
        while (zipFiles.hasMoreElements())
        {
            ZipEntry zipFile = zipFiles.nextElement();
            String name = zipFile.getName();
            if (name.equals(className)) {
            	return zipArchive.getInputStream(zipFile);
            }
        }
        throw new Exception("Failed to locate class '" + className + "' inside sample jar file: " + jarfile);
	}
}
