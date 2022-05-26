/** 
 * This class contains the methods used for conducting a simple sentiment analysis.
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;

public class Original {

	private static class Word implements Comparable<Word> {
		private String word;
		private int sum;
		private int count;

		public Word(String word) {
			this.word = word;
			this.sum = 0;
			this.count = 0;
		}
		
		public String getWord() {
			return word;
		}

		public int getSum() {
			return sum;
		}
		
		public int getCount() {
			return count;
		}

		public void calculateSum(int number) {
			sum = sum + number;
			count++;
		}

		public double calculateWordScore() {
			return (double) (sum / count);
		}

		@Override
		public int hashCode() {
			return word.hashCode();
		}

		@Override
		public int compareTo(Word other) {
			return word.compareTo(other.word);
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof Word == false) {
				return false;
			}
			Word other = (Word) obj;
			return word.equals(other.word);
		}

	}
	
	/**
	 * This method reads sentences from the input file, creates a Sentence object
	 * for each, and returns a Set of the Sentences.
	 * 
	 * @param filename Name of the input file to be read
	 * @return Set containing one Sentence object per sentence in the input file
	 */
	public static Set<Sentence> readFile(String filename) {
		/*
		 * Implement this method in Part 1
		 */
		Set<Sentence> sentences = new HashSet<>();
		try {
			File file = new File(filename); 
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
	
			String line = "";
	
			while ((line = br.readLine()) != null) {
				String[] tokens = line.split(" ", 2);
				int score = 0;
				try {
					score = Integer.parseInt(tokens[0]);
				}
				catch (NumberFormatException nfe) {
					continue;
				}
				String text = tokens[1];
				if (score < -2 || score > 2 || isAnInt(score) == false ) {
					continue;
				}
				if (text == null) {
					continue;
				}
				Sentence newSentence = new Sentence(score, text);
				sentences.add(newSentence);
			} 
		}
		catch (IOException ioe) {
			System.out.println("Invalid data"); // throw IllegalArgumentException;
		}
		return sentences;
	}

	/**
	 * This method calculates the weighted average for each word in all the Sentences.
	 * This method is case-insensitive and all words should be stored in the Map using
	 * only lowercase letters.
	 * 
	 * @param sentences Set containing Sentence objects with words to score
	 * @return Map of each word to its weighted average
	 */
	public static Map<String, Double> calculateWordScores(Set<Sentence> sentences) {
		/*
		 * Implement this method in Part 2
		 */

		Map<String, Double> mapping = new HashMap<>();
		Set<Word> wordSet = new HashSet<Word>(); // A hash set that contains all the words

		for (Sentence sentence : sentences) {
			// the Sentence object in the Set is null
			if (sentence == null) {
				continue;
			}
			// the score of a Sentence object in the Set is outside the range [-2, 2]
			if (sentence.getScore() < -2 || sentence.getScore() > 2) {
				continue;
			}
			// the input Set of Sentences is null or the input Set of Sentences is empty
			if (sentences == null || sentences.isEmpty()) {
				mapping = Collections.emptyMap();
			}
			String text = sentence.getText();
			StringTokenizer word = new StringTokenizer(text);
			// the text of a Sentence object in the Set is null
			if (word == null) {
			continue;
			}
			// the text of a Sentence object in the Set is empty
			if (text.isEmpty()) {
				continue;
			}
			while (word.hasMoreTokens()) {
				String newWord = word.nextToken().toLowerCase();
				Word w = new Word(newWord);
				if (wordSet.contains(w)) {
					for (Word eachWord : wordSet) {
						if (w == eachWord) {
							w = eachWord;
							break;
						}
					}
				}
				w.calculateSum(sentence.getScore()); // calculate sum
				wordSet.add(w); // add this word to the wordset
			}
		}
		// produce mapping for final scores for each word
		for (Word oneWord : wordSet) {
			if (oneWord == null || oneWord.getWord().isEmpty()) {
				continue;
			}
			mapping.put(oneWord.getWord(), oneWord.calculateWordScore());
		}
		return mapping;
	}
	
	/**
	 * This method determines the sentiment of the input sentence using the average of the
	 * scores of the individual words, as stored in the Map.
	 * This method is case-insensitive and all words in the input sentence should be
	 * converted to lowercase before searching for them in the Map.
	 * 
	 * @param wordScores Map of words to their weighted averages
	 * @param sentence Text for which the method calculates the sentiment
	 * @return Weighted average scores of all words in input sentence
	 */
	public static double calculateSentenceScore(Map<String, Double> wordScores, String sentence) {
		/*
		 * Implement this method in Part 3
		 */
		// The input Map is null || The input Map is empty
		if (wordScores == null | wordScores.isEmpty()) {
			return 0;
		}
		// The input sentence is null || The input sentence is empty
		if (sentence == null || sentence.isEmpty()) {
			return 0;
		}
		int numWords = 0;
		double sentenceScore = 0;
		StringTokenizer str = new StringTokenizer(sentence);
		while (str.hasMoreTokens()) {
			String eachString = str.nextToken().toLowerCase();
			if (wordScores.containsKey(eachString)) {
				sentenceScore = sentenceScore + wordScores.get(eachString);
			}
			numWords++;
		}
		return sentenceScore / (double) numWords;
	}

	public static boolean isAnInt(Object o) {
		return o instanceof Integer;
	}
	
	public static void main(String[] args) {
		/*
		 * Implement this method in Part 4
		 */
		String file = args[0];
		// If the name of the input file is not provided as a runtime argument
		// if (file = null) {
			
		// }
		Set<Sentence> sentences = readFile(file);
		Map<String, Double> map = calculateWordScores(sentences);
		System.out.print("Enter a sentence: ");
		Scanner sc = new Scanner(System.in);
		String input = "";
		while(sc.hasNextLine()){
			input = sc.nextLine();
			if (input.equals("quit")) {
				System.exit(0);;
			}
			System.out.println("Result: " + calculateSentenceScore(map, input));
			System.out.println("Enter a sentence: ");
		}
		sc.close();
	}

}