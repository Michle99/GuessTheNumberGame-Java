package com.guess.api.service;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guess.api.data.GuessGameDBDao;
import com.guess.api.data.GuessRoundDBDao;
import com.guess.api.models.Game;
import com.guess.api.models.Round;

@Service
public class GuessGameServiceLayerImpl implements GuessGameServiceLayer{
    
    GuessGameDBDao guessDao;
    GuessRoundDBDao roundDao;

    @Autowired
    public GuessGameServiceLayerImpl(GuessGameDBDao guessDao, GuessRoundDBDao roundDao){
        this.guessDao = guessDao;
        this.roundDao = roundDao;
    }

    @Override
    public int begin() {
        Game game = new Game();
        String answer = generateAnswer();
        game.setAnswer(answer);
        game = guessDao.addGame(game);
        return game.getGameId();
    }

    @Override
    public List<Game> getAllGame(){
        List<Game> games = guessDao.getAllGames();
        for(int i = 0; i < games.size();i++){
            if(games.get(i).isGameStatus()== true){
                games.get(i).setAnswer("In Progress");
            }
        }
        return games;
    }

    @Override
    public Game findById(int id){
        Game gm = guessDao.findById(id);
        if(gm.isGameStatus()==true){
            gm.setAnswer("In Progress");
        }
        return gm;
    }

    @Override
    public Round guess(Round round){
        // gets game id to get game
        int id = round.getGameId();
        Game gm = guessDao.findById(id);
        String trueAnswer = gm.getAnswer();
        String userGuess = round.getGuess();
        // calculates the match between answer and guess
        String result = guessResult(trueAnswer, userGuess);
        round.setResult(result);
        // checks to see if its a perfect match and if so mark game as complete
        if(trueAnswer.equals(userGuess)){
            gm.setGameStatus(false);
            guessDao.updateGameStatus(gm);
        }
        
        round = roundDao.newRound(round);
        return round;
    }

    @Override
    public List<Round> getAllRoundsForGame(int id){
        List<Round> rounds = roundDao.getAllRounds(id);
        return rounds;
    }

    //generates a 4-digit number with no duplicate digits
    private String generateAnswer(){
        //initialize 
        Random rand = new Random();
        String answer = "";
        
        // set first number to a random int 0-9
        int num1 = rand.nextInt(10);
        
        // set second number to a random int 0-9
        int num2 = rand.nextInt(10);
        //check to see its not the same as first number and if so loop until its not
        while (num2 == num1) {
            num2 = rand.nextInt(10);
        }
        
        // set third number to a random int 0-9
        int num3 = rand.nextInt(10);
        //check to see its not the same as previous numbers and if so loop until its not
        while (num3 == num2 || num3 == num1) {
            num3 = rand.nextInt(10);
        }

        // set fourth number to a random int 0-9
        int num4 = rand.nextInt(10);
        //check to see its not the same as previous numbers and if so loop until its not
        while (num4 == num3 || num4 == num2 || num4 == num1) {
            num4 = rand.nextInt(10);
        }
        
        // adds the generated ints into a string and returns
        answer = String.valueOf(num1) + String.valueOf(num2) + String.valueOf(num3) + String.valueOf(num4);
        
        return answer;
    }
    
    // used to comapre guess to actual answer and return the munber of matches
    @Override
    public String guessResult(String answer, String guess){
        char[] answerSet = answer.toCharArray();
        char[] guessSet = guess.toCharArray();
        int partial = 0;
        int exact = 0;
        String result = "";
        
        //cycles through all the postions in the guess set
        for(int i =0; i <guessSet.length; i++){
            if(answerSet[i] == guessSet[i]){
                    exact++;
                    continue;
                }
            //checks to see if the index in guess set is in answer and if not it 
            //moves onto the next otherwise determines if exact or partial match
            for(int j = 0; j < guessSet.length;j++){    
            if(answerSet[i] == guessSet[j]){
                    partial++;
                    break;
                }
            }
            
        }
        
        result = "e:" + exact + ":p:" + partial;
        return result;
    }
}
