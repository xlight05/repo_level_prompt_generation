/*******************************************************************************
 * Copyright (c) Dino Octavian.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 *  
 *  Contributors:
 *      Dino Octavian - initial API and implementation
 *******************************************************************************/
package gloodb.utils;

import gloodb.GlooException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Utility class for file manipulation.
 * 
 */
public final class FileUtils {

    private FileUtils() {
    	// Private constructor of an utility class
    }

    /**
     * Copies the source directory to the destination directory.
     * @param srcDir The source directory.
     * @param dstDir The destination directory.
     * @throws java.io.IOException If the copy operation fails.
     */
    public static void copyDirectory(File srcDir, File dstDir) throws IOException {
        // Copies all files under srcDir to dstDir.
        // If dstDir does not exist, it will be created.
        if (srcDir.isDirectory()) {
            if (!dstDir.exists()) {
                if (!dstDir.mkdir()) {
                    throw new GlooException("Cannot create directory " + dstDir.getName());
                }
            }

            String[] children = srcDir.list();
            for (int i = 0; i < children.length; i++) {
                copyDirectory(new File(srcDir, children[i]), new File(dstDir, children[i]));
            }
        } else {
            // This method is implemented in e1071 Copying a File
            copyFile(srcDir, dstDir);
        }
    }

    /**
     * Copies the source file into the destination file.
     * @param src The source file.
     * @param dst The destination file.
     * @throws java.io.IOException If the copy operation fails.
     */
    public static void copyFile(File src, File dst) throws IOException {
        // Copies src file to dst file.
        // If the dst file does not exist, it is created
        InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(dst);

        // Transfer bytes from in to out
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }

    /**
     * Deletes the specified file or directory. 
     * @param src The source file or directory to delete.
     * @return True if the delete is successful.
     */
    public static boolean deleteFileOrDirectory(File src) {
        if (src == null) {
            return true;
        }
        boolean result = true;
        if (src.isDirectory()) {
            for (File subFile : src.listFiles()) {
                result &= deleteFileOrDirectory(subFile);
            }
        }
        result &= src.delete();
        return result;
    }
}
