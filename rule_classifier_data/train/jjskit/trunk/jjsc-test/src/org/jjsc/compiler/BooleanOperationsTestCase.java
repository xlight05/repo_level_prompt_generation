package org.jjsc.compiler;

import org.jjsc.utils.StringUtils;


public class BooleanOperationsTestCase extends MethodCompilerTestCase {
	/**
	 * Bytecode equivalent for operation:
	 * 
	 * boolean rez = args.length==10||args.length==12&&rez;
	 * 
	 * (where args is array single argument of method) is following:
	 * 
	 * 8  aload_0 [args]
	 * 9  arraylength
	 * 10  bipush 10
	 * 12  if_icmpeq 30
	 * 15  aload_0 [args]
	 * 16  arraylength
	 * 17  bipush 12
	 * 19  if_icmpne 26
	 * 22  iload_1 [rez]
	 * 23  ifne 30
	 * 26  iconst_0
	 * 27  goto 31
	 * 30  iconst_1
	 * 31  istore_1 [rez]
	 */
	public void testBooleanOperationGraphBuildORAND(){
		MethodCompiler compiler = createCompiler("zzz",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH), 
				BIPUSH+" 10",
				IF_ICMPEQ+" L30", 
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH),
				BIPUSH+" 12",
				IF_ICMPNE+" L26",
				ILOAD+" 1",
				IFNE+" L30",
				"L26",
				String.valueOf(ICONST_0),
				GOTO+" L31",
				"L30",
				String.valueOf(ICONST_1),
				"L31"/*,
				ISTORE+" 1"*/);
		OpcodesGraphNode node1 = compiler.getCurrentBuilder().getEntryPoint();
		assertNotNull(node1);
		assertEquals("[]", node1.getStack().toString());
		assertNotNull(node1.getCondition());
		assertEquals("arguments[0].length==10", node1.getCondition().toString());
		
		OpcodesGraphNode node2 = node1.getConditionalChild();
		assertNotNull(node2);
		assertNull(node2.getCondition());
		assertEquals("[1]", node2.getStack().toString());
		assertNotNull(node2.getDirectChild());
		assertNull(node2.getConditionalChild());
		
		OpcodesGraphNode node3 = node1.getDirectChild();
		assertNotNull(node3);
		assertEquals("[]", node3.getStack().toString());
		assertNotNull(node1.getCondition());
		assertEquals("arguments[0].length!=12", node3.getCondition().toString());
		
		OpcodesGraphNode node4 = node3.getDirectChild();
		assertNotNull(node4);
		assertEquals("[]", node4.getStack().toString());
		assertNotNull(node1.getCondition());
		assertEquals("arguments[1]", node4.getCondition().toString());
		
		OpcodesGraphNode node5 = node3.getConditionalChild();
		assertNotNull(node5);
		assertNull(node5.getCondition());
		assertEquals("[0]", node5.getStack().toString());
		assertNull(node5.getDirectChild());
		assertNotNull(node5.getConditionalChild());
		
		assertTrue(node2==node4.getConditionalChild());
		assertTrue(node5==node4.getDirectChild());
		
		assertEquals("arguments[0].length==10||(!(arguments[0].length!=12)&&arguments[1])",
				compiler.getCurrentBuilder().getStack().pop().toString());
	}
	/**
	 * Bytecode equivalent for operation:
	 * 
	 * boolean rez = args.length==10||args.length==12||rez;
	 * 
	 * (where args is array single argument of method) is following:
	 * 8   aload_0 [args]
	 * 9   arraylength
	 * 10  bipush 10
	 * 12  if_icmpeq 30
	 * 15  aload_0 [args]
	 * 16  arraylength
	 * 17  bipush 12
	 * 19  if_icmpeq 30
	 * 22  iload_1 [rez]
	 * 23  ifne 30
	 * 26  iconst_0
	 * 27  goto 31
	 * 30  iconst_1
	 * 
	 * 8  aload_0 [args]
     9  arraylength
    10  bipush 10
    12  if_icmpeq 30
    15  aload_0 [args]
    16  arraylength
    17  bipush 12
    19  if_icmpeq 30
    22  iload_1 [rez]
    23  ifne 30
    26  iconst_0
    27  goto 31
    30  iconst_1
    31  istore_1 [rez]
	 */
	public void testBooleanOperationGraphBuildOROR(){
		MethodCompiler compiler = createCompiler("zzz",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH), 
				BIPUSH+" 10",
				IF_ICMPEQ+" L30", 
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH),
				BIPUSH+" 12",
				IF_ICMPEQ+" L30",
				ILOAD+" 1",
				IFNE+" L30",
				"L26",
				String.valueOf(ICONST_0),
				GOTO+" L31",
				"L30",
				String.valueOf(ICONST_1),
				"L31"/*,
				ISTORE+" 1"*/);
		OpcodesGraphNode node1 = compiler.getCurrentBuilder().getEntryPoint();
		assertNotNull(node1);
		assertEquals("[]", node1.getStack().toString());
		assertNotNull(node1.getCondition());
		assertEquals("arguments[0].length==10", node1.getCondition().toString());
		
		OpcodesGraphNode node2 = node1.getConditionalChild();
		assertNotNull(node2);
		assertNull(node2.getCondition());
		assertEquals("[1]", node2.getStack().toString());
		assertNotNull(node2.getDirectChild());
		assertNull(node2.getConditionalChild());
		
		OpcodesGraphNode node3 = node1.getDirectChild();
		assertNotNull(node3);
		assertEquals("[]", node3.getStack().toString());
		assertNotNull(node1.getCondition());
		assertEquals("arguments[0].length==12", node3.getCondition().toString());
		
		OpcodesGraphNode node4 = node3.getDirectChild();
		assertNotNull(node4);
		assertEquals("[]", node4.getStack().toString());
		assertNotNull(node1.getCondition());
		assertEquals("arguments[1]", node4.getCondition().toString());
		
		OpcodesGraphNode node5 = node4.getDirectChild();
		assertNotNull(node5);
		assertNull(node5.getCondition());
		assertEquals("[0]", node5.getStack().toString());
		assertNull(node5.getDirectChild());
		assertNotNull(node5.getConditionalChild());
		
		assertTrue(node2==node3.getConditionalChild());
		assertTrue(node5==node4.getDirectChild());
		
		assertEquals("arguments[0].length==10||(arguments[0].length==12||arguments[1])",
				compiler.getCurrentBuilder().getStack().pop().toString());
	}
	/**
	 * Bytecode equivalent for operation:
	 * 
	 * boolean rez = args.length==10||args.length==12;
	 * 
	 * (where args is array single argument of method) is following:
	 * 8  aload_0 [args]
	 * 9  arraylength
	 * 10  bipush 10
	 * 12  if_icmpeq 26
	 * 15  aload_0 [args]
	 * 16  arraylength
	 * 17  bipush 12
	 * 19  if_icmpeq 26
	 * 22  iconst_0
	 * 23  goto 27
	 * 26  iconst_1
	 */
	public void testOR(){
		MethodCompiler compiler = createCompiler("zzz",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH), 
				BIPUSH+" 10",
				IF_ICMPEQ+" L26", 
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH),
				BIPUSH+" 12",
				IF_ICMPEQ+" L26",
				String.valueOf(ICONST_0),
				GOTO+" L27",
				"L26",
				String.valueOf(ICONST_1),
				"L27"/*,
				ISTORE+" 1"*/);
		OpcodesGraphNode node1 = compiler.getCurrentBuilder().getEntryPoint();
		assertNotNull(node1);
		assertEquals("[]", node1.getStack().toString());
		assertNotNull(node1.getCondition());
		assertEquals("arguments[0].length==10", node1.getCondition().toString());
		
		OpcodesGraphNode node2 = node1.getConditionalChild();
		assertNotNull(node2);
		assertNull(node2.getCondition());
		assertEquals("[1]", node2.getStack().toString());
		assertNotNull(node2.getDirectChild());
		assertNull(node2.getConditionalChild());
		
		OpcodesGraphNode node3 = node1.getDirectChild();
		assertNotNull(node3);
		assertEquals("[]", node3.getStack().toString());
		assertNotNull(node1.getCondition());
		assertEquals("arguments[0].length==12", node3.getCondition().toString());
		
		OpcodesGraphNode node4 = node3.getDirectChild();
		assertNotNull(node4);
		assertNull(node4.getCondition());
		assertEquals("[0]", node4.getStack().toString());
		assertNull(node4.getDirectChild());
		assertNotNull(node4.getConditionalChild());
		
		assertTrue(node2==node3.getConditionalChild());
		
		assertEquals("arguments[0].length==10||arguments[0].length==12",compiler.getCurrentBuilder().getStack().pop().toString());
	}
	/**
	 * Bytecode equivalent for operation:
	 * 
	 * boolean rez = args.length==10&&args.length==12;
	 * 
	 * (where args is array single argument of method) is following:
	 *  8  aload_0 [args]
	 *  9  arraylength
	 * 10  bipush 10
	 * 12  if_icmpne 26
	 * 15  aload_0 [args]
	 * 16  arraylength
	 * 17  bipush 12
	 * 19  if_icmpne 26
	 * 22  iconst_1
	 * 23  goto 27
	 * 26  iconst_0
	 * 27  istore_1 [rez]
	 */
	public void testAND(){
		MethodCompiler compiler = createCompiler("zzz",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH), 
				BIPUSH+" 10",
				IF_ICMPNE+" L26", 
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH),
				BIPUSH+" 12",
				IF_ICMPNE+" L26",
				String.valueOf(ICONST_1),
				GOTO+" L27",
				"L26",
				String.valueOf(ICONST_0),
				"L27"/*,
				ISTORE+" 1"*/);
		OpcodesGraphNode node1 = compiler.getCurrentBuilder().getEntryPoint();
		assertNotNull(node1);
		assertEquals("[]", node1.getStack().toString());
		assertNotNull(node1.getCondition());
		assertEquals("arguments[0].length!=10", node1.getCondition().toString());
		
		OpcodesGraphNode node2 = node1.getConditionalChild();
		assertNotNull(node2);
		assertNull(node2.getCondition());
		assertEquals("[0]", node2.getStack().toString());
		assertNotNull(node2.getDirectChild());
		assertNull(node2.getConditionalChild());
		
		OpcodesGraphNode node3 = node1.getDirectChild();
		assertNotNull(node3);
		assertEquals("[]", node3.getStack().toString());
		assertNotNull(node1.getCondition());
		assertEquals("arguments[0].length!=12", node3.getCondition().toString());
		
		OpcodesGraphNode node4 = node3.getDirectChild();
		assertNotNull(node4);
		assertNull(node4.getCondition());
		assertEquals("[1]", node4.getStack().toString());
		assertNull(node4.getDirectChild());
		assertNotNull(node4.getConditionalChild());
		
		assertTrue(node2==node3.getConditionalChild());
		
		assertEquals("(!(arguments[0].length!=10)&&!(arguments[0].length!=12))",compiler.getCurrentBuilder().getStack().pop().toString());
	}
	/**
	 * Small prioritizing test, java code is folowing:
	 * <code>
	 *  b = (c||a)&&b;
	 * </code>
	 * and will be compiled in the bytecode:
	 * 
	 *  0  iload_3 [c]
	 *  1  ifne 8
	 *  4  iload_1 [a]
	 *  5  ifeq 16
	 *  8  iload_2 [b]
	 *  9  ifeq 16
	 * 12  iconst_1
	 * 13  goto 17
	 * 16  iconst_0
	 * 17  istore_2 [b]
	 */
	public void testPriotizedORAND() {
		MethodCompiler compiler = createCompiler("zzz", "(ZZZ)V", new String[]{
				ILOAD+" 3",
				IFNE+" L8",
				ILOAD+" 1",
				IFEQ+" L16",
				"L8",
				ILOAD+" 2",
				IFEQ+" L16",
				String.valueOf(ICONST_1),
				GOTO+" L17",
				"L16",
				String.valueOf(ICONST_0),
				"L17"/*,
				ISTORE+" 1"*/});
		assertEquals("(arguments[3]||!(!arguments[1]))&&!(!arguments[2])",compiler.getCurrentBuilder().getStack().pop().toString());
	}
	/**
	 * Bytecode equivalent for operation:
	 * 
	 * boolean rez = args.length==8||args.length==10&&args.length==12||args.length==14&&args.length==16;
	 * 
	 * (where args is array single argument of method) is following:
	 *  8  aload_0 [args]
	 *  9  arraylength
	 * 10  bipush 8
	 * 12  if_icmpeq 47
	 * 15  aload_0 [args]
	 * 16  arraylength
	 * 17  bipush 10
	 * 19  if_icmpne 29
	 * 22  aload_0 [args]
	 * 23  arraylength
	 * 24  bipush 12
	 * 26  if_icmpeq 47
	 * 29  aload_0 [args]
	 * 30  arraylength
	 * 31  bipush 14
	 * 33  if_icmpne 43
	 * 36  aload_0 [args]
	 * 37  arraylength
	 * 38  bipush 16
	 * 40  if_icmpeq 47
	 * 43  iconst_0
	 * 44  goto 48
	 * 47  iconst_1
	 * 48  istore_1 [rez]
	 */
	public void testORANDORAND(){
		// Repeat test 50 times to detect possible hashing problems
		for(int z=0;z<50;z++){
			MethodCompiler compiler = createCompiler("zzz",
					ALOAD+" 0",
					String.valueOf(ARRAYLENGTH), 
					BIPUSH+" 8",
					IF_ICMPEQ+" L47",
					ALOAD+" 0",
					String.valueOf(ARRAYLENGTH), 
					BIPUSH+" 10",
					IF_ICMPNE+" L29", 
					ALOAD+" 0",
					String.valueOf(ARRAYLENGTH),
					BIPUSH+" 12",
					IF_ICMPEQ+" L47",
					"L29",
					ALOAD+" 0",
					String.valueOf(ARRAYLENGTH),
					BIPUSH+" 14",
					IF_ICMPNE+" L43",
					ALOAD+" 0",
					String.valueOf(ARRAYLENGTH), 
					BIPUSH+" 16",
					IF_ICMPEQ+" L47",
					"L43",
					String.valueOf(ICONST_0),
					GOTO+" L48",
					"L47",
					String.valueOf(ICONST_1),
					"L48"/*,
					ISTORE+" 1"*/);
			
			assertEquals("arguments[0].length==8||!(arguments[0].length!=10)&&arguments[0].length==12||" +
					"(!(arguments[0].length!=14)&&arguments[0].length==16)",
				compiler.getCurrentBuilder().getStack().pop().toString());
		}
	}
	/**
	 * Bytecode equivalent for operation:
	 * 
	 * boolean rez = args.length==8&&args.length==10||args.length==12&&(args.length==14||args.length==16);
	 * 
	 * (where args is array single argument of method) is following:
	 *  8  aload_0 [args]
	 *  9  arraylength
	 * 10  bipush 8
	 * 12  if_icmpne 22
	 * 15  aload_0 [args]
	 * 16  arraylength
	 * 17  bipush 10
	 * 19  if_icmpeq 47
	 * 22  aload_0 [args]
	 * 23  arraylength
	 * 24  bipush 12
	 * 26  if_icmpne 43
	 * 29  aload_0 [args]
	 * 30  arraylength
	 * 31  bipush 14
	 * 33  if_icmpeq 47
	 * 36  aload_0 [args]
	 * 37  arraylength
	 * 38  bipush 16
	 * 40  if_icmpeq 47
	 * 43  iconst_0
	 * 44  goto 48
	 * 47  iconst_1
	 * 48  istore_1 [rez]
	 */
	public void testANDORANDOROR(){
		;
		
		for(int i=-20;i<20;i++){
			assertEquals(i==8&&i==10||i==12&&(i==14||i==16), 
					(!(i!=8)&&(i==10||(!(i!=12)&&(i==14||i==16)))));
			assertEquals(i==8&&i==10||i==12&&(i==14||i==16), 
					(!(i!=8)&&i==10||(!(i!=12)&&(i==14||i==16))));
		}
		// Repeat test 50 times to detect possible hashing problems
		for(int z=0;z<50;z++){
			MethodCompiler compiler = createCompiler("zzz",
					ALOAD+" 0",
					String.valueOf(ARRAYLENGTH), 
					BIPUSH+" 8",
					IF_ICMPNE+" L22",
					ALOAD+" 0",
					String.valueOf(ARRAYLENGTH), 
					BIPUSH+" 10",
					IF_ICMPEQ+" L47", 
					"L22",
					ALOAD+" 0",
					String.valueOf(ARRAYLENGTH),
					BIPUSH+" 12",
					IF_ICMPNE+" L43",
					ALOAD+" 0",
					String.valueOf(ARRAYLENGTH),
					BIPUSH+" 14",
					IF_ICMPEQ+" L47",
					ALOAD+" 0",
					String.valueOf(ARRAYLENGTH), 
					BIPUSH+" 16",
					IF_ICMPEQ+" L47",
					"L43",
					String.valueOf(ICONST_0),
					GOTO+" L48",
					"L47",
					String.valueOf(ICONST_1),
					"L48"/*,
					ISTORE+" 1"*/);
			
			assertEquals("!(arguments[0].length!=8)&&arguments[0].length==10||" +
					"(!(arguments[0].length!=12)&&(arguments[0].length==14||arguments[0].length==16))",
				compiler.getCurrentBuilder().getStack().pop().toString());
		}
	}
	/**
	 * Bytecode equivalent for operation:
	 * 
	 * return args.length==8&&args.length==10||args.length==12;
	 * 
	 * (where args is array single argument of method) is following:
	 * 
	 *  0  aload_0 [args]
	 *  1  arraylength
	 *  2  bipush 8
	 *  4  if_icmpne 14
	 *  7  aload_0 [args]
	 *  8   arraylength
	 *  9  bipush 10
	 * 11  if_icmpeq 23
	 * 14  aload_0 [args]
	 * 15  arraylength
	 * 16  bipush 12
	 * 18  if_icmpeq 23
	 * 21  iconst_0
	 * 22  ireturn
	 * 23  iconst_1
	 * 24  ireturn
	 */
	public void testReturnBoolean(){
		MethodCompiler compiler = createCompiler("zzz",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH), 
				BIPUSH+" 8",
				IF_ICMPNE+" L14", 
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH),
				BIPUSH+" 10",
				IF_ICMPEQ+" L23",
				"L14",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH),
				BIPUSH+" 12",
				IF_ICMPEQ+" L23",
				String.valueOf(ICONST_0),
				String.valueOf(IRETURN),
				"L23",
				String.valueOf(ICONST_1),
				String.valueOf(IRETURN));
		compiler.getCurrentBuilder().getCurrent();
		assertEquals("return(!(arguments[0].length!=8)&&arguments[0].length==10||arguments[0].length==12);",
			compiler.getCurrentBuilder().getCurrent().getOutput());
	}
	/**
	 * Boolean operation within if statement test. Java code:
	 * <code>
	 *  boolean rez = false;
	 *  if(args.length>=1 && args.length<10 || args.length==17 && args.length==22 || args.length == 44 && args.length>1000){
	 *  	System.out.println();
	 *  }
	 *  return rez;
	 * </code>
	 * The following bytecode is produced:
	 *  0  iconst_0
	 *  1  istore_1 [rez]
	 *  2  aload_0 [args]
	 *  3  arraylength
	 *  4  iconst_1
	 *  5  if_icmplt 15
	 *  8  aload_0 [args]
	 *  9  arraylength
	 * 10  bipush 10
	 * 12  if_icmplt 44
	 * 15  aload_0 [args]
	 * 16  arraylength
	 * 17  bipush 17
	 * 19  if_icmpne 29
	 * 22  aload_0 [args]
	 * 23  arraylength
	 * 24  bipush 22
	 * 26  if_icmpeq 44
	 * 29  aload_0 [args]
	 * 30  arraylength
	 * 31  bipush 44
	 * 33  if_icmpne 50
	 * 36  aload_0 [args]
	 * 37  arraylength
	 * 38  sipush 1000
	 * 41  if_icmple 50
	 * 44  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 47  invokevirtual java.io.PrintStream.println() : void [22]
	 * 50  iload_1 [rez]
	 * 51  ireturn 
	 */
	public void testBooleanOperationInIf() {
		MethodCompiler compiler = createCompiler("zzz",
				String.valueOf(ICONST_0),
				ISTORE+" 1",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH),
				String.valueOf(ICONST_1),
				IF_ICMPLT+" L15",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH),
				BIPUSH+" 10",
				IF_ICMPLT+" L44",
				"L15",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH),
				BIPUSH+" 17",
				IF_ICMPNE+" L29",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH),
				BIPUSH+" 22",
				IF_ICMPEQ+" L44",
				"L29",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH),
				BIPUSH+" 44",
				IF_ICMPNE+" L50",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH),
				SIPUSH+" 1000",
				IF_ICMPLE+" L50",
				"L44",
				GETSTATIC+" java/lang/System out java/io/PrintStream",
				INVOKEVIRTUAL+" java/io/PrintStream println ()V",
				"L50",
				ILOAD+" 1",
				String.valueOf(IRETURN));
		compiler.visitEnd();
		assertEquals("arguments[0]=0;if(!(arguments[1].length<1)&&arguments[1].length<10||!(arguments[1].length!=17)&&" +
				"arguments[1].length==22||(!(arguments[1].length!=44)&&!(arguments[1].length<=1000)))" +
				"{System.out['println()']();}return(arguments[0]);};",
				StringUtils.cutWhiteSpaces(compiler.getCompilationBuffer().toString()));
	}
	/**
	 * Complex boolean expression test. Java code:
	 * <code>
	 *  boolean rez = false;
	 *  if (args.length<50) {
	 *  	rez = (args.length==1 || args.length==2) && (args.length==3 || args.length==5) && (args.length==7 || args.length==11);
	 *  }
	 *  return rez;
	 * </code>
	 * The following bytecode is produced:
	 *  0  iconst_0
	 *  1  istore_1 [rez]
	 *  2  aload_0 [args]
	 *  3  arraylength
	 *  4  bipush 50
	 *  6  if_icmpge 53
	 *  9  aload_0 [args]
	 * 10  arraylength
	 * 11  iconst_1
	 * 12  if_icmpeq 21
	 * 15  aload_0 [args]
	 * 16  arraylength
	 * 17  iconst_2
	 * 18  if_icmpne 51
	 * 21  aload_0 [args]
	 * 22  arraylength
	 * 23  iconst_3
	 * 24  if_icmpeq 33
	 * 27  aload_0 [args]
	 * 28  arraylength
	 * 29  iconst_5
	 * 30  if_icmpne 51
	 * 33  aload_0 [args]
	 * 34  arraylength
	 * 35  bipush 7
	 * 37  if_icmpeq 47
	 * 40  aload_0 [args]
	 * 41  arraylength
	 * 42  bipush 11
	 * 44  if_icmpne 51
	 * 47  iconst_1
	 * 48  goto 52
	 * 51  iconst_0
	 * 52  istore_1 [rez]
	 * 53  iload_1 [rez]
	 * 54  ireturn
	 */
	public void testComplexPrioritizedExpression1() {
		MethodCompiler compiler = createCompiler("zzz",
				String.valueOf(ICONST_0),
				ISTORE+" 1",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH),
				BIPUSH+" 50",
				IF_ICMPGE+" L53",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH),
				String.valueOf(ICONST_1),
				IF_ICMPEQ+" L21",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH),
				String.valueOf(ICONST_2),
				IF_ICMPNE+" L51",
				"L21",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH),
				String.valueOf(ICONST_3),
				IF_ICMPEQ+" L33",
				"L27",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH),
				String.valueOf(ICONST_5),
				IF_ICMPNE+" L51",
				"L33",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH),
				BIPUSH+" 7",
				IF_ICMPEQ+" L47",
				"L40",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH),
				BIPUSH+" 11",
				IF_ICMPNE+" L51",
				"L47",
				String.valueOf(ICONST_1),
				GOTO+" L52",
				"L51",
				String.valueOf(ICONST_0),
				"L52",
				ISTORE+" 1",
				"L53",
				ILOAD+" 1",
				String.valueOf(IRETURN));
		compiler.visitEnd();
		assertEquals("arguments[0]=0;if(!(arguments[1].length>=50)){arguments[0]=(arguments[1].length==1||" +
				"!(arguments[1].length!=2))&&(arguments[1].length==3||!(arguments[1].length!=5))&&" +
				"(arguments[1].length==7||!(arguments[1].length!=11));}return(arguments[0]);};",
				StringUtils.cutWhiteSpaces(compiler.getCompilationBuffer().toString()));
	}
	/**
	 * Complex boolean expression test. Java code:
	 * <code>
	 *  boolean rez = false;
	 *  if (args.length<50) {
	 *  	rez = ((((args.length==1 || args.length==2) && args.length==3) || args.length==5) && args.length==7) || args.length==11;
	 *  }
	 *  return rez;
	 * </code>
	 * The following bytecode is produced:
	 *  0  iconst_0
	 *  1  istore_1 [rez]
	 *  2  aload_0 [args]
	 *  3  arraylength
	 *  4  bipush 50
	 *  6  if_icmpge 53
	 *  9  aload_0 [args]
	 * 10  arraylength
	 * 11  iconst_1
	 * 12  if_icmpeq 21
	 * 15  aload_0 [args]
	 * 16  arraylength
	 * 17  iconst_2
	 * 18  if_icmpne 27
	 * 21  aload_0 [args]
	 * 22  arraylength
	 * 23  iconst_3
	 * 24  if_icmpeq 33
	 * 27  aload_0 [args]
	 * 28  arraylength
	 * 29  iconst_5
	 * 30  if_icmpne 40
	 * 33  aload_0 [args]
	 * 34  arraylength
	 * 35  bipush 7
	 * 37  if_icmpeq 51
	 * 40  aload_0 [args]
	 * 41  arraylength
	 * 42  bipush 11
	 * 44  if_icmpeq 51
	 * 47  iconst_0
	 * 48  goto 52
	 * 51  iconst_1
	 * 52  istore_1 [rez]
	 * 53  iload_1 [rez]
	 * 54  ireturn
	 */
	public void testComplexPrioritizedExpression2() {
		MethodCompiler compiler = createCompiler("zzz",
				String.valueOf(ICONST_0),
				ISTORE+" 1",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH),
				BIPUSH+" 50",
				IF_ICMPGE+" L53",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH),
				String.valueOf(ICONST_1),
				IF_ICMPEQ+" L21",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH),
				String.valueOf(ICONST_2),
				IF_ICMPNE+" L27",
				"L21",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH),
				String.valueOf(ICONST_3),
				IF_ICMPEQ+" L33",
				"L27",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH),
				String.valueOf(ICONST_5),
				IF_ICMPNE+" L40",
				"L33",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH),
				BIPUSH+" 7",
				IF_ICMPEQ+" L51",
				"L40",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH),
				BIPUSH+" 11",
				IF_ICMPEQ+" L51",
				"L47",
				String.valueOf(ICONST_0),
				GOTO+" L52",
				"L51",
				String.valueOf(ICONST_1),
				"L52",
				ISTORE+" 1",
				"L53",
				ILOAD+" 1",
				String.valueOf(IRETURN));
		compiler.visitEnd();
		assertEquals("arguments[0]=0;if(!(arguments[1].length>=50)){" +
				"arguments[0]=((arguments[1].length==1||!(arguments[1].length!=2))&&" +
				"arguments[1].length==3||!(arguments[1].length!=5))&&arguments[1].length==7||" +
				"arguments[1].length==11;}return(arguments[0]);};",
				StringUtils.cutWhiteSpaces(compiler.getCompilationBuffer().toString()));
	}
	/**
	 * Complex boolean expression test. Java code:
	 * <code>
	 *  boolean rez = false;
	 *  if (args.length<50) {
	 *  	rez = (args.length==1 || args.length==2) && args.length==3 || args.length==5 || args.length==7;
	 *  }
	 *  return rez;
	 * </code>
	 * The following bytecode is produced:
	 *  0  iconst_0
	 *  1  istore_1 [rez]
	 *  2  aload_0 [args]
	 *  3  arraylength
	 *  4  bipush 50
	 *  6  if_icmpge 46
	 *  9  aload_0 [args]
	 *  10  arraylength
	 *  11  iconst_1
	 *  12  if_icmpeq 21
	 *  15  aload_0 [args]
	 *  16  arraylength
	 *  17  iconst_2
	 *  18  if_icmpne 27
	 *  21  aload_0 [args]
	 *  22  arraylength
	 *  23  iconst_3
	 *  24  if_icmpeq 44
	 *  27  aload_0 [args]
	 *  28  arraylength
	 *  29  iconst_5
	 *  30  if_icmpeq 44
	 *  33  aload_0 [args]
	 *  34  arraylength
	 *  35  bipush 7
	 *  37  if_icmpeq 44
	 *  40  iconst_0
	 *  41  goto 45
	 *  44  iconst_1
	 *  45  istore_1 [rez]
	 *  46  iload_1 [rez]
	 *  47  ireturn
	 */
	public void testComplexPrioritizedExpression3() {
		MethodCompiler compiler = createCompiler("zzz",
				String.valueOf(ICONST_0),
				ISTORE+" 1",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH),
				BIPUSH+" 50",
				IF_ICMPGE+" L46",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH),
				String.valueOf(ICONST_1),
				IF_ICMPEQ+" L21",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH),
				String.valueOf(ICONST_2),
				IF_ICMPNE+" L27",
				"L21",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH),
				String.valueOf(ICONST_3),
				IF_ICMPEQ+" L44",
				"L27",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH),
				String.valueOf(ICONST_5),
				IF_ICMPEQ+" L44",
				"L33",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH),
				BIPUSH+" 7",
				IF_ICMPEQ+" L44",
				"L40",
				String.valueOf(ICONST_0),
				GOTO+" L45",
				"L44",
				String.valueOf(ICONST_1),
				"L45",
				ISTORE+" 1",
				"L46",
				ILOAD+" 1",
				String.valueOf(IRETURN));
		compiler.visitEnd();
		assertEquals("arguments[0]=0;if(!(arguments[1].length>=50)){arguments[0]=(arguments[1].length==1||" +
				"!(arguments[1].length!=2))&&arguments[1].length==3||(arguments[1].length==5||" +
				"arguments[1].length==7);}return(arguments[0]);};",
				StringUtils.cutWhiteSpaces(compiler.getCompilationBuffer().toString()));
	}
	/**
	 * Complex boolean expression test. Java code:
	 * <code>
	 *  boolean rez = false;
	 *  if (args.length<50) {
	 *  	rez = (args.length==1 || args.length==2 || args.length==3 || args.length==5) && args.length==7;
	 *  }
	 *  return rez;
	 * </code>
	 * The following bytecode is produced:
	 *  0  iconst_0
	 *  1  istore_1 [rez]
	 *  2  aload_0 [args]
	 *  3  arraylength
	 *  4  bipush 50
	 *  6  if_icmpge 46
	 *  9  aload_0 [args]
	 * 10  arraylength
	 * 11  iconst_1
	 * 12  if_icmpeq 33
	 * 15  aload_0 [args]
	 * 16  arraylength
	 * 17  iconst_2
	 * 18  if_icmpeq 33
	 * 21  aload_0 [args]
	 * 22  arraylength
	 * 23  iconst_3
	 * 24  if_icmpeq 33
	 * 27  aload_0 [args]
	 * 28  arraylength
	 * 29  iconst_5
	 * 30  if_icmpne 44
	 * 33  aload_0 [args]
	 * 34  arraylength
	 * 35  bipush 7
	 * 37  if_icmpne 44
	 * 40  iconst_1
	 * 41  goto 45
	 * 44  iconst_0
	 * 45  istore_1 [rez]
	 * 46  iload_1 [rez]
	 * 47  ireturn
	 */
	public void testComplexPrioritizedExpression4() {
		MethodCompiler compiler = createCompiler("zzz",
				String.valueOf(ICONST_0),
				ISTORE+" 1",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH),
				BIPUSH+" 50",
				IF_ICMPGE+" L46",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH),
				String.valueOf(ICONST_1),
				IF_ICMPEQ+" L33",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH),
				String.valueOf(ICONST_2),
				IF_ICMPEQ+" L33",
				"L21",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH),
				String.valueOf(ICONST_3),
				IF_ICMPEQ+" L33",
				"L27",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH),
				String.valueOf(ICONST_5),
				IF_ICMPNE+" L44",
				"L33",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH),
				BIPUSH+" 7",
				IF_ICMPNE+" L44",
				"L40",
				String.valueOf(ICONST_1),
				GOTO+" L45",
				"L44",
				String.valueOf(ICONST_0),
				"L45",
				ISTORE+" 1",
				"L46",
				ILOAD+" 1",
				String.valueOf(IRETURN));
		compiler.visitEnd();
		assertEquals("arguments[0]=0;if(!(arguments[1].length>=50)){arguments[0]=(((arguments[1].length==1||" +
				"arguments[1].length==2)||arguments[1].length==3)||!(arguments[1].length!=5))&&" +
				"!(arguments[1].length!=7);}return(arguments[0]);};",
				StringUtils.cutWhiteSpaces(compiler.getCompilationBuffer().toString()));
	}

	/**
	 * Complex boolean expression test. Java code:
	 * <code>
	 *  rez = (((args.length==1 || args.length==2) && args.length==3) || args.length==5) && args.length==7;
	 * </code>
	 * The following bytecode is produced:
	 *  9  aload_0 [args]
	 * 10  arraylength
	 * 11  iconst_1
	 * 12  if_icmpeq 21
	 * 15  aload_0 [args]
	 * 16  arraylength
	 * 17  iconst_2
	 * 18  if_icmpne 27
	 * 21  aload_0 [args]
	 * 22  arraylength
	 * 23  iconst_3
	 * 24  if_icmpeq 33
	 * 27  aload_0 [args]
	 * 28  arraylength
	 * 29  iconst_5
	 * 30  if_icmpne 44
	 * 33  aload_0 [args]
	 * 34  arraylength
	 * 35  bipush 7
	 * 37  if_icmpne 44
	 * 40  iconst_1
	 * 41  goto 45
	 * 44  iconst_0
	 * 45  istore_1 [rez]
	 */
	public void testComplexPrioritizedExpression5() {
		MethodCompiler compiler = createCompiler("zzz",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH),
				String.valueOf(ICONST_1),
				IF_ICMPEQ+" L21",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH),
				String.valueOf(ICONST_2),
				IF_ICMPNE+" L27",
				"L21",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH),
				String.valueOf(ICONST_3),
				IF_ICMPEQ+" L33",
				"L27",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH),
				String.valueOf(ICONST_5),
				IF_ICMPNE+" L44",
				"L33",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH),
				BIPUSH+" 7",
				IF_ICMPNE+" L44",
				"L40",
				String.valueOf(ICONST_1),
				GOTO+" L45",
				"L44",
				String.valueOf(ICONST_0),
				"L45"/*,
				ISTORE+" 1"*/);
		assertEquals("((arguments[0].length==1||!(arguments[0].length!=2))&&" +
				"arguments[0].length==3||!(arguments[0].length!=5))&&!(arguments[0].length!=7)",
			compiler.getCurrentBuilder().getStack().pop().toString());
	}
	/**
	 * Complex boolean expression test. Java code:
	 * <code>
	 *  rez = (((args.length==1 && args.length==2) || args.length==3) || args.length==5) && args.length==7;
	 *  </code>
	 * The following bytecode is produced:
	 *  9  aload_0 [args]
	 * 10  arraylength
	 * 11  iconst_1
	 * 12  if_icmpne 21
	 * 15  aload_0 [args]
	 * 16  arraylength
	 * 17  iconst_2
	 * 18  if_icmpeq 33
	 * 21  aload_0 [args]
	 * 22  arraylength
	 * 23  iconst_3
	 * 24  if_icmpeq 33
	 * 27  aload_0 [args]
	 * 28  arraylength
	 * 29  iconst_5
	 * 30  if_icmpne 44
	 * 33  aload_0 [args]
	 * 34  arraylength
	 * 35  bipush 7
	 * 37  if_icmpne 44
	 * 40  iconst_1
	 * 41  goto 45
	 * 44  iconst_0
	 * 45  istore_1 [rez]
	 */
	public void testComplexPrioritizedExpression6() {
		MethodCompiler compiler = createCompiler("zzz",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH),
				String.valueOf(ICONST_1),
				IF_ICMPNE+" L21",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH),
				String.valueOf(ICONST_2),
				IF_ICMPEQ+" L33",
				"L21",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH),
				String.valueOf(ICONST_3),
				IF_ICMPEQ+" L33",
				"L27",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH),
				String.valueOf(ICONST_5),
				IF_ICMPNE+" L44",
				"L33",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH),
				BIPUSH+" 7",
				IF_ICMPNE+" L44",
				"L40",
				String.valueOf(ICONST_1),
				GOTO+" L45",
				"L44",
				String.valueOf(ICONST_0),
				"L45"/*,
				ISTORE+" 1"*/);
		assertEquals("(!(arguments[0].length!=1)&&arguments[0].length==2||arguments[0]" +
				".length==3||!(arguments[0].length!=5))&&!(arguments[0].length!=7)",
			compiler.getCurrentBuilder().getStack().pop().toString());
	}
}
