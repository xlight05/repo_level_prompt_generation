package com.xebia.gclogviewer.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;

import junit.framework.Assert;
import nl.kvk.vt.monitoring.gc.TestUtils;

import org.junit.Test;

import com.shautvast.realtimegc.util.RegionReader;


public class RegionReaderTest {
	/**
	 * should skip the comment section
	 */
	@Test
	public void testSkipShortString() {
		String before = "before";
		String after = "after";
		String comment = "/*comment*/";
		String input = before + comment + after;
		String expectedOutput = before + after;
		testSkip(input, expectedOutput, false);
	}

	/**
	 * should skip the comment section
	 */
	@Test
	public void testSkipLongString() {
		String before = "beforebeforebeforebeforebeforebeforebeforebeforebeforebeforebeforebeforebeforebeforebefore";
		String after = "afterafterafterafterafterafterafterafterafterafterafterafterafterafterafterafterafter";
		String comment = "/*commentcommentcommentcommentcommentcommentcommentcommentcommentcommentcommentcommentcommentcommentcomment*/";
		String input = before + comment + after;
		String expectedOutput = before + after;
		testSkip(input, expectedOutput, false);
	}

	@Test
	public void test2Skips() {
		String before = "before";
		String after = "after";
		String comment = "/*comment*//*comment*/";
		String input = before + comment + after;
		String expectedOutput = before + after;
		testSkip(input, expectedOutput, false);
	}
	
	@Test
	public void testIncludeShortString() {
		String before = "before/*";
		String after = "*/after";
		String comment = "comment";
		String input = before + comment + after;
		String expectedOutput = comment;
		testInclude(input, expectedOutput, false);
	}

	/**
	 * should skip the comment section
	 */
	@Test
	public void testIncludeLongString() {
		String before = "beforebeforebeforebeforebeforebeforebeforebeforebeforebeforebeforebeforebeforebeforebefore/*";
		String after = "*/afterafterafterafterafterafterafterafterafterafterafterafterafterafterafterafterafter";
		String comment = "commentcommentcommentcommentcommentcommentcommentcommentcommentcommentcommentcommentcommentcommentcomment";
		String input = before + comment + after;
		String expectedOutput = comment;
		testInclude(input, expectedOutput, false);
	}

	@Test
	public void test2Includes() {
		String input = "before/*comment*//*comment*/after";
		String expectedOutput = "commentcomment";
		testInclude(input, expectedOutput, false);
	}
	
	@Test
	public void test2IncludesWithMarkers() {
		String input = "before/*comment*//*comment*/after";
		String expectedOutput = "/*comment*//*comment*/";
		testInclude(input, expectedOutput, true);
	}
	
	@Test
	public void testIncompleteMatch(){
		String input = "b/com*//*comment*/after";
		String expectedOutput = "/*comment*/";
		testInclude(input, expectedOutput, true);
	}
	
	private void testInclude(String input, String expectedOutput, boolean markers) {
		RegionReader reader = new RegionReader(
				new StringReader(input),500, "/*", "*/");
		reader.setSkipping(false);
		reader.setIncludingMarkers(markers);
		String outcome = TestUtils.readAll(reader);
		Assert.assertEquals("", expectedOutput, outcome);
	}

	private void testSkip(String input, String expectedOutput, boolean markers) {
		RegionReader reader = new RegionReader(
				new StringReader(input), "/*", "*/");
		reader.setSkipping(true);
		reader.setIncludingMarkers(markers);
		String outcome = TestUtils.readAll(reader);
		Assert.assertEquals("", expectedOutput, outcome);
	}
	
	
	

	@Test
	public void testGClogPart() throws IOException {
		String completeFileContents = TestUtils.readAll(new InputStreamReader(new FileInputStream(getClass()
				.getResource("/gc-part-output.log").getFile())));
		
		RegionReader regionReader = new RegionReader(
				new InputStreamReader(new FileInputStream(getClass()
						.getResource("/gc-part.log").getFile())),
				"<af",
				"</af>");
		regionReader.setSkipping(false);
		regionReader.setIncludingMarkers(true);
		
		String filteredFileContents = TestUtils.readAll(regionReader);
		Assert.assertEquals(completeFileContents, filteredFileContents);
	}
	
}
