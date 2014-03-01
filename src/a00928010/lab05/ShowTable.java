package a00928010.lab05;

import java.sql.*;
import java.util.*;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DataValidation.DataValidator;
import a00928010.lab05.data.DatabaseAccessObject;
import a00928010.lab05.view.HTMLManager;

public class ShowTable extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private String studentID;
	private String firstName;
	private String lastName;
	private String outputPageTitle;
	
	private String driverString;
	private String url;
	private String userName;
	private String passWord;
	private String dbname;
	
	DatabaseAccessObject dao;

	public void init(){
		ServletConfig config = getServletConfig();
		firstName = config.getInitParameter("firstName");
		lastName = config.getInitParameter("lastName");
		studentID = config.getInitParameter("studentID");
		outputPageTitle = config.getInitParameter("outputPageTitle");
		
		
		driverString = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		// CHANGE DB CONNECTION STRING and CONNECTION INFO AS NEEDED
		url = "MYSQL URL";
		userName = "USERNAME";
		passWord = "PASSWORD";
		dbname = "A00928010_Members";
		
		
		dao = new DatabaseAccessObject();
		dao.loadDatabaseDriver(driverString);
		try {
			dao.connect(url, userName, passWord);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dao.getConnection();
		dao.initializeStatement();
		
		String dropTableQuery = "Drop table  A00928010_Members";
		dao.executeUpdateQuery(dropTableQuery);
		
		String createTableQuery = "create table A00928010_Members " +
									"(MemberID INTEGER IDENTITY(1,1), FirstName VARCHAR(32), LastName VARCHAR(32), " +
									"Address VARCHAR(60), City VARCHAR(32), Code VARCHAR(10), Country VARCHAR(32), " +
									"PhoneNumber VARCHAR(32), Email VARCHAR(32) )";
									
		dao.executeUpdateQuery(createTableQuery);
		
		//dao.executeUpdateQuery("insert into A00928010_Members (MemberID, FirstName, LastName, Address, City) values(1, 'Mythili', 'Gariki', '550 Cottonwood', 'Coq')");
		//dao.executeUpdateQuery("insert into A00928010_Members (MemberID, FirstName, LastName, Address, City) values(2, 'Renu', 'Paruchuri', '550 Cottonwood', 'Coq')");
		
		dao.executeUpdateQuery("insert into A00928010_Members (FirstName, LastName, Address, City, Code, Country, PhoneNumber, Email) values('Mystle', 'Toe', '1 Main St', 'Coquitlam', 'V3J2S1', 'Canada', '555-555-5555', 'toe@gmail.com' )");
		dao.executeUpdateQuery("insert into A00928010_Members (FirstName, LastName, Address, City, Code, Country, PhoneNumber, Email) values('John', 'Doe', '567 Smith Ave', 'Vancouver', 'V5H 1E6', 'Canada', '123-888-8888', 'doe@gmail.com')");
				
		
	}

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		StringBuffer outputResponse = HTMLManager.addPageHeader(outputPageTitle);
		outputResponse.append(HTMLManager.addDataToOutput(firstName, lastName, studentID));
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		
		String command = request.getParameter("command");
		if(command == null){
			//do nothing; just show the table.
		}
		else if(command.equals("Update"))
		{
			String dataValidationResult = DataValidator.validateInputData(request);
			out.println("<FONT COLOR=red>"+ dataValidationResult + "</FONT>");
			System.out.println("here: " + dataValidationResult);
			
			if(dataValidationResult.length()==0)
			{
				String memberid = request.getParameter("MemberID");
				String firstname = request.getParameter("FirstName");
				String lastname = request.getParameter("LastName");
				String address = request.getParameter("Address");
				String city = request.getParameter("City");
				String code = request.getParameter("Code");
				String country = request.getParameter("Country");
				String phonenumber = request.getParameter("PhoneNumber");
				String email = request.getParameter("Email");
				
				// output for debugging
				System.out.println("received: Update");
				System.out.println("Member id: " + memberid);
				System.out.println(firstname);
				System.out.println(lastname);
				System.out.println(address);
				System.out.println(city);
				
				
				
				String sqlUpdateQuery = "UPDATE A00928010_Members " +
						 "SET FirstName='"+firstname + "', LastName='" + lastname + "', Address='" + address + "', City='" + city + 
						  "', Code='"+ code + "', Country='" + country + "', PhoneNumber='" + phonenumber + "', Email='" + email +
						 "' WHERE MemberID=" + memberid;
				
				System.out.println(sqlUpdateQuery);
				
				dao.executeUpdateQuery(sqlUpdateQuery);
			 }
			 else{
				// Do nothing and refresh;
			 }
				
		}
		else if(command.equals("Delete"))
		{
			// output for debugging
			System.out.println("received: Delete");
						
			String memberid = request.getParameter("MemberID");
			
			String sqlUpdateQuery = "DELETE FROM A00928010_Members " +
					 " WHERE MemberID=" + memberid;
			
			System.out.println(sqlUpdateQuery);
			dao.executeUpdateQuery(sqlUpdateQuery);
		}
		else if(command.equals("Insert"))
		{
			System.out.println("received: Insert");
			
			String dataValidationResult = DataValidator.validateInputData(request);
			out.println("<FONT COLOR=red>"+ dataValidationResult + "</FONT>");
			System.out.println("here: " + dataValidationResult);
			
			
			if(dataValidationResult.length()==0)
			{
				String firstname = request.getParameter("FirstName");
				String lastname = request.getParameter("LastName");
				String address = request.getParameter("Address");
				String city = request.getParameter("City");
				String code = request.getParameter("Code");
				String country = request.getParameter("Country");
				String phonenumber = request.getParameter("PhoneNumber");
				String email = request.getParameter("Email");
				
			
				String sqlInsertQuery = "INSERT into A00928010_Members (FirstName, LastName, Address, City, Code, Country, PhoneNumber, Email) " +
									"values('" + firstname + "','" + lastname + "','" + address + "','" + city + "','" + code + "','" + country + "','" + phonenumber + "','" + email  + "')";
			
				System.out.println(sqlInsertQuery);
				dao.executeUpdateQuery(sqlInsertQuery);							
			}		
			else{
				return;
			}
		}
		else if(command.equals("Clear"))
		{
			//Do nothing and refresh.
		}
				
		ResultSet rs = null;
	
		//out.println("here <br>");
		//out.println("dbname is	:"+ dbname +"<br>");
				
		String query = "SELECT * FROM A00928010_Members";
		rs = dao.generateResultSet(query);
		
			
		outputResponse.append(HTMLManager.addDataToOutput(rs));

		outputResponse.append(HTMLManager.addPageFooter()); 
		out.println(outputResponse.toString());
		
	}

	private void getParametersFromWebForm(HttpServletRequest request)
	{
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);

	}

}
