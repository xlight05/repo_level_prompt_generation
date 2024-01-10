package org.jjsc.compiler.javascript;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.jjsc.utils.Name;
import org.jjsc.utils.StringUtils;
import org.objectweb.asm.Label;

/**
 * This class holds static utility methods to refer to javascript 
 * language bridge module implementation.
 * 
 * @author alex.bereznevatiy@gmail.com
 */
public final class JavaScriptLang {
	/**
	 * Variable name reserved for exception handling.
	 */
	public static final String EXCEPTION_VAR = "ex";
	/**
	 * Undefined keyword.
	 */
	public static final String UNDEFINED = "undefined";
	/**
	 * These keywords are either reserved by java(ESMA)script specification or accessible as
	 * global properties so that cannot be used as indentifiers names.
	 */
	public static final Set<String> RESERVED_KEYWORDS = new HashSet<String>(Arrays.asList(new String[]{
			"break","continue","do","for","import","new","this","void","case","default",
			"else","function","in","return","typeof","while","comment","delete","export",
			"if","label","switch","var","with","abstract","implements","protected","boolean",
			"instanceOf","public","byte","int","short","char","interface","static","double",
			"long","synchronized","false","native","throws","final","null","transient","float",
			"package","true","goto","private","catch","enum","throw","class","extends","try",
			"const","finally","debugger","super","alert","eval","Link","outerHeight","scrollTo",
			"Anchor","FileUpload","location","outerWidth","Select","Area","find","Location",
			"Packages","self","arguments","focus","locationbar","pageXoffset","setInterval",
			"Array","Form","Math","pageYoffset","setTimeout","assign","Frame","menubar",
			"parent","status","blur","frames","MimeType","parseFloat","statusbar","Boolean",
			"Function","moveBy","parseInt","stop","Button","getClass","moveTo","Password","String",
			"callee","Hidden","name","personalbar","Submit","caller","history","NaN","Plugin","sun",
			"captureEvents","History","navigate","print","taint","Checkbox","home","navigator",
			"prompt","Text","clearInterval","Image","Navigator","prototype","Textarea","clearTimeout",
			"Infinity","netscape","Radio","toolbar","close","innerHeight","Number","ref","top",
			"closed","innerWidth","Object","RegExp","toString","confirm","isFinite","onBlur",
			"releaseEvents","unescape","constructor","isNan","onError","Reset","untaint","Date",
			"java","onFocus","resizeBy","unwatch","defaultStatus","JavaArray","onLoad","resizeTo",
			"valueOf","document","JavaClass","onUnload","routeEvent","watch","Document","JavaObject",
			"open","scroll","window","Element","JavaPackage","opener","scrollbars","Window","escape",
			"length","Option","scrollBy",EXCEPTION_VAR, UNDEFINED
	}));
	
	private JavaScriptLang(){}
	/**
	 * Writes package declaration to the result {@link StringBuilder}.
	 * @param packageName
	 * @param result
	 */
	public static void packageDeclaration(String packageName,StringBuilder result) {
		if(StringUtils.isEmpty(packageName))return;
		result.append("J.pkg('");
		result.append(packageName);
		result.append("');\n");
	}
	/**
	 * Adds extends statement to extend class from first argument with class from
	 * second argument. The result will be written to the {@link StringBuilder} 
	 * passed as third argument.
	 * @param className
	 * @param superClassName
	 * @param result
	 */
	public static void extend(String className,String superClassName,StringBuilder result){
		result.append(className);
		result.append("=J.ext(");
		result.append(superClassName);
	}
	/**
	 * Adds import statement to load and import class from second argument 
	 * and assigns it to the ally name from first argument. The result will be 
	 * written to the {@link StringBuilder} passed as third argument.
	 * @param ally
	 * @param className
	 * @param result
	 */
	public static void importDeclaration(String ally,
			String className, StringBuilder result) {
		result.append("var ");
		result.append(ally);
		result.append("=J.lc('");
		result.append(className);
		result.append("');\n");
	}
	/**
	 * Returns the debug function invocation.
	 * This function will be used by debugger for breakpoints installations and 
	 * JDPA implementation for JJS Virtual Machine.
	 * @param line
	 * @param start
	 * @return the debug function invocation.
	 */
	public static String debugLine(int line, Label start) {
		return "J.ln("+line+");\n";
	}
	/**
	 * Switches debugger to point to the source file specified.
	 * All debug requests will point to that file.
	 * @param className
	 * @param source
	 * @param result
	 */
	public static void setSourceFile(String className, 
			String source, StringBuilder result) {
		result.append(className);
		result.append(".prototype.__debugSrc__='");
		result.append(Name.getSourcePath(className,source));
		result.append("';\n");
	}
	/**
	 * @return classloader registration statement open.
	 */
	public static String registerClassLoader() {
		return "J.reg(";
	}
	/**
	 * @param first argument
	 * @param second argument
	 * @return function call that compares values and results -1 if second argument is greater, 
	 * 1 if first argument is grater and 0 if arguments are equals. 
	 */
	public static Object compare(Object first, Object second) {
		return "J.cmp("+first+','+second+')';
	}
	/**
	 * Creates new array creation expression for array of given type and 
	 * with dimensions passed.
	 * @param type
	 * @param dimensions
	 * @return new array definition
	 */
	public static Object newArray(Object type, Object[] dimensions) {
		StringBuilder result = new StringBuilder("J.array(");
		result.append(type);
		for (Object size : dimensions) {
			result.append(',');
			result.append(size);
		}
		return result.append(')').toString();
	}
	/**
	 * @param type
	 * @param expression
	 * @return instruction that checks that expression returns result of type specified.
	 */
	public static Object checkCast(String type, Object expression) {
		return "J.cast("+expression+','+type+')';
	}
	/**
	 * @param type
	 * @param expression
	 * @return instanceOf expression
	 */
	public static Object checkType(String type, Object expression) {
		return "J.is("+expression+','+type+')';
	}
}
