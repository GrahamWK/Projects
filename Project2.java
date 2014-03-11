import java.util.*;
import java.io.*;
public class Project2{
	public static void main(String[] args){
		if (args.length < 1 || args.length > 7)
			System.out.print("Error: You must supply between 1-7 arguments");
		switch (args.length) {
            case 1:  
            	if(validateArgument((args), 1))	
            		System.out.print("well Done");
            	break;
            case 2:  	
            	if(validateArgument((args), 1) && validateArgument((args), 2))	
            		System.out.print("well Done");
           		break;
            case 3:  
            	if(validateArgument((args), 1) && validateArgument((args), 2) && validateArgument((args), 3))	
            		System.out.print("well Done");	
            	break;
            case 4:
            	if(validateArgument((args), 1) && validateArgument((args), 2) && validateArgument((args), 3) && validateArgument((args), 4))	
            		System.out.print("well Done");
            	break;
            case 5:  	
            	if(validateArgument((args), 1) && validateArgument((args), 2) && validateArgument((args), 3) && validateArgument((args), 5))	
            		System.out.print("well Done");
            	break;
            case 6:  	
            	if(validateArgument((args), 6))	
            		System.out.print("well Done");
            	break;
            case 7:  	
            	break;
        }
		
			
	}
	
	public static boolean validateArgument(String [] part, int option)
	{
		boolean validArg = true;;
		switch (option) {
            case 1:  	
            	String pattern = "[1|2]";
            	if(!(part[0].matches(pattern)))
            	{
            		System.out.print("The first argument must be either 1 or 2");
            		validArg = false;
        		}
            	break;
            case 2:  
            	String pattern2 = "[a-zA-Z0-9]+";
            	if(!(part[1].matches(pattern2)))
            	{
            		System.out.print("The second argument must be one of the following: A R S LP1 LP2");
            		validArg = false;
        		}	
        		else if(!(part[1].equals("A") || part[1].equals("R") || part[1].equals("S") || part[1].equals("LP1") || part[1].equals("LP2")))
        		{
	        		System.out.print("The second argument must be one of the following: A R S LP1 LP2");
            		validArg = false;
        		}
            	break;
            case 3:  
            	String pattern3 = "[0|1|2]";
            	if(!(part[2].matches(pattern3)))
            	{
            		System.out.print("The third argument must be either 0 or 1 or 2");
            		validArg = false;
        		}	
            	break;
            case 4: 
             	String pattern4 = "[0-9]{4}";
            	if(!(part[3].matches(pattern4)))
            	{
            		System.out.print("The fourth argument must be a 4 digit year eg, 2014");
            		validArg = false;
        		}	
            	break;
            case 5:  	
            	String pattern5 = "[0-9]{1,2}/[0-9]{1,2}/[0-9]{4}";
            	if(!(part[3].matches(pattern5)))
            	{
            		System.out.print("The fourth argument must be a date in the format day/month/year eg 13/2/1998");
            		validArg = false;
        		}	
        		else if(!(part[4].matches(pattern5)))
            	{
            		System.out.print("The fifth argument must be a date in the format day/month/year eg 13/2/1998");
            		validArg = false;
        		}	
            	break;
            case 6:  	
            	String pattern6
           	 	break;
            case 7:  	
            	break;
	}
	return validArg;
}
	public static File openFile(String fileName){
		/*
			Returns a File object if the desired file exists in the current directory.
			Prints an error message and terminates if the file does not exist.
		*/
		File openedFile;
		openedFile = new File(fileName);
		if (!openedFile.exists()){
			System.err.println("The given file does not exist");
			System.exit(74);
		}
		return openedFile;

	}


	public static String[] getDataRange(File dataFile, String start, String end){
		/*
			Accepts a File object and a start and end date (DD/MM/YYYY).
			Returns an array of Strings, one for each line found.
		*/
			Scanner dataInFile = new Scanner("");
		try{
			dataInFile = new Scanner(dataFile);
		}
		catch (Exception e) {
			//this should never be executed if the openFile Method works
			System.err.println("The data file does not exist.");
			System.exit(1);
		}
		GregorianCalendar startDate = toCalendar(start);
		GregorianCalendar endDate = toCalendar(end);
		GregorianCalendar checkDate;
		ArrayList<String> inRange = new ArrayList<String>();
		String toCheck = "";
		while (dataInFile.hasNext()){
			toCheck = dataInFile.nextLine();
			checkDate = toCalendar(toCheck.substring(0,10));
			if (startDate.before(checkDate) || startDate.equals(checkDate)) {
				if (endDate.after(checkDate) || endDate.equals(checkDate)) {
					inRange.add(toCheck);
				}
			}
		}
		String[] dataReturn = new String[inRange.size()];
		for (int i = 0; i < dataReturn.length; ++i) {
			dataReturn[i] = inRange.get(i);
		}
		return dataReturn;

	}


public static String[] getDataRange(File dataFile, String year){
		/*
			Accepts a File object and a year.
			Returns an array of Strings, one for each line found.
		*/
		String[] dataReturn = getDataRange(dataFile, ("01/01/" + year), ("31/12/" + year));
		return dataReturn;

	}


	public static GregorianCalendar toCalendar(String toConvert){
		/*
			Converts a String in the form DD/MM/YYYY to a Gregorian Calendar object.
			Returns a default GregorianCalendar in the case of an error.
		*/
		final String validDatePattern = "\\d{1,2}/\\d{1,2}/\\d{4}";
		String[] dayMonthYearStrings;
		int[] dayMonthYearValues = new int[3];
		GregorianCalendar dateOutput = new GregorianCalendar();
		if (toConvert.matches(validDatePattern)) {
			dayMonthYearStrings = toConvert.split("/");
			dayMonthYearValues = arrayToInt(dayMonthYearStrings);
			if (dayMonthYearValues.length == 3) {
				dateOutput = new GregorianCalendar(dayMonthYearValues[2], dayMonthYearValues[1]-1, dayMonthYearValues[0]);	
			}
			
		}
		return dateOutput;
	}


	public static int[] arrayToInt(String[] numberArray){
		/*
			Converts an array of Strings into an array of ints.
			Returns a zero length array if something went wrong.
		*/
		String numeric = "(\\d)+";
		int[] intArray = new int[numberArray.length];
		boolean someError = false;
		//convert all elements of the String array to ints.
		for (int i = 0; i < numberArray.length && !someError; ++i) {
			//Only executed if the String is a number.
			if (numberArray[i].matches(numeric)) {
				intArray[i] = Integer.parseInt(numberArray[i]);
			}
			else someError = true;
		}
		if (someError) intArray = new int[0];
		return intArray;
	}


	public static byte[] arrayToByte(String[] numberArray){
		/*
			Converts an array of Strings into an array of bytes.
			Returns a zero length array if something went wrong.
		*/
		String numeric = "\\d+";
		byte[] byteArray = new byte[numberArray.length];
		boolean someError = false;
		//convert all elements of the String array to bytes.
		for (int i = 0; i < numberArray.length; ++i) {
			//Only executed if the String is a number.
			if (numberArray[i].matches(numeric)) {
				byteArray[i] = Byte.parseByte(numberArray[i]);
			}
			else someError = true;
		}
		if (someError) byteArray = new byte[0];
		return byteArray;
	}


}
