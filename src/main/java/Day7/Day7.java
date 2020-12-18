package Day7;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day7
{
	private static List<String> bagSpecs;

	public static void main(String[] args) throws FileNotFoundException
	{
		Path inputPath = Paths.get(String.format("src%1$smain%1$sjava%1$sDay7%1$sinput.txt", File.separator));
		File inputFile = new File(inputPath.toUri());
		Scanner scanner = new Scanner(inputFile);
		bagSpecs = new ArrayList<>();
		while (scanner.hasNextLine())
		{
			bagSpecs.add(scanner.nextLine());
		}

		System.out.println("Contain: " + part1());
		System.out.println("Contain: " + part2());
	}

	private static int part1()
	{
		Map<String, List<String>> containingBags = new HashMap<>();
		Pattern superBagPattern = Pattern.compile("[a-zA-Z ]+bags contain");
		Pattern subBagPattern = Pattern.compile("([0-9]+ [a-zA-Z ]+bag)|(no other bag)");
		for (String s : bagSpecs)
		{
			Matcher superBagMatcher = superBagPattern.matcher(s);
			Matcher subBagMatcher = subBagPattern.matcher(s);
			superBagMatcher.find();
			String superBag = s.substring(superBagMatcher.start(), superBagMatcher.end() - 13);
			List<String> subBags = new ArrayList<>();
			while (subBagMatcher.find())
			{
				String bag = subBagMatcher.group();
				if (!bag.equals("no other bag"))
				{
					bag = bag.substring(2, bag.length() - 4);
					subBags.add(bag);
				}
			}
			containingBags.put(superBag, subBags);
		}
		int num = 0;
		for (Map.Entry<String, List<String>> b : containingBags.entrySet())
		{
			if (containsGold(b.getKey(), containingBags)) num++;
		}
		return num;
	}

	private static int part2()
	{
		Map<String, Map<String, Integer>> containingBags = new HashMap<>();
		Pattern superBagPattern = Pattern.compile("[a-zA-Z ]+bags contain");
		Pattern subBagPattern = Pattern.compile("([0-9]+ [a-zA-Z ]+bag)|(no other bag)");
		for (String s : bagSpecs)
		{
			Matcher superBagMatcher = superBagPattern.matcher(s);
			Matcher subBagMatcher = subBagPattern.matcher(s);
			superBagMatcher.find();
			String superBag = s.substring(superBagMatcher.start(), superBagMatcher.end() - 13);
			Map<String, Integer> subBags = new HashMap<>();
			while (subBagMatcher.find())
			{
				String bag = subBagMatcher.group();
				int numBag = 0;
				if (!bag.equals("no other bag"))
				{
					numBag = Integer.parseInt(bag.substring(0, 1));
					bag = bag.substring(2, bag.length() - 4);
					subBags.put(bag, numBag);
				}
			}
			containingBags.put(superBag, subBags);
		}
		return numInside("shiny gold", containingBags);
	}

	private static boolean containsGold(String currentBag, Map<String, List<String>> containers)
	{
		if (containers.get(currentBag).contains("shiny gold")) return true;
		if (containers.get(currentBag).isEmpty()) return false;
		return containers.get(currentBag).stream().anyMatch(bag -> containsGold(bag, containers));
	}

	private static int numInside(String outerBag, Map<String, Map<String, Integer>> containers)
	{
		if (containers.get(outerBag).isEmpty()) return 0;
		int num = 0;
		for (Map.Entry<String, Integer> innerBags : containers.get(outerBag).entrySet())
		{
			num += innerBags.getValue();
			num += (numInside(innerBags.getKey(), containers) * innerBags.getValue());
		}
		return num;
	}
}
