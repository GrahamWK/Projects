import java.util.*;
import java.io.*;
public class Project2{
	public static void main(String[] args) throws IOException
	{
		//Here the arguments entered by the user are analyzed, validated and then the according actions are taken. 
		//The first argument can be 1 2 3 4 or a four digit year, if more than one argument is supplied it can only be 1 or 2
		//1 and 2 are used to specify whether to include bonus, 3 and 4 are used to specify between odd and even.
		//The second argument can be either A R S L1 L2
		//The third can be 0 1 or 2
		//The fourth can be a four digit year, or in the case that 5 arguments are supplied it must be a valid date
		//If 6 or 7 arguments are supplied, they are all number between 1-45
		if (args.length < 1 || args.length > 7)
			System.out.print("Error: You must supply between 1-7 arguments");
		switch (args.length) {
            case 1:
             if(args[0].length() == 4)
             {
              if(validateArgument((args), 7))
              highOrLow(args);
	    	 }
             else if(validateArgument((args), 1))
				Frequency(args);
             break;
            case 2:  	
            	if(validateArgument((args), 1) && validateArgument((args), 2))	
            		Frequency(args);
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
            		rangeAnalysis(args);
            	break;
            case 6:  	
            	if(validateArgument((args), 6))	
            		JackpotAndBonus(0, args);
            	break;
            case 7: 
        	if(validateArgument((args), 6))	
             		JackpotAndBonus(1, args);
        	break;
        }
		
			
	}
	
	
	
	
	public static boolean validateArgument(String [] part, int option)
	{
		//This method validates every argument the user inputs. It does not check dates, it checks to see if the argument is written in the write format.
		boolean validArg = true;;
		switch (option) {
            case 1:  	
            	String pattern = "[1|2]";
            	if(!(part[0].matches(pattern)))
            	{
            		validArg = false;
					if(part.length == 1)
						System.out.print("The first argument must be either 1 2 or a four digit year");
        		}
            	break;
            case 2:  
            	String pattern2 = "[a-zA-Z0-9]+";
            	if(!(part[1].matches(pattern2)))
            	{
            		System.out.print("The second argument must be one of the following: A R S L1 L2");
            		validArg = false;
        		}	
        		else if(!(part[1].equals("A") || part[1].equals("R") || part[1].equals("S") || part[1].equals("L1") || part[1].equals("L2")))
        		{
	        		System.out.print("The second argument must be one of the following: A R S L1 L2");
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
				String pattern7 = "[0-9]{1,2}";
				int num;
				for(int i = 0; i < part.length; i++)
					{
					num = Integer.parseInt(part[i]);
					if(!(part[i].matches(pattern7)))
						validArg = false;
					else if(num < 1 || num > 45)
						validArg = false;
					}
				if(validArg == false)
					System.out.print("You must only input number's between 1 and 45");	
            break;
            case 7:
             String pattern8 = "[0-9]{4}";
             if(!(part[0].matches(pattern8)))
             {
             System.out.print("The first argument must be either 1 2 3 or 4 or a four digit year");
             validArg = false;  	
            
			}
		
		}
		return validArg;
		//validarg returns true if the argument is a valid one.
	}
	
	public static void highOrLow(String[] arguments){
		int year = Integer.parseInt(arguments[0]), max, numSmall = 0, numLarge = 0, freqMainlySmall = 0;
		String startDate = "01/01/" + year, endDate = "31/12/" + year, changeDate = "";
		String[] linesFromFile;
		byte[] lottoResults;
		boolean checkDate = (year == 1992 || year == 1994 || year == 2006);
		if (year < 1993){
			max = 36;
			changeDate = "22/08/1992";
		} else if (year < 1995){
			max = 39;
			changeDate = "24/09/1994";
		} else if (year < 2007){
			max = 42;
			changeDate = "04/11/2006";
		} else max = 45;
		File lottoData = openFile("SampleLottoData.txt");
		linesFromFile = getDataRange(lottoData, startDate, endDate);
		for (String line : linesFromFile){
			lottoResults = arrayToByte(line.split(","));
			numSmall = numLarge = 0;
			if (checkDate) {
				if (toCalendar(line.substring(0,10)).compareTo(toCalendar(changeDate)) < 0) max += 3;
			}
			for (int i = 1; i <= 6; ++i) {
				if (lottoResults[i] <= 10) {
					numSmall++;
				} else if (lottoResults[i] >= max - 10) {
					numLarge++;
				}
			}
			if (numSmall > numLarge) freqMainlySmall++;
			
		}
		System.out.println("For the year " + year + " there was " + freqMainlySmall + " occourence(s) of more small numbers than large numbers");
	}



	public static void JackpotAndBonus (int bonusOrNot, String[] numbersString) throws IOException
	{
			String result = "";
			int [] numbersInteger = new int [6];
			int bonusNumber = 0;
			//This puts the bonus number from the args into a String into an int value.
			if(bonusOrNot == 1)
				bonusNumber = Integer.parseInt(numbersString[6]);
			for(int i = 0; i < numbersInteger.length ; ++i) 
			{
				numbersInteger[i] = Integer.parseInt(numbersString[i]);
			}
			//Sorts the numbers supplied into ascending order.
			for(int i=1; i<numbersInteger.length; i++) 
			{
				boolean is_sorted = true;
	
				for(int j=0; j<numbersInteger.length - i; j++) 
				{
					if(numbersInteger[j] > numbersInteger[j+1]) 
					{
					int temp = numbersInteger[j];
					numbersInteger[j] = numbersInteger[j+1];
					numbersInteger[j+1] = temp;
					is_sorted = false;
					}
				} 
				if(is_sorted) break;
			}
			//Checks if they are unique.
			int dupCounter = 0;
			for(int i = 0; i < numbersInteger.length-1 ; i++)
			{
				if(numbersInteger[i] != numbersInteger[i+1])
					dupCounter ++;
			}
			if(dupCounter != numbersInteger.length-1)
			{
				System.out.print("Most provide unique numbers");
				System.exit(0);
			}
			
			File openedFile;
			openedFile = new File("SampleLottoData.txt");
			int [] winnings = new int [4];
			int [] bonusWinnings = new int [4];
			String aLineFromFile = "";
			int [] lineFromFile = new int [6];
			int bonusNumberFromFile;
			String [] arrayLineFromFile;
			if(!openedFile.exists())
			{
				System.out.print("Cannot find file");
				System.exit(0);
			}
			else
			{
				Scanner in = new Scanner(openedFile);
				while(in.hasNext())
				{
					int counter = 0;
					aLineFromFile = in.nextLine();
					arrayLineFromFile = aLineFromFile.split(",");
					for(int d = 1; d < arrayLineFromFile.length - 2; d++)
						lineFromFile[d-1] = Integer.parseInt(arrayLineFromFile[d]); 
					boolean found;
					for(int i = 0; i < numbersInteger.length; i++)
					{	
						found = false;
						for(int c = 0; c < lineFromFile.length && !found; c++)
						{
							if(numbersInteger[i] == lineFromFile[c])
							{
								counter ++;
								found = true;
							}
						}
					}
					//If the counter reaches 3 it gos into this piece of code where it first checks if a bonus number was supplied.
					if(counter >= 3)
					{
						if(bonusOrNot == 1)
						{
							//Here a bonus number was supplied and then checks if the bonus number supplied = the bonus number from the file.
							//If the bonus number supplied is equivalent to the bonus number fron the file it increaments the value at which the amount of winning numbers there are in the bonusWinnings array.
							//Else it goes into the winnings array.
							bonusNumberFromFile = Integer.parseInt(arrayLineFromFile[7]);
							if(bonusNumberFromFile == bonusNumber)
							{
								switch(counter)
								{
									case 3: bonusWinnings[0]++;		break;
									case 4: bonusWinnings[1]++;		break;
									case 5: bonusWinnings[2]++;		break;
									case 6: bonusWinnings[3]++;		break;
								}
							}
							else
							{
								switch(counter)
								{
									case 3: winnings[0]++;		break;
									case 4: winnings[1]++;		break;
									case 5: winnings[2]++;		break;
									case 6: winnings[3]++;		break;
								}
							}								
						}
						else
						{
							switch(counter)
							{
								case 3: winnings[0]++;		break;
								case 4: winnings[1]++;		break;
								case 5: winnings[2]++;		break;
								case 6: winnings[3]++;		break;
							}
						}
					}
				}
			}
			//All outputing is done here.
			boolean oneWinAtLeast = false;
			for(int i = 0;i<winnings.length && !oneWinAtLeast; i++)
			{
				if(winnings[i] != 0) 
					oneWinAtLeast = true;
				else if(bonusWinnings[i] != 0)
					oneWinAtLeast = true;
			}		
			if(oneWinAtLeast = false)
				result += "No winnings from the numbers supplied";
			else
			{
				result += "The number supplied had:";
				if(bonusOrNot == 0)
				{
					for(int i = 0; i < winnings.length; i++)
					{
						if(winnings[i] != 0)
							result += "\n" + (i+3) + " winning numbers " + winnings[i] + " times.";
					}
				}
				else
					{
					for(int i = 0; i < winnings.length; i++)
					{
						if(winnings[i] != 0)
							result += "\n" + (i+3) + " winning numbers " + winnings[i] + " times but no winning bonus number.";
						
						if(bonusWinnings[i] != 0)
							result += "\n" + (i+3) + " winning numbers " + bonusWinnings[i] + " times and a winning bonus number.";
					}
				}
			}	
			System.out.print(result);
	}
		
	public static void Frequency(String [] args) throws FileNotFoundException{
	//This method reports the frequency of each jackpot and bonus number, in a certain categry supplied by the user. 
	int max = 7, numEven = 0, numOdd = 0;
	if(Integer.parseInt(args[0]) == 2)
		max = 8;
	String criteria = "";
	if(args.length == 1)
		criteria = "A";
	else
		criteria = args[1];
	boolean doCriteria = true, allEven, allOdd;
	int [] numArray = new int[45];
	String [] fileItem;
	File inputFile = new File("SampleLottoData.txt");
	if(inputFile.exists())
	{
		
		Scanner fileReader = new Scanner(inputFile);
		while(fileReader.hasNext())
		{
			//The loop only analyzes the contents of the line if it matches the criteria argument, ie A R S L1 L2
			doCriteria = true;
			fileItem = (fileReader.nextLine()).split(",");
			if((!(criteria.equalsIgnoreCase("A"))) && (!(fileItem[8].equalsIgnoreCase(criteria))))
			{	
				doCriteria = false;
			}
			if(doCriteria)
			{
				for(int i = 1; i < max; i++)
					numArray[(Integer.parseInt(fileItem[i]))-1]++;
			}
			if(criteria.equalsIgnoreCase("A"))
			{
				allEven = true;
				allOdd = true;
				for(int i = 1; i < max; i++)
				{
					if((Integer.parseInt(fileItem[i]))%2 == 0)
						allOdd = false;
					else
						allEven = false;
				}
				if(allEven)
					numEven++;
				else if(allOdd)
					numOdd++;
			}
				
				
		}
		fileReader.close();
		for(int i = 0; i < numArray.length; i++)
			System.out.println("The number " + (i + 1) + " occurred " + numArray[i] + " times.");
		if(criteria.equalsIgnoreCase("A"))
		{
			if(max == 7)
				System.out.println("Number of times all jackpot numbers were even: " + numEven + "\nNumber of times all jackpot numbers were odd: " + numOdd);
			else
				System.out.println("Number of times all jackpot and bonus numbers were even: " + numEven + "\nNumber of times all jackpot and bonus numbers were odd: " + numOdd);		
			
		}
	}
	else
		System.err.println("Error: File does not exist");
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

	public static void rangeAnalysis(String[] arguments){
		/*
			Accepts validated arguments, as they were entered at the command line, and performs
			analysis on the desired range of dates.
		*/
		ArrayList<String> linesFiltered = new ArrayList<String>();
		//the day of the draw to look for, 0-any, 1-wednesday(4), 2-saturday(7).
		byte drawDay = Byte.parseByte(arguments[2]);
		String[] linesFromFile, elementsFromLine;
		byte[] numbers;
		int[] jackpotNumberFrequency = new int[45], bonusNumberFrequency = new int[45];
		boolean includeBonus = false, noDrawsInRange = false;
		String results = "";
		if (arguments[0].matches("2")) includeBonus =true;
		File lottoData = openFile("SampleLottoData.txt");
		//Get an array of lined from the file in the desired range.
		linesFromFile = getDataRange(lottoData, arguments[3], arguments[4]);
		//If the analysis is restricted, filter out other results.
		if (!arguments[1].matches("(a|A)") && drawDay != 0) {
			for (String line : linesFromFile) {
				//check if the results are from the right draw.
				if (line.lastIndexOf(arguments[1]) != -1)
					//checks if they are from the right day.
					if (drawDay == 0) {
						linesFiltered.add(line);
					}
					else{
						if (drawDay == 1) {
							//this spaghetti, basically checks what day of the week a date is.
							if (toCalendar(line.substring(0,10)).get(Calendar.DAY_OF_WEEK) == 4) {
								linesFiltered.add(line);
							}
						}
						if (drawDay == 2) {
							//Same thing, but checks it against saturday.
							if (toCalendar(line.substring(0,10)).get(Calendar.DAY_OF_WEEK) == 7) {
								linesFiltered.add(line);
							}
							
						}

					}
			}
			//check if there are no draws which meet the user's requirements.
			if (linesFiltered.size()==0) noDrawsInRange = true;
			//dump the filtered lines into the original array.
			linesFromFile = new String[linesFiltered.size()];
			for (int i = 0; i < linesFromFile.length; i++) {
				linesFromFile[i] = linesFiltered.get(i);
			}
			
		}
		for (String line : linesFromFile) {
			System.out.println(line);
			elementsFromLine = line.split(",");
			numbers = arrayToByte(elementsFromLine);
			bonusNumberFrequency[numbers[numbers.length-2]-1]++;
			for (int i = 1; i < numbers.length-2; i++) {
				//invalid numbers are represented as zeros
				if (numbers[i] != 0) {
					jackpotNumberFrequency[numbers[i]-1]++;
				}
			}
		}


		results += "For the draws between " + arguments[3] + " and " + arguments[4] + ";\n";

		results += "---Jackpot Number analysis.---\n";
		for (int i = 0; i < jackpotNumberFrequency.length; ++i){
			if (jackpotNumberFrequency[i] != 0)
				results += "The Number " + (i+1) + " appeared " + jackpotNumberFrequency[i] + " times.\n";
		}
		if (includeBonus){
			results += "---Bonus Number Analysis.---\n";
			for (int i = 0; i < bonusNumberFrequency.length; ++i){
				if (bonusNumberFrequency[i] != 0)
					results += "The Number " + (i+1) + " appeared " + bonusNumberFrequency[i] + " times.\n";
			}
		}
		results += "Any other numbers were not drawn.\n";
		//if there's no draws matching the users demands, write an error.
		if (noDrawsInRange)results = "There were no results in the file for this range and draw type\n";
		System.out.print(results);


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
		dataInFile.close();
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
			Returns a value of zero for anything that's non-numeric.
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
		}
		if (someError) byteArray = new byte[0];
		return byteArray;
	}


}
