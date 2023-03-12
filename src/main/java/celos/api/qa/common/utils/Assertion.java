package celos.api.qa.common.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

public class Assertion {

	private static Logger log = LogManager.getLogger(Assertion.class.getName());

	/**
	 * Fails a test with the given message.
	 * 
	 * @param message the assertion error message
	 */
	public static void fail(String message) {
		Log.logFatal(log, message);
	}

	/**
	 * Fails a test with the given message and wrapping the original exception.
	 *
	 * @param message the assertion error message
	 * @param origEx  the original exception
	 */
	public static void fail(String message, Throwable origEx) {
		Log.logFatal(log, message, origEx);
	}

	/**
	 * Asserts that a condition is true.
	 * 
	 * @param condition the condition to evaluate
	 * @param message   the assertion error message
	 */
	public static void assertTrue(boolean condition, String message) {
		try {
			Assert.assertTrue(condition, message);
		} catch (AssertionError failure) {
			Log.logFatal(log, message, failure);
		}
	}

	/**
	 * Asserts that a condition is true.
	 * 
	 * @param condition the condition to evaluate
	 */
	public static void assertTrue(boolean condition) {
		assertTrue(condition, "");
	}

	/**
	 * Asserts that a condition is false.
	 * 
	 * @param condition the condition to evaluate
	 * @param message   the assertion error message
	 */
	public static void assertFalse(boolean condition, String message) {
		try {
			Assert.assertFalse(condition, message);
		} catch (AssertionError failure) {
			Log.logFatal(log, message, failure);
		}
	}

	/**
	 * Asserts that a condition is false.
	 * 
	 * @param condition the condition to evaluate
	 */
	public static void assertFalse(boolean condition) {
		assertFalse(condition, "");
	}

	/**
	 * Asserts that two objects are equal.
	 * 
	 * @param actual   the actual value
	 * @param expected the expected value
	 * @param message  the assertion error message
	 */
	public static void assertEquals(Object actual, Object expected, String message) {
		try {
			Assert.assertEquals(actual, expected, message);
		} catch (AssertionError failure) {
			Log.logFatal(log, message, failure);
		}
	}

	/**
	 * Asserts that two objects are equal.
	 * 
	 * @param actual   the actual value
	 * @param expected the expected value
	 */
	public static void assertEquals(Object actual, Object expected) {
		assertEquals(actual, expected, "");
	}

	/**
	 * Asserts that two objects are not equal.
	 * 
	 * @param actual   the actual value
	 * @param expected the expected value
	 * @param message  the assertion error message
	 */
	public static void assertNotEquals(Object actual, Object expected, String message) {
		try {
			Assert.assertNotEquals(actual, expected, message);
		} catch (AssertionError failure) {
			Log.logFatal(log, message, failure);
		}
	}

	/**
	 * Asserts that two objects are not equal.
	 * 
	 * @param actual   the actual value
	 * @param expected the expected value
	 */
	public static void assertNotEquals(Object actual, Object expected) {
		assertNotEquals(actual, expected, "");
	}

	/**
	 * Asserts that an object isn't null.
	 * 
	 * @param object  the assertion object
	 * @param message the assertion error message
	 */
	public static void assertNull(Object object, String message) {
		try {
			Assert.assertNull(object, message);
		} catch (AssertionError failure) {
			Log.logFatal(log, message, failure);
		}
	}

	/**
	 * Asserts that an object isn't null.
	 * 
	 * @param object the assertion object
	 */
	public static void assertNull(Object object) {
		assertNull(object, "");
	}

	/**
	 * Asserts that an object isn't null.
	 * 
	 * @param object  the assertion object
	 * @param message the assertion error message
	 */
	public static void assertNotNull(Object object, String message) {
		try {
			Assert.assertNotNull(object, message);
		} catch (AssertionError failure) {
			Log.logFatal(log, message, failure);
		}
	}

	/**
	 * Asserts that an object isn't null.
	 * 
	 * @param object the assertion object
	 */
	public static void assertNotNull(Object object) {
		assertNotNull(object, "");
	}

	/**
	 * Asserts that two objects refer to the same objects.
	 * 
	 * @param actual   the actual value
	 * @param expected the expected value
	 * @param message  the assertion error message
	 */
	public static void assertSame(Object actual, Object expected, String message) {
		try {
			Assert.assertSame(actual, expected, message);
		} catch (AssertionError failure) {
			Log.logFatal(log, message, failure);
		}
	}

	/**
	 * Asserts that two objects refer to the same objects.
	 * 
	 * @param actual   the actual value
	 * @param expected the expected value
	 */
	public static void assertSame(Object actual, Object expected) {
		assertSame(actual, expected, "");
	}

	/**
	 * Asserts that two objects do not refer to the same objects.
	 * 
	 * @param actual   the actual value
	 * @param expected the expected value
	 * @param message  the assertion error message
	 */
	public static void assertNotSame(Object actual, Object expected, String message) {
		try {
			Assert.assertNotSame(actual, expected, message);
		} catch (AssertionError failure) {
			Log.logFatal(log, message, failure);
		}
	}

	/**
	 * Asserts that two objects do not refer to the same objects.
	 * 
	 * @param actual   the actual value
	 * @param expected the expected value
	 */
	public static void assertNotSame(Object actual, Object expected) {
		assertNotSame(actual, expected, "");
	}

	/**
	 * Asserts that two arrays contain the same elements in no particular order.
	 * 
	 * @param actual   the actual value
	 * @param expected the expected value
	 * @param message  the assertion error message
	 */
	public static void assertEqualsNoOrder(Object[] actual, Object[] expected, String message) {
		try {
			Assert.assertEqualsNoOrder(actual, expected, message);
		} catch (AssertionError failure) {
			Log.logFatal(log, message, failure);
		}
	}

	/**
	 * Asserts that two arrays contain the same elements in no particular order.
	 * 
	 * @param actual   the actual value
	 * @param expected the expected value
	 */
	public static void assertEqualsNoOrder(Object[] actual, Object[] expected) {
		assertEqualsNoOrder(actual, expected, "");
	}

}
