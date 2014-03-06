public class Project2{
	public static void main(String[] args){
		
		
	}
	public static int toDays(String toConvert){
		/*
			Converts a String in the form DD/MM/YYYY to the number of days
			since January 1st 1970.
			Returns intMin in the case of an error.
		*/
		final String validDatePattern = "\\d{1,2}/\\d{1,2}/\\d{4}";
		String[] dayMonthYearStrings;
		int[] dayMonthYearValues = new int[3];
		int numberOfDays;
		GregorianCalendar dateToConvert;
		if (toConvert.matches(validDatePattern)) {
			dayMonthYearStrings = toConvert.split("/");
			dayMonthYearValues = arrayToInt(dayMonthYearStrings);
			dateToConvert = new GregorianCalendar(dayMonthYearValues[2], dayMonthYearValues[1]-1, dayMonthYearValues[0]);
			numberOfDays = toDays(dateToConvert.getTimeInMillis());
		}
		else numberOfDays = -2147483648;
		return numberOfDays;
	}
	public static int toDays(Date toConvert){
		/*
			Converts a Date object into days since January 1970.
			Returns an int value.
		*/
		int numberOfDays; long milliseconds;
		milliseconds = toConvert.getTime();
		numberOfDays = toDays(milliseconds);
		return numberOfDays;
	}
	public static int toDays(long toConvert){
		/*
			Converts time in milliseconds into days since January 1st 1970.
			Returns an int value.
		*/
		int numberOfDays;
		numberOfDays = (int) (toConvert / 86400000L);
		return numberOfDays;
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
