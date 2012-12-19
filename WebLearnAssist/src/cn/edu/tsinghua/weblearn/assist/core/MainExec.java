package cn.edu.tsinghua.weblearn.assist.core;

import cn.edu.tsinghua.weblearn.assist.modules.MyCourseModule;

public class MainExec {
	
	private static Navigator navigator;

	/**
	 * @param args
	 * args[0]: user name
	 * args[1]: password
	 */
	public static void main(String[] args) {
		if(args.length != 2){
			System.err.println("Usage: MainExec <user name> <password>");
			return;
		}
		
		navigator = new Navigator();
		
		PostData post = new PostData();
		post.addEntry("userid", args[0]);
		post.addEntry("userpass", args[1]);
		
		navigator.getHtml("https://learn.tsinghua.edu.cn/MultiLanguage/lesson/teacher/loginteacher.jsp", post);
		navigator.getHtml("https://learn.tsinghua.edu.cn/MultiLanguage/lesson/teacher/loginteacher_action.jsp");
		navigator.getHtml("http://learn.tsinghua.edu.cn/MultiLanguage/lesson/teacher/mainteacher.jsp");
		navigator.getHtml("http://learn.tsinghua.edu.cn/MultiLanguage/lesson/student/mainstudent.jsp");
		
		MyCourseModule module = new MyCourseModule(navigator);
		if(module.sync()){
			System.out.println("Sync successful");
			module.saveTo("MyCourses.xml");
		}
		
	}

}
