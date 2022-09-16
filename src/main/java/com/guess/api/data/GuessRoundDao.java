package com.guess.api.data;

import java.util.List;

import com.guess.api.models.Round;

public interface GuessRoundDao {
    
    public Round newRound(Round newround);

    public List<Round> getAllRounds(int id);

    public boolean deleteRoundById(int id);
}
