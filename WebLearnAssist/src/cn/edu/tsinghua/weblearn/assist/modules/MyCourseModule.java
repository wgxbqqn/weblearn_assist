package cn.edu.tsinghua.weblearn.assist.modules;

import javax.xml.xpath.XPathExpressionException;

import cn.edu.tsinghua.weblearn.assist.core.Navigator;
import cn.edu.tsinghua.weblearn.assist.core.Utils;

import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * MyCourse module
 * 鎴戠殑璇剧▼
 *
 */
public class MyCourseModule extends AbstractModule {

	private String tablePath = "/html/body/table[2]";

	private int rowBeginIndex = 5;

	// the first item: /html/body/table[2]/tr[5]/td/a
	private String courseNamePath = "/td/a";
	// the first item: /html/body/table[2]/tr[5]/td/a
	private String courseLinkPath = "/td/a";

	public MyCourseModule(Navigator nav) {
		super(nav);
		this.moduleName = "main page";
		this.inputEncoding = "UTF-8";
		this.mainURL = "http://learn.tsinghua.edu.cn/MultiLanguage/lesson/student/MyCourse.jsp?language=cn";
	}
	
	@Override
	public boolean sync(){
		super.sync();
		
		boolean status = true;
		
		Element root = outDoc.getDocumentElement();
		try{
			//synchronize anouncement
			/*
			for(int i = 1 ; i <= root.getChildNodes().getLength(); i ++){
				String courseID = Utils.getNodeTextByXPath(outDoc, "/content/record[" + i + "]/courseID");
				String courseName = Utils.getNodeTextByXPath(outDoc, "/content/record[" + i + "]/courseName");
				String link = "http://learn.tsinghua.edu.cn/MultiLanguage/public/bbs/getnoteid_student.jsp?course_id=" + courseID;
				link = navigator.getRedirectURL(link);
				AnouncementModule module = new AnouncementModule(this.navigator, link);
				module.sync();
				module.saveTo("local/anouncement/" + courseName + ".xml");
			}
			*/
			//synchronize course file info
			/*
			for(int i = 1 ; i <= root.getChildNodes().getLength(); i ++){
				String courseID = Utils.getNodeTextByXPath(outDoc, "/content/record[" + i + "]/courseID");
				String courseName = Utils.getNodeTextByXPath(outDoc, "/content/record[" + i + "]/courseName");
				String link = "http://learn.tsinghua.edu.cn/MultiLanguage/lesson/student/download.jsp?course_id=" + courseID;
				CourseFileModule module = new CourseFileModule(this.navigator, link);
				module.sync();
				module.downloadAllFiles();
				module.saveTo("local/course_files/" + courseName + ".xml");
			}
			*/
			
			//synchronize assignment
			/*
			for(int i = 1 ; i <= root.getChildNodes().getLength(); i ++){
				String courseID = Utils.getNodeTextByXPath(outDoc, "/content/record[" + i + "]/courseID");
				String courseName = Utils.getNodeTextByXPath(outDoc, "/content/record[" + i + "]/courseName");
				String link = "http://learn.tsinghua.edu.cn/MultiLanguage/lesson/student/hom_wk_brw.jsp?course_id=" + courseID;
				AssignmentModule module = new AssignmentModule(this.navigator, link);
				module.sync();
				module.saveTo("local/assignments/" + courseName + ".xml");
			}
			*/
			
			String courseID = "93961";
			String courseName = Utils.getNodeTextByXPath(outDoc, "/content/record[" + 1 + "]/courseName");
			courseName = "文献检索与利用";
			String link = "http://learn.tsinghua.edu.cn/MultiLanguage/lesson/student/download.jsp?course_id=" + courseID;
			CourseFileModule module = new CourseFileModule(this.navigator, link);
			module.sync();
			module.downloadAllFiles();
			module.saveTo("local/course_files/" + courseName + ".xml");
		}catch(XPathExpressionException e){
			System.err.println(e.getMessage());
			e.printStackTrace();
			status = false;
		}
		return status;
	}

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
