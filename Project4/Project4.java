import java.io.*;
import java.util.*;
public class Project4{
	/**
		This program allows a user to view Premiership results and fixtures and display a table with all available results.
		Fixture information, results and a table of team names and numbers are stored in the same folder as CSV.
	**/
	public static void main(String[] args) {


	}

	private static ArrayList<String> readFile(String filename){
		/*
			Reads a file in the same directory.
			Returns an ArrayList with each of the lines from the file.
			Produces an error message if the file is not found and returns an empty ArrayList.
		*/
		Scanner fileData;
		ArrayList<String> toReturn = new ArrayList<String>();
		File fileToRead = new File(filename);
		//Check if the desired file does not exist.
		if (!fileToRead.exists()){
			System.err.println("The file \"" + filename + "\" was not found.");
		}else{
			try{
				//Construct a Scanner and read all of the lines in the file.
				fileData = new Scanner(fileToRead);
				while (fileData.hasNext())
					toReturn.add(fileData.nextLine());
				fileData.close();
			}
			/*
				Catch any and all exceptions that occour when reading the file,
				print some friendly debug information to the screen,
				and construct an empty ArrayList to return.
			*/
			catch (Exception e) {
				System.err.println("A file exception occoured, give this to a developer:\n\n" + e + "\nOn file: " + filename);
				toReturn = new ArrayList<String>();
			}
		}
		return toReturn;

	}
}
