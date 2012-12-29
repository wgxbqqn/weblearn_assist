package cn.edu.tsinghua.weblearn.assist.core;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import cn.edu.tsinghua.weblearn.assist.core.Utils;
/**
 * Collect the message from the xml generated
 * @author user
 *
 */
public class MessageCollector {
	private String homework_xml_path = "";
	private String inform_root_path = "";
	private String main_xml = "";
	private Document doc;
	private Document rootdoc;
     //从DOM工厂中获得DOM解析器
     //把要解析的xml文档读入DOM解析器
    
	public MessageCollector(String path,String root,String mainXml) {
		this.homework_xml_path = path;
		/*
		 * 作业的xml文件保存地址
		 */
		this.inform_root_path = root;
		/*
		 * 总的课程信息xml保存底地址
		 */
		this.main_xml = mainXml;
	}
	
	private Document gen_doc(String filepath) throws SAXException, IOException {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance(); 
		DocumentBuilder dbBuilder = null;
		try {
			dbBuilder = dbFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	    doc = dbBuilder.parse(filepath);
	    return doc;
	}
    /*
	public ArrayList gen_message() throws Exception {
		ArrayList list = new ArrayList();
		rootdoc = gen_doc(inform_root_path + "/" + main_xml);
		Element root = rootdoc.getDocumentElement();
		String courseID = "";
		String dueDate = "";
		String buffer = "";
		int total_count = 0;
		int unsubmitted_count = 0;
		for(int i = 1; i <= root.getChildNodes().getLength(); i++)  {
			courseID = Utils.getNodeTextByXPath(rootdoc, "/content/record[" + i + "]/courseID");
			doc = gen_doc(homework_xml_path + "/" + "hw_" + i + ".xml");
		    Element child = doc.getDocumentElement();
		    unsubmitted_count = 0;
		    for(int j = 1 ; j <= child.getChildNodes().getLength(); j ++){
		        dueDate = Utils.getNodeTextByXPath(doc, "/content/record["+j+"]/dueDate");
		    	dueDate  = Utils.changeCharset(dueDate,"UTF-8", "gb2312");
		    	if(Utils.compareDate(dueDate)) {
		    		buffer = "Course ID: " + courseID + " Deadline: " + dueDate;
		    		System.out.println(buffer);
		    		list.add(buffer);
		    		unsubmitted_count++;
		    		total_count++;
		    	}
		    }
		    if(unsubmitted_count >0) {
		    	buffer = "\nCourse ID: " + courseID + " : \n" +  unsubmitted_count + "" + "  homework to submit\n\n ";
		    	list.add(buffer);
		    	System.out.println("\nCourse ID: " + courseID + " : \n" +  unsubmitted_count + "" + "  homework to submit\n\n");
		    	System.out.println("\n\n");
		    	}
		    }
		if(total_count > 0) {
			buffer = "\nYou have  " + total_count + "" + "  homeworks to submit in all ";
			list.add(buffer);
			System.out.println("\nYou have  " + total_count + "" + "  homeworks to submit in all ");
        }
		return list;
	}
	*/
	
	public void gen_message() throws Exception {  //这里初步实现了还未截止的作业的提醒功能， 
		rootdoc = gen_doc(inform_root_path + "/" + main_xml);
		Element root = rootdoc.getDocumentElement();
		String courseID = "";
		String dueDate = "";
		int total_count = 0;
		int unsubmitted_count = 0;
		for(int i = 1; i <= root.getChildNodes().getLength(); i++)  {
			courseID = Utils.getNodeTextByXPath(rootdoc, "/content/record[" + i + "]/courseID");
			doc = gen_doc(homework_xml_path + "/" + "hw_" + i + ".xml");
		    Element child = doc.getDocumentElement();
		    unsubmitted_count = 0;
		    for(int j = 1 ; j <= child.getChildNodes().getLength(); j ++){
		        dueDate = Utils.getNodeTextByXPath(doc, "/content/record["+j+"]/dueDate");
		    	dueDate  = Utils.changeCharset(dueDate,"UTF-8", "gb2312");
		    	if(Utils.compareDate(dueDate)) {
		    		System.out.println("Course ID: " + courseID + " Deadline: " + dueDate);
		    		unsubmitted_count++;
		    		total_count++;
		    	}
		    }
		    if(unsubmitted_count >0) {
		    	System.out.println("\nCourse ID: " + courseID + " : \n" +  unsubmitted_count + "" + "  homework to submit");
		    	System.out.println("\n\n");
		    	}
		    }
		if(total_count > 0) {
			System.out.println("You have  " + total_count + "" + "  homeworks to submit in all ");
        }
	}
}


