package com.guess.api.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.guess.api.models.Round;

@Repository
public class GuessRoundDBDao implements GuessRoundDao{
    
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GuessRoundDBDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    @Override
    @Transactional
    public Round newRound(Round round){
        final String sql = "INSERT INTO round(gameId, guess, result) VALUES(?,?,?);";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        
        jdbcTemplate.update((Connection conn) -> {

            PreparedStatement statement = conn.prepareStatement(
                sql, 
                Statement.RETURN_GENERATED_KEYS);

            statement.setInt(1, round.getGameId());
            statement.setString(2, round.getGuess());
            statement.setString(3, round.getResult());
            return statement;

        }, keyHolder);

        round.setRoundId(keyHolder.getKey().intValue());
        int roundId = round.getRoundId();
        

        final String sql2 = "SELECT * FROM round WHERE roundId = ?";
        return jdbcTemplate.queryForObject(sql2, new RoundMapper(), roundId);
    }
    
    
    @Override
    public List<Round> getAllRounds(int id){
        final String sql = "SELECT * FROM round WHERE GameId = ? ORDER BY time;";
        return jdbcTemplate.query(sql, new RoundMapper(), id);
    }
    
    
    public static final class RoundMapper implements RowMapper<Round> {
        
        @Override
        public Round mapRow(ResultSet rs, int index) throws SQLException {
            Round round = new Round();
            round.setRoundId(rs.getInt("roundId"));
            round.setGameId(rs.getInt("gameId"));
            round.setGuess(rs.getString("guess"));
            
            Timestamp timestamp = rs.getTimestamp("time");
            round.setTime(timestamp.toLocalDateTime());
            
            round.setResult(rs.getString("result"));
            return round;
        }
    }
    

    @Override 
    public boolean deleteRoundById(int id){
        final String sql = "DELETE FROM round WHERE roundId = ?;";
        return jdbcTemplate.update(sql, id) > 0;
    }
}
