package dovetaildb.admin;

import java.util.List;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import jline.ConsoleReader;
import jline.Terminal;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import dovetaildb.Main;
import dovetaildb.dbrepository.DbRepositoryProxy;

public class AdminTelnetServer {
/*
	public static void run(int port) {
		while (true) {
			ServerSocket s = new ServerSocket(port);
			Scoket incoming = s.accept();
			InputStream inps = incoming.getInputStream();
			OutputStream outs = incoming.getOutputStream();
			Scanner in = new Scanner(inps);
			PrintWriter out = new PrintWriter(outs, true);
			boolean done = false;
			while (!done && in.hasNextLine()) {
				String line = in.nextLine();
				if (line.trim().equalsIgnoreCase("exit")) {
					done = true;
				} else {
					out.println("Echo: " + line);
				}
			}
		}
	}
*/	
}
