package cn.edu.tsinghua.weblearn.assist.core;

import java.util.Date;

public class Message {
	protected Date date;
	protected String content;
	/**
	 * higher values indicate higher priority
	 */
	protected int priority;
	/**
	 * true if the message has been read
	 */
	protected boolean status = false;
	
	public Message(Date dt, String cont, int prior){
		date = dt;
		content = cont;
		prior = priority;
	}
	
	public void markAsRead(){
		status = true;
	}
	
	public void markAsUnread(){
		status = false;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the priority
	 */
	public int getPriority() {
		return priority;
	}

	/**
	 * @param priority the priority to set
	 */
	public void setPriority(int priority) {
		this.priority = priority;
	}
}
