/***
 * ASM 3.0 ClassDump Example
 * Copyright (c) 2006 Andrew Cowan
 * All rights reserved.
 *
 * Based on the ASM3.0 DependencyTracker
 *
 */
package org.objectweb.asm.example;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.InnerClassNode;

/**
 * ASM3.0 ClassDump example.
 *
 * @author Andrew Cowan - 4044363
 *
 * @see
 */
public class ClassDump
{
    public static void main(final String[] args) throws IOException
    {
		ClassNode cv = new ClassNode();

        FileInputStream file = new FileInputStream(args[0]);
        new ClassReader(file).accept(cv, 0);

		System.out.println();
		System.out.println("Analysing source file: " + cv.sourceFile);
		System.out.println();
		System.out.println("name: " + cv.name);
		System.out.println("superName: " + cv.superName);

		System.out.println();
		System.out.println("interfaces:");
		Iterator<String> interfaces = cv.interfaces.iterator();
		while (interfaces.hasNext()) {
			String _interface = interfaces.next();
			System.out.println("  " + _interface);
		 }

		System.out.println();
		System.out.println("fields:");
		Iterator<FieldNode> fields = cv.fields.iterator();
        while (fields.hasNext()) {
            FieldNode field = fields.next();
            System.out.println("  " + field.desc + " " + field.name + " = '" + field.value + "'");
        }

		System.out.println();
		System.out.println("methods:");
		Iterator<MethodNode> methods = cv.methods.iterator();
        while (methods.hasNext()) {
            MethodNode method = methods.next();
			System.out.print("  " + method.desc + " " + method.name + "()");
			boolean _throws = false;
            Iterator<String> exceptions = method.exceptions.iterator();
			while (exceptions.hasNext())
			{
				String exception = exceptions.next();
				String prefix = (_throws)? ", " : " throws ";
				System.out.print(prefix + exception);
				_throws = true;
			}
			System.out.println();
        }
    }
}
