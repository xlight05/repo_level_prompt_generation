package org.jjsc.compiler;

import java.util.HashMap;
import java.util.Map;

import org.jjsc.compiler.namespace.LocalNamespace;
import org.jjsc.utils.Log;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.Label;
/**
 * This class represents execution graph for conditional and/or looping
 * sequence of opcodes. This graph will be transformed to javascript code 
 * after construction. Exception handling is also responsibility of this class.
 * @see MethodCompiler
 * @see ConstructorCompiler
 * @see OpcodesCompiler
 * @author alex.bereznevatiy@gmail.com
 */
final class OpcodesGraphBuilder extends OpcodesCompiler {
	private StringBuilder compilationBuffer;
	private OpcodesGraphNode entryPoint;
	private OpcodesGraphNode current;
	private int currentOffset = 1;
	private Map<Label, OpcodesGraphNode> nodes;

	OpcodesGraphBuilder(LocalNamespace namespace, 
			StringBuilder compilationBuffer) {
		this(namespace, compilationBuffer, null);
	}

	public OpcodesGraphBuilder(LocalNamespace namespace, 
			StringBuilder compilationBuffer, VirtualStack stack) {
		super(namespace);
		this.compilationBuffer = compilationBuffer;
		this.entryPoint = new OpcodesGraphNode(stack);
		this.current = this.entryPoint;
		this.nodes = new HashMap<Label, OpcodesGraphNode>();
	}

	@Override
	protected VirtualStack getStack() {
		return current.getStack();
	}
	/**
	 * Searches for the node by label.
	 * @param label
	 * @return node associated with label passed or new node if there aren't any.
	 * Never returns <code>null</code>.
	 */
	OpcodesGraphNode getNode(Label label) {
		if (nodes.get(label) == null) {
			nodes.put(label, new OpcodesGraphNode());
		}
		return nodes.get(label);
	}
	/*
	 * (non-Javadoc)
	 * @see org.objectweb.asm.MethodVisitor#visitJumpInsn(int, org.objectweb.asm.Label)
	 */
	public final void visitJumpInsn(int opcode, Label label) {
		if(opcode==GOTO){
			current = current.createGoto(getNode(label));
			current.setOffset(currentOffset++);
			return;
		}
		Object condition = getCondition(opcode);
		if(condition==null){
			Log.warn("Wrong visitJumpInsn("+opcode+
					", "+label+")");
			return;
		}
		current = current.createCondition(condition, getNode(label));
		current.setOffset(currentOffset++);
	}
	/*
	 * (non-Javadoc)
	 * @see org.jjsc.compiler.OpcodesCompiler#visitReturn(int)
	 */
	protected final void visitReturn(int opcode) {
		if (opcode==RETURN || !new BooleanExpressionsCompiler(current)
				.isReturnBooleanExpressionGraph()){
			super.visitReturn(opcode);
			return;
		}
		current = current.createReturn(currentOffset++);
	}

	private Object getCondition(int opcode) {
		StringBuilder result = new StringBuilder();
		Object arg = getStack().pop();
		switch(opcode){
		case IFEQ:
			result.append('!');
			result.append(arg);
			return result;
		case IFNE:
			return arg;
		case IFLT:
			result.append(arg);
			result.append('<');
			result.append('0');
			return result;
		case IFGE:
			result.append(arg);
			result.append('>');
			result.append('=');
			result.append('0');
			return result;
		case IFGT:
			result.append(arg);
			result.append('>');
			result.append('0');
			return result;
		case IFLE:
			result.append(arg);
			result.append('<');
			result.append('=');
			result.append('0');
			return result;
		case IF_ICMPEQ:
			result.append(getStack().pop());
			result.append('=');
			result.append('=');
			result.append(arg);
			return result;
		case IF_ICMPNE:
			result.append(getStack().pop());
			result.append('!');
			result.append('=');
			result.append(arg);
			return result;
		case IF_ICMPLT:
			result.append(getStack().pop());
			result.append('<');
			result.append(arg);
			return result;
		case IF_ICMPGE:
			result.append(getStack().pop());
			result.append('>');
			result.append('=');
			result.append(arg);
			return result;
		case IF_ICMPGT:
			result.append(getStack().pop());
			result.append('>');
			result.append(arg);
			return result;
		case IF_ICMPLE:
			result.append(getStack().pop());
			result.append('<');
			result.append('=');
			result.append(arg);
			return result;
		case IF_ACMPEQ:
			result.append(getStack().pop());
			result.append('=');
			result.append('=');
			result.append('=');
			result.append(arg);
			return result;
		case IF_ACMPNE:
			result.append(getStack().pop());
			result.append('!');
			result.append('=');
			result.append('=');
			result.append(arg);
			return result;
		case IFNULL:
			result.append(arg);
			result.append("===null");
			return result;
		case IFNONNULL:
			result.append(arg);
			result.append("!==null");
			return result;
		case JSR:
			throw new CompilationError("This builder cannot process JSR instructions.");
		}
		return null;
	}

	public void visitLabel(Label label) {
		current = current.create(getNode(label));
		current.setOffset(currentOffset++);
	}
	/**
	 * Visits the exception handler with label specified for block specified.
	 * @param handler
	 * @param start
	 * @param type
	 */
	void visitExceptionHandler(Label handler, Label start, String type) {
		OpcodesGraphNode node = current.createException(start, getNode(handler), 
				type==null ? null : namespace.resolve(type));
		node.setOffset(currentOffset++);
	}
	/**
	 * Increases try dept on the node with label specified.
	 * @param label
	 */
	void visitTry(Label label) {
		getNode(label).createTry();
	}
	/*
	 * (non-Javadoc)
	 * @see org.objectweb.asm.MethodVisitor#visitTableSwitchInsn(int, int, org.objectweb.asm.Label, org.objectweb.asm.Label[])
	 */
	public void visitTableSwitchInsn(int min, int max, Label dflt,
			Label... labels) {
		int[] keys = new int[labels.length];
		OpcodesGraphNode[] cases = new OpcodesGraphNode[labels.length];
		for(int i=0;i<labels.length;i++){
			keys[i] = min + i;
			cases[i] = getNode(labels[i]);
		}
		current = current.createSwitch(keys, getNode(dflt), labels, cases);
		current.setOffset(currentOffset++);
	}
	/*
	 * (non-Javadoc)
	 * @see org.objectweb.asm.MethodVisitor#visitLookupSwitchInsn(org.objectweb.asm.Label, int[], org.objectweb.asm.Label[])
	 */
	public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
		OpcodesGraphNode[] cases = new OpcodesGraphNode[labels.length];
		for(int i=0;i<labels.length;i++){
			cases[i] = getNode(labels[i]);
		}
		current = current.createSwitch(keys, getNode(dflt), labels, cases);
		current.setOffset(currentOffset++);
	}
	/*
	 * (non-Javadoc)
	 * @see org.objectweb.asm.MethodVisitor#visitTryCatchBlock(org.objectweb.asm.Label, org.objectweb.asm.Label, org.objectweb.asm.Label, java.lang.String)
	 */
	public void visitTryCatchBlock(Label start, Label end, Label handler,
			String type) {
		throw new CompilationError("This class is unable to handle try/catch/finally");
	}
	/*
	 * (non-Javadoc)
	 * @see org.objectweb.asm.MethodVisitor#visitEnd()
	 */
	public final void visitEnd() {
		if (BooleanExpressionsCompiler.hasBooleanReturnNode() &&
				!current.getStack().isEmpty()) {
			super.visitReturn(IRETURN);
		}
		BooleanExpressionsCompiler.clearBooleanReturnNode();
		new CompilationBufferBuilder(this).buildResult();
	}
	/*
	 * (non-Javadoc)
	 * @see org.jjsc.compiler.OpcodesCompiler#output(java.lang.Object)
	 */
	@Override
	protected final void output(Object value) {
		current.output(value);
	}
	/*
	 * (non-Javadoc)
	 * @see org.jjsc.compiler.OpcodesCompiler#output(java.lang.String)
	 */
	@Override
	protected final void output(String value) {
		current.output(value);
	}
	/*
	 * (non-Javadoc)
	 * @see org.jjsc.compiler.OpcodesCompiler#output(char)
	 */
	@Override
	protected final void output(char value) {
		current.output(value);
	}
	/*
	 * (non-Javadoc)
	 * @see org.jjsc.compiler.OpcodesCompiler#output(int)
	 */
	@Override
	protected final void output(int value) {
		current.output(value);
	}
	/**
	 * @return compilation buffer associated with this opcodes graph builder
	 */
	StringBuilder getCompilationBuffer() {
		return compilationBuffer;
	}
	/**
	 * @return entry point for the graph of this opcodes graph builder
	 */
	OpcodesGraphNode getEntryPoint() {
		return entryPoint;
	}
	/**
	 * @return current node for this builder
	 */
	OpcodesGraphNode getCurrent() {
		return current;
	}
	/**
	 * Sets current node for this builder to passed node.
	 * This node should have a root the same as entry point of this builder.
	 * @param current
	 */
	void setCurrent(OpcodesGraphNode current) {
		this.current = current;
	}
	/**
	 * Sets current to the node with label passed.
	 * If there are no such node it will be created.
	 * @param label
	 */
	void setCurrent(Label label) {
		this.current = getNode(label);
	}
	
	// ----------------------- NON-USED METHODS ---------------------------
	
	public AnnotationVisitor visitAnnotationDefault() {
		return null;
	}

	public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
		return null;
	}

	public AnnotationVisitor visitParameterAnnotation(int parameter,
			String desc, boolean visible) {
		return null;
	}

	public void visitAttribute(Attribute attr) {
	}

	public void visitCode() {
	}

	public void visitMaxs(int maxStack, int maxLocals) {
	}
}