/**
 * 
 */
package cn.edu.tsinghua.weblearn.assist.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;

/**
 * A http-based class for receiving html and files and analyzing html
 * 
 */
public class Navigator {
	private HttpClient httpClient = new DefaultHttpClient();
	private String inputEncoding = "UTF-8";
	private String outputEncoding = "gb2312";
	//private String filenameEncoding = "ISO8859-1";
	private HtmlAnalyzer analyzer = new HtmlAnalyzer();
	private String content;
	private String fileName;
	public String getHtml(String url) {                        
		HttpGet request = new HttpGet(url);
		try {
			HttpResponse response = httpClient.execute(request);
			HttpEntity entity = response.getEntity();
			content = Utils.inputStreamToString(entity.getContent(),
					outputEncoding);
			EntityUtils.consume(entity);
		} catch (Exception e) {
			e.printStackTrace();
			content = null;
		} finally {
			request.releaseConnection();
		}
		return content;
	}
	
	public String getHtml(String url, PostData data) {                        //用于登录
		HttpPost httpPost = new HttpPost(url);
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		for (Map.Entry<String, String> entry : data.getHashMap().entrySet()) {
			nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nvps));
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			content = Utils.inputStreamToString(entity.getContent(),
					outputEncoding);
			EntityUtils.consume(entity);
		} catch (IOException e) {
			System.out.println("Cannot Login");
			System.err.println(e.getMessage());
			e.printStackTrace();
			content = null;
		} finally {
			httpPost.releaseConnection();
		}
		return content;
	}

	public boolean setInputEncoding(String charsetName) {
		inputEncoding = charsetName;
		return true;
	}

	public boolean setOutputEncoding(String charsetName) {
		outputEncoding = charsetName;
		return true;
	}
	
	public boolean downloadFile(String url, String savePath,                   //需要文件下载的url地址
			String fileNameEncoding) {
		boolean status = true;
		HttpGet request = new HttpGet(url);
		try {
			HttpResponse response = httpClient.execute(request);
			HttpEntity entity = response.getEntity();
			Header header = entity.getContentType();
			System.out.println("Content type: name: " + header.getName()
					+ ", value: " + header.getValue());
			String filePath = savePath 
					+ getDownloadedFileName(response, fileNameEncoding);
			Utils.saveFile(entity.getContent(), filePath);                   //内容作为inputstream 用工具函数保存文件
			EntityUtils.consume(entity);
		} catch (IOException e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
			status = false;
		} finally {
			request.releaseConnection();
		}
		return status;
	}

	public Document analyze(String html, String inputEncoding) {         //将html 文件 用tidy 变成document 文件
		return analyzer.analyze(html, inputEncoding);
	}
	public String getRedirectURL(String url) {
		String redirectURL = url;
		HttpGet request = new HttpGet(url);
		try {
			HttpResponse response = httpClient.execute(request);
			Header header = response.getFirstHeader("Location");
			if (header != null) {
				redirectURL = header.getValue();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			request.releaseConnection();
		}
		return redirectURL;
	}
	private String getDownloadedFileName(HttpResponse response,          //获得下载的文件
			String fileNameEncoding) {
		Header contentHeader = response.getFirstHeader("Content-Disposition");
		if (contentHeader != null) {
			HeaderElement[] values = contentHeader.getElements();
			if (values.length == 1) {
				NameValuePair param = values[0].getParameterByName("filename");
				if (param != null) {
					try {
						fileName = new String(param.getValue().getBytes(
								fileNameEncoding));
					} catch (Exception e) {
						e.printStackTrace();
						fileName = null;
					}
				}
			}
		}
		System.out.println(fileName);
		return fileName;
	}
	public String getDownloadedFileName(String url, String fileNameEncoding) {
		HttpGet request = new HttpGet(url);
		String fileName = null;
		try {
			HttpResponse response = httpClient.execute(request);
			HttpEntity entity = response.getEntity();
			Header header = entity.getContentType();
			System.out.println("Content type: name: " + header.getName()
					+ ", value: " + header.getValue());
			fileName = getDownloadedFileName(response, fileNameEncoding);
		} catch (Exception e) {
			e.printStackTrace();
			fileName = null;
		} finally {
			request.releaseConnection();
		}
		return fileName;
	}
}

