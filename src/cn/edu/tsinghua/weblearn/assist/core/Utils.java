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

//import cn.edu.tsinghua.weblearn.assist.modules.File;

public class Utils {
	private static Calendar calendar = Calendar.getInstance();
	private static XPath xpath = XPathFactory.newInstance().newXPath();
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

	public static InputStream stringToInputStream(String str) {
		InputStream is = new ByteArrayInputStream(str.getBytes());
		return is;
	}
	
    public  static Node getNodeByXPath(Document doc, String path)
			throws XPathExpressionException {
		XPathExpression expr = xpath.compile(path);
		Node node = (Node) expr.evaluate(doc.getDocumentElement(),
				XPathConstants.NODE);
		return node;
	}
    
    public static String dealCharset(String originEncode, String nowEncode, String str) {
    	String result = "";
    	try {
			result = new String(str.getBytes(originEncode),nowEncode);
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
    }
    public static String changeCharset(String str, String oldCharset, String newCharset)
    	    throws UnsupportedEncodingException {
    	   if (str != null) {
    	    //用旧的字符编码解码字符串。解码可能会出现异常。
    	    byte[] bs = str.getBytes(oldCharset);
    	    //用新的字符编码生成字符串
    	    return new String(bs, newCharset);
    	   }
    	   return null;
    	}
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
		//System.out.println(text);
		return text;
	}
	
	public static String getLinkByXPath(Document doc, String path) throws XPathExpressionException{
		Element elem = (Element)getNodeByXPath(doc, path);
		String link = elem.getAttribute("href");
		//System.out.println("The link: " + link);
		return link;
	}
	
	public static void addTextElement(Document doc, Element parent, String tagName, String text){
		Element elem = doc.createElement(tagName);
		elem.appendChild(doc.createTextNode(text));
		parent.appendChild(elem);
	}
	
	public static void saveFile(InputStream in, String filePath) throws IOException{
		OutputStream out = new FileOutputStream(new File(filePath));
		byte[] buffer = new byte[1024 * 1024];
		int readBytes;
		while((readBytes = in.read(buffer)) != -1){
			out.write(buffer, 0, readBytes);
		}
		out.close();
	}
	public static void checkFilePath(String filepath) {
		File f = new File(filepath);
		if(f.exists()) {
			System.out.println("Existed");
			return;
		} else {
			f.mkdirs();
			System.out.println("Created" + f);
		}
	}
	public static boolean checkDownLoadFile(String filepath) {
		File f = new File(filepath);
		if(f.exists()) {
			System.out.println("Already Existed");
			return false;
		} else {
			System.out.println("Has not been downloaded");
			return true;
		}
	}
	
	public static Date stringToDate(String str) {
		String[] tokens = str.split("-");
		calendar.set(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]),
				Integer.parseInt(tokens[2]));
		return calendar.getTime();
	}
	
	public static boolean compareDate(String due) {
		String[] tokens = due.split("-");
		int year_now = calendar.get(Calendar.YEAR);
		int month_now = calendar.get(Calendar.MONTH);
		boolean flag = false;
		month_now++;
		int day_now = calendar.get(Calendar.DATE);
		if(year_now <= Integer.parseInt(tokens[0])) {
			if(month_now <= Integer.parseInt(tokens[1])) {
				if(day_now <= Integer.parseInt(tokens[2])) {
					flag = true;
				}
			}
		}
		return flag;
		}
	
	public static String dateToString(Date date) {
		StringBuffer buffer = new StringBuffer();
		calendar.setTime(date);
		buffer.append(calendar.get(Calendar.YEAR));
		buffer.append("-");
		buffer.append((calendar.get(Calendar.MONTH)+1));
		buffer.append("-");
		buffer.append(calendar.get(Calendar.DATE));
		return buffer.toString();
	}
}
