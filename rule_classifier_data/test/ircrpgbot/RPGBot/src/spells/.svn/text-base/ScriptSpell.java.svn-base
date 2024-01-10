/*
 * @author Kyle Kemp
 */
package spells;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.script.Bindings;
import javax.script.Invocable;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import sun.org.mozilla.javascript.internal.NativeArray;
import sun.org.mozilla.javascript.internal.NativeJavaObject;
import bot.Bot;
import entities.Mob;

public class ScriptSpell extends Spell {

	// The script manager. Only used to get the JavaScript engine running
	private ScriptEngineManager mgr = new ScriptEngineManager();
	
	// The JavaScript engine, which is only used to allow the invocable engine
	// to run
	// and evaluate scripts
	private ScriptEngine jsEngine = mgr.getEngineByName("JavaScript");
	
	// The Invocable Engine, which actually executes the script
	private Invocable iEngine = (Invocable) jsEngine;


	// The bindings used by the engine - what variables in JS are bound to what
	// in Java
	private Bindings myBinds = jsEngine.getBindings(ScriptContext.ENGINE_SCOPE);

	private File myFile;
	// A string representation of the script, formatted exactly like it was
	// given
	// for debugging purposes
	private String myScript;

	public ScriptSpell(Bot b, File script) {
		super(b);
		
		myFile=script;

		// get ready for parsing the file
		StringBuffer contents = new StringBuffer();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(script));
			String text = null;
			// parse the file line by line
			while ((text = reader.readLine()) != null) {
				contents.append(text).append(
						System.getProperty("line.separator"));
			}
			reader.close();
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// take the string representation of the file, and put it in this script
		setMyScript(contents.toString());

		initialize();
	}

	public ScriptSpell(Bot b, String s) {
		super(b);

		setMyScript(s);

		initialize();
	}

	@Override
	protected int calcDamage(Mob target) {
		return invoke("calcDamage", target);
	}

	@Override
	public void cast(Mob caster, Mob target) {
		int i=0;
		for(Spell s : caster.getSpells()){
			if(s instanceof ScriptSpell){
				ScriptSpell ss = (ScriptSpell) s;
				if(ss.myScript.contains("preCast")){
					i += invoke("preCast",caster,target);
				}
			}
		}
		if(i > 0){ return; }
		
		super.cast(caster, target);
		invokenull("cast", caster, target);
	}
	
	public Invocable getiEngine() {
		return iEngine;
	}

	/**
	 * @return the myFile
	 */
	public File getMyFile() {
		return myFile;
	}

	public String getMyScript() {
		return myScript;
	}

	public void initialize() {
		myBinds.put("spell", this);
		myBinds.put("bot", bot);

		try {
			jsEngine.eval(myScript);
			NativeArray arr = (NativeArray) iEngine.invokeFunction("init");
			Object[] array = new Object[(int) arr.getLength()];
			for (Object o : arr.getIds()) {
				int index = (Integer) o;
				array[index] = arr.get(index, null);
			}
			Restriction[] rarray = new Restriction[array.length];
			for (int i = 0; i < array.length; i++) {
				rarray[i] = (Restriction) ((NativeJavaObject) array[i])
						.unwrap();
			}
			super.addClasses(rarray);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Failed to initialize spell. (" + myFile.getName()+")");
			bot.sendMessage(Bot.channel, "Failed to initialize spell. (" + myFile.getName()+")");
		}
	}

	private int invoke(String method, Object... args) {
		int rv = 0;
		try {
			rv = (Integer) iEngine.invokeFunction(method, args);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (ScriptException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return rv;
	}

	private void invokenull(String method, Object... args) {
		try {
			iEngine.invokeFunction(method, args);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (ScriptException e) {
			e.printStackTrace();
			bot.sendMessage(Bot.channel, e.getMessage());
			bot.sendMessage(Bot.channel, getMyFile()+ "#"+e.getLineNumber());
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see spells.Spell#postTick()
	 */
	@Override
	public void postTick() {
		if (getMyScript().contains("postTick")) {
			invokenull("postTick");
			decreaseDuration();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see spells.Spell#preTick()
	 */
	@Override
	public void preTick() {
		if (getMyScript().contains("preTick")) {
			invokenull("preTick");
			decreaseDuration();
		}
	}
	
	/* (non-Javadoc)
	 * @see spells.Spell#onDurationOver()
	 */
	@Override
	public void onDurationOver(boolean forced) {
		if(getMyScript().contains("onDurationOver")){
			invokenull("onDurationOver", forced);
		}
	}

	public void setiEngine(Invocable iEngine) {
		this.iEngine = iEngine;
	}

	public void setMyScript(String myScript) {
		this.myScript = myScript;
	}

}
