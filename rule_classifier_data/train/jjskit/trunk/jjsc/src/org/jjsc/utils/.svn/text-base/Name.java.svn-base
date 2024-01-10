package org.jjsc.utils;

import java.util.regex.Pattern;

/**
 * Holds utilities for different kinds of naming conversions.
 * @author alex.bereznevatiy@gmail.com
 */
public class Name {
	private static final Pattern PKG_PART_PATTERN = 
		Pattern.compile("^([A-Za-z$_][$_\\w]*.)*[A-Za-z$_][$_\\w]*$");
	
	public static final char PACKAGE_SEPARATOR = '.';
	public static final char RESOURCE_SEPARATOR = '/';
	
	private Name(){}
	/**
	 * @param packageName
	 * @return <code>true</code> if specified package name 
	 * is valid and <code>false</code> otherwise.
	 */
	public static boolean isValidPackageName(String packageName) {
		if(packageName==null)return false;
		if(packageName.length()==0)return true;
		return PKG_PART_PATTERN.matcher(packageName).matches();
	}
	/**
	 * Creates valid name for subelement (class or subpackage) 
	 * of passed parent package.
	 * @param parent
	 * @param name
	 * @return name of class or subpackage.
	 */
	public static String getChildJavaName(String parent, String name) {
		if(parent==null||parent.length()==0){
			return name;
		}
		return parent+PACKAGE_SEPARATOR+name;
	}
	/**
	 * @param name
	 * @return parent package or class of package or class
	 * represented by the name passed.
	 */
	public static String getParentJavaName(String name) {
		if(name==null||name.length()==0)return "";
		int i = name.lastIndexOf(PACKAGE_SEPARATOR);
		if(i<0)return "";
		return name.substring(0,i);
	}
	/**
	 * @param cls
	 * @return simple name for fully qualified java name.
	 */
	public static String getSimpleName(String cls) {
		int i = cls.lastIndexOf(PACKAGE_SEPARATOR);
		if(i<0)return cls;
		return cls.substring(i+1);
	}
	/**
	 * @param pkg
	 * @param parent
	 * @return true of pkg is direct subpackage for parent.
	 */
	public static boolean isDirectSubPackage(String pkg, String parent) {
		if(parent==null||pkg==null||
			pkg.length()==0||
			!pkg.startsWith(parent)){
			return false;
		}
		int len = parent.length();
		if(len==0){
			return pkg.indexOf(PACKAGE_SEPARATOR)<0;
		}
		len++;
		if(pkg.length()<=len||
			pkg.charAt(len-1)!=PACKAGE_SEPARATOR){
			return false;
		}
		return pkg.indexOf(PACKAGE_SEPARATOR,len)<0;
	}
	/**
	 * Return common package (path) for two java names.
	 * If names belongs to different package trees - returns empty
	 * string.
	 * @param name1
	 * @param name2
	 * @return common package or empty string if there is no common
	 * package (only default package is common for all other packages)
	 */
	public static String getCommonPackage(String name1, String name2) {
		if(name1==name2)return name1;
		if(name1.length()>name2.length()){
			return getCommonPackage(name2,name1);
		}
		int checkPoint = 0;
		final int LEN = name1.length();
		for(int i=0;i<LEN;i++){
			final char c = name1.charAt(i);
			if(name2.charAt(i)!=c){
				return name1.substring(0,checkPoint); 
			}
			if(c==PACKAGE_SEPARATOR){
				checkPoint = i;
			}
		}
		if(name2.length()>LEN&&
			name2.charAt(LEN)!=PACKAGE_SEPARATOR){
			return name1.substring(0,checkPoint);
		}
		return name1;
	}
	/**
	 * @param type
	 * @return <code>true</code> if the name passed is the simple
	 * java name. Note that names of classes from default package 
	 * will be treated as simple names.
	 */
	public static boolean isSimpleName(String type) {
		return type!=null&&type.indexOf(PACKAGE_SEPARATOR)<0;
	}
	/**
	 * @param token
	 * @return <code>true</code> only if passed token 
	 * is valid java identifier.
	 */
	public static boolean isJavaName(String token) {
		char[] name = token.toCharArray();
		if(!Character.isJavaIdentifierStart(name[0])){
			return false;
		}
		for(int i=1;i<name.length;i++){
			if(!Character.isJavaIdentifierPart(name[i])){
				return false;
			}
		}
		return true;
	}
	/**
	 * Converts bytecode internal name to the java fully qualified name.
	 * @param bytecode internal name
	 * @return fully qualified java name
	 */
	public static String fromInternalName(String name) {
		return name.replace(RESOURCE_SEPARATOR, PACKAGE_SEPARATOR);
	}
	/**
	 * @param name
	 * @return <code>true</code> if the passed name is the bytecode internal java class name.
	 */
	public static boolean isInternalName(String name) {
		return name.indexOf(RESOURCE_SEPARATOR)>=0;
	}
	/**
	 * Returns source path for the class with name specified and 
	 * source file name passed.
	 * @param className
	 * @param source file
	 * @return
	 */
	public static Object getSourcePath(String className, String source) {
		String resource = getParentJavaName(className).replace(PACKAGE_SEPARATOR, RESOURCE_SEPARATOR);
		return resource+RESOURCE_SEPARATOR+source;
	}
}
