import java.util.*;
import java.io.*;
public class Project3{
	/**
		A program to demonstrate string manipulation and handling files.
		Spelling in this code is compliant with American English.
	**/
	//This Scanner object is declared globally because multiple methods require it.
	public static Scanner consoleInput = new Scanner(System.in);
	public static ArrayList<String> userDictArrayList;
	public static void main(String[] args) {
		File userDictFile;
		byte menuChoice = -1;
		if (args.length > 0) {
			if (args[0].matches("-h") || args[0].matches("--help")) {
				System.out.println("This program allows users to interact with a dictionary file");
				System.exit(0);
			}
		}
		String dictFileName = "";
		System.out.println("Please enter the name of your dictionary file.");
		dictFileName = consoleInput.nextLine();
		userDictFile = new File(dictFileName);
		if (userDictFile.exists()){
			userDictArrayList = readFile(dictFileName);
		} else {
			userDictArrayList = new ArrayList<String>();
			System.out.println()
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
						case 1: SearchDictionary();
						break;
						case 2: AddDictionary();
						break;
						case 3: DeleteDictionary();
						break;
						case 4: FrequencyWordCount();
						break;
						case 5: LongesyShortestPalindrome();
						break;
						case 6: AddPassage();
						break;
						case 7: Lexicographer();
						break;
						case 0: break;
						default: menuChoice = -1;
			}
		}


	}
	public static byte getMenuOption(){
		byte chosenOption;
		String userChoice = "", menuOptions = "Please select one of the following options by entering the option number:";
		menuOptions += "\n 1) Search.";
		menuOptions += "\n 2) Add.";
		menuOptions += "\n 3) Delete.";
		menuOptions += "\n 4) Frequency of starting letters and word count";
		menuOptions += "\n 5) Longest, shortest word and palindrome.";
		menuOptions += "\n 6) Add passage from file";
		menuOptions += "\n 7) Lexicographer function";
		menuOptions += "\n 0) Exit";
		/*
			gets an option from the user and trims off leading and trailing whitespace
			a better solution would be needed if the menu had more than ten options.
		*/
		System.out.println(menuOptions);
		userChoice = consoleInput.nextLine();
		//If input isn't null, it checks if it's in the range 0-7. If it isn't, the menu will repeat.
		if (userChoice != null){
			 userChoice = userChoice.trim();
			if (userChoice.matches("[0-7]"))
				chosenOption = Byte.parseByte(userChoice);
			else{
				System.out.println("You must enter a number between 0-7");
				chosenOption = -1;	
			}
		}
		else chosenOption = 0;
		return chosenOption;
	}
	
	public static void SearchDictionary(){}

	public static void AddDictionary(){}
	
	public static void DeleteDictionary(){}
	
	public static void FrequencyWordCount(){}
	
	public static void LongesyShortestPalindrome(){}
	
	public static void AddPassage(){

}

	public static void Lexicographer(){}
	


	public static ArrayList<String> readFile(String fileName){
		/*
			Accepts a string denoting the relative position of a text file.
			Returns an ArrayList of Strings with a single word in each, including special characters.
		*/
		File fileToRead = new File(fileName);
		if (!fileToRead.exists()){}
		ArrayList<String> fileWordsArrayList = new ArrayList<String>();
		return fileWordsArrayList;
	}
}
	
