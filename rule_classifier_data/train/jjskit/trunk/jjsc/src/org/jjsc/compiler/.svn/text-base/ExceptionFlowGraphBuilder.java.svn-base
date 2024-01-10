package org.jjsc.compiler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jjsc.compiler.javascript.JavaScriptLang;
import org.jjsc.compiler.namespace.LocalNamespace;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
/**
 * This visitor provides implementation to analyze code exception handling flow
 * and JSR jump instructions. After that it uses {@link OpcodesGraphBuilder}s
 * to build underlying bytecode parts into javascript.
 * 
 * @author alex.bereznevatiy@gmail.com
 */
final class ExceptionFlowGraphBuilder extends DelayedMethodVisitor {
	private LocalNamespace namespace;

	private List<TryCatchBlock> tryCatchBlocks;
	
	private CodeBlock root;
	private CodeBlock current;
	
	private Set<Label> labels;
	private Set<Label> handlers;
	private Set<Label> jsrs;
	private Map<Label, CodeBlock> blocks; 
	
	private OpcodesGraphBuilder builder;
	private StringBuilder compilationBuffer;
	
	
	public ExceptionFlowGraphBuilder(LocalNamespace localNamespace,
			StringBuilder compilationBuffer) {
		this.namespace = localNamespace;
		this.compilationBuffer = compilationBuffer;
		this.current = root = new CodeBlock(null, null);
		this.tryCatchBlocks = new LinkedList<TryCatchBlock>();
		this.labels = new HashSet<Label>();
		this.handlers = new HashSet<Label>();
		this.jsrs = new HashSet<Label>();
		this.blocks = new HashMap<Label, CodeBlock>();
	}

	public void visitTryCatchBlock(Label start, Label end, Label handler,
			String type) {
		TryCatchBlock block = new TryCatchBlock(start, end, handler, type);
		if (!isNestedHandler(block)) {
			tryCatchBlocks.add(block);
			labels.add(start);
			labels.add(end);
			handlers.add(handler);
		}
	}

	private boolean isNestedHandler(TryCatchBlock handler) {
		TryCatchBlock handelingHandler = null;
		TryCatchBlock otherArea = null;
		for (TryCatchBlock block : tryCatchBlocks) {
			if (block.handler == handler.handler) {
				otherArea = block;
			}
			if (block.handler == handler.start) {
				handelingHandler = block;
			}
		}
		return otherArea != null && handelingHandler != null && handelingHandler.start == otherArea.start &&
			handelingHandler.end != otherArea.end;
	}

	public void visitEnd() {
		builder = new OpcodesGraphBuilder(namespace, compilationBuffer);
		root.process(new ArrayList<TryCatchBlock>());
		builder.visitEnd();
	}

	public int getLineNumber() {
		Instruction instr = getLastProcessedInstruction();
		if (instr == null) {
			return -1;
		}
		return instr.getLineNumber();
	}

	protected boolean processInstruction(MethodVisitor processor,
			Instruction instruction) {
		switch (instruction.getOpcode()) {
		case Opcodes.JSR:
			JumpInstruction jump = (JumpInstruction)instruction;
			OpcodesGraphBuilder builder = (OpcodesGraphBuilder)processor;
			OpcodesGraphBuilder jsrProcessor = new OpcodesGraphBuilder(namespace, new StringBuilder(), builder.getStack());
			builder.getStack().push(JavaScriptLang.UNDEFINED);
			processInstructions(jump.target, null, jsrProcessor);
			jsrProcessor.visitEnd();
			builder.output(jsrProcessor.getCompilationBuffer());
			return true;
		case Opcodes.RET:
			return false;
		}
		return super.processInstruction(processor, instruction);
	}

	@Override
	protected boolean visitProxy(Instruction instruction) {
		Label label = instruction.getLabel();
		int opcode = instruction.getOpcode();
		if (opcode == Opcodes.JSR) {
			jsrs.add(((JumpInstruction)instruction).target);
		}
		if (label != null && (labels.contains(label) || handlers.contains(label)) || jsrs.contains(label)) {
			createNextBlock(label);
		}
		return true;
	}

	private void createNextBlock(Label label) {
		Instruction previous = getPreviousInstruction();
		if (previous != null && isMethodReturn(previous.getOpcode()) && jsrs.contains(label)) {
			current.conclude(label, null);
			current = createBlock(label, null);
			return;
		}
		CodeBlock old = this.current;
		this.current = createBlock(label, null);
		old.conclude(label, this.current);
	}
	
	private boolean isMethodReturn(int opcode) {
		return opcode >= Opcodes.IRETURN && opcode <= Opcodes.RETURN || opcode == Opcodes.ATHROW;
	}

	/**
	 * Gets {@link OpcodesGraphBuilder} assigned. In case {@link #visitEnd()} wasn't called  
	 * artificial builder is returned (for testing purposes). This means that calling to this 
	 * method doesn't change the compilation result.
	 * @return artificial {@link OpcodesGraphBuilder}
	 */
	OpcodesGraphBuilder getBuilder() {
		if (builder != null) {
			return builder;
		}
		OpcodesGraphBuilder artificial = new OpcodesGraphBuilder(namespace, new StringBuilder());
		processInstructions(null, null, artificial);
		// ensure we have clear environment after calling this method
		BooleanExpressionsCompiler.clearBooleanReturnNode();
		return artificial;
	}

	private CodeBlock createBlock(Label start, Label end) {
		if (blocks.get(start) == null) {
			blocks.put(start, new CodeBlock(start, end));
		}
		return blocks.get(start);
	}
	
	private class CodeBlock {
		private Label start;
		private Label end;
		private CodeBlock next;
		
		CodeBlock(Label start, Label end) {
			this.start = start;
			this.end = end;
		}

		void conclude(Label end, CodeBlock next) {
			this.end = end;
			this.next = next;
		}

		CodeBlock process(List<TryCatchBlock> exceptions) {
			Instruction previous = getLastProcessedInstruction();
			if (previous !=null && handlers.contains(start) && isMethodReturn(previous.getOpcode())) {
				// reset builder to exceptional flow since return and throw statements
				// are not processed properly by OpcodesGraphBuilder
				builder.setCurrent(start); 
			}
			exceptions.addAll(findHandlers());
			
			processInstructions(start, end, builder);
			
			if (!exceptions.isEmpty()) {
				processHandlers(exceptions);
			}
			return processBlocks(exceptions, next);
		}

		private CodeBlock processBlocks(List<TryCatchBlock> exceptions, CodeBlock result) {
			while (result != null) {
				if (!exceptions.isEmpty()) {
					return result;
				}
				result = result.process(exceptions);
				processHandlers(exceptions);
			}
			return null;
		}

		private List<TryCatchBlock> findHandlers() {
			List<TryCatchBlock> exceptions = new LinkedList<ExceptionFlowGraphBuilder.TryCatchBlock>();
			Set<Label> ends = new HashSet<Label>();
			for (TryCatchBlock block : tryCatchBlocks) {
				if (block.end == end) {
					exceptions.add(block);
				}
				if (block.start !=  start) {
					continue;
				}
				if (!ends.contains(block.end)) {
					builder.visitTry(start);
					ends.add(block.end);
				}
			}
			return exceptions;
		}

		private void processHandlers(List<TryCatchBlock> exceptions) {
			Set<TryCatchBlock> toRemove = new HashSet<TryCatchBlock>();
			for (int i=0;i<exceptions.size(); i++) {
				TryCatchBlock tryCatchBlock = exceptions.get(i);
				if (tryCatchBlock.start == start) {
					CodeBlock handler = blocks.get(tryCatchBlock.handler);
					builder.visitExceptionHandler(handler.start, tryCatchBlock.start, tryCatchBlock.type);
					toRemove.add(tryCatchBlock);
				}
			}
			exceptions.removeAll(toRemove);
		}
	}
	
	private static class TryCatchBlock {
		private Label start;
		private Label end;
		private Label handler;
		private String type;
		
		TryCatchBlock(Label start, Label end, Label handler,
				String type) {
			this.start = start;
			this.end = end;
			this.handler = handler;
			this.type = type;
		}
	}
	
	public void visitCode() {
	}

	public void visitMaxs(int maxStack, int maxLocals) {
	}
	
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

	public void visitFrame(int type, int nLocal, Object[] local, int nStack,
			Object[] stack) {
	}

	public void visitLocalVariable(String name, String desc, String signature,
			Label start, Label end, int index) {
	}
}