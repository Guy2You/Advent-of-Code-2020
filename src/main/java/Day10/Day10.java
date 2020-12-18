package Day10;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Day10
{
	private static List<Integer> adapters;

	public static void main(String[] args) throws Exception
	{
		Path inputPath = Paths.get(String.format("src%1$smain%1$sjava%1$sDay10%1$sinput.txt", File.separator));
		File inputFile = new File(inputPath.toUri());
		Scanner scanner = new Scanner(inputFile);
		adapters = new ArrayList<>();
		while (scanner.hasNextLine())
		{
			adapters.add(scanner.nextInt());
		}
		adapters.sort(Comparator.comparingInt(Integer::intValue));

		System.out.println("Product: " + part1());
		System.out.println("Distinct: " + part2());
	}

	public static int part1()
	{
		int jolt1 = 0;
		int jolt3 = 0;
		int currentJoltage = 0;
		for (int jolt : adapters)
		{
			int diff = jolt - currentJoltage;
			currentJoltage = jolt;
			if (diff == 1) jolt1++;
			if (diff == 3) jolt3++;
		}
		jolt3++;
		return jolt3 * jolt1;
	}

	public static long part2()
	{
		Map<Integer, Long> distToGoal = new HashMap<>();
		List<Integer> adaptersDesc = new ArrayList<>(adapters);
		Collections.reverse(adaptersDesc);
		adaptersDesc.add(0);
		distToGoal.put(adaptersDesc.get(0), 1L);
		for (int a : adaptersDesc.subList(1, adaptersDesc.size()))
		{
			long sum = 0;
			for (int i = 1; i < 4; i++)
			{
				if (distToGoal.containsKey(a + i)) sum += (distToGoal.get(a + i));
			}
			distToGoal.put(a, sum);
		}
		return distToGoal.get(0);
	}
}
