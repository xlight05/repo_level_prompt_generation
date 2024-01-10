package org.jjsc.compiler;


/**
 * TODO:
 * - enums
 * - class compilation alias & property access strategy annotations.
 * - native modifier
 * - verify all opcodes for absent ones
 * - local variables table and type casts
 * - super and this call
 * - fields
 * - private members overriding and hidding 
 * @author alex.bereznevatiy@gmail.com
 */
public class MiscellaneousTestCase extends MethodCompilerTestCase {
	/**
	 * Case + return integration test:
	 * <code>
	 *  switch(args.length) {
	 *  case 0:
	 *  case 1:
	 *  	return;
	 *  case 2:
	 *  case 4:
	 *  	System.out.println(args[0]);
	 *  case 3:
	 *  	System.out.println(args[1]);
	 *  	return;
	 *  }
	 * </code>
	 * will be compiled into following bytecode:
	 *  0  aload_0 [args]
	 *  1  arraylength
	 *  2  tableswitch default: 56
	 *     case 0: 36
	 *     case 1: 36
	 *     case 2: 37
	 *     case 3: 46
	 *     case 4: 37
	 * 36  return
	 * 37  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 40  aload_0 [args]
	 * 41  iconst_0
	 * 42  aaload
	 * 43  invokevirtual java.io.PrintStream.println(java.lang.String) : void [22]
	 * 46  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 49  aload_0 [args]
	 * 50  iconst_1
	 * 51  aaload
	 * 52  invokevirtual java.io.PrintStream.println(java.lang.String) : void [22]
	 * 55  return
	 * 56  return
	 */
	public void testCaseReturn() {
		MethodCompiler compiler = createCompiler("zzz","()V",new Object[]{
			ALOAD+ " 0",
			ARRAYLENGTH,
			TABLESWITCH+" 0 4 L56 L36 L36 L37 L46 L37",
			"L36",
			RETURN,
			"L37",
			GETSTATIC+" java/lang/System out java/io/PrintStream",
			ALOAD+" 0",
			ICONST_0,
			AALOAD,
			INVOKEVIRTUAL+" java/io/PrintStream println (Ljava/lang/String;)V",
			"L46",
			GETSTATIC+" java/lang/System out java/io/PrintStream",
			ALOAD+" 0",
			ICONST_1,
			AALOAD,
			INVOKEVIRTUAL+" java/io/PrintStream println (Ljava/lang/String;)V",
			RETURN,
			"L56",
			RETURN});
		compiler.visitEnd();
		assertEquals("switch(arguments[0].length){ case 0: case 1: return;case 2: case 4: " +
				"System.out['println(Ljava/lang/String;)'](arguments[0][0]);case 3: " +
				"System.out['println(Ljava/lang/String;)'](arguments[0][1]);return;default:return;} }; ", 
				compiler.getCompilationBuffer().toString().replaceAll("\\s+", " "));
	}
	/**
	 * Case + while + branching statements integration test:
	 * <code>
	 *  A : {
	 *  	System.out.println();
	 *  	do {
	 *  		switch(args.length) {
	 *  		case 0:
	 *  			System.out.println();
	 *  			continue;
	 *  		case 1:
	 *  			System.out.println(args[0]);
	 *  		case 2:
	 *  			do {
	 *  				System.out.println();
	 *  			} while (System.out.checkError()); 
	 *  			break A;	
	 *  		}
	 *  	} while (System.out != null);
	 *  	System.out.println();
	 *  }
	 *  System.out.println();
	 * </code>
	 * will be compiled into following bytecode:
	 *  0  getstatic java.lang.System.out : java.io.PrintStream [16]
	 *  3  invokevirtual java.io.PrintStream.println() : void [22]
	 *  6  aload_0 [args]
	 *  7  arraylength
	 *  8  tableswitch default: 72
	 *     case 0: 36
	 *     case 1: 45
	 *     case 2: 54
	 * 36  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 39  invokevirtual java.io.PrintStream.println() : void [22]
	 * 42  goto 72
	 * 45  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 48  aload_0 [args]
	 * 49  iconst_0
	 * 50  aaload
	 * 51  invokevirtual java.io.PrintStream.println(java.lang.String) : void [27]
	 * 54  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 57  invokevirtual java.io.PrintStream.println() : void [22]
	 * 60  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 63  invokevirtual java.io.PrintStream.checkError() : boolean [30]
	 * 66  ifne 54
	 * 69  goto 84
	 * 72  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 75  ifnonnull 6
	 * 78  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 81  invokevirtual java.io.PrintStream.println() : void [22]
	 * 84  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 87  invokevirtual java.io.PrintStream.println() : void [22]
	 * 90  return
	 */
	public void testCaseAndBranchingStatements() {
		MethodCompiler compiler = createCompiler("zzz","()V",new Object[]{
			GETSTATIC+" java/lang/System out java/io/PrintStream",
			INVOKEVIRTUAL+" java/io/PrintStream println ()V",
			"L6",
			ALOAD+ " 0",
			ARRAYLENGTH,
			TABLESWITCH+" 0 2 L72 L36 L45 L54",
			"L36",
			GETSTATIC+" java/lang/System out java/io/PrintStream",
			INVOKEVIRTUAL+" java/io/PrintStream println ()V",
			GOTO+" L72",
			"L45",
			GETSTATIC+" java/lang/System out java/io/PrintStream",
			ALOAD+" 0",
			ICONST_0,
			AALOAD,
			INVOKEVIRTUAL+" java/io/PrintStream println (Ljava/lang/String;)V",
			"L54",
			GETSTATIC+" java/lang/System out java/io/PrintStream",
			INVOKEVIRTUAL+" java/io/PrintStream println ()V",
			GETSTATIC+" java/lang/System out java/io/PrintStream",
			INVOKEVIRTUAL+" java/io/PrintStream checkError ()Z",
			IFNE+" L54",
			GOTO+" L84",
			"L72",
			GETSTATIC+" java/lang/System out java/io/PrintStream",
			IFNONNULL+" L6",
			GETSTATIC+" java/lang/System out java/io/PrintStream",
			INVOKEVIRTUAL+" java/io/PrintStream println ()V",
			"L84",
			GETSTATIC+" java/lang/System out java/io/PrintStream",
			INVOKEVIRTUAL+" java/io/PrintStream println ()V",
			RETURN});
		compiler.visitEnd();
		assertEquals("L11:{ System.out['println()']();do{switch(arguments[0].length){ case 0: " +
				"System.out['println()']();break; case 1: System.out['println(Ljava/lang/String;)']" +
				"(arguments[0][0]);case 2: do{ System.out['println()'](); }while(System.out" +
				"['checkError()']());break L11;default:}}while(System.out!==null); System.out" +
				"['println()']();} System.out['println()']();return; }; ", 
				compiler.getCompilationBuffer().toString().replaceAll("\\s+", " "));
	}
	/**
	 * Case + while + branching statements integration test (two-levels cycle) :
	 * <code>
	 *  A : {
	 *  	System.out.println();
	 *  	B: while (System.out.checkError()) {
	 *  		do {
	 *  			System.out.println();
	 *  			switch(args.length) {
	 *  			case 0:
	 *  				System.out.println();
	 *  				continue B;
	 *  			case 1:
	 *  				System.out.println();
	 *  			case 2: 
	 *  				break A;
	 *  			}
	 *  		} while (System.out != null);
	 *  		System.out.println();
	 *  	}
	 *  	System.out.println();
	 *  }
	 *  System.out.println();
	 * </code>
	 * will be compiled into following bytecode:
	 *  0  getstatic java.lang.System.out : java.io.PrintStream [16]
	 *  3  invokevirtual java.io.PrintStream.println() : void [22]
	 *  6  goto 74
	 *  9  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 12  invokevirtual java.io.PrintStream.println() : void [22]
	 * 15  aload_0 [args]
	 * 16  arraylength
	 * 17  tableswitch default: 62
	 *     case 0: 44
	 *     case 1: 53
	 *     case 2: 59
	 * 44  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 47  invokevirtual java.io.PrintStream.println() : void [22]
	 * 50  goto 74
	 * 53  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 56  invokevirtual java.io.PrintStream.println() : void [22]
	 * 59  goto 89
	 * 62  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 65  ifnonnull 9
	 * 68  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 71  invokevirtual java.io.PrintStream.println() : void [22]
	 * 74  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 77  invokevirtual java.io.PrintStream.checkError() : boolean [27]
	 * 80  ifne 9
	 * 83  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 86  invokevirtual java.io.PrintStream.println() : void [22]
	 * 89  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 92  invokevirtual java.io.PrintStream.println() : void [22]
	 * 95  return
	 */
	public void testCaseAndBranchingStatementsTwoCycles() {
		MethodCompiler compiler = createCompiler("zzz","()V",new Object[]{
			GETSTATIC+" java/lang/System out java/io/PrintStream",
			INVOKEVIRTUAL+" java/io/PrintStream println ()V",
			GOTO+" L74",
			"L9",
			GETSTATIC+" java/lang/System out java/io/PrintStream",
			INVOKEVIRTUAL+" java/io/PrintStream println ()V",
			ALOAD+ " 0",
			ARRAYLENGTH,
			TABLESWITCH+" 0 2 L62 L44 L53 L59",
			"L44",
			GETSTATIC+" java/lang/System out java/io/PrintStream",
			INVOKEVIRTUAL+" java/io/PrintStream println ()V",
			GOTO+" L74",
			"L53",
			GETSTATIC+" java/lang/System out java/io/PrintStream",
			INVOKEVIRTUAL+" java/io/PrintStream println ()V",
			"L59",
			GOTO+" L89",
			"L62",
			GETSTATIC+" java/lang/System out java/io/PrintStream",
			IFNONNULL+" L9",
			GETSTATIC+" java/lang/System out java/io/PrintStream",
			INVOKEVIRTUAL+" java/io/PrintStream println ()V",
			"L74",
			GETSTATIC+" java/lang/System out java/io/PrintStream",
			INVOKEVIRTUAL+" java/io/PrintStream checkError ()Z",
			IFNE+" L9",
			GETSTATIC+" java/lang/System out java/io/PrintStream",
			INVOKEVIRTUAL+" java/io/PrintStream println ()V",
			"L89",
			GETSTATIC+" java/lang/System out java/io/PrintStream",
			INVOKEVIRTUAL+" java/io/PrintStream println ()V",
			RETURN});
		compiler.visitEnd();
		assertEquals("L13:{ System.out['println()'](); L11:while(System.out['checkError()']()){" +
				"do{ System.out['println()']();switch(arguments[0].length){ case 0: System.out['println()']();" +
				"continue L11;case 1: System.out['println()']();case 2: break L13;default:}}while(System.out!==null); " +
				"System.out['println()'](); } System.out['println()']();} System.out['println()']();return; }; ", 
				compiler.getCompilationBuffer().toString().replaceAll("\\s+", " "));
	}
	/**
	 * Case + while integration test:
	 * <code>
	 *  while (args.length != 0) {
	 *  	switch(args.length) {
	 *  	case 0:
	 *  		return;
	 *  	case 1:
	 *  		System.out.println(args[0]);
	 *  	case 2:
	 *  		while (System.out.checkError()) {
	 *  			System.out.println(args[1]);
	 *  		}
	 *  	default:
	 *  		return;	
	 *  	}
	 *  }
	 * </code>
	 *  0  aload_0 [args]
	 *  1  arraylength
	 *  2  ifeq 64
	 *  5  aload_0 [args]
	 *  6  arraylength
	 *  7  tableswitch default: 63
	 *     case 0: 32
	 *     case 1: 33
	 *     case 2: 42
	 * 32  return
	 * 33  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 36  aload_0 [args]
	 * 37  iconst_0
	 * 38  aaload
	 * 39  invokevirtual java.io.PrintStream.println(java.lang.String) : void [22]
	 * 42  goto 54
	 * 45  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 48  aload_0 [args]
	 * 49  iconst_1
	 * 50  aaload
	 * 51  invokevirtual java.io.PrintStream.println(java.lang.String) : void [22]
	 * 54  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 57  invokevirtual java.io.PrintStream.checkError() : boolean [28]
	 * 60  ifne 45
	 * 63  return
	 * 64  return
	 */
	public void testCaseWhile () {
		MethodCompiler compiler = createCompiler("zzz","()V",new Object[]{
			ALOAD+ " 0",
			ARRAYLENGTH,
			IFEQ+ " L64",
			ALOAD+ " 0",
			ARRAYLENGTH,
			TABLESWITCH+" 0 2 L63 L32 L33 L42",
			"L32",
			RETURN,
			"L33",
			GETSTATIC+" java/lang/System out java/io/PrintStream",
			ALOAD+" 0",
			ICONST_0,
			AALOAD,
			INVOKEVIRTUAL+" java/io/PrintStream println (Ljava/lang/String;)V",
			"L42",
			GOTO+" L54",
			"L45",
			GETSTATIC+" java/lang/System out java/io/PrintStream",
			ALOAD+" 0",
			ICONST_1,
			AALOAD,
			INVOKEVIRTUAL+" java/io/PrintStream println (Ljava/lang/String;)V",
			"L54",
			GETSTATIC+" java/lang/System out java/io/PrintStream",
			INVOKEVIRTUAL+" java/io/PrintStream checkError ()Z",
			IFNE+ " L45",
			"L63",
			RETURN,
			"L64",
			RETURN});
		compiler.visitEnd();
		assertEquals("if(!(!arguments[0].length)){switch(arguments[0].length){ case 0: return;case 1: " +
				"System.out['println(Ljava/lang/String;)'](arguments[0][0]);case 2: while(System.out['checkError()']())" +
				"{ System.out['println(Ljava/lang/String;)'](arguments[0][1]); }default:return;return;}}return; }; ", 
				compiler.getCompilationBuffer().toString().replaceAll("\\s+", " "));
	}
	/**
	 * switch/case with try/catch inside:
	 * <code>
	 *  switch (args.length) {
	 *  case 0:
	 *  	System.out.println();
	 *  	break;
	 *  case 1:
	 *  	try {
	 *  		System.out.println("try");
	 *  	} catch (RuntimeException ex) {
	 *  		ex.printStackTrace();
	 *  	}
	 *  	return;
	 *  default:
	 *  	System.out.println("ret");
	 *  }
	 * </code>
	 * will produce following bytecode:
	 *  0  aload_0 [args]
	 *  1  arraylength
	 *  2  tableswitch default: 50
	 *     case 0: 24
	 *     case 1: 33
	 * 24  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 27  invokevirtual java.io.PrintStream.println() : void [22]
	 * 30  goto 58
	 * 33  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 36  ldc <String "try"> [27]
	 * 38  invokevirtual java.io.PrintStream.println(java.lang.String) : void [29]
	 * 41  goto 49
	 * 44  astore_1 [ex]
	 * 45  aload_1 [ex]
	 * 46  invokevirtual java.lang.RuntimeException.printStackTrace() : void [32]
	 * 49  return
	 * 50  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 53  ldc <String "ret"> [37]
	 * 55  invokevirtual java.io.PrintStream.println(java.lang.String) : void [29]
	 * 58  return
	 *     Exception Table:
	 *     [pc: 33, pc: 41] -> 44 when : java.lang.RuntimeException
	 */
	public void testCaseTryCatch() {
		MethodCompiler compiler = createCompiler("zzz","()V",new Object[]{
			ALOAD+ " 0",
			ARRAYLENGTH,
			TABLESWITCH+" 0 1 L50 L24 L33",
			"L24",
			GETSTATIC+" java/lang/System out java/io/PrintStream",
			INVOKEVIRTUAL+" java/io/PrintStream println ()V",
			GOTO+" L58",
			"L33",
			GETSTATIC+" java/lang/System out java/io/PrintStream",
			LDC+" try",
			INVOKEVIRTUAL+" java/io/PrintStream println (Ljava/lang/String;)V",
			"L41",
			GOTO+" L49",
			"L44",
			ASTORE+" 1",
			ALOAD+" 1",
			INVOKEVIRTUAL+" java/io/Exception printStackTrace ()V",
			"L49",
			RETURN,
			"L50",
			GETSTATIC+" java/lang/System out java/io/PrintStream",
			LDC+" ret",
			INVOKEVIRTUAL+" java/io/PrintStream println (Ljava/lang/String;)V",
			"L58",
			RETURN},
			new Object[]{
				"java/lang/RuntimeException L33 L41 L44"
		});
		compiler.visitEnd();
		assertEquals("switch(arguments[0].length){ case 0: System.out['println()']();break; case 1: try{ " +
				"System.out['println(Ljava/lang/String;)'](\"try\");}catch(ex){if(ex instanceof RuntimeException){" +
				"arguments[1]=ex;arguments[1]['printStackTrace()']();}else{throw ex;}}return;default: " +
				"System.out['println(Ljava/lang/String;)'](\"ret\");}return; }; ", 
				compiler.getCompilationBuffer().toString().replaceAll("\\s+", " "));
	}
	/**
	 * switch/case with try/catch/finally inside:
	 * <code>
	 *  switch(args.length) {
	 *  case 0:
	 *  	try {
	 *  		System.out.println();
	 *  		break;
	 *  	} catch(RuntimeException ex) {
	 *  		ex.printStackTrace();
	 *  	} finally {
	 *  		System.out.println("finally");
	 *  	}
	 *  case 1:
	 *  	try {
	 *  		System.out.println();
	 *  	} catch(RuntimeException ex) {
	 *  		ex.printStackTrace();
	 *  	}
	 *  case 2:
	 *  	System.out.println();
	 *  }
	 * </code>
	 *  0  aload_0 [args]
	 *  1  arraylength
	 *  2  tableswitch default: 92
	 *     case 0: 28
	 *     case 1: 72
	 *     case 2: 86
	 * 28  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 31  invokevirtual java.io.PrintStream.println() : void [22]
	 * 34  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 37  ldc <String "finally"> [27]
	 * 39  invokevirtual java.io.PrintStream.println(java.lang.String) : void [29]
	 * 42  goto 92
	 * 45  astore_1 [ex]
	 * 46  aload_1 [ex]
	 * 47  invokevirtual java.lang.RuntimeException.printStackTrace() : void [32]
	 * 50  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 53  ldc <String "finally"> [27]
	 * 55  invokevirtual java.io.PrintStream.println(java.lang.String) : void [29]
	 * 58  goto 72
	 * 61  astore_2
	 * 62  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 65  ldc <String "finally"> [27]
	 * 67  invokevirtual java.io.PrintStream.println(java.lang.String) : void [29]
	 * 70  aload_2
	 * 71  athrow
	 * 72  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 75  invokevirtual java.io.PrintStream.println() : void [22]
	 * 78  goto 86
	 * 81  astore_1 [ex]
	 * 82  aload_1 [ex]
	 * 83  invokevirtual java.lang.RuntimeException.printStackTrace() : void [32]
	 * 86  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 89  invokevirtual java.io.PrintStream.println() : void [22]
	 * 92  return
	 *     Exception Table:
	 *     [pc: 28, pc: 34] -> 45 when : java.lang.RuntimeException
	 *     [pc: 28, pc: 34] -> 61 when : any
	 *     [pc: 45, pc: 50] -> 61 when : any
	 *     [pc: 72, pc: 78] -> 81 when : java.lang.RuntimeException 
	 */
	public void testCaseTryCatchFinally () {
		MethodCompiler compiler = createCompiler("zzz","()V",new Object[]{
			ALOAD+ " 0",
			ARRAYLENGTH,
			TABLESWITCH+" 0 2 L92 L28 L72 L86",
			"L28",
			GETSTATIC+" java/lang/System out java/io/PrintStream",
			INVOKEVIRTUAL+" java/io/PrintStream println ()V",
			"L34",
			GETSTATIC+" java/lang/System out java/io/PrintStream",
			LDC+" finally",
			INVOKEVIRTUAL+" java/io/PrintStream println (Ljava/lang/String;)V",
			GOTO+" L92",
			"L45",
			ASTORE+" 1",                    
			ALOAD+" 1",
			INVOKEVIRTUAL+" java/io/Exception printStackTrace ()V",
			"L50",
			GETSTATIC+" java/lang/System out java/io/PrintStream",
			LDC+" finally",
			INVOKEVIRTUAL+" java/io/PrintStream println (Ljava/lang/String;)V",
			GOTO+" L72",
			"L61",
			ASTORE+" 2",
			GETSTATIC+" java/lang/System out java/io/PrintStream",
			LDC+" finally",
			INVOKEVIRTUAL+" java/io/PrintStream println (Ljava/lang/String;)V",
			ALOAD+" 2",
			ATHROW,
			"L72",
			GETSTATIC+" java/lang/System out java/io/PrintStream",
			INVOKEVIRTUAL+" java/io/PrintStream println ()V",
			"L78",
			GOTO+" L86",
			"L81",
			ASTORE+" 1",
			ALOAD+" 1",
			INVOKEVIRTUAL+" java/io/Exception printStackTrace ()V",
			"L86",
			GETSTATIC+" java/lang/System out java/io/PrintStream",
			INVOKEVIRTUAL+" java/io/PrintStream println ()V",
			"L92",
			RETURN}, new Object[]{
				"java/lang/RuntimeException L28 L34 L45",
				" L28 L34 L61",
				" L45 L50 L61",
				"java/lang/RuntimeException L72 L78 L81"
		}); 
		compiler.visitEnd();
		assertEquals("switch(arguments[0].length){ case 0: L12:{try{ System.out['println()']();}catch(ex){" +
				"if(ex instanceof RuntimeException){try{arguments[1]=ex;arguments[1]['printStackTrace()']();}catch(ex){" +
				"arguments[2]=ex; System.out['println(Ljava/lang/String;)'](\"finally\");throw arguments[2];} " +
				"System.out['println(Ljava/lang/String;)'](\"finally\");break L12;}else{arguments[2]=ex; " +
				"System.out['println(Ljava/lang/String;)'](\"finally\");throw arguments[2];}} " +
				"System.out['println(Ljava/lang/String;)'](\"finally\");break; }case 1: try{ System.out['println()']();}" +
				"catch(ex){if(ex instanceof RuntimeException){arguments[1]=ex;arguments[1]['printStackTrace()']();}" +
				"else{throw ex;}}case 2: System.out['println()']();default:return;} }; ", 
				compiler.getCompilationBuffer().toString().replaceAll("\\s+", " "));
	}
	/**
	 * While + try/catch statement integration test:
	 * <code>
	 *  while (System.out.checkError()) {
	 *  	try {
	 *  		System.out.println();
	 *  	} catch(RuntimeException ex) {
	 *  		ex.printStackTrace();
	 *  	}
	 *  }
	 * </code>
	 * bytecode is following:
	 *  0  goto 17
	 *  3  getstatic java.lang.System.out : java.io.PrintStream [16]
	 *  6  invokevirtual java.io.PrintStream.println() : void [22]
	 *  9  goto 17
	 * 12  astore_1 [ex]
	 * 13  aload_1 [ex]
	 * 14  invokevirtual java.lang.RuntimeException.printStackTrace() : void [27]
	 * 17  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 20  invokevirtual java.io.PrintStream.checkError() : boolean [32]
	 * 23  ifne 3
	 * 26  return
	 *     Exception Table:
	 *     [pc: 3, pc: 9] -> 12 when : java.lang.RuntimeException
	 */
	public void testWhileTryCatch () {
		MethodCompiler compiler = createCompiler("zzz","()V",new Object[]{
			GOTO+" L17",
			"L3",
			GETSTATIC+" java/lang/System out java/io/PrintStream",
			INVOKEVIRTUAL+" java/io/PrintStream println ()V",
			"L9",
			GOTO+" L17",
			"L12",
			ASTORE+" 1",
			ALOAD+" 1",
			INVOKEVIRTUAL+" java/io/Exception printStackTrace ()V",
			"L17",
			GETSTATIC+" java/lang/System out java/io/PrintStream",
			INVOKEVIRTUAL+" java/io/PrintStream checkError ()Z",
			IFNE+ " L3",
			RETURN}, new Object[]{
				"java/lang/RuntimeException L3 L9 L12"
		});
		compiler.visitEnd();
		assertEquals(" while(System.out['checkError()']()){try{ System.out['println()']();}catch(ex){" +
				"if(ex instanceof RuntimeException){arguments[0]=ex;arguments[0]['printStackTrace()']();" +
				"}else{throw ex;}}continue; }return; }; ", 
				compiler.getCompilationBuffer().toString().replaceAll("\\s+", " "));
	}
	/**
	 * do/while cycle with try/catch within:
	 * <code>
	 *  do {
	 *  	try {
	 *  		System.out.println();
	 *  	} catch(RuntimeException ex) {
	 *  		ex.printStackTrace();
	 *  	}
	 *  } while (System.out.checkError());
	 * </code>
	 * will be compiled into:
	 *  0  getstatic java.lang.System.out : java.io.PrintStream [16]
	 *  3  invokevirtual java.io.PrintStream.println() : void [22]
	 *  6  goto 14
	 *  9  astore_1 [ex]
	 * 10  aload_1 [ex]
	 * 11  invokevirtual java.lang.RuntimeException.printStackTrace() : void [27]
	 * 14  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 17  invokevirtual java.io.PrintStream.checkError() : boolean [32]
	 * 20  ifne 0
	 * 23  return
	 *     Exception Table:
	 *     [pc: 0, pc: 6] -> 9 when : java.lang.RuntimeException
	 */
	public void testDoWhileTryCatch() {
		MethodCompiler compiler = createCompiler("zzz","()V",new Object[]{
			"L0",
			GETSTATIC+" java/lang/System out java/io/PrintStream",
			INVOKEVIRTUAL+" java/io/PrintStream println ()V",
			"L6",
			GOTO+" L14",
			"L9",
			ASTORE+" 1",
			ALOAD+" 1",
			INVOKEVIRTUAL+" java/io/Exception printStackTrace ()V",
			"L14",
			GETSTATIC+" java/lang/System out java/io/PrintStream",
			INVOKEVIRTUAL+" java/io/PrintStream checkError ()Z",
			IFNE+ " L0",
			RETURN}, new Object[]{
				"java/lang/RuntimeException L0 L6 L9"
		});
		compiler.visitEnd();
		assertEquals("do{try{ System.out['println()']();}catch(ex){if(ex instanceof RuntimeException){" +
				"arguments[0]=ex;arguments[0]['printStackTrace()']();}else{throw ex;}}continue;}while(System.out['checkError()']());return; }; ", 
				compiler.getCompilationBuffer().toString().replaceAll("\\s+", " "));
	}
	/**
	 * While + try/catch/finally statement integration test:
	 * <code>
	 *  while (System.out.checkError()) {
	 *  	try {
	 *  		System.out.println();
	 *  	} catch(RuntimeException ex) {
	 *  		ex.printStackTrace();
	 *  	} finally {
	 *  		System.out.println("finally");
	 *  	}
	 *  }
	 * </code>
	 * bytecode is following:
	 *  0  goto 49
	 *  3  getstatic java.lang.System.out : java.io.PrintStream [16]
	 *  6  ldc <String "try"> [22]
	 *  8  invokevirtual java.io.PrintStream.println(java.lang.String) : void [24]
	 * 11  goto 41
	 * 14  astore_1 [ex]
	 * 15  aload_1 [ex]
	 * 16  invokevirtual java.lang.RuntimeException.printStackTrace() : void [30]
	 * 19  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 22  ldc <String "finally"> [35]
	 * 24  invokevirtual java.io.PrintStream.println(java.lang.String) : void [24]
	 * 27  goto 49
	 * 30  astore_2
	 * 31  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 34  ldc <String "finally"> [35]
	 * 36  invokevirtual java.io.PrintStream.println(java.lang.String) : void [24]
	 * 39  aload_2
	 * 40  athrow
	 * 41  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 44  ldc <String "finally"> [35]
	 * 46  invokevirtual java.io.PrintStream.println(java.lang.String) : void [24]
	 * 49  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 52  invokevirtual java.io.PrintStream.checkError() : boolean [37]
	 * 55  ifne 3
	 * 58  return
	 *     Exception Table:
	 *     [pc: 3, pc: 11] -> 14 when : java.lang.RuntimeException
	 *     [pc: 3, pc: 19] -> 30 when : any
	 */
	public void testWhileTryCatchFinally () {
		MethodCompiler compiler = createCompiler("zzz","()V",new Object[]{
			GOTO+" L49",
			"L3",
			GETSTATIC+" java/lang/System out java/io/PrintStream",
			LDC+" try",
			INVOKEVIRTUAL+" java/io/PrintStream println (Ljava/lang/String;)V",
			"L11",
			GOTO+" L41",
			"L14",
			ASTORE+" 1",
			ALOAD+" 1",
			INVOKEVIRTUAL+" java/io/Exception printStackTrace ()V",
			"L19",
			GETSTATIC+" java/lang/System out java/io/PrintStream",
			LDC+" finally",
			INVOKEVIRTUAL+" java/io/PrintStream println (Ljava/lang/String;)V",
			GOTO+" L49",
			"L30",
			ASTORE+" 2",
			GETSTATIC+" java/lang/System out java/io/PrintStream",
			LDC+" finally",
			INVOKEVIRTUAL+" java/io/PrintStream println (Ljava/lang/String;)V",
			ALOAD+" 2",
			ATHROW,
			"L41",
			GETSTATIC+" java/lang/System out java/io/PrintStream",
			LDC+" finally",
			INVOKEVIRTUAL+" java/io/PrintStream println (Ljava/lang/String;)V",
			"L49",
			GETSTATIC+" java/lang/System out java/io/PrintStream",
			INVOKEVIRTUAL+" java/io/PrintStream checkError ()Z",
			IFNE+ " L3",
			RETURN}, new Object[]{
				"java/lang/RuntimeException L3 L11 L14",
				" L3 L19 L30"
		});
		compiler.visitEnd();
		assertEquals(" while(System.out['checkError()']()){try{try{ System.out['println(Ljava/lang/String;)'](\"try\");}" +
				"catch(ex){if(ex instanceof RuntimeException){arguments[0]=ex;arguments[0]['printStackTrace()']();}else{" +
				"throw ex;}}}catch(ex){arguments[1]=ex; System.out['println(Ljava/lang/String;)'](\"finally\");" +
				"throw arguments[1];} System.out['println(Ljava/lang/String;)'](\"finally\"); }return; }; ", 
				compiler.getCompilationBuffer().toString().replaceAll("\\s+", " "));
	}
	/**
	 * While + try/catch/finally statement integration test:
	 * <code>
	 *  do {
	 *  	try {
	 *  		System.out.println();
	 *  	} catch(RuntimeException ex) {
	 *  		ex.printStackTrace();
	 *  	} finally {
	 *  		System.out.println("finally");
	 *  	}
	 *  } while (System.out.checkError());
	 * </code>
	 * bytecode is following:
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
	 * 46  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 49  invokevirtual java.io.PrintStream.checkError() : boolean [37]
	 * 52  ifne 0
	 * 55  return
	 *     Exception Table:
	 *     [pc: 0, pc: 8] -> 11 when : java.lang.RuntimeException
	 *     [pc: 0, pc: 16] -> 27 when : any
	 */
	public void testDoWhileTryCatchFinally () {
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
			INVOKEVIRTUAL+" java/io/Exception printStackTrace ()V",
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
			GETSTATIC+" java/lang/System out java/io/PrintStream",
			INVOKEVIRTUAL+" java/io/PrintStream checkError ()Z",
			IFNE+ " L0",
			RETURN}, new Object[]{
				"java/lang/RuntimeException L0 L8 L11",
				" L0 L16 L27"
		});
		compiler.visitEnd();
		assertEquals("do{try{try{ System.out['println(Ljava/lang/String;)'](\"try\");}catch(ex){" +
				"if(ex instanceof RuntimeException){arguments[0]=ex;arguments[0]['printStackTrace()']();}" +
				"else{throw ex;}}}catch(ex){arguments[1]=ex; System.out['println(Ljava/lang/String;)'](\"finally\");" +
				"throw arguments[1];} System.out['println(Ljava/lang/String;)'](\"finally\");}" +
				"while(System.out['checkError()']());return; }; ", 
				compiler.getCompilationBuffer().toString().replaceAll("\\s+", " "));
	}
	/**
	 * While + try/catch/finally statement integration test:
	 * <code>
	 *  if (System.out.checkError()) {
	 *  	try {
	 *  		System.out.println();
	 *  	} catch(RuntimeException ex) {
	 *  		ex.printStackTrace();
	 *  	} finally {
	 *  		System.out.println("finally");
	 *  	}
	 *  }
	 * </code>
	 * bytecode is following:
	 *  0  getstatic java.lang.System.out : java.io.PrintStream [16]
	 *  3  invokevirtual java.io.PrintStream.checkError() : boolean [22]
	 *  6  ifeq 55
	 *  9  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 12  ldc <String "try"> [28]
	 * 14  invokevirtual java.io.PrintStream.println(java.lang.String) : void [30]
	 * 17  goto 47
	 * 20  astore_1 [ex]
	 * 21  aload_1 [ex]
	 * 22  invokevirtual java.lang.RuntimeException.printStackTrace() : void [34]
	 * 25  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 28  ldc <String "finally"> [39]
	 * 30  invokevirtual java.io.PrintStream.println(java.lang.String) : void [30]
	 * 33  goto 55
	 * 36  astore_2
	 * 37  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 40  ldc <String "finally"> [39]
	 * 42  invokevirtual java.io.PrintStream.println(java.lang.String) : void [30]
	 * 45  aload_2
	 * 46  athrow
	 * 47  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 50  ldc <String "finally"> [39]
	 * 52  invokevirtual java.io.PrintStream.println(java.lang.String) : void [30]
	 * 55  return
	 *     Exception Table:
	 *     [pc: 9, pc: 17] -> 20 when : java.lang.RuntimeException
	 *     [pc: 9, pc: 25] -> 36 when : any
	 */
	public void testIfTryCatchFinally () {
		MethodCompiler compiler = createCompiler("zzz","()V",new Object[]{
			GETSTATIC+" java/lang/System out java/io/PrintStream",
			INVOKEVIRTUAL+" java/io/PrintStream checkError ()Z",
			IFEQ+ " L55",
			"L9",
			GETSTATIC+" java/lang/System out java/io/PrintStream",
			LDC+" try",
			INVOKEVIRTUAL+" java/io/PrintStream println (Ljava/lang/String;)V",
			"L17",
			GOTO+" L47",
			"L20",
			ASTORE+" 1",
			ALOAD+" 1",
			INVOKEVIRTUAL+" java/io/Exception printStackTrace ()V",
			"L25",
			GETSTATIC+" java/lang/System out java/io/PrintStream",
			LDC+" finally",
			INVOKEVIRTUAL+" java/io/PrintStream println (Ljava/lang/String;)V",
			GOTO+" L55",
			"L36",
			ASTORE+" 2",
			GETSTATIC+" java/lang/System out java/io/PrintStream",
			LDC+" finally",
			INVOKEVIRTUAL+" java/io/PrintStream println (Ljava/lang/String;)V",
			ALOAD+" 2",
			ATHROW,
			"L47",
			GETSTATIC+" java/lang/System out java/io/PrintStream",
			LDC+" finally",
			INVOKEVIRTUAL+" java/io/PrintStream println (Ljava/lang/String;)V",
			"L55",
			RETURN}, new Object[]{
				"java/lang/RuntimeException L9 L17 L20",
				" L9 L25 L36"
		});
		compiler.visitEnd();
		assertEquals(" if(!(!System.out['checkError()']())){try{try{ System.out['println(Ljava/lang/String;)'](\"try\");}" +
				"catch(ex){if(ex instanceof RuntimeException){arguments[0]=ex;arguments[0]['printStackTrace()']();}" +
				"else{throw ex;}}}catch(ex){arguments[1]=ex; System.out['println(Ljava/lang/String;)'](\"finally\");" +
				"throw arguments[1];} System.out['println(Ljava/lang/String;)'](\"finally\");}else{}return; }; ", 
				compiler.getCompilationBuffer().toString().replaceAll("\\s+", " "));
	}
	/**
	 * While + try/catch/finally statement and break/continue integration test:
	 * <code>
	 *  A: {
	 *  	System.out.println();
	 *  	while (System.out.checkError()) {
	 *  		try {
	 *  			System.out.println("try");
	 *  			break A;
	 *  		} catch(RuntimeException ex) {
	 *  			ex.printStackTrace();
	 *  			continue;
	 *  		} finally {
	 *  			System.out.println("finally");
	 *  		}
	 *  	}
	 *  	System.out.println();
	 *  }
	 *  System.out.println("ret");
	 * </code>
	 *  0  getstatic java.lang.System.out : java.io.PrintStream [16]
	 *  3  invokevirtual java.io.PrintStream.println() : void [22]
	 *  6  goto 55
	 *  9  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 12  ldc <String "try"> [27]
	 * 14  invokevirtual java.io.PrintStream.println(java.lang.String) : void [29]
	 * 17  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 20  ldc <String "finally"> [32]
	 * 22  invokevirtual java.io.PrintStream.println(java.lang.String) : void [29]
	 * 25  goto 70
	 * 28  astore_1 [ex]
	 * 29  aload_1 [ex]
	 * 30  invokevirtual java.lang.RuntimeException.printStackTrace() : void [34]
	 * 33  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 36  ldc <String "finally"> [32]
	 * 38  invokevirtual java.io.PrintStream.println(java.lang.String) : void [29]
	 * 41  goto 55
	 * 44  astore_2
	 * 45  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 48  ldc <String "finally"> [32]
	 * 50  invokevirtual java.io.PrintStream.println(java.lang.String) : void [29]
	 * 53  aload_2
	 * 54  athrow
	 * 55  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 58  invokevirtual java.io.PrintStream.checkError() : boolean [39]
	 * 61  ifne 9
	 * 64  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 67  invokevirtual java.io.PrintStream.println() : void [22]
	 * 70  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 73  ldc <String "ret"> [43]
	 * 75  invokevirtual java.io.PrintStream.println(java.lang.String) : void [29]
	 * 78  return
	 *     Exception Table:
	 *     [pc: 9, pc: 17] -> 28 when : java.lang.RuntimeException
	 *     [pc: 9, pc: 17] -> 44 when : any
	 *     [pc: 28, pc: 33] -> 44 when : any
	 */
	public void testTryCatchBreakContinue () {
		MethodCompiler compiler = createCompiler("zzz","()V",new Object[]{
			GETSTATIC+" java/lang/System out java/io/PrintStream",
			INVOKEVIRTUAL+" java/io/PrintStream println ()V",
			GOTO+" L55",
			"L9",
			GETSTATIC+" java/lang/System out java/io/PrintStream",
			LDC+" try",
			INVOKEVIRTUAL+" java/io/PrintStream println (Ljava/lang/String;)V",
			"L17",
			GETSTATIC+" java/lang/System out java/io/PrintStream",
			LDC+" finally",
			INVOKEVIRTUAL+" java/io/PrintStream println (Ljava/lang/String;)V",
			GOTO+" L70",
			"L28",
			ASTORE+" 1",
			ALOAD+" 1",
			INVOKEVIRTUAL+" java/io/Exception printStackTrace ()V",
			"L33",
			GETSTATIC+" java/lang/System out java/io/PrintStream",
			LDC+" finally",
			INVOKEVIRTUAL+" java/io/PrintStream println (Ljava/lang/String;)V",
			GOTO+" L55",
			"L44",
			ASTORE+" 2",
			GETSTATIC+" java/lang/System out java/io/PrintStream",
			LDC+" finally",
			INVOKEVIRTUAL+" java/io/PrintStream println (Ljava/lang/String;)V",
			ALOAD+" 2",
			ATHROW,
			"L55",
			GETSTATIC+" java/lang/System out java/io/PrintStream",
			INVOKEVIRTUAL+" java/io/PrintStream checkError ()Z",
			IFNE+" L9",
			GETSTATIC+" java/lang/System out java/io/PrintStream",
			INVOKEVIRTUAL+" java/io/PrintStream println ()V",
			"L70",
			GETSTATIC+" java/lang/System out java/io/PrintStream",
			LDC+" ret",
			INVOKEVIRTUAL+" java/io/PrintStream println (Ljava/lang/String;)V",
			"L78",
			RETURN}, new Object[]{
				"java/lang/RuntimeException L9 L17 L28",
				" L9 L17 L44",
				" L28 L33 L44"
		});
		compiler.visitEnd();
		assertEquals("L14:{ System.out['println()'](); while(System.out['checkError()']()){try{ System.out" +
				"['println(Ljava/lang/String;)'](\"try\");}catch(ex){if(ex instanceof RuntimeException){try{" +
				"arguments[0]=ex;arguments[0]['printStackTrace()']();}catch(ex){arguments[1]=ex; " +
				"System.out['println(Ljava/lang/String;)'](\"finally\");throw arguments[1];} " +
				"System.out['println(Ljava/lang/String;)'](\"finally\");continue;}else{arguments[1]=ex; " +
				"System.out['println(Ljava/lang/String;)'](\"finally\");throw arguments[1];}} " +
				"System.out['println(Ljava/lang/String;)'](\"finally\");break L14; } System.out['println()']();} " +
				"System.out['println(Ljava/lang/String;)'](\"ret\");return; }; ", 
				compiler.getCompilationBuffer().toString().replaceAll("\\s+", " "));
	}
}
