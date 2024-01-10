package org.jjsc.compiler;

import org.jjsc.utils.StringUtils;

public class ConditionsTestCase extends MethodCompilerTestCase {
	/**
	 * Simple if statement of following java code:
	 * <code>
	 *  if(System.out!=null){
	 *      System.out.println();
	 *  }
	 *  return true;
	 * </code>
	 * will be compiled in the next bytecode:
	 *  7  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 10  ifnull 19
	 * 13  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 16  invokevirtual java.io.PrintStream.println() : void [22]
	 * 19  iconst_1
	 * 20  ireturn
	 */
	public void testIf() {
		MethodCompiler compiler = createCompiler("zzz",
				GETSTATIC+" java/lang/System out java/io/PrintStream",
				IFNULL+" L19", 
				GETSTATIC+" java/lang/System out java/io/PrintStream",
				INVOKEVIRTUAL+" java/io/PrintStream println ()V",
				"L19",
				String.valueOf(ICONST_1),
				String.valueOf(IRETURN));
		
		OpcodesGraphNode node1 = compiler.getCurrentBuilder().getEntryPoint();
		assertNotNull(node1);
		assertNotNull(node1.getCondition());
		assertNotNull(node1.getConditionalChild());
		assertNotNull(node1.getDirectChild());
		assertEquals("System.out===null", node1.getCondition().toString());
		
		OpcodesGraphNode node2 = node1.getDirectChild();
		assertEquals("System.out['println()']();", node2.getOutput().toString().trim());
		assertNull(node2.getCondition());
		assertNull(node2.getConditionalChild());
		assertNotNull(node2.getDirectChild());
		assertTrue(node1.getConditionalChild()==node2.getDirectChild());
		
		OpcodesGraphNode node3 = node2.getDirectChild();
		assertEquals("return(1);", node3.getOutput().toString().trim());
		assertNull(node3.getConditionalChild());
		assertNull(node3.getDirectChild());
		assertNull(node3.getCondition());
		
		compiler.visitEnd();
		assertEquals("if(!(System.out===null)){System.out['println()']();}return(1);};",
				StringUtils.cutWhiteSpaces(compiler.getCompilationBuffer().toString()));
	}
	/**
	 * If/else statement test. Java code is following:
	 * <code>
	 *  boolean rez = false;
	 *  if(args.length==1){
	 *  	System.out.println();
	 *  }
	 *  else {
	 *  	rez = args.length==2;
	 *  }
	 *  return rez;
	 * </code>
	 * will be compiled in bytecode:
	 *  0  iconst_0
	 *  1  istore_1 [rez]
	 *  2  aload_0 [args]
	 *  3  arraylength
	 *  4  iconst_1
	 *  5  if_icmpne 17
	 *  8  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 11  invokevirtual java.io.PrintStream.println() : void [22]
	 * 14  goto 29
	 * 17  aload_0 [args]
	 * 18  arraylength
	 * 19  iconst_2
	 * 20  if_icmpne 27
	 * 23  iconst_1
	 * 24  goto 28
	 * 27  iconst_0
	 * 28  istore_1 [rez]
	 * 29  iload_1 [rez]
	 * 30  ireturn
	 */
	public void testIfElse() {
		MethodCompiler compiler = createCompiler("zzz","([Ljava/lang/String;)V",new String[]{
				String.valueOf(ICONST_0),
				ISTORE+" 1",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH), 
				String.valueOf(ICONST_1),
				IF_ICMPNE+" L17", 
				GETSTATIC+" java/lang/System out java/io/PrintStream",
				INVOKEVIRTUAL+" java/io/PrintStream println ()V",
				GOTO+" L29",
				"L17",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH), 
				String.valueOf(ICONST_2),
				IF_ICMPNE+" L27",
				String.valueOf(ICONST_1),
				GOTO+" L28",
				"L27",
				String.valueOf(ICONST_0),
				"L28",
				ISTORE+" 1",
				"L29",
				ILOAD+" 1",
				String.valueOf(IRETURN)});
		
		OpcodesGraphNode node1 = compiler.getCurrentBuilder().getEntryPoint();
		assertNotNull(node1);
		assertNotNull(node1.getCondition());
		assertNotNull(node1.getConditionalChild());
		assertNotNull(node1.getDirectChild());
		assertEquals("arguments[1]=0;", node1.getOutput().toString().trim());
		assertEquals("arguments[0].length!=1", node1.getCondition().toString());
		
		OpcodesGraphNode node2 = node1.getDirectChild();
		assertEquals("System.out['println()']();", node2.getOutput().toString().trim());
		assertNull(node2.getCondition());
		assertNotNull(node2.getConditionalChild());
		assertNull(node2.getDirectChild());
		
		OpcodesGraphNode node3 = node1.getConditionalChild();
		assertNull(node3.getCondition());
		assertEquals("", node3.getOutput().toString().trim());
		assertNull(node3.getConditionalChild());
		assertNotNull(node3.getDirectChild());
		
		OpcodesGraphNode node4 = node3.getDirectChild();
		assertNull(node4.getCondition());
		assertEquals("arguments[1]=!(arguments[0].length!=2);", node4.getOutput().toString().trim());
		assertNull(node4.getConditionalChild());
		assertNotNull(node4.getDirectChild());
		
		assertTrue(node2.getConditionalChild()==node4.getDirectChild());
		
		OpcodesGraphNode node5 = node4.getDirectChild();
		assertNull(node5.getCondition());
		assertEquals("return(arguments[1]);", node5.getOutput().toString().trim());
		assertNull(node5.getConditionalChild());
		assertNull(node5.getDirectChild());
		
		compiler.visitEnd();
		assertEquals("arguments[1]=0;if(!(arguments[0].length!=1)){System.out['println()']();}" +
				"else{arguments[1]=!(arguments[0].length!=2);}return(arguments[1]);};",
				StringUtils.cutWhiteSpaces(compiler.getCompilationBuffer().toString()));
	}
	/**
	 * If/elseIf statement test. Java code is following:
	 * <code>
	 *  boolean rez = false;
	 *  if(args.length==1){
	 *  	System.out.println();
	 *  }
	 *  else if (args.length<10) {
	 *  	rez = args.length==2;
	 *  }
	 *  return rez;
	 * </code>
	 * will be compiled in bytecode:
	 *  0  iconst_0
	 *  1  istore_1 [rez]
	 *  2  aload_0 [args]
	 *  3  arraylength
	 *  4  iconst_1
	 *  5  if_icmpne 17
	 *  8  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 11  invokevirtual java.io.PrintStream.println() : void [22]
	 * 14  goto 36
	 * 17  aload_0 [args]
	 * 18  arraylength
	 * 19  bipush 10
	 * 21  if_icmpge 36
	 * 24  aload_0 [args]
	 * 25  arraylength
	 * 26  iconst_2
	 * 27  if_icmpne 34
	 * 30  iconst_1
	 * 31  goto 35
	 * 34  iconst_0
	 * 35  istore_1 [rez]
	 * 36  iload_1 [rez]
	 * 37  ireturn
	 */
	public void testIfElseIf() {
		MethodCompiler compiler = createCompiler("zzz","([Ljava/lang/String;)V",new String[]{
				String.valueOf(ICONST_0),
				ISTORE+" 1",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH), 
				String.valueOf(ICONST_1),
				IF_ICMPNE+" L17", 
				GETSTATIC+" java/lang/System out java/io/PrintStream",
				INVOKEVIRTUAL+" java/io/PrintStream println ()V",
				GOTO+" L36",
				"L17",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH),
				BIPUSH+" 10",
				IF_ICMPGE+" L36",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH),
				String.valueOf(ICONST_2),
				IF_ICMPNE+" L34",
				String.valueOf(ICONST_1),
				GOTO+" L35",
				"L34",
				String.valueOf(ICONST_0),
				"L35",
				ISTORE+" 1",
				"L36",
				ILOAD+" 1",
				String.valueOf(IRETURN)});
		
		OpcodesGraphNode node1 = compiler.getCurrentBuilder().getEntryPoint();
		assertNotNull(node1);
		assertNotNull(node1.getCondition());
		assertNotNull(node1.getConditionalChild());
		assertNotNull(node1.getDirectChild());
		assertEquals("arguments[1]=0;", node1.getOutput().toString().trim());
		assertEquals("arguments[0].length!=1", node1.getCondition().toString());
		
		OpcodesGraphNode node2 = node1.getDirectChild();
		assertEquals("System.out['println()']();", node2.getOutput().toString().trim());
		assertNull(node2.getCondition());
		assertNotNull(node2.getConditionalChild());
		assertNull(node2.getDirectChild());
		
		OpcodesGraphNode node3 = node1.getConditionalChild();
		assertNotNull(node3.getCondition());
		assertEquals("arguments[0].length>=10", node3.getCondition().toString());
		assertEquals("", node3.getOutput().toString().trim());
		assertNotNull(node3.getConditionalChild());
		assertNotNull(node3.getDirectChild());
		
		assertTrue(node3.getConditionalChild()==node2.getConditionalChild());
		
		OpcodesGraphNode node3_5 = node3.getDirectChild();
		assertNull(node3_5.getCondition());
		assertEquals("", node3_5.getOutput().toString().trim());
		assertNull(node3_5.getConditionalChild());
		assertNotNull(node3_5.getDirectChild());
		
		OpcodesGraphNode node4 = node3_5.getDirectChild();
		assertNull(node4.getCondition());
		assertEquals("arguments[1]=!(arguments[0].length!=2);", node4.getOutput().toString().trim());
		assertNull(node4.getConditionalChild());
		assertNotNull(node4.getDirectChild());
		
		assertTrue(node2.getConditionalChild()==node4.getDirectChild());
		
		OpcodesGraphNode node5 = node4.getDirectChild();
		assertNull(node5.getCondition());
		assertEquals("return(arguments[1]);", node5.getOutput().toString().trim());
		assertNull(node5.getConditionalChild());
		assertNull(node5.getDirectChild());
		
		compiler.visitEnd();
		assertEquals("arguments[1]=0;if(!(arguments[0].length!=1)){System.out['println()']();}else{" +
				"if(!(arguments[0].length>=10)){arguments[1]=!(arguments[0].length!=2);}}return(arguments[1]);};",
				StringUtils.cutWhiteSpaces(compiler.getCompilationBuffer().toString()));
	}
	/**
	 * Most complex case - contains if, few else if and else statements. Java code is following:
	 * <code>
	 *  boolean rez = false;
	 *  if(args.length==1){
	 *  	System.out.println();
	 *  }
	 *  else if (args.length<10) {
	 *  	rez = args.length==2;
	 *  }
	 *  else if (args.length<50) {
	 *  	rez = true;
	 *  }
	 *  else {
	 *  	System.out.println("else");
	 *  }
	 *  return rez;
	 * </code>
	 * will be compiled in bytecode:
	 *  0  iconst_0
	 *  1  istore_1 [rez]
	 *  2  aload_0 [args]
	 *  3  arraylength
	 *  4  iconst_1
	 *  5  if_icmpne 17
	 *  8  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 11  invokevirtual java.io.PrintStream.println() : void [22]
	 * 14  goto 59
	 * 17  aload_0 [args]
	 * 18  arraylength
	 * 19  bipush 10
	 * 21  if_icmpge 39
	 * 24  aload_0 [args]
	 * 25  arraylength
	 * 26  iconst_2
	 * 27  if_icmpne 34
	 * 30  iconst_1
	 * 31  goto 35
	 * 34  iconst_0
	 * 35  istore_1 [rez]
	 * 36  goto 59
	 * 39  aload_0 [args]
	 * 40  arraylength
	 * 41  bipush 50
	 * 43  if_icmpge 51
	 * 46  iconst_1
	 * 47  istore_1 [rez]
	 * 48  goto 59
	 * 51  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 54  ldc <String "else"> [27]
	 * 56  invokevirtual java.io.PrintStream.println(java.lang.String) : void [29]
	 * 59  iload_1 [rez]
	 * 60  ireturn
	 */
	public void testIfElseIfElseIfElse() {
		MethodCompiler compiler = createCompiler("zzz","([Ljava/lang/String;)V",new String[]{
				String.valueOf(ICONST_0),
				ISTORE+" 1",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH), 
				String.valueOf(ICONST_1),
				IF_ICMPNE+" L17", 
				GETSTATIC+" java/lang/System out java/io/PrintStream",
				INVOKEVIRTUAL+" java/io/PrintStream println ()V",
				GOTO+" L59",
				"L17",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH),
				BIPUSH+" 10",
				IF_ICMPGE+" L39",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH),
				String.valueOf(ICONST_2),
				IF_ICMPNE+" L34",
				String.valueOf(ICONST_1),
				GOTO+" L35",
				"L34",
				String.valueOf(ICONST_0),
				"L35",
				ISTORE+" 1",
				GOTO+" L59",
				"L39",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH),
				BIPUSH+" 50",
				IF_ICMPGE+" L51",
				String.valueOf(ICONST_1),
				ISTORE+" 1",
				GOTO+" L59",
				"L51",
				GETSTATIC+" java/lang/System out java/io/PrintStream",
				LDC+" else",
				INVOKEVIRTUAL+" java/io/PrintStream println (Ljava/lang/String;)V",
				"L59",
				ILOAD+" 1",
				String.valueOf(IRETURN)});
		compiler.visitEnd();
		assertEquals("arguments[1]=0;if(!(arguments[0].length!=1)){System.out['println()']();}else{" +
				"if(!(arguments[0].length>=10)){arguments[1]=!(arguments[0].length!=2);}else{" +
				"if(!(arguments[0].length>=50)){arguments[1]=1;}else{System.out" +
				"['println(Ljava/lang/String;)'](\"else\");}}}return(arguments[1]);};",
				StringUtils.cutWhiteSpaces(compiler.getCompilationBuffer().toString()));
	}
	/**
	 * Switch statement test. Java code is following:
	 * <code>
	 *  boolean rez = false;
	 *  switch(args.length){
	 *  case 1:
	 *  case 2:
	 *  	System.out.println();
	 *  	break;
	 *  case 3:
	 *  	rez = true;
	 *  	break;
	 *  default:
	 *  	System.out.println("else");
	 *  }
	 *  return rez;
	 * </code>
	 * will be compiled in bytecode:
	 *  0  iconst_0
	 *  1  istore_1 [rez]
	 *  2  aload_0 [args]
	 *  3  arraylength
	 *  4  tableswitch default: 46
	 *      case 1: 32
	 *      case 2: 32
	 *      case 3: 41
	 * 32  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 35  invokevirtual java.io.PrintStream.println() : void [22]
	 * 38  goto 54
	 * 41  iconst_1
	 * 42  istore_1 [rez]
	 * 43  goto 54
	 * 46  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 49  ldc <String "else"> [27]
	 * 51  invokevirtual java.io.PrintStream.println(java.lang.String) : void [29]
	 * 54  iload_1 [rez]
	 * 55  ireturn
	 * 
	 * 
	 * In case of non-linear cases (not an integer sequence):
	 * <code>
	 *  boolean rez = false;
	 *  switch(args.length){
	 *  case 1:
	 *  default:
	 *  	System.out.println("else");
	 *  case 30:
	 *  	System.out.println();
	 *  case 2:
	 *  	rez = true;
	 *  	break;
	 *  }
	 *  return rez;
	 * </code>
	 * will be compiled in bytecode:
	 *  0  iconst_0
	 *  1  istore_1 [rez]
	 *  2  aload_0 [args]
	 *  3  arraylength
	 *  4  lookupswitch default: 40
	 *      case 1: 40
	 *      case 2: 54
	 *      case 30: 48
	 * 40  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 43  ldc <String "else"> [22]
	 * 45  invokevirtual java.io.PrintStream.println(java.lang.String) : void [24]
	 * 48  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 51  invokevirtual java.io.PrintStream.println() : void [30]
	 * 54  iconst_1
	 * 55  istore_1 [rez]
	 * 56  iload_1 [rez]
	 * 57  ireturn
	 */
	public void testSwitch() {
		//TABLESWITCH
		MethodCompiler compiler = createCompiler("zzz","([Ljava/lang/String;)V",new String[]{
				String.valueOf(ICONST_0),
				ISTORE+" 1",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH), 
				TABLESWITCH+" 1 3 L46 L32 L32 L41",
				"L32",
				GETSTATIC+" java/lang/System out java/io/PrintStream",
				INVOKEVIRTUAL+" java/io/PrintStream println ()V",
				GOTO+" L54",
				"L41",
				String.valueOf(ICONST_1),
				ISTORE+" 1",
				GOTO+" L54",
				"L46",
				GETSTATIC+" java/lang/System out java/io/PrintStream",
				LDC+" else",
				INVOKEVIRTUAL+" java/io/PrintStream println (Ljava/lang/String;)V",
				"L54",
				ILOAD+" 1",
				String.valueOf(IRETURN)});
		compiler.visitEnd();
		assertEquals("arguments[1]=0;switch(arguments[0].length){case1:case2:" +
				"System.out['println()']();break;case3:arguments[1]=1;break;default:" +
				"System.out['println(Ljava/lang/String;)'](\"else\");}return(arguments[1]);};",
				StringUtils.cutWhiteSpaces(compiler.getCompilationBuffer().toString()));
		
		//LOOKUPSWITCH
		
		MethodCompiler compiler2 = createCompiler("zzz","([Ljava/lang/String;)V",new String[]{
				String.valueOf(ICONST_0),
				ISTORE+" 1",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH), 
				LOOKUPSWITCH+" L40 1 L40 2 L54 30 L48",
				"L40",
				GETSTATIC+" java/lang/System out java/io/PrintStream",
				LDC+" else",
				INVOKEVIRTUAL+" java/io/PrintStream println (Ljava/lang/String;)V",
				"L48",
				GETSTATIC+" java/lang/System out java/io/PrintStream",
				INVOKEVIRTUAL+" java/io/PrintStream println ()V",
				"L54",
				String.valueOf(ICONST_1),
				ISTORE+" 1",
				"L56",
				ILOAD+" 1",
				String.valueOf(IRETURN)});
		 
		compiler2.visitEnd();
		assertEquals("arguments[1]=0;switch(arguments[0].length){default:case1:" +
				"System.out['println(Ljava/lang/String;)'](\"else\");case30:" +
				"System.out['println()']();case2:arguments[1]=1;return(arguments[1]);}};",
				StringUtils.cutWhiteSpaces(compiler2.getCompilationBuffer().toString()));
	}
	/**
	 * Break statement in the switch default branch. Java code is following:
	 * <code>
	 *  PrintStream out = System.out;
	 *  switch (args.length) {
	 *  default:
	 *  	out.println();
	 *  	break;
	 *  case 0:
	 *  case 1:
	 *  case 6:
	 *  	out.println("zz");
	 *  	break;
	 *  }
	 *  out.println("ss");
	 *  </code>
	 *  This code produces following opcodes:
	 *  0  getstatic java.lang.System.out : java.io.PrintStream [16]
	 *  3  astore_1 [out]
	 *  4  aload_0 [args]
	 *  5  arraylength
	 *  6  tableswitch default: 48
	 *       case 0: 55
	 *       case 1: 55
	 *       case 2: 48
	 *       case 3: 48
	 *       case 4: 48
	 *       case 5: 48
	 *       case 6: 55
	 * 48  aload_1 [out]
	 * 49  invokevirtual java.io.PrintStream.println() : void [22]
	 * 52  goto 61
	 * 55  aload_1 [out]
	 * 56  ldc <String "zz"> [27]
	 * 58  invokevirtual java.io.PrintStream.println(java.lang.String) : void [29]
	 * 61  aload_1 [out]
	 * 62  ldc <String "ss"> [32]
	 * 64  invokevirtual java.io.PrintStream.println(java.lang.String) : void [29]
	 * 67  return
	 */
	public void testSwitchBreakDefaultLabel() {
		MethodCompiler compiler = createCompiler("zzz","([Ljava/lang/String;)V",new Object[]{
				GETSTATIC+" java/lang/System out java/io/PrintStream",
				ISTORE+" 1",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH), 
				TABLESWITCH+" 0 6 L48 L55 L55 L48 L48 L48 L48 L55",
				"L48",
				ALOAD+" 1",
				INVOKEVIRTUAL+" java/io/PrintStream println ()V",
				GOTO+" L61",
				"L55",
				ALOAD+" 1",
				LDC+" zz",
				INVOKEVIRTUAL+" java/io/PrintStream println (Ljava/lang/String;)V",
				"L61",
				ALOAD+" 1",
				LDC+" ss",
				INVOKEVIRTUAL+" java/io/PrintStream println (Ljava/lang/String;)V",
				RETURN});
		compiler.visitEnd();
		assertEquals("arguments[1]=System.out;switch(arguments[0].length){" +
				"default:case2:case3:case4:case5:arguments[1]['println()']();" +
				"break;case0:case1:case6:arguments[1]['println(Ljava/lang/String;)']" +
				"(\"zz\");}arguments[1]['println(Ljava/lang/String;)'](\"ss\");return;};",
				StringUtils.cutWhiteSpaces(compiler.getCompilationBuffer().toString()));
	}
	/**
	 * Boolean expression in the if statement. Java code is following:
	 * <code>
	 *  boolean rez = false;
	 *  if(args.length>=1 && args.length<10 || args.length==17){
	 *  	System.out.println();
	 *  }
	 *  return rez;
	 *  </code>
	 *  This code produces following opcodes:
	 *  0  iconst_0
	 *  1  istore_1 [rez]
	 *  2  aload_0 [args]
	 *  3  arraylength
	 *  4  iconst_1
	 *  5  if_icmplt 15
	 *  8  aload_0 [args]
	 *  9  arraylength
	 * 10  bipush 10
	 * 12  if_icmplt 22
	 * 15  aload_0 [args]
	 * 16  arraylength
	 * 17  bipush 17
	 * 19  if_icmpne 28
	 * 22  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 25  invokevirtual java.io.PrintStream.println() : void [22]
	 * 28  iload_1 [rez]
	 * 29  ireturn
	 */
	public void testBooleanOperationIf(){
		MethodCompiler compiler = createCompiler("zzz","([Ljava/lang/String;)V",new String[]{
				String.valueOf(ICONST_0),
				ISTORE+" 1",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH),
				String.valueOf(ICONST_1),
				IF_ICMPLT + " L15",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH),
				BIPUSH + " 10",
				IF_ICMPLT + " L22",
				"L15",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH),
				BIPUSH + " 17",
				IF_ICMPNE + " L28",
				"L22",
				GETSTATIC+" java/lang/System out java/io/PrintStream",
				INVOKEVIRTUAL+" java/io/PrintStream println ()V",
				"L28",
				ILOAD+" 1",
				String.valueOf(IRETURN)});
		compiler.visitEnd();
		assertEquals("arguments[1]=0;if(!(arguments[0].length<1)&&arguments[0].length<10||" +
				"!(arguments[0].length!=17)){System.out['println()']();}return(arguments[1]);};",
				StringUtils.cutWhiteSpaces(compiler.getCompilationBuffer().toString()));
	}
	/**
	 * More complex expression including few ifs and boolean operations.
	 * Java code is following:
	 * <code>
	 *  boolean rez = false;
	 *  if(args.length>=1 && args.length<10 || args.length==17){
	 *  	if(args.length==22){
	 *  		System.out.println();
	 *  	}
	 *  	System.out.println();
	 *  }
	 *  if (args.length<50) {
	 *  	rez = args.length<30;
	 *  }
	 *  return rez;
	 *  </code>
	 *  This code produces following opcodes:
	 *  0  iconst_0
	 *  1  istore_1 [rez]
	 *  2  aload_0 [args]
	 *  3  arraylength
	 *  4  iconst_1
	 *  5  if_icmplt 15
	 *  8  aload_0 [args]
	 *  9  arraylength
	 * 10  bipush 10
	 * 12  if_icmplt 22
	 * 15  aload_0 [args]
	 * 16  arraylength
	 * 17  bipush 17
	 * 19  if_icmpne 41
	 * 22  aload_0 [args]
	 * 23  arraylength
	 * 24  bipush 22
	 * 26  if_icmpne 35
	 * 29  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 32  invokevirtual java.io.PrintStream.println() : void [22]
	 * 35  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 38  invokevirtual java.io.PrintStream.println() : void [22]
	 * 41  aload_0 [args]
	 * 42  arraylength
	 * 43  bipush 50
	 * 45  if_icmpge 61
	 * 48  aload_0 [args]
	 * 49  arraylength
	 * 50  bipush 30
	 * 52  if_icmpge 59
	 * 55  iconst_1
	 * 56  goto 60
	 * 59  iconst_0
	 * 60  istore_1 [rez]
	 * 61  iload_1 [rez]
	 * 62  ireturn
	 */
	public void testBooleanOperationIfComplex() {
		MethodCompiler compiler = createCompiler("zzz","([Ljava/lang/String;)V",new String[]{
				String.valueOf(ICONST_0),
				ISTORE+" 1",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH),
				String.valueOf(ICONST_1),
				IF_ICMPLT + " L15",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH),
				BIPUSH + " 10",
				IF_ICMPLT + " L22",
				"L15",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH),
				BIPUSH + " 17",
				IF_ICMPNE + " L41",
				"L22",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH),
				BIPUSH + " 22",
				IF_ICMPNE + " L35",
				GETSTATIC+" java/lang/System out java/io/PrintStream",
				INVOKEVIRTUAL+" java/io/PrintStream println ()V",
				"L35",
				GETSTATIC+" java/lang/System out java/io/PrintStream",
				INVOKEVIRTUAL+" java/io/PrintStream println ()V",
				"L41",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH),
				BIPUSH + " 50",
				IF_ICMPGE + " L61",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH),
				BIPUSH + " 30",
				IF_ICMPGE + " L59",
				String.valueOf(ICONST_1),
				GOTO+" L60",
				"L59",
				String.valueOf(ICONST_0),
				"L60",
				ISTORE+" 1",
				"L61",
				ILOAD+" 1",
				String.valueOf(IRETURN)});
		compiler.visitEnd();
		assertEquals("arguments[1]=0;if(!(arguments[0].length<1)&&arguments[0].length<10||" +
				"!(arguments[0].length!=17)){if(!(arguments[0].length!=22)){System.out" +
				"['println()']();}System.out['println()']();}if(!(arguments[0].length>=50)){" +
				"arguments[1]=!(arguments[0].length>=30);}return(arguments[1]);};",
				StringUtils.cutWhiteSpaces(compiler.getCompilationBuffer().toString()));
	}
	/**
	 * Boolean expression inside if statement. Java code is following:
	 * <code>
	 *  boolean rez = false;
	 *  if (args.length<50) {
	 *  	rez = args.length>=1 && args.length<10 || args.length==17;
	 *  }
	 *  else {
	 *  	rez = args.length%20!=0 || args.length%21!=0;
	 *  }
	 *  return rez;
	 * </code>
	 *  0  iconst_0
	 *  1  istore_1 [rez]
	 *  2  aload_0 [args]
	 *  3  arraylength
	 *  4  bipush 50
	 *  6  if_icmpge 38
	 *  9  aload_0 [args]
	 * 10  arraylength
	 * 11  iconst_1
	 * 12  if_icmplt 22
	 * 15  aload_0 [args]
	 * 16  arraylength
	 * 17  bipush 10
	 * 19  if_icmplt 33
	 * 22  aload_0 [args]
	 * 23  arraylength
	 * 24  bipush 17
	 * 26  if_icmpeq 33
	 * 29  iconst_0
	 * 30  goto 34
	 * 33  iconst_1
	 * 34  istore_1 [rez]
	 * 35  goto 60
	 * 38  aload_0 [args]
	 * 39  arraylength
	 * 40  bipush 20
	 * 42  irem
	 * 43  ifne 58
	 * 46  aload_0 [args]
	 * 47  arraylength
	 * 48  bipush 21
	 * 50  irem
	 * 51  ifne 58
	 * 54  iconst_0
	 * 55  goto 59
	 * 58  iconst_1
	 * 59  istore_1 [rez]
	 * 60  iload_1 [rez]
	 * 61  ireturn
	 */
	public void testIfBooleanOperation(){
		MethodCompiler compiler = createCompiler("zzz","([Ljava/lang/String;)V",new String[]{
				String.valueOf(ICONST_0),
				ISTORE+" 1",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH),
				BIPUSH + " 50",
				IF_ICMPGE + " L38",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH),
				String.valueOf(ICONST_1),
				IF_ICMPLT + " L22",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH),
				BIPUSH + " 10",
				IF_ICMPLT + " L33",
				"L22",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH),
				BIPUSH + " 17",
				IF_ICMPEQ + " L33",
				String.valueOf(ICONST_0),
				GOTO+" L34",
				"L33",
				String.valueOf(ICONST_1),
				"L34",
				ISTORE+" 1",
				GOTO+" L60",
				"L38",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH),
				BIPUSH + " 20",
				String.valueOf(IREM),
				IFNE+" L58",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH),
				BIPUSH + " 21",
				String.valueOf(IREM),
				IFNE+" L58",
				String.valueOf(ICONST_0),
				GOTO+" L59",
				"L58",
				String.valueOf(ICONST_1),
				"L59",
				ISTORE+" 1",
				"L60",
				ILOAD+" 1",
				String.valueOf(IRETURN)});
		compiler.visitEnd();
		assertEquals("arguments[1]=0;if(!(arguments[0].length>=50)){" +
				"arguments[1]=!(arguments[0].length<1)&&arguments[0].length<10||arguments[0].length==17;" +
				"}else{arguments[1]=arguments[0].length%20||arguments[0].length%21;}return(arguments[1]);};",
				StringUtils.cutWhiteSpaces(compiler.getCompilationBuffer().toString()));
	}
}
