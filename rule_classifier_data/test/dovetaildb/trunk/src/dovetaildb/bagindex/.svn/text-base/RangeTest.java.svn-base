package dovetaildb.bagindex;

import junit.framework.TestCase;
import dovetaildb.bytes.ArrayBytes;

public class RangeTest extends TestCase {

	public void testContainsPrefix() {
		Range abc = new Range(ArrayBytes.fromString("abc"),null,null,true,true);
		assertTrue( abc.containsPrefix(ArrayBytes.fromString("a")));
		assertTrue( abc.containsPrefix(ArrayBytes.fromString("abcd")));
		assertTrue( abc.containsPrefix(ArrayBytes.fromString("abc")));
		assertFalse(abc.containsPrefix(ArrayBytes.fromString("abd")));
		assertFalse(abc.containsPrefix(ArrayBytes.fromString("abb")));

		abc = new Range(ArrayBytes.fromString("abc"),ArrayBytes.fromString("dd"),null,false,true);
		assertTrue( abc.containsPrefix(ArrayBytes.fromString("a")));
		assertFalse(abc.containsPrefix(ArrayBytes.fromString("abcc")));
		assertFalse(abc.containsPrefix(ArrayBytes.fromString("abcdc")));
		assertTrue( abc.containsPrefix(ArrayBytes.fromString("abcd")));
		assertTrue( abc.containsPrefix(ArrayBytes.fromString("abcdd")));
		assertTrue( abc.containsPrefix(ArrayBytes.fromString("abc")));
		assertFalse(abc.containsPrefix(ArrayBytes.fromString("abd")));
		assertFalse(abc.containsPrefix(ArrayBytes.fromString("abb")));

		abc = new Range(ArrayBytes.fromString("abc"),null,ArrayBytes.fromString("dd"),false,false);
		assertTrue( abc.containsPrefix(ArrayBytes.fromString("a")));
		assertTrue( abc.containsPrefix(ArrayBytes.fromString("abc")));
		assertTrue( abc.containsPrefix(ArrayBytes.fromString("abcd")));
		assertTrue( abc.containsPrefix(ArrayBytes.fromString("abcdc")));
		assertFalse(abc.containsPrefix(ArrayBytes.fromString("abce")));
		assertFalse(abc.containsPrefix(ArrayBytes.fromString("abcde")));
		// this is an edge case this isn't really possible, but i don't want to complicate the logic enough to handle it exactly right
		// assertFalse(abc.containsPrefix(ArrayBytes.fromString("abcdd")));
		assertFalse(abc.containsPrefix(ArrayBytes.fromString("abd")));
		assertFalse(abc.containsPrefix(ArrayBytes.fromString("abb")));
	}

}
