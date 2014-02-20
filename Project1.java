import javax.swing.JOptionPane;
import java.util.Scanner;
public class Project1{
	/**
		A simple program to demonstrate string manipulation.
		Effort was made to have only one exit point in the code, returning an exit state would probably be a better idea though.
		I'm also assuming that the main method is meant to deal with anything but selecting the method to execute.
		Spelling in this code is complaint with American English.
	**/
	//This boolean determines if the program is to run only in the console window. Or if it should present dialog windows to the user.
	public static boolean headless = false;
	//This Scanner object is declared globally because two methods require it.
	public static Scanner consoleInput = new Scanner(System.in);
	public static void main(String[] args) {
		byte menuChoice = -1;
		if (args.length > 0) {
			if (args[0].matches("-h") || args[0].matches("--help")) {
				menuChoice = 0;
				System.out.println("This program can analyze sentences or words based on their content.\nUse --text-only or -t to run the program in text only mode.");
			}
			if (args[0].matches("-t") || args[0].matches("--text-only")){
				headless = true;
			}
		}
		while (menuChoice != 0){
			menuChoice = getMenuOption();
			/*
			The switch calls the desired method based on the user's selection and resets
			the menuChoice variable to the default of -1 if a feature (method) was used.
			The option 0 results in a break from the switch construct in order to keep menuChoice
			at 0 and end the while loop.
			*/
			switch (menuChoice){
				case 1: vowelContent();
				break;
				case 2: consonantCount();
				break;
				case 3: stringContent();
				break;
				case 4: areAnagrams();
				break;
				case 5: isPalindrome();
				break;
				case 6: longestShortestWord();
				break;
				case 0: break;
				default: menuChoice = -1;
			}
			System.out.println(menuChoice);
		}


	}
	public static byte getMenuOption(){
		byte chosenOption;
		String userChoice = "", menuOptions = "Please select one of the following options by entering the option number:";
		menuOptions += "\n 1) Count the number of vowels in a word or phrase.";
		menuOptions += "\n 2) Count the number of consonants in a word or phrase.";
		menuOptions += "\n 3) Analise the content of a word or phrase.";
		menuOptions += "\n 4) Determine if two words are anagrams of each other.";
		menuOptions += "\n 5) Determine if a word or sentence is a palindrome.;";
		menuOptions += "\n 6) Report longest and shortest words in a sentence.";
		menuOptions += "\n 0) Exit.";
		/*
		gets an option from the user and trims off leading and trailing whitespace
		a better solution would be needed if the menu had more than ten options.
		*/
		if (!headless) userChoice = JOptionPane.showInputDialog(null, menuOptions, "Project 1 Menu", 3);
		if (headless){
			System.out.println(menuOptions);
			userChoice = consoleInput.nextLine();}
		//If input isn't null, it checks if it's in the range 0-6. If it isn't, the menu will repeat.
		if (userChoice != null){
			 userChoice = userChoice.trim();
			if (userChoice.matches("[0-6]"))
				chosenOption = Byte.parseByte(userChoice);
			else{
				JOptionPane.showMessageDialog(null, "You must enter a number between 0-6");
				chosenOption = -1;	
			}
		}
		else chosenOption = 0;
		return chosenOption;
	}
	public static void vowelContent(){
		//the string of vowels must be in alphabetical order.
		String vowels = "aeiou", requestMessage = "Enter a word or sentence consisting of spaces, letters and numbers only.", foundVowels = "";
		String windowTitle = "Vowel Analyzer",userInput, resultsMessage = "";
		int [] vowelCount = new int[vowels.length()];
		//This variable is used to store the position in vowelCount which is to be edited to improve code clarity.
		byte arrayPosition;
		/*
		The array contains toggles for each of the following states.
		0 - no vowels. 1 - all of the vowels. 2 - all vowels, in alphabetical order
		3 - all vowels in reverse alphabetical order.
		assuming all are true to begin with, so the values can be used as entry conditions.
		*/
		boolean [] satisfiedCondition = {true, true, true, true};
		userInput = getUserInput(requestMessage, windowTitle, "[a-zA-Z0-9\\s]+");
		if (userInput.length() == 0) {
			announceError("Invalid input.");
		}
		//this loop walks the String, userInput, and checks if there are no vowels and sets satisfiedCondition[0] to false if any vowels are found, breaking out of the loop.
		for (int i = 0; i < userInput.length() && satisfiedCondition[0]; i++)
			if (vowels.indexOf(userInput.substring(i, i+1)) >= 0)
				satisfiedCondition[0] = false;
		//If there's no vowels found by the end of the string, set all other conditions to false.
		if (satisfiedCondition[0]){
			satisfiedCondition[1] = false;
			satisfiedCondition[2] = false;
			satisfiedCondition[3] = false;
		}
		//only executes when a vowel has been found.
		if (!satisfiedCondition[0]) {
			//goes through the string, increments the count for any vowels that are found
			for (int i = 0; i < userInput.length(); i++) {
				if (vowels.indexOf(userInput.substring(i, i+1)) >= 0){
					arrayPosition = (byte) vowels.indexOf(userInput.substring(i, i+1));
					vowelCount[arrayPosition]++;
					//make a string containing all of the vowels found, in the order they were found.
					foundVowels += vowels.substring(arrayPosition);
				}
			}
			//checks if any of the counts for each vowel are zero and sets every boolean for a string with all of the vowels to false.
			for (int amount: vowelCount) {
				if (amount == 0){
					satisfiedCondition[1] = false;
					satisfiedCondition[2] = false;
					satisfiedCondition[3] = false;
				}
			}
			//Check the list of all vowels found, to see if they are in order, and in which order.
			for (int i = 0; i < foundVowels.length() && (satisfiedCondition[2]||satisfiedCondition[3]); i++) {
				//check if the first vowel found is the first or last in the list of vowels and changing conditions accordingly.
				if (i == 0) {
					if (vowels.indexOf(foundVowels.substring(0,1)) != 0)
						satisfiedCondition[2] = false;
					if (vowels.indexOf(foundVowels.substring(0,1)) != vowels.length()-1)
						satisfiedCondition[3] = false;
				}
				//check that every other found vowel is in ascending or descending alphabetical order.
				else {
					//If two consecutive vowel occurrences aren't identical or bordering each other in the string of vowels set the relevant boolean to false
					if ((vowels.indexOf(foundVowels.substring(i,i+1)) - vowels.indexOf(foundVowels.substring(i-1,i))) < 0)
						satisfiedCondition[2] = false;
					if ((vowels.indexOf(foundVowels.substring(i,i+1)) - vowels.indexOf(foundVowels.substring(i-1,i))) > 0)
						satisfiedCondition[3] = false;
				}
			}
		}
			//Builds the results message with the relevant results for each condition.
			if (satisfiedCondition[0]) {
				resultsMessage += "The input contained no vowels";
			}
			else{
				if (satisfiedCondition[1]) {
					resultsMessage += "The input contained all vowels";
					if (satisfiedCondition[2]) {
						resultsMessage += "\\nThe input contained all vowels in alphabetical order. ";
					}
					else if (satisfiedCondition[3]) {
						resultsMessage += "\\nThe input contained all vowels in reverse alphabetical order. ";
					}
				}
			
				//If a vowel was found at all, the amount of times it was found is added to the message.
				resultsMessage += "The vowel";
				for (int i = 0; i < vowelCount.length; i++) {
					if (vowelCount[i] == 1)
						resultsMessage += ", \'" + vowels.substring(i,i+1) + "\'" + " occurs " + vowelCount[i] + " time";
					if (vowelCount[i] > 1)
						resultsMessage += ", \'" + vowels.substring(i,i+1) + "\'" + " occurs " + vowelCount[i] + " times";
					if (i == vowelCount.length-1) resultsMessage += ".";
				}
			}
			announceResults(resultsMessage);






	}
	public static void consonantCount(){
		//the string of consonants must be in alphabetical order.
		String consonants = "bcdfghjklmnpqrstvwxyz", requestMessage = "Enter a word or sentence consisting of spaces, letters and numbers only.", foundConsonants = "";
		String windowTitle = "Consonant Analyzer",userInput, resultsMessage = "";
		int [] consonantCount = new int[consonants.length()];
		//This variable is used to store the position in consonantCount which is to be edited to improve code clarity.
		byte arrayPosition;
		/*
		The array contains toggles for each of the following states.
		0 - no consonants. 1 - all of the consonants. 2 - all consonants, in alphabetical order
		3 - all consonants in reverse alphabetical order.
		assuming all are true to begin with, so the values can be used as entry conditions.
		*/
		boolean [] satisfiedCondition = {true, true, true, true};
		userInput = getUserInput(requestMessage, windowTitle, "[a-zA-Z0-9\\s]+");
		if (userInput.length() == 0) {
			announceError("Invalid input.");
		}
		//this loop walks the String, userInput, and checks if there are no consonants and sets satisfiedCondition[0] to false if any consonants are found, breaking out of the loop.
		for (int i = 0; i < userInput.length() && satisfiedCondition[0]; i++)
			if (consonants.indexOf(userInput.substring(i, i+1)) >= 0)
				satisfiedCondition[0] = false;
		//If there's no consonants found by the end of the string, set all other conditions to false.
		if (satisfiedCondition[0]){
			satisfiedCondition[1] = false;
			satisfiedCondition[2] = false;
			satisfiedCondition[3] = false;
		}
		//only executes when a consonant has been found.
		if (!satisfiedCondition[0]) {
			//goes through the string, increments the count for any consonants that are found
			for (int i = 0; i < userInput.length(); i++) {
				if (consonants.indexOf(userInput.substring(i, i+1)) >= 0){
					arrayPosition = (byte) consonants.indexOf(userInput.substring(i, i+1));
					consonantCount[arrayPosition]++;
					//make a string containing all of the consonants found, in the order they were found.
					foundConsonants += consonants.substring(arrayPosition);
				}
			}
			//checks if any of the counts for each consonant are zero and sets every boolean for a string with all of the consonants to false.
			for (int amount: consonantCount) {
				if (amount == 0){
					satisfiedCondition[1] = false;
					satisfiedCondition[2] = false;
					satisfiedCondition[3] = false;
				}
			}
			//Check the list of all consonants found, to see if they are in order, and in which order.
			for (int i = 0; i < foundConsonants.length() && (satisfiedCondition[2]||satisfiedCondition[3]); i++) {
				//check if the first consonant found is the first or last in the list of consonants and changing conditions accordingly.
				if (i == 0) {
					if (consonants.indexOf(foundConsonants.substring(0,1)) != 0)
						satisfiedCondition[2] = false;
					if (consonants.indexOf(foundConsonants.substring(0,1)) != consonants.length()-1)
						satisfiedCondition[3] = false;
				}
				//check that every other found consonant is in ascending or descending alphabetical order.
				else {
					//If two consecutive consonant occurrences aren't identical or bordering each other in the string of consonants set the relevant boolean to false
					if ((consonants.indexOf(foundConsonants.substring(i,i+1)) - consonants.indexOf(foundConsonants.substring(i-1,i))) < 0)
						satisfiedCondition[2] = false;
					if ((consonants.indexOf(foundConsonants.substring(i,i+1)) - consonants.indexOf(foundConsonants.substring(i-1,i))) > 0)
						satisfiedCondition[3] = false;
				}
			}
		}
			//Builds the results message with the relevant results for each condition.
			if (satisfiedCondition[0]) {
				resultsMessage += "The input contained no consonants";
			}
			else{
				if (satisfiedCondition[1]) {
					resultsMessage += "The input contained all consonants";
					if (satisfiedCondition[2]) {
						resultsMessage += "\\nThe input contained all consonants in alphabetical order. ";
					}
					else if (satisfiedCondition[3]) {
						resultsMessage += "\\nThe input contained all consonants in reverse alphabetical order. ";
					}
				}
			
				//If a consonant was found at all, the amount of times it was found is added to the message.
				resultsMessage += "The consonant";
				for (int i = 0; i < consonantCount.length; i++) {
					if (consonantCount[i] == 1)
						resultsMessage += ", \'" + consonants.substring(i,i+1) + "\'" + " occurs " + consonantCount[i] + " time";
					if (consonantCount[i] > 1)
						resultsMessage += ", \'" + consonants.substring(i,i+1) + "\'" + " occurs " + consonantCount[i] + " times";
					if (i == consonantCount.length-1) resultsMessage += ".";
				}
			}
			announceResults(resultsMessage);






	}
	public static void stringContent(){
		String requestMessage = "Enter a word or sentence consisting of spaces, letters and numbers only.", windowTitle = "String analyzer";
		String userInput = getUserInput(requestMessage, windowTitle, "[a-zA-Z0-9\\s]+");
		String words[];
		String resultsMessage = "The word/phrase:\t " + userInput + "\n", line1Keys = "qwertyuiop", line2Keys = "asdfghjkl", line3Keys = "zxcvbnm";
		String vowels = "aeiou";
		int [] charCount = new int[26];
		if(userInput != "")
		{
			String trimmedUserInput = userInput.replaceAll("\\s+","");
			trimmedUserInput = trimmedUserInput.toLowerCase();
			if(userInput.length() == 0)
				announceError("Invalid input.");
			// Getting the individual characters and putting them into an array to keep count of them by going through the string.
			for(int i = 0; i < trimmedUserInput.length(); i++)
			{
				char c = trimmedUserInput.charAt(i);
				charCount[c-97] += 1;
			}
			resultsMessage += "The frequency of each characters in the English alphabet present in the word/phrase are:";
			// Getting the output by going throught the count array and ouputing the ones that dont have a value of 0 
			boolean pangram = true;//this boolean will be used later to check if string is a pangram by going to false if one of the values in the array is 0.
			for(int i = 0;i < charCount.length; i++)
			{
				int ch = i + 65;
				char c = (char) ch;
				if(charCount[i] != 0)
					resultsMessage += "\n"+ c + ":" + charCount[i];
				if(charCount[i] == 0)
					pangram = false;			
			}
			words = userInput.split(" ");
			resultsMessage+= "\nNumber of words in the supplied string are:\t" + words.length;
			if(pangram)
				resultsMessage+= "\nSupplied string is a pangram i.e. contains all letters of the alphabet.";
			else
				resultsMessage+= "\nSupplied string is not a pangram i.e. one or more letters of the alphabet is missing.";
			//this boolean array keeps an eye on which lines have been pressed
			boolean [] keys = {false,false,false};
			//this for loop goes through the charCount array to check to see if there is a value in that point and terminates if all the values are true
			for(int i = 0; i < charCount.length && (!keys[0]||!keys[1]||!keys[2]); i++)
			{
				int ch = i + 97;
				char c = (char) ch;
				if(charCount[i] != 0)
				{
					if(line1Keys.indexOf(c)!= -1)
						keys[0] = true;
					else if(line2Keys.indexOf(c) != -1)
						keys[1] = true;
					else if(line3Keys.indexOf(c) != -1)
						keys[2] = true;
				}
			}
			//Goes through the keys array and outputs wether or not the line of keys were used.
			for(int i = 0; i < keys.length; i++)
			{
				if(keys[i])
					resultsMessage+="\nLine " + (i+1) + " on a QWERTY keyboard used.";
			}
			boolean isAlternating = true;
			int count = 0;
			//this if statment checks to see if the first character is a vowel and if it isnt sets the count integer as the next character ie sets it to 1 rather than 0.
			if(vowels.indexOf(trimmedUserInput.charAt(0)) == -1)
				count = 1;
			for( ;count < trimmedUserInput.length() && isAlternating; )
			{
				if(vowels.indexOf(trimmedUserInput.charAt(count)) == -1)
					isAlternating = false;
				else
					count += 2;
			}
			if(isAlternating)
				resultsMessage += "\nWord/phrase contains alternating vowels.";
			else
				resultsMessage += "\nWord/phrase doesn't contain alternating vowels.";
			
			announceResults(resultsMessage);
		}
		else
			announceError("Invalid Input");
	}
	public static void areAnagrams(){

	}
	public static void isPalindrome(){
		String requestMessage = "Enter a word or sentence consisting of spaces, numbers and alphabetic characters only.";	//This is initiation for the getinput method
		String windowTitle = "Palindrome Analyzer", userInput, result = "";													//This will be the window title for the input box
		String trimmedUserInput = "";																						
		boolean letterLevel = false, wordLevel = false;																		//The boolean variable which will show what type of palindrom the phrase/word may be
		userInput = getUserInput(requestMessage, windowTitle, ".*");										//Here we call the getUserInput method and put in our message and title and a pattern
		char aChar;
		int i, j, index;																			
		for(index = 0; index < userInput.length(); index++)											//If it isn't then it isn't included in the trimmedUserInput
		{																							//This section checks whether each character in the phrase is an alphabet or white space.
			aChar = userInput.charAt(index);
			if (Character.isLetterOrDigit(aChar) || Character.isWhitespace(aChar))
				trimmedUserInput += aChar;
		}
		if (trimmedUserInput.equals(""))														//If the word was all non alphabets it will return empty 
			result = "Input must consist of a word or phrase";									//Thus the appropriate error message is displayed
		else
		{
			String userInputArray [] = trimmedUserInput.split(" ");								//The user input is split into an array 
			if(userInputArray.length > 1)														//if it's not a single word, we check for palindrome on word level
			{																					//we start at each end of the phrase, and compare the words whilst moving inwards
				i = 0; j =  userInputArray.length - 1;
				while ((userInputArray[i].equalsIgnoreCase(userInputArray[j])) && (i < j))
				{	
					i++;
					j--;
				}
				if (i >= j)
				{
					wordLevel = true;															//If the word is a palindrome, wordlevel is set to true
				}
					
			}
			String reverseUserInput = "", newTrimmedUserInput = "";								//We create two new empty strings
			for(index = 0; index < trimmedUserInput.length(); index++)							//The first is filled with just the alphabetic characters from trimmedUserInput
			{
				aChar = trimmedUserInput.charAt(index);											//we utilize precisely the same method as we did to make trimmedUserInput
				if (Character.isLetterOrDigit(aChar))											//but we only checks for is letter or digit
					newTrimmedUserInput += aChar;
			}
			for(index = newTrimmedUserInput.length() - 1; index >= 0; index--)					//This simply reverse the contents of newTrimmedUserInput
					reverseUserInput += newTrimmedUserInput.charAt(index);						//and puts it into reverseUserInput
			if(reverseUserInput.equalsIgnoreCase(newTrimmedUserInput))
				letterLevel = true;																//If the reversed contents are equals to the newTrimmedUserInput contents then it is a letter palindrome
			if(letterLevel && wordLevel)																				//If both the letter and word level returned true we know that the input was a phrase
				result = "The Phrase \"" + userInput + "\" is a palindrome at the letter level and the word level";		//It was a palindrome at both levels
			else if(wordLevel)																							//This would indicate a palindrome at just the word level
				result = "The Phrase \"" + userInput + "\" is a palindrome the word level";								//again we only have to cater for phrases, not words.
			else if(letterLevel)																						//Here we check for letter palindrome
				result = "The word/phrase \"" + userInput + "\" is a palindrome at the letter level";					//The input could have been a phrase or a word
			else																										//;In the case that it fails all conditions it is not a plaindrom
				result = "The word/phrase \"" + userInput + "\" is not a palindrome";
		}
		JOptionPane.showMessageDialog(null, result, "Content Analyzer Results", 1);
			

	}
		public static void longestShortestWord(){
		String requestMessage = "Enter a sentence consisting of spaces, numbers and alphabetic characters only.";	//Message for getUserInput
		String windowTitle = "Word Length Analyzer", userInput, result = "";										//Title for getUserInput
		String trimmedUserInput = "";
		char aChar;
		boolean isDuplicate = false;
		int index, maxInt, minInt, count;																					//int vraibles for holding max and min values
		userInput = getUserInput(requestMessage, windowTitle, ".*");
		for(index = 0; index < userInput.length(); index++)																	//Trimming the user input
		{
			aChar = userInput.charAt(index);
			if (Character.isLetterOrDigit(aChar) || Character.isWhitespace(aChar))
				trimmedUserInput += aChar;
		}
		String userInputArray [] = trimmedUserInput.split(" ");														//Splitting the trimmed phrase into an array
		int lengthArray [] = new int [userInputArray.length];														//this parallel array holds the length of each word. Implemented to remove excess use of .length() method
		if(userInputArray.length > 1)																				//This checks whether it is bigger than one word
		{
			minInt = userInputArray [0].length();																	//Min and max values default to the first word
			maxInt = userInputArray [0].length();
			for(index = 1; index < userInputArray.length; index++)
			{
				lengthArray[index] = userInputArray [index].length();												//We go through the string array, placing the length of each word into the lnegth array
				if((lengthArray[index]) > maxInt)																	//If it's greater than maxInt, then maxInt becomes that value
					maxInt = lengthArray[index];																	//the opposite happens for minInt
				else if ((lengthArray[index]) < minInt)
					minInt = lengthArray[index];
			}
			for(index = 0; index < userInputArray.length; index++)													//This code checks whether the length of a word is the same to another
			{																										//If it is then it also checks whether the words are the same
				isDuplicate = false;
				for(count = index + 1; count < userInputArray.length && isDuplicate == false; count++)				//if they are the same, that word is given a length of -1, so it will not be recognized later
				{																									
					if(lengthArray[index] == lengthArray[count])													//the loop cancels once it finds a a duplicate.
						if(userInputArray[index].equalsIgnoreCase(userInputArray[count]))							//Since it sequentially scanning only those that it has to, no duplicates can be left remaining
						{
							lengthArray[index] = -1;
							isDuplicate = true;
						}
				}
			}
			
			result = "The maximum length of a word in this sentence is " + maxInt +					//Here we set up the results
												"\nThe words with this length are: ";				//If a word is the same length as the max or min value it is printed
			for(index = 0; index < userInputArray.length; index++)									//All the words with a length of -a can therefore not be printed
			{
				if(lengthArray[index] == maxInt)
					result += "\n" + userInputArray [index];
			}
			result += "\nThe minimum length of a word in this sentence is " + minInt +
												"\nThe words with this length are: ";
			for(index = 0; index < userInputArray.length; index++)
			{
				if(lengthArray[index] == minInt)
					result += "\n" + userInputArray [index];
			}
		}
		else
			result = "You must enter a sentence using spaces, number and alphabets only";			//This is reached if the user input less than 2 words, or just non alphabets
		JOptionPane.showMessageDialog(null, result, "Content Analyser Results", 1);
		
	
	}
	public static String getUserInput(String message, String windowTitle, String validPattern){
		/*
		This method returns general input from the user, sanitized to remove
		any leading or trailing whitespace, and unnecessary spaces.
		It also allows a message and matching pattern to be passed down based
		on what kind of input is required.
		I'm assuming that only the string being used for calculation is being returned, not the actual string typed by the user.
		*/
		String userInput = "", sanatizedString = "";
		//the concatenation operation ensures that a null pointer exception should not occur if the user cancels text entry.
		if (!headless) userInput += JOptionPane.showInputDialog(null, message, windowTitle, 3);
		else{
			System.out.println(message);
			userInput += consoleInput.nextLine();
		}
		//clean up the string to remove common text entry errors, multiple assignments are used for clarity.
		//remove surrounding whitespace.
		sanatizedString = userInput.trim();
		//remove multiple uses of spaces.
		sanatizedString = sanatizedString.replaceAll(" +", " ");
		//verify that the string matches the given pattern, sets the return to an empty string if it does not match. A while loop should be used here to improve user experience.
		if (!sanatizedString.matches(validPattern)) sanatizedString = "";
		return sanatizedString;
	}
	/*
	A separate method is used to display errors to the user in order to keep the experience consistent throughout execution.
	*/
	public static void announceError(String errorMessage){
		if (headless){
			System.err.println("Error: " + errorMessage);
		}
		else {
			JOptionPane.showMessageDialog(null, errorMessage, "Error.", 2);
		}
	}
	/*
	A method is also used to announce results to the user to make it much easier to change the way the program shows its results to the user.
	*/
	public static void announceResults(String results){
		if (headless) {
			System.out.println(results);
		}
		else {
			JOptionPane.showMessageDialog(null, results, "Results.", 1
				);
		}
	}

}
