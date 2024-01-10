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
package gloodb;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * Utility class for cloning arbitrary Serializable object.
 */
public final class Cloner {

    private Cloner() {
    	// Private constructor of utility class.
    }

    /**
     * Clones the prototype object. If the object implements the clonable interface
     * the clone() method is used for deep copying. Otherwise cloning is implemented
     * via in memory serialization / deserialization.
     *
     * @param prototype The prototype object
     * @return A deep copy of the prototype.
     * @throws GlooException If the object cannot be copied
     */
    static public <T extends Serializable> T deepCopy(T prototype) {
    	if(prototype == null) return null;
        if(prototype instanceof Cloneable) {
            return deepCopyByCloning(prototype);
        } else {
            return deepCopyBySerialization(prototype);
        }
    }

    /**
     * Clones the prototype object via serialization (the object is serilialized
     * / deserialized in a memory buffer).
     * @param prototype The prototype object
     * @return A deep copy of the prototype.
     * @throws GlooException If the object cannot be copied
     */
    @SuppressWarnings("unchecked")
    static private <T extends Serializable> T deepCopyBySerialization(T prototype) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(prototype);
            oos.flush();
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()));
            T result = (T) ois.readObject();
            return result;
        } catch (Exception ex) {
            throw new GlooException("Cannot deep copy object using serialization.", ex);
        }
    }

    /**
     * Clones the prototype object via cloning.
     * @param prototype The prototype object
     * @return A deep copy of the prototype.
     * @throws GlooException If the object cannot be copied
     */
    @SuppressWarnings("unchecked")
    static private <T extends Serializable> T deepCopyByCloning(T prototype) {
        try {
            Method cloneMethod = prototype.getClass().getDeclaredMethod("clone");
            cloneMethod.setAccessible(true);
            Object result = cloneMethod.invoke(prototype);
            return (T)result;
        } catch (Exception ex) {
            throw new GlooException("Cannot deep copy object using cloning.", ex);
        }
    }
}
