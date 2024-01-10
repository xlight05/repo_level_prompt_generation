package classes;

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

import entities.Entity;
import entities.Mob;

import spells.Spell;
import sun.org.mozilla.javascript.internal.NativeArray;
import sun.org.mozilla.javascript.internal.NativeJavaObject;

public class ScriptClass extends CharacterClass {

	// The script manager. Only used to get the JavaScript engine running
	private ScriptEngineManager emgr = new ScriptEngineManager();
	
	// The JavaScript engine, which is only used to allow the invocable engine
	// to run
	// and evaluate scripts
	private ScriptEngine fjsEngine = emgr.getEngineByName("JavaScript");
	
	// The Invocable Engine, which actually executes the script
	private Invocable iEngine = (Invocable) fjsEngine;

	// The bindings used by the engine - what variables in JS are bound to what
	// in Java
	private Bindings myBinds = fjsEngine.getBindings(ScriptContext.ENGINE_SCOPE);

	private File myFile;

	// A string representation of the script, formatted exactly like it was
	// given
	// for debugging purposes
	private String myScript;
	
	public ScriptClass(File script) {

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
		setStats();
	}
	
	/* (non-Javadoc)
	 * @see classes.CharacterClass#calcDamage(entities.Mob)
	 */
	@Override
	public int calcDamage(Mob m) {
		int i=super.calcDamage(m);
		if(myScript.contains("calcDamage")){
			i += invoke("calcDamage",m,i);
		}
		return i;
	}


	/* (non-Javadoc)
	 * @see classes.CharacterClass#calcReductions(int)
	 */
	@Override
	public int calcReductions(int i) {
		int x = 0;
		if(myScript.contains("calcReductions")){
			x = invoke("calcReductions",i);
		}
		return x;
	}

	/* (non-Javadoc)
	 * @see classes.CharacterClass#calcReductions(int,Spell)
	 */
	@Override
	public int calcReductions(int i, Spell s) {
		int x = 0;
		if(myScript.contains("calcSpellReductions")){
			x = invoke("calcSpellReductions",i,s);
		}
		return x;
	}

	/* (non-Javadoc)
	 * @see classes.CharacterClass#canDodge()
	 */
	@Override
	public boolean canDodge() {
		if(myScript.contains("canDodge")){
			return invoke("canDodge")==1;
		}
		return false;
	}
	
	public Invocable getiEngine() {
		return iEngine;
	}

	public File getMyFile() {
		return myFile;
	}

	public String getMyScript() {
		return myScript;
	}

	/* (non-Javadoc)
	 * @see classes.CharacterClass#heal()
	 */
	@Override
	public void heal() {
		if(getMyScript().contains("heal(")){
			doinvoke("heal");
		}
		super.heal();
	}

	public void initialize() {
		myBinds.put("Class", this);
		try {
			fjsEngine.eval(myScript);
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
			System.err.println("Failed to initialize class. (" + myFile.getName()+")");
		}
	}

	/* (non-Javadoc)
	 * @see classes.CharacterClass#onAllyDeath(entities.Entity, entities.Entity)
	 */
	@Override
	public void onAllyDeath(Entity killer, Entity target) {
		doinvoke("onAllyDeath");
		super.onAllyDeath(killer, target);
	}

	/* (non-Javadoc)
	 * @see classes.CharacterClass#onEnemyDeath(entities.Entity, entities.Entity)
	 */
	@Override
	public void onEnemyDeath(Entity killer, Entity target) {
		doinvoke("onEnemyDeath");
		super.onEnemyDeath(killer, target);
	}

	/* (non-Javadoc)
	 * @see classes.CharacterClass#onRecieveDeath(entities.Entity, entities.Entity)
	 */
	@Override
	public void onRecieveDeath(Entity killer, Entity target) {
		doinvoke("onRecieveDeath");
		super.onRecieveDeath(killer, target);
	}

	/* (non-Javadoc)
	 * @see classes.CharacterClass#onSelfDeath(entities.Entity, entities.Entity)
	 */
	@Override
	public void onSelfDeath(Entity killer, Entity target) {
		doinvoke("onSelfDeath");
		super.onSelfDeath(killer, target);
	}

	private int invoke(String method, Object... args) {
		double rv = 0;
		try {
			rv = Double.parseDouble(""+iEngine.invokeFunction(method, args));
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (ScriptException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return (int)rv;
	}

	private void invokenull(String method, Object... args) {
		try {
			iEngine.invokeFunction(method, args);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (ScriptException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

	}

	/* (non-Javadoc)
	 * @see classes.CharacterClass#modDamage(entities.Mob)
	 */
	@Override
	public int modDamage(Mob m, int x) {
		if(myScript.contains("modDamage")){
			x = invoke("modDamage", m, x);
		}
		return x;
	}

	public void setiEngine(Invocable iEngine) {
		this.iEngine = iEngine;
	}

	public void setMyFile(File myFile) {
		this.myFile = myFile;
	}

	public void setMyScript(String myScript) {
		this.myScript = myScript;
	}

	@Override
	protected void setStats() {
		if(myScript!=null && myScript.contains("setStats")){
			invokenull("setStats");
		}
	}

	@Override
	public void onRecieveCriticalHit(Entity hitter, Entity hit, Integer damage) {
		super.onRecieveCriticalHit(hitter, hit, damage);
		doinvoke("onRecieveCriticalHit");
	}
	
	@Override
	public void onRecieveHit(Entity hitter, Entity hit, Integer damage) {
		super.onRecieveHit(hitter, hit, damage);
		doinvoke("onRecieveHit");
	}
	
	private void doinvoke(String s) {
		if(myScript.contains(s)){
			invokenull(s);
		}
	}
}
