package Day1;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day1
{
	private static final int targetSum = 2020;
	private static List<Integer> numbers;

	public static void main(String[] args) throws Exception
	{
		Path inputPath = Paths.get(String.format("src%1$smain%1$sjava%1$sDay1%1$sinput.txt", File.separator));
		File inputFile = new File(inputPath.toUri());
		Scanner scanner = new Scanner(inputFile);
		numbers = new ArrayList<>();
		while (scanner.hasNextLine())
		{
			numbers.add(scanner.nextInt());
		}
		part1();
		part2();
	}

	public static int part1() throws Exception
	{
		List<Integer> copyNumbers = new ArrayList<>(numbers);
		for (Integer i : numbers)
		{
			copyNumbers.remove(0);
			for (int j : copyNumbers)
			{
				if (i + j == targetSum)
				{
					int product = i * j;
					System.out.println("i = " + i);
					System.out.println("j = " + j);
					System.out.println("ij = " + product);
					return product;
				}
			}
		}
		throw new Exception("No pair of numbers that sum to " + targetSum + "found.");
	}

	public static int part2() throws Exception
	{
		List<Integer> copyNumbers = new ArrayList<>(numbers);
		for (Integer i : numbers)
		{
			copyNumbers.remove(0);
			for (int j : copyNumbers)
			{
				List<Integer> copyNumbers2 = new ArrayList<>(copyNumbers);
				copyNumbers2.remove(0);
				for (int k : copyNumbers2)
				{
					if (i + j + k == targetSum)
					{
						int product = i * j * k;
						System.out.println("i = " + i);
						System.out.println("j = " + j);
						System.out.println("k = " + k);
						System.out.println("ijk = " + product);
						return product;
					}
				}
			}
		}
		throw new Exception("No triplet of numbers that sum to " + targetSum + "found.");
	}
}
