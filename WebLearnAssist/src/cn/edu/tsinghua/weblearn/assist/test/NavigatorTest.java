package cn.edu.tsinghua.weblearn.assist.test;

import static org.junit.Assert.*;

import org.junit.Test;

import cn.edu.tsinghua.weblearn.assist.core.Navigator;
import cn.edu.tsinghua.weblearn.assist.core.PostData;

public class NavigatorTest {

	@Test
	public final void testGetHtmlString() {
		Navigator navigator = new Navigator();

		assertNotNull(navigator.getHtml("http://learn.tsinghua.edu.cn"));
		assertNotNull(navigator.getHtml("http://portal.tsinghua.edu.cn"));
		assertNotNull(navigator
				.getHtml("https://learn.tsinghua.edu.cn/MultiLanguage/lesson/teacher/loginteacher.jsp"));
	}

	@Test
	public final void testGetHtmlStringPostData() {
		Navigator navigator = new Navigator();

		PostData post = new PostData();
		post.addEntry("userid", "GUEST");
		post.addEntry("userpass", "");

		assertNotNull(navigator
				.getHtml(
						"https://learn.tsinghua.edu.cn/MultiLanguage/lesson/teacher/loginteacher.jsp",
						post));
	}

	@Test
	public final void testDownloadFile() {
		Navigator navigator = new Navigator();
		
		assertTrue(navigator.downloadFile("http://www.tsinghua.edu.cn/publish/th/index.html", "local/test", "GBK"));
		
	}

	@Test
	public final void testGetDownloadedFileName() {
		Navigator navigator = new Navigator();
		
		navigator.getDownloadedFileName("http://www.tsinghua.edu.cn/publish/th/index.html", "GBK");
	}

	@Test
	public final void testAnalyze() {
		Navigator navigator = new Navigator();
		
		String html = navigator.getHtml("http://www.tsinghua.edu.cn/publish/th/index.html");
		navigator.analyze(html, "UTF-8");
	}

}
