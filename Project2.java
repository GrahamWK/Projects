import java.io.*;
import java.util.*;
public class Project2{
	public static void main(String[] args){
		
		
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
