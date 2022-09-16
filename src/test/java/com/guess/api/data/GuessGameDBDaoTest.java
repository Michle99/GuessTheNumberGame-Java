package com.guess.api.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.*;
import com.guess.api.TestApplicationConfig;
import com.guess.api.models.Game;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationConfig.class)
public class GuessGameDBDaoTest {
    
    @Autowired
    GuessGameDBDao testDao;

    public GuessGameDBDaoTest(){}

    @Before
    public void setUp(){
        List<Game> games = testDao.getAllGames();
        for(Game game: games){
            testDao.deleteById(game.getGameId());
        }
    }
    /**
     * Test of getAll method, of class GuessGameDatabaseDao. 
     * Done first to work with a fresh DB since no delete method
     */
    @Test
    public void testGetAll() {
        Game testGame = new Game();
        testGame.setAnswer("0987");
        testGame.setGameStatus(true);
        testDao.addGame(testGame);
        
        // Game testGame2 = new Game();
        // testGame2.setAnswer("3456");
        // testGame2.setGameStatus(true);
        // testDao.add(testGame2);
        
        List<Game> games = testDao.getAllGames();
        
        assertEquals(1,games.size());
        assertTrue(games.contains(testGame));
        // assertTrue(games.contains(testGame2));
    }
    
    /**
     * Test of add method, of class GuessGameDatabaseDao.
     */
    @Test
    public void testAddFindById() {
        Game testGame = new Game();
        testGame.setAnswer("0987");
        testGame.setGameStatus(true);
        
        testGame = testDao.addGame(testGame);
        Game testFromDao = testDao.findById(testGame.getGameId());
        
        assertEquals(testGame, testFromDao);
        
    }

        /**
     * Test of updateGameStatus method, of class GuessGameDatabaseDao.
     */
    @Test
    public void testUpdateGameStatus() {
        Game testGame = new Game();
        testGame.setAnswer("0987");
        testGame.setGameStatus(true);
        testDao.addGame(testGame);
        
        Game testFromDao = testDao.findById(testGame.getGameId());
        assertEquals(testGame, testFromDao);
        
        testGame.setGameStatus(false);
        testDao.updateGameStatus(testGame);
        
        assertNotEquals(testGame, testFromDao);
        
        testFromDao = testDao.findById(testGame.getGameId());
        assertEquals(testGame, testFromDao);
    }
}
