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

import java.io.Serializable;
import java.util.Comparator;

public class StringComparator implements Comparator<String>, Serializable, Cloneable {
    private static final long serialVersionUID = 6051430525959250939L;

    /*
     * (non-Javadoc)
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    @Override
    public int compare(String arg0, String arg1) {
        if(arg0 == null) return arg1 == null? 0: 1;
        if(arg1 == null) return -1;
        return arg0.compareTo(arg1);
    }
    
    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException cnse) {
            throw new GlooException(cnse);
        }
    }

}
