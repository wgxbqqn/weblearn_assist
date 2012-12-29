package cn.edu.tsinghua.weblearn.assist.core;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

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
		tidy.setOutputEncoding(Charset.defaultCharset().toString());
		tidy.setDropFontTags(true);
		tidy.setMakeClean(true);
		tidy.setRawOut(true);
	}
	
	public Document analyze(String htmlContent, String inputEncoding) {
		content = htmlContent;
		tidy.setInputEncoding(inputEncoding);
		InputStream in = Utils.stringToInputStream(content);
		OutputStream out = null;
		doc = tidy.parseDOM(in, out);
		return this.doc;
	}
	public Document getDocument() {
		return doc;
	}
}
