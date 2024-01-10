package java.lang;

import java.lang.annotation.Native;

@Native("java/lang/Object.js")
public class Object {
	public final native Class<? extends Object> getClass();
}
