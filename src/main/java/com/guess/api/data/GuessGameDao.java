package com.guess.api.data;

import java.util.List;

import com.guess.api.models.Game;

public interface GuessGameDao {
    Game addGame(Game game);

    List<Game> getAllGames();

    Game findById(int id);

    public void updateGameStatus(Game game);

    public void deleteById(int id);
}
