package pl.karolpat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Task {

	public static String T = "2B 2D 3D 4D 4A 1B 1C";
	public static String S = "1B 2C,2D 4D, 1A 3A";
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

			Set<String> onTarget = new HashSet<>(); // Set that will contain coordinates of hits that reached the
														// target (ship).

			for (String i : shipCoordinates) {

				if (hitsList.contains(i)) { // If coordinates is on hitsList and on shipCoorinated
					onTarget.add(i); // then this coordinate is added to onTarget list
				}
			}
			if (shipCoordinates.size() == onTarget.size()) {
				sink++;
			} else if (onTarget.size() != 0 && onTarget.size() < shipCoordinates.size()) {
				hit++;
			}
		}
		System.out.println("Hit: " + hit + ", sink: " + sink);
		return "Hit: " + hit + ", sink: " + sink;
	}

	public static String[][] createBoard(int N) {

		char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toUpperCase().toCharArray();
		String[][] board = new String[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				board[i][j] = (i + 1) + "" + alphabet[j];
			}
		}
		return board;
	}

	public static Map<Integer, Set<String>> shipPosition(String ship) {

		String[] shipCoordinates = getShipCorners(ship);
		Map<Integer, Set<String>> map = new HashMap<>();
		int count = 1;

		List<Character> alphabetList = new ArrayList<>();
		for (char c : alphabet) {
			alphabetList.add(c);
		}

		for (String s : shipCoordinates) {

			Set<String> positions = new HashSet<>();
			String[] splitted = s.split("");

			for (int i = 0; i < splitted.length; i = i + 2) {
				positions.add(splitted[i + 1].charAt(0) + "" + splitted[i]);

				if (i <= 1) {
					if (splitted[i + 1].charAt(0) == splitted[i + 3].charAt(0)) {

						positions.add((Integer.parseInt(splitted[i]) + 1) + "" + splitted[i + 1].charAt(0));
						positions.add((Integer.parseInt(splitted[i]) + 2) + "" + splitted[i + 1].charAt(0));
					} else if (splitted[i].charAt(0) == splitted[i + 2].charAt(0)) {
						
						int alphabetIndex = alphabetList.indexOf(splitted[i].charAt(0));
						positions.add(((splitted[i + 1]) + "" + alphabetList.get(alphabetIndex + 1)));
						positions.add(((splitted[i + 1]) + "" + alphabetList.get(alphabetIndex + 2)));
					} else if (splitted[i].charAt(0) == splitted[i + 2].charAt(0) - 1) {
						
						positions.add((splitted[i]) +""+splitted[i + 3].charAt(0));
						positions.add(((splitted[i + 2]) +""+(splitted[i + 1].charAt(0))));
					}
				}
			}

			map.put(count, positions);
			count++;
		}
		return map;
	}

	public static Set<String> hitPosition(String hit) {
		hit = hit.replaceAll(" ", "");
		String[] splitted = hit.split("");
		Set<String> positions = new HashSet<>();

		for (int i = 0; i < splitted.length; i = i + 2) {
			positions.add(splitted[i] + "" + splitted[i + 1].charAt(0));
		}
		return positions;
	}

	public static String[] getShipCorners(String ship) {

		ship = ship.replaceAll("\\s", "");
		String[] shipCorners = ship.split(",");

		return shipCorners;
	}

}
