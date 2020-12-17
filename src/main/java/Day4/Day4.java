package Day4;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day4
{
	private static final String[] VALID_CREDENTIALS = {"byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid", "cid"};
	private static List<Map<String, String>> credentialsList;

	public static void main(String[] args) throws Exception
	{
		Path inputPath = Paths.get(String.format("src%1$smain%1$sjava%1$sDay4%1$sinput.txt", File.separator));
		File inputFile = new File(inputPath.toUri());
		Scanner scanner = new Scanner(inputFile);
		scanner.useDelimiter("\r\n\r\n");
		credentialsList = new ArrayList<>();
		while (scanner.hasNext())
		{
			credentialsList.add(buildCredentialMap(scanner.next()));
		}

		System.out.println("Valid: " + part1());
		System.out.println("Valid: " + part2());
	}

	public static int part1()
	{
		int numValid = 0;
		String[] requiredCredentials = Arrays.stream(VALID_CREDENTIALS).filter(c -> !c.equals("cid")).toArray(String[]::new);
		for (Map<String, String> credentials : credentialsList)
		{
			boolean valid = true;
			for (String reqCred : requiredCredentials)
			{
				if (!credentials.containsKey(reqCred))
				{
					valid = false;
					break;
				}
			}
			if (valid) numValid++;
		}
		return numValid;
	}

	public static int part2()
	{
		int numValid = 0;
		String[] requiredCredentials = Arrays.stream(VALID_CREDENTIALS).filter(c -> !c.equals("cid")).toArray(String[]::new);
		for (Map<String, String> credentials : credentialsList)
		{
			boolean valid = true;
			for (String reqCred : requiredCredentials)
			{
				if (!valid || !credentials.containsKey(reqCred))
				{
					valid = false;
					break;
				}
				String val = credentials.get(reqCred);
				Pattern heightPattern = Pattern.compile("([0-9]+cm)|([0-9]+in)");
				Pattern hairColourPattern = Pattern.compile("#[0-9a-f]{6}");
				String[] eyeColours = {"amb", "blu", "brn", "gry", "grn", "hzl", "oth"};
				Pattern passportIdPattern = Pattern.compile("[0-9]{9}");
				switch (reqCred)
				{
					case "byr":
						if (!(val.length() == 4 && Integer.parseInt(val) >= 1920 && Integer.parseInt(val) <= 2002))
						{
							valid = false;
						}
						break;
					case "iyr":
						if (!(val.length() == 4 && Integer.parseInt(val) >= 2010 && Integer.parseInt(val) <= 2020))
						{
							valid = false;
						}
						break;
					case "eyr":
						if (!(val.length() == 4 && Integer.parseInt(val) >= 2020 && Integer.parseInt(val) <= 2030))
						{
							valid = false;
						}
						break;
					case "hgt":
						Matcher heightMatcher = heightPattern.matcher(val);
						if (!heightMatcher.matches())
						{
							valid = false;
							break;
						}
						int height = Integer.parseInt(val.substring(0, heightMatcher.end() - 2));
						if (val.endsWith("cm"))
						{
							if (!(height >= 150 && height <= 193)) valid = false;
						}
						else
						{
							if (!(height >= 59 && height <= 76)) valid = false;
						}
						break;
					case "hcl":
						Matcher hairColourMatcher = hairColourPattern.matcher(val);
						if (!hairColourMatcher.matches()) valid = false;
						break;
					case "ecl":
						if (Arrays.stream(eyeColours).noneMatch(c -> c.equals(val))) valid = false;
						break;
					case "pid":
						Matcher passportIdMatcher = passportIdPattern.matcher(val);
						if (!passportIdMatcher.matches()) valid = false;
						break;
				}
			}
			if (valid) numValid++;
		}
		return numValid;
	}

	public static Map<String, String> buildCredentialMap(String credentials) throws Exception
	{
		Pattern credentialPattern = Pattern.compile("[a-z]+:[#a-zA-Z0-9]+");
		Matcher credentialMatcher = credentialPattern.matcher(credentials);
		HashMap<String, String> credentialMap = new HashMap<String, String>();
		while (credentialMatcher.find())
		{
			String cred = (credentialMatcher.group());
			if (Arrays.stream(VALID_CREDENTIALS).anyMatch(c -> c.equals(cred.substring(0, 3))))
			{
				credentialMap.put(cred.substring(0, 3), cred.substring(4));
			}
			else
			{
				throw new Exception("Encountered unexpected credential: " + cred.substring(0, 3));
			}
		}
		return credentialMap;
	}
}
