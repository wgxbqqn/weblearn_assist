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
	public String getModuleName();
	public void setModuleName(String name);
	public boolean saveTo(String filepath,String fileName);
	public boolean loadFrom(String filepath, String fileName);
	public Document getOutputDocument();
	public boolean sync();
}
