package dovetaildb.util;

import java.util.Comparator;


/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

public class CheckpointableHeap<T extends Checkpointable> implements Checkpointable {
	protected Node<T> root = null;
	protected Node<T> checkpointRoot = null;
	protected Comparator<T> comparator;

	public static final class Node<T extends Checkpointable> implements Checkpointable {
		public T value;
		public Node<T> left, right;
		T checkpointValue;
		Node<T> checkpointLeft, checkpointRight;
		
		public Node(T value) {
			this.value = value;
		}
		public void uncheckpoint() {
			if (checkpointValue != null) {
				checkpointValue.uncheckpoint();
				checkpointValue = null;
				if (left != null) left.uncheckpoint();
				if (right != null) right.uncheckpoint();
			}
		}
		public void checkpoint() {
			value.checkpoint();
			checkpointValue = value;
			checkpointLeft = left;
			checkpointRight = right;
		}
		public void rewindToCheckpoint() {
			if (checkpointValue != null) {
				checkpointValue.rewindToCheckpoint();
				value = checkpointValue;
				left = checkpointLeft;
				right = checkpointRight;
				checkpointValue = null;
				checkpointLeft = checkpointRight = null;
				if (left != null) left.rewindToCheckpoint();
				if (right != null) right.rewindToCheckpoint();
			}
		}
		public Node<T> popThis(Comparator<T> cmp, boolean hasCheckpoint) {
			markForUpdate(hasCheckpoint);
			return merge(left, right, cmp, hasCheckpoint);
		}
		public void adjustThis(Comparator<T> cmp, boolean hasCheckpoint) {
			Node<T> target;
			if (left == null) {
				if (right == null) return;
				target = right;
			} else if (right == null) {
				target = left;
			} else {
				boolean lSmaller = cmp.compare(left.value, right.value) < 0;
				target = lSmaller ? left: right;
			}
			if (cmp.compare(value, target.value) <= 0) return;
			markForUpdate(hasCheckpoint);
			target.markForUpdate(hasCheckpoint);
			T myOldVal = value;
			value = target.value;
			target.value = myOldVal;
			target.adjustThis(cmp, hasCheckpoint);
		}
		public Node<T> insert(T item, Comparator<T> cmp, boolean hasCheckpoint) {
			return merge(new Node<T>(item), this, cmp, hasCheckpoint);
		}
		public Node<T> merge(Node<T> left, Node<T> right, Comparator<T> cmp, boolean hasCheckpoint) {
			if (left == null) return right;
			if (right == null) return left;
			if (cmp.compare(left.value, right.value) > 0) {
				Node<T> temp = left;
				left = right;
				right = temp;
			}
			left.markForUpdate(hasCheckpoint);
			Node<T> oldLeftRight = left.right;
			left.right = left.left;
			left.left = merge(right, oldLeftRight, cmp, hasCheckpoint);
			return left;
		}
		void markForUpdate(boolean hasCheckpoint) {
			if (hasCheckpoint && checkpointValue == null) {
				checkpoint();
			}
		}
		public String toString() {
			return value+":"+(checkpointValue!=null?"T":"F")+"("+left+":"+right+")";
		}
		public int size() {
			int ct = 1;
			if (left != null) ct += left.size();
			if (right != null) ct += right.size();
			return ct;
		}
	}
	
	public CheckpointableHeap(Comparator<T> cmp) {
		this.comparator = cmp;
	}
	public boolean isEmpty() {
		return (root == null);
	}
	public void checkpoint() {
		if (checkpointRoot != null) {
			root.uncheckpoint();
		}
		checkpointRoot = root;
		root.checkpoint();
	}
	public void uncheckpoint() {
		root.uncheckpoint();
		checkpointRoot = null;
	}
	public void rewindToCheckpoint() {
		root = checkpointRoot;
		root.rewindToCheckpoint();
		checkpointRoot = null;
	}
	public void insert(T item) {
		if (root == null) {
			root = new Node<T>(item);
		} else {
			root = root.insert(item, comparator, checkpointRoot!=null);
		}
	}
	public T pop() {
		T oldVal = root.value;
		root = root.popThis(comparator, checkpointRoot!=null);
		return oldVal;
	}
	public T top() {
		return root.value;
	}
	public void adjustTop() {
		root.adjustThis(comparator, checkpointRoot!=null);
	}
	public String toString() {
		return "CheckpointableHeap("+root+")"; 
	}
	public int size() {
		if (root == null) return 0;
		return root.size();
	}
	public T getTopForAdjustment() {
		root.markForUpdate(checkpointRoot!=null);
		return root.value;
	}
}
