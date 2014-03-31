import java.util.*;
import java.io.*;
public class Project3{
	/**
		A simple program to demonstrate string manipulation.
		Effort was made to have only one exit point in the code, returning an exit state would probably be a better idea though.
		I'm also assuming that the main method is meant do deal with anything but selecting the method to execute.
		Spelling in this code is complaint with American English.
	**/
	//This Scanner object is declared globally because two methods require it.
	public static Scanner consoleInput = new Scanner(System.in);
	public static ArrayList<String> tempArrayList = new ArrayList<String>();
	public static ArrayList<String> dictionaryArrayList;
	public static void main(String[] args) throws IOException {
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
			dictionaryArrayList = readWordsFromFile(dictFileName);
		} else {
			dictionaryArrayList = new ArrayList<String>();
			System.out.println("The file was not found, an empty one will be created");
			//Create a new file 
			writeToFile(dictionaryArrayList, dictFileName);
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
						case 1: searchDictionary();
						break;
						case 2: addDictionary();
						break;
						case 3: deleteDictionary(dictFileName);
						break;
						case 4: frequencyWordCount();
						break;
						case 5: longesyShortestPalindrome();
						break;
						case 6: addPassage();
						break;
						case 7: lexicographer();
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
		menuOptions += "\n 6) Add passage";
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
	
	public static void searchDictionary() throws IOException{
		System.out.print("Please enter a word to search: ");
		String wordToSearch = consoleInput.nextLine();
		if(searchDictionaryArrayList(wordToSearch)){
			System.out.println("The word " + wordToSearch + " was found in the dictionary");
			}
		else{
			 System.out.print(wordToSearch + " was not found in dictionary");
			 addTempArrayList(wordToSearch);
		 }
	}		
	
	public static void addDictionary() throws IOException{
		System.out.print("Please enter a word to add: ");
		String wordToAdd = consoleInput.nextLine();
		if(searchDictionaryArrayList(wordToAdd)){
			System.out.println("The word " + wordToAdd + " is already in the dictionary");
		}
		else{
			System.out.print(wordToAdd + " was not found in dictionary");
			addTempArrayList(wordToAdd);	
		}
	}	
	
	public static void deleteDictionary(String file) throws IOException{
		System.out.print("Please enter a word to delete: ");
		String wordToDelete = consoleInput.nextLine();
		if(searchDictionaryArrayList(wordToDelete)){
			dictionaryArrayList.remove(wordToDelete);
			updateWordTxt(file, 0);
			System.out.println("The word " + wordToDelete + " was deleted from the dictionary");
		}
		else{
			System.out.print(wordToDelete + " was not found in dictionary");
			addTempArrayList(wordToDelete);	
		}
	}
		
	public static void updateWordTxt(String file, int n) throws IOException {
		PrintWriter outFile = new PrintWriter(file);
		if(n == 1){
			for(int i =0; i < tempArrayList.size(); i++){
				outFile.println(tempArrayList.get(i));
			}
		}
		else{
			for(int i =0; i < dictionaryArrayList.size(); i++){
				outFile.println(dictionaryArrayList.get(i));
			}
		}
			
		outFile.close();
	}
	
	public static boolean searchDictionaryArrayList(String wordToSearch) throws IOException{
		int first = 0; int last = dictionaryArrayList.size()-1; int middle = (first + last)/2;
		boolean found = false;
		while(first <= last && !found)
		{
			if((dictionaryArrayList.get(middle)).equalsIgnoreCase(wordToSearch)) 			found = true;
			else if ((dictionaryArrayList.get(middle)).compareToIgnoreCase(wordToSearch) > 0) last = middle - 1;
			else if ((dictionaryArrayList.get(middle)).compareToIgnoreCase(wordToSearch) < 0) first = middle + 1;
			middle = (first + last)/2;
		}
		return found;	
		}
		
	public static void addTempArrayList(String wordToSearch) throws IOException{
		int first = 0, last = tempArrayList.size()-1; int middle = (first + last)/2;
		boolean found = false;
		while(first <= last && !found)
		{
			if((tempArrayList.get(middle)).equalsIgnoreCase(wordToSearch)) 			found = true;
			else if ((tempArrayList.get(middle)).compareToIgnoreCase(wordToSearch) > 0) last = middle - 1;
			else if ((tempArrayList.get(middle)).compareToIgnoreCase(wordToSearch) < 0) last = middle + 1;
			middle = (first + last)/2;
		}
		if(!found){
			System.out.print("\na lexicographer has been requested to approve the word\n");
			tempArrayList.add(wordToSearch);
			Collections.sort(tempArrayList);
			updateWordTxt("temp.txt", 1);
			}
		else
			System.out.print("\nit is currently awaiting a lexocgraphers approval\n");
		}

	
	public static void frequencyWordCount(){}
	
	public static void longesyShortestPalindrome(){}
	
	public static void addPassage(){}
	
	public static void lexicographer(){}
		
	public static ArrayList<String> readWordsFromFile(String fileName){
		/*
			Accepts a string denoting the relative position of a text file.
			Returns an ArrayList of Strings with a single word in each, including special characters.
		*/
		File fileToRead = new File(fileName);
		Scanner dataFromFile;
		ArrayList<String> fileWordsArrayList = new ArrayList<String>();
		String[] wordsFromFile;
		try {
				if (fileToRead.exists()){
					dataFromFile = new Scanner(fileToRead);
					while (dataFromFile.hasNext()) {
						//deal with having multiple words on a line.
						wordsFromFile = dataFromFile.nextLine().split(" ");
						//dump all of that business into the ArrayList no matter what.
						for (String word : wordsFromFile) fileWordsArrayList.add(word);
					}
				}
		}
		catch (Exception e){
			//If the file doesn't exist and logic fails, empty out the ArrayList.
			fileWordsArrayList = new ArrayList<String>();
		}
		return fileWordsArrayList;
	}


	public static void writeToFile(ArrayList<String> lines, String fileName){
		/*
			Will print an ArrayList of Strings to a file defined by a supplied String.
			Terminates the application with an error message if exceptions occur.
		*/
		PrintWriter output;
		try {
			output = new PrintWriter(fileName);
			//This is to make a new file, or blank it out if the ArrayList is empty
			output.println("");
			for (String lineToWrite : lines)
				output.println(lineToWrite);
			output.close();
		}
		catch (Exception e) {
			System.err.println("Something dreadful has happened.\nThe application can't access/modify files.\nIt will now exit.");
			System.exit(74);
		}
	}
}
		
