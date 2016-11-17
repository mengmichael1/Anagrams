Michael Meng

(0) Implementation details:
- Read in all words from dictionary txt 
- Hash words by length, have mapping from wordLength to list of words that are that length
- also Map (Words) -> (Map of Char to # occurances)
- Read in user input
- compare inputWord with dictWord, based on # of char occurances

(1) Running Time:

Assuming HashMap get/put operations are constant time (amortized)

n = # words in .txt file
m = avg length of words 

adding 1 word via addWord: O(m), need to iterate thru all char
adding all words via addWord (when reading in .txt file): O(nm)

taking in a word input:
- O(m) to get char counts
- since i hash words by length, WORST CASE is that all words are same length...
	- WORST CASE is identifying ALL dictionary words as poss candidates: O(n) time to compare candidates with input word 

(2) Memory:

private HashMap<Integer, ArrayList<String>> lengthToWords; -> roughly O(n)

private HashMap<String, HashMap<Character, Integer>> wordToCharCounts; 
->  also roughly O(n), n words will be mapped to their HashMap<Character, Integer> (which maps character to # occurances)



