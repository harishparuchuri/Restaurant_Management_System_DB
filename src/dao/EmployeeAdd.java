package dao;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.User;
import utility.ConnectionManager;



public class EmployeeAdd {

	User user=new User();
	BufferedReader br=new BufferedReader(new InputStreamReader(System.in));

	
	public int signUp(String uname, String pass) {
		
		
		String insert = "INSERT INTO RUSER(USERNAME,PASSWORD)VALUES(?,?)";

		int result = 0;
		try
		{
			Connection cn = ConnectionManager.getconnection();
			
			
			PreparedStatement ps = cn.prepareStatement(insert);
			ps.setString(1,uname);
			ps.setString(2,pass);
			System.out.println("Employee Added Sucessfully");
			
			// Step 3: Execute the query or update query
			result = ps.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}
		return result;
	}
	
	
	
	

	

	
}

