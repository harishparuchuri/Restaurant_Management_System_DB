package utility;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class ConnectionManager {
	
	public static Connection getconnection() throws Exception {
		

		
	
				

				 Class.forName("oracle.jdbc.driver.OracleDriver");
				 Connection con;
				 con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1522:orcl","system","harish");
				 
				 return con;
				 


		
	}
	
	public static Properties loadPropertiesFile() {
		Properties prop = new Properties();
		try {
		InputStream in = ConnectionManager.class.getClassLoader().getResourceAsStream("jdbc.properties");
		in.close();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
		return prop;
	}

	

	 
}

