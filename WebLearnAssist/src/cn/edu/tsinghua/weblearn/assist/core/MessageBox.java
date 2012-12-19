/**
 * 
 */
package cn.edu.tsinghua.weblearn.assist.core;

import java.util.List;
import java.util.LinkedList;
/**
 * A container for managing messages
 * The messages are ranked by priority
 * The high-priority messages are located in low index
 */
public class MessageBox {
	private List<Message> list;
	
	public MessageBox(){
		list = new LinkedList<Message>();
	}
	
	/**
	 * get number of messages
	 */
	public int getCount(){
		return list.size();
	}
	
	/**
	 * add a message to the message box, ranked by priority
	 * @param msg the message to add
	 */
	public void add(Message msg){
		if(list.size() <= 0){
			list.add(msg);
		}
		else{
			for(int i = 0; i < list.size(); i ++){
				if(msg.getPriority() >= list.get(i).getPriority()){
					list.add(i, msg);
					break;
				}
			}
		}
	}
	
	/**
	 * remove a message at the position index
	 * @param index
	 */
	public void remove(int index){
		list.remove(index);
	}
	
	/**
	 * get a List object 
	 * @return
	 */
	public List<Message> getList(){
		return list;
	}
}
