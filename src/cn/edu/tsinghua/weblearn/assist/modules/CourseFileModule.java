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
 * course file module 下载课程文件模块
 */
public class CourseFileModule extends AbstractModule {             //outdoc indoc 为从AbstractModule 继承
	private String tablePath = "/html/body/table/tr[5]/td/div/table";   
	private int rowBeginIndex = 2;
	private String titlePath = "/td[2]/a";
	private String fileLinkPath = "/td[2]/a";
	private String briefPath = "/td[3]";
	private String fileSizePath = "/td[4]";
	private String datePath = "/td[5]";
	
	CourseFileModule(Navigator nav, String mainURL) {                 //传入的url是关键 ,是进入了相应的课程文件列表地址的url
		super(nav, mainURL);
		this.moduleName = "course file";
		this.inputEncoding = "UTF-8";
		buildOutputDocument();
	}
	@Override
	protected boolean buildOutputDocument() {           //用xpath 从tidy初步得到的indoc生成需要的信息outdoc，类不同，outdoc不同
		boolean status = true;                          //这里的是用来下载文件用的outdoc
		try {
			Node tableNode = Utils.getNodeByXPath(inDoc, tablePath);
			Element root = outDoc.getDocumentElement();
			for (int i = rowBeginIndex; i <= tableNode.getChildNodes()
					.getLength(); i++) {
				Element record = outDoc.createElement("record");
				String title = Utils.getNodeTextByXPath(inDoc, tablePath
						+ "/tr[" + i + "]" + titlePath);
				Utils.addTextElement(outDoc, record, "title", title);
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
	public void downloadAllFiles(String filepath){                                           
		Element root = outDoc.getDocumentElement();
		NodeList fileLinkList = root.getElementsByTagName("fileLink");
		for(int i = 0; i < fileLinkList.getLength(); i ++){
			Node node = fileLinkList.item(i);
			String link = node.getFirstChild().getNodeValue();
			String fileName = this.navigator. getDownloadedFileName(link, "ISO8859-1");
			String savepath = filepath + "/" + fileName;
			if(Utils.checkDownLoadFile(savepath)) {
				System.out.println("dowloading file: " + fileName);
				this.navigator.downloadFile(link, filepath + "/", "ISO8859-1");
			} else {
				System.out.println("Will not download existing file");
			}
		}
	}
}
