import java.util.ArrayList;
import java.util.Collections;
public class Leaderboard{
	/**
		This class describes an object which records and organises the results of 
		a number of premiership matches into a ranked leaderboard.
		Team numbers must start at one and not skip any numbers.
		Allignment only tested with monotype font, your milage may vary.
	**/
	//
	private String title;
	private ArrayList<TeamStats> unsortedLines = new ArrayList<TeamStats>();
	private ArrayList<TeamStats> sortedLines;

	public Leaderboard(ArrayList<String> teamNames, ArrayList<Integer> teamNums){
		//Construct a TeamStats object for every team provided in parallel arrays.
		for (int i = 0; i < teamNames.size() && i < teamNums.size(); ++i){
			unsortedLines.add(new TeamStats(teamNames.get(i), teamNums.get(i)));
		}
		title = String.format("%3s %3s %3s %5s %3s %3s %3s %3s %3s %6s %3s %3s %3s %3s %3s %3s %-15s", "Pos.", "Pl.", "Home:", "W", "D", "L", "F", "A", "Away:", "W", "D", "L", "F", "A", "GD", "Pts.", "Team Name");
	}


	public void addResults(Fixture fixture, Outcome results){
		/*
			This method adds the results from a played match to its teams
		*/

		//add results to the home team. (index from 0)
		unsortedLines.get(fixture.getHomeTeamNumber()-1).addResults(fixture, results);
		//add results to the away team. (index from 0)
		unsortedLines.get(fixture.getAwayTeamNumber()-1).addResults(fixture, results);
	}


	public void addResults(ArrayList<Fixture> fixtures, ArrayList<Outcome> results){
		/*
			This method adds a large numer of results in a big batch.
		*/
		for (int i = 0; i < fixtures.size() && i < results.size(); ++i){
			addResults(fixtures.get(i), results.get(i));
		}

	}


	private ArrayList<TeamStats> arrangeByPoints(ArrayList<TeamStats> toArrange){
		/*
			This method arranges an ArrayList of TeamStats objects by their score.
			A different ArrayList is returned rather than sorting in place.
		*/
		ArrayList<TeamStats> localCopy = toArrange;
		ArrayList<TeamStats> toReturn = new ArrayList<TeamStats>();
		int highestPoints, posOfHighest = -1;

		//Keep executing while the output ArrayList is smaller than the input.
		while (toArrange.size() > toReturn.size()){
			highestPoints = -1;
			for (int i = 0; i < localCopy.size(); i++){
				if (localCopy.get(i).getPoints() > highestPoints){
					highestPoints = localCopy.get(i).getPoints();
					posOfHighest = i;
				}
			}
			//add the team with the highest points to the return array.
			toReturn.add(localCopy.get(posOfHighest));
			//remove the TeamStats object (with greatest points) from the ArrayList.
			localCopy.remove(posOfHighest);
		}
		return toReturn;
	}


	public String toString(){
		/*
			Sorts the Leaderboard in order of points and creates a table.
			Returns a String formatted for immediet printing.
		*/
		String toReturn = (title + "\n");
		ArrayList<TeamStats> orderedStats = arrangeByPoints(unsortedLines);

		//add every line to the String to return, on a seperate line.
		for (int i = 0; i < orderedStats.size(); ++i){
			toReturn+= String.format("%3d ",i+1);
			toReturn+=  (orderedStats.get(i) + "\n");
		}
		return toReturn;
	}


}


class TeamStats{
	/**
		This class defines an object which tracks a team's premiership stats
		Objects of this class can be sorted based on their number of points.
	**/
	private String teamName;
	private int homeWins, homeLosses, homeDraws, matchesPlayed, teamNum;
	private int awayWins, awayLosses, awayDraws, goalDiff, points, hGoalsFor, hGoalsAg, aGoalsFor, aGoalsAg;

	public TeamStats(String teamName, int teamNum){
		this.teamNum = teamNum;
		this.teamName = teamName;
		//Initialise all leaderboard stats to zero.
		homeWins = homeLosses = homeDraws = matchesPlayed = awayWins = awayLosses = awayDraws = goalDiff = points = hGoalsAg = hGoalsFor = aGoalsAg = aGoalsFor = 0;
	}



	public String toString(){
		/*
			Returns a String to allow the object to be printed to the screen.
		*/

		//Format strings are terrible, but this was te easiest solution.
		return String.format("%3s %8s  %3s %3s %3s %3s %6s %3s %3s %3s %3s %3s %3s %-25s" ,matchesPlayed, homeWins, homeDraws, homeLosses, hGoalsFor, hGoalsAg, awayWins, awayDraws, awayLosses, aGoalsFor, aGoalsAg, goalDiff, points, teamName);
	}


	protected void addResults(Fixture fixture, Outcome results){
		/*
			Applies results from a fixture to a team's stats.
			Prints an error if the Outcome is for the Wrong Fixture.
		*/
		//This variable shows if this team was home or away 1-home 2-away.
		byte homeOrAway = -1;
		if (teamNum == fixture.getHomeTeamNumber())
			homeOrAway = 1;
		else if (teamNum == fixture.getAwayTeamNumber())
			homeOrAway = 2;
		else if (homeOrAway == -1) System.err.println("Error: Cannot add results, Fixture is not for this team.");
		if (fixture.getFixtureNumber() != results.getFixtureNumber()){
			System.err.println("Error: Cannot add results, outcome and fixture mismatch.");
		} else if (homeOrAway != -1) {
			/*
				This whole block only gets executed when there's no error.
			*/

			//if this team is the home team.
			if (homeOrAway == 1){
				//Add the goals for and against.
				hGoalsFor+= (results.getHomeScore());
				hGoalsAg+= (results.getAwayScore());
				//if this team won.
				if (results.getHomeScore() > results.getAwayScore()){
					homeWins++;
					points+= 3;
				//if this team lost.
				} else if (results.getHomeScore() < results.getAwayScore()) {
					homeLosses++;
				//if there was a draw.
				} else{
					points++;
					homeDraws++;
				}

			//if this team is the home team.
			} else if (homeOrAway == 2) {
				//Add the goals for and against.
				aGoalsFor+= (results.getAwayScore());
				aGoalsAg+= (results.getHomeScore());
				//if this team won.
				if (results.getHomeScore() < results.getAwayScore()){
					awayWins++;
					points+= 3;
				//if this team lost.
				} else if (results.getHomeScore() > results.getAwayScore()) {
					awayLosses++;
				//if there was a draw.
				} else{
					points++;
					awayDraws++;
				}
				
			}
			//if this team played at all.
			matchesPlayed++;
			//Recalculate the goal difference for this outcome.
			goalDiff = ((aGoalsFor + hGoalsFor) - (aGoalsAg + hGoalsAg));
		}

	}


	public int getTeamNumber(){
		/*
			Returns this team's number.
		*/
		return teamNum;
	}


	public int getPoints(){
		/*
			Returns the Number of points this team has.
		*/
		return points;
	}
}
