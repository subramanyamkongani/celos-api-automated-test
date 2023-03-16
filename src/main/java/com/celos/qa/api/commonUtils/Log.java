package com.celos.qa.api.commonUtils;

import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.Reporter;

public class Log {

	/**
	 * Logs a message of type FATAL Calling this method will terminate the current
	 * test and mark it as failed
	 *
	 * @param log - log4j2 object sent from the calling class
	 * @param msg - the message to output
	 **/
	public static void logFatal(Logger log, String msg) {
		log.fatal(retrieveThreadId() + TestCoreUtils.truncateTrace(TestCoreUtils.formatStackTrace(new Exception(msg))));
		Assert.fail(msg);
	}

	/**
	 * Logs a message of type FATAL, which will use the passed in Throwable for
	 * logging the stacktrace Calling this method will terminate the current test
	 * and mark it as failed
	 *
	 * @param log - log4j2 object sent from the calling class
	 * @param msg - the message to output
	 **/
	public static void logFatal(Logger log, String msg, Throwable ex) {
		log.fatal(retrieveThreadId() + msg + "\n" + TestCoreUtils.truncateTrace(TestCoreUtils.formatStackTrace(ex)));
		Assert.fail(msg, ex);
	}

	/**
	 * Logs a message of type ERROR
	 *
	 * @param log - log4j2 object sent from the calling class
	 * @param msg - the message to output
	 **/
	public static void logError(Logger log, String msg) {
		log.error(retrieveThreadId() + msg);
		Reporter.log(msg);
	}

	/**
	 * Logs a message of type WARN
	 *
	 * @param log - log4j2 object sent from the calling class
	 * @param msg - the message to output
	 **/
	public static void logWarn(Logger log, String msg) {
		log.warn(retrieveThreadId() + msg);
		Reporter.log(msg);
	}

	/**
	 * Logs a message of type INFO
	 *
	 * @param log - log4j2 object sent from the calling class
	 * @param msg - the message to output
	 **/
	public static void logInfo(Logger log, String msg) {
		log.info(msg);
		Reporter.log(msg);
	}

	/**
	 * Logs a message of type DEBUG
	 *
	 * @param log - log4j2 object sent from the calling class
	 * @param msg - the message to output
	 **/
	public static void logDebug(Logger log, String msg) {
		log.debug(retrieveThreadId() + msg);
		Reporter.log(msg);
	}

	/**
	 * Logs a message of type TRACE
	 *
	 * @param log - log4j2 object sent from the calling class
	 * @param msg - the message to output
	 **/
	public static void logTrace(Logger log, String msg) {
		log.trace(retrieveThreadId() + msg);
		Reporter.log(msg);
	}

	/**
	 * Gets the ID of the current thread and prepends '@thread' for logging purposes
	 *
	 * @return String - '@thread{threadID}'
	 **/
	private static String retrieveThreadId() {
		return "@thread" + Thread.currentThread().getId() + " - ";
	}
}
