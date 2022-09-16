package com.guess.api.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.guess.api.models.Game;
import com.guess.api.models.Round;
import com.guess.api.service.GuessGameServiceLayerImpl;

@RestController
@RequestMapping("/api/guessgame")
public class GuessGameController {
    
    private final GuessGameServiceLayerImpl gameService;

    public GuessGameController(GuessGameServiceLayerImpl gameService){
        this.gameService = gameService;
    }

    @PostMapping("/begin")
    @ResponseStatus(HttpStatus.CREATED)
    public int begin() {
        return gameService.begin();
    }

    @GetMapping("/game")
    public List<Game> allGames(){
        return gameService.getAllGame();
    }

    @GetMapping("/game/{gameId}")
    public Game getGameById(@PathVariable int gameId){
        return gameService.findById(gameId);
    }

    @PostMapping("/guess")
    public Round guess(@RequestBody Round round){
        return gameService.guess(round);
    }

    @GetMapping("/rounds/{gameId}")
    public List<Round> getllRounds(@PathVariable int gameId){
        return gameService.getAllRoundsForGame(gameId);
    }
}
