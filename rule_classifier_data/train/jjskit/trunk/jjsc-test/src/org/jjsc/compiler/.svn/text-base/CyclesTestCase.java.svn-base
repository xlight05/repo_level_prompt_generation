package org.jjsc.compiler;




public class CyclesTestCase extends MethodCompilerTestCase {

	/**
	 * While cycle with an simple condition:
	 * <code>
	 *  int i=args.length;
	 *  while (i-->0) {
	 *  	System.out.println();
	 *  }
	 * </code>
	 * following bytecode will be produced:
	 *  0  aload_0 [args]
	 *  1  arraylength
	 *  2  istore_1 [i]
	 *  3  goto 12
	 *  6  getstatic java.lang.System.out : java.io.PrintStream [16]
	 *  9  invokevirtual java.io.PrintStream.println() : void [22]
	 * 12  iload_1 [i]
	 * 13  iinc 1 -1 [i]
	 * 16  ifgt 6
	 */
	public void testWhile(){
		MethodCompiler compiler = createCompiler("zzz",
			ALOAD+" 0",
			ARRAYLENGTH,
			ISTORE+ " 1",
			GOTO + " L12",
			"L6",
			GETSTATIC+" java/lang/System out java/io/PrintStream",
			INVOKEVIRTUAL+" java/io/PrintStream println ()V",
			"L12",
			ILOAD+" 1",
			IINC+" 1 -1",
			IFGT+" L6",
			ILOAD+" 1",
			IRETURN);
		
		OpcodesGraphNode node1 = compiler.getCurrentBuilder().getEntryPoint();
		assertNotNull(node1);
		assertNull(node1.getCondition());
		assertNotNull(node1.getConditionalChild());
		assertNull(node1.getDirectChild());
		
		OpcodesGraphNode node2 = node1.getConditionalChild();
		assertNotNull(node2.getCondition());
		assertNotNull(node2.getConditionalChild());
		assertNotNull(node2.getDirectChild());
		assertEquals("arguments[1]-->0", node2.getCondition().toString());
		
		OpcodesGraphNode node4 = node2.getConditionalChild();
		assertEquals("System.out['println()']();", node4.getOutput().toString().trim());
		assertNull(node4.getConditionalChild());
		assertNotNull(node4.getDirectChild());
		assertNull(node4.getCondition());
		assertTrue(node2==node4.getDirectChild());
		
		compiler.visitEnd();
		assertEquals("arguments[1]=arguments[0].length;while(arguments[1]-->0){ System.out['println()']();}return(arguments[1]); }; ",
				compiler.getCompilationBuffer().toString().replaceAll("\\s+", " "));
	}
	/**
	 * For cycle with an simple condition:
	 * <code>
	 *  for (int i=0; i<args.length; i++) {
	 *  	System.out.println();
	 *  }
	 * </code>
	 * following bytecode will be produced:
	 *  0  iconst_0
	 *  1  istore_1 [i]
	 *  2  goto 14
	 *  5  getstatic java.lang.System.out : java.io.PrintStream [16]
	 *  8  invokevirtual java.io.PrintStream.println() : void [22]
	 * 11  iinc 1 1 [i]
	 * 14  iload_1 [i]
	 * 15  aload_0 [args]
	 * 16  arraylength
	 * 17  if_icmplt 5
	 */
	public void testFor(){
		MethodCompiler compiler = createCompiler("zzz","([Ljava/lang/String;)V",
			new Object[]{
				ICONST_0,
				ISTORE+ " 1",
				GOTO + " L14",
				"L5",
				GETSTATIC+" java/lang/System out java/io/PrintStream",
				INVOKEVIRTUAL+" java/io/PrintStream println ()V",
				IINC+" 1 1",
				"L14",
				ILOAD+" 1",
				ALOAD+" 0",
				ARRAYLENGTH,
				IF_ICMPLT+" L5"});
			
		OpcodesGraphNode node1 = compiler.getCurrentBuilder().getEntryPoint();
		assertNotNull(node1);
		assertNull(node1.getCondition());
		assertNotNull(node1.getConditionalChild());
		assertNull(node1.getDirectChild());
		
		OpcodesGraphNode node2 = node1.getConditionalChild();
		assertNotNull(node2.getCondition());
		assertNotNull(node2.getConditionalChild());
		assertNotNull(node2.getDirectChild());
		assertEquals("arguments[1]<arguments[0].length", node2.getCondition().toString());
		
		OpcodesGraphNode node4 = node2.getConditionalChild();
		assertEquals("System.out['println()']();arguments[1]++;", node4.getOutput().toString().trim());
		assertNull(node4.getConditionalChild());
		assertNotNull(node4.getDirectChild());
		assertNull(node4.getCondition());
		assertTrue(node2==node4.getDirectChild());
		
		compiler.visitEnd();
		assertEquals("arguments[1]=0;while(arguments[1]<arguments[0].length){ " +
				"System.out['println()']();arguments[1]++;} }; ",
				compiler.getCompilationBuffer().toString().replaceAll("\\s+", " "));
	}
	/**
	 * Do/while cycle with an simple condition:
	 * <code>
	 *  int i=args.length;
	 *  do {
	 *  	System.out.println();
	 *  } while (i++<args.length) ;
	 * </code>
	 * following bytecode will be produced:
	 *  0  aload_0 [args]
	 *  1  arraylength
	 *  2  istore_1 [i]
	 *  3  getstatic java.lang.System.out : java.io.PrintStream [16]
	 *  6  invokevirtual java.io.PrintStream.println() : void [22]
	 *  9  iload_1 [i]
	 * 10  iinc 1 1 [i]
	 * 13  aload_0 [args]
	 * 14  arraylength
	 * 15  if_icmplt 3
	 */
	public void testDoWhile(){
		MethodCompiler compiler = createCompiler("zzz","([Ljava/lang/String;)V",
			new Object[]{
			ALOAD+" 0",
			ARRAYLENGTH,
			ISTORE+ " 1",
			"L3",
			GETSTATIC+" java/lang/System out java/io/PrintStream",
			INVOKEVIRTUAL+" java/io/PrintStream println ()V",
			ILOAD+" 1",
			IINC+" 1 1",
			ALOAD+" 0",
			ARRAYLENGTH,
			IF_ICMPLT+" L3"});
				
		OpcodesGraphNode node1 = compiler.getCurrentBuilder().getEntryPoint();
		assertNotNull(node1);
		assertNull(node1.getCondition());
		assertNull(node1.getConditionalChild());
		assertNotNull(node1.getDirectChild());
		
		OpcodesGraphNode node2 = node1.getDirectChild();
		assertNotNull(node2.getCondition());
		assertNotNull(node2.getConditionalChild());
		assertNotNull(node2.getDirectChild());
		assertEquals("arguments[1]++<arguments[0].length", node2.getCondition().toString());
		assertTrue(node2.getConditionalChild()==node2);
		
		compiler.visitEnd();
		assertEquals("arguments[1]=arguments[0].length;do{ System.out['println()']();}while(arguments[1]++<arguments[0].length); }; ",
				compiler.getCompilationBuffer().toString().replaceAll("\\s+", " "));
	}
	/**
	 * While cycle with an simple condition and conditional continue statement:
	 * <code>
	 *  int i=args.length;
	 *  while (i-->0) {
	 *  	System.out.println();
	 *  }
	 * </code>
	 * following bytecode will be produced:
	 *  0  iconst_0
	 *  1  istore_1 [i]
	 *  2  goto 20
	 *  5  iload_1 [i]
	 *  6  iconst_2
	 *  7  irem
	 *  8  ifne 14
	 * 11  goto 20
	 * 14  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 17  invokevirtual java.io.PrintStream.println() : void [22]
	 * 20  iload_1 [i]
	 * 21  iinc 1 1 [i]
	 * 24  aload_0 [args]
	 * 25  arraylength
	 * 26  if_icmplt 5
	 * 29  return
	 */
	public void testContinue(){
		MethodCompiler compiler = createCompiler("zzz","([Ljava/lang/String;)V",
			new Object[]{
				ICONST_0,
				ISTORE+ " 1",
				GOTO+" L20",
				"L5",
				// Commented out because compilation produces valid 'if' expression :)
				// This one actually produces dead code, but pretty useful for testing
				/*ILOAD+" 1",
				ICONST_2,
				IREM,
				IFNE+" L14",*/
				GOTO+" L20",
				"L14",
				GETSTATIC+" java/lang/System out java/io/PrintStream",
				INVOKEVIRTUAL+" java/io/PrintStream println ()V",
				"L20",
				ILOAD+" 1",
				IINC+" 1 1",
				ALOAD+" 0",
				ARRAYLENGTH,
				IF_ICMPLT+" L5",
				RETURN});
		compiler.visitEnd();
		assertEquals("arguments[1]=0;while(arguments[1]++<arguments[0].length){continue;}return; }; ",
				compiler.getCompilationBuffer().toString().replaceAll("\\s+", " "));
	}
	/**
	 * While cycle with an simple condition and conditional break statement:
	 * <code>
	 *  int i=0;
	 *  while (i++<args.length)  {
	 *  	if (i%10==0) {
	 *  		break;
	 *  	}
	 *  	System.out.println();
	 *  }
	 *  </code>
	 * following bytecode will be produced:
	 *  0  iconst_0
	 *  1  istore_1 [i]
	 *  2  goto 21
	 *  5  iload_1 [i]
	 *  6  bipush 10
	 *  8  irem
	 *  9  ifne 15
	 * 12  goto 30
	 * 15  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 18  invokevirtual java.io.PrintStream.println() : void [22]
	 * 21  iload_1 [i]
	 * 22  iinc 1 1 [i]
	 * 25  aload_0 [args]
	 * 26  arraylength
	 * 27  if_icmplt 5
	 * 30  return
	 */
	public void testBreak(){
		MethodCompiler compiler = createCompiler("zzz","([Ljava/lang/String;)V",
				new Object[]{
					ICONST_0,
					ISTORE+ " 1",
					GOTO+" L21",
					"L5",
					ILOAD+" 1",
					BIPUSH+" 10",
					IREM,
					IFNE+" L15",
					GOTO+" L30",
					"L15",
					GETSTATIC+" java/lang/System out java/io/PrintStream",
					INVOKEVIRTUAL+" java/io/PrintStream println ()V",
					"L21",
					ILOAD+" 1",
					IINC+" 1 1",
					ALOAD+" 0",
					ARRAYLENGTH,
					IF_ICMPLT+" L5",
					"L30",
					RETURN});
			compiler.visitEnd();
			assertEquals("arguments[1]=0;while(arguments[1]++<arguments[0].length){" +
					"if(!(arguments[1]%10)){break;} System.out['println()']();}return; }; ",
					compiler.getCompilationBuffer().toString().replaceAll("\\s+", " "));
	}
	/**
	 * While cycle with conditional labeled break and labeled continue statements:
	 * <code>
	 *  int i=0;
	 *  A: while (i++<args.length)  {
	 *  	System.out.println();
	 *  	while (i-->0)  {
	 *  		if (i%150==1) {
	 *  			System.out.println();
	 *  			break A;
	 *  		}
	 *  		if (i%10==0) {
	 *  			System.out.println();
	 *  			continue A;
	 *  		}
	 *  		System.out.println();
	 *  	}
	 *  	System.out.println();
	 *  }
	 *  </code>
	 * following bytecode will be produced:
	 *  0  iconst_0
	 *  1  istore_1 [i]
	 *  2  goto 67
	 *  5  getstatic java.lang.System.out : java.io.PrintStream [16]
	 *  8  invokevirtual java.io.PrintStream.println() : void [22]
	 * 11  goto 54
	 * 14  iload_1 [i]
	 * 15  sipush 150
	 * 18  irem
	 * 19  iconst_1
	 * 20  if_icmpne 32
	 * 23  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 26  invokevirtual java.io.PrintStream.println() : void [22]
	 * 29  goto 76
	 * 32  iload_1 [i]
	 * 33  bipush 10
	 * 35  irem
	 * 36  ifne 48
	 * 39  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 42  invokevirtual java.io.PrintStream.println() : void [22]
	 * 45  goto 67
	 * 48  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 51  invokevirtual java.io.PrintStream.println() : void [22]
	 * 54  iload_1 [i]
	 * 55  iinc 1 -1 [i]
	 * 58  ifgt 14
	 * 61  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 64  invokevirtual java.io.PrintStream.println() : void [22]
	 * 67  iload_1 [i]
	 * 68  iinc 1 1 [i]
	 * 71  aload_0 [args]
	 * 72  arraylength
	 * 73  if_icmplt 5
	 * 76  return
	 */
	public void testBreakContinueLabel(){
		MethodCompiler compiler = createCompiler("zzz","([Ljava/lang/String;)V",
			new Object[]{
				ICONST_0,
				ISTORE+ " 1",
				GOTO+" L67",
				"L5",
				GETSTATIC+" java/lang/System out java/io/PrintStream",
				INVOKEVIRTUAL+" java/io/PrintStream println ()V",
				GOTO+" L54",
				"L14",
				ILOAD+" 1",
				SIPUSH+" 150",
				IREM,
				ICONST_1,
				IF_ICMPNE+" L32",
				GETSTATIC+" java/lang/System out java/io/PrintStream",
				INVOKEVIRTUAL+" java/io/PrintStream println ()V",
				GOTO+" L76",
				"L32",
				ILOAD+" 1",
				BIPUSH+" 10",
				IREM,
				IFNE+" L48",
				GETSTATIC+" java/lang/System out java/io/PrintStream",
				INVOKEVIRTUAL+" java/io/PrintStream println ()V",
				GOTO+" L67",
				"L48",
				GETSTATIC+" java/lang/System out java/io/PrintStream",
				INVOKEVIRTUAL+" java/io/PrintStream println ()V",
				"L54",
				ILOAD+" 1",
				IINC+" 1 -1",
				IFGT+" L14",
				GETSTATIC+" java/lang/System out java/io/PrintStream",
				INVOKEVIRTUAL+" java/io/PrintStream println ()V",
				"L67",
				ILOAD+" 1",
				IINC+" 1 1",
				ALOAD+" 0",
				ARRAYLENGTH,
				IF_ICMPLT+ " L5",
				"L76",
				RETURN});
		compiler.visitEnd();
		assertEquals("arguments[1]=0;L15:L13:while(arguments[1]++<arguments[0].length){ " +
				"System.out['println()']();while(arguments[1]-->0){if(!(arguments[1]%150!=1)){" +
				"break L15;}if(!(arguments[1]%10)){continue L13;} System.out['println()']();} " +
				"System.out['println()']();}return; }; ",
				compiler.getCompilationBuffer().toString().replaceAll("\\s+", " "));
	}
	/**
	 * For-each style of cycle:
	 * <code>
	 *  for(String arg : args){
	 *  	System.out.println(arg);
	 *  }
	 *  </code>
	 * following bytecode will be produced:
	 *  0  aload_0 [args]
	 *  1  dup
	 *  2  astore 4
	 *  4  arraylength
	 *  5  istore_3
	 *  6  iconst_0
	 *  7  istore_2
	 *  8  goto 26
	 * 11  aload 4
	 * 13  iload_2
	 * 14  aaload
	 * 15  astore_1 [arg]
	 * 16  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 19  aload_1 [arg]
	 * 20  invokevirtual java.io.PrintStream.println(java.lang.String) : void [22]
	 * 23  iinc 2 1
	 * 26  iload_2
	 * 27  iload_3
	 * 28  if_icmplt 11
	 * 31  return
	 */
	public void testForIn(){
		MethodCompiler compiler = createCompiler("zzz","([Ljava/lang/String;)V",
			new Object[]{
				ILOAD+" 0",
				DUP,
				ASTORE+" 4",
				ARRAYLENGTH,
				ISTORE+" 3",
				ICONST_0,
				ISTORE+" 2",
				GOTO+" L26",
				"L11",
				ALOAD+" 4",
				ILOAD+" 2",
				AALOAD,
				ASTORE+" 1",
				GETSTATIC+" java/lang/System out java/io/PrintStream",
				ALOAD+" 1",
				INVOKEVIRTUAL+" java/io/PrintStream println (Ljava/lang/String;)V",
				IINC+" 2 1",
				"L26",
				ILOAD+" 2",
				ILOAD+" 3",
				IF_ICMPLT+" L11",
				RETURN});
		compiler.visitEnd();
		assertEquals("var a=arguments[0];arguments[1]=a;arguments[2]=a.length;" +
				"arguments[3]=0;while(arguments[3]<arguments[2]){arguments[4]=arguments[1]" +
				"[arguments[3]]; System.out['println(Ljava/lang/String;)'](arguments[4]);arguments[3]++;}return; }; ",
			compiler.getCompilationBuffer().toString().replaceAll("\\s+", " "));
	}
	/**
	 * Simple nesting of do/while in the while cycle:
	 * <code>
	 *  while (System.out.checkError()) {
	 *  	do {
	 *  		System.out.println();
	 *  	} while (System.out != null);
	 *  }
	 * </code>
	 * will produce following bytecode:
	 *  0  goto 15
	 *  3  getstatic java.lang.System.out : java.io.PrintStream [16]
	 *  6  invokevirtual java.io.PrintStream.println() : void [22]
	 *  9  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 12  ifnonnull 3
	 * 15  getstatic java.lang.System.out : java.io.PrintStream [16]
	 * 18  invokevirtual java.io.PrintStream.checkError() : boolean [27]
	 * 21  ifne 3
	 * 24  return
	 */
	public void testNestedCycles(){
		MethodCompiler compiler = createCompiler("zzz","([Ljava/lang/String;)V",
			new Object[]{
				GOTO+" L15",
				"L3",
				GETSTATIC+" java/lang/System out java/io/PrintStream",
				INVOKEVIRTUAL+" java/io/PrintStream println ()V",
				GETSTATIC+" java/lang/System out java/io/PrintStream",
				IFNONNULL+" L3",
				"L15",
				GETSTATIC+" java/lang/System out java/io/PrintStream",
				INVOKEVIRTUAL+" java/io/PrintStream checkError ()Z",
				IFNE+" L3",
				RETURN});
		compiler.visitEnd();
		assertEquals(" while(System.out['checkError()']()){do{ System.out['println()'](); }while(System.out!==null); }return; }; ",
			compiler.getCompilationBuffer().toString().replaceAll("\\s+", " "));
	}
	/**
	 * Complex code to analyse:
	 * <code>
	 *  PrintStream out = System.out;
	 *  A: {
	 *  	out.println();
	 *  	while(out.checkError()){
	 *  		out.println();
	 *  		D: if(args.length>0){
	 *  			do {
	 *  				if(args.length==5){
	 *  					continue;
	 *  				}
	 *  				if(args.length==20){
	 *  					break D;
	 *  				}
	 *  				if(args.length==21){
	 *  					break A;
	 *  				}
	 *  			}
	 *  			while(args!=null);
	 *  		}
	 *  	}
	 *  	B: {
	 *  		switch(args.length){
	 *  		case 1:
	 *  		case 5:
	 *  		case 12:
	 *  			break B;
	 *  		}
	 *  		C: while(args.length==5){
	 *  			for(int i=0;i<args.length;i++){
	 *  				out.println(args[i]);
	 *  				if(args.length>20){
	 *  					break C;
	 *  				}
	 *  				else if(args.length==0){
	 *  					break;
	 *  				}
	 *  			}
	 *  		}
	 *  	}
	 *  }
	 * </code>
	 * Bytecode is not provided here because its too long.
	 */
	public void testNestedComplexCycles(){
		MethodCompiler compiler = createCompiler("zzz","([Ljava/lang/String;)V",
				new Object[]{
					"L0",
					GETSTATIC+" java/lang/System out java/io/PrintStream",
					ASTORE+" 1",
					ALOAD+" 1",
					INVOKEVIRTUAL+" java/io/PrintStream println ()V",
					GOTO+" L53",
					"L11",
					ALOAD+" 1",
					INVOKEVIRTUAL+" java/io/PrintStream println ()V",
					ALOAD+" 0",
					ARRAYLENGTH,
					IFLE+" L53",
					"L20",
					ALOAD+" 0",
					ARRAYLENGTH,
					ICONST_5,
					IF_ICMPNE+" L29",
					GOTO+" L49",
					"L29",
					ALOAD+" 0",
					ARRAYLENGTH,
					BIPUSH+" 20",
					IF_ICMPNE+" L39",
					GOTO+" L53",
					"L39",
					ALOAD+" 0",
					ARRAYLENGTH,
					BIPUSH+" 21",
					IF_ICMPNE+" L49",
					GOTO+" L147",
					"L49",
					ALOAD+" 0",
					IFNONNULL+" L20",
					"L53",
					ALOAD+" 1",
					INVOKEVIRTUAL+" java/io/PrintStream checkError ()Z",
					IFNE+" L11",
					ALOAD+" 0",
					ARRAYLENGTH,
					LOOKUPSWITCH+" L99 1 L96 5 L96 12 L96",
					"L96",
					GOTO+" L147",
					"L99",
					GOTO+" L141",
					"L102",
					ICONST_0,
					ISTORE+" 2",
					GOTO+" L135",
					"L107",
					ALOAD+" 1",
					ALOAD+" 0",
					ILOAD+" 2",
					AALOAD,
					INVOKEVIRTUAL+" java/io/PrintStream println (Ljava/lang/String;)V",
					ALOAD+" 0",
					ARRAYLENGTH,
					BIPUSH+" 20",
					IF_ICMPLE+" L124",
					GOTO+" L147",
					"L124",
					ALOAD+" 0",
					ARRAYLENGTH,
					IFNE+" L132",
					GOTO+" L141",
					"L132",
					IINC+" 2 1",
					"L135",
					ILOAD+" 2",
					ALOAD+" 0",
					ARRAYLENGTH,
					IF_ICMPLT+" L107",
					"L141",
					ALOAD+" 0",
					ARRAYLENGTH,
					ICONST_5,
					IF_ICMPEQ+" L102",
					"L147",
					RETURN});
			compiler.visitEnd();
			assertEquals("L36:{ arguments[1]=System.out;arguments[1]['println()']();" +
					"L16:while(arguments[1]['checkError()']()){" +
					"arguments[1]['println()']();" +
					"if(!(arguments[0].length<=0)){" +
					"do{" +
					"if(!(arguments[0].length!=5)){continue;}" +
					"if(!(arguments[0].length!=20)){continue L16;}" +
					"if(!(arguments[0].length!=21)){break L36;}" +
					"}while(arguments[0]!==null);" +
					"}}switch(arguments[0].length){ " +
					"case 1: case 5: case 12: break; " +
					"default:while(arguments[0].length==5){" +
					"arguments[2]=0;L34:while(arguments[2]<arguments[0].length){" +
					"arguments[1]['println(Ljava/lang/String;)'](arguments[0][arguments[2]]);" +
					"if(!(arguments[0].length<=20)){break L36;}" +
					"if(!(arguments[0].length)){continue L34;}" +
					"arguments[2]++;" +
					"}}break; }}return; }; ",
				compiler.getCompilationBuffer().toString().replaceAll("\\s+", " "));
	}
	/**
	 * Breaking instruction for outside label:
	 * <code>
	 * PrintStream out = System.out;
	 *  A: {
	 *  	out.println();
	 *  	while(out.checkError()){
	 *  		out.println();
	 *  		if(args.length>0){
	 *  			out.println();
	 *  			break A;
	 *  		}
	 *  		out.println();
	 *  		out.println();
	 *  	}
	 *  	out.println();
	 *  }
	 *  out.println();
	 *  while (out.checkError()) {
	 *  	out.println();
	 *  }
	 *  out.println();
	 * </code>
	 * following bytecode will be produces: 
	 *   0  getstatic java.lang.System.out : java.io.PrintStream [16]
	 *   3  astore_1 [out]
	 *   4  aload_1 [out]
	 *   5  invokevirtual java.io.PrintStream.println() : void [22]
	 *   8  goto 35
	 *  11  aload_1 [out]
	 *  12  invokevirtual java.io.PrintStream.println() : void [22]
	 *  15  aload_0 [args]
	 *  16  arraylength
	 *  17  ifle 27
	 *  20  aload_1 [out]
	 *  21  invokevirtual java.io.PrintStream.println() : void [22]
	 *  24  goto 46
	 *  27  aload_1 [out]
	 *  28  invokevirtual java.io.PrintStream.println() : void [22]
	 *  31  aload_1 [out]
	 *  32  invokevirtual java.io.PrintStream.println() : void [22]
	 *  35  aload_1 [out]
	 *  36  invokevirtual java.io.PrintStream.checkError() : boolean [27]
	 *  39  ifne 11
	 *  42  aload_1 [out]
	 *  43  invokevirtual java.io.PrintStream.println() : void [22]
	 *  46  aload_1 [out]
	 *  47  invokevirtual java.io.PrintStream.println() : void [22]
	 *  50  goto 57
	 *  53  aload_1 [out]
	 *  54  invokevirtual java.io.PrintStream.println() : void [22]
	 *  57  aload_1 [out]
	 *  58  invokevirtual java.io.PrintStream.checkError() : boolean [27]
	 *  61  ifne 53
	 *  64  aload_1 [out]
	 *  65  invokevirtual java.io.PrintStream.println() : void [22]
	 *  68  return
	 */
	public void testBreakLabelInTheMiddle() {
		MethodCompiler compiler = createCompiler("zzz","([Ljava/lang/String;)V",
				new Object[]{
					"L0",
					GETSTATIC+" java/lang/System out java/io/PrintStream",
					ASTORE+" 1",
					ALOAD+" 1",
					INVOKEVIRTUAL+" java/io/PrintStream println ()V",
					GOTO+" L35",
					"L11",
					ALOAD+" 1",
					INVOKEVIRTUAL+" java/io/PrintStream println ()V",
					ALOAD+" 0",
					ARRAYLENGTH,
					IFLE+" L27",
					ALOAD+" 1",
					INVOKEVIRTUAL+" java/io/PrintStream println ()V",
					GOTO+" L46",
					"L27",
					ALOAD+ " 1",
					INVOKEVIRTUAL+" java/io/PrintStream println ()V",
					ALOAD+ " 1",
					INVOKEVIRTUAL+" java/io/PrintStream println ()V",
					"L35",
					ALOAD+ " 1",
					INVOKEVIRTUAL+" java/io/PrintStream checkError ()Z",
					IFNE+" L11",
					ALOAD+ " 1",
					INVOKEVIRTUAL+" java/io/PrintStream println ()V",
					"L46",
					ALOAD+ " 1",
					INVOKEVIRTUAL+" java/io/PrintStream println ()V",
					GOTO+" L57",
					"L53",
					ALOAD+ " 1",
					INVOKEVIRTUAL+" java/io/PrintStream println ()V",
					"L57",
					ALOAD+ " 1",
					INVOKEVIRTUAL+" java/io/PrintStream checkError ()Z",
					IFNE+" L53",
					ALOAD+ " 1",
					INVOKEVIRTUAL+" java/io/PrintStream println ()V",
					RETURN});
			compiler.visitEnd();
			assertEquals("L9:{ arguments[1]=System.out;arguments[1]['println()']();" +
					"while(arguments[1]['checkError()']()){arguments[1]['println()']();" +
					"if(!(arguments[0].length<=0)){break L9;}arguments[1]['println()']();" +
					"arguments[1]['println()']();}arguments[1]['println()']();}" +
					"arguments[1]['println()']();while(arguments[1]['checkError()']()){" +
					"arguments[1]['println()']();}arguments[1]['println()']();return; }; ",
				compiler.getCompilationBuffer().toString().replaceAll("\\s+", " "));
	}
	/**
	 * Infinite while loop:
	 * <code>
	 *  while(true) {
	 *  	System.out.println();
	 *  }
	 * </code>
	 * , infinite for loop
	 * <code>
	 *  for(;;) {
	 *  	System.out.println();
	 *  }
	 * </code>
	 * and infinite do/while loop:
	 * <code>
	 *  do {
	 *  	System.out.println();
	 *  } while(true);
	 * </code>
	 * all tree will produce following bytecode: 
	 *  0  getstatic java.lang.System.out : java.io.PrintStream [16]
	 *  3  invokevirtual java.io.PrintStream.println() : void [22]
	 *  6  goto 0
	 */
	public void testInfiniteLoop () {
		MethodCompiler compiler = createCompiler("zzz","()V",
			new Object[]{
				"L0",
				GETSTATIC+" java/lang/System out java/io/PrintStream",
				INVOKEVIRTUAL+" java/io/PrintStream println ()V",
				GOTO+" L0"});
		compiler.visitEnd();
		assertEquals("do{ System.out['println()']();}while(true); }; ",
			compiler.getCompilationBuffer().toString().replaceAll("\\s+", " "));
	}
}
