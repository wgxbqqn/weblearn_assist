package cn.edu.tsinghua.weblearn.assist.modules;

import java.io.UnsupportedEncodingException;

import javax.xml.xpath.XPathExpressionException;

import cn.edu.tsinghua.weblearn.assist.core.Navigator;
import cn.edu.tsinghua.weblearn.assist.core.Utils;

import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * MyCourse module
 * 获得各个课程的信息
 *
 */
public class MyCourseModule extends AbstractModule {
	private String tablePath = "/html/body/table[2]";
	private int rowBeginIndex = 5;
	// the first item: /html/body/table[2]/tr[5]/td/a
	private String courseNamePath = "/td/a";
	// the first item: /html/body/table[2]/tr[5]/td/a
	private String courseLinkPath = "/td/a";
	private String announcement_path = "";
	private String assignment_path = "";
	private String file_root_path = "";
	public MyCourseModule(Navigator nav) {
		super(nav);
		this.moduleName = "main page";
		this.inputEncoding = "UTF-8";
		this.mainURL = "http://learn.tsinghua.edu.cn/MultiLanguage/lesson/student/MyCourse.jsp?language=cn";
	}
	
	public void set_announcement_path(String path) {
		Utils.checkFilePath(path);
		announcement_path = path;
	}
	
	public void set_assignment_path(String path) {
		Utils.checkFilePath(path);
		assignment_path = path;
	}
	
	public void set_file_path(String path) {
		Utils.checkFilePath(path);
		file_root_path = path;
	}
	@Override
	public boolean sync(){
		super.sync();
		boolean status = true;
		Element root = outDoc.getDocumentElement();
		try{
			//synchronize announcement
			for(int i = 1 ; i <= root.getChildNodes().getLength(); i ++){
				String courseID = Utils.getNodeTextByXPath(outDoc, "/content/record[" + i + "]/courseID");
				//String courseName = Utils.getNodeTextByXPath(outDoc, "/content/record[" + i + "]/courseName");
				String link = "http://learn.tsinghua.edu.cn/MultiLanguage/public/bbs/getnoteid_student.jsp?course_id=" + courseID;
				link = navigator.getRedirectURL(link);
				AnouncementModule module = new AnouncementModule(this.navigator, link);
				module.sync();
				module.saveTo(announcement_path,"bbs_"+ i + ".xml");
			}
			//synchronize course file info
            /*
			for(int i = 1 ; i <= root.getChildNodes().getLength(); i ++) {
				String courseID = Utils.getNodeTextByXPath(outDoc, "/content/record[" + i + "]/courseID");
				String courseName = Utils.getNodeTextByXPath(outDoc, "/content/record[" + i + "]/courseName");
				
				String link = "http://learn.tsinghua.edu.cn/MultiLanguage/lesson/student/download.jsp?course_id=" + courseID;
				String courseFileName = file_root_path + "/" + courseName;
				Utils.checkFilePath(courseFileName);
				CourseFileModule module = new CourseFileModule(this.navigator, link);
				module.sync();
				module.downloadAllFiles(courseFileName);
				module.saveTo(courseFileName,"file_" + i + ".xml");
			}
			*/
			//synchronize assignment
			for(int i = 1 ; i <= root.getChildNodes().getLength(); i ++){
				String courseID = Utils.getNodeTextByXPath(outDoc, "/content/record[" + i + "]/courseID");
				//String courseName = Utils.getNodeTextByXPath(outDoc, "/content/record[" + i + "]/courseName");
				String link = "http://learn.tsinghua.edu.cn/MultiLanguage/lesson/student/hom_wk_brw.jsp?course_id=" + courseID;
				AssignmentModule module = new AssignmentModule(this.navigator, link);
				module.sync();
				module.saveTo(assignment_path, "hw_" + i + ".xml");
			}
			
		}catch(XPathExpressionException e){
			System.err.println(e.getMessage());
			e.printStackTrace();
			status = false;
		}
		return status;
	}
	@Override
	protected boolean buildOutputDocument(){
		try {
			Node tableNode = Utils.getNodeByXPath(inDoc, tablePath);
			Element root = outDoc.getDocumentElement();
			for (int i = rowBeginIndex; i <= tableNode.getChildNodes()
					.getLength(); i++) {
				Element record = outDoc.createElement("record");
				//course name
				String courseName = Utils.getNodeTextByXPath(inDoc, tablePath
						+ "/tr[" + i + "]" + courseNamePath);
				Utils.addTextElement(outDoc, record, "courseName", courseName);
				//course link
				String courseLink = Utils.getLinkByXPath(inDoc, tablePath
						+ "/tr[" + i + "]" + courseLinkPath);
				String courseID = courseLink.split("=")[1];
				Utils.addTextElement(outDoc, record, "courseID", courseID);
				root.appendChild(record);
			}
		} catch (XPathExpressionException e) {
			e.printStackTrace();
			return false;
		} catch(DOMException e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
