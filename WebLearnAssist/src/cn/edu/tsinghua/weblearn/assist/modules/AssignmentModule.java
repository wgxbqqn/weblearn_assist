/**
 * 
 */
package cn.edu.tsinghua.weblearn.assist.modules;

import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import cn.edu.tsinghua.weblearn.assist.core.Navigator;
import cn.edu.tsinghua.weblearn.assist.core.Utils;

/**
 * assignment module
 * 课程作业
 *
 */
public class AssignmentModule extends AbstractModule {
	
	private String tablePath = "/html/body/table/tr[3]/td/table[2]";
	
	private int rowBeginIndex = 1;
	
	private String titlePath = "/td/a";
	
	private String titleLinkPath = "/td/a";
	
	private String beginDatePath = "/td[2]";
	
	private String dueDatePath = "/td[3]";
	
	private String submitStatusPath = "/td[4]";
	
	private String fileSizePath = "/td[5]";
	

	AssignmentModule(Navigator nav, String mainURL) {
		super(nav, mainURL);
		this.moduleName = "assignment";
		this.inputEncoding = "UTF-8";
	}

	@Override
	protected boolean buildOutputDocument() {
		boolean status = true;
		try {
			Node tableNode = Utils.getNodeByXPath(inDoc, tablePath);

			Element root = outDoc.getDocumentElement();

			//the last row is invalid
			for (int i = rowBeginIndex; i < tableNode.getChildNodes()
					.getLength(); i++) {
				Element record = outDoc.createElement("record");

				// title
				String title = Utils.getNodeTextByXPath(inDoc, tablePath
						+ "/tr[" + i + "]" + titlePath);
				Utils.addTextElement(outDoc, record, "title", title);
				// title link
				String titleLink = Utils.getLinkByXPath(inDoc, tablePath
						+ "/tr[" + i + "]" + titleLinkPath);
				Utils.addTextElement(outDoc, record, "titleLink", titleLink);
				//begin date
				String beginDate = Utils.getNodeTextByXPath(inDoc, tablePath
						+ "/tr[" + i + "]" + beginDatePath);
				Utils.addTextElement(outDoc, record, "beginDate", beginDate);
				//due date
				String dueDate = Utils.getNodeTextByXPath(inDoc, tablePath
						+ "/tr[" + i + "]" + dueDatePath);
				Utils.addTextElement(outDoc, record, "dueDate", dueDate);
				//submit status
				String submitStatus = Utils.getNodeTextByXPath(inDoc, tablePath
						+ "/tr[" + i + "]" + submitStatusPath);
				Utils.addTextElement(outDoc, record, "submitStatus", submitStatus);
				//file size
				String fileSize = Utils.getNodeTextByXPath(inDoc, tablePath
						+ "/tr[" + i + "]" + fileSizePath);
				Utils.addTextElement(outDoc, record, "fileSize", fileSize);

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

}
