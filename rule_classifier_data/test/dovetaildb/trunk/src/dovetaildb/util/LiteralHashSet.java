package dovetaildb.util;
/*
 * Copyright 2008 Phillip Schanely
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.util.HashSet;

public class LiteralHashSet<T> extends HashSet<T> {
	private static final long serialVersionUID = 8957471432688112116L;
	public LiteralHashSet a(T val) {
		super.add(val);
		return this;
	}
}
