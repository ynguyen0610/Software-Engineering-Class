import org.junit.Assert.*; 
import org.junit.Test;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

public class CalculateSentenceScoreTest {

    @Test // Test output
    public void testCorrectOutput() {
        Map<String, Double> wordScores = new HashMap<>();
        wordScores.put("dogs", 1.5);
        wordScores.put("are", 1.5);
        wordScores.put("cute", 3.0);
        String sentence = "dogs are cute";
        double result = Analyzer.calculateSentenceScore(wordScores, sentence);
        assertTrue(2.0 == result);
    }

    @Test // Test to see if input Map is null
    public void testNullInputMap() {
        Map<String, Double> wordScores = null;
        String sentence = "dogs are cute";
        double result = Analyzer.calculateSentenceScore(wordScores, sentence);
        assertTrue(0.0 == result);
    }

    @Test // Test to see if input Map is empty
    public void testEmptyInputMap() {
        Map<String, Double> wordScores = new HashMap<>();
        String sentence = "dogs are cute";
        double result = Analyzer.calculateSentenceScore(wordScores, sentence);
        assertTrue(0.0 == result);
    }

    @Test // Test to see if input sentence is null
    public void testNullInputSentence() {
        Map<String, Double> wordScores = new HashMap<>();
        wordScores.put("dogs", 1.0);
        wordScores.put("are", 1.0);
        wordScores.put("cute", 2.0);
        double result = Analyzer.calculateSentenceScore(wordScores, null);
        assertTrue(0.0 == result);
    }

    @Test // Test to see if input sentence is empty
    public void testEmptyInputSentence() {
        Map<String, Double> wordScores = new HashMap<>();
        wordScores.put("dogs", 1.0);
        wordScores.put("are", 1.0);
        wordScores.put("cute", 3.0);
        String sentence = "";
        double result = Analyzer.calculateSentenceScore(wordScores, sentence);
        assertTrue(0.0 == result);
    }

    @Test // Test to see if input sentence has no valid words
    public void testNoValidWords() {
        Map<String, Double> wordScores = new HashMap<>();
        wordScores.put("dogs", 1.0);
        wordScores.put("are", 1.0);
        wordScores.put("cute", 2.0);
        String sentence = "5dogs %are $cute";
        double result = Analyzer.calculateSentenceScore(wordScores, sentence);
        assertTrue(0.0 == result);
    }

    @Test // Test to see if input sentence has a few invalid words
    public void testSomeInvalidWords() {
        Map<String, Double> wordScores = new HashMap<>();
        wordScores.put("dogs", 1.0);
        wordScores.put("are", 1.0);
        wordScores.put("cute", 2.0);
        String sentence = "dogs are $cute";
        double result = Analyzer.calculateSentenceScore(wordScores, sentence);
        assertTrue(1.0 == result);
    }  

    @Test // Test if the word in the sentence is not in the map
    public void testWordNotInMap() {
        Map<String, Double> wordScores = new HashMap<>();
        wordScores.put("dogs", 1.5);
        wordScores.put("are", 1.5);
        wordScores.put("cute", 3.0);
        String sentence = "dogs are crazy cute";
        double result = Analyzer.calculateSentenceScore(wordScores, sentence);
        assertTrue(1.5 == result);
    }  

    @Test // Test if the word in the sentence starts with an uppercase letter
    public void testUppercaseLetter() {
        Map<String, Double> wordScores = new HashMap<>();
        wordScores.put("dogs", 1.5);
        wordScores.put("are", 1.5);
        wordScores.put("cute", 3.0);
        String sentence = "Dogs are Cute";
        double result = Analyzer.calculateSentenceScore(wordScores, sentence);
        assertTrue(2.0 == result);
    }  

    @Test // Test if a word appears twice in the input sentence
    public void testWordCount() {
        Map<String, Double> wordScores = new HashMap<>();
        wordScores.put("dogs", 1.5);
        wordScores.put("are", 1.5);
        wordScores.put("cute", 3.0);
        String sentence = "dogs dogs are cute";
        double result = Analyzer.calculateSentenceScore(wordScores, sentence);
        assertTrue(1.875 == result);
    }
}
