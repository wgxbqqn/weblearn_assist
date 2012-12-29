package cn.edu.tsinghua.weblearn.assist.core;

import org.w3c.dom.Document;

import cn.edu.tsinghua.weblearn.assist.modules.MyCourseModule;

import cn.edu.tsinghua.weblearn.assist.gui.SwingConsole;
import cn.edu.tsinghua.weblearn.assist.gui.Window;

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
        String g_bbs_path = "F:/SE/bbs";
		String g_homework_path = "F:/SE/homework";
		String g_file_path = "F:/SE/file";
		String g_root_path = "F:/SE/root";
		String g_main_xml = "Mycourses.xml";
		MyCourseModule module = new MyCourseModule(navigator);
		module.set_announcement_path(g_bbs_path);
		module.set_assignment_path(g_homework_path);
		module.set_file_path(g_file_path);
		/*
		if(module.sync()){
			System.out.println("Sync successful");
			module.saveTo(g_root_path, g_main_xml);
		}
		*/
		MessageCollector collector = new MessageCollector(g_homework_path, g_root_path, g_main_xml);
		try {
			collector.gen_message();
		} catch (Exception e) {
			System.out.println("Error");
			e.printStackTrace();
		}
	
	   System.out.println("ok");
	}
}
	

