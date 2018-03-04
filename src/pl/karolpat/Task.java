package pl.karolpat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Task {

	public static String T = "2B 2D 3D 4D 4A 1B 1C";
	public static String S = "1B 2C, 2D 4D";
	public static char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toUpperCase().toCharArray();

	public static void main(String[] args) {

		solution(5, S, T);
		createBoard(5);

	}

	/**
	 * @param N
	 *            width/height of the board.
	 * @param S
	 *            String that contains coordinates on board where the ship is
	 *            located.
	 * @param T
	 *            String that contains coordinates on board where hits are located.
	 * @return String that represents result of shots on ships.
	 */
	public static String solution(int N, String S, String T) {

		Map<Integer, Set<String>> mapOfShipsLocation = shipPosition(S);
		Set<String> hitsList = hitPosition(T); // Set containing all hits coordinates
		int sink = 0; // number of ships that went under
		int hit = 0; // number of ships that were hit

		for (Map.Entry<Integer, Set<String>> entry : mapOfShipsLocation.entrySet()) {

			Set<String> shipCoordinates = entry.getValue(); // Set of consecutive ship's coordinates on board.

			List<String> onTarget = new ArrayList<>(); // Set that will contain coordinates of hits that reached the
														// target (ship).

			for (String i : shipCoordinates) {

				if (hitsList.contains(i)) { // If coordinates is on hitsList and on shipCoorinated
					onTarget.add(i); // then this coordinate is added to onTarget list
					
				}
			}
			if (shipCoordinates.size() == onTarget.size()) { //if all coordinates from shipCoordinates list are present on onTarget list
				sink++;										//then that means ship is sunken	
			} else if (onTarget.size() != 0 && onTarget.size() < shipCoordinates.size()) { //if onTarget list size is not 0 but it is less than
				hit++;                                                                     //size of list containing ship coordinates then that means  
			}																				//ship is hit but still floats	
		}
		System.out.println("Hit: " + hit + ", sink: " + sink);
		return "Hit: " + hit + ", sink: " + sink;
	}

	/** Creates board to play battle ships but it is never used in this project
	 * @param N size of the board, width/height
	 * @return an array describing fields on the board
	 */
	public static String[][] createBoard(int N) {

		char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toUpperCase().toCharArray(); //String that creates char array of letters. Found on the Internet.
											
		String[][] board = new String[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				board[i][j] = (i + 1) + "" + alphabet[j];
			}
		}
		return board;
	}

	/** Receives String that contains coordinates of left top and right bottom corner of the ship location.
	 * The input String is split by spaces and comma to get only fields on board. Then consecutive ship and its coordinates are stored in map.
	 * @param ship String that contains coordinates of left top and right bottom corner of the ship location.
	 * @return map of Integer that is index of consecutive ship and String which constitutes all coordinates of the ship.
	 */
	public static Map<Integer, Set<String>> shipPosition(String ship) {
		
		ship = ship.replaceAll("\\s", "");
		String[] shipCoordinates = ship.split(",");
		Map<Integer, Set<String>> map = new HashMap<>();

		List<Character> alphabetList = new ArrayList<>(); //
		for (char c : alphabet) {
			alphabetList.add(c);
		}
		int count = 1; //index that will constitute the ship's index on map
		
		for (String s : shipCoordinates) {

			Set<String> positions = new HashSet<>();  //storage of coordinates on board of the ship
			String[] split = s.split("");  //split on every char so that the array contains four chars

			for (int i = 0; i < split.length; i = i + 2) { //i=i+2 will prevent from ArrayIndexOutOfBoundException
				positions.add(split[i] + "" + split[i+1]);  //first coordinates that are present on input String
				
				if (i <= 1) {
					if (split[i + 1].charAt(0) == split[i + 3].charAt(0)) { //checks if letters of two next coordinates are the same, 
																			//i.e. 2D, 4D returns true

						positions.add((Integer.parseInt(split[i]) + 1) + "" + split[i + 1].charAt(0));
						positions.add((Integer.parseInt(split[i]) + 2) + "" + split[i + 1].charAt(0));
						
					} else if (split[i].charAt(0) == split[i + 2].charAt(0)) {//checks if digits of two next coordinates are the same, 
																			  //i.e. 1B, 1D returns true
						
						int alphabetIndex = alphabetList.indexOf(split[i].charAt(0)); //this will produce next letter from alphabet
						positions.add(((split[i + 1]) + "" + alphabetList.get(alphabetIndex + 1)));
						positions.add(((split[i + 1]) + "" + alphabetList.get(alphabetIndex + 2)));
						
					} else if (split[i].charAt(0) == split[i + 2].charAt(0) - 1) {//checks if digit of the first coordinates equals the digit of next coordinets minus 1 
						  														  //i.e. 1B, 2C returns true
						
						positions.add((split[i]) +""+split[i + 3].charAt(0));
						positions.add(((split[i + 2]) +""+(split[i + 1].charAt(0))));
					}
				}
			}
			map.put(count, positions);
			count++;
		}
		return map;
	}

	/** Receives String that contains coordinates of hits.
	 * The input String is split by spaces to get only fields on board.
	 * @param hit ship String that contains coordinates of hits.
	 * @return Set of coordinates of hits to be compared with ship coordinates.
	 */
	public static Set<String> hitPosition(String hit) {

		String[] split = hit.split(" ");
		Set<String> positions = new HashSet<>();

		for (String s : split) {
			positions.add(s);
		}
		return positions;
	}

}
