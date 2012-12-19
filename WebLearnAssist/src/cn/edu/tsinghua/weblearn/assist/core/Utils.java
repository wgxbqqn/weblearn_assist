/**
 * 
 */
package cn.edu.tsinghua.weblearn.assist.core;

import java.io.*;
import java.util.Calendar;
import java.util.Date;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * frequently used utility functions by other classes
 */
public class Utils {
	private static Calendar calendar = Calendar.getInstance();

	private static XPath xpath = XPathFactory.newInstance().newXPath();

	/**
	 * convert InputStream to String
	 * 
	 * @param encoding
	 *            the encoding to be converted to
	 * @throws IOException
	 */
	public static String inputStreamToString(InputStream is, String encoding)
			throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is,
				encoding));
		StringBuffer content = new StringBuffer();

		String line;
		while ((line = reader.readLine()) != null) {
			content.append(line);
		}

		return content.toString();
	}

	/**
	 * convert string to input stream
	 * 
	 * @param str
	 *            the string to be converted
	 */
	public static InputStream stringToInputStream(String str) {
		InputStream is = new ByteArrayInputStream(str.getBytes());
		return is;
	}

	/**
	 * convert String to Date
	 * 
	 * @param str
	 *            date in format: yyyy-mm-dd
	 */
	public static Date stringToDate(String str) {
		String[] tokens = str.split("-");
		calendar.set(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]),
				Integer.parseInt(tokens[2]));
		return calendar.getTime();
	}

	/**
	 * convert date to string
	 * 
	 * @return a string in format: yyyy-mm-dd
	 */
	public static String dateToString(Date date) {
		StringBuffer buffer = new StringBuffer();
		calendar.setTime(date);
		buffer.append(calendar.get(Calendar.YEAR));
		buffer.append("-");
		buffer.append(calendar.get(Calendar.MONTH));
		buffer.append("-");
		buffer.append(calendar.get(Calendar.DATE));

		return buffer.toString();
	}

	/**
	 * @param doc
	 *            the document to find in
	 * @param path
	 *            The xpath
	 * @return Return the node if the node is found, null if the node is not
	 *         found
	 * @throws XPathExpressionException
	 */
	public static Node getNodeByXPath(Document doc, String path)
			throws XPathExpressionException {
		XPathExpression expr = xpath.compile(path);
		Node node = (Node) expr.evaluate(doc.getDocumentElement(),
				XPathConstants.NODE);

		return node;
	}

	/**
	 * @param path
	 *            The xpath of the node to be queried
	 * 
	 * @return Return the text content of the node if the node is found, null if
	 *         the node is not found.
	 * @throws XPathExpressionException
	 */
	public static String getNodeTextByXPath(Document doc, String path)
			throws XPathExpressionException {
		Node node = getNodeByXPath(doc, path);
		String text = " ";
		if (node != null) {
			Node textNode = node.getFirstChild();
			if ((textNode != null)
					&& (textNode.getNodeType() == Node.TEXT_NODE))
				text = textNode.getNodeValue();
		}
		return text;
	}
	/**
	 * @param doc the document to find in
	 * @param path the xpath
	 * @return the link of the node
	 * @throws XPathExpressionException
	 */
	public static String getLinkByXPath(Document doc, String path) throws XPathExpressionException{
		Element elem = (Element)getNodeByXPath(doc, path);
		String link = elem.getAttribute("href");
		return link;
	}
	
	/**
	 * add a new element with text to a root element
	 * @param doc the document to add the element to
	 * @param root the root element to append the text node to
	 * @param tagName the tag name of newly created element
	 * @param text the content of the text node
	 */
	public static void addTextElement(Document doc, Element parent, String tagName, String text){
		Element elem = doc.createElement(tagName);
		elem.appendChild(doc.createTextNode(text));
		parent.appendChild(elem);
	}
	
	/**
	 * @throws IOException 
	 * 
	 */
	public static void saveFile(InputStream in, String filePath) throws IOException{
		OutputStream out = new FileOutputStream(new File(filePath));
		byte[] buffer = new byte[1024 * 1024];
		int readBytes;
		while((readBytes = in.read(buffer)) != -1){
			out.write(buffer, 0, readBytes);
		}
		out.close();
	}
}
