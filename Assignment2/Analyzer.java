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

public class Analyzer {
	
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
		// Check if the input filename is null
		if (filename == null) {
			throw new IllegalArgumentException();
		}
		Set<Sentence> sentences = new HashSet<>();
		try {
			File file = new File(filename); 
			// Check if the file does not exist
			if (file.exists() == false) {
				throw new IllegalArgumentException();
			}
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
				// Check to skip a line that starts with an int outside the range [-2, 2] or a number that is not an integer
				if (score < -2 || score > 2 || isAnInt(score) == false ) {
					continue;
				}
				// Check to skip a line that starts with a number but is not followed by any text
				if (text == null) {
					continue;
				}
				Sentence newSentence = new Sentence(score, text);
				sentences.add(newSentence);
			} 
		}
		catch (IOException ioe) {
			throw new IllegalArgumentException();
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
		// Check if the input Set of Sentences is null or the input Set of Sentences is empty
		if(sentences == null || sentences.isEmpty()){
			return new HashMap<String, Double>();
		}

		Map<String, Double> mapWithScore = new HashMap<>();
		Map<String, Double> mapWithCount = new HashMap<>();

		for (Sentence sentence : sentences) {
			if (sentence == null || sentence.getText() == null || sentence.getText().isEmpty()) {
				continue;
			}
			if (sentence.getScore() < -2 || sentence.getScore() > 2) {
				continue;
			}
			String text = sentence.getText();
			StringTokenizer word = new StringTokenizer(text);
			String newWord = "";
			double defaultScore = sentence.getScore();
			while (word.hasMoreTokens()) {
				newWord = word.nextToken().toLowerCase();
				// Ignore tokens
				if(!newWord.matches("^[a-z]*$")){ 
					continue;
				}
				if (mapWithScore.containsKey(newWord)) {
					mapWithScore.put(newWord, mapWithScore.get(newWord)+(defaultScore));
				}
				else {
					mapWithScore.put(newWord, defaultScore);
				}
				// Add to second map 
				if (mapWithCount.containsKey(newWord)) {
					mapWithCount.put(newWord, mapWithCount.get(newWord) + 1);
				}
				else { 
					mapWithCount.put(newWord, 1.0); 
				}
			}
		}

		for (Map.Entry<String, Double> token1 : mapWithScore.entrySet()) {
			for (Map.Entry<String, Double> token2 : mapWithCount.entrySet()) {
				if (token1.getKey().equals(token2.getKey())) {
					mapWithScore.put(token1.getKey(), mapWithScore.get(token1.getKey())/token2.getValue());
				}
			}
		}
		return mapWithScore;
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
		if (wordScores == null || wordScores.isEmpty() || sentence == null || sentence.isEmpty()) {
			return 0;
		}

		int numWords = 0;
		double sentenceScore = 0.0;
		StringTokenizer str = new StringTokenizer(sentence);
		while (str.hasMoreTokens()) {
			String eachString = str.nextToken().toLowerCase();
			// Ignore tokens
			if (!eachString.matches("^[a-z]*$")){ 
				continue;
			}
			// Check if the input sentence does not contain any valid words
			if (Character.isLetter(eachString.charAt(0))) {
				if (wordScores.containsKey(eachString)) {
					sentenceScore = sentenceScore + wordScores.get(eachString);
				}
				numWords++;
			}
		}
		if (numWords == 0) {
			return 0;
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
		// Check if there is any input file
		if (args.length == 0) {
			System.out.println("No input file");
			System.exit(0);
		}
		String file = args[0];
		Set<Sentence> sentences;
		try {
			sentences = readFile(file);
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
		} catch (IllegalArgumentException e) {
			System.out.println("Bad input file");
		}
	}

}