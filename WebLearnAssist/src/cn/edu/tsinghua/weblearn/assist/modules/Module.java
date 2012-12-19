/**
 * 
 */
package cn.edu.tsinghua.weblearn.assist.modules;

import org.w3c.dom.Document;

/**
 * Modules of the web learn client Anouncement, course files, assignments must
 * implement this interface
 */
public interface Module {
	/**
	 * get the name of this module
	 */
	public String getModuleName();

	/**
	 * set the name of this module
	 */
	public void setModuleName(String name);

	/**
	 * save the output document to a xml file
	 * @param fileName the name of the xml file to save to
	 * 
	 * @return true if the operation is successful
	 */
	public boolean saveTo(String fileName);
	
	/**
	 * load the output document from a xml file
	 * @param fileName the name of the xml file to load from
	 * @return true if the operation is successful
	 */
	public boolean loadFrom(String fileName);

	/**
	 * get the output document
	 * 
	 * @return a document object if the input document is valid, null if the
	 *         input document is invalid
	 */
	public Document getOutputDocument();
	
	/**
	 * connect to the Internet and sychronize the content
	 * @return true if the operation is successful
	 */
	public boolean sync();
}
