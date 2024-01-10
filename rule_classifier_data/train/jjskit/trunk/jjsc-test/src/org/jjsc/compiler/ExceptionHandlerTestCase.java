package org.jjsc.compiler;

import org.jjsc.utils.Log;
import org.jjsc.utils.Log.LogLevel;


public class ExceptionHandlerTestCase extends MethodCompilerTestCase {
	/**
	 * Test try/catch handler:
	 * <code>
	 *  try {
	 *  	System.out.println("try");
	 *  }
	 *  catch (Exception ex) {
	 *  	System.out.println("ex");
	 *  }
	 * </code>
	 * following bytecode will be produced:
	 *  0  getstatic java.lang.System.out : java.io.PrintStream [16]
	 *  3  ldc <String "try"> [22]
	 *  5  invokevirtual java.io.PrintStream.println(java.lang.String) : void [24]
	 *  8  goto 20
	 * 11  astore_1 [ex]
	 * 12  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 15  ldc <String "ex"> [30]
	 * 17  invokevirtual java.io.PrintStream.println(java.lang.String) : void [24]
	 * 20  return
	 *     Exception Table:
	 *     [pc: 0, pc: 8] -> 11 when : java.lang.Exception
	 */
	public void testTryCatch() {
		MethodCompiler compiler = createCompiler("zzz","()V",new Object[]{
			"L0",
			GETSTATIC+" java/lang/System out java/io/PrintStream",
			LDC+" try",
			INVOKEVIRTUAL+" java/io/PrintStream println (Ljava/lang/String;)V",
			"L8",
			GOTO+" L20",
			"L11",
			ASTORE+" 1",
			GETSTATIC+" java/lang/System out java/io/PrintStream",
			LDC+" ex",
			INVOKEVIRTUAL+" java/io/PrintStream println (Ljava/lang/String;)V",
			"L20",
			RETURN},
		new Object[]{
			"java/lang/Exception L0 L8 L11"
		});
		compiler.visitEnd();
		assertEquals("try{ System.out['println(Ljava/lang/String;)'](\"try\");}" +
				"catch(ex){if(ex instanceof Exception){arguments[0]=ex; " +
				"System.out['println(Ljava/lang/String;)'](\"ex\");}else{throw ex;}}return; }; ", 
				compiler.getCompilationBuffer().toString().replaceAll("\\s+", " "));
	}
	/**
	 * Test try/finally handler
	 * <code>
	 *  try {
	 *  	System.out.println("try");
	 *  } finally {
	 *  	System.out.println("finally");
	 *  }
	 * </code>
	 * following bytecode will be produced (eclipse compiler):
	 *  0  getstatic java.lang.System.out : java.io.PrintStream [16]
	 *  3  ldc <String "try"> [22]
	 *  5  invokevirtual java.io.PrintStream.println(java.lang.String) : void [24]
	 *  8  goto 22
	 * 11  astore_1
	 * 12  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 15  ldc <String "finally"> [30]
	 * 17  invokevirtual java.io.PrintStream.println(java.lang.String) : void [24]
	 * 20  aload_1
	 * 21  athrow
	 * 22  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 25  ldc <String "finally"> [30]
	 * 27  invokevirtual java.io.PrintStream.println(java.lang.String) : void [24]
	 * 30  return
	 *     Exception Table:
	 *     [pc: 0, pc: 11] -> 11 when : any
	 */
	public void testTryFinally() {
		MethodCompiler compiler = createCompiler("zzz","()V",new Object[]{
			"L0",
			GETSTATIC+" java/lang/System out java/io/PrintStream",
			LDC+" try",
			INVOKEVIRTUAL+" java/io/PrintStream println (Ljava/lang/String;)V",
			"L8",
			GOTO+" L22",
			"L11",
			ASTORE+" 1",
			GETSTATIC+" java/lang/System out java/io/PrintStream",
			LDC+" finally",
			INVOKEVIRTUAL+" java/io/PrintStream println (Ljava/lang/String;)V",
			ALOAD+" 1",
			ATHROW,
			"L22",
			GETSTATIC+" java/lang/System out java/io/PrintStream",
			LDC+" finally",
			INVOKEVIRTUAL+" java/io/PrintStream println (Ljava/lang/String;)V",
			RETURN},
		new Object[]{
			" L0 L11 L11"
		});
		compiler.visitEnd();
		assertEquals("try{ System.out['println(Ljava/lang/String;)'](\"try\");}catch(ex){arguments[0]=ex; " +
				"System.out['println(Ljava/lang/String;)'](\"finally\");throw arguments[0];} " +
				"System.out['println(Ljava/lang/String;)'](\"finally\");return; }; ", 
				compiler.getCompilationBuffer().toString().replaceAll("\\s+", " "));
	}
	/**
	 * Test try/finally handler
	 * <code>
	 *  try {
	 *  	System.out.println("try");
	 *  } finally {
	 *  	System.out.println("finally");
	 *  }
	 * </code>
	 * using javac way to produce the code:
	 *  0  getstatic java.lang.System.out : java.io.PrintStream [2]
	 *  3  ldc <String "try"> [3]
	 *  5  invokevirtual java.io.PrintStream.println(java.lang.String) : void [4]
	 *  8  getstatic java.lang.System.out : java.io.PrintStream [2]
	 * 11  ldc <String "finally"> [5]
	 * 13  invokevirtual java.io.PrintStream.println(java.lang.String) : void [4]
	 * 16  goto 30
	 * 19  astore_1
	 * 20  getstatic java.lang.System.out : java.io.PrintStream [2]
	 * 23  ldc <String "finally"> [5]
	 * 25  invokevirtual java.io.PrintStream.println(java.lang.String) : void [4]
	 * 28  aload_1
	 * 29  athrow
	 * 30  return
	 *     Exception Table:
	 *     [pc: 0, pc: 8] -> 19 when : any
	 *     [pc: 19, pc: 20] -> 19 when : any
	 */
	public void testTryFinallyJavac() {
		MethodCompiler compiler = createCompiler("zzz","()V",new Object[]{
			"L0",
			GETSTATIC+" java/lang/System out java/io/PrintStream",
			LDC+" try",
			INVOKEVIRTUAL+" java/io/PrintStream println (Ljava/lang/String;)V",
			"L8",
			GETSTATIC+" java/lang/System out java/io/PrintStream",
			LDC+" finally",
			INVOKEVIRTUAL+" java/io/PrintStream println (Ljava/lang/String;)V",
			GOTO+" L30",
			"L19",
			ASTORE+" 1",
			"L20",
			GETSTATIC+" java/lang/System out java/io/PrintStream",
			LDC+" finally",
			INVOKEVIRTUAL+" java/io/PrintStream println (Ljava/lang/String;)V",
			ALOAD+" 1",
			ATHROW,
			"L30",
			RETURN},
		new Object[]{
			" L0 L8 L19",
			" L19 L20 L19"
		});
		compiler.visitEnd();
		assertEquals("try{ System.out['println(Ljava/lang/String;)'](\"try\");}catch(ex){" +
				"try{arguments[0]=ex;}catch(ex){} System.out['println(Ljava/lang/String;)'](\"finally\");" +
				"throw arguments[0];} System.out['println(Ljava/lang/String;)'](\"finally\");return; }; ", 
				compiler.getCompilationBuffer().toString().replaceAll("\\s+", " "));
	}
	/**
	 * Test JSR instruction compilation:
	 *  0  ldc "Message1"
	 *  1  jsr 7
	 *  2  ldc "Message2"
	 *  3  jsr 7
	 *  4  ldc "Message3"
	 *  5  jsr 7
	 *  6  return
	 *  7  astore_1
	 *  9  getstatic java/lang/System/out Ljava/io/PrintStream;
	 * 10  invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
	 * 11  ret 1
	 */
	public void testJSR() {
		MethodCompiler compiler = createCompiler("zzz","()V",new Object[]{
			LDC+" Message1",
			JSR+" L7",
			LDC+" Message2",
			JSR+" L7",
			LDC+" Message3",
			JSR+" L7",
			RETURN,
			"L7",
			ASTORE+" 1",
			GETSTATIC+" java/lang/System out java/io/PrintStream",
			SWAP,
			INVOKEVIRTUAL+" java/io/PrintStream println (Ljava/lang/String;)V",
			RET+" 1"});
		compiler.visitEnd();
		assertEquals("arguments[0]=undefined; System.out['println(Ljava/lang/String;)'](\"Message1\");" +
				"arguments[0]=undefined; System.out['println(Ljava/lang/String;)'](\"Message2\");" +
				"arguments[0]=undefined; System.out['println(Ljava/lang/String;)'](\"Message3\");return; }; ", 
			compiler.getCompilationBuffer().toString().replaceAll("\\s+", " "));
	}
	/**
	 * Test try/catch/finally handler
	 * <code>
	 *  try {
	 *  	System.out.println("try");
	 *  } catch(RuntimeException ex){
	 *  	ex.printStackTrace();
	 *  } finally {
	 *  	System.out.println("finally");
	 *  }
	 * </code>
	 * will be compiled into following bytecode:
	 *  0  getstatic java.lang.System.out : java.io.PrintStream [16]
	 *  3  ldc <String "try"> [22]
	 *  5  invokevirtual java.io.PrintStream.println(java.lang.String) : void [24]
	 *  8  goto 38
	 * 11  astore_1 [ex]
	 * 12  aload_1 [ex]
	 * 13  invokevirtual java.lang.RuntimeException.printStackTrace() : void [30]
	 * 16  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 19  ldc <String "finally"> [35]
	 * 21  invokevirtual java.io.PrintStream.println(java.lang.String) : void [24]
	 * 24  goto 46
	 * 27  astore_2
	 * 28  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 31  ldc <String "finally"> [35]
	 * 33  invokevirtual java.io.PrintStream.println(java.lang.String) : void [24]
	 * 36  aload_2
	 * 37  athrow
	 * 38  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 41  ldc <String "finally"> [35]
	 * 43  invokevirtual java.io.PrintStream.println(java.lang.String) : void [24]
	 * 46  return
	 *     Exception Table:
	 *     [pc: 0, pc: 8] -> 11 when : java.lang.RuntimeException
	 *     [pc: 0, pc: 16] -> 27 when : any
	 */
	public void testTryCatchFinally() {
		MethodCompiler compiler = createCompiler("zzz","()V",new Object[]{
			"L0",
			GETSTATIC+" java/lang/System out java/io/PrintStream",
			LDC+" try",
			INVOKEVIRTUAL+" java/io/PrintStream println (Ljava/lang/String;)V",
			"L8",
			GOTO+" L38",
			"L11",
			ASTORE+" 1",
			ALOAD+" 1",
			INVOKEVIRTUAL+" java/io/RuntimeException printStackTrace ()V",
			"L16",
			GETSTATIC+" java/lang/System out java/io/PrintStream",
			LDC+" finally",
			INVOKEVIRTUAL+" java/io/PrintStream println (Ljava/lang/String;)V",
			GOTO+" L46",
			"L27",
			ASTORE+" 2",
			GETSTATIC+" java/lang/System out java/io/PrintStream",
			LDC+" finally",
			INVOKEVIRTUAL+" java/io/PrintStream println (Ljava/lang/String;)V",
			ALOAD+" 2",
			ATHROW,
			"L38",
			GETSTATIC+" java/lang/System out java/io/PrintStream",
			LDC+" finally",
			INVOKEVIRTUAL+" java/io/PrintStream println (Ljava/lang/String;)V",
			"L46",
			RETURN},
		new Object[]{
			"java/lang/RuntimeException L0 L8 L11",
			" L0 L16 L27"
		});
		compiler.visitEnd();
		assertEquals("try{try{ System.out['println(Ljava/lang/String;)'](\"try\");}catch(ex){" +
				"if(ex instanceof RuntimeException){arguments[0]=ex;arguments[0]['printStackTrace()']();" +
				"}else{throw ex;}}}catch(ex){arguments[1]=ex; System.out['println(Ljava/lang/String;)']" +
				"(\"finally\");throw arguments[1];} System.out['println(Ljava/lang/String;)'](\"finally\");return; }; ", 
				compiler.getCompilationBuffer().toString().replaceAll("\\s+", " "));
	}
	/**
	 * Test try/catch/finally handler
	 * <code>
	 *  try {
	 *  	System.out.println("try");
	 *  	return;
	 *  } catch(RuntimeException ex){
	 *  	ex.printStackTrace();
	 *  } finally {
	 *  	System.out.println("finally");
	 *  }
	 * </code>
	 * will be compiled into following bytecode:
	 *  0  getstatic java.lang.System.out : java.io.PrintStream [16]
	 *  3  ldc <String "try"> [22]
	 *  5  invokevirtual java.io.PrintStream.println(java.lang.String) : void [24]
	 *  8  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 11  ldc <String "finally"> [30]
	 * 13  invokevirtual java.io.PrintStream.println(java.lang.String) : void [24]
	 * 16  return
	 * 17  astore_1 [ex]
	 * 18  aload_1 [ex]
	 * 19  invokevirtual java.lang.RuntimeException.printStackTrace() : void [32]
	 * 22  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 25  ldc <String "finally"> [30]
	 * 27  invokevirtual java.io.PrintStream.println(java.lang.String) : void [24]
	 * 30  goto 44
	 * 33  astore_2
	 * 34  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 37  ldc <String "finally"> [30]
	 * 39  invokevirtual java.io.PrintStream.println(java.lang.String) : void [24]
	 * 42  aload_2
	 * 43  athrow
	 * 44  return
	 *     Exception Table:
	 *     [pc: 0, pc: 8] -> 17 when : java.lang.RuntimeException
	 *     [pc: 0, pc: 8] -> 33 when : any
	 *     [pc: 17, pc: 22] -> 33 when : any
	 */
	public void testTryReturnCatchFinally() {Log.setDefaultLevel(LogLevel.DEBUG);
		MethodCompiler compiler = createCompiler("zzz","()V",new Object[]{
			"L0",
			GETSTATIC+" java/lang/System out java/io/PrintStream",
			LDC+" try",
			INVOKEVIRTUAL+" java/io/PrintStream println (Ljava/lang/String;)V",
			"L8",
			GETSTATIC+" java/lang/System out java/io/PrintStream",
			LDC+" finally",
			INVOKEVIRTUAL+" java/io/PrintStream println (Ljava/lang/String;)V",
			RETURN,
			"L17",
			ASTORE+" 1",
			ALOAD+" 1",
			INVOKEVIRTUAL+" java/io/RuntimeException printStackTrace ()V",
			"L22",
			GETSTATIC+" java/lang/System out java/io/PrintStream",
			LDC+" finally",
			INVOKEVIRTUAL+" java/io/PrintStream println (Ljava/lang/String;)V",
			GOTO+" L44",
			"L33",
			ASTORE+" 2",
			GETSTATIC+" java/lang/System out java/io/PrintStream",
			LDC+" finally",
			INVOKEVIRTUAL+" java/io/PrintStream println (Ljava/lang/String;)V",
			ALOAD+" 2",
			ATHROW,
			"L44",
			RETURN},
		new Object[]{
			"java/lang/RuntimeException L0 L8 L17",
			" L0 L8 L33",
			" L17 L22 L33"
		});
		compiler.visitEnd();
		assertEquals("try{ System.out['println(Ljava/lang/String;)'](\"try\");}catch(ex){" +
				"if(ex instanceof RuntimeException){try{arguments[0]=ex;arguments[0]['printStackTrace()']();}" +
				"catch(ex){arguments[1]=ex; System.out['println(Ljava/lang/String;)'](\"finally\");throw arguments[1];} " +
				"System.out['println(Ljava/lang/String;)'](\"finally\");return;}else{" +
				"arguments[1]=ex; System.out['println(Ljava/lang/String;)'](\"finally\");throw arguments[1];return;}} " +
				"System.out['println(Ljava/lang/String;)'](\"finally\");return; }; ", 
				compiler.getCompilationBuffer().toString().replaceAll("\\s+", " "));
	}
	/**
	 * Test try/catch nested in the catch block of other try/catch
	 * <code>
	 *  try {
	 *  	System.out.println("try");
	 *  } catch (Exception ex) {
	 *  	System.out.println("ex");
	 *  	try {
	 *  		ex.printStackTrace();
	 *  	} catch (Error e) {
	 *  		System.out.println("error");
	 *  	}
	 *  	System.out.println("ey");
	 *  }
	 * </code>
	 * will be compiled into following bytecode:
	 *  0  getstatic java.lang.System.out : java.io.PrintStream [16]
	 *  3  ldc <String "try"> [22]
	 *  5  invokevirtual java.io.PrintStream.println(java.lang.String) : void [24]
	 *  8  goto 44
	 * 11  astore_1 [ex]
	 * 12  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 15  ldc <String "ex"> [30]
	 * 17  invokevirtual java.io.PrintStream.println(java.lang.String) : void [24]
	 * 20  aload_1 [ex]
	 * 21  invokevirtual java.lang.Exception.printStackTrace() : void [32]
	 * 24  goto 36
	 * 27  astore_2 [e]
	 * 28  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 31  ldc <String "error"> [37]
	 * 33  invokevirtual java.io.PrintStream.println(java.lang.String) : void [24]
	 * 36  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 39  ldc <String "ey"> [39]
	 * 41  invokevirtual java.io.PrintStream.println(java.lang.String) : void [24]
	 * 44  return
	 *     Exception Table:
	 *     [pc: 0, pc: 8] -> 11 when : java.lang.Exception
	 *     [pc: 20, pc: 24] -> 27 when : java.lang.Error
	 */
	public void testTryInCatch() {
		MethodCompiler compiler = createCompiler("zzz","()V",new Object[]{
			"L0",
			GETSTATIC+" java/lang/System out java/io/PrintStream",
			LDC+" try",
			INVOKEVIRTUAL+" java/io/PrintStream println (Ljava/lang/String;)V",
			"L8",
			GOTO+" L44",
			"L11",
			ASTORE+" 1",
			GETSTATIC+" java/lang/System out java/io/PrintStream",
			LDC+" ex",
			INVOKEVIRTUAL+" java/io/PrintStream println (Ljava/lang/String;)V",
			"L20",
			ALOAD+" 1",
			INVOKEVIRTUAL+" java/io/RuntimeException printStackTrace ()V",
			"L24",
			GOTO+ " L36",
			"L27",
			ASTORE+" 2",
			GETSTATIC+" java/lang/System out java/io/PrintStream",
			LDC+" error",
			INVOKEVIRTUAL+" java/io/PrintStream println (Ljava/lang/String;)V",
			"L36",
			GETSTATIC+" java/lang/System out java/io/PrintStream",
			LDC+" ey",
			INVOKEVIRTUAL+" java/io/PrintStream println (Ljava/lang/String;)V",
			"L44",
			RETURN},
		new Object[]{
			"java/lang/Exception L0 L8 L11",
			"java/lang/Error L20 L24 L27"
		});
		compiler.visitEnd();
		assertEquals("try{ System.out['println(Ljava/lang/String;)'](\"try\");}catch(ex){" +
				"if(ex instanceof Exception){arguments[0]=ex; System.out['println(Ljava/lang/String;)'](\"ex\");" +
				"try{arguments[0]['printStackTrace()']();}catch(ex){if(ex instanceof Error){arguments[1]=ex; " +
				"System.out['println(Ljava/lang/String;)'](\"error\");}else{throw ex;}} " +
				"System.out['println(Ljava/lang/String;)'](\"ey\");}else{throw ex;}}return; }; ", 
				compiler.getCompilationBuffer().toString().replaceAll("\\s+", " "));
	}
	/**
	 * Test try/catch nested in the catch block of other try/catch with multiple catch statements
	 * <code>
	 *  try {
	 *  	System.out.println("try");
	 *  } catch (Exception ex) {
	 *  	try {
	 *  		ex.printStackTrace();
	 *  	} catch (Error e) {
	 *  		System.out.println("error");
	 *  	}
	 *  }
	 *  catch(Throwable th) {
	 *  	System.out.println("th");
	 *  }
	 *  finally {
	 *  	System.out.println("finally");
	 *  }
	 * </code>
	 * will be compiled into following bytecode:
	 *  0  getstatic java.lang.System.out : java.io.PrintStream [16]
	 *  3  ldc <String "try"> [22]
	 *  5  invokevirtual java.io.PrintStream.println(java.lang.String) : void [24]
	 *  8  goto 70
	 * 11  astore_1 [ex]
	 * 12  aload_1 [ex]
	 * 13  invokevirtual java.lang.Exception.printStackTrace() : void [30]
	 * 16  goto 28
	 * 19  astore_2 [e]
	 * 20  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 23  ldc <String "error"> [35]
	 * 25  invokevirtual java.io.PrintStream.println(java.lang.String) : void [24]
	 * 28  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 31  ldc <String "finally"> [37]
	 * 33  invokevirtual java.io.PrintStream.println(java.lang.String) : void [24]
	 * 36  goto 78
	 * 39  astore_1 [th]
	 * 40  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 43  ldc <String "th"> [39]
	 * 45  invokevirtual java.io.PrintStream.println(java.lang.String) : void [24]
	 * 48  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 51  ldc <String "finally"> [37]
	 * 53  invokevirtual java.io.PrintStream.println(java.lang.String) : void [24]
	 * 56  goto 78
	 * 59  astore_3
	 * 60  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 63  ldc <String "finally"> [37]
	 * 65  invokevirtual java.io.PrintStream.println(java.lang.String) : void [24]
	 * 68  aload_3
	 * 69  athrow
	 * 70  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 73  ldc <String "finally"> [37]
	 * 75  invokevirtual java.io.PrintStream.println(java.lang.String) : void [24]
	 * 78  return
	 *     Exception Table:
	 *     [pc: 0, pc: 8] -> 11 when : java.lang.Exception
	 *     [pc: 12, pc: 16] -> 19 when : java.lang.Error
	 *     [pc: 0, pc: 8] -> 39 when : java.lang.Throwable
	 *     [pc: 0, pc: 28] -> 59 when : any
	 *     [pc: 39, pc: 48] -> 59 when : any
	 */
	public void testTryInMultiCatch() {
		MethodCompiler compiler = createCompiler("zzz","()V",new Object[]{
   			"L0",
   			GETSTATIC+" java/lang/System out java/io/PrintStream",
   			LDC+" try",
   			INVOKEVIRTUAL+" java/io/PrintStream println (Ljava/lang/String;)V",
   			"L8",
   			GOTO+" L70",
   			"L11",
   			ASTORE+" 1",
   			"L12",
   			ALOAD+" 1",
   			INVOKEVIRTUAL+" java/io/Exception printStackTrace ()V",
   			"L16",
   			GOTO+" L28",
   			"L19",
   			ASTORE+" 2",
   			GETSTATIC+" java/lang/System out java/io/PrintStream",
   			LDC+" error",
   			INVOKEVIRTUAL+" java/io/PrintStream println (Ljava/lang/String;)V",
   			"L28",
   			GETSTATIC+" java/lang/System out java/io/PrintStream",
   			LDC+" finally",
   			INVOKEVIRTUAL+" java/io/PrintStream println (Ljava/lang/String;)V",
   			"L36",
   			GOTO+" L78",
   			"L39",
   			ASTORE+" 1",
   			GETSTATIC+" java/lang/System out java/io/PrintStream",
   			LDC+" th",
   			INVOKEVIRTUAL+" java/io/PrintStream println (Ljava/lang/String;)V",
   			"L48",
   			GETSTATIC+" java/lang/System out java/io/PrintStream",
   			LDC+" finally",
   			INVOKEVIRTUAL+" java/io/PrintStream println (Ljava/lang/String;)V",
   			"L56",
   			GOTO+" L78",
   			"L59",
   			ASTORE+" 3",
   			GETSTATIC+" java/lang/System out java/io/PrintStream",
   			LDC+" finally",
   			INVOKEVIRTUAL+" java/io/PrintStream println (Ljava/lang/String;)V",
   			ALOAD+" 3",
   			ATHROW,
   			"L70",
   			GETSTATIC+" java/lang/System out java/io/PrintStream",
   			LDC+" finally",
   			INVOKEVIRTUAL+" java/io/PrintStream println (Ljava/lang/String;)V",
   			"L78",
   			RETURN},
   		new Object[]{
   			"java/lang/Exception L0 L8 L11",
   			"java/lang/Error L12 L16 L19",
   			"java/lang/Throwable L0 L8 L39",
   			" L0 L28 L59",
   			" L39 L48 L59"
   		});
        compiler.visitEnd();
		assertEquals("L22:{try{try{ System.out['println(Ljava/lang/String;)'](\"try\");}catch(ex){" +
				"if(ex instanceof Exception){arguments[0]=ex;try{arguments[0]['printStackTrace()']();}catch(ex){" +
				"if(ex instanceof Error){arguments[1]=ex; System.out['println(Ljava/lang/String;)'](\"error\");}" +
				"else{throw ex;}} System.out['println(Ljava/lang/String;)'](\"finally\");break L22;}" +
				"else if(ex instanceof Throwable){arguments[0]=ex; System.out['println(Ljava/lang/String;)'](\"th\"); " +
				"System.out['println(Ljava/lang/String;)'](\"finally\");break L22;}else{throw ex;}}}catch(ex){" +
				"arguments[2]=ex; System.out['println(Ljava/lang/String;)'](\"finally\");throw arguments[2];} " +
				"System.out['println(Ljava/lang/String;)'](\"finally\");}return; }; ", 
				compiler.getCompilationBuffer().toString().replaceAll("\\s+", " "));
	}
	/**
	 * Most complex case: 
	 * <code>
	 *  try {
	 *  	try {
	 *  		System.out.println("try");
	 *  	}
	 *  	catch(RuntimeException runtime){
	 *  		System.out.println("runtime");
	 *  	}
	 *  	finally {
	 *  		System.out.println("finally1");
	 *  	}
	 *  }
	 *  catch (Exception ex) {
	 *  	try {
	 *  		ex.printStackTrace();
	 *  	} catch (Error e) {
	 *  		System.out.println("error");
	 *  	}
	 *  }
	 *  catch(Throwable th) {
	 *  	System.out.println("th");
	 *  }
	 *  finally {
	 *  	System.out.println("finally2");
	 *  }
	 * </code>
	 * 
	 */
	public void testNested() {
       MethodCompiler compiler = createCompiler("zzz","()V",new Object[]{
   			"L0",
   			GETSTATIC+" java/lang/System out java/io/PrintStream",
   			LDC+" try",
   			INVOKEVIRTUAL+" java/io/PrintStream println (Ljava/lang/String;)V",
   			"L8",
   			GOTO+ " L42",
   			"L11",
   			ASTORE+" 1",
   			GETSTATIC+" java/lang/System out java/io/PrintStream",
   			LDC+" runtime",
   			INVOKEVIRTUAL+" java/io/PrintStream println (Ljava/lang/String;)V",
   			"L20",
   			GETSTATIC+" java/lang/System out java/io/PrintStream",
   			LDC+" finally1",
   			INVOKEVIRTUAL+" java/io/PrintStream println (Ljava/lang/String;)V",
   			"L28",
   			GOTO+" L112",
   			"L31",
   			ASTORE+" 2",
   			GETSTATIC+" java/lang/System out java/io/PrintStream",
   			LDC+" finally1",
   			INVOKEVIRTUAL+" java/io/PrintStream println (Ljava/lang/String;)V",
   			ALOAD+" 2",
   			ATHROW,
   			"L42",
   			GETSTATIC+" java/lang/System out java/io/PrintStream",
   			LDC+" finally1",
   			INVOKEVIRTUAL+" java/io/PrintStream println (Ljava/lang/String;)V",
   			"L50",
   			GOTO+" L112",
   			"L53",
   			ASTORE+" 1",
   			"L54",
   			ALOAD+" 1",
   			INVOKEVIRTUAL+" java/io/Exception printStackTrace ()V",
   			"L58",
   			GOTO+" L70",
   			"L61",
   			ASTORE+" 2",
   			GETSTATIC+" java/lang/System out java/io/PrintStream",
   			LDC+" error",
   			INVOKEVIRTUAL+" java/io/PrintStream println (Ljava/lang/String;)V",
   			"L70",
   			GETSTATIC+" java/lang/System out java/io/PrintStream",
   			LDC+" finally2",
   			INVOKEVIRTUAL+" java/io/PrintStream println (Ljava/lang/String;)V",
   			"L78",
   			GOTO+" L120",
   			"L81",
   			ASTORE+" 1",
   			GETSTATIC+" java/lang/System out java/io/PrintStream",
   			LDC+" th",
   			INVOKEVIRTUAL+" java/io/PrintStream println (Ljava/lang/String;)V",
   			"L90",
   			GETSTATIC+" java/lang/System out java/io/PrintStream",
   			LDC+" finally2",
   			INVOKEVIRTUAL+" java/io/PrintStream println (Ljava/lang/String;)V",
   			"L98",
   			GOTO+" L120",
   			"L101",
   			ASTORE+" 3",
   			GETSTATIC+" java/lang/System out java/io/PrintStream",
   			LDC+" finally2",
   			INVOKEVIRTUAL+" java/io/PrintStream println (Ljava/lang/String;)V",
   			ALOAD+" 3",
   			ATHROW,
   			"L112",
   			GETSTATIC+" java/lang/System out java/io/PrintStream",
   			LDC+" finally2",
   			INVOKEVIRTUAL+" java/io/PrintStream println (Ljava/lang/String;)V",
   			"L120",
   			RETURN},
   		new Object[]{
   			"java/lang/RuntimeException L0 L8 L11",
   			" L0 L20 L31",
   			"java/lang/Exception L0 L50 L53",
   			"java/lang/Error L54 L58 L61",
   			"java/lang/Throwable L0 L50 L81",
   			" L0 L70 L101",
   			" L81 L90 L101"
   		});
        compiler.visitEnd();
		assertEquals("L32:{try{try{try{try{ System.out['println(Ljava/lang/String;)'](\"try\");}catch(ex){" +
				"if(ex instanceof RuntimeException){arguments[0]=ex; System.out['println(Ljava/lang/String;)'](\"runtime\");}" +
				"else{throw ex;}}}catch(ex){arguments[1]=ex; System.out['println(Ljava/lang/String;)'](\"finally1\");" +
				"throw arguments[1];} System.out['println(Ljava/lang/String;)'](\"finally1\");}catch(ex){" +
				"if(ex instanceof Exception){arguments[0]=ex;try{arguments[0]['printStackTrace()']();}catch(ex){" +
				"if(ex instanceof Error){arguments[1]=ex; System.out['println(Ljava/lang/String;)'](\"error\");}" +
				"else{throw ex;}} System.out['println(Ljava/lang/String;)'](\"finally2\");break L32;}else if" +
				"(ex instanceof Throwable){arguments[0]=ex; System.out['println(Ljava/lang/String;)'](\"th\"); " +
				"System.out['println(Ljava/lang/String;)'](\"finally2\");break L32;}else{throw ex;}}}catch(ex){" +
				"arguments[2]=ex; System.out['println(Ljava/lang/String;)'](\"finally2\");throw arguments[2];} " +
				"System.out['println(Ljava/lang/String;)'](\"finally2\");}return; }; ", 
				compiler.getCompilationBuffer().toString().replaceAll("\\s+", " "));
	}
}
