import javax.swing.JOptionPane;
import java.util.Scanner;
public class Project1{
	/**
		A simple program to demonstrate string manipulation.
		Effort was made to have only one exit point in the code, returning an exit state would probably be a better idea though.
		I'm also assuming that the main method is meant do deal with anything but selecting the method to execute.
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
				case 2: consonantCount();
				case 3: stringContent();
				case 4: areAnagrams();
				case 5: isPalindrome();
				case 6: longestShortestWord();
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
		if (userChoice != null) userChoice = userChoice.trim();
		else userChoice = "";
		//checks if the option is in the valid range, selects exit if anything else is entered.
		if (userChoice.matches("[0-6]"))
			chosenOption = Byte.parseByte(userChoice);
		else{
			chosenOption = 0;
			announceError("Invalid option, terminating program.");
		}
		return chosenOption;
	}
	public static void vowelContent(){
		//the string of vowels must be in alphabetical order.
		String vowels = "aeiou", requestMessage = "Enter a word or sentence consisting of spaces, letters and numbers only.", foundVowels = "";
		String windowTitle = "Vowel Analyzer",userInput, resultsMessage = "";
		int [] vowelCount = {0, 0, 0, 0, 0};
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
						resultsMessage += "\\nThe input contained all vowels in alphabetical order.";
					}
					else if (satisfiedCondition[3]) {
						resultsMessage += "\\nThe input contained all vowels in reverse alphabetical order.";
					}
				}
			
				//If a vowel was found at all, the amount of times it was found is added to the message.
				resultsMessage += "The vowel";
				for (int i = 0; i < vowelCount.length; i++) {
					if (vowelCount[i] > 0)
						resultsMessage += ", \'" + vowels.substring(i,i+1) + "\'" + " occurs " + vowelCount[i] + " times";
					if (i == vowelCount.length-1) resultsMessage += ".";
				}
			}
			announceResults(resultsMessage);






	}
	public static void consonantCount(){
	/*
	TODO: copy/paste above method and find/replace "vowel" with "consonant", or make a single method for both...
	*/
	}
	public static void stringContent(){

	}
	public static void areAnagrams(){

	}
	public static void isPalindrome(){

	}
	public static void longestShortestWord(){

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
		userInput += JOptionPane.showInputDialog(null, message, windowTitle, 3);
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
