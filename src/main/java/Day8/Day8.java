package Day8;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Day8
{
	private static List<Map.Entry<String, Integer>> instructions;

	public static void main(String[] args) throws Exception
	{
		Path inputPath = Paths.get(String.format("src%1$smain%1$sjava%1$sDay8%1$sinput.txt", File.separator));
		File inputFile = new File(inputPath.toUri());
		parseInstructions(inputFile);

		System.out.println("Acc = " + part1());
		System.out.println("Acc = " + part2());
	}

	private static int part1()
	{
		return terminates(instructions)[1];
	}

	private static int part2() throws Exception
	{
		int accumulatorValue = 0;
		List<Map.Entry<String, Integer>> terminatingInstructions;
		for (int i = 0; i < instructions.size(); i++)
		{
			terminatingInstructions = new ArrayList<>(instructions);
			if (instructions.get(i).getKey().equals("jmp"))
			{
				terminatingInstructions.set(i, new AbstractMap.SimpleEntry<>("nop", instructions.get(i).getValue()));
				int[] ti = terminates(terminatingInstructions);
				if (ti[0] == 1) return ti[1];
			}
			else if (instructions.get(i).getKey().equals("nop"))
			{
				terminatingInstructions.set(i, new AbstractMap.SimpleEntry<>("jmp", instructions.get(i).getValue()));
				int[] ti = terminates(terminatingInstructions);
				if (ti[0] == 1) return ti[1];
			}
		}
		throw new Exception("Didn't find terminating solution");
	}

	private static void parseInstructions(File input) throws FileNotFoundException
	{
		instructions = new ArrayList<>();
		Scanner scanner = new Scanner(input);
		while (scanner.hasNextLine())
		{
			String line = scanner.nextLine();
			instructions.add(new AbstractMap.SimpleEntry<>(line.substring(0, 3), Integer.parseInt(line.substring(4))));
		}
	}

	private static int[] terminates(List<Map.Entry<String, Integer>> instructions)
	{
		int accumulator = 0;
		int currentLine = 0;
		List<Integer> linesExecuted = new ArrayList<>();
		while (!linesExecuted.contains(currentLine))
		{
			linesExecuted.add(currentLine);
			if (currentLine >= instructions.size()) return new int[]{1, accumulator};
			if (instructions.get(currentLine).getKey().equals("jmp"))
			{
				currentLine += instructions.get(currentLine).getValue();
			}
			else if (instructions.get(currentLine).getKey().equals("acc"))
			{
				accumulator += instructions.get(currentLine).getValue();
				currentLine += 1;
			}
			else
			{
				currentLine++;
			}
		}
		return new int[]{0, accumulator};
	}
}
