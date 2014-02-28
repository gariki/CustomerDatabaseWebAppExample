package DataValidation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

public class DataValidator {
	
	public static boolean isValidInput(String input, String pattern)
	{
		Pattern patt = Pattern.compile(pattern);
		Matcher match = patt.matcher(input);
		return match.matches();
	}
	
	
	public static String removeBlankSpaces(String str)
	{
		return str.replaceAll("\\s+", "");
	}
	
	public static String removeNumericChars(String str)
	{
		return str.replaceAll("[0-9]+","");
	}
	
	public static String removeNonNumericChars(String str)
	{
		return str.replaceAll("[^0-9]+","");
	}
	
	public static String validateInputData(HttpServletRequest request)
	{
		String nonBlankPattern = ".+";
		String alphaNumericPattern = "[a-zA-Z0-9]+"; 
		String alphaPattern ="[a-zA-Z]+";
		String phonePattern = "\\d{10}";
		String emailPattern = "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$";
		//String outString = "Errors:";
		String outString = "";
		
		//Check: name is non blank alpha numeric
		String name = request.getParameter("FirstName");
		Boolean result = isValidInput(removeBlankSpaces(name), alphaNumericPattern);
		if(result == false) outString = "FirstName has to be a non empty alpha-numeric string <br>";
		
		name = request.getParameter("LastName");
		result = isValidInput(removeBlankSpaces(name), alphaNumericPattern);
		if(result == false) outString = "LastName has to be a non empty alpha-numeric string <br>";
				
		//Check: MemberID is not blank
		String memberID = request.getParameter("MemberID");
		if(removeBlankSpaces(memberID).length() == 0) outString += "Member ID cannot be empty <br>";
		
		//Check: MemberID is not blank
		String address = request.getParameter("Address");
		if(removeBlankSpaces(address).length() == 0) outString += "Address cannot be empty <br>";
		
		
		//Check: Phone number is atleast 10 digit numeric
		String phoneNumber = request.getParameter("PhoneNumber");
		result = isValidInput(removeNonNumericChars(phoneNumber), phonePattern);
		if(result == false) outString += "Phone number has to be a 10 digit number <br>";
		
		//Check: Phone number is atleast 10 digit numeric
		String emailAddress = request.getParameter("Email");
		result = isValidInput(emailAddress, emailPattern);
		if(result == false) outString += "Email address should be in xxx@xxx.xxx <br>";
				
		
		
		return outString;
	}

		
	
}
