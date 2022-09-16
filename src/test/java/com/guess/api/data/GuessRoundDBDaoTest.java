package com.guess.api.data;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.guess.api.TestApplicationConfig;
import com.guess.api.models.Game;
import com.guess.api.models.Round;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationConfig.class)
public class GuessRoundDBDaoTest {
    
    @Autowired
    GuessRoundDBDao testRoundDao;

    @Autowired
    GuessGameDBDao testGameDao;

    public GuessRoundDBDaoTest(){}

    @Test
    public void testNewGetAllRound() {
        Game testGame = new Game();
        testGame.setAnswer("0987");
        testGame.setGameStatus(true);
        testGameDao.addGame(testGame);
        
        Round testRound = new Round();
        testRound.setGameId(testGame.getGameId());
        testRound.setGuess("1234");
        testRound.setResult("e:0:p:0");
        testRoundDao.newRound(testRound);
        
        Round testRound2 = new Round();
        testRound2.setGameId(testGame.getGameId());
        testRound2.setGuess("0987");
        testRound2.setResult("e:4:p:0");
        testRoundDao.newRound(testRound2);
        
        List<Round> rounds = testRoundDao.getAllRounds(testGame.getGameId());
        
        assertEquals(2,rounds.size());
    }
    
}
