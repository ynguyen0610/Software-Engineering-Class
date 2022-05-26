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

public class Analyzer2 {
	
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

		Map<String, Double> map1 = new HashMap<>();
		Map<String, Double> map2 = new HashMap<>();

		// calculate scores for each word
		for (Sentence sentence : sentences) {
			String text = sentence.getText();
			StringTokenizer word = new StringTokenizer(text);
			double result = 0;
			String newWord = "";
			double defaultScore = sentence.getScore();
			while (word.hasMoreTokens()) {
				int wordCount = 0;
				// double totalWordScore = 0;
				newWord = word.nextToken().toLowerCase();
				// defaultMap.put(newWord, defaultScore);
				if (map1.containsKey(newWord)) {
					map1.put(newWord, map1.get(newWord)+(defaultScore));
				}
				else {
					map1.put(newWord, defaultScore);
				}

				// second map does math
				if (map2.containsKey(newWord)) {
					double newScore = map1.get(newWord); 
					double totalWordScore = 0;
					totalWordScore += newScore;
					wordCount++;
					result = totalWordScore / wordCount;
					map2.put(newWord, result);
				}
				else { // if Map2 doesn't contain
					wordCount++;
					map2.put(newWord, map1.get(newWord)/wordCount); // count; 
				}
			}
		}
		return map2;
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
		int numWords = 0;
		double sentenceScore = 0.0;
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
		Set<Sentence> sentences = readFile(file);
		Map<String, Double> map = calculateWordScores(sentences);
		System.out.println(map.get("dog"));
		// System.out.print("Enter a sentence: ");
		// Scanner sc = new Scanner(System.in);
		// String input = "";
		// while(sc.hasNextLine()){
		// 	input = sc.nextLine();
		// 	if (input.equals("quit")) {
		// 		System.exit(0);;
		// 	}
		// 	System.out.println("Result: " + calculateSentenceScore(map, input));
		// 	System.out.println("Enter a sentence: ");
		// }
		// sc.close();
	}

}