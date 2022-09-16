package com.guess.api.service;

import java.util.List;

import com.guess.api.models.Game;
import com.guess.api.models.Round;

public interface GuessGameServiceLayer {
    public int begin();
    
    public List<Game> getAllGame();
    
    public Game findById(int id);
    
    public Round guess(Round round);
    
    public List<Round> getAllRoundsForGame(int id);
    
    public String guessResult(String answer, String guess);
}
