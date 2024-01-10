package dovetaildb;

import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import joptsimple.OptionException;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.NetworkListener;
import org.glassfish.grizzly.servlet.ServletRegistration;
import org.glassfish.grizzly.servlet.WebappContext;

import dovetaildb.dbrepository.FileDbRepository;
import dovetaildb.servlet.DovetaildbServlet;
import dovetaildb.util.Base64;
import dovetaildb.util.Util;

public class Main {

	private static void error(String s) {
		System.err.println(s);
		System.exit(1);
	}
	
	public static void main(String[] argv) throws Exception {
		if (argv.length > 0) {
			String cmd = argv[0];
			String[] args = Arrays.copyOfRange(argv, 1, argv.length);
			if (cmd.equals("standalone")) {
				HttpServer ws = createWebServer(args);
				ws.start();
				Thread.currentThread().join(0);
				return;
			} else if (cmd.equals("uploadcode")) {
				uploadCode(args);
				return;
			}
		}
		System.err.println("Please specify either 'standalone' or 'uploadcode' as the first argument");
		System.exit(2);
	}

	public static void setLogLevel(Level level) {
		Util.logger.setLevel(level);
	    for (Handler handler : Util.logger.getHandlers()) {
		    handler.setLevel(level);
	    }
	}

	public static void uploadCode(String[] argv) throws IOException {
		OptionParser parser = new OptionParser();
		parser.accepts("db","Database name in DovetailDB").withRequiredArg();
		parser.accepts("accesskey","Accesskey").withOptionalArg();
		parser.accepts("url","DovetailDB server URL").withRequiredArg().defaultsTo("http://127.0.0.1:19156");
		parser.accepts("log","Log level (WARNING,INFO,FINER)").withRequiredArg().defaultsTo("info");
		parser.accepts("help","Help");
		OptionSet opts = Main.parseWithHelp(argv, parser, true);
		
		List<String> codefiles = opts.nonOptionArguments();
		
		UploadCode uploader = new UploadCode((String)opts.valueOf("url"));
		Level level = Level.parse((String) opts.valueOf("log"));
		setLogLevel(level);
		if (opts.has("accesskey")) {
			uploader.setAccessKey((String)opts.valueOf("accesskey"));
		}
		String db = (String)opts.valueOf("db");
		if (db == null) {
			error("A database name must be supplied");
		}
		uploader.setCodeFromLocal(db, codefiles);
	}
	
	public static HttpServer createWebServer(String[] argv) throws IOException {
		OptionParser parser = new OptionParser();
		parser.accepts("createmode","Create mode (OPEN,RAND_KEY)").withRequiredArg().defaultsTo("RAND_KEY");
		parser.accepts("port","HTTP listen port").withRequiredArg().ofType(Integer.class).defaultsTo(19156);
		parser.accepts("interface","HTTP listen interface").withRequiredArg().defaultsTo("0.0.0.0");
		parser.accepts("meta","Metadata file").withRequiredArg().ofType(File.class).defaultsTo(new File("metadata.ser"));
		parser.accepts("log","Log level (WARNING,INFO,FINER)").withRequiredArg();
		parser.accepts("help","Help");
		OptionSet opts = Main.parseWithHelp(argv, parser, false);
		
		int port = ((Integer)opts.valueOf("port")).intValue();
		File headerFile = (File)opts.valueOf("meta");
		Level logLevel = Level.INFO;
		if (opts.has("log")) {
			logLevel = Level.parse((String) opts.valueOf("log"));
		}
		setLogLevel(logLevel);
		String listenInterface = (String)opts.valueOf("interface");
		if (headerFile.exists() && ! headerFile.canRead()) {
			error("Metadata file \""+headerFile.getPath()+"\" exists but is not readable.");
		}
		String createMode = (String)opts.valueOf("createmode");
		if ("NO_CREATE".equals(createMode)) {
			if (! headerFile.exists()) { 
				error("Metadata file does not exist at "+headerFile.getPath());
			}
		} else if ("RAND_KEY".equals(createMode)) {
			if (! headerFile.exists()) {
				FileDbRepository repo = new FileDbRepository(headerFile.getPath());
				repo.initMeta();
				byte[] accesskeyBytes = new byte[24];
				new SecureRandom().nextBytes(accesskeyBytes);
				String accesskey = Base64.encodeBytes(accesskeyBytes, Base64.URL_SAFE);
				String code = "apiplugins.add(Packages.dovetaildb.api.Plugin({'wrapApiService':function(api, req){return req.getParameter(\"accesskey\")==\""+accesskey+"\" ? api : null}}))";
				System.out.println();
				System.out.println("Configuration file not found; created a new (empty) configuration at:");
				System.out.println("  "+headerFile.getPath());
				System.out.println("The _meta database has a single code file named \"lockdown.js\".");
				System.out.println("It limits access to a newly generated accesskey with this single line:");
				System.out.println("  "+code);
				System.out.println("Make a note of this accesskey.");
				System.out.println();
				repo.setCodeFile("_meta", "lockdown.js", code);
			}
		} else if ("OPEN".equals(createMode)) {
			if (! headerFile.exists()) {
				FileDbRepository repo = new FileDbRepository(headerFile.getPath());
				repo.initMeta();
				repo.force();
			}
		} else {
			error("Invalid create mode: \""+createMode+"\"");
		}

		final String MAX_HTTP_HEADER_SIZE = "com.sun.enterprise.web.connector.grizzly.maxHttpHeaderSize";
		final String ALLOW_ENCODED_SLASH = "org.apache.tomcat.util.buf.UDecoder.ALLOW_ENCODED_SLASH"; 
		if (System.getProperty(MAX_HTTP_HEADER_SIZE) == null) {
			System.setProperty(MAX_HTTP_HEADER_SIZE, Long.toString(10*1024*1024)); // 10 MB
		}
		if (System.getProperty(ALLOW_ENCODED_SLASH) == null) {
			System.setProperty(ALLOW_ENCODED_SLASH, "true");
		}

		HttpServer ws = new HttpServer();
		ws.getHttpHandler().setAllowEncodedSlash(true);
		ws.addListener(new NetworkListener("web",listenInterface,port));
		DovetaildbServlet ddbServlet = new DovetaildbServlet();
		
		WebappContext webappContext = new WebappContext("webapp", "/");
		ServletRegistration reg = webappContext.addServlet("dovetaildb", ddbServlet);
		reg.setInitParameter("headerFile", headerFile.getAbsolutePath());
		reg.setInitParameter("logLevel", logLevel.getName());
		reg.addMapping("/*");
		reg.setLoadOnStartup(1);
		webappContext.deploy(ws);

		System.out.println();
		System.out.println("  .:::::::::.");
		System.out.println("   ::::::::: ");
		System.out.println("  .   ...   .    DovetailDB");
		System.out.println("  :::     :::    v 0.4");
		System.out.println();

		return ws;
	}

	public static OptionSet parseWithHelp(String[] argv, OptionParser parser, boolean extraOk) throws IOException {
		OptionSet opts = null;
		try {
			opts = parser.parse(argv);
		} catch(OptionException e) {
			parser.printHelpOn(System.err);
			System.err.println();
			System.err.println(e.getMessage());
			System.exit(2);
		}
		if (! opts.nonOptionArguments().isEmpty()) {
			if (! extraOk) {
				parser.printHelpOn(System.err);
				System.err.println();
				System.err.println("Unrecognized argument, \""+opts.nonOptionArguments().get(0)+"\"");
				System.exit(2);
			}
		}
		if (opts.has("h") || opts.has("help")) {
			parser.printHelpOn(System.out);
			System.exit(0);
		}
		return opts;
	}
}
