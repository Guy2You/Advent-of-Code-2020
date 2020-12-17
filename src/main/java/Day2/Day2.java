package Day2;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day2
{
	private static List<String> passwordList = new ArrayList<>();

	public static void main(String[] args) throws FileNotFoundException
	{
		Path inputPath = Paths.get(String.format("src%1$smain%1$sjava%1$sDay2%1$sinput.txt", File.separator));
		File inputFile = new File(inputPath.toUri());
		Scanner scanner = new Scanner(inputFile);

		while (scanner.hasNextLine())
		{
			passwordList.add(scanner.nextLine());
		}

		System.out.println("valid = " + part1());
		System.out.println("valid = " + part2());
	}

	public static int part1()
	{
		int numValid = 0;
		Pattern lowerBoundPattern = Pattern.compile("[0-9]+-");
		Pattern upperBoundPattern = Pattern.compile("-[0-9]+");
		Pattern characterPattern = Pattern.compile(" [a-zA-Z]:");
		Pattern passwordPattern = Pattern.compile(": [a-zA-Z]+");
		for (String s : passwordList)
		{
			Matcher lowerBoundMatcher = lowerBoundPattern.matcher(s);
			Matcher upperBoundMatcher = upperBoundPattern.matcher(s);
			Matcher characterMatcher = characterPattern.matcher(s);
			Matcher passwordMatcher = passwordPattern.matcher(s);
			lowerBoundMatcher.find();
			upperBoundMatcher.find();
			characterMatcher.find();
			passwordMatcher.find();
			int lowerBound = Integer.parseInt(s.substring(lowerBoundMatcher.start(), lowerBoundMatcher.end() - 1));
			int upperBound = Integer.parseInt(s.substring(upperBoundMatcher.start() + 1, upperBoundMatcher.end()));
			char character = s.charAt(characterMatcher.start() + 1);
			String password = s.substring(passwordMatcher.start() + 2, passwordMatcher.end());

			int numCharacter = 0;
			for (char c : password.toCharArray())
			{
				if (character == c) numCharacter++;
			}
			if (numCharacter >= lowerBound && numCharacter <= upperBound) numValid++;
		}
		return numValid;
	}

	public static int part2()
	{
		int numValid = 0;
		Pattern position1Pattern = Pattern.compile("[0-9]+-");
		Pattern position2Pattern = Pattern.compile("-[0-9]+");
		Pattern characterPattern = Pattern.compile(" [a-zA-Z]:");
		Pattern passwordPattern = Pattern.compile(": [a-zA-Z]+");
		for (String s : passwordList)
		{
			Matcher position1Matcher = position1Pattern.matcher(s);
			Matcher position2Matcher = position2Pattern.matcher(s);
			Matcher characterMatcher = characterPattern.matcher(s);
			Matcher passwordMatcher = passwordPattern.matcher(s);
			position1Matcher.find();
			position2Matcher.find();
			characterMatcher.find();
			passwordMatcher.find();
			int position1 = Integer.parseInt(s.substring(position1Matcher.start(), position1Matcher.end() - 1)) - 1;
			int position2 = Integer.parseInt(s.substring(position2Matcher.start() + 1, position2Matcher.end())) - 1;
			char character = s.charAt(characterMatcher.start() + 1);
			String password = s.substring(passwordMatcher.start() + 2, passwordMatcher.end());

			char c1;
			char c2;
			try
			{
				c1 = password.charAt(position1);
			}
			catch (StringIndexOutOfBoundsException e)
			{
				continue;
			}
			try
			{
				c2 = password.charAt(position2);
			}
			catch (StringIndexOutOfBoundsException e)
			{
				if (c1 == character) numValid++;
				continue;
			}
			if ((c1 == character || c2 == character) && c1 != c2) numValid++;
		}
		return numValid;
	}
}