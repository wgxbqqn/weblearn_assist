package cn.edu.tsinghua.weblearn.assist.tools;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.SortedMap;

public class CharsetDetector {

	public static void printAvailCharsets() {
		SortedMap<String, Charset> availCharsets = Charset.availableCharsets();
		for (String charsetName : availCharsets.keySet()) {
			System.out.println(charsetName + ", ");
		}
		System.out.print("\n");
	}

	public static void printDefaultCharset() {
		System.out.println("Default charset: "
				+ Charset.defaultCharset().toString());
	}

	/**
	 * try to encode the string for all available charsets
	 * 
	 * @param s
	 *            the string to be encoded
	 */
	public static void testAllCharsets(String s) {
		printDefaultCharset();
		System.out.println("Trying to encode the string for all available charsets");
		System.out.println("尝试使用全部可用的字符集进行编码");
		
		SortedMap<String, Charset> availCharsets = Charset.availableCharsets();
		for (String charsetName : availCharsets.keySet()) {
			try{
				//String str = new String(s.getBytes(Charset.forName(charsetName)));
				String str = new String(s.getBytes(charsetName));
				System.out.println(charsetName + ": "
					+ str);
			}catch(UnsupportedEncodingException e){
				System.err.println("Unsupported encoding: " + charsetName);
			}catch(UnsupportedOperationException e){
				System.err.println("Unsupported encoding: " + charsetName);
			}
		}
		System.out.print("\n");
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Default charset: "
				+ Charset.defaultCharset().toString());
		System.out.println("Available charsets: ");
		SortedMap<String, Charset> availCharsets = Charset.availableCharsets();

		for (String charsetName : availCharsets.keySet()) {
			System.out.println(charsetName + ", ");
		}
		System.out.print("\n");
	}

}
