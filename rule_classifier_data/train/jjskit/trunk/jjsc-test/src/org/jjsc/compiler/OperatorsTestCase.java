package org.jjsc.compiler;

import org.jjsc.utils.StringUtils;

public class OperatorsTestCase extends MethodCompilerTestCase {
	/**
	 * Simple ternary operator of following java code:
	 * <code>
	 *  int rez = args.length==1 && args.length==2 || args.length==3 ? 5 : 12;
	 * </code>
	 * will be compiled in the next bytecode:
	 *  0  aload_0 [args]
	 *  1  arraylength
	 *  2  iconst_1
	 *  3  if_icmpne 12
	 *  6  aload_0 [args]
	 *  7  arraylength
	 *  8  iconst_2
	 *  9  if_icmpeq 18
	 * 12  aload_0 [args]
	 * 13  arraylength
	 * 14  iconst_3
	 * 15  if_icmpne 22
	 * 18  iconst_5
	 * 19  goto 24
	 * 22  bipush 12
	 */
	public void testTernaryOperator() {
		MethodCompiler compiler = createCompiler("zzz",
			ALOAD+" 0",
			String.valueOf(ARRAYLENGTH), 
			String.valueOf(ICONST_1),
			IF_ICMPNE+" L12", 
			ALOAD+" 0",
			String.valueOf(ARRAYLENGTH), 
			String.valueOf(ICONST_2),
			IF_ICMPEQ+" L18",
			"L12",
			ALOAD+" 0",
			String.valueOf(ARRAYLENGTH), 
			String.valueOf(ICONST_3),
			IF_ICMPNE+" L22",
			"L18",
			String.valueOf(ICONST_5),
			GOTO+" L24",
			"L22",
			BIPUSH+" 12",
			"L24");
		String result = compiler.getCurrentBuilder().getStack().pop().toString();
		assertNotNull(result);
		assertTrue("(arguments[0].length!=1||!(arguments[0].length==2))&&arguments[0].length!=3?12:5".equals(result) ||
				"!(arguments[0].length!=1)&&arguments[0].length==2||!(arguments[0].length!=3)?5:12".equals(result));
	}
	/**
	 *  Complex nested ternary operators of following java code:
	 * <code>
	 *  int rez = args.length<0 && args.length==2 || args.length==3 ? 
	 *  	(args.length<-50 ? args.length : args.length+1) : 
	 *  	(args.length>50 ? args.length*2 : args.length-1);
	 * </code>
	 * will be compiled in the next bytecode:
	 *  0  aload_0 [args]
	 *  1  arraylength
	 *  2  ifge 11
	 *  5  aload_0 [args]
	 *  6  arraylength
	 *  7  iconst_2
	 *  8  if_icmpeq 17
	 * 11  aload_0 [args]
	 * 12  arraylength
	 * 13  iconst_3
	 * 14  if_icmpne 36
	 * 17  aload_0 [args]
	 * 18  arraylength
	 * 19  bipush -50
	 * 21  if_icmpge 29
	 * 24  aload_0 [args]
	 * 25  arraylength
	 * 26  goto 54
	 * 29  aload_0 [args]
	 * 30  arraylength
	 * 31  iconst_1
	 * 32  iadd
	 * 33  goto 54
	 * 36  aload_0 [args]
	 * 37  arraylength
	 * 38  bipush 50
	 * 40  if_icmple 50
	 * 43  aload_0 [args]
	 * 44  arraylength
	 * 45  iconst_2
	 * 46  imul
	 * 47  goto 54
	 * 50  aload_0 [args]
	 * 51  arraylength
	 * 52  iconst_1
	 * 53  isub
	 * 54  istore_1 [rez]
	 */
	public void testTwoNestedTernaryOperators() {
		MethodCompiler compiler = createCompiler("zzz",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH), 
				IFGE+" L11",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH), 
				String.valueOf(ICONST_2),
				IF_ICMPEQ+" L17",
				"L11",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH), 
				String.valueOf(ICONST_3),
				IF_ICMPNE+" L36",
				"L17",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH), 
				BIPUSH + " -50",
				IF_ICMPGE+" L29",
				"L24",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH),
				GOTO+" L54",
				"L29",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH), 
				String.valueOf(ICONST_1),
				String.valueOf(IADD),
				GOTO+" L54",
				"L36",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH), 
				BIPUSH + " 50",
				IF_ICMPLE+" L50",
				"L43",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH), 
				String.valueOf(ICONST_2),
				String.valueOf(IMUL),
				GOTO+" L54",
				"L50",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH), 
				String.valueOf(ICONST_1),
				String.valueOf(ISUB),
				"L54");
			String result = compiler.getCurrentBuilder().getStack().pop().toString();
			assertNotNull(result);
			
			String[] tmp = StringUtils.split(result, ")?(");
			assertEquals(2, tmp.length);
			assertEquals("!(arguments[0].length>=0)&&arguments[0].length==2||!(arguments[0].length!=3", tmp[0]);
			
			tmp = StringUtils.split(tmp[1], "):(");
			assertEquals(2, tmp.length);
			
			assertTrue("arguments[0].length>=-50?arguments[0].length+1:arguments[0].length".equals(tmp[0]) ||
					"!(arguments[0].length>=-50)?arguments[0].length:arguments[0].length+1".equals(tmp[0]));
			
			assertTrue("arguments[0].length<=50?arguments[0].length-1:arguments[0].length*2)".equals(tmp[1]) ||
					"!(arguments[0].length<=50)?arguments[0].length*2:arguments[0].length-1)".equals(tmp[1]));
	}
	/**
	 *  Complex nested ternary operator of following java code:
	 * <code>
	 *  int rez = args.length<0 && args.length==2 || args.length==3 ? 
	 *  	(args.length<-50 ? args.length : args.length+1) : 
	 *  	args.length-1;
	 * </code>
	 * will be compiled in the next bytecode:
	 *  0  aload_0 [args]
	 *  1  arraylength
	 *  2  ifge 11
	 *  5  aload_0 [args]
	 *  6  arraylength
	 *  7  iconst_2
	 *  8  if_icmpeq 17
	 * 11  aload_0 [args]
	 * 12  arraylength
	 * 13  iconst_3
	 * 14  if_icmpne 36
	 * 17  aload_0 [args]
	 * 18  arraylength
	 * 19  bipush -50
	 * 21  if_icmpge 29
	 * 24  aload_0 [args]
	 * 25  arraylength
	 * 26  goto 40
	 * 29  aload_0 [args]
	 * 30  arraylength
	 * 31  iconst_1
	 * 32  iadd
	 * 33  goto 40
	 * 36  aload_0 [args]
	 * 37  arraylength
	 * 38  iconst_1
	 * 39  isub
	 * 40  istore_1 [rez]
	 */
	public void testNestedTernaryOperator() {
		MethodCompiler compiler = createCompiler("zzz",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH), 
				IFGE+" L11",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH), 
				String.valueOf(ICONST_2),
				IF_ICMPEQ+" L17",
				"L11",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH), 
				String.valueOf(ICONST_3),
				IF_ICMPNE+" L36",
				"L17",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH), 
				BIPUSH + " -50",
				IF_ICMPGE+" L29",
				"L24",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH),
				GOTO+" L40",
				"L29",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH), 
				String.valueOf(ICONST_1),
				String.valueOf(IADD),
				GOTO+" L40",
				"L36",
				ALOAD+" 0",
				String.valueOf(ARRAYLENGTH), 
				String.valueOf(ICONST_1),
				String.valueOf(ISUB),
				"L40");
			String result = compiler.getCurrentBuilder().getStack().pop().toString();
			assertNotNull(result);
			
			String[] tmp = StringUtils.split(result, ")?(");
			assertEquals(2, tmp.length);
			assertEquals("!(arguments[0].length>=0)&&arguments[0].length==2||!(arguments[0].length!=3", tmp[0]);
			
			tmp = StringUtils.split(tmp[1], "):(");
			assertEquals(2, tmp.length);
			
			assertTrue("arguments[0].length>=-50?arguments[0].length+1:arguments[0].length".equals(tmp[0]) ||
					"!(arguments[0].length>=-50)?arguments[0].length:arguments[0].length+1".equals(tmp[0]));
			
			assertEquals("arguments[0].length-1)", tmp[1]);
	}
	
	public void testBinaryOperations() {
		assertEquals("arguments[0]+1", compileAndReturnStack("(I)V",ILOAD+" 0",ICONST_1, IADD));
		assertEquals("arguments[0]*arguments[1]", compileAndReturnStack("(I)V",ILOAD+" 0",ILOAD+" 1", IMUL));
		MethodCompiler compiler = createCompiler("zzz",IINC+" 2 1");
		compiler.visitEnd();
		assertEquals("arguments[0]++;};", StringUtils.cutWhiteSpaces(compiler.getCompilationBuffer().toString()));
		assertEquals("OperationsMock.internal-1", compileAndReturnStack("(I)V",
				GETSTATIC+" org/jjsc/mock/operations/OperationsMock internal int", ICONST_1, ISUB));
		assertEquals("OperationsMock.internal-10", compileAndReturnStack("(I)V",
				GETSTATIC+" org/jjsc/mock/operations/OperationsMock internal int", BIPUSH+" 10", ISUB));
		assertEquals("OperationsMock.internal+5", compileAndReturnStack("(I)V",
				GETSTATIC+" org/jjsc/mock/operations/OperationsMock internal int", BIPUSH+" 5", IADD));
		assertEquals("OperationsMock.internal/arguments[1]", compileAndReturnStack("(I)V",
				GETSTATIC+" org/jjsc/mock/operations/OperationsMock internal int", ILOAD+" 3", IDIV));
	}
	
	public void testUnaryOperations() {
		assertEquals("arguments[0]+1.0", compileAndReturnStack("(DDI)V",DLOAD+" 0",DCONST_1, DADD));
		assertEquals("arguments[1]+5.0", compileAndReturnStack("(DDI)V",DLOAD+" 2",LDC+" 5.0", DADD));
		MethodCompiler compiler = createCompiler("zzz", "(DDI)V",
				new Object[] {DLOAD+" 0",DUP2, DCONST_1, DADD, DSTORE+ " 0", ILOAD +" 5",
				IINC+" 5 1", I2D, DADD, DSTORE+ " 2"});
		compiler.visitEnd();
		assertEquals("var a=arguments[0];arguments[0]=a+1.0;arguments[1]=a+(arguments[3]++); }; ",
				compiler.getCompilationBuffer().toString().replaceAll("\\s+", " "));
	}
	
	public void testByteOperations() {
		assertEquals("arguments[0]^arguments[1]", compileAndReturnStack("(BBB)V",ILOAD+" 0",ILOAD+" 1", IXOR));
		assertEquals("arguments[0]|arguments[2]", compileAndReturnStack("(BBB)V",ILOAD+" 0",ILOAD+" 2", IOR));
		assertEquals("arguments[1]&arguments[2]", compileAndReturnStack("(BBB)V",ILOAD+" 1",ILOAD+" 2", IAND));
		assertEquals("arguments[1]<<arguments[2]", compileAndReturnStack("(BBB)V",ILOAD+" 1",ILOAD+" 2", ISHL));
		assertEquals("arguments[1]>>arguments[2]", compileAndReturnStack("(BBB)V",ILOAD+" 1",ILOAD+" 2", ISHR));
		assertEquals("arguments[1]>>>arguments[2]", compileAndReturnStack("(BBB)V",ILOAD+" 1",ILOAD+" 2", IUSHR));
	}
	
	public void testPriority() {
		assertEquals("(((arguments[0]|arguments[1])&arguments[2])|(arguments[3]&arguments[4]))|arguments[5]", 
				compileAndReturnStack("(BBB)V",ILOAD+" 0",ILOAD+" 1", IOR, ILOAD+" 2", IAND, ILOAD+" 3", 
						ILOAD+" 4", IAND, IOR, ILOAD+" 5", IOR));
	}
}
