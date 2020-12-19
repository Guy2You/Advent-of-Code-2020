package Day11;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Day11
{
	private static int[][] seatingArray;

	public static void main(String[] args) throws Exception
	{
		Path inputPath = Paths.get(String.format("src%1$smain%1$sjava%1$sDay11%1$sinput.txt", File.separator));
		File inputFile = new File(inputPath.toUri());
		Scanner scanner = new Scanner(inputFile);
		List<String> seating = new ArrayList<>();
		while (scanner.hasNextLine())
		{
			seating.add(scanner.nextLine());
		}
		seatingArray = new int[seating.get(0).length()][seating.size()];
		for (int i = 0; i < seating.size(); i++)
		{
			for (int j = 0; j < seating.get(i).length(); j++)
			{
				char c = seating.get(i).charAt(j);
				if (c == 'L')
				{
					seatingArray[j][i] = 1;
				}
				else if (c == '.')
				{
					seatingArray[j][i] = 0;
				}
				else
				{
					throw new Exception("Unexpected char.");
				}
			}
		}
		System.out.println("Occupied: " + part1());
		System.out.println("Occupied: " + part2());
	}

	public static int part1()
	{
		int occupied = 0;
		emptyAllSeats();
		simulateUntilSettlePart1();
		for (int[] row : seatingArray)
		{
			for (int seat : row)
			{
				if (seat == 2) occupied++;
			}
		}
		return occupied;
	}

	public static int part2()
	{
		int occupied = 0;
		emptyAllSeats();
		simulateUntilSettlePart2();
		for (int[] row : seatingArray)
		{
			for (int seat : row)
			{
				if (seat == 2) occupied++;
			}
		}
		return occupied;
	}

	public static void simulateUntilSettlePart1()
	{
		int[][] previousSeatingArray = new int[seatingArray.length][];
		for (int i = 0; i < seatingArray.length; i++)
		{
			previousSeatingArray[i] = Arrays.copyOf(seatingArray[i], seatingArray[i].length);
		}

		int height = seatingArray.length;
		int width = seatingArray[0].length;
		for (int i = 0; i < height; i++)
		{
			for (int j = 0; j < width; j++)
			{
				int occupiedNeighbours = 0;
				for (int x = -1; x < 2; x++)
				{
					for (int y = -1; y < 2; y++)
					{
						if (!(x == 0 && y == 0))
						{
							if (i + x >= 0 && i + x < height && j + y >= 0 && j + y < width)
							{
								if (previousSeatingArray[i + x][j + y] == 2) occupiedNeighbours++;
							}
						}
					}
				}
				if (occupiedNeighbours == 0 && seatingArray[i][j] == 1) seatingArray[i][j] = 2;
				if (occupiedNeighbours >= 4 && seatingArray[i][j] == 2) seatingArray[i][j] = 1;
			}
		}
		if (!(Arrays.deepEquals(seatingArray, previousSeatingArray))) simulateUntilSettlePart1();
	}

	public static void simulateUntilSettlePart2()
	{
		int[][] previousSeatingArray = new int[seatingArray.length][];
		for (int i = 0; i < seatingArray.length; i++)
		{
			previousSeatingArray[i] = Arrays.copyOf(seatingArray[i], seatingArray[i].length);
		}

		int height = seatingArray.length;
		int width = seatingArray[0].length;
		int[][] directions = {
				{0, -1},
				{1, -1},
				{1, 0},
				{1, 1},
				{0, 1},
				{-1, 1},
				{-1, 0},
				{-1, -1}};
		for (int i = 0; i < height; i++)
		{
			for (int j = 0; j < width; j++)
			{
				int occupiedNeighbours = 0;
				for (int[] direction : directions)
				{
					int x = i + direction[0];
					int y = j + direction[1];
					while (x >= 0 && x < height && y >= 0 && y < width)
					{
						if (previousSeatingArray[x][y] == 2)
						{
							occupiedNeighbours++;
							break;
						}
						if (previousSeatingArray[x][y] == 1) break;
						x += direction[0];
						y += direction[1];
					}
				}
				if (occupiedNeighbours == 0 && seatingArray[i][j] == 1) seatingArray[i][j] = 2;
				if (occupiedNeighbours >= 5 && seatingArray[i][j] == 2) seatingArray[i][j] = 1;
			}
		}
		if (!(Arrays.deepEquals(seatingArray, previousSeatingArray))) simulateUntilSettlePart2();
	}

	private static void emptyAllSeats()
	{
		for (int i = 0; i < seatingArray.length; i++)
		{
			for (int j = 0; j < seatingArray[0].length; j++)
			{
				if (seatingArray[i][j] == 2) seatingArray[i][j] = 1;
			}
		}
	}
}
