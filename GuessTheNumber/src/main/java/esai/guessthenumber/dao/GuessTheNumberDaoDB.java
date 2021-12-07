package esai.guessthenumber.dao;

import esai.guessthenumber.models.Game;
import esai.guessthenumber.models.Round;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Esai
 */
@Repository
public class GuessTheNumberDaoDB implements GuessTheNumberDao {

    @Autowired
    JdbcTemplate jdbc;

    @Autowired
    public GuessTheNumberDaoDB(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    // Add new game to DB
    @Override
    public Game add(Game game) {
        final String INSERT_GAME = "INSERT INTO game(answer, status) VALUES(?,?)";
        jdbc.update(INSERT_GAME, game.getAnswer(), game.getStatus());
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        game.setGameID(newId);
        return game;
    }

    // Get all games from DB
    // Returns a list of all games. Be sure in-progress games do not display their answer.
    @Override
    public List<Game> getAllGames() {
        final String SELECT_ALL_GAMES = "SELECT * FROM game WHERE status = 1";
        return jdbc.query(SELECT_ALL_GAMES, new GameMapper());
    }

    // Get Game from DB
    // Returns specific game based on ID. Be sure in-progress games do not display their answer.
    @Override
    public Game getGameByGameID(int gameID) {
        try {
            final String SELECT_GAME_BY_GAME_ID = "Select * from game WHERE game_id = ? AND status = 0";
            return jdbc.queryForObject(SELECT_GAME_BY_GAME_ID, new GameMapper(), gameID);
        } catch (DataAccessException e) {
            return null;
        }
    }

    // change status of game and answer in DB based on game results
    @Override
    public void update(Game game) {
        final String UPDATE_GAME = "UPDATE game SET status = ?, answer = ? WHERE game_id = ?";
        jdbc.update(UPDATE_GAME, game.getGameID(), game.getAnswer(), game.getStatus());
    }

    @Override
    public void deleteGameById(int id) {
        final String DELETE_MEETING_BY_ROOM = "DELETE FROM game WHERE game_id = ?";
        jdbc.update(DELETE_MEETING_BY_ROOM, id);
    }

    @Override
    public Round addRound(Round round) {
        final String INSERT_ROUND = "INSERT INTO round(game_id, guess, result) VALUES(?,?,?)";
        jdbc.update(INSERT_ROUND, round.getGameID(), round.getGuess(), round.getResult());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        round.setRoundID(newId);
        return getRoundByID(newId);
    }

    @Override
    public List<Round> getAllRounds() {
        final String SELECT_ALL_ROUNDS = "SELECT * FROM round";
        return jdbc.query(SELECT_ALL_ROUNDS, new RoundMapper());
    }

    @Override
    public Round getRoundByID(int id) {
        try {
            final String SELECT_ROUND_BY_ID = "SELECT * FROM round WHERE round_id = ?";
            return jdbc.queryForObject(SELECT_ROUND_BY_ID, new RoundMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public void updateRound(Round round) {
        final String UPDATE_ROOM = "UPDATE round SET guess = ?, result = ? WHERE id = ?";
        jdbc.update(UPDATE_ROOM, round.getGuess(), round.getResult(), round.getRoundID());
    }

    // Get Rounds from DB by gameID
    // Returns a list of rounds for the specified game sorted by time.
    @Override
    public List<Round> getRoundByGameID(int gameID) {
        final String SELECT_ROUND_BY_GAME_ID = "Select * FROM round WHERE game_ID = ? ORDER BY guess_time";
        List<Round> rounds = jdbc.query(SELECT_ROUND_BY_GAME_ID, new RoundMapper(), gameID);
        return rounds;
    }

    @Override
    public void deleteRoundById(int id) {
        final String DELETE_ROUND_BY_ROOM = "DELETE FROM round WHERE round_id = ?";
        jdbc.update(DELETE_ROUND_BY_ROOM, id);
    }

    private static final class GameMapper implements RowMapper<Game> {

        @Override
        public Game mapRow(ResultSet rs, int index) throws SQLException {
            Game game = new Game();
            game.setGameID(rs.getInt("game_id"));
            game.setAnswer(rs.getString("answer"));
            game.setStatus(rs.getBoolean("status"));
            return game;
        }
    }

    private static final class RoundMapper implements RowMapper<Round> {

        @Override
        public Round mapRow(ResultSet rs, int i) throws SQLException {
            Round round = new Round();

            round.setRoundID(rs.getInt("round_id"));
            round.setGameID(rs.getInt("game_id"));
            round.setGuess(rs.getString("guess"));

            Timestamp timestamp = rs.getTimestamp("guess_time");

            round.setGuessTime(timestamp.toLocalDateTime());

            round.setResult(rs.getString("result"));
            return round;
        }
    }

}
