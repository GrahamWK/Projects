import java.io.*;
import java.util.*;
public class Project4
{
	public static ArrayList<TeamOrPlayer> teamOrPlayerArrayList = new ArrayList<TeamOrPlayer>();
	public static ArrayList<Outcome> outcomesArrayList = new ArrayList<Outcome>();
	public static ArrayList<Fixture> fixturesArrayList = new ArrayList<Fixture>();
	public static ArrayList<Leaderboard> leaderboardArrayList = new ArrayList<Leaderboard>();
	public static ArrayList<String> teamNamesArrayList = new ArrayList<String>();
	public static ArrayList<Integer> teamNumbersArrayList = new ArrayList<Integer>();
	public static ArrayList<Integer> fixtureNumbersArrayList = new ArrayList<Integer>();
	public static void main (String [] args)
	{
		ArrayList<String> temp = new ArrayList<String>();
		temp = readFile("PremiershipTeamsOrPlayers.txt");
		for(int i = 0; i < temp.size(); i ++)
		{
			TeamOrPlayer teamPlayer;
			int teamNumber,commaLocation;
			String teamName, fileData;
			fileData = temp.get(i);
			commaLocation = fileData.indexOf(",");
			teamNumber = Integer.parseInt(fileData.substring(0,commaLocation));
			teamNumbersArrayList.add(teamNumber);
			teamName = fileData.substring(commaLocation+1);
			teamNamesArrayList.add(teamName);
			teamPlayer = new TeamOrPlayer(teamNumber, teamName);
			teamOrPlayerArrayList.add(teamPlayer);
		}
		temp = readFile("PremiershipFixtures.txt");
		for(int i = 0; i < temp.size(); i ++)
		{
			Fixture fixture;
			int fixtureNumber, homeTeamNumber, awayTeamNumber, firstCommaLocation, secondCommaLocation;
			String fileData;
			fileData = temp.get(i);
			firstCommaLocation = fileData.indexOf(",");
			secondCommaLocation = fileData.lastIndexOf(",");
			fixtureNumber = Integer.parseInt(fileData.substring(0,firstCommaLocation));
			fixtureNumbersArrayList.add(fixtureNumber);
			homeTeamNumber = Integer.parseInt(fileData.substring(firstCommaLocation+1, secondCommaLocation));
			awayTeamNumber = Integer.parseInt(fileData.substring(secondCommaLocation+1));
			fixture = new Fixture(fixtureNumber, homeTeamNumber, awayTeamNumber);
			fixturesArrayList.add(fixture);
		}
		temp = readFile("PremiershipResults.txt");
		for(int i = 0; i < temp.size(); i ++)
		{
			Outcome outcome;
			int fixtureNumber, homeTeamScore, awayTeamScore, firstCommaLocation, secondCommaLocation;
			String fileData;
			fileData = temp.get(i);
			firstCommaLocation = fileData.indexOf(",");
			secondCommaLocation = fileData.lastIndexOf(",");
			fixtureNumber = Integer.parseInt(fileData.substring(0,firstCommaLocation));
			homeTeamScore = Integer.parseInt(fileData.substring(firstCommaLocation+1, secondCommaLocation));
			awayTeamScore = Integer.parseInt(fileData.substring(secondCommaLocation+1));
			outcome = new Outcome(fixtureNumber, homeTeamScore, awayTeamScore);
			outcomesArrayList.add(outcome);
		}
		
		byte menuChoice = -1;
		if (args.length > 0) {
			if (args[0].matches("-h") || args[0].matches("--help")) {
				menuChoice = 0;
				System.out.println("This program allows users to see results from the preimership and the table associated with it.");
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
						case 1: ;
						break;
						case 2: ;
						break;
						case 3: leaderboard();
						break;
						case 0: break;
						default: menuChoice = -1;
			}
		}


	}
	public static byte getMenuOption(){
		Scanner consoleInput = new Scanner(System.in);
		byte chosenOption;
		String userChoice = "", menuOptions = "Please select one of the following options by entering the option number:";
		menuOptions += "\n 1) Display fixture that have been displayed and their results.";
		menuOptions += "\n 2) Display the fixtures that are yet to be played.";
		menuOptions += "\n 3) Display Premiership Leaderboard.";
		menuOptions += "\n 0) Exit";
		/*
			gets an option from the user and trims off leading and trailing whitespace
			a better solution would be needed if the menu had more than ten options.
		*/
		System.out.println(menuOptions);
		if (consoleInput.hasNext())
		userChoice = consoleInput.nextLine();
		//If input isn't null, it checks if it's in the range 0-7. If it isn't, the menu will repeat.
		if (userChoice != null){
			 userChoice = userChoice.trim();
			if (userChoice.matches("[0-3]"))
				chosenOption = Byte.parseByte(userChoice);
			else{
				System.out.println("\n\nYou must enter a number between 0-7\n\n");
				chosenOption = -1;	
			}
		}
		else chosenOption = 0;
		return chosenOption;
	}
	
	private static ArrayList<String> readFile(String filename)
	{
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
	public static void leaderboard(){
		//Create a leaderboard object with all of the teams.
		Leaderboard premiershipResults = new Leaderboard(teamNamesArrayList, teamNumbersArrayList);
		//Add all of the fixtures and results.
		premiershipResults.addResults(fixturesArrayList, outcomesArrayList);
		//Print the results table.
		System.out.println(premiershipResults);
	}
}
