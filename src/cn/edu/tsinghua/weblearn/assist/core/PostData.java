package cn.edu.tsinghua.weblearn.assist.core;

import java.util.HashMap;
/**
 * manage value and key pairs for HtmlFetcher
 * @see HtmlFetcher
 */
public class PostData {
	private HashMap<String, String> dict;
	
	public PostData(){
		dict = new HashMap<String, String>();
	}
	/**
	 * add a value pair with the given name
	 */
	public void addEntry(String name, String value){
		dict.put(name, value);
	}
	/**
	 * remove all the contents
	 */
	public void clear(){
		dict.clear();
	}
	/**
	 * remove an entry with the specified name
	 */
	public void removeEntry(String name){
		dict.remove(name);
	}
	/**
	 * get the HashMap object for iterating
	 */
	public HashMap<String, String> getHashMap(){
		return dict;
	}
}
