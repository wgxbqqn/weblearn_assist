package cn.edu.tsinghua.weblearn.assist.gui;
import javax.swing.*;
import java.awt.*;

public class Window extends JFrame{
	private JButton file_button = new JButton("Get File");
	private JButton homework_button = new JButton("Get HomeWork");
	private JButton bulletin_button = new JButton("Get BBS");
	private JButton login_button = new JButton("Login");
	private JPanel button_pane = new JPanel();
	private JPanel text_pane = new JPanel();
	private JLabel label = new JLabel("Text area");
	private JTextArea txt = new JTextArea();
	private Container container = this.getContentPane();
	public Window() {
		button_pane.setLayout(new BoxLayout(button_pane,BoxLayout.Y_AXIS));
		button_pane.add(bulletin_button);
		button_pane.add(file_button);
		button_pane.add(login_button);
		button_pane.add(Box.createVerticalGlue());
		button_pane.add(Box.createRigidArea(getPreferredSize()));
        
		text_pane.setLayout(new BoxLayout(text_pane, BoxLayout.PAGE_AXIS));
		text_pane.add(label);
		text_pane.add(Box.createRigidArea(new Dimension(0,5)));
		text_pane.add(txt);
		
        
		container.add(button_pane,BorderLayout.EAST);
	    container.add(text_pane, BorderLayout.CENTER);
		
	}
}
