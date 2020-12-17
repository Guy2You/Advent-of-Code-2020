package Day5;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Day5
{
	private static List<boolean[]> boardingPasses;

	public static void main(String[] args) throws Exception
	{
		Path inputPath = Paths.get(String.format("src%1$smain%1$sjava%1$sDay5%1$sinput.txt", File.separator));
		File inputFile = new File(inputPath.toUri());
		Scanner scanner = new Scanner(inputFile);
		boardingPasses = new ArrayList<>();
		while (scanner.hasNextLine())
		{
			boardingPasses.add(buildBoardingPass(scanner.nextLine()));
		}
		boardingPasses = boardingPasses.stream()
				.sorted(Comparator.comparingInt(a -> calculateBoardingID(a))).collect(Collectors.toList());

		System.out.println("Seat ID: " + part1());
		System.out.println("Seat ID: " + part2());
	}

	public static int part1()
	{
		int maxSeatID = Integer.MIN_VALUE;
		for (boolean[] pass : boardingPasses)
		{
			int seatID = calculateBoardingID(pass);
			if (seatID > maxSeatID) maxSeatID = seatID;
		}
		return maxSeatID;
	}

	public static int part2() throws Exception
	{
		List<Integer> middleBoardingIDs = boardingPasses.stream()
				.map(a -> calculateBoardingID(a)).collect(Collectors.toList());
		int i = middleBoardingIDs.get(0) - 1;
		for (int id : middleBoardingIDs)
		{
			if (!(id == i + 1)) return i + 1;
			i++;
		}
		throw new Exception("Couldn't find value.");
	}

	public static boolean[] buildBoardingPass(String pass)
	{
		boolean[] boardingPass = new boolean[pass.length()];
		for (int i = 0; i < pass.length(); i++)
		{
			boardingPass[i] = pass.charAt(i) == 'B' || pass.charAt(i) == 'R';
		}
		return boardingPass;
	}

	public static int calculateBoardingID(boolean[] pass)
	{
		int seatID = 0;
		for (int i = 0; i < 7; i++)
		{
			if (pass[i]) seatID += Math.pow(2, 6 - i);
		}
		seatID *= 8;
		for (int i = 0; i < 3; i++)
		{
			if (pass[i + 7]) seatID += Math.pow(2, 2 - i);
		}
		return seatID;
	}
}
