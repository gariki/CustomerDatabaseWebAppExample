package a00928010.lab05.data;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import a00928010.lab05.view.HTMLManager;

public class DatabaseAccessObject {

	public Connection con = null;
	public Statement stmt = null;
	public ResultSet rs = null;
	
	public DatabaseAccessObject()
	{
		super();
		//nothing in constructor yet
	}
	
	public void connect(String url, String userName, String passWord) throws ClassNotFoundException
	{
	    
		try {
			con = DriverManager.getConnection(url, userName, passWord);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Connection getConnection()
	{
		return con;
	}
	
	public void closeConnection() 
	{
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void closeResultSet()
	{
		try {
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void closeStatement()
	{
		try {
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int executeUpdateQuery(String updateQuery)
	{
		int x=0;
		try {
			x = stmt.executeUpdate(updateQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return x;
	}
	
	public ResultSet generateResultSet(String selectQuery) 
	{
		
		try {
			rs = stmt.executeQuery(selectQuery);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	
	public DatabaseMetaData getDatabaseMetadata(Connection conn) 
	{
		DatabaseMetaData dbmd = null;
		try {
			dbmd = con.getMetaData();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dbmd;
	}
	
	public ResultSet getResultSet()
	{
		return rs;
	}
	
	public static ResultSetMetaData getResultSetMetaData(ResultSet rs)
	{
		ResultSetMetaData rsmd = null;
		try {
			rsmd = rs.getMetaData();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rsmd;
	}
	
	public Statement getStatement()
	{
		return stmt;
	}
	
	public void initializeStatement(int resultSetType, int resultSetConcurrency)
	{
		// TBD
	}
	
	public Statement initializeStatement()
	{
		try {
			stmt = con.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stmt;
	}
	
	public void loadDatabaseDriver(String className)
	{
		try {
			Class.forName(className);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
