/**
 * 
 */
package cn.edu.tsinghua.weblearn.assist.modules;

import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cn.edu.tsinghua.weblearn.assist.core.Navigator;
import cn.edu.tsinghua.weblearn.assist.core.Utils;

/**
 * course file module 课程文件
 */
public class CourseFileModule extends AbstractModule {

	private String tablePath = "/html/body/table/tr[5]/td/div/table";

	private int rowBeginIndex = 2;

	private String titlePath = "/td[2]/a";

	private String fileLinkPath = "/td[2]/a";

	private String briefPath = "/td[3]";

	private String fileSizePath = "/td[4]";

	private String datePath = "/td[5]";

	CourseFileModule(Navigator nav, String mainURL) {
		super(nav, mainURL);
		this.moduleName = "course file";
		this.inputEncoding = "UTF-8";
	}

	@Override
	protected boolean buildOutputDocument() {
		boolean status = true;
		try {
			Node tableNode = Utils.getNodeByXPath(inDoc, tablePath);

			Element root = outDoc.getDocumentElement();

			for (int i = rowBeginIndex; i <= tableNode.getChildNodes()
					.getLength(); i++) {
				Element record = outDoc.createElement("record");

				// title
				String title = Utils.getNodeTextByXPath(inDoc, tablePath
						+ "/tr[" + i + "]" + titlePath);
				Utils.addTextElement(outDoc, record, "title", title);
				// filelink
				String fileLink = Utils.getLinkByXPath(inDoc, tablePath
						+ "/tr[" + i + "]" + fileLinkPath);
				fileLink = "http://learn.tsinghua.edu.cn" + fileLink;
				Utils.addTextElement(outDoc, record, "fileLink", fileLink);
				//brief description
				String brief = Utils.getNodeTextByXPath(inDoc,  tablePath
						+ "/tr[" + i + "]" + briefPath);
				Utils.addTextElement(outDoc, record, "brief", brief);
				//file size
				String fileSize = Utils.getNodeTextByXPath(inDoc,  tablePath
						+ "/tr[" + i + "]" + fileSizePath);
				Utils.addTextElement(outDoc, record, "fileSize", fileSize);
				// date
				String date = Utils.getNodeTextByXPath(inDoc, tablePath
						+ "/tr[" + i + "]" + datePath);
				Utils.addTextElement(outDoc, record, "date", date);

				root.appendChild(record);
			}
		} catch (XPathExpressionException e) {
			e.printStackTrace();
			status = false;
		} catch (DOMException e) {
			e.printStackTrace();
			status = false;
		}
		return status;
	}
	
	public void downloadAllFiles(){
		Element root = outDoc.getDocumentElement();
		NodeList fileLinkList = root.getElementsByTagName("fileLink");
		for(int i = 0; i < fileLinkList.getLength(); i ++){
			Node node = fileLinkList.item(i);
			String link = node.getFirstChild().getNodeValue();
			String fileName = this.navigator.getDownloadedFileName(link, "ISO8859-1");
			System.out.println("dowloading file: " + fileName);
			this.navigator.downloadFile(link, "course_files/", "ISO8859-1");
		}
	}

}
