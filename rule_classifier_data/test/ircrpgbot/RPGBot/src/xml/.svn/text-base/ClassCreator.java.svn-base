package xml;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import classes.CharacterClass;
import classes.ScriptClass;

public class ClassCreator {
	public static CharacterClass createNewClass(CharacterClass c) throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException{
		if(c instanceof ScriptClass){
			ScriptClass s = (ScriptClass) c;
			Constructor<?> constructor = s.getClass().getDeclaredConstructor(File.class);
			CharacterClass com = (CharacterClass) constructor
					.newInstance(s.getMyFile());
			return com;
		} else {
			return c.getClass().newInstance();
		}
	}
}
