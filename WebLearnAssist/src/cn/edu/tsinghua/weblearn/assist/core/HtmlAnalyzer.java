package cn.edu.tsinghua.weblearn.assist.core;

import java.io.InputStream;
import java.io.OutputStream;

import org.w3c.dom.Document;
import org.w3c.tidy.Tidy;

public class HtmlAnalyzer {
	protected String content;

	protected Tidy tidy;

	protected Document doc = null;

	public HtmlAnalyzer(){
		tidy = new Tidy();

		tidy.setXmlOut(true);
		tidy.setShowWarnings(false);
		tidy.setQuiet(true);
		tidy.setTidyMark(true);
		tidy.setForceOutput(true);
		tidy.setOutputEncoding("UTF-8");
		tidy.setDropFontTags(true);
		tidy.setMakeClean(true);
		tidy.setRawOut(true);
	}

	/**
	 * analyze the html content specified in the parameter
	 * 
	 * @param htmlContent
	 * @return an analyzed document object
	 */
	public Document analyze(String htmlContent, String inputEncoding) {
		content = htmlContent;
		
		tidy.setInputEncoding(inputEncoding);
		
		InputStream in = Utils.stringToInputStream(content);
		OutputStream out = null;

		doc = tidy.parseDOM(in, out);
		
		return this.doc;
	}

	/**
	 * get the DOM object
	 * 
	 * @return the document if the input html is valid, null if the input is
	 *         invalid
	 */
	public Document getDocument() {
		return doc;
	}
}
