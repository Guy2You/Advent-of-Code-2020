package Day3;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day3
{
	private static List<String> forest;

	public static void main(String[] args) throws FileNotFoundException
	{
		Path inputPath = Paths.get(String.format("src%1$smain%1$sjava%1$sDay3%1$sinput.txt", File.separator));
		File inputFile = new File(inputPath.toUri());
		Scanner scanner = new Scanner(inputFile);
		forest = new ArrayList<>();
		while (scanner.hasNextLine())
		{
			forest.add(scanner.nextLine());
		}

		System.out.println("Trees = " + part1());
		System.out.println("Trees Product = " + part2());
	}

	public static int part1()
	{
		int numRight = 3;
		int numDown = 1;
		return traverse(numRight, numDown);
	}

	public static long part2()
	{
		int[][] slopes = {{1, 1}, {3, 1}, {5, 1}, {7, 1}, {1, 2}};
		long product = 1;
		for (int[] slope : slopes) product *= traverse(slope[0], slope[1]);
		return product;
	}

	public static int traverse(int numRight, int numDown)
	{
		int column = 0;
		int row = 0;
		int numTrees = 0;

		while (row < forest.size())
		{
			String lineOfTrees = forest.get(row);
			if (lineOfTrees.charAt(column) == '#') numTrees++;

			row += numDown;
			column += numRight;
			column %= lineOfTrees.length();
		}
		return numTrees;
	}
}
