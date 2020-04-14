package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

//import businesslogic.Validation;

public class User {
	//Validation valid=new Validation();

	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private String username;
	private String password;

	public User() {

	}

	User(String username, String password) {

		this.username = username;
		this.password = password;
	}

		public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	
	
	
	
	//Admin login
	public boolean adminlogin() throws IOException 
	{

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		final String Adminuser="harish";
		final String adminpass="paruchuri";
		System.out.println("enter user name");
		String aname=br.readLine();
		System.out.println("enter passWord");
		String apass=br.readLine();

		if(aname.equals(Adminuser) && apass.equals(adminpass))

			return true;
		else
			return false;

	}




}
