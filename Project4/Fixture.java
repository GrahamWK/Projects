import java.util.*;
import java.io.*;
public class Fixture{
	/**
		The Fixture class describes an object containing information on a premiership match fixture.
		This class can be constructed as either a past, or future match.
		Objects of this class can easily be displayed using print methods.
	**/

	private boolean hasBeenPlayed;
	private int homeTeamNumber, awayTeamNumber;
	//This file name should not be written here in any serious application.
	private final String[] teamNamesAndNumbers = readLinesFromFile("PremiershipTeamsOrPlayers.txt");

	public String toString(){
	/*
		This method replaces the toString method to allow direct printing of the object.
		Will return formatted results for the instance of fixture.
	*/

		return "fuzzy dice";
	}

	/**
	************************************************
	This method does not currently work correctly.
	It will be fixed in timer to use it in one of the constructors
	**/
	private static String[] readLinesFromFile(String fileName){
		/*
			Accepts a string denoting the relative position of a text file.
			Returns an Array of Strings with a single line in each.
		*/
		File fileToRead = new File(fileName);
		Scanner dataFromFile;
		ArrayList<String> fileLinesArrayList = new ArrayList<String>();
		String[] linesFromFile;
		try {
				if (!fileToRead.exists()){
					dataFromFile = new Scanner(fileToRead);
					while (dataFromFile.hasNext()) {
						//Add every line from the file to the ArrayList.
						fileLinesArrayList.add(dataFromFile.nextLine());
					}
				}
		}
		catch (Exception e){
			//If the file doesn't exist and logic fails, empty out the ArrayList.
			fileLinesArrayList = new ArrayList<String>();
		}
		//Create an Array large enough to hold all of the lines from the file.
		linesFromFile = new String[fileLinesArrayList.size()];
		//Copy every line from the ArrayList to the Array.
		for (int i = 0; i < linesFromFile.length; ++i) linesFromFile[i] = fileLinesArrayList.get(i);
		return linesFromFile;
	}
}
