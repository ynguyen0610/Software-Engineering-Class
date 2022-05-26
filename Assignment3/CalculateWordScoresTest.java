import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class CalculateWordScoresTest {

    @Test // Test final output
    public void testOutput() {
        Set<Sentence> sentences = new HashSet<>();
        Sentence newSentence1 = new Sentence(2, "this dog is cute");
        Sentence newSentence2 = new Sentence(-1, "bad dog");
		sentences.add(newSentence1);
        sentences.add(newSentence2);
        Map<String, Double> result = new HashMap<>();
        result.put("this", 2.0);
        result.put("is", 2.0);
        result.put("dog", 0.5);
        result.put("bad", -1.0);
        result.put("cute", 2.0);
        Map<String, Double> mapWithScore = Analyzer.calculateWordScores(sentences);
        assertEquals(result.size(), mapWithScore.size());
        assertTrue(0.5 == mapWithScore.get("dog"));
    }

    @Test // Test to see if the Sentence object in the Set is null - program should continue
    public void testNullSentenceObject() {
        Set<Sentence> sentences = new HashSet<>();
        Sentence newSentence = null; 
        Sentence newSentence1 = new Sentence(2, "this dog is cute");
        sentences.add(newSentence);
        sentences.add(newSentence1);
        Map<String, Double> result = new HashMap<>();
        result.put("this", 2.0);
        result.put("is", 2.0);
        result.put("dog", 2.0);
        result.put("cute", 2.0);
        Map<String, Double> mapWithScore = Analyzer.calculateWordScores(sentences);
        assertEquals(result, mapWithScore);
    }

    @Test // Test to see if the text of a Sentence object in the Set is null - program should continue
    public void testNullText() {
        Set<Sentence> sentences = new HashSet<>();
        Sentence newSentence = new Sentence(1, null);
        Sentence newSentence1 = new Sentence(2, "this dog is cute");
        sentences.add(newSentence);
        sentences.add(newSentence1);
        Map<String, Double> result = new HashMap<>();
        result.put("this", 2.0);
        result.put("is", 2.0);
        result.put("dog", 2.0);
        result.put("cute", 2.0);
        Map<String, Double> mapWithScore = Analyzer.calculateWordScores(sentences);
        assertEquals(result, mapWithScore);
    }

    @Test // Test to see if the text of a Sentence object in the Set is empty - program should continue
    public void testEmptyText() {
        Set<Sentence> sentences = new HashSet<>();
        Sentence newSentence = new Sentence(1, "");
        Sentence newSentence1 = new Sentence(2, "this dog is cute");
        sentences.add(newSentence);
        sentences.add(newSentence1);
        Map<String, Double> result = new HashMap<>();
        result.put("this", 2.0);
        result.put("is", 2.0);
        result.put("dog", 2.0);
        result.put("cute", 2.0);
        Map<String, Double> mapWithScore = Analyzer.calculateWordScores(sentences);
        assertEquals(result, mapWithScore);
    }

    @Test // Test to see if the score of a Sentence object in the Set is outside the range [-2, 2] - program should continue
    public void testScoreRangeNegative(){
        Set<Sentence> sentences = new HashSet<>();
        Sentence newSentence = new Sentence(-3, "dogs are cute");
        Sentence newSentence1 = new Sentence(2, "this dog is cute");
        sentences.add(newSentence);
        sentences.add(newSentence1);
        Map<String, Double> result = new HashMap<>();
        result.put("this", 2.0);
        result.put("is", 2.0);
        result.put("dog", 2.0);
        result.put("cute", 2.0);
        Map<String, Double> mapWithScore = Analyzer.calculateWordScores(sentences);
        assertEquals(result, mapWithScore);
    }

    @Test // Test to see if the score of a Sentence object in the Set is outside the range [-2, 2] - program should continue
    public void testScoreRangePositive(){
        Set<Sentence> sentences = new HashSet<>();
        Sentence newSentence = new Sentence(3, "dogs are cute");
        Sentence newSentence1 = new Sentence(2, "this dog is cute");
        sentences.add(newSentence);
        sentences.add(newSentence1);
        Map<String, Double> result = new HashMap<>();
        result.put("this", 2.0);
        result.put("is", 2.0);
        result.put("dog", 2.0);
        result.put("cute", 2.0);
        Map<String, Double> mapWithScore = Analyzer.calculateWordScores(sentences);
        assertEquals(result, mapWithScore);
    }

    @Test // Test if the method returns an empty Map if the input Set of Sentences is null
    public void testNullInputSet() {
        Set<Sentence> sentences = null;
        Map<String, Double> mapWithScore = Analyzer.calculateWordScores(sentences);
        Map<String, Double> emptyMap = new HashMap<>();
        assertEquals(emptyMap, mapWithScore);
    }

    @Test // Test if the input Set of Sentences is empty
    public void testEmptyInputSet() {
        Set<Sentence> sentences = new HashSet<>();
        Map<String, Double> mapWithScore = Analyzer.calculateWordScores(sentences);
        Map<String, Double> emptyMap = new HashMap<>();
        assertEquals(emptyMap, mapWithScore);
    }

    @Test
    public void testInvalidToken() {
        Set<Sentence> sentences = new HashSet<>();
        Sentence newSentence = new Sentence(2, "dogs #are cute");
        sentences.add(newSentence);
        Map<String, Double> result = new HashMap<>();
        result.put("dogs", 2.0);
        result.put("cute", 2.0);
        Map<String, Double> mapWithScore = Analyzer.calculateWordScores(sentences);
        assertEquals(result, mapWithScore);
    }

    @Test
    public void testCapitalLetters() {
        Set<Sentence> sentences = new HashSet<>();
        Sentence newSentence = new Sentence(2, "Dogs are Cute");
        sentences.add(newSentence);
        Map<String, Double> result = new HashMap<>();
        result.put("dogs", 2.0);
        result.put("are", 2.0);
        result.put("cute", 2.0);
        Map<String, Double> mapWithScore = Analyzer.calculateWordScores(sentences);
        assertEquals(result, mapWithScore);
    }
}
