package com.guess.api.service;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.guess.api.TestApplicationConfig;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationConfig.class)
public class GuessGameServiceTest {
    
    @Autowired
    GuessGameServiceLayerImpl testGameService;

    public GuessGameServiceTest(){}

    @Test
    public void testGuessResult1() {
        String testGuess = "1234";
        String testAnswer = "0987";
        String testResult = "e:0:p:0";
        
        String result = testGameService.guessResult(testAnswer, testGuess);
        assertEquals(result,testResult);
    }
    
    /**
     * Test of guessResult method, of class GuessGameServiceLayerImpl.
     * All partial match
     */
    @Test
    public void testGuessResult2() {
        String testGuess = "7890";
        String testAnswer = "0987";
        String testResult = "e:0:p:4";
        
        String result = testGameService.guessResult(testAnswer, testGuess);
        assertEquals(result,testResult);
    }
    
    /**
     * Test of guessResult method, of class GuessGameServiceLayerImpl.
     * All exact match
     */
    @Test
    public void testGuessResult3() {
        String testGuess = "0987";
        String testAnswer = "0987";
        String testResult = "e:4:p:0";
        
        String result = testGameService.guessResult(testAnswer, testGuess);
        assertEquals(result,testResult);
    }
    
    /**
     * Test of guessResult method, of class GuessGameServiceLayerImpl.
     * split exact and partial
     */
    @Test
    public void testGuessResult4() {
        String testGuess = "0978";
        String testAnswer = "0987";
        String testResult = "e:2:p:2";
        
        String result = testGameService.guessResult(testAnswer, testGuess);
        assertEquals(result,testResult);
    }

}
