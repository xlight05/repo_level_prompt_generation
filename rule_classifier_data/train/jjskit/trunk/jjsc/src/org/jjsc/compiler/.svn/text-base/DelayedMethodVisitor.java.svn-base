package org.jjsc.compiler;

import java.util.LinkedList;

import org.jjsc.compiler.special.CompilationUtilsManager;
import org.jjsc.utils.AssertUtils;
import org.jjsc.utils.Log;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodHandle;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
/**
 * This abstract class represents all the visitors that may delay the 
 * visiting some part of instructions.
 * 
 * @author alex.bereznevatiy@gmail.com
 */
abstract class DelayedMethodVisitor implements MethodVisitor {
	private LinkedList<Instruction> instructions;
	private Label lastLabel;
	private int lastLine = -1;
	private Instruction lastInstruction;
	/**
	 * Represents opcode instruction with parameters.
	 * @author alex.bereznevatiy@gmail.com
	 */
	protected interface Instruction {
		/**
		 * No opcode instruction. This instruction is not the same as 
		 * {@link Opcodes#NOP} because that one meant empty opcode and this 
		 * one means that instruction is not related to opcode at all.
		 */
		int NO_OPCODE = -1;
		/**
		 * Processes the instruction using visitor passed. 
		 * @param visitor
		 */
		void visit(MethodVisitor visitor);
		/**
		 * @return opcode associated with this instruction or 
		 * {@link #NO_OPCODE} if there aren't any.
		 */
		int getOpcode();
		/**
		 * @return label that precedes this instruction or <code>null</code>
		 * if there aren't any.
		 */
		Label getLabel();
		/**
		 * @return the debug line number of source file associated with this instruction or
		 * -1 if there is no line number associated.
		 */
		int getLineNumber();
	}
	
	DelayedMethodVisitor(){
		instructions = new LinkedList<Instruction>();
	}
	/**
	 * Processes all instructions from label in first parameter (inclusive) till label in
	 * second parameter (exclusive).
	 * @param from label (inclusive) 
	 * 	      or <code>null</code> to process from start.
	 * @param to label (exclusive) 
	 *        or <code>null</code> to process till the end.
	 * @param visitor to process instructions.
	 */
	protected void processInstructions(Label from, Label to, MethodVisitor visitor) {
		AssertUtils.assertNotNull(visitor, "Visitor cannot be NULL");
		MethodVisitor processor = null;
		
		if (from == null) {
			processor = visitor;
		}
		
		for (Instruction instruction : instructions){
			lastInstruction = instruction;
			if (to!=null && instruction.getLabel() == to) {
				processor = null;
			}
			else if (from != null && instruction.getLabel() == from) {
				processor = visitor;
			}
			if (processor!=null && !processInstruction(processor, instruction)) {
				return;
			}
		}
		if (lastLabel != null && processor!=null) {
			processor.visitLabel(lastLabel);
		}
	}
	/**
	 * @return last processed instruction or <code>null</code> if 
	 * there aren't any.
	 */
	protected Instruction getLastProcessedInstruction() {
		return lastInstruction;
	}
	/**
	 * @return last added instruction or <code>null</code> if 
	 * there aren't any.
	 */
	protected Instruction getPreviousInstruction() {
		return instructions.isEmpty()?null:instructions.getLast();
	}
	/**
	 * Visits instruction before storing it in the internal repository.
	 * @param instruction to proxy
	 * @return <code>true</code> if this instruction should be stored 
	 * in the internal repository. Otherwise returns <code>false</code>.
	 */
	protected boolean visitProxy(Instruction instruction) {
		return true;
	}
	/**
	 * Prints instruction description to the {@link System#out}.
	 * @param instruction
	 */
	protected void printInstruction(Instruction instruction) {
		if (instruction.getLabel()!=null) {
			Log.debug(instruction.getLabel()+":");
		}
		StringBuilder result = new StringBuilder();
		if (instruction.getOpcode() != Instruction.NO_OPCODE) {
			result.append(CompilationUtilsManager.getOpcodeName(instruction.getOpcode()));
			result.append(' ');
		}
		result.append(instruction);
		Log.debug(result);
	}
	
	protected boolean processInstruction(MethodVisitor processor,
			Instruction instruction) {
		if (Log.isDebug()) {
			printInstruction(instruction);
		}
		Label label = instruction.getLabel();
		int line = instruction.getLineNumber();
		if (line >= 0) {
			processor.visitLineNumber(line, label);
		}
		if (label != null) {
			processor.visitLabel(label);
		}
		instruction.visit(processor);
		return true;
	}
	
	protected abstract class LabeledInstruction implements Instruction {
		final Label label;
		final int line;
		
		LabeledInstruction(){
			label = lastLabel;
			lastLabel = null;
			line = lastLine;
			lastLine = -1;
		}

		public final Label getLabel() {
			return label;
		}
		
		public final int getLineNumber() {
			return line;
		}
	}
	
	public void visitEnd() {
		lastInstruction = null;
	}
	
	protected class OpcodeInstruction extends LabeledInstruction implements Instruction {
		final int opcode;
		
		OpcodeInstruction(int opcode){
			this.opcode = opcode;
		}

		public void visit(MethodVisitor visitor) {
			visitor.visitInsn(opcode);
		}

		public int getOpcode() {
			return opcode;
		}

		@Override
		public String toString() {
			return "";
		}
	}
	
	public void visitInsn(int opcode) {
		Instruction instruction = new OpcodeInstruction(opcode);
		if (visitProxy(instruction)) {
			instructions.add(instruction);
		}
	}
	
	protected class IntInstruction extends OpcodeInstruction {
		final int operand;

		IntInstruction(int opcode, int operand) {
			super(opcode);
			this.operand = operand;
		}
		
		public void visit(MethodVisitor visitor) {
			visitor.visitIntInsn(opcode, operand);
		}
		
		@Override
		public String toString() {
			return String.valueOf(operand);
		}
	}

	public void visitIntInsn(int opcode, int operand) {
		Instruction instruction = new IntInstruction(opcode, operand);
		if (visitProxy(instruction)) {
			instructions.add(instruction);
		}
	}
	
	protected final class VarInstruction extends IntInstruction {
		VarInstruction(int opcode, int var) {
			super(opcode, var);
		}
		
		public void visit(MethodVisitor visitor) {
			visitor.visitVarInsn(opcode, operand);
		}
	}

	public void visitVarInsn(int opcode, int var) {
		Instruction instruction = new VarInstruction(opcode, var);
		if (visitProxy(instruction)) {
			instructions.add(instruction);
		}
	}
	
	protected final class TypeInstruction extends  OpcodeInstruction {
		final String type;
		
		TypeInstruction(int opcode, String type) {
			super(opcode);
			this.type = type;
		}
		
		public void visit(MethodVisitor visitor) {
			visitor.visitTypeInsn(opcode, type);
		}

		@Override
		public String toString() {
			return type;
		}
	}

	public void visitTypeInsn(int opcode, String type) {
		Instruction instruction = new TypeInstruction(opcode, type);
		if (visitProxy(instruction)) {
			instructions.add(instruction);
		}
	}
	
	protected class FieldInstruction extends OpcodeInstruction {
		final String owner;
		final String name;
		final String desc;
		
		FieldInstruction(int opcode, String owner, String name, String desc) {
			super(opcode);
			this.owner = owner;
			this.name = name;
			this.desc = desc;
		}
		
		public void visit(MethodVisitor visitor) {
			visitor.visitFieldInsn(opcode, owner, name, desc);
		}

		@Override
		public String toString() {
			return owner+' '+name+' '+desc;
		}
	}

	public void visitFieldInsn(int opcode, String owner, String name,
			String desc) {
		Instruction instruction = new FieldInstruction(opcode, owner, name, desc);
		if (visitProxy(instruction)) {
			instructions.add(instruction);
		}
	}
	
	protected final class MethodInstruction extends FieldInstruction {
		MethodInstruction(int opcode, String owner, String name, String desc) {
			super(opcode, owner, name, desc);
		}
		
		public void visit(MethodVisitor visitor) {
			visitor.visitMethodInsn(opcode, owner, name, desc);
		}
	}

	public void visitMethodInsn(int opcode, String owner, String name,
			String desc) {
		Instruction instruction = new MethodInstruction(opcode, owner, name, desc);
		if (visitProxy(instruction)) {
			instructions.add(instruction);
		}
	}
	
	protected final class InvokeDynamicInstruction extends LabeledInstruction implements Instruction {
		final String name;
		final String desc;
		final MethodHandle bsm;
		final Object[] bsmArgs;
		
		InvokeDynamicInstruction(String name, String desc, MethodHandle bsm, Object[] bsmArgs) {
			this.name = name;
			this.desc = desc;
			this.bsm = bsm;
			this.bsmArgs = bsmArgs;
		}
		
		public void visit(MethodVisitor visitor) {
			visitor.visitInvokeDynamicInsn(name, desc, bsm, bsmArgs);
		}

		public int getOpcode() {
			return Opcodes.INVOKEDYNAMIC;
		}

		@Override
		public String toString() {
			return "?";
		}
	}

	public void visitInvokeDynamicInsn(String name, String desc,
			MethodHandle bsm, Object... bsmArgs) {
		Instruction instruction = new InvokeDynamicInstruction(name, desc, bsm, bsmArgs);
		if (visitProxy(instruction)) {
			instructions.add(instruction);
		}
	}
	
	protected final class JumpInstruction extends OpcodeInstruction {
		final Label target;
		
		JumpInstruction(int opcode, Label target) {
			super(opcode);
			this.target = target;
		}
		
		public void visit(MethodVisitor visitor) {
			visitor.visitJumpInsn(opcode, target);
		}

		@Override
		public String toString() {
			return target.toString();
		}
	}

	public void visitJumpInsn(int opcode, Label label) {
		Instruction instruction = new JumpInstruction(opcode, label);
		if (visitProxy(instruction)) {
			instructions.add(instruction);
		}
	}

	public void visitLabel(Label label) {
		lastLabel = label;
	}
	
	protected final class LdcInstruction extends LabeledInstruction implements Instruction {
		final Object cst;
		
		LdcInstruction(Object cst){
			this.cst = cst;
		}

		public void visit(MethodVisitor visitor) {
			visitor.visitLdcInsn(cst);
		}

		public int getOpcode() {
			return Opcodes.LDC;
		}

		@Override
		public String toString() {
			return cst.getClass().toString() + " " + cst.toString();
		}
	}

	public void visitLdcInsn(Object cst) {
		Instruction instruction = new LdcInstruction(cst);
		if (visitProxy(instruction)) {
			instructions.add(instruction);
		}
	}
	
	protected final class IincInstruction extends LabeledInstruction implements Instruction {
		final int var;
		final int increment;
		
		IincInstruction(int var, int increment){
			this.var = var;
			this.increment = increment;
		}

		public void visit(MethodVisitor visitor) {
			visitor.visitIincInsn(var, increment);
		}

		public int getOpcode() {
			return Opcodes.IINC;
		}

		@Override
		public String toString() {
			return String.valueOf(var)+' '+increment;
		}
	}

	public void visitIincInsn(int var, int increment) {
		Instruction instruction = new IincInstruction(var, increment);
		if (visitProxy(instruction)) {
			instructions.add(instruction);
		}
	}
	
	protected final class TableSwitchInstruction extends LabeledInstruction implements Instruction {
		final int min;
		final int max;
		final Label dflt;
		final Label[] labels;
		
		TableSwitchInstruction(int min, int max, Label dflt,
				Label[] labels){
			this.min = min;
			this.max = max;
			this.dflt = dflt;
			this.labels = labels;
		}

		public void visit(MethodVisitor visitor) {
			visitor.visitTableSwitchInsn(min, max, dflt, labels);
		}

		public int getOpcode() {
			return Opcodes.TABLESWITCH;
		}
	}

	public void visitTableSwitchInsn(int min, int max, Label dflt,
			Label... labels) {
		Instruction instruction = new TableSwitchInstruction(min, max, dflt, labels);
		if (visitProxy(instruction)) {
			instructions.add(instruction);
		}
	}
	
	protected final class LookupSwitchInstruction extends LabeledInstruction implements Instruction {
		final Label dflt;
		final int[] keys;
		final Label[] labels;
		
		LookupSwitchInstruction(Label dflt, int[] keys, Label[] labels){
			this.dflt = dflt;
			this.keys = keys;
			this.labels = labels;
		}

		public void visit(MethodVisitor visitor) {
			visitor.visitLookupSwitchInsn(dflt, keys, labels);
		}

		public int getOpcode() {
			return Opcodes.LOOKUPSWITCH;
		}
	}

	public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
		Instruction instruction = new LookupSwitchInstruction(dflt, keys, labels);
		if (visitProxy(instruction)) {
			instructions.add(instruction);
		}
	}
	
	protected final class MultiANewArrayInstruction extends LabeledInstruction implements Instruction {
		final String desc;
		final int dims;
		
		MultiANewArrayInstruction(String desc, int dims){
			this.desc = desc;
			this.dims = dims;
		}

		public void visit(MethodVisitor visitor) {
			visitor.visitMultiANewArrayInsn(desc, dims);
		}

		public int getOpcode() {
			return Opcodes.MULTIANEWARRAY;
		}
	}

	public void visitMultiANewArrayInsn(String desc, int dims) {
		Instruction instruction = new MultiANewArrayInstruction(desc, dims);
		if (visitProxy(instruction)) {
			instructions.add(instruction);
		}
	}

	public void visitLineNumber(int line, Label start) {
		lastLine = line;
	}
}
