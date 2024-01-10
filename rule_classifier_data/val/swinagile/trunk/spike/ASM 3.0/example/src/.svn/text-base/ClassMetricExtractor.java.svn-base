import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.util.TraceMethodVisitor;

/**
 * Data class - This will hold the metrics for a class ClassMetricData
 * @author rvasa
 */
public class ClassMetricExtractor
{

	String className; // Fully-qualified class name
	String superClassName;

	public int superClassCount = 0; // will be set to 1, if it is not java/lang/Object
	public int fanOutCount = 0; // Non-primitive dependencies, including library deps
	public int internalFanOutCount = 0;
	public int fanInCount = 0;
	public int branchCount = 0; // Total number of branch instructions
	public int externalMethodCallCount = 0;
	public int internalMethodCallCount = 0;
	public int methodCount = 0; // including constructions & static initializers

	int interfaceCount = 0; // Number of implemented interfaces
	int fieldCount = 0; // all fields defined in this class
	int innerClassCount = 0;
	boolean isInterface = false;
	boolean isAbstract = false;

	int refLoadOpCount = 0; // Number of reference loads
	int refStoreOpCount = 0; // Number of reference stores
	int loadFieldCount = 0; // Number of times a field was loaded
	int storeFieldCount = 0; // Number of times a field was stored
	int tryCatchBlockCount = 0; // Number of try-catch blocks
	int localVarCount = 0;
	int privateMethodCount = 0;
	int constantLoadCount = 0;
	int incrementOpCount = 0;

	// set of classes that this class depends on including lib. classes
	Set<String> dependencies = new HashSet<String>(); 

	int loadCount = 0;
	int storeCount = 0;
	int typeInsnCount = 0;
	int zeroOpInsnCount = 0;
	double computedDistance = -1.0; // used to store distance once computed
	public double instability = 0.0;
	public int layer = 0; // Currenly only 3 layers are extracted [top/Mid/bottom]
	
	/** Demo code that shows how this the class actually works/can be used */
	public static void main(String args[])
	{
		if (args.length == 0) printUsageAndExit("ERR: No arguments provided");
		
		try
		{
			System.out.println("Processing: "+args[0]);
			InputStream is = new FileInputStream(args[0]);
			ClassMetricExtractor cme = new ClassMetricExtractor(is);
			System.out.println(cme);
		}
		catch (FileNotFoundException e)
		{
			printUsageAndExit("ERR: File "+args[0]+" not found");
		}
		catch (IOException iox)
		{
			printUsageAndExit("ERR: I/O error while processing the input file");
		}
	}

	private static void printUsageAndExit(String err)
	{
		System.out.println(err);
		System.out.println("Usage: java ClassMetricExtractor [compiled-class-name]");
		System.out.println("Example: java ClassMetricExtractor HelloWorld.class");
		System.exit(1);
	}

	/** Reads the bytecode for an individual class, and will extract all metrics */
	public ClassMetricExtractor(InputStream istream) throws IOException
	{
		ClassReader cr = new ClassReader(istream);
		ClassNode cn = new ClassNode();
		cr.accept(cn, ClassReader.SKIP_DEBUG);
		extractClassMetrics(cn);
		extractMethodMetrics(cn);
		extractDependencies(cn);
	}

	/** Extract metrics from the bytecode using the library 
	 *  TODO: Requires major refactoring to reduce complexity and improve readability
	 */
	private void extractMethodMetrics(ClassNode cn)
	{
		List methods = cn.methods;
		for (int i = 0; i < methods.size(); ++i)
		{
			MethodNode method = (MethodNode) methods.get(i);
			localVarCount += method.maxLocals;

			if ((method.access & Opcodes.ACC_PRIVATE) != 0) privateMethodCount++;
			if (method.instructions.size() > 0)
			{
				TraceMethodVisitor mv = new TraceMethodVisitor() {
					public void visitVarInsn(int opcode, int var)
					{
						if (opcode >= Opcodes.ILOAD && opcode <= Opcodes.DLOAD) loadCount++;
						if (opcode >= Opcodes.ISTORE && opcode <= Opcodes.DSTORE)
							storeCount++;
						if (opcode == Opcodes.ALOAD) refLoadOpCount++;
						if (opcode == Opcodes.ASTORE) refStoreOpCount++;

					}

					public void visitTryCatchBlock(Label start, Label end, Label handler,
							String type)
					{
						tryCatchBlockCount++;
					}

					public void visitFieldInsn(int opcode, String owner, String name,
							String desc)
					{
						if (opcode == Opcodes.PUTFIELD || opcode == Opcodes.PUTSTATIC)
							storeFieldCount++;
						else if (opcode == Opcodes.GETFIELD || opcode == Opcodes.GETSTATIC)
							loadFieldCount++;
					}

					public void visitJumpInsn(int opcode, Label label)
					{
						if (opcode == Opcodes.GOTO) branchCount++;
					}

					public void visitTypeInsn(int opcode, String desc)
					{
						typeInsnCount++;
					}

					public void visitInsn(int opcode)
					{
						zeroOpInsnCount++;
					}

					public void visitMethodInsn(int opcode, String owner, String n, String d)
					{
						if (owner.equals(className))
							internalMethodCallCount++;
						else
							externalMethodCallCount++;
						dependencies.add(owner);
						// System.out.println(owner+"::"+n+" "+d);
					}

					public void visitLdcInsn(Object cst)
					{
						constantLoadCount++;
					}

					public void visitIincInsn(int var, int increment)
					{
						incrementOpCount++;
					}
				};

				for (int j = 0; j < method.instructions.size(); ++j)
				{
					Object insn = method.instructions.get(j);
					((AbstractInsnNode) insn).accept(mv);
				}
			}
		}
	}

	/** Add a dependency to the list if type is not null, primitive types are ignored */
	private void addDependency(Type t)
	{
		if (t == null) return; // not interested in null types
		if (t.getSort() == Type.OBJECT) dependencies.add(t.getInternalName());
	}

	/** Extract all dependencies from fields and method decl. */
	private void extractDependencies(ClassNode cn)
	{
		Iterator iter = cn.fields.iterator();
		while (iter.hasNext())
		{
			FieldNode fn = (FieldNode) iter.next();
			Type t = Type.getType(fn.desc);
			addDependency(t);
		}

		iter = cn.methods.iterator();
		while (iter.hasNext())
		{
			MethodNode mn = (MethodNode) iter.next();
			for (int i = 0; i < mn.exceptions.size(); i++)
			{
				dependencies.add((String) mn.exceptions.get(i));
			}
			Type t = Type.getReturnType(mn.desc);
			addDependency(t);

			Type[] argTypes = Type.getArgumentTypes(mn.desc);
			for (int i = 0; i < argTypes.length; i++)
			{
				t = argTypes[i];
				addDependency(t);
			}
		}
		fanOutCount = dependencies.size();
	}

	/**
	 * Extract raw counts of innerclasses, methods, fields, implemented interfaces, class-name
	 * and super class-name
	 */
	private void extractClassMetrics(ClassNode cn)
	{
		innerClassCount = cn.innerClasses.size();
		interfaceCount = cn.interfaces.size();
		fieldCount = cn.fields.size();
		methodCount = cn.methods.size();
		className = cn.name;
		superClassName = cn.superName.trim();
		if (!superClassName.equals("java/lang/Object")) superClassCount = 1;
		if ((cn.access & Opcodes.ACC_ABSTRACT) != 0) isAbstract = true;
		if ((cn.access & Opcodes.ACC_INTERFACE) != 0) isInterface = true;
	}

	/** Utility method to show the various metrics as a string */
	public String toString()
	{
		String classType = "C";
		if (isAbstract) classType = "A";
		if (isInterface) classType = "I";
		String header = String.format(
				"%1d %3d %3d %3d %3d %4d %3d %3d %3d %4d %3d %4d %3d %3d %s %s %s",
				superClassCount, interfaceCount, fieldCount, methodCount,
				localVarCount,
				(loadCount + loadFieldCount + refLoadOpCount + constantLoadCount),
				(storeCount + storeFieldCount + refStoreOpCount), branchCount, typeInsnCount,
				zeroOpInsnCount, privateMethodCount, fanOutCount, internalMethodCallCount,
				externalMethodCallCount, classType, className , superClassName);
		String deps = "Dependencies:\n";
		for (String d : dependencies) deps += d+"\n";
		return header+"\n"+deps;
	}

	@Override
	public int hashCode()
	{
		return className.hashCode();
	}
}
