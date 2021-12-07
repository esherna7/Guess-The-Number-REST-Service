package esai.guessthenumber.dao;

import esai.guessthenumber.models.Game;
import esai.guessthenumber.models.Round;
import java.util.List;

/**
 *
 * @author Esai
 */
public interface GuessTheNumberDao {
    
    Game add(Game game);

    List<Game> getAllGames();

    Game getGameByGameID(int gameID);

    // true if item exists and is updated
    void update(Game game);

    Round addRound(Round round);

    List<Round> getAllRounds();

    Round getRoundByID(int id);

    List<Round> getRoundByGameID(int id);

    // true if item exists and is deleted
    void deleteGameById(int id);

    // true if item exists and is updated
    void updateRound(Round round);

    // true if item exists and is deleted
    void deleteRoundById(int id);

}
