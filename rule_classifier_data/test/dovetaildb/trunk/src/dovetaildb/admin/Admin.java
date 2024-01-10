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

public class Admin {

	private static void error(String s) {
		System.err.println(s);
		System.exit(1);
	}
	
	public static void main(String[] argv) throws Exception {
		Terminal.setupTerminal();
		ConsoleReader reader = new ConsoleReader();
		OptionParser parser = new OptionParser();
		parser.accepts("l","Language extention").withRequiredArg().defaultsTo("js");
		parser.accepts("u","The base URL of DovetailDB").withRequiredArg().defaultsTo("http://localhost:19156");
		parser.accepts("a","DovetailDB root accesskey").withRequiredArg();
		parser.accepts("h","Help");
		OptionSet opts = Main.parseWithHelp(argv, parser, false);
		String langExt = (String) opts.valueOf("l");
		String baseUrl = (String) opts.valueOf("u");
		String accesskey = (String) opts.valueOf("a");
		if (accesskey == null) {
			reader.printString("Enter root accesskey> ");
			reader.flushConsole();
			accesskey = reader.readLine('*');
//			System.out.print("Enter root accesskey > ");
//			accesskey = new BufferedReader(new InputStreamReader(System.in)).readLine();
		}
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByExtension(langExt);
		DbRepositoryProxy proxy = new DbRepositoryProxy(baseUrl, accesskey);
		try {
			List<String> names = proxy.getDbNames();
			reader.printString("DovetailDB at "+baseUrl+" is alive, databases are: "+names+"\n");
			reader.printString("Entering "+langExt+" interpreter with \"repo\" proxy defined. Ctrl-D to exit.\n");
		} catch(Exception e) {
			error("Could not communicate with DovetailDB at "+baseUrl+": "+e);
		}
		Bindings bindings = engine.createBindings();
		bindings.put("repo", proxy);
		
		InteractiveScriptInterpreter interpreter = new InteractiveScriptInterpreter(engine, bindings);
		TOP: while(true) {
			String prompt = "DB> ";
			Object result;
			while(true) {
				reader.printString(prompt);
				reader.flushConsole();
				prompt = "..> ";
				String line = reader.readLine();
				if (line == null) {
					reader.printNewline();
					return;
				}
				try {
					result = interpreter.interpret(line);
				} catch(ScriptException e) {
					e.printStackTrace();
					continue TOP;
				}
				if (result != InteractiveScriptInterpreter.NOT_DONE) {
					break;
				}
			}
			String retString = (result == null) ? "" : result.toString(); 
			reader.printString(retString+"\n");
		}
	}
}
