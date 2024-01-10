package org.jjsc.compiler;


public class MethodsInvocationTestCase extends MethodCompilerTestCase {
	/**
	 * Simple bytecode:
	 *  0  getstatic java.lang.System.out : java.io.PrintStream [16]
	 *  3  astore_1 [out]
	 *  4  aload_1 [out]
	 *  5  invokevirtual java.io.PrintStream.println() : void [22]
	 *  8  return
	 */
	public void testInvokeNoArgs() {
		MethodCompiler compiler = createCompiler("zzz",
			GETSTATIC+" java/lang/System out java/io/PrintStream",
			ASTORE+" 1",
			ALOAD+" 1",
			INVOKEVIRTUAL+" java/io/PrintStream println ()V",
			RETURN);
		compiler.visitEnd();
		assertEquals(" arguments[0]=System.out;arguments[0]['println()']();return; }; ",
				compiler.getCompilationBuffer().toString().replaceAll("\\s+", " "));
	}
	/**
	 * Simple bytecode:
	 *  0  getstatic java.lang.System.out : java.io.PrintStream [16]
	 *  3  astore_1 [out]
	 *  4  aload_1 [out]
	 *  5  ldc <String "zz"> [22]
	 *  7  invokevirtual java.io.PrintStream.println(java.lang.String) : void [24]
	 * 10  aload_1 [out]
	 * 11  bipush 12
	 * 13  invokevirtual java.io.PrintStream.println(int) : void [30]
	 * 16  return
	 */
	public void testInvokeConstantArgs() {
		MethodCompiler compiler = createCompiler("zzz",
				GETSTATIC+" java/lang/System out java/io/PrintStream",
				ASTORE+" 1",
				ALOAD+" 1",
				LDC+" zz",
				INVOKEVIRTUAL+" java/io/PrintStream println (Ljava/lang/String;)V",
				ALOAD+" 1",
				BIPUSH+ " 12",
				INVOKEVIRTUAL+" java/io/PrintStream println (I)V",
				RETURN);
		compiler.visitEnd();
		assertEquals(" arguments[0]=System.out;arguments[0]['println(Ljava/lang/String;)'](\"zz\");" +
				"arguments[0]['println(I)'](12);return; }; ",
				compiler.getCompilationBuffer().toString().replaceAll("\\s+", " "));
	}
	/**
	 * While cycle with an simple condition:
	 * <code>
	 *  System.out.println(String.format(args[0], "%s","ss"));
	 * </code>
	 * following bytecode will be produced:
	 *  0  getstatic java.lang.System.out : java.io.PrintStream [16]
	 *  3  aload_0 [args]
	 *  4  iconst_0
	 *  5  aaload
	 *  6  iconst_2
	 *  7  anewarray java.lang.Object [3]
	 * 10  dup
	 * 11  iconst_0
	 * 12  ldc <String "%s"> [22]
	 * 14  aastore
	 * 15  dup
	 * 16  iconst_1
	 * 17  ldc <String "ss"> [24]
	 * 19  aastore
	 * 20  invokestatic java.lang.String.format(java.lang.String, java.lang.Object[]) : java.lang.String [26]
	 * 23  invokevirtual java.io.PrintStream.println(java.lang.String) : void [32]
	 * 26  return
	 */
	public void testInvokeNested() {
		MethodCompiler compiler = createCompiler("zzz",
			GETSTATIC+" java/lang/System out java/io/PrintStream",
			ALOAD+" 0",
			ICONST_0,
			AALOAD,
			ICONST_2,
			ANEWARRAY+" java/lang/Object",
			DUP,
			ICONST_0,
			LDC+" %s",
			AASTORE,
			DUP,
			ICONST_1,
			LDC+" ss",
			AASTORE,
			INVOKESTATIC+" java/lang/String format (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;",
			INVOKEVIRTUAL+" java/io/PrintStream println (Ljava/lang/String;)V",
			RETURN);
		compiler.visitEnd();
		assertEquals(" var a=J.array(Object0,2);a[0]=\"%s\";var b=a;b[1]=\"ss\";" +
				"System.out['println(Ljava/lang/String;)'](String0['format(Ljava/lang/String;[Ljava/lang/Object;)'](arguments[0][0],b));return; }; ",
			compiler.getCompilationBuffer().toString().replaceAll("\\s+", " "));
	}
	/**
	 * While cycle with an simple condition:
	 * <code>
	 *  StringBuilder result = new StringBuilder();
	 *  result.append(args.length).append("zzz")
	 *        .append(args[0]).append(args[1]);
	 * </code>
	 * following bytecode will be produced:
	 *  0  new java.lang.StringBuilder [16]
	 *  3  dup
	 *  4  invokespecial java.lang.StringBuilder() [18]
	 *  7  astore_1 [result]
	 *  8  aload_1 [result]
	 *  9  aload_0 [args]
	 * 10  arraylength
	 * 11  invokevirtual java.lang.StringBuilder.append(int) : java.lang.StringBuilder [19]
	 * 14  ldc <String "zzz"> [23]
	 * 16  invokevirtual java.lang.StringBuilder.append(java.lang.String) : java.lang.StringBuilder [25]
	 * 19  aload_0 [args]
	 * 20  iconst_0
	 * 21  aaload
	 * 22  invokevirtual java.lang.StringBuilder.append(java.lang.String) : java.lang.StringBuilder [25]
	 * 25  aload_0 [args]
	 * 26  iconst_1
	 * 27  aaload
	 * 28  invokevirtual java.lang.StringBuilder.append(java.lang.String) : java.lang.StringBuilder [25]
	 * 31  pop
	 * 32  return
	 */
	public void testInvokeChain() {
		MethodCompiler compiler = createCompiler("zzz",
			NEW+" java/lang/StringBuilder",
			DUP,
			INVOKESPECIAL+" java/lang/StringBuilder "+MethodCompiler.CONSTRUCTOR_METHOD_NAME+" ()V",
			ASTORE+" 1",
			ALOAD+" 1",
			ALOAD+" 0",
			ARRAYLENGTH,
			INVOKEVIRTUAL+" java/lang/StringBuilder append (I)Ljava/lang/StringBuilder;",
			LDC+" zzz",
			INVOKEVIRTUAL+" java/lang/StringBuilder append (Ljava/lang/String;)Ljava/lang/StringBuilder;",
			ALOAD+" 0",
			ICONST_0,
			AALOAD,
			INVOKEVIRTUAL+" java/lang/StringBuilder append (Ljava/lang/String;)Ljava/lang/StringBuilder;",
			ALOAD+" 0",
			ICONST_1,
			AALOAD,
			INVOKEVIRTUAL+" java/lang/StringBuilder append (Ljava/lang/String;)Ljava/lang/StringBuilder;",
			POP, RETURN);
		compiler.visitEnd();
		assertEquals("var a=new StringBuilder();a['<init>()']();arguments[0]=a;" +
				"arguments[0]['append(I)'](arguments[1].length)" +
				"['append(Ljava/lang/String;)'](\"zzz\")" +
				"['append(Ljava/lang/String;)'](arguments[1][0])" +
				"['append(Ljava/lang/String;)'](arguments[1][1]);return; }; ",
			compiler.getCompilationBuffer().toString().replaceAll("\\s+", " "));
	}
}
