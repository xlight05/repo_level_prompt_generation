package org.jjsc.compiler;


/**
 * This testcase goal is to test early bugs detected due implementation of jjskit core.
 * @author alex.bereznevatiy@gmail.com
 *
 */
public class EarlyBugsTestCase extends MethodCompilerTestCase {

	/**
	 * Load class method causes error:
	 *  0  aload_0 [this]
	 *  1  aload_1 [name]
	 *  2  invokevirtual java.lang.ClassLoader.findLoadedClass(java.lang.String) : java.lang.Class [44]
	 *  5  astore_3 [c]
	 *  6  aload_3 [c]
	 *  7  ifnonnull 51
	 * 10  aload_0 [this]
	 * 11  getfield java.lang.ClassLoader.parent : java.lang.ClassLoader [19]
	 * 14  ifnull 30
	 * 17  aload_0 [this]
	 * 18  getfield java.lang.ClassLoader.parent : java.lang.ClassLoader [19]
	 * 21  aload_1 [name]
	 * 22  iconst_0
	 * 23  invokevirtual java.lang.ClassLoader.loadClass(java.lang.String, boolean) : java.lang.Class [38]
	 * 26  astore_3 [c]
	 * 27  goto 41
	 * 30  aload_0 [this]
	 * 31  aload_1 [name]
	 * 32  invokespecial java.lang.ClassLoader.findBootstrapClassOrNull(java.lang.String) : java.lang.Class [47]
	 * 35  astore_3 [c]
	 * 36  goto 41
	 * 39  astore 4
	 * 41  aload_3 [c]
	 * 42  ifnonnull 51
	 * 45  aload_0 [this]
	 * 46  aload_1 [name]
	 * 47  invokevirtual java.lang.ClassLoader.findClass(java.lang.String) : java.lang.Class [50]
	 * 50  astore_3 [c]
	 * 51  iload_2 [resolve]
	 * 52  ifeq 60
	 * 55  aload_0 [this]
	 * 56  aload_3 [c]
	 * 57  invokevirtual java.lang.ClassLoader.resolveClass(java.lang.Class) : void [53]
	 * 60  aload_3 [c]
	 * 61  areturn
	 *     Exception Table:
	 *     [pc: 10, pc: 36] -> 39 when : java.lang.ClassNotFoundException
	 */
	public void testLoadClass() {
		MethodCompiler compiler = createCompiler("loadClass","(Ljava/lang/String;Z)Ljava/lang/Class;",new Object[]{
			ALOAD+ " 0",
			ALOAD+ " 1",
			INVOKEVIRTUAL+" java/lang/ClassLoader findLoadedClass (Ljava/lang/String;)Ljava/lang/Class;",
			ASTORE+" 3",
			ALOAD+ " 3",
			IFNONNULL+" L51",
			"L10",
			ALOAD+" 0",
			GETFIELD+" java/lang/ClassLoader parent java/lang/ClassLoader",
			IFNULL+" L30",
			ALOAD+" 0",
			GETFIELD+" java/lang/ClassLoader parent java/lang/ClassLoader",
			ALOAD+" 1",
			ICONST_0,
			INVOKEVIRTUAL+" java/lang/ClassLoader loadClass (Ljava/lang/String;Z)Ljava/lang/Class;",
			ASTORE+" 3",
			GOTO+" L41",
			"L30",
			ALOAD+" 0",
			ALOAD+" 1",
			INVOKESPECIAL+" java/lang/ClassLoader findBootstrapClassOrNull (Ljava/lang/String;)Ljava/lang/Class;",
			ASTORE+" 3",
			"L36",
			GOTO+" L41",
			"L39",
			ASTORE+" 4",
			"L41",
			ALOAD+" 3",
			IFNONNULL+" L51",
			ALOAD+" 0",
			ALOAD+" 1",
			INVOKEVIRTUAL+" java/lang/ClassLoader findClass (Ljava/lang/String;)Ljava/lang/Class;",
			ASTORE+" 3",
			"L51",
			ILOAD+" 2",
			IFEQ+" L60",
			ALOAD+" 0",
			ALOAD+" 3",
			INVOKEVIRTUAL+" java/lang/ClassLoader resolveClass (Ljava/lang/Class;)V",
			"L60",
			ALOAD+" 3",
			ARETURN}, new Object[]{
				"java/lang/ClassNotFoundException L10 L36 L39"
			},ACC_PROTECTED|ACC_SYNCHRONIZED);
		compiler.visitEnd();
		assertEquals("?", 
				compiler.getCompilationBuffer().toString().replaceAll("\\s+", " "));
	}
}
