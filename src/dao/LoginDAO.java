package dao;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.UserLogin;
import utility.ConnectionManager;

public class LoginDAO {

	public boolean Validate(UserLogin userlogin) throws Exception {
		// TODO Auto-generated method stub
		
		String username=userlogin.getUsername();
		String password=userlogin.getPassword();
		
		ConnectionManager con=new ConnectionManager();
		Statement st=con.getconnection().createStatement();
		int flag=0;
		ResultSet rs=st.executeQuery("select * from RUSER");
		
		while(rs.next())
		{
			if(username.equals(rs.getString("USERNAME")) && password.equals(rs.getString("PASSWORD")))
			{
				con.getconnection().close();
				flag=1;
				break;
			}
			else
			{
				con.getconnection().close();
				flag=0;
			}
		}
		if(flag==1)
			return true;
		else
			return false;
		
		
	}

}
