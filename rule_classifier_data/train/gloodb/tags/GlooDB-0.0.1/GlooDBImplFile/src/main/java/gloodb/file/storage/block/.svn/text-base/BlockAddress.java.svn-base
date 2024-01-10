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
package gloodb.file.storage.block;

import gloodb.storage.Address;
import java.util.Iterator;
import java.util.LinkedList;

class BlockAddress implements Address {
	private static final long serialVersionUID = 2529346525931404941L;

	private LinkedList<Integer> addressList = new LinkedList<Integer>();

	private int writtenSize;

	public BlockAddress() {
		super();
		addressList = new LinkedList<Integer>();
	}

	public BlockAddress(LinkedList<Integer> list) {
		super();
		addressList = list;
	}

	void addItem(int blockStart) {
		addressList.addLast(blockStart);
	}

	int size() {
		return addressList.size();
	}

	Iterator<Integer> iterator() {
		return addressList.iterator();
	}

	public BlockAddress clear(int from) {
		// copy the stuff to remove in the sublist
		LinkedList<Integer> cleared = new LinkedList<Integer>();

		// Remove last elements (until the size == from)
		while (addressList.size() > from) {
			cleared.addFirst(addressList.removeLast());
		}
		return new BlockAddress(cleared);
	}

	public int getItem(int i) {
		return addressList.get(i);
	}

	public void clear() {
		addressList.clear();
	}

	public int getWrittenSize() {
		return this.writtenSize;
	}

	public void setWrittenSize(int size) {
		this.writtenSize = size;
	}

}
