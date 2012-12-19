/**
 * 
 */
package cn.edu.tsinghua.weblearn.assist.modules;

import javax.xml.xpath.XPathExpressionException;

import cn.edu.tsinghua.weblearn.assist.core.Navigator;
import cn.edu.tsinghua.weblearn.assist.core.Utils;

import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Amouncement module 课程公告
 */
public class AnouncementModule extends AbstractModule {

	private String tablePath = "/html/body/table/tr[2]/td/table";

	private int rowBeginIndex = 2;

	private String titlePath = "/td[2]/a";

	private String titleLinkPath = "/td[2]/a";

	private String authorPath = "/td[3]";

	private String datePath = "/td[4]";

	AnouncementModule(Navigator nav, String mainURL) {
		super(nav, mainURL);
		this.moduleName = "anoucement";
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
				
				//title
				String title = Utils.getNodeTextByXPath(inDoc, tablePath
						+ "/tr[" + i + "]" + titlePath);
				Utils.addTextElement(outDoc, record, "title", title);
				//title link
				
				String titleLink = Utils.getLinkByXPath(inDoc, tablePath
						+ "/tr[" + i + "]" + titleLinkPath);
				Utils.addTextElement(outDoc, record, "titleLink", titleLink);
				
				//author
				String author = Utils.getNodeTextByXPath(inDoc, tablePath
						+ "/tr[" + i + "]" + authorPath);
				Utils.addTextElement(outDoc, record, "author", author);
				//date
				String date =  Utils.getNodeTextByXPath(inDoc, tablePath
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

}
