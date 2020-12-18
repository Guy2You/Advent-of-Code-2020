package Day6;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Day6
{
	private static List<String> groupAnswers;

	public static void main(String[] args) throws FileNotFoundException
	{
		Path inputPath = Paths.get(String.format("src%1$smain%1$sjava%1$sDay6%1$sinput.txt", File.separator));
		File inputFile = new File(inputPath.toUri());
		Scanner scanner = new Scanner(inputFile);
		scanner.useDelimiter("\r\n\r\n");
		groupAnswers = new ArrayList<>();
		while (scanner.hasNext())
		{
			groupAnswers.add(scanner.next());
		}

		System.out.println("Sum: " + part1());
		System.out.println("Sum: " + part2());
	}

	private static int part1()
	{
		int sum = 0;
		for (String answers : groupAnswers)
		{
			List<Character> inAnswers = new ArrayList<>();
			for (Character c : answers.toCharArray())
			{
				if (Character.isLowerCase(c) && !inAnswers.contains(c)) inAnswers.add(c);
			}
			sum += inAnswers.size();
		}
		return sum;
	}

	private static int part2()
	{
		int sum = 0;
		for (String answers : groupAnswers)
		{
			String[] answerArray = answers.split("\r\n");
			Set<Character> commonAnswers = new HashSet<>();
			for (char c : answerArray[0].toCharArray())
			{
				commonAnswers.add(c);
			}
			for (String answer : answerArray)
			{
				Set<Character> answerChars = new HashSet<>();
				for (char c : answer.toCharArray())
				{
					answerChars.add(c);
				}
				commonAnswers.retainAll(answerChars);
			}
			sum += commonAnswers.size();
		}
		return sum;
	}
}
