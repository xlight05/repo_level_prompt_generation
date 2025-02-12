package org.rsbot.util;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.LogManager;

import javax.swing.filechooser.FileSystemView;

import org.rsbot.util.logging.ConsoleLogger;
import org.rsbot.util.logging.LogFormatter;
import org.rsbot.util.logging.TextAreaLogHandler;

public class GlobalConfiguration {
	public enum OperatingSystem {
		MAC, WINDOWS, LINUX, UNKNOWN
	}

	public static class Paths {
		public static class Resources {
			public static final String ROOT = "resources";
			public static final String SCRIPTS = Paths.SCRIPTS_NAME_SRC + "/";

			public static final String ROOT_IMG = "/" + Resources.ROOT + "/images";
			public static final String ICON = Resources.ROOT_IMG + "/icon.png";
			public static final String ICON_DELETE = Resources.ROOT_IMG + "/delete.png";
			public static final String ICON_PLAY = Resources.ROOT_IMG + "/control_play_blue.png";
			public static final String ICON_PAUSE = Resources.ROOT_IMG + "/control_pause.png";
			public static final String ICON_TICK = Resources.ROOT_IMG + "/tick.png";
			public static final String ICON_SCRIPT = Resources.ROOT_IMG + "/script_gear.png";

			public static final String VERSION = Resources.ROOT + "/version.dat";
		}

		public static class URLs {
			public static final String DOMAIN = "powerbot.org";
			public static final String UPDATER = "https://rsbotownversion.googlecode.com/svn/trunk/";
			public static final String UPDATE = UPDATER + "Updater/legacy.dat";
			public static final String DOWNLOAD = UPDATER + "/data/RSBot.jar";
			public static final String VERSION = UPDATER + "Updater/version.dat";
			public static final String PROJECT = "https://code.google.com/p/rsbotownversion/";
			public static final String SITE = "http://www.powerbot.org/";
		}

		public static final String ROOT = "." + File.separator + "resources";

		public static final String RSJAR = "lib" + File.separator + "rs.jar";
		public static final String COMPILE_SCRIPTS_BAT = "Compile-Scripts.bat";
		public static final String COMPILE_SCRIPTS_SH = "compile-scripts.sh";

		public static final String COMPILE_FINDJDK = "FindJDK.bat";
		private static final String ROOT_IMG = Paths.ROOT + File.separator + "images";
		public static final String ICON = Paths.ROOT_IMG + File.separator + "icon.png";
		public static final String ICON_DELETE = Paths.ROOT_IMG + File.separator + "delete.png";
		public static final String ICON_PLAY = Paths.ROOT_IMG + File.separator + "control_play_blue.png";
		public static final String ICON_PAUSE = Paths.ROOT_IMG + File.separator + "control_pause.png";
		public static final String ICON_TICK = Paths.ROOT_IMG + File.separator + "tick.png";
		public static final String ICON_SCRIPT = Paths.ROOT_IMG + File.separator + "script_gear.png";

		public static final String VERSION = Paths.ROOT + File.separator + "version.dat";

		public static final String SCRIPTS_NAME_SRC = "scripts";
		public static final String SCRIPTS_NAME_OUT = "Scripts";

		public static String getAccountsFile() {
			final String path;
			if (GlobalConfiguration.getCurrentOperatingSystem() == OperatingSystem.WINDOWS) {
				path = System.getenv("APPDATA") + File.separator + GlobalConfiguration.NAME + " Accounts.ini";
			} else {
				path = Paths.getUnixHome() + File.separator + "." + GlobalConfiguration.NAME_LOWERCASE + "pw";
			}
			return path;
		}

		public static String getHomeDirectory() {
			final String env = System.getenv(GlobalConfiguration.NAME.toUpperCase() + "_HOME");
			if ((env == null) || env.isEmpty())
				return (GlobalConfiguration.getCurrentOperatingSystem() == OperatingSystem.WINDOWS ? FileSystemView.getFileSystemView().getDefaultDirectory().getAbsolutePath() : Paths.getUnixHome()) + File.separator + GlobalConfiguration.NAME;
			else
				return env;
		}

		public static String getLogsDirectory() {
			return Paths.getHomeDirectory() + File.separator + "Logs";
		}

		public static String getMenuCache() {
			return Paths.getSettingsDirectory() + File.separator + "Menu.txt";
		}

		public static String getPathCache() {
			return Paths.getSettingsDirectory() + File.separator + "path.txt";
		}
		
		public static String getVersionCache() {
			return Paths.getCacheDirectory() + File.separator + "info.dat";
		}

		public static String getClientCache() {
			return Paths.getCacheDirectory() + File.separator + "client.dat";
		}

		public static String getScreenshotsDirectory() {
			return Paths.getHomeDirectory() + File.separator + "Screenshots";
		}

		public static String getScriptsDirectory() {
			return Paths.getHomeDirectory() + File.separator + Paths.SCRIPTS_NAME_OUT;
		}

		public static String getScriptsPrecompiledDirectory() {
			return Paths.getScriptsDirectory() + File.separator + "Precompiled";
		}
		
		public static String getCacheDirectory() {
			return Paths.getHomeDirectory() + File.separator + "Cache";
		}

		public static String getSettingsDirectory() {
			return Paths.getHomeDirectory() + File.separator + "Settings";
		}

		public static String getBreaksDirectory() {
			return Paths.getSettingsDirectory() + File.separator + "Breaks.txt";
		}

		public static String getUnixHome() {
			final String home = System.getProperty("user.home");
			return home == null ? "~" : home;
		}
	}

	public static final String NAME = "RSBot";

	public static final String NAME_LOWERCASE = "rsbot";

	private static final OperatingSystem CURRENT_OS;

	public static boolean RUNNING_FROM_JAR = false;

	static {
		final URL resource = GlobalConfiguration.class.getClassLoader().getResource(Paths.Resources.VERSION);
		if (resource != null) {
			GlobalConfiguration.RUNNING_FROM_JAR = true;
		}

		final String os = System.getProperty("os.name");
		if (os.contains("Mac")) {
			CURRENT_OS = OperatingSystem.MAC;
		} else if (os.contains("Windows")) {
			CURRENT_OS = OperatingSystem.WINDOWS;
		} else if (os.contains("Linux")) {
			CURRENT_OS = OperatingSystem.LINUX;
		} else {
			CURRENT_OS = OperatingSystem.UNKNOWN;
		}

		final ArrayList<String> dirs = new ArrayList<String>();
		dirs.add(Paths.getHomeDirectory());
		dirs.add(Paths.getLogsDirectory());
		dirs.add(Paths.getCacheDirectory());
		dirs.add(Paths.getSettingsDirectory());
		if (GlobalConfiguration.RUNNING_FROM_JAR) {
			dirs.add(Paths.getScriptsDirectory());
			dirs.add(Paths.getScriptsPrecompiledDirectory());
		}

		for (final String name : dirs) {
			final File dir = new File(name);
			if (!dir.exists()) {
				dir.mkdirs();
			}
		}

		final Properties logging = new Properties();
		final String logformatter = LogFormatter.class.getCanonicalName();
		final String filehandler = FileHandler.class.getCanonicalName();
		logging.setProperty("handlers", TextAreaLogHandler.class.getCanonicalName() + "," + filehandler);
		logging.setProperty(".level", "INFO");
		logging.setProperty(ConsoleLogger.class.getCanonicalName() + ".formatter", logformatter);
		logging.setProperty(filehandler + ".formatter", logformatter);
		logging.setProperty(TextAreaLogHandler.class.getCanonicalName() + ".formatter", logformatter);
		logging.setProperty(filehandler + ".pattern", Paths.getLogsDirectory() + File.separator + "%u.%g.log");
		logging.setProperty(filehandler + ".count", "10");
		final ByteArrayOutputStream logout = new ByteArrayOutputStream();
		try {
			logging.store(logout, "");
			LogManager.getLogManager().readConfiguration(new ByteArrayInputStream(logout.toByteArray()));
		} catch (final Exception e) {
		}

		if (GlobalConfiguration.RUNNING_FROM_JAR) {
			String path = resource.toString();
			try {
				path = URLDecoder.decode(path, "UTF-8");
			} catch (final UnsupportedEncodingException e1) {
			}
			final String prefix = "jar:file:/";
			if (path.indexOf(prefix) == 0) {
				path = path.substring(prefix.length());
				path = path.substring(0, path.indexOf('!'));
				if (File.separatorChar != '/') {
					path = path.replace('/', File.separatorChar);
				}
				try {
					final File pathfile = new File(Paths.getPathCache());
					if (pathfile.exists()) {
						pathfile.delete();
					}
					pathfile.createNewFile();
					final Writer out = new BufferedWriter(new FileWriter(Paths.getPathCache()));
					out.write(path);
					out.close();
				} catch (final Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static OperatingSystem getCurrentOperatingSystem() {
		return GlobalConfiguration.CURRENT_OS;
	}

	public static String getHttpUserAgent() {
		String agent = "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Tablet PC 2.0)";
		if (GlobalConfiguration.getCurrentOperatingSystem() == GlobalConfiguration.OperatingSystem.MAC) {
			agent = "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_5_8; en-us) AppleWebKit/531.9 (KHTML, like Gecko) Version/4.0.3 Safari/531.9";
		} else if (GlobalConfiguration.getCurrentOperatingSystem() != GlobalConfiguration.OperatingSystem.WINDOWS) {
			agent = "Mozilla/5.0 (X11; U; Linux i686; en-GB; rv:1.9.1.5) Gecko/20091109 Firefox/3.5.5";
		}
		return agent;
	}

	public static URLConnection getURLConnection(final URL url, final String referer) throws IOException {
		final URLConnection con = url.openConnection();
		con.addRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		con.addRequestProperty("Accept-Charset", "ISO-8859-1,utf-8;q=0.7,*;q=0.7");
		con.addRequestProperty("Accept-Encoding", "gzip,deflate");
		con.addRequestProperty("Accept-Language", "en-us,en;q=0.5");
		con.addRequestProperty("Connection", "keep-alive");
		con.addRequestProperty("Host", url.getHost());
		con.addRequestProperty("Keep-Alive", "300");
		if (referer != null) {
			con.addRequestProperty("Referer", referer);
		}
		con.addRequestProperty("User-Agent", getHttpUserAgent());
		return con;
	}

	public static int getVersion() {
		try {
			final InputStream is = RUNNING_FROM_JAR ? GlobalConfiguration.class.getClassLoader().getResourceAsStream(Paths.Resources.VERSION) : new FileInputStream(Paths.VERSION);
			
			int off = 0;
			byte[] b = new byte[2];
			while((off += is.read(b, off, 2 - off)) != 2);
			
			return ((0xFF & b[0]) << 8) + (0xFF & b[1]);
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
}
