package org.jjsc.compiler;

import java.util.HashMap;
import java.util.Map;

import org.jjsc.CommonJJSTestCase;
import org.jjsc.compiler.namespace.JavaTypesNamespace;
import org.jjsc.utils.Log;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
/**
 * Common abstract superclass for all testcases that relates to {@link MethodCompiler}.
 * Provides ASM framework {@link ClassReader} functionality emulator.
 * 
 * @author alex.bereznevatiy@gmail.com
 */
public abstract class MethodCompilerTestCase extends CommonJJSTestCase implements Opcodes {
	
	protected final MethodCompiler createCompiler(String name, Object...opcodes) {
		return createCompiler(name, "()V", opcodes);
	}
	
	protected final Object compileAndReturnStack(String signature, Object...opcodes) {
		return createCompiler("zzz", signature, opcodes).getCurrentBuilder().getStack().pop();
	}

	protected final MethodCompiler createCompiler(String name, String signature, Object[] opcodes) {
		return createCompiler(name, signature, opcodes, new Object[0]);
	}
	
	protected final MethodCompiler createCompiler(String name, 
			String signature, Object[] opcodes, Object[] exceptions) {
		return createCompiler(name, signature, opcodes, exceptions, ACC_STATIC);
	}
	
	protected final MethodCompiler createCompiler(String name, 
			String signature, Object[] opcodes, Object[] exceptions, int flags) {
		MethodCompiler result = new MethodCompiler(flags, name, signature, 
				new StringBuilder(), new JavaTypesNamespace(null));
		Map<String,Label> labels = new HashMap<String, Label>();
		for (Object ex : exceptions) {
			String[] args = ex.toString().split(" ");
			result.visitTryCatchBlock(
					getLabel(labels, args[1]), 
					getLabel(labels, args[2]), 
					getLabel(labels, args[3]), 
					args[0].length()==0?null:args[0]);
		}
		for(int i = 0; i < opcodes.length; i++){
			try {
				processInstruction(result, opcodes[i].toString(), labels);
			} catch(Throwable th) {
				Log.error("Error while processing opcode at "+i+":"+opcodes[i], th);
				throw new RuntimeException(th);
			}
		}
		return result;
	}

	private void processInstruction(MethodVisitor visitor, String instruction, Map<String,Label> labels) {
		String[] args = instruction.split(" ");
		try {
			int opcode = Integer.parseInt(args[0]);
			processOpcode(visitor, opcode, args, labels);
		} catch(NumberFormatException ex) {
			visitor.visitLabel(getLabel(labels, args[0]));
		}
	}

	private Label getLabel(Map<String, Label> labels, String name) {
		if (!labels.containsKey(name)) {
			labels.put(name, new Label());
		}
		return labels.get(name);
	}

	private void processOpcode(MethodVisitor visitor, int opcode, String[] args, Map<String,Label> labels) {
		switch(opcode){
		case Opcodes.NOP:
		case Opcodes.ACONST_NULL:
		case Opcodes.ICONST_M1:
		case Opcodes.ICONST_0:
		case Opcodes.ICONST_1:
		case Opcodes.ICONST_2:
		case Opcodes.ICONST_3:
		case Opcodes.ICONST_4:
		case Opcodes.ICONST_5:
		case Opcodes.LCONST_0:
		case Opcodes.LCONST_1:
		case Opcodes.FCONST_0:
		case Opcodes.FCONST_1:
		case Opcodes.FCONST_2:
		case Opcodes.DCONST_0:
		case Opcodes.DCONST_1:
		case Opcodes.IALOAD:
		case Opcodes.LALOAD:
		case Opcodes.FALOAD:
		case Opcodes.DALOAD:
		case Opcodes.AALOAD:
		case Opcodes.BALOAD:
		case Opcodes.CALOAD:
		case Opcodes.SALOAD:
		case Opcodes.IASTORE:
		case Opcodes.LASTORE:
		case Opcodes.FASTORE:
		case Opcodes.DASTORE:
		case Opcodes.AASTORE:
		case Opcodes.BASTORE:
		case Opcodes.CASTORE:
		case Opcodes.SASTORE:
		case Opcodes.POP:
		case Opcodes.POP2:
		case Opcodes.DUP:
		case Opcodes.DUP_X1:
		case Opcodes.DUP_X2:
		case Opcodes.DUP2:
		case Opcodes.DUP2_X1:
		case Opcodes.DUP2_X2:
		case Opcodes.SWAP:
		case Opcodes.IADD:
		case Opcodes.LADD:
		case Opcodes.FADD:
		case Opcodes.DADD:
		case Opcodes.ISUB:
		case Opcodes.LSUB:
		case Opcodes.FSUB:
		case Opcodes.DSUB:
		case Opcodes.IMUL:
		case Opcodes.LMUL:
		case Opcodes.FMUL:
		case Opcodes.DMUL:
		case Opcodes.IDIV:
		case Opcodes.LDIV:
		case Opcodes.FDIV:
		case Opcodes.DDIV:
		case Opcodes.IREM:
		case Opcodes.LREM:
		case Opcodes.FREM:
		case Opcodes.DREM:
		case Opcodes.INEG:
		case Opcodes.LNEG:
		case Opcodes.FNEG:
		case Opcodes.DNEG:
		case Opcodes.ISHL:
		case Opcodes.LSHL:
		case Opcodes.ISHR:
		case Opcodes.LSHR:
		case Opcodes.IUSHR:
		case Opcodes.LUSHR:
		case Opcodes.IAND:
		case Opcodes.LAND:
		case Opcodes.IOR:
		case Opcodes.LOR:
		case Opcodes.IXOR:
		case Opcodes.LXOR:
		case Opcodes.I2L:
		case Opcodes.I2F:
		case Opcodes.I2D:
		case Opcodes.L2I:
		case Opcodes.L2F:
		case Opcodes.L2D:
		case Opcodes.F2I:
		case Opcodes.F2L:
		case Opcodes.F2D:
		case Opcodes.D2I:
		case Opcodes.D2L:
		case Opcodes.D2F:
		case Opcodes.I2B:
		case Opcodes.I2C:
		case Opcodes.I2S:
		case Opcodes.LCMP:
		case Opcodes.FCMPL:
		case Opcodes.FCMPG:
		case Opcodes.DCMPL:
		case Opcodes.DCMPG:
		case Opcodes.IRETURN:
		case Opcodes.LRETURN:
		case Opcodes.FRETURN:
		case Opcodes.DRETURN:
		case Opcodes.ARETURN:
		case Opcodes.RETURN:
		case Opcodes.ARRAYLENGTH:
		case Opcodes.ATHROW:
		case Opcodes.MONITORENTER:
		case Opcodes.MONITOREXIT:
		    visitor.visitInsn(opcode);
		    return;
		case Opcodes.BIPUSH:
		case Opcodes.SIPUSH:
		case Opcodes.NEWARRAY:
			visitor.visitIntInsn(opcode, Integer.parseInt(args[1]));
			return;
		case Opcodes.LDC:
			try {
				visitor.visitLdcInsn(Double.valueOf(args[1]));
			}
			catch (NumberFormatException ex) {
				visitor.visitLdcInsn(args[1]);
			}
			return;
		case Opcodes.ILOAD:
		case Opcodes.LLOAD:
		case Opcodes.FLOAD:
		case Opcodes.DLOAD:
		case Opcodes.ALOAD:
		case Opcodes.ISTORE:
		case Opcodes.LSTORE:
		case Opcodes.FSTORE:
		case Opcodes.DSTORE:
		case Opcodes.ASTORE:
		case Opcodes.RET:
			visitor.visitVarInsn(opcode, Integer.parseInt(args[1]));
			return;
		case Opcodes.IINC:
			visitor.visitIincInsn(Integer.parseInt(args[1]), Integer.parseInt(args[2]));
			return;
		case Opcodes.IFEQ:
		case Opcodes.IFNE:
		case Opcodes.IFLT:
		case Opcodes.IFGE:
		case Opcodes.IFGT:
		case Opcodes.IFLE:
		case Opcodes.IF_ICMPEQ:
		case Opcodes.IF_ICMPNE:
		case Opcodes.IF_ICMPLT:
		case Opcodes.IF_ICMPGE:
		case Opcodes.IF_ICMPGT:
		case Opcodes.IF_ICMPLE:
		case Opcodes.IF_ACMPEQ:
		case Opcodes.IF_ACMPNE:
		case Opcodes.GOTO:
		case Opcodes.JSR:
		case Opcodes.IFNULL:
		case Opcodes.IFNONNULL:
			visitor.visitJumpInsn(opcode, getLabel(labels, args[1]));
			return;
		case Opcodes.GETSTATIC:
		case Opcodes.PUTSTATIC:
		case Opcodes.GETFIELD:
		case Opcodes.PUTFIELD:
			visitor.visitFieldInsn(opcode, args[1], args[2], args[3]);
			return;
		case Opcodes.INVOKEVIRTUAL:
		case Opcodes.INVOKESPECIAL:
		case Opcodes.INVOKESTATIC:
		case Opcodes.INVOKEINTERFACE:
			visitor.visitMethodInsn(opcode, args[1], args[2], args[3]);
			return;
		case Opcodes.INVOKEDYNAMIC:
			visitor.visitInvokeDynamicInsn(args[0], args[1], null);
			return;
		case Opcodes.NEW:
		case Opcodes.ANEWARRAY:
		case Opcodes.CHECKCAST:
		case Opcodes.INSTANCEOF:
			visitor.visitTypeInsn(opcode, args[1]);
			return;
		case Opcodes.MULTIANEWARRAY:
			visitor.visitMultiANewArrayInsn(args[1], Integer.parseInt(args[2]));
			return;
		case Opcodes.TABLESWITCH:
			Label[] cases = new Label[args.length-4];
			for(int i=4;i<args.length;i++){
				cases[i-4] = getLabel(labels, args[i]);
			}
			visitor.visitTableSwitchInsn(Integer.parseInt(args[1]), Integer.parseInt(args[2]), 
					getLabel(labels, args[3]), cases);
			return;
		case Opcodes.LOOKUPSWITCH:
			final int COUNT = (int)(args.length/2-1);
			int[] keys = new int[COUNT];
			Label[] lbls = new Label[COUNT];
			for(int i=2;i<args.length;i+=2){
				int j = i/2-1;
				keys[j] = Integer.parseInt(args[i]);
				lbls[j] = getLabel(labels, args[i+1]);
			}
			visitor.visitLookupSwitchInsn(getLabel(labels, args[1]), keys, lbls);
			return;
		default:
			throw new IllegalArgumentException("Wrong opcode: "+opcode);
		}
	}
}
