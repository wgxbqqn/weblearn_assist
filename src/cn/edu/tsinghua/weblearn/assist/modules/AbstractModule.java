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
import cn.edu.tsinghua.weblearn.assist.core.Utils;


public abstract class AbstractModule implements Module {
	protected String moduleName = "abstract module";
	protected Document inDoc = null;
	protected Document outDoc = null;
	protected String inputEncoding;
	protected String mainURL;
	protected Navigator navigator = null;
	protected static DocumentBuilderFactory docFactory = DocumentBuilderFactory
			.newInstance();
	protected DocumentBuilder docBuilder;        //dom解析器  
	AbstractModule(Navigator nav, String mainURL) {
		this.navigator = nav;
		this.mainURL = mainURL;
		initDocBuilder();
		prepareOutputDocument();
		prepareInputDocument();
	}
	AbstractModule(Navigator nav) {
		this.navigator = nav;
		initDocBuilder();
	}
	private void initDocBuilder() {
		try {
			docBuilder = docFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public String getModuleName() {
		return moduleName;
	}

	@Override
	public void setModuleName(String name) {
		moduleName = name;
	}

	@Override
	public Document getOutputDocument() {
		return outDoc;
	}

	@Override
	public boolean saveTo(String filepath, String fileName) {                   
		boolean status = true;
		Utils.checkFilePath(filepath);
		if (outDoc != null) {
			try {
				Transformer transformer = TransformerFactory.newInstance()
						.newTransformer();
				Result result = new StreamResult(new File(filepath+"/" + fileName));
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

	@Override
	public boolean loadFrom(String filepath,String fileName) {      //从本地读取xml文件
		boolean status = true;
		try {
			FileInputStream in = new FileInputStream(new File(filepath +"/" + fileName));
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

	protected void prepareOutputDocument() {                       //
		outDoc = docBuilder.newDocument();
		Element rootElement = outDoc.createElement("content");
		outDoc.appendChild(rootElement);
		rootElement.setAttribute("name", this.moduleName);
	}
	
	protected boolean prepareInputDocument() {
		String html = navigator.getHtml(this.mainURL);
		if (html == null){
			return false;
		}
		else {
			inDoc = navigator.analyze(html, this.inputEncoding);         //这个是用tidy得到的xml，在内存中
			System.out.println("Input Document Prepared");
			return true;
		}
	}

	@Override
	public boolean sync() {
		if (!prepareInputDocument())
			return false;

		prepareOutputDocument();
		if (!buildOutputDocument())
			return false;

		return true;
	}

	protected abstract boolean buildOutputDocument();                   //抽象方法
}
