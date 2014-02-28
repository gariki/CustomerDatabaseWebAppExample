package a00928010.lab05.view;


import java.io.IOException;
import java.lang.String;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

public class HTMLManager {

	public static StringBuffer addPageHeader(String title) {
		StringBuffer header = new StringBuffer("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 ");
		header.append( "Transitional//EN\">\n" );
		header.append( "<LINK REL=STYLESHEET HREF=\"style.css\" TYPE=\"text/css\">" );
		header.append( "<HTML>\n" );
		header.append( "<HEAD><TITLE>" + title + "</TITLE>");
		header.append( "<SCRIPT language=\"javascript\" type=\"text/javascript\">" +
						"re = /^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$/" +
						"regexp = /^\\(?(\\d{3})\\)?[\\.\\-\\/ ]?(\\d{3})[\\.\\-\\/ ]?(\\d{4})$/" +

		" function submitIt(myForm){ errMsg = \"\" " +
			"if(myForm.LastName.value == \"\"){"+
				"errMsg = \"Please fill in your LastName\n\""+
			"}"+
			"if(myForm.FirstName.value === \"\"){"+
				"errMsg = errMsg + \"Please fill in your FirstName\n\""+
			"}"+
			"if(myForm.Address.value === \"\"){"+
				"errMsg = errMsg + \"Please fill in your Address\n\""+
			"}"+
			"if (!regexp.test(myForm.phone.value)) {"+
				"errMsg = errMsg + \"Your phone number has been incorrectly formatted\n\""+
			"}"+
			"if (!re.test(myForm.email.value)) {"+
				"errMsg = errMsg + \"Your email address has been incorrectly formatted\n\""+
			"}"+
		
			"if(errMsg != \"\"){"+
				"alert(errMsg)"+
				"myForm.focus()"+
				"return false"+
			"}"+
			"return true"+
		"}"+
	 "</SCRIPT>");
				
		header.append("</HEAD>\n" );
		header.append( "<BODY>" );
		header.append( "<div id=\"header\"> " );
		header.append( "<H1>" + title + "</H1>\n" );
				
		header.append( "</div>" );

		return header;
	}

	public static StringBuffer addDataToOutput(String fName, String lName, String id) {
		StringBuffer outputData = new StringBuffer();

		outputData.append("<div id=\"header\">");
		outputData.append("First Name: " + fName + "<BR>");
		outputData.append("Last Name: " + lName + "<p>");
		//outputData.append("<img src=images/me.jpg width=80/>");
		
		outputData.append("</div>");
		
		return outputData;
	}

			
	public static StringBuffer addPageFooter() {
		StringBuffer footer = new StringBuffer( "<div id=\"footer\"></div>" + "</BODY>\n" + "</HTML>");
		
		return footer;
	}
	
		
	public static void reportProblem(HttpServletResponse response, String message) throws IOException
	{
		response.sendError(HttpServletResponse.SC_BAD_REQUEST, message);
	}
	
	public static StringBuffer addDataToOutput(ResultSet rs) {
		StringBuffer outputData = new StringBuffer();
			
		outputData.append( 
				"<table align=\"left\" border=\"1\"  >	"
				+ "<tbody align=\"center\" style=\"font-family:verdana; color:purple; background-color:yellow\">"
				+ "  <tr><!-- Row 1 -->  <td>MemberID </td><!-- Col 1 -->" 
			    + " <td>FirstName </td>    <td>LastName </td>   <td>Address </td>  <td>City </td>  "
			    + " <td>Code </td>   <td>Country </td>    <td>Phone Number </td>     <td>Email </td> <td colspan=\"2\"> Action </td></tr> "
			    + " <tbody align=\"center\" style=\"font-family:verdana; color:purple; background-color:white\">");
			    
		
				
				//System.out.println(colName + "   " + typeName + "  " + width + "   " + searcheable + "   " + writeable + "   " + nullable);
				/*
				try {
					//outputData.append(rs.getString(1) + "  " + rs.getString(2) + "<br>");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				*/
		try {
			while(rs.next())
			{
				String currentID = rs.getString("MemberID");
				
					
				outputData.append(	
				  "<form action=\"ShowTable.do\" method=\"post\"> "
				  + "<tr><!-- Row 2 -->" +
				  "  <td BGCOLOR=\"#00ff00\"> <input   readonly=\"readonly\" value=" + rs.getString("MemberID") + " name=\"MemberID\" </td><!-- Col 1 --> " +
				  "  <td> <input value='" + rs.getString("FirstName") + "' name=\"FirstName\"  </td><!-- Col 2 --> " +
				  "  <td> <input value='" + rs.getString("LastName") + "' name=\"LastName\"  </td><!-- Col 3 --> " +
				  "  <td> <input value='" + rs.getString(4) + "' name=\"Address\" </td><!-- Col 4 --> " +
				  "  <td> <input value='" + rs.getString("City") + "' name=\"City\" </td><!-- Col 5 --> " +
				  "  <td> <input value='" + rs.getString("Code") + "' name=\"Code\" </td><!-- Col 5 --> " +
				  "  <td> <input value='" + rs.getString("Country") + "' name=\"Country\" </td><!-- Col 5 --> " +
				  "  <td> <input value='" + rs.getString("PhoneNumber") + "' name=\"PhoneNumber\" </td><!-- Col 5 --> " +
				  "  <td> <input value='" + rs.getString("Email") + "' name=\"Email\" </td><!-- Col 5 --> " +
				  "  <td> <input type=\"submit\" name=\"command\" value=\"Update\" > </td><!-- Col 5 --> " +
				  "  <td> <input type=\"submit\" name=\"command\" value=\"Delete\" > </td><!-- Col 6 --> " +
				  " </tr> </form> "); 
									
			}
			
			//Additional row for new data insertion.
			outputData.append(	
					  "<form action=\"ShowTable.do\" method=\"post\" onSubmit=\"return submitIt(this)\"> "
					  + "<tr><!-- New Row -->" +
					  "  <td BGCOLOR=\"#00ff00\"> <input   readonly=\"readonly\" value=AUTOMATIC" + " name=\"MemberID\" </td><!-- Col 1 --> " +
					  "  <td> <input value=\"\" name=\"FirstName\" </td><!-- Col 2 --> " +
					  "  <td> <input value=\"\" name=\"LastName\"  </td><!-- Col 3 --> " +
					  "  <td> <input value=\"\" name=\"Address\" </td><!-- Col 4 --> " +
					  "  <td> <input value=\"\" name=\"City\" </td><!-- Col 5 --> " +
					  "  <td> <input value=\"\" name=\"Code\" </td><!-- Col 5 --> " +
					  "  <td> <input value=\"\" name=\"Country\" </td><!-- Col 5 --> " +
					  "  <td> <input value=\"\" name=\"PhoneNumber\" </td><!-- Col 5 --> " +
					  "  <td> <input value=\"\" name=\"Email\" </td><!-- Col 5 --> " +
					  "  <td> <input type=\"submit\" name=\"command\" value=\"Insert\" > </td><!-- Col 5 --> " +
					  "  <td> <input type=\"submit\" name=\"command\" value=\"Clear\" > </td><!-- Col 6 --> " +
					  " </tr> </form> "); 
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
			
		return outputData;
	}
}
	