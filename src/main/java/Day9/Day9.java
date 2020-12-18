package Day9;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Day9
{
	private static List<Long> numbers;

	public static void main(String[] args) throws Exception
	{
		Path inputPath = Paths.get(String.format("src%1$smain%1$sjava%1$sDay9%1$sinput.txt", File.separator));
		File inputFile = new File(inputPath.toUri());
		Scanner scanner = new Scanner(inputFile);
		numbers = new ArrayList<>();
		while (scanner.hasNextLine())
		{
			numbers.add(scanner.nextLong());
		}

		System.out.println("Inavlid: " + part1());
		System.out.println("Sum: " + part2());
	}

	private static long part1() throws Exception
	{
		int preambleLength = 25;
		int numRange = 25;
		for (int i = preambleLength; i < numbers.size(); i++)
		{
			if (!isValid(i, numRange)) return numbers.get(i);
			//if numbers(i) is valid i++ else return numbers(i)
		}
		throw new Exception("No invalid numbers found.");
	}

	private static long part2() throws Exception
	{
		long target = part1();
		for (int i = 0; i < numbers.size(); i++)
		{
			try
			{
				List<Long> sumList = contiguousList(numbers.subList(i, numbers.size()), target);
				long min = sumList.stream().min(Comparator.comparingLong(Long::longValue)).get();
				long max = sumList.stream().max(Comparator.comparingLong(Long::longValue)).get();
				return min + max;
			}
			catch (Exception ignored)
			{

			}
		}
		throw new Exception("Did not find contiguous sum list.");
	}

	private static List<Long> contiguousList(List<Long> numbers, long target) throws Exception
	{
		long newTarget = target - numbers.get(0);
		if (newTarget == 0) return numbers.subList(0, 1);
		if (newTarget < 0) throw new Exception("No contiguous sum here.");
		List<Long> contiguousList = new ArrayList<>(numbers.subList(0, 1));
		contiguousList.addAll(contiguousList(numbers.subList(1, numbers.size()), newTarget));
		return contiguousList;
	}

	private static boolean isValid(int index, int numRange)
	{
		List<Long> sumNumbers = numbers.subList(index - numRange, index);
		for (Long num1 : sumNumbers)
		{
			List<Long> otherSumNumbers = new ArrayList<>(sumNumbers);
			otherSumNumbers.remove(num1);
			for (long num2 : otherSumNumbers)
			{
				if (num1 + num2 == numbers.get(index)) return true;
			}
		}
		return false;
	}
}
