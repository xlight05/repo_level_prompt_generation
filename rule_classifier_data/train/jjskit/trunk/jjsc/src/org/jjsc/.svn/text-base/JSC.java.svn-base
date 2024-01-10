package org.jjsc;

import java.io.File;
import java.io.FileWriter;

import org.jjsc.compiler.javascript.JavaScriptBuilder;
import org.jjsc.utils.IO;


/**
 * Entry point for javascript compiler. Provides really powerful compilation tool
 * for javascript, that increases code quality and performance in times.
 * 
 * @author alex.bereznevatiy@gmail.com
 */
public class JSC {
	public static void main(String[] args) {
		if (args.length != 2 || args[0] == "--help") {
			printUsage();
			return;
		}
		FileWriter out = null;
		try {
			out = new FileWriter(args[1]);
			new JavaScriptBuilder(new File(args[0])).compile(out);
		} catch(Throwable th) {
			th.printStackTrace(System.err);
		} finally {
			IO.close(out);
		}
	}

	private static void printUsage() {
		System.out.println("Usage:");
		System.out.println("  java -jar [TODO] [entry point] [target]");
	}
}
