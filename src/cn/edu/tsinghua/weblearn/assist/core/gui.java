package cn.edu.tsinghua.weblearn.assist.core;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import cn.edu.tsinghua.weblearn.assist.core.*;
import cn.edu.tsinghua.weblearn.assist.modules.*;

public class gui {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					gui window = new gui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public gui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		JButton button = new JButton("GO");
		final JTextArea textArea = new JTextArea();
		textArea.setBounds(34, 10, 378, 197);
		frame.getContentPane().add(textArea);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Navigator navigator = new Navigator();
				PostData post = new PostData();
				post.addEntry("userid", "gyz10");
				post.addEntry("userpass", "abcdefg199244");
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
				ArrayList list = new ArrayList();
				MessageCollector collector = new MessageCollector(g_homework_path, g_root_path, g_main_xml);
				try {
					collector.gen_message();
					textArea.append(list.toString());
				} catch (Exception e) {
					System.out.println("Error");
					e.printStackTrace();
				}
			}
		});
		button.setBounds(185, 212, 93, 23);
		frame.getContentPane().add(button);
		
		
	}
}
