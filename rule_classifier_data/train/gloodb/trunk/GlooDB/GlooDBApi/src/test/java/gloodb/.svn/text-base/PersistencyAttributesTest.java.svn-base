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

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;


/**
 * Test set for PersistencyAttributes utility class.
 * 
 */
@SuppressWarnings("unused")
public class PersistencyAttributesTest {
	class FullyLoadedPersistent implements Serializable, Cloneable {
        private static final long serialVersionUID = -5657885396622549541L;

        @Identity
	    Serializable id;
	    
	    @Version
	    long version;
	    
	    FullyLoadedPersistent(Serializable id) {
	        this.id = id;
	    }
	    
	    long getVersion() {
	        return version;
	    }
	    
	    @PreCreate
	    void preCreate(Repository repository) {
	        
	    }
        @PreUpdate
        void preUpdate(Repository repository) {
            
        }
        @PreRemove
        void preRemove(Repository repository) {
            
        }

        @PostCreate
        void postCreate(Repository repository) {
            
        }
        @PostUpdate
        void postUpdate(Repository repository) {
            
        }
        @PostRemove
        void postRemove(Repository repository) {
            
        }
        @PostRestore
        void postRestore(Repository repository) {
            
        }
        
        @Override
        public Object clone() {
            FullyLoadedPersistent copy = new FullyLoadedPersistent(id);
            copy.version = version;
            return copy;
        }
	}

	class NoOverridesFullyLoadedPersistentSubClass extends FullyLoadedPersistent {
        NoOverridesFullyLoadedPersistentSubClass(Serializable id) {
            super(id);
        }

        private static final long serialVersionUID = -2857447772986481719L;
	}
	
	private void incrementVersion(FullyLoadedPersistent object) {
		assertEquals(0L, object.getVersion());
		PersistencyAttributes.incrementVersion(object, object);
		assertEquals(1L, object.getVersion());
	}

	/**
	 * Tests identity parsing for classes with no identity defined.
	 */
	@Test	
	public void testGetIdApiWithNoField() {
		try {
			PersistencyAttributes.getId();
			fail();
		} catch (GlooException ge) {
			assertEquals("Need at least one field to specify an object identity", ge.getMessage());
		}
	}

	/**
	 * Tests identity parsing for classes with no identity defined.
	 */
	@Test	
	public void testGetIdWithNoIdentity() {
		try {
			PersistencyAttributes.getIdForObject("Test");
			fail();
		} catch (GlooException ge) {
			assertEquals("Class java.lang.String does not define an identity or method field.", ge.getMessage());
		}
	}	
	/**
	 * Version fields are incremented.
	 */
	@Test
	public void testIncrementLongVersion() {
		incrementVersion(new FullyLoadedPersistent("Test"));
		incrementVersion(new NoOverridesFullyLoadedPersistentSubClass("Test"));
	}

	/**
	 * Test versioning related behaviour for non versioned objects.
	 */
	@SuppressWarnings("serial")
    @Test
	public void testNonVersionedObject() {
		// Incrementing version on non-versioned object should not throw an
		// exception.
		PersistencyAttributes.incrementVersion(new Serializable() { @Identity String id; }, null);
	}

	/**
	 * Callback method are correctly identified via annotations.
	 */
	@Test
	public void testCallbackMethods() {
		callBackMethod(new FullyLoadedPersistent("Test"), FullyLoadedPersistent.class);
		callBackMethod(new NoOverridesFullyLoadedPersistentSubClass("Test"), FullyLoadedPersistent.class);
	}

	@Test
	public void testNullObjectAttributes() {
		assertNull(PersistencyAttributes.getPersistentInfo(null));
	}

	@Test
	public void testIncorrectIdAndVersionField() {
		Serializable obj = new Serializable() {
			private static final long serialVersionUID = 6270762340639699825L;

			@Identity
			@Version
			int idAndVersion;
		};

		try {
			PersistencyAttributes.getPersistentInfo(obj);
			fail("Id fields cannot be used for versioning.");
		} catch (GlooException e) {
			// an exception should get thrown.
		}
	}

	@Test
	public void testMultiplePostCreate() {
		Serializable obj = new Serializable() {
			private static final long serialVersionUID = 6270762340639699825L;

			@Identity
			String id;

			@PostCreate
			void foo() {
				// Test method
			}

			@PostCreate
			void bar() {
				// Test method
			}
		};

		try {
			PersistencyAttributes.getCallback(PostCreate.class, obj);
			fail("Only one post create method allowed");
		} catch (GlooException e) {
			// an exception should get thrown.
		}
	}

	@Test
	public void testMultiplePreCreate() {
		Serializable obj = new Serializable() {
			private static final long serialVersionUID = 6270762340639699825L;

			@Identity
			String id;

			@PreCreate
			void foo() {
				// Test
			}

			@PreCreate
			void bar() {
				// Test
			}
		};

		try {
			PersistencyAttributes.getCallback(PreCreate.class, obj);
			fail("Only one pre create method allowed");
		} catch (GlooException e) {
			// an exception should get thrown.
		}
	}

	@Test
	public void testMultiplePreUpdate() {
		Serializable obj = new Serializable() {
			private static final long serialVersionUID = 6270762340639699825L;

			@Identity
			String id;

			@PreUpdate
			void foo() {
				// Test
			}

			@PreUpdate
			void bar() {
				// Test
			}
		};

		try {
			PersistencyAttributes.getCallback(PreUpdate.class, obj);
			fail("Only one pre store method allowed");
		} catch (GlooException e) {
			// an exception should get thrown.
		}
	}

	@Test
	public void testMultiplePostUpdate() {
		Serializable obj = new Serializable() {
			private static final long serialVersionUID = 6270762340639699825L;

			@Identity
			String id;

			@PostUpdate
			void foo() {
				// Test
			}

			@PostUpdate
			void bar() {
				// Test
			}
		};

		try {
			PersistencyAttributes.getCallback(PostUpdate.class, obj);
			fail("Only one post store method allowed");
		} catch (GlooException e) {
			// an exception should get thrown.
		}
	}

	@Test
	public void testMultiplePreRemove() {
		Serializable obj = new Serializable() {
			private static final long serialVersionUID = 6270762340639699825L;

			@Identity
			String id;

			@PreRemove
			void foo() {
				// Test
			}

			@PreRemove
			void bar() {
				// Test
			}
		};

		try {
			PersistencyAttributes.getCallback(PreRemove.class, obj);
			fail("Only one pre remove method allowed");
		} catch (GlooException e) {
			// an exception should get thrown.
		}
	}

	@Test
	public void testMultiplePostRemove() {
		Serializable obj = new Serializable() {
			private static final long serialVersionUID = 6270762340639699825L;

			@Identity
			String id;

			@PostRemove
			void foo() {
				// Test
			}

			@PostRemove
			// Test
			void bar() {
				// Test
			}
		};

		try {
			PersistencyAttributes.getCallback(PostRemove.class, obj);
			fail("Only one post remove method allowed");
		} catch (GlooException e) {
			// an exception should get thrown.
		}
	}

	@Test
	public void testMultiplePostRestore() {
		Serializable obj = new Serializable() {
			private static final long serialVersionUID = 6270762340639699825L;

			@Identity
			String id;

			@PostRestore
			void foo() {
				// Test
			}

			@PostRestore
			void bar() {
				// Test
			}
		};

		try {
			PersistencyAttributes.getCallback(PostRestore.class, obj);
			fail("Only one post restore method allowed");
		} catch (GlooException e) {
			// an exception should get thrown.
		}
	}
	
	private void callBackMethod(Serializable object, Class<?> ownerClass) {
		assertCallback(ownerClass, PersistencyAttributes.getCallback(PostRestore.class, object));
		assertCallback(ownerClass, PersistencyAttributes.getCallback(PostUpdate.class, object));
		assertCallback(ownerClass, PersistencyAttributes.getCallback(PostCreate.class, object));
		assertCallback(ownerClass, PersistencyAttributes.getCallback(PreRemove.class, object));
		assertCallback(ownerClass, PersistencyAttributes.getCallback(PreCreate.class, object));
		assertCallback(ownerClass, PersistencyAttributes.getCallback(PreUpdate.class, object));
		assertCallback(ownerClass, PersistencyAttributes.getCallback(PostRemove.class, object));
	}

    private void assertCallback(Class<?> ownerClass, Method callback) {
        assertThat(callback, is(not(nullValue())));
        assertThat(ownerClass.getName(), is(equalTo(callback.getDeclaringClass().getName())));
    }
	
	
}