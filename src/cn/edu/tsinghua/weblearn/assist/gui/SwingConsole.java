package cn.edu.tsinghua.weblearn.assist.gui;
import javax.swing.*;
/*
 * The Swing FrameWork 
 * 
 */
public class SwingConsole {
	public void run(final JFrame f, final int width, final int height) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				f.setTitle(f.getClass().getSimpleName());
				f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				f.setSize(width, height);
				f.setVisible(true);
			}
		});
	}
}
