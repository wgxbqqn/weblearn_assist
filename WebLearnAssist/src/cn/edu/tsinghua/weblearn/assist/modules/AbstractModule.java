/**
 * 
 */
package cn.edu.tsinghua.weblearn.assist.modules;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import cn.edu.tsinghua.weblearn.assist.core.Navigator;

/**
 * This class provides a skeletal implementation of the Module interface
 */
public abstract class AbstractModule implements Module {
	/**
	 * the name of the module
	 */
	protected String moduleName = "abstract module";
	/**
	 * the input document object
	 */
	protected Document inDoc = null;
	/**
	 * the output document object for saving
	 */
	protected Document outDoc = null;
	/**
	 * charactor encoding of the main page of the module, must be specified in
	 * the constructor
	 */
	protected String inputEncoding;
	/**
	 * URL of main page of the module, must be specified in the constructor
	 */
	protected String mainURL;
	/**
	 * a handle to an Navigator object
	 */
	protected Navigator navigator = null;
	/**
	 * a document builder factory for generating new document builder
	 */
	protected static DocumentBuilderFactory docFactory = DocumentBuilderFactory
			.newInstance();
	/**
	 * a document builder for creating and parsing xml files
	 */
	protected DocumentBuilder docBuilder;

	/**
	 * construct a module with a navigator and main url
	 */
	AbstractModule(Navigator nav, String mainURL) {
		this.navigator = nav;
		this.mainURL = mainURL;

		initDocBuilder();
	}

	/**
	 * construct a module with a navigator
	 */
	AbstractModule(Navigator nav) {
		this.navigator = nav;

		initDocBuilder();
	}

	/**
	 * initialize the document builder object
	 */
	private void initDocBuilder() {
		try {
			docBuilder = docFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String name) {
		moduleName = name;
	}

	public Document getOutputDocument() {
		return outDoc;
	}

	public boolean saveTo(String fileName) {
		boolean status = true;
		if (outDoc != null) {
			try {
				Transformer transformer = TransformerFactory.newInstance()
						.newTransformer();
				Result result = new StreamResult(new File(fileName));
				Source source = new DOMSource(outDoc);

				transformer.transform(source, result);

				status = true;
			} catch (TransformerConfigurationException e) {
				System.err.println("Error: transform configuration exception");
				e.printStackTrace();
				status = false;
			} catch (TransformerException e) {
				System.err.println("Error: transformer exception");
				e.printStackTrace();
				status = false;
			}
		} else {
			// invalid document
			status = false;
		}
		return status;
	}

	public boolean loadFrom(String fileName) {
		boolean status = true;

		try {
			FileInputStream in = new FileInputStream(new File(fileName));
			outDoc = docBuilder.parse(in);
		} catch (IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			status = false;
		} catch (SAXException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			status = false;
		}
		return status;
	}

	/**
	 * Create the output document object for storing If an exception occurs, the
	 * output document is invalid
	 */
	protected void prepareOutputDocument() {
		outDoc = docBuilder.newDocument();

		Element rootElement = outDoc.createElement("content");
		outDoc.appendChild(rootElement);
		rootElement.setAttribute("name", this.moduleName);
	}

	/**
	 * get the html from the main url and analyze it
	 * 
	 * @return true if the operation is successful
	 */
	protected boolean prepareInputDocument() {
		String html = navigator.getHtml(this.mainURL);
		if (html == null)
			return false;
		else {
			inDoc = navigator.analyze(html, this.inputEncoding);
			return true;
		}
	}

	public boolean sync() {
		if (!prepareInputDocument())
			return false;

		prepareOutputDocument();
		if (!buildOutputDocument())
			return false;

		return true;
	}

	/**
	 * extract information from the input document and store then in the output
	 * document
	 * 
	 * @return true if the operation is successful
	 */
	protected abstract boolean buildOutputDocument();
}
