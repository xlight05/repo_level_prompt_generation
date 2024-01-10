package gloodb;

import static org.junit.Assert.*;

import org.junit.Test;

public class QueryResultTest {

	@Test
	public void testGetResult() {
		QueryResult result = new QueryResult(Integer.valueOf(0));
		assertEquals(0, result.getResult());

		result = new QueryResult(new GlooException("Test"));
		try {
			result.getResult();
			fail("Should throw a gloo exception");
		} catch (GlooException ge) {
			assertEquals("Test", ge.getMessage());
			assertNull(ge.getCause());
		}

		Exception e = new Exception("Test");
		result = new QueryResult(e);
		try {
			result.getResult();
			fail("Should throw a gloo exception");
		} catch (GlooException ge) {
			assertEquals(e.toString(), ge.getMessage());
			assertNotNull(ge.getCause());
			assertEquals("Test", ge.getCause().getMessage());
		}
	}

	@Test
	public void testHasGlooException() {
		QueryResult result = new QueryResult(Integer.valueOf(0));
		assertFalse(result.hasGlooException());

		result = new QueryResult(new GlooException("Test"));
		assertTrue(result.hasGlooException());

		result = new QueryResult(new Exception("Test"));
		assertFalse(result.hasGlooException());
	}

	@Test
	public void testHasException() {
		QueryResult result = new QueryResult(Integer.valueOf(0));
		assertFalse(result.hasGlooException());

		result = new QueryResult(new GlooException("Test"));
		assertTrue(result.hasGlooException());

		result = new QueryResult(new Exception("Test"));
		assertFalse(result.hasGlooException());
	}

	@Test
	public void testGetExceptionResult() {
		QueryResult result = new QueryResult(Integer.valueOf(0));
		try {
			result.getExceptionResult();
			fail("Should throw a class cast exception");
		} catch (ClassCastException cce) {
			// expected
		}

		result = new QueryResult(new GlooException("Test"));
		assertTrue(result.getExceptionResult() instanceof GlooException);

		result = new QueryResult(new Exception("Test"));
		assertNotNull(result.getExceptionResult());
	}

	@Test
	public void testThrowExceptionsIfAny() {
		try {
			QueryResult result = new QueryResult(Integer.valueOf(0));
			result.throwExceptionsIfAny();
		} catch (Exception e) {
			fail("Should not throw exception");
		}

		QueryResult result = new QueryResult(new GlooException("Test"));
		try {
			result.throwExceptionsIfAny();
			fail("Should throw a gloo exception");
		} catch (GlooException ge) {
			assertEquals("Test", ge.getMessage());
			assertNull(ge.getCause());
		}

		Exception e = new Exception("Test");
		result = new QueryResult(e);
		try {
			result.throwExceptionsIfAny();
			fail("Should throw a gloo exception");
		} catch (GlooException ge) {
			assertEquals(e.toString(), ge.getMessage());
			assertNotNull(ge.getCause());
			assertEquals("Test", ge.getCause().getMessage());
		}
	}
	
	@Test
	public void testThrowUnwrappedExceptionsIfAny() {
		try {
			QueryResult result = new QueryResult(Integer.valueOf(0));
			result.throwUnwrappedExceptionIfAny();
		} catch (Exception e) {
			fail("Should not throw exception");
		}

		QueryResult result = new QueryResult(new GlooException("Test"));
		try {
			result.throwUnwrappedExceptionIfAny();
			fail("Should throw a gloo exception");
		} catch (Exception ex) {
			assertTrue(ex instanceof GlooException);
			assertEquals("Test", ex.getMessage());
			assertNull(ex.getCause());
		}

		Exception e = new Exception("Test");
		result = new QueryResult(e);
		try {
			result.throwUnwrappedExceptionIfAny();
			fail("Should throw an exception");
		} catch (Exception ex) {
			assertSame(ex, e);
			assertEquals("Test", ex.getMessage());
			assertNull(ex.getCause());
		}
	}	

	@Test
	public void testThrowSpecificExceptionsIfAny() {
		try {
			QueryResult result = new QueryResult(Integer.valueOf(0));
			result.throwSpecifiedExceptionIfAny(Exception.class);
		} catch (Exception e) {
			fail("Should not throw exception");
		}

		QueryResult result = new QueryResult(new GlooException("Test"));
		try {
			result.throwSpecifiedExceptionIfAny(GlooException.class);
			fail("Should throw a gloo exception");
		} catch (GlooException ex) {
			assertEquals("Test", ex.getMessage());
			assertNull(ex.getCause());
		}

		result = new QueryResult(new GlooException("Test"));
		try {
			result.throwSpecifiedExceptionIfAny(Exception.class);
		} catch (Exception ex) {
			fail("Should not throw since the result is a GlooException and not Exception");
		}
		
		Exception e = new Exception("Test");
		result = new QueryResult(e);
		try {
			result.throwSpecifiedExceptionIfAny(Exception.class);
			fail("Should throw an exception");
		} catch (Exception ex) {
			assertSame(ex, e);
			assertEquals("Test", ex.getMessage());
			assertNull(ex.getCause());
		}
	}	
}