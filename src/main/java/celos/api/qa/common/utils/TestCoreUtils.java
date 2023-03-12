package celos.api.qa.common.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Stack;
import java.util.TimeZone;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class TestCoreUtils {

	private static Logger log = LogManager.getLogger(TestCoreUtils.class.getName());

	private static Random random = new Random();
	public static String charSet_alpha_lowerCase = "abcdefghijklmnopqrstuvxyz";
	public static String charSet_alpha_upperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static String charSet_numeric = "0123456789";

	/**
	 * Read file from path and return as String
	 *
	 * @param pathname - path to file
	 * @return String - file contents
	 */
	public static String readFile(String pathname) throws Exception {
		File file = new File(pathname);
		StringBuilder buffer = new StringBuilder();
		if (file.exists() && file.canRead()) {
			BufferedReader reader = new BufferedReader(new FileReader(file.getAbsoluteFile()));
			String line = reader.readLine();
			while (line != null) {
				buffer.append(line);
				line = reader.readLine();
				if (line != null) {
					buffer.append("\n");
				}
			}
			reader.close();
		} else {
			Log.logFatal(log, "Unable to read template file: " + pathname);
		}
		return buffer.toString();
	}

	/**
	 * Read file from resource directory and return as String
	 *
	 * @param pathname - path to file within - src/main/resources
	 * @return String - file contents
	 */
	public static String readResourceFile(String pathname) {
		InputStream inputStream = TestCoreUtils.class.getResourceAsStream(pathname);
		InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
		StringBuilder buffer = new StringBuilder();
		BufferedReader reader = new BufferedReader(streamReader);
		try {
			String line = reader.readLine();
			while (line != null) {
				buffer.append(line);
				line = reader.readLine();
				if (line != null) {
					buffer.append("\n");
				}
			}
		} catch (Exception e) {
			Log.logFatal(log, "Unable to read resources file: " + pathname, e);
		} finally {
			try {
				reader.close();
			} catch (Exception e) {
				Log.logFatal(log, "Unable to close reader object", e);
			}
		}
		return buffer.toString();
	}

	/**
	 * Copy a file into another file. The contents of 'source' will be written into
	 * 'dest'.
	 *
	 * @param source - path to source file
	 * @param dest   - path to destination file
	 */
	public static void copyFile(File source, File dest) {
		try {
			Log.logInfo(log, "Creating file (" + dest + ") with the contents from file (" + source + ")");
			Files.copy(source.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
			Log.logInfo(log, "Successfully created file (" + dest + ")");
		} catch (Exception e) {
			Log.logFatal(log, "Failure to copy file (" + source + ") into file (" + dest + ")", e);
		}
	}

	/**
	 * Resets the seed in the random generator object
	 */
	public static void resetRandomSeed() {
		synchronized (random) {
			random = new Random();
		}
	}

	/**
	 * Generate a random integer
	 * 
	 * @param min - minimum value of integer to be generated
	 * @param max - maximum value of integer to be generated
	 * @return int - random int between min & max
	 */
	public static int generateRandomNumber(int min, int max) {
		int number;
		synchronized (random) {
			// Add 1 to make it inclusive.
			number = random.nextInt((max - min) + 1) + min;
		}
		return number;
	}

	/**
	 * Swallows exceptions, use with care. This is discouraged, please try to use
	 * wait methods instead.
	 * 
	 * @param millis Time to sleep in milliseconds (1sec = 1000ms)
	 */
	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @summary Method to generate random number of the length passed.
	 *
	 * @param length     - of the number of to be generated,
	 * @param characters - to generate random number from
	 * @return String - random number
	 */
	public static String generateRandomNumber(int length, String characters) {

		StringBuffer buffer = new StringBuffer("");
		int charactersLength = characters.length();

		for (int i = 0; i < length; i++) {
			double index = Math.random() * charactersLength;
			buffer.append(characters.charAt((int) index));
		}
		return buffer.toString();
	}

	/**
	 * @summary Method to generate random String of the length passed in and derived
	 *          from the characterSet passed in
	 *
	 * @param length       - length of String to generate
	 * @param characterSet - characters to generate random String from
	 * @return String - random String
	 **/
	public static String getRandomString(int length, String characterSet) {
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			int index = (int) (characterSet.length() * Math.random());
			sb.append(characterSet.charAt(index));
		}
		return sb.toString();
	}

	/**
	 * This method generates a random name of length ranging from 6-12 characters
	 * comprised of capital letters.
	 *
	 * @return String - random name
	 */
	public static String generateRandomName() {
		StringBuilder builder = new StringBuilder();
		int length = random.nextInt(6) + 6;
		for (int i = 0; i < length; i++) {
			builder.append(charSet_alpha_upperCase.charAt(random.nextInt(charSet_alpha_upperCase.length())));
		}
		return builder.toString();
	}

	/**
	 * This fails the test immediately by logging the defect information
	 * 
	 * @param testStep - String description of test step
	 * @param defectId - defect ID number from Jira
	 */
	public static void failTestWithDefect(String testStep, String defectId) {
		Log.logFatal(log, "Test step " + testStep + " fails due to a defect - https://myjira.disney.com/browse/"
				+ defectId + ". Please remove this assert after the defect has been fixed");
	}

	/**
	 * This logs defect information and continues execution
	 *
	 * @param defectId - defect ID number from Jira
	 */
	public static void logDefectWithoutFail(StackTraceElement[] trace, String defectId) {
		StringBuffer sb = new StringBuffer();
		for (StackTraceElement line : trace) {
			sb.append("\t at " + line.toString() + "\n");
		}
		Log.logError(log, "Bypassing defect ( https://jira-nge.disney.com/browse/" + defectId
				+ " ). Please remove bypassing command after the defect has been fixed");
		Log.logError(log, truncateTrace(sb.toString()));
	}

	/**
	 * Returns a String formatted with the exception message of e followed by a
	 * colon, newline, then the stacktrace of e
	 *
	 * @param e - any Throwable object (Exception or Error type)
	 * @return String - the exception message and trace formatted to print out
	 **/
	public static String formatStackTrace(Throwable e) {
		StringBuffer sb = new StringBuffer();
		StackTraceElement[] trace = e.getStackTrace();
		sb.append(e.getClass().getName());
		sb.append(": ");
		sb.append(e.getMessage());
		sb.append("\n");
		for (StackTraceElement line : trace) {
			sb.append("\t at " + line.toString() + "\n");
		}
		return sb.toString();
	}

	/**
	 * Truncates a stacktrace to show only useful trace back lines
	 *
	 * @param ex - exception containing the trace
	 **/
	public static String truncateTrace(String formattedTrace) {
		StringBuilder truncatedTrace = new StringBuilder();
		String[] splitByLine = formattedTrace.split("\n");
		truncatedTrace.append(splitByLine[0] + "\n");
		if (splitByLine.length > 1) {
			for (int i = 1; i < splitByLine.length; i++) {
				if (splitByLine[i].contains("sun.reflect"))
					break;
				truncatedTrace.append(splitByLine[i] + "\n");
			}
		}

		return truncatedTrace.toString();
	}

	/**
	 * Parse a String using the regex. Returns the first match specified in the
	 * regex.
	 *
	 * @param line  - String to be parsed
	 * @param regex - Regular expression to match
	 * @return String - 1st group matched. If not found, null is returned
	 **/
	public static String parseLine(String line, String regex) {
		return parseLineMatchingAllGroups(line, regex).group(1);
	}

	/**
	 * Parse a String using the regex. Returns a matcher object containing all
	 * groups matched.
	 *
	 * @param line  - String to be parsed
	 * @param regex - Regular expression to match
	 * @return Matcher - All groups matched. If none found, null is returned
	 **/
	public static Matcher parseLineMatchingAllGroups(String line, String regex) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(line);
		if (matcher.find()) {
			return matcher;
		}
		return null;
	}

	/**
	 * Generates random name
	 *
	 * @return String - random name
	 */
	public static String randomName() {
		String[] namesList = new String[300];
		for (int i = 0; i < 300; i++) {
			namesList[i] = TestCoreUtils.generateRandomName();
		}

		// randomly selects an index from the array
		return namesList[random.nextInt(namesList.length)];
	}

	/**
	 * Method to provide a unique userId using UUID
	 *
	 * @return String - random userId
	 */
	public static String getRandomUserId() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	/**
	 * Search str and replace values that exist in kvPairs
	 *
	 * @param kvPairs - Keys & Values to replace
	 * @param str     - String to have values replaced
	 * @return String - str with keys replaced
	 **/
	public static String searchAndReplaceTemplate(Map<String, String> kvPairs, String str) {
		String result = str;
		for (String key : kvPairs.keySet()) {
			// Search first and avoid constantly rebuilding the string.
			if (result.contains(key)) {
				String search = key.replaceAll("\\$", "\\\\\\$");
				try {
					result = result.replaceAll(search, kvPairs.get(key));
				} catch (Exception e) {
					// Replacement is blank
					result = result.replaceAll(search, "");
				}
			}
		}
		return result;
	}

	/**
	 * Converts a JSON object to a single line String. If body is null, returns
	 * null.
	 *
	 * @param body - JSON object as String
	 * @return String - JSON object represented as a single line String
	 **/
	public static String convertJsonToSingleLine(String body) {
		StringBuilder singleLineJson = new StringBuilder();
		if (body != null) {
			String[] lines = body.split("\n");
			for (String line : lines) {
				singleLineJson.append(line.trim());
			}
		}
		return body == null ? body : singleLineJson.toString();
	}

	/**
	 * Date formatter to a specific pattern
	 * 
	 * @param numberOfDaysFromToday - number of days to added or subtracted from
	 *                              todays date.
	 * @param formatPattern         - the pattern in which date should be formatted.
	 * @return String - formatted date
	 **/
	public static String dateFormat(int numberOfDaysFromToday, String formatPattern) {
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(formatPattern);
		LocalDateTime localDate = LocalDateTime.now().plusDays(numberOfDaysFromToday);

		return localDate.format(dateFormat);
	}

	/**
	 * This method converts the format of a date to a new format
	 * 
	 * @param input          - formatted date as a String
	 * @param actualFormat   - format of the input date
	 * @param expectedFormat - new format to convert to
	 * @return String - newly formatted date
	 **/
	public static String convertDateFormat(String input, String actualFormat, String expectedFormat) {
		Log.logInfo(log, "Converting date - " + input + " - to format: " + expectedFormat);
		LocalDate inputDate = LocalDate.parse(input, DateTimeFormatter.ofPattern(actualFormat));
		DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern(expectedFormat);
		return outputFormatter.format(inputDate);
	}

	/**
	 * Defaults to "./" if there's an exception of any sort.
	 * 
	 * @warning Exceptions are swallowed.
	 * @return String - current pathname
	 */
	public static String determineCurrentPath() {
		try {
			return (new File(".").getCanonicalPath()) + File.separator;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "." + File.separator;
	}

	/**
	 * Return DateFormat for specific pattern of specific time zone.
	 * 
	 * @param ofPattern - pattern in which the date is to be formatted
	 * @param timeZone  - time zone to set
	 * @return DateFormat
	 */
	public static DateFormat timeZoneDateFormat(String ofPattern, String timeZone) {
		DateFormat dateFormat = new SimpleDateFormat(ofPattern);
		if (!(timeZone == null))
			dateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
		return dateFormat;
	}

	/**
	 * To format date to specific pattern and convert it to specific time
	 * zone/region.
	 * 
	 * @param String dateToFormat - the date to format
	 * @param String ofPattern - the pattern in which the date is to be formatted
	 * @param String ofTimeZone - time zone of the date
	 * @param String toTimeZone - time zone, the date will be converted to
	 * @return String - formatted date
	 */
	public static String dateTimeFormat(String dateToFormat, String ofPattern, String ofTimeZone, String toTimeZone) {
		String formattedDate = "";
		try {
			Date date = timeZoneDateFormat(ofPattern, ofTimeZone).parse(dateToFormat);
			formattedDate = timeZoneDateFormat(ofPattern, toTimeZone).format(date);
		} catch (ParseException e) {
			Log.logFatal(log, "Cannot parse the date : " + dateToFormat, e);
		}
		return formattedDate;
	}

	/***************************************************************************
	 * Reads from file and stores data in the TestContainer indexed by key. It then
	 * retrieves data from the container (amount = iQty)
	 *
	 * @param key:  key of data in TestContainer
	 * @param path: path to file to read from
	 * @param iQty: number of items to retrieve from the container
	 * @since March 21, 2019
	 ***************************************************************************/
	public static synchronized String[] retrieveDataViaFile(int iQty, String key, String path) {
		try {
			if (TestContainer.getGlobal(key) == null) {
				String[] fileContents = TestCoreUtils.readResourceFile(path).split("\n");
				// read from file and upload contents to global
				Stack<String> data = new Stack<>();
				for (String line : fileContents) {
					if (!line.trim().equals(""))
						data.push(line.trim());
				}
				TestContainer.setGlobal(key, data);
			}
		} catch (Exception e) {
			Log.logFatal(log, "Error when attempting to read from file path: " + path, e);
		}
		return retrieveDataViaContainer(iQty, key);
	}

	/***************************************************************************
	 * Retrieves data from the container (amount = iQty)
	 *
	 * @param key:  key of data in TestContainer
	 * @param iQty: number of items to retrieve from the container
	 * @since March 21, 2019
	 ***************************************************************************/
	public static synchronized String[] retrieveDataViaContainer(int iQty, String key) {
		String[] data = new String[iQty];
		try {
			Stack<?> dataStack = (Stack<?>) TestContainer.getGlobal(key);
			for (int i = 0; i < iQty; i++) {
				data[i] = ((String) dataStack.pop());
				Log.logInfo(log, "Item retrieved: " + data[i]);
			}
			TestContainer.setGlobal(key, dataStack);
		} catch (Exception e) {
			Assertion.fail("Error when attempting to retrieve data from TestContainer");
		}
		return data;
	}

	/**
	 * Method to write class paths in XML file
	 * 
	 * @param classNames - List of class names
	 * @param xmlPath    - XML file to write to
	 * @throws Exception
	 */
	public static void writeClassNameToXml(List<String> classNames, String xmlPath) throws Exception {
		File file = new File(xmlPath);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(file);
		doc.getDocumentElement().normalize();
		NodeList nodeList = doc.getElementsByTagName("classes");
		Node node = nodeList.item(0);
		for (String className : classNames) {
			Element name = doc.createElement("class");
			if (className.contains("$")) {
				className = className.substring(0, className.indexOf("$"));
			}
			name.setAttribute("name", className);
			node.appendChild(name);
		}
		DOMImplementation domImpl = doc.getImplementation();
		DocumentType doctype = domImpl.createDocumentType("doctype", "", "http://testng.org/testng-1.0.dtd");
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, doctype.getSystemId());
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(xmlPath);
		transformer.transform(source, result);
	}

	/**
	 * Method to compile list of class names
	 * 
	 * @param packageName - base package name
	 * @param testIds     - set of Test IDs
	 * @param xmlPath     - XML file to write to
	 * @throws Exception
	 */
	public static void getClasses(String packageName, HashSet<String> testIds, String xmlPath) throws Exception {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		assert classLoader != null;
		Enumeration<URL> resources = classLoader.getResources(packageName.replace('.', '/'));
		List<File> dirs = new ArrayList<File>();
		while (resources.hasMoreElements()) {
			URL resource = resources.nextElement();
			dirs.add((Paths.get(resource.toURI()).toFile()));
		}
		System.out.println("Directories to parse: " + dirs);
		HashSet<String> classes = new HashSet<String>();
		for (File directory : dirs) {
			classes.addAll(findClasses(directory, packageName, testIds));
		}
		System.out.println("Paths to write to XML: " + classes);
		List<String> uniqueClasses = new ArrayList<>(classes);
		writeClassNameToXml(uniqueClasses, xmlPath);
	}

	/**
	 * Method to find list of class names
	 * 
	 * @param directory   - directory under base package
	 * @param packageName - base package name
	 * @param testIds     - Set of Test IDs
	 * @return List<String> - list of class paths
	 * @throws Exception
	 */
	public static List<String> findClasses(File directory, String packageName, HashSet<String> testIds)
			throws Exception {
		List<String> classes = new ArrayList<String>();
		if (!directory.exists()) {
			return classes;
		}
		File[] files = directory.listFiles();
		for (File file : files) {
			if (testIds.isEmpty()) {
				return classes;
			}
			if (file.isDirectory()) {
				classes.addAll(findClasses(file, packageName + "." + file.getName(), testIds));
				continue;
			}
			if (file.getName().contains("$"))
				continue;
			for (String testId : testIds) {
				if (file.getName().endsWith(".class") && file.getName().contains(testId)) {
					classes.add(packageName + '.' + file.getName().substring(0, file.getName().length() - 6));
					testIds.remove(testId);
					break;
				}
			}
		}
		return classes;
	}

	/**
	 * Splits a file into multiple files. The key is the indicator of what to split
	 * by. New files will be named by the unique identifier provided by each unique
	 * match using the key.
	 *
	 * @param initialLog - path to initial file
	 * @param key        - regular expression to match each line in the file; used
	 *                   as the key to split
	 * @param resultsDir - path to the results directory to save the new files too
	 */
	public static void splitLogByKey(String initialLog, String key, String resultsDir) {
		try {
			Map<String, String> logs = new HashMap<>();
			BufferedReader reader = new BufferedReader(new FileReader(initialLog));
			String currentLine = reader.readLine();
			while (currentLine != null) {
				Matcher match = TestCoreUtils.parseLineMatchingAllGroups(currentLine, key);
				if (!logs.containsKey(match.group(1))) {
					logs.put(match.group(1), "");
				}
				String currentVal = logs.get(match.group(1));
				logs.put(match.group(1), currentVal + match.group(2) + "\n");
				currentLine = reader.readLine();
			}
			reader.close();

			File dir = new File(resultsDir + new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
			dir.mkdirs();
			for (Map.Entry<String, String> entry : logs.entrySet()) {
				Path newLog = Paths
						.get(new URI("file://" + dir.getAbsolutePath() + File.separator + entry.getKey() + ".log"));
				byte[] strToBytes = entry.getValue().getBytes();
				Files.write(newLog, strToBytes);
			}
		} catch (Exception e) {
			Log.logError(log, "Failure during log splitting due to: " + e.getMessage());
		}
	}

}
