/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esai.guessthenumber.dao;

import esai.guessthenumber.TestApplicationConfiguration;
import esai.guessthenumber.models.Game;
import esai.guessthenumber.models.Round;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
//import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author Esai
 */
//@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
public class GuessTheNumberDaoDBTest {

    @Autowired
    GuessTheNumberDao dao;

    public GuessTheNumberDaoDBTest() {
    }

    @BeforeEach
    public void setUp() {
        List<Round> rounds = dao.getAllRounds();
        for (Round r : rounds) {
            dao.deleteRoundById(r.getRoundID());
        }

        List<Game> games = dao.getAllGames();
        for (Game g : games) {
            dao.deleteGameById(g.getGameID());
        }
    }

    //Test of add method, of class GuessTheNumberDaoDB.
    @Test
    public void testAdd() {
        Game game = new Game();
        game.setAnswer("3876");
        game.setStatus(false);
        game = dao.add(game);

        Game newGame = dao.getGameByGameID(game.getGameID());

        assertEquals(game.getGameID(), newGame.getGameID());
    }

    
    //Test of getAllGames method, of class GuessTheNumberDaoDB.
    @Test
    public void testGetAllGames() {
        Game game = new Game();
        game.setAnswer("3456");
        game.setStatus(true);
        game = dao.add(game);

        Game game2 = new Game();
        game2.setAnswer("2134");
        game2.setStatus(true);
        game2 = dao.add(game2);

        Game game3 = new Game();
        game3.setAnswer("2155");
        game3.setStatus(true);
        game3 = dao.add(game3);

        List<Game> games = dao.getAllGames();

        assertEquals(3, games.size());
        //assertTrue(games.contains(game));
        //assertTrue(games.contains(game2));
        //assertTrue(games.contains(game3));
    }
  
    //Test of update method, of class GuessTheNumberDaoDB.
    @Test
    public void testUpdate() {
        Game game = new Game();
        game.setAnswer("3456");
        game.setStatus(false);
        game = dao.add(game);

        Game newGame = dao.getGameByGameID(game.getGameID());

        assertEquals(game.getGameID(), newGame.getGameID());

        game.setStatus(true);

        dao.update(game);

        assertNotEquals(game, newGame);

        newGame = dao.getGameByGameID(game.getGameID());
    }

    //Test of deleteGameById method, of class GuessTheNumberDaoDB.
    @Test
    public void testDeleteGameById() {
        Game game = new Game();
        game.setAnswer("3456");
        game.setStatus(false);
        game = dao.add(game);

        dao.deleteGameById(game.getGameID());

        Game fromDao = dao.getGameByGameID(game.getGameID());

        assertNull(fromDao);
    }

    //Test of addRound method, of class GuessTheNumberDaoDB.
    @Test
    public void testAddRound() {
        Game game = new Game();
        game.setAnswer("3456");
        game.setStatus(false);
        game = dao.add(game);

        Round round1 = new Round();

        round1.setGuess("3456");
        round1.setResult("e:0:p:3");
        round1.setGameID(game.getGameID());
        dao.addRound(round1);

        Round fromDao = dao.getRoundByID(round1.getRoundID());

        round1.setGuessTime(fromDao.getGuessTime());
        assertEquals(round1.getRoundID(), fromDao.getRoundID());
    }

    //Test of getAllRounds method, of class GuessTheNumberDaoDB.
    @Test
    public void testGetAllRounds() {
        Game game = new Game();
        game.setAnswer("3456");
        game.setStatus(false);
        game = dao.add(game);

        Round round1 = new Round();

        round1.setGuess("3456");
        round1.setResult("e:0:p:3");
        round1.setGameID(game.getGameID());

        dao.addRound(round1);

        Game game2 = new Game();
        game2.setAnswer("9876");
        game2.setStatus(true);
        game2 = dao.add(game2);

        Round round2 = new Round();

        round2.setGuess("8796");
        round2.setResult("e:0:p:4");
        round2.setGameID(game2.getGameID());
        dao.addRound(round2);

        List<Round> rounds = dao.getAllRounds();

        assertEquals(2, rounds.size());
    }
}
