
public class SpellChecker {


	public static void main(String[] args) {
		String word = args[0];
		int threshold = Integer.parseInt(args[1]);
		String[] dictionary = readDictionary("dictionary.txt");
		String correction = spellChecker(word, threshold, dictionary);
		System.out.println(correction);
		
	}

	public static String tail(String str) {
		if (str.length() == 1) {
			return "";
		}
		return str.substring(1);
	}

	public static int levenshtein(String word1, String word2) {
		// number of changes when 1 word is an empty string
		if (word2.length() == 0) {
			return word1.length();
		} else if (word1.length() == 0) {
			return word2.length();
		} 
		// ensures both words are lower case
		word1 = word1.toLowerCase();
		word2 = word2.toLowerCase();
		if (word1.substring(0, 1).equals(word2.substring(0, 1))) {
			return levenshtein(tail(word1), tail(word2));
		}
		// checks minimum changes between deletion insertion and replacement
		int minChanges = Math.min(levenshtein(tail(word1), word2), levenshtein(word1, tail(word2)));
		return 1 + Math.min(minChanges, levenshtein(tail(word1), tail(word2)));
	}

	public static String[] readDictionary(String fileName) {
		String[] dictionary = new String[3000];

		In in = new In(fileName);

		for (int i = 0; i < dictionary.length && !in.isEmpty(); i++) {
			dictionary[i] = in.readLine();
		}

		return dictionary;
	}

	public static String spellChecker(String word, int threshold, String[] dictionary) {
		int minEditDistance = levenshtein(word, dictionary[0]);
		int mostSimilarIndex = 0;
		for (int i = 1; i < dictionary.length; i++) {
			if (minEditDistance > levenshtein(word, dictionary[i])) {
				minEditDistance = levenshtein(word, dictionary[i]);
				mostSimilarIndex = i;
			}
		}
		if (minEditDistance > threshold) {
			return word;
		} else {
			return dictionary[mostSimilarIndex];
		}
	}

}
