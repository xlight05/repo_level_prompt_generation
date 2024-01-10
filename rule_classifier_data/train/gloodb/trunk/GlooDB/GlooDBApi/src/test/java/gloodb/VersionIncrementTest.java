/*
 * Copyright (c) Dino Octavian.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 * 
 * Contributors:
 *      Dino Octavian - initial API and implementation
 */
package gloodb;

import gloodb.GlooException;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test set for version incrementing.
 * 
 */
@SuppressWarnings("unused")
public class VersionIncrementTest implements Serializable {
	private static final long serialVersionUID = -3641513343076539045L;

	interface Versioned extends Serializable {

        Serializable getVersion();
        void setVersion(Serializable version);
    }

    /**
     * Long can be used for version fields.
     */
    @Test
    public void testIncrementLongVersion() {
        Versioned obj = new Versioned() {

            private static final long serialVersionUID = 1L;
            @Identity
            Serializable id = "1";
            @Version
            long version;

            public Serializable getVersion() {
                return version;
            }
            public void setVersion(Serializable version) {
            	this.version = (Long) version;
            }
        };
        incrementVersion(obj, (long) 0, (long) 1);
        updateLockedSerializable(obj, (long)0);

    }

    /**
     * Byte can be used for version fields.
     */
    @Test
    public void testIncrementByteVersion() {
        Versioned obj = new Versioned() {

            private static final long serialVersionUID = 1L;
            @Identity
            Serializable id = "2";
            @Version
            byte version;

            public Serializable getVersion() {
                return version;
            }
            public void setVersion(Serializable version) {
            	this.version = (Byte) version;
            }
            
        };
        incrementVersion(obj, (byte) 0, (byte) 1);
        updateLockedSerializable(obj, (byte)0);

    }

    /**
     * Short can be used for version fields.
     */
    @Test
    public void testIncrementShortVersion() {
        Versioned obj = new Versioned() {

            private static final long serialVersionUID = 1L;
            @Identity
            Serializable id = "3";
            @Version
            short version;

            public Serializable getVersion() {
                return version;
            }
            public void setVersion(Serializable version) {
            	this.version = (Short) version;
            }
            
        };
        incrementVersion(obj, (short) 0, (short) 1);
        updateLockedSerializable(obj, (short)0);

    }

    /**
     * Int can be used for version fields.
     */
    @Test
    public void testIncrementIntVersion() {
        Versioned obj = new Versioned() {

            private static final long serialVersionUID = 1L;
            @Identity
            Serializable id = "4";
            @Version
            int version;

            public Serializable getVersion() {
                return version;
            }
            public void setVersion(Serializable version) {
            	this.version = (Integer) version;
            }
            
        };
        incrementVersion(obj, 0, 1);
        updateLockedSerializable(obj, 0);
        
    }

    /**
     * Double can be used for version fields.
     */
    @Test
    public void testIncrementDoubleVersion() {
        Versioned obj = new Versioned() {

            private static final long serialVersionUID = 1L;
            @Identity
            Serializable id = "5";
            @Version
            double version;

            public Serializable getVersion() {
                return version;
            }
            public void setVersion(Serializable version) {
            	this.version = (Double) version;
            }
            
        };
        incrementVersion(obj, 0.0, 1.0);
        updateLockedSerializable(obj, 0.0);        
    }

    /**
     * Float can be used for version fields.
     */
    @Test
    public void testIncrementFloatVersion() {
        Versioned obj = new Versioned() {

            private static final long serialVersionUID = 1L;
            @Identity
            Serializable id = "6";
            @Version
            float version;

            public Serializable getVersion() {
                return version;
            }
            public void setVersion(Serializable version) {
            	this.version = (Float) version;
            }
            
        };
        incrementVersion(obj, (float) 0.0, (float) 1.0);
        updateLockedSerializable(obj, (float)0.0);
    }

    /**
     * BigInteger can be used for version fields.
     */
    @Test
    public void testIncrementBigIntVersion() {
        Versioned obj = new Versioned() {

            private static final long serialVersionUID = 1L;
            @Identity
            Serializable id ="7";
            @Version
            BigInteger version = BigInteger.ZERO;

            public Serializable getVersion() {
                return version;
            }
            public void setVersion(Serializable version) {
            	this.version = (BigInteger) version;
            }
            
        };
        incrementVersion(obj, BigInteger.ZERO, BigInteger.ONE);
        updateLockedSerializable(obj, BigInteger.ZERO);
    }

    /**
     * BigDecimal can be used for version fields.
     */
    @Test
    public void testIncrementBigDecimalVersion() {
        Versioned obj = new Versioned() {

            private static final long serialVersionUID = 1L;
            @Identity
            Serializable id = "8";
            @Version
            BigDecimal version = BigDecimal.ZERO;

            public Serializable getVersion() {
                return version;
            }
            public void setVersion(Serializable version) {
            	this.version = (BigDecimal) version;
            }
            
        };
        incrementVersion(obj, BigDecimal.ZERO, BigDecimal.ONE);
        updateLockedSerializable(obj, BigDecimal.ZERO);
    }

    private void incrementVersion(Versioned obj, Serializable initial, Serializable incremented) {
        assertEquals(initial, obj.getVersion());
        PersistencyAttributes.incrementVersion(obj, obj);
        assertEquals(incremented, obj.getVersion());

    }
    
    private void updateLockedSerializable(Versioned obj, Serializable initial) {
        try {
        	Versioned originalVersion = Cloner.deepCopy(obj);
        	originalVersion.setVersion(initial);
            PersistencyAttributes.incrementVersion(originalVersion, obj);
            fail("An optimistic lock exception should be triggered.");
        } catch(LockingException le) {
        	// expected to fail
        }
    }

    /**
     * Only one field can be annotated with Version
     */
    @Test
    public void testMultipleVersions() {
        Serializable obj = new Serializable() {

            private static final long serialVersionUID = 1L;
            @Identity
            Serializable id = "9";
            @Version
            Long version;
            @Version
            Long version2;
        };

        try {
            PersistencyAttributes.getPersistentInfo(obj);
            fail();
        } catch (GlooException e) {
            // Cannot have Lazy fields used as version.
        }
    }

    /**************************************************
     * Tests for wrapper classes
     **************************************************/
    /**
     * Long can be used for version fields.
     */
    @Test
    public void testIncrementWrapperLongVersion() {
        Versioned obj = new Versioned() {

            private static final long serialVersionUID = 1L;
            @Identity
            Serializable id = "10";
            @Version
            Long version = 0L;

            public Serializable getVersion() {
                return version;
            }
            public void setVersion(Serializable version) {
            	this.version = (Long) version;
            }
        };
        incrementVersion(obj, Long.valueOf(0), Long.valueOf(1));
        updateLockedSerializable(obj, Long.valueOf(0));

    }

    /**
     * Byte can be used for version fields.
     */
    @Test
    public void testIncrementWrappedByteVersion() {
        Versioned obj = new Versioned() {

            private static final long serialVersionUID = 1L;
            @Identity
            Serializable id = "11";
            @Version
            Byte version = 0;

            public Serializable getVersion() {
                return version;
            }
            public void setVersion(Serializable version) {
            	this.version = (Byte) version;
            }
            
        };
        incrementVersion(obj, Byte.valueOf((byte)0), Byte.valueOf((byte)1));
        updateLockedSerializable(obj, Byte.valueOf((byte)0));

    }

    /**
     * Short can be used for version fields.
     */
    @Test
    public void testIncrementWrappedShortVersion() {
        Versioned obj = new Versioned() {

            private static final long serialVersionUID = 1L;
            @Identity
            Serializable id = "12";
            
            @Version
            Short version = 0;

            public Serializable getVersion() {
                return version;
            }
            public void setVersion(Serializable version) {
            	this.version = (Short) version;
            }
            
        };
        incrementVersion(obj, Short.valueOf((short)0), Short.valueOf((short)1));
        updateLockedSerializable(obj, Short.valueOf((short)0));

    }

    /**
     * Int can be used for version fields.
     */
    @Test
    public void testIncrementIntegerVersion() {
        Versioned obj = new Versioned() {

            private static final long serialVersionUID = 1L;
            @Identity
            Serializable id = "13";
            @Version
            Integer version = 0;

            public Serializable getVersion() {
                return version;
            }
            public void setVersion(Serializable version) {
            	this.version = (Integer) version;
            }
            
        };
        incrementVersion(obj, Integer.valueOf(0), Integer.valueOf(1));
        updateLockedSerializable(obj, Integer.valueOf(0));
        
    }

    /**
     * Double can be used for version fields.
     */
    @Test
    public void testIncrementWrappedDoubleVersion() {
        Versioned obj = new Versioned() {

            private static final long serialVersionUID = 1L;
            @Identity
            Serializable id = "14";
            @Version
            Double version = 0.0;

            public Serializable getVersion() {
                return version;
            }
            public void setVersion(Serializable version) {
            	this.version = (Double) version;
            }
            
        };
        incrementVersion(obj, Double.valueOf(0.0), Double.valueOf(1.0));
        updateLockedSerializable(obj, Double.valueOf(0.0));        
    }

    /**
     * Float can be used for version fields.
     */
    @Test
    public void testIncrementWrappedFloatVersion() {
        Versioned obj = new Versioned() {

            private static final long serialVersionUID = 1L;
            @Identity
            Serializable id = "15";
            @Version
            Float version = (float) 0.0;

            public Serializable getVersion() {
                return version;
            }
            public void setVersion(Serializable version) {
            	this.version = (Float) version;
            }
            
        };
        incrementVersion(obj, Float.valueOf((float) 0.0), Float.valueOf((float) 1.0));
        updateLockedSerializable(obj, Float.valueOf((float)0.0));
    }
}
