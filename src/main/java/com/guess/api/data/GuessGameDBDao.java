package com.guess.api.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.guess.api.models.Game;

@Repository
public class GuessGameDBDao implements GuessGameDao{
    
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GuessGameDBDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public Game addGame(Game game){
        final String insertSql = "INSERT INTO game(answer) VALUES(?);";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        
        jdbcTemplate.update((Connection conn) -> {

            PreparedStatement statement = conn.prepareStatement(
                insertSql, 
                Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, game.getAnswer());
            return statement;

        }, keyHolder);

        game.setGameId(keyHolder.getKey().intValue());
        
        return game;
    }


    @Override
    public List<Game> getAllGames() {
        final String sql = "SELECT GameId, answer, GameStatus FROM game;";
        return jdbcTemplate.query(sql, new GameMapper());
    }
    
    // uses a helper method to get a game specified by the id passed
    @Override
    public Game findById(int id){
        final String sql = "SELECT GameId, answer, GameStatus From game WHERE GameId = ?;";
        return jdbcTemplate.queryForObject(sql, new GameMapper(), id);
    }
    
    //used to update the game status
    @Override
    public void updateGameStatus(Game game){
        final String sql = "UPDATE game SET gameStatus = ? WHERE gameId = ?;";
        jdbcTemplate.update(sql, game.isGameStatus(),game.getGameId());
    }
    
    //helper class that gets the games from the database and puts it in a format to return to user 
    private static final class GameMapper implements RowMapper<Game> {
        
        @Override
        public Game mapRow(ResultSet rs, int index) throws SQLException {
            Game gm = new Game();
            gm.setGameId(rs.getInt("GameId"));
            gm.setGameStatus(rs.getBoolean("GameStatus"));
            gm.setAnswer(rs.getString("answer"));
            return gm;
        }
    }
    
    //just for cleaning test
    @Override
    @Transactional
    public void deleteById(int id){
        // final String DELETE_GAMEROUND = "DELETE FROM gameround " + "WHERE gameId = ?";
        // jdbcTemplate.update(DELETE_GAMEROUND, id);

        final String DELETE_GAME = "DELETE FROM game WHERE gameId = ?";
        jdbcTemplate.update(DELETE_GAME, id);
    }

}
