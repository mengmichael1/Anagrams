import java.io.*;
import java.util.*;
// normally i only input what i need, but i dont have time to figure out exactly right now

public class Anagrams {

	// hash by length
	// anagrams if same num of char and same char

	// idea: map dictWord to charArray? 
	// e.g. bate will have value of 1 in arr[b], arr[a], arr[t], arr[e]

	// mapping length to list of words that have that length
	private HashMap<Integer, ArrayList<String>> lengthToWords; 

	// mapping words to hashmap, that counts how many of each char it has
	// HashMap<Character, Integer> maps character to # of char occurences
		// i could just use an array too, but ill need to look up ascii values for chars
	private HashMap<String, HashMap<Character, Integer>> wordToCharCounts;

	public Anagrams() {
		this.lengthToWords = new HashMap<>();
		this.wordToCharCounts = new HashMap<>();
	}

	// add dictionary word to my hashmaps
	public void addWord(String word) {

		// add to lengthToWords
		int length = word.length();

		if (lengthToWords.containsKey(length)) {
			lengthToWords.get(length).add(word);

		} else {
			ArrayList<String> newWordList = new ArrayList<>();
			newWordList.add(word);
			lengthToWords.put(length, newWordList);
		}

		// add to word to char arr
		HashMap<Character, Integer> charCounts = new HashMap<>();
		for (int i = 0; i < length; i++) {
			char charAt = word.charAt(i); // getting char of word
			
			if (charCounts.containsKey(charAt)) {
				charCounts.put(charAt, charCounts.get(charAt) + 1);
			} else {
				charCounts.put(charAt, 1);
			}
		}

		wordToCharCounts.put(word, charCounts);
	}

	public String getAnagrams(String input) {

		// first, get length of word, narrowing down my search for anagrams
		// next, convert input word into array of char counts (or in my case, HashMap)
		// for each word candidate, check if char arrays match
		// to address case-insensitive, 
		//	count(lowercaseChar) + count(uppercaseChar) should be same

		ArrayList<String> toReturn = new ArrayList<>();

		int length = input.length();

		// candidate list of words 
		ArrayList<String> wordCandidates = lengthToWords.get(length);

		HashMap<Character, Integer> charCounts = new HashMap<>();
		for (int i = 0; i < length; i++) {
			char charAt = input.charAt(i); // getting char of word
			
			if (charCounts.containsKey(charAt)) {
				charCounts.put(charAt, charCounts.get(charAt) + 1);
			} else {
				charCounts.put(charAt, 1);
			}
		}	

		// for each cand word, compare with input word char array
		// iterate thru input word, look at char counts for each char
		for (String wordCand : wordCandidates) {
			boolean isAnag = true;

			// if count for ANY character is diff, isAnag is FALSE
			for (int i = 0; i < length; i++) {
				char c = input.charAt(i);

				//(a < b) ? a : b;
				// if i am getting paid to write code, i use ternary operators less often :P 
				int wordCandCharLower = (wordToCharCounts.get(wordCand).get(Character.toLowerCase(c)) != null) 
											? wordToCharCounts.get(wordCand).get(Character.toLowerCase(c)) : 0;
				int wordCandCharUpper = (wordToCharCounts.get(wordCand).get(Character.toUpperCase(c)) != null) 
											? wordToCharCounts.get(wordCand).get(Character.toUpperCase(c)) : 0;

				int wordInputCharLower = (charCounts.get(Character.toLowerCase(c)) != null) 
											? charCounts.get(Character.toLowerCase(c)) : 0;

				int wordInputCharUpper = (charCounts.get(Character.toUpperCase(c)) != null) 
											? charCounts.get(Character.toUpperCase(c)) : 0;

				if ( (wordCandCharUpper + wordCandCharLower) != (wordInputCharLower + wordInputCharUpper) ){
					isAnag = false;
				}
			}

			if (isAnag) {
				toReturn.add(wordCand);
			}
		}

		Collections.sort(toReturn); // natural sorting order

		StringBuilder stringBuilder = new StringBuilder();

		for (String item : toReturn) {
			stringBuilder.append(item + " ");
		}
		
		return stringBuilder.toString();
	}


    public static void main(String[] args) {

    	Anagrams anagramsProgram = new Anagrams();

        String textDictFile = args[0]; 
        //System.out.println(textDictFile);

        // read in the words from txt file
        try {
        	Scanner reader = new Scanner(new FileInputStream(args[0]));

        	while (reader.hasNextLine()) {
        		String dictWord = reader.next();
        		
        		//System.out.println(dictWord); // can read in words correctly! 
        		anagramsProgram.addWord(dictWord);
        	}

        	reader.close();
        } catch (FileNotFoundException e) {
        	e.printStackTrace();
        }


        // take in user input
        try {
        	Scanner scanner = new Scanner(System.in);
        	while(true) {
        		System.out.println("Please input word: ");
        		String userInput = scanner.nextLine();
        		//System.out.println(userInput);

        		System.out.println(anagramsProgram.getAnagrams(userInput));
        	}
        } catch(IllegalStateException | NoSuchElementException e) {
            e.printStackTrace();
        }

    }
}
