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
package gloodb.storage;

import gloodb.Cloner;
import gloodb.SimpleSerializable;

import java.math.BigInteger;
import java.util.ArrayList;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 * A storage test set, agnostic from actual storage strategy used.
 */
public class StorageTestBase {

	/**
	 * Will store the storage in UnitsTests/Storage
	 */
	protected static final String nameSpace = "target/UnitTests/Storage";

	/**
	 * The storage instance.
	 */
	protected Storage storage;

	/**
	 * Objects can be stored and restored from the storage.
	 */
	@Test
	public void storeAndRestore() {
		// Create 5 different objects. Retrieve object number 3.
		storage.store(new MockManagedObject(1), storage.buildAddress());
		storage.store(new MockManagedObject(2), storage.buildAddress());
		Address address = storage.buildAddress();
		storage.store(new MockManagedObject(3), address);
		storage.store(new MockManagedObject(4), storage.buildAddress());
		storage.store(new MockManagedObject(5), storage.buildAddress());
		MockManagedObject managedObject = storage.restore(MockManagedObject.class, address);
		assertEquals(3, managedObject.version);
	}

	/**
	 * Objects can be updated and restored from the storage.
	 */
	@Test
	public void updateAndRestore() {
		// Create 5 different objects. Update and retrieve object number 3.
		storage.store(new MockManagedObject(1), storage.buildAddress());
		storage.store(new MockManagedObject(2), storage.buildAddress());
		Address address = storage.buildAddress();
		storage.store(new MockManagedObject(3), address);
		storage.store(new MockManagedObject(4), storage.buildAddress());
		storage.store(new MockManagedObject(5), storage.buildAddress());
		MockManagedObject managedObject = storage.restore(MockManagedObject.class, address);

		// update the object
		managedObject.version = 6;
		storage.store(managedObject, address);
		managedObject = storage.restore(MockManagedObject.class, address);
		assertEquals(6, managedObject.version);
	}

	/**
	 * Objects can be removed from the storage.
	 */
	@Test
	public void remove() {
		// Create 5 different objects, remove the 3rd one.
		storage.store(new MockManagedObject(1), storage.buildAddress());
		storage.store(new MockManagedObject(2), storage.buildAddress());
		Address address = storage.buildAddress();
		storage.store(new MockManagedObject(3), address);
		storage.store(new MockManagedObject(4), storage.buildAddress());
		storage.store(new MockManagedObject(5), storage.buildAddress());
		storage.remove(address);
		MockManagedObject managedObject = storage.restore(MockManagedObject.class, address);
		assertNull(managedObject);
	}

	/**
	 * Snapshots of the storage can be restored at will.
	 */
	@Test
	public void snapshots() {
		// Create 5 different objects, take a snapshot, remove the 3rd one
		// Restore snapshot. Object no 3 should re-appear.
		storage.store(new MockManagedObject(1), storage.buildAddress());
		storage.store(new MockManagedObject(2), storage.buildAddress());
		Address address = storage.buildAddress();
		storage.store(new MockManagedObject(3), address);
		storage.store(new MockManagedObject(4), storage.buildAddress());
		storage.store(new MockManagedObject(5), storage.buildAddress());
		storage.takeSnapshot(BigInteger.ZERO);

		// Copy the address since the remove may reset it.
		Address addressCopy = Cloner.deepCopy(address);
		storage.remove(address);
		MockManagedObject managedObject = storage.restore(MockManagedObject.class, address);
		assertNull(managedObject);

		// Restore the snapshot. This should bring the deleted object back.
		storage.restoreSnapshot(BigInteger.ZERO);
		managedObject = storage.restore(MockManagedObject.class, addressCopy);
		assertEquals(3, managedObject.version);
	}

	/**
	 * Any snapshot can be restored, independent of any other existing
	 * snapshots.
	 */
	@Test
	public void multipleSnapshots() {
		// Create 5 different objects, take a snapshot, remove the 3rd one
		// Restore snapshot. Object no 3 should re-appear.
		storage.store(new MockManagedObject(1), storage.buildAddress());
		storage.store(new MockManagedObject(2), storage.buildAddress());
		Address address = storage.buildAddress();
		storage.store(new MockManagedObject(3), address);
		storage.takeSnapshot(BigInteger.ZERO);
		storage.store(new MockManagedObject(4), storage.buildAddress());
		storage.store(new MockManagedObject(5), storage.buildAddress());
		storage.takeSnapshot(BigInteger.ONE);

		// Copy the address since the remove may reset it.
		Address addressCopy = (Address) Cloner.deepCopy(address);
		storage.remove(address);
		MockManagedObject managedObject = storage.restore(MockManagedObject.class, address);
		assertNull(managedObject);

		// Restore the snapshot. This should bring the deleted object back.
		storage.restoreSnapshot(BigInteger.ONE);
		managedObject = storage.restore(MockManagedObject.class, addressCopy);
		assertEquals(3, managedObject.version);

	}

	@Test
    public void randomRun() {
		MockManagedObject obj;
		int idx;
		Address address;
		BigInteger bigInteger;
		ArrayList<Address> addresses = new ArrayList<Address>();
    	for(int i = 0; i < 500; i++) {
    		switch(i % 4) {
    		case 0:
    		case 1:
    			// Create
    			obj = new MockManagedObject();
    			bigInteger = obj.allocateBigInteger((int) Math.floor(Math.random() * 5000 + 1));
    			address = storage.buildAddress();
    			storage.store(obj, address);
    			addresses.add(address);
    			// Restore and compare
    			obj = storage.restore(MockManagedObject.class, address);
    			assertEquals(bigInteger, obj.getBigInteger());
    			break;
    		case 2: 
    			// Update
    			idx = (int)Math.floor(Math.random() * addresses.size());
    			address = addresses.get(idx);
    			obj = storage.restore(MockManagedObject.class, address);
    			bigInteger = obj.allocateBigInteger((int) Math.floor(Math.random() * 5000 + 1));
    			storage.store(obj, address);
    			addresses.set(idx, address);
    			// Restore and compare
    			obj = storage.restore(MockManagedObject.class, address);
    			assertEquals(bigInteger, obj.getBigInteger());
    			break;
    		default:
    			// Remove
    			idx = (int)Math.floor(Math.random() * addresses.size());
    			address = addresses.remove(idx);
    			storage.remove(address);
    			// Restore and assert null
    			obj = storage.restore(MockManagedObject.class, address);
    			assertNull(obj);
    			break;
    		}
    	}
    }
	
	@Test
	public void testEquality() {
	    String testId = "testId";
	    String testId2 = "testId2";
	    StorageProxy proxy1 = storage.buildStorageProxy(storage.buildAddress());
	    StorageProxy proxy2 = storage.buildStorageProxy(storage.buildAddress());
	    proxy1.store(new SimpleSerializable(testId));
 	    assertThat(proxy1.equals(proxy2), is (false));
 	    assertThat(proxy2.equals(proxy1), is (false));
 	    assertThat(proxy1.equals(null), is(false));
 	    assertThat(proxy1.equals("nothing"), is(false));
 	    proxy2.store(new SimpleSerializable(testId2));
 	    assertThat(proxy1.equals(proxy2), is(false));
 	    proxy2.store(new SimpleSerializable(testId));
 	    assertThat(proxy1.equals(proxy2), is(true));
       
	}
}
