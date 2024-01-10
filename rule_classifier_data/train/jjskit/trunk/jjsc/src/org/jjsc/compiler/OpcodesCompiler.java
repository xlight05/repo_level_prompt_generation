package org.jjsc.compiler;

import java.util.regex.Pattern;

import org.jjsc.compiler.javascript.JavaScriptLang;
import org.jjsc.compiler.namespace.LocalNamespace;
import org.jjsc.utils.Log;
import org.jjsc.utils.StringUtils;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodHandle;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
/**
 * Abstract base class for all compilers that represents a 
 * line sequence of opcode commands.
 * Contains all necessary methods to transform non-loop 
 * non-conditional sequence of opcodes into javascript fast 
 * and valid code.
 * @see MethodCompiler
 * @see ConstructorCompiler
 * @see OpcodesGraphBuilder
 * @author alex.bereznevatiy@gmail.com
 */
abstract class OpcodesCompiler implements MethodVisitor, Opcodes {
	private static final Pattern OPERATORS = Pattern.compile("[%+\\-\\*\\&\\^\\/\\<\\>\\?\\:\\!\\|]");
	
	protected final LocalNamespace namespace;

	OpcodesCompiler(LocalNamespace namespace) {
		this.namespace = namespace;
	}
	/**
	 * Prints object value to the compilation output. 
	 * @param value
	 */
	protected abstract void output(Object value);
	/**
	 * Prints {@link String} to the compilation output.
	 * @param value
	 */
	protected abstract void output(String value);
	/**
	 * Prints single char to the compilation output.
	 * @param value
	 */
	protected abstract void output(char value);
	/**
	 * Prints integer value to the compilation output.
	 * @param value
	 */
	protected abstract void output(int value);
	/**
	 * @return reference to the current {@link VirtualStack} object.
	 */
	protected abstract VirtualStack getStack();

	public void visitFrame(int type, int nLocal, Object[] local, int nStack,
			Object[] stack) {}

	public void visitInsn(int opcode) {
		if(opcode == NOP)return;
		if(opcode<=DCONST_1){
			getStack().push(getOpConstant(opcode));
			return;
		}
		if(opcode>=POP&&opcode<=SWAP){
			visitStackInstruction(opcode);
			return;
		}
	    if(opcode>=IADD&&opcode<=LXOR){
	    	visitOperator(opcode);
	    	return;
	    }
	    if(opcode>=I2L&&opcode<=I2S){
	    	visitInternalCast(opcode);
	    	return;
	    }
	    if (opcode>=LCMP && opcode <= DCMPG) {
	    	visitMutliCompare(opcode);
	    	return;
	    }
	    if(opcode>=IRETURN&&opcode<=RETURN){
			visitReturn(opcode);
			return;
	    }
	    if(opcode==ARRAYLENGTH||(opcode>=IALOAD&&opcode<=SALOAD)
	    		||(opcode>=IASTORE&&opcode<=SASTORE)){
	    	visitArrayInstruction(opcode);
	    	return;
	    }
	    if(opcode==ATHROW || opcode==MONITORENTER || opcode == MONITOREXIT) {
	    	visitOtherInstruction(opcode);
	    	return;
	    }
	    Log.warn("visitInsn("+opcode+") is not implemented!");
	}
	
	private void visitMutliCompare(int opcode) {
		getStack().push(JavaScriptLang.compare(getStack().pop(), getStack().pop()));
	}
	private void visitOtherInstruction(int opcode) {
		switch(opcode) {
		case ATHROW:
			output("throw ");
			output(getStack().pop());
			output(';');
			return;
		case MONITORENTER:
		case MONITOREXIT:
			// ignore
			getStack().pop();
			return;
		}
	}
	private void visitArrayInstruction(int opcode){
		switch(opcode) {
		case IASTORE:
		case LASTORE:
		case FASTORE:
		case DASTORE:
		case AASTORE:
		case BASTORE:
		case CASTORE:
		case SASTORE:
			Object val2store = getStack().pop();
			Object storeIndex = getStack().pop();
			StringBuilder result = new StringBuilder();
			result.append(getStack().pop()).append('[');
			result.append(storeIndex).append(']').append('=');
			result.append(val2store).append(';');
			output(result);
			return;
		case ARRAYLENGTH:
			getStack().push(getStack().pop()+".length");
			return;
		case IALOAD:
		case LALOAD:
		case FALOAD:
		case DALOAD:
		case AALOAD:
		case BALOAD:
		case CALOAD:
		case SALOAD:
			StringBuilder value = new StringBuilder();
			Object index = getStack().pop();
			value.append(getStack().pop()).append('[')
				.append(index).append(']');
			getStack().push(value);
		}
	}

	private void visitStackInstruction(int opcode) {
		switch(opcode){
		case POP:
		case POP2:
			output(getStack().pop());
			output(';');
			return;
		case DUP:
		case DUP2:
			String name = namespace.add();
			output("var "+name+'='+getStack().pop()+';');
			getStack().push(name);
			getStack().push(name);
			return;
		case DUP_X1:
		case DUP2_X1:
			String name1 = namespace.add();
			output("var "+name1+'='+getStack().pop()+';');
			Object x12 = getStack().pop();
			getStack().push(name1);
			getStack().push(x12);
			getStack().push(name1);
			return;
		case DUP_X2:
		case DUP2_X2:
			String name2 = namespace.add();
			output("var "+name2+'='+getStack().pop()+';');
			Object x22 = getStack().pop();
			Object x23 = getStack().pop();
			getStack().push(name2);
			getStack().push(x22);
			getStack().push(x23);
			getStack().push(name2);
			return;
		case SWAP:
			Object s1 = getStack().pop();
			Object s2 = getStack().pop();
			getStack().push(s1);
			getStack().push(s2);
			return;
		}
	}

	private void visitInternalCast(int opcode) {
		switch(opcode){
		case L2I:
		case F2I:
		case D2I:
			getStack().push(getStack().pop()+"|0");
			return;
		case I2C:
			getStack().push("String.fromCharCode("+getStack().pop()+')');
			return;
		case I2L:
		case I2F:
		case I2D:
		case L2F:
		case L2D:
		case F2L:
		case F2D:
		case D2L:
		case D2F:
		case I2B:
		case I2S:
			return;
			// ignore - javascript has no equivalent for such a cast
		}
	}

	protected void visitReturn(int opcode) {
		switch(opcode){
		case RETURN:
			output("return;");
			return;
		case IRETURN:
		case LRETURN:
		case FRETURN:
		case DRETURN:
		case ARETURN:
			output("return(");
			output(getStack().pop());
			output(");");
			return;
		}
	}

	private void visitOperator(int opcode) {
		String operator = null;
		switch(opcode){
		case INEG:
		case LNEG:
		case FNEG:
		case DNEG:
			getStack().push("(-"+getStack().pop().toString()+')');
			return;
		case IADD:
		case LADD:
		case FADD:
		case DADD:
			operator = "+";
			break;
		case IMUL:
		case LMUL:
		case FMUL:
		case DMUL:
			operator = "*";
			break;
		case IDIV:
		case LDIV:
		case FDIV:
		case DDIV:
			operator = "/";
			break;
		case ISUB:
		case LSUB:
		case FSUB:
		case DSUB:
			operator = "-";
			break;
		case ISHL:
		case LSHL:
			operator = "<<";
			break;
		case ISHR:
		case LSHR:
			operator = ">>";
			break;
		case IUSHR:
		case LUSHR:
			operator = ">>>";
			break;
		case IAND:
		case LAND:
			operator = "&";
			break;
		case IOR:
		case LOR:
			operator = "|";
			break;
		case IXOR:
		case LXOR:
			operator = "^";
			break;
		case IREM:
		case LREM:
		case FREM:
		case DREM:
			operator = "%";
			break;
		}
		if(operator==null){
			Log.warn("Unknown opcode: "+opcode);
			return;
		}
		final StringBuilder tmp = new StringBuilder();
		final String secondParam = getStack().pop().toString();
		final String firstParam = getStack().pop().toString();
		if (OPERATORS.matcher(firstParam).find()) {
			tmp.append('(').append(firstParam).append(')');
		}
		else {
			tmp.append(firstParam);
		}
		tmp.append(operator);
		if (OPERATORS.matcher(secondParam).find()) {
			tmp.append('(').append(secondParam).append(')');
		}
		else {
			tmp.append(secondParam);
		}
		getStack().push(tmp);
	}

	private Object getOpConstant(int opcode) {
		switch(opcode){
		case ACONST_NULL:
			return "null";
		case ICONST_M1:
			return -1;
		case ICONST_0:
			return 0;
		case ICONST_1:
			return 1;
		case ICONST_2:
			return 2;
		case ICONST_3:
			return 3;
		case ICONST_4:
			return 4;
		case ICONST_5:
			return 5;
		case LCONST_0:
			return 0;
		case LCONST_1:
			return 1;
		case FCONST_1:
			return 1.0;
		case FCONST_2:
			return 2.0;
		case DCONST_0:
			return 0.0;
		case DCONST_1:
			return 1.0;
		}
		throw new InternalError();
	}

	public void visitIntInsn(int opcode, int operand) {
		switch(opcode){
		case BIPUSH: 
		case SIPUSH:
			getStack().push(operand);
			return;
		case NEWARRAY:
			getStack().push(JavaScriptLang.newArray(operand, new Object[]{getStack().pop()}));
			return;
		}
	}

	public void visitVarInsn(int opcode, int var) {
		switch(opcode){
		case LLOAD:
		case DLOAD:
		case ILOAD: 
		case FLOAD:
		case ALOAD:
			getStack().push(namespace.getLocalVar(var));
			return;
		case LSTORE:
		case DSTORE:
		case FSTORE:
		case ISTORE: 
		case ASTORE:
			getStack().push(namespace.getLocalVar(var));
			output(getStack().pop());
			output('=');
			output(getStack().pop());
			output(';');
			return;
		case RET:
			return;
		}
		throw new InternalError();
	}

	public void visitTypeInsn(int opcode, String type) {
		switch(opcode){
		case ANEWARRAY:{
				getStack().push(JavaScriptLang.newArray(namespace.resolve(type), new Object[]{getStack().pop()}));
				return;
			}
		case NEW:{
				StringBuilder result = new StringBuilder();
				result.append("new ").append(namespace.resolve(type)).append("()");
				getStack().push(result);
				return;
			}
		case CHECKCAST:
			getStack().push(JavaScriptLang.checkCast(namespace.resolve(type), getStack().pop()));
			return;
		case INSTANCEOF:
			getStack().push(JavaScriptLang.checkType(namespace.resolve(type), getStack().pop()));
			return;
		}
	}

	public void visitFieldInsn(int opcode, String owner, String name,
			String desc) {
		output('\t');output('\t');
		Object value = null;
		switch(opcode){
		case PUTSTATIC:
			value = getStack().pop();
			output(namespace.resolve(owner));
			output('.');
			break;
		case GETSTATIC:
			name = namespace.resolve(owner)+'.'+name;
			break;
		case PUTFIELD:
			value = getStack().pop();
			output(getStack().pop());
			output('.');
			break;
		case GETFIELD:
			name = String.valueOf(getStack().pop())+'.'+name;
			break;
		}
		
		if(value==null){
			getStack().push(name);
		}
		else{
			output(name);
			output('=');
			output(value);
			output(';');
		}
	}

	public void visitMethodInsn(int opcode, String owner, String name,
			String desc) {
		Object args = loadArgs(desc);
		StringBuilder result = new StringBuilder();
		switch(opcode){
		case INVOKESPECIAL://TODO
		case INVOKEVIRTUAL:
		case INVOKEINTERFACE:
			result.append(getStack().pop());
			break;
		case INVOKESTATIC:
			result.append(namespace.resolve(owner));
		}
		result.append("['");
		result.append(name);
		result.append(desc.substring(0, desc.lastIndexOf(')')+1));
		result.append("']");
		result.append('(');
		result.append(args);
		result.append(")");
		if(desc.charAt(desc.length()-1)!='V'){
			getStack().push(result);
		}
		else {
			output(result);
			output(';');
		}
	}
	
	private String loadArgs(String desc) {
		final int count = namespace.getParameters(desc).size();
		Object[] result = new Object[count];
		for(int i = count-1;i>=0;i--){
			result[i] = getStack().pop();
		}
		
		return StringUtils.join(result,",");
	}

	public void visitInvokeDynamicInsn(String name, String desc,
			MethodHandle bsm, Object... bsmArgs) {
		Log.error("INVOKE_DYNAMIC is not implemented for this compiler");
	}

	public void visitLdcInsn(Object cst) {
		if(cst instanceof String){
			StringBuilder temp = new StringBuilder();
			temp.append('"');
			temp.append(StringUtils.escape(cst.toString()));
			temp.append('"');
			cst = temp;
		}
		else if(cst instanceof Type){
			cst = namespace.resolve(((Type) cst).getInternalName())+"['class']";
		}
		getStack().push(cst);
	}

	public void visitIincInsn(int var, int increment) {
		String varName = namespace.getLocalVar(var);
		int count = getStack().getOccurences(varName);
		if (count>1) {
			String tmpName = namespace.add();
			getStack().replace(varName, tmpName);
			output("var "+tmpName+'='+varName+';');
		}
		StringBuilder expression = new StringBuilder();
		expression.append(varName);
		if(increment==1){
			expression.append("++");
		}
		else if(increment==-1){
			expression.append("--");
		}
		else {
			if(increment>0){
				expression.append("+=");
			}
			else {
				expression.append("-=");
			}
			expression.append(increment);
			expression.append(';');
			output(expression);
			return;
		}
		if (count == 1) {
			getStack().replace(varName, expression.toString());
			return;
		}
		expression.append(';');
		output(expression);
	}

	public void visitMultiANewArrayInsn(String desc, int dims) {
		Object[] dimensions = new Object[dims];
		while(dims-->0) {
			dimensions[dims] = getStack().pop();
		}
		getStack().push(JavaScriptLang.newArray(desc, dimensions));
	}

	public void visitLocalVariable(String name, String desc, String signature,
			Label start, Label end, int index) {
		// all local variables names are ignored because of different visibility scopes in java and javascript
	}

	public void visitLineNumber(int line, Label start) {
		output(JavaScriptLang.debugLine(line,start));
	}
	
	LocalNamespace getNamespace() {
		return namespace;
	}
}
