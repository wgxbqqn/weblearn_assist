/**
 * 
 */
package cn.edu.tsinghua.weblearn.assist.tools;

import java.io.*;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.tidy.Tidy;

/**
 * make non-standard html file clean and tidy
 */
public class HtmlCleaner {

	/**
	 * @param args
	 *            command line arguments, arg[0]: input file, arg[1]: output
	 *            file
	 */
	public static void main(String[] args) {
		if (args.length != 2) {
			System.err.println("Usage: HtmlCleaner <input html> <output html>");
			return;
		} else {
			InputStream in = System.in;
			OutputStream out = System.out;
			try {
				in = new FileInputStream(args[0]);
				out = new FileOutputStream(args[1]);
				// configure JTidy
				Tidy tidy = new Tidy();
				tidy.setXmlOut(true);
				tidy.setShowWarnings(true);
				tidy.setQuiet(true);
				tidy.setTidyMark(true);
				tidy.setInputEncoding("UTF-8");
				//tidy.setOutputEncoding("UTF-8");
				tidy.setForceOutput(true);
				tidy.setDropFontTags(true);
				tidy.setMakeClean(true);
				//tidy.setRawOut(true);
				// parse document
				Document doc = tidy.parseDOM(in, out);
				//print document
				printDOM(doc);

			} catch (IOException e) {
				System.err.println("Error: IOException");
				e.printStackTrace();
			}
		}

	}
	
	/**
	 * print the content of the document tree
	 */
	public static void printDOM(Document doc) {
		Node root = doc.getDocumentElement();
		printNodeRecur(root, 0, "", 1);
	}

	/**
	 * print the name, xpath and type of the nodes recursively
	 * 
	 * @param node
	 *            the root of the node tree
	 * @param depth
	 *            the depth from the node to the document root
	 * @param xpath
	 *            the xpath of the node
	 * @param index
	 *            the index of the node
	 */
	public static void printNodeRecur(Node node, int depth, String xpath,
			int index) {
		for (int i = 0; i < depth; i++)
			System.out.print("--");
		System.out.print("Name: " + node.getNodeName());

		String nextXPath = new String(xpath);
		nextXPath += "/";
		nextXPath += node.getNodeName();
		if (index > 1)
			nextXPath += "[" + index + "]";
		System.out.print(", XPath: " + nextXPath);

		System.out.print(", Value: " + node.getNodeValue() + "\n");

		NodeList nodeList = node.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			printNodeRecur(nodeList.item(i), depth + 1, nextXPath, i + 1);
		}
	}

}
