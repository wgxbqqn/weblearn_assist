/**
 * 
 */
package cn.edu.tsinghua.weblearn.assist.core;

/**
 * Manage user names and passwords and control login and logout
 *
 */
public class AccountManager {

	private String userName = "";
	private String password = "";
	/**
	 * true if the account is logged in
	 */
	private boolean status = false;
	
	/**
	 * login using name and password
	 * @return true if logged in successfully
	 */
	public boolean login(String name, String pass){
		if(name == userName && pass == password){
			status = true;
			return true;
		}
		return false;
	}
	/**
	 * logout and set status to false
	 */
	public boolean logout(){
		if(status){
			status = false;
			return true;
		}
		return false;
	}
	
	/**
	 * save the account to file
	 */
	public void saveAccount(){
		
	}
	

}
