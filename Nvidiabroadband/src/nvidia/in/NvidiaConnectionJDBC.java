package nvidia.in;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class NvidiaConnectionJDBC {

	Connection c;
	Statement s;
	
	public NvidiaConnectionJDBC() 
	{
		try 
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			c = DriverManager.getConnection("jdbc:mysql://localhost:3306/nvidiabroadband","root","root");
			s = c.createStatement();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
}