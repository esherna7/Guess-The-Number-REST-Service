/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esai.guessthenumber.service;

import esai.guessthenumber.TestApplicationConfiguration;
import esai.guessthenumber.dao.GuessTheNumberDao;
import esai.guessthenumber.models.Game;
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
public class GuessTheNumberServiceLayerTest {

    @Autowired
    GuessTheNumberServiceLayer service;

    @Autowired
    GuessTheNumberDao dao;

    public GuessTheNumberServiceLayerTest() {
    }

    /**
     * Test of createAnswer method, of class GuessTheNumberServiceLayer.
     */
    @Test
    public void testCreateAnswer() {
        String result = service.createAnswer();
        //Checking that the length is 4
        assertEquals(4, result.length());
        assertEquals(true, uniqueCharacters(result));
    }

    //helper function for above test
    boolean uniqueCharacters(String str) {
        // If at any time we encounter 2 same
        // characters, return false
        for (int i = 0; i < str.length(); i++) {
            for (int j = i + 1; j < str.length(); j++) {
                if (str.charAt(i) == str.charAt(j)) {
                    return false;
                }
            }
        }

        // If no duplicate characters encountered,
        // return true
        return true;
    }

    @Test
    public void testGetGameFalse() {

        Game game = new Game();
        game.setAnswer("3876");
        game.setStatus(false);
        game = dao.add(game);

        Game newGame = service.getGame(game.getGameID());
        assertEquals("####", newGame.getAnswer());

    }

    @Test
    public void testGetGameTrue() {

        Game game1 = new Game();
        game1.setAnswer("8542");
        game1.setStatus(true);
        game1 = dao.add(game1);

        //Game newGame = service.getGame(game1.getGameID());
        //assertEquals(game1.getAnswer(), newGame.getAnswer());

    }

    @Test
    public void testGame1() {
        String guess = "1234";
        String answer = "2159";
        String result = service.getResult(guess, answer);

        assertEquals("e:0:p:2", result);
    }

    @Test
    public void testGame2() {
        String guess = "1234";
        String answer = "1234";
        String result = service.getResult(guess, answer);

        assertEquals("e:4:p:0", result);
    }

    @Test
    public void testGame3() {
        String guess = "1234";
        String answer = "4321";
        String result = service.getResult(guess, answer);

        assertEquals("e:0:p:4", result);
    }

}
