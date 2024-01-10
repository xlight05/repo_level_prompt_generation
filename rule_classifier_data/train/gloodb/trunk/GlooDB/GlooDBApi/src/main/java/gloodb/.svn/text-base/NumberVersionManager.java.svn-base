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

import gloodb.GlooException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Version manager for number types. Accepted number types are byte, short, int, long,
 * float, double, BigInteger and BigDecimal. The NumberVersionManager will increment
 * version by 1. 
 */
class NumberVersionManager implements VersionManager {

    /**
     * Increments the version field by 1.
     * @param field The version field.
     * @throws LockingException if the original and copied versions are not same.
     * @throws GlooException if the version field is not of a number type.
     */
    public synchronized void increment(Serializable original, Serializable copy, Field field) {
        try {
            Class<?> fieldClass = field.getType();
            if (fieldClass.isAssignableFrom(long.class)) {
                long oldVersion = field.getLong(original);
                long newVersion = field.getLong(copy);
                if (oldVersion != newVersion) {
                    throw new LockingException(String.format("Locking exception: ID %s, repository version: %d, provided version: %d", 
                            PersistencyAttributes.getIdForObject(original).toString(),
                            oldVersion, newVersion));
                }
                field.setLong(copy, oldVersion + 1);
            } else if (fieldClass.isAssignableFrom(int.class)) {
                int oldVersion = field.getInt(original);
                int newVersion = field.getInt(copy);
                if (oldVersion != newVersion) {
                    throw new LockingException(String.format("Locking exception: ID %s, repository version: %d, provided version: %d", 
                            PersistencyAttributes.getIdForObject(original).toString(),
                            oldVersion, newVersion));
                }
                field.setInt(copy, oldVersion + 1);
            } else if (fieldClass.isAssignableFrom(short.class)) {
                short oldVersion = field.getShort(original);
                short newVersion = field.getShort(copy);
                if (oldVersion != newVersion) {
                    throw new LockingException(String.format("Locking exception: ID %s, repository version: %d, provided version: %d", 
                            PersistencyAttributes.getIdForObject(original).toString(),
                            oldVersion, newVersion));
                }
                field.setShort(copy, (short) (oldVersion + 1));
            } else if (fieldClass.isAssignableFrom(byte.class)) {
                byte oldVersion = field.getByte(original);
                byte newVersion = field.getByte(copy);
                if (oldVersion != newVersion) {
                    throw new LockingException(String.format("Locking exception: ID %s, repository version: %d, provided version: %d", 
                            PersistencyAttributes.getIdForObject(original).toString(),
                            oldVersion, newVersion));
                }
                field.setByte(copy, (byte) (oldVersion + 1));
            } else if (fieldClass.isAssignableFrom(float.class)) {
                float oldVersion = field.getFloat(original);
                float newVersion = field.getFloat(copy);
                if (oldVersion != newVersion) {
                    throw new LockingException(String.format("Locking exception: ID %s, repository version: %f, provided version: %f", 
                            PersistencyAttributes.getIdForObject(original).toString(),
                            oldVersion, newVersion));
                }
                field.setFloat(copy, oldVersion + 1);
            } else if (fieldClass.isAssignableFrom(double.class)) {
                double oldVersion = field.getDouble(original);
                double newVersion = field.getDouble(copy);
                if (oldVersion != newVersion) {
                    throw new LockingException(String.format("Locking exception: ID %s, repository version: %f, provided version: %f", 
                            PersistencyAttributes.getIdForObject(original).toString(),
                            oldVersion, newVersion));
                }
                field.setDouble(copy, oldVersion + 1);
            } else if (fieldClass.isAssignableFrom(BigInteger.class)) {
                BigInteger oldVersion = (BigInteger) field.get(original);
                BigInteger newVersion = (BigInteger) field.get(copy);
                if (!oldVersion.equals(newVersion)) {
                    throw new LockingException(String.format("Locking exception: ID %s, repository version: %s, provided version: %s", 
                            PersistencyAttributes.getIdForObject(original).toString(),
                            oldVersion.toString(), newVersion.toString()));
                }
                field.set(copy, oldVersion.add(BigInteger.ONE));
            } else if (fieldClass.isAssignableFrom(BigDecimal.class)) {
                BigDecimal oldVersion = (BigDecimal) field.get(original);
                BigDecimal newVersion = (BigDecimal) field.get(copy);
                if (!oldVersion.equals(newVersion)) {
                    throw new LockingException(String.format("Locking exception: ID %s, repository version: %s, provided version: %s", 
                            PersistencyAttributes.getIdForObject(original).toString(),
                            oldVersion.toString(), newVersion.toString()));
                }
                field.set(copy, oldVersion.add(BigDecimal.ONE));
            } else if (fieldClass.isAssignableFrom(Long.class)) {
                Long oldVersion = (Long)field.get(original);
                Long newVersion = (Long)field.get(copy);
                if (!oldVersion.equals(newVersion)) {
                    throw new LockingException(String.format("Locking exception: ID %s, repository version: %s, provided version: %s", 
                            PersistencyAttributes.getIdForObject(original).toString(),
                            oldVersion.toString(), newVersion.toString()));
                }
                field.set(copy, Long.valueOf(oldVersion + 1));
            } else if (fieldClass.isAssignableFrom(Integer.class)) {
                Integer oldVersion = (Integer)field.get(original);
                Integer newVersion = (Integer)field.get(copy);
                if (!oldVersion.equals(newVersion)) {
                    throw new LockingException(String.format("Locking exception: ID %s, repository version: %s, provided version: %s", 
                            PersistencyAttributes.getIdForObject(original).toString(),
                            oldVersion.toString(), newVersion.toString()));
                }
                field.set(copy, Integer.valueOf(oldVersion + 1));
            } else if (fieldClass.isAssignableFrom(Short.class)) {
                Short oldVersion = (Short)field.get(original);
                Short newVersion = (Short)field.get(copy);
                if (!oldVersion.equals(newVersion)) {
                    throw new LockingException(String.format("Locking exception: ID %s, repository version: %s, provided version: %s", 
                            PersistencyAttributes.getIdForObject(original).toString(),
                            oldVersion.toString(), newVersion.toString()));
                }
                field.set(copy, Short.valueOf((short) (oldVersion + 1)));
            } else if (fieldClass.isAssignableFrom(Byte.class)) {
                Byte oldVersion = (Byte)field.get(original);
                Byte newVersion = (Byte)field.get(copy);
                if (!oldVersion.equals(newVersion)) {
                    throw new LockingException(String.format("Locking exception: ID %s, repository version: %s, provided version: %s", 
                            PersistencyAttributes.getIdForObject(original).toString(),
                            oldVersion.toString(), newVersion.toString()));
                }
                field.set(copy, Byte.valueOf((byte) (oldVersion + 1)));
            } else if (fieldClass.isAssignableFrom(Float.class)) {
                Float oldVersion = (Float)field.get(original);
                Float newVersion = (Float)field.get(copy);
                if (!oldVersion.equals(newVersion)) {
                    throw new LockingException(String.format("Locking exception: ID %s, repository version: %s, provided version: %s", 
                            PersistencyAttributes.getIdForObject(original).toString(),
                            oldVersion.toString(), newVersion.toString()));
                }
                field.set(copy, Float.valueOf(oldVersion + 1));
            } else if (fieldClass.isAssignableFrom(Double.class)) {
                Double oldVersion = (Double)field.get(original);
                Double newVersion = (Double)field.get(copy);
                if (!oldVersion.equals(newVersion)) {
                    throw new LockingException(String.format("Locking exception: ID %s, repository version: %s, provided version: %s", 
                            PersistencyAttributes.getIdForObject(original).toString(),
                            oldVersion.toString(), newVersion.toString()));
                }
                field.set(copy, Double.valueOf(oldVersion + 1));
            } else {
                throw new GlooException("Class %s has a non-numeric @Version field. " +
                        "Either set the field type to a numeric one, " +
                        "or provide the VersionManager class that knows " +
                        "how to manage this version type.");
            }
        } catch (LockingException le) {
            throw le;
        } catch (Exception e) {
            throw new GlooException(e);
        }
    }
}
