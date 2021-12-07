/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esai.guessthenumber.service;

import esai.guessthenumber.dao.GuessTheNumberDao;
import esai.guessthenumber.models.Game;
import esai.guessthenumber.models.Round;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author herna
 */
@Component
public class GuessTheNumberServiceLayer {

    @Autowired
    GuessTheNumberDao dao;

    // Create a new Game Object with random answer
    // call dao to add new Game to database
    public int startGame() {
        Game game = new Game();
        // call createanswer() to generate answer for new game
        game.setAnswer(createAnswer());
        dao.add(game);
        return game.getGameID();
    }

    public Round makeGuess(Round round) {
        // get gameID of specified round and its answer
        String answer = dao.getGameByGameID(round.getGameID()).getAnswer();

        //Determines result based of method following
        String result = getResult(round.getGuess(), answer);

        //Change the result 
        round.setResult(result);

        //Change status if guess matches answer
        if (round.getGuess().equals(answer)) {
            Game game = getGame(round.getGameID());
            game.setStatus(true);
            dao.update(game);
        }

        return dao.addRound(round);
    }

    // Returns a list of all games. Be sure in-progress games do not display their answer.
    public List<Game> getListGames() {
        return dao.getAllGames();
    }

    // Returns a specific game based on ID. Be sure in-progress games do not display their answer.
    public Game getGameByGameID(int gameID) {
        return dao.getGameByGameID(gameID);
    }

    // Returns a list of rounds for the specified game sorted by time.
    public List<Round> getRoundByGameID(int gameID) {
        return dao.getRoundByGameID(gameID);
    }

    // generate an answer for game
    public String createAnswer() {
        List<Integer> l = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        Collections.shuffle(l);
        Integer result = 1000 * l.get(0) + 100 * l.get(1) + 10 * l.get(2) + l.get(3);

        return result.toString();
    }

    // determine the exact and partial positons
    public String getResult(String guess, String answer) {
        int exactMatch = 0;
        int partialMatch = 0;
        int index = 0;

        // Turn numbers into character array for iteration
        char[] guessArray = guess.toCharArray();
        char[] answerArray = answer.toCharArray();

        // Iterate until the values reach 4
        while (index < guessArray.length) {
            //Check if the number exists in the array
            //If its less then 0, it does't exist
            //&& answer.indexOf(guessArray[index]) >= 0 
            if (guess.indexOf(answerArray[index]) >= 0) {
                //Comparing each character to see if there matched
                if (guessArray[index] == answerArray[index]) {
                    //if so its an exact match
                    exactMatch++;
                } else {
                    //if not, position is off
                    partialMatch++;
                }
            }
            index++;
        }
        return "e:" + exactMatch + ":p:" + partialMatch;
    }

    // get current game and set game status to false if not correct guess
    public Game getGame(int gameID) {
        Game game = dao.getGameByGameID(gameID);
        if (game.getStatus() == false) {
            game.setAnswer("####");
        }
        return game;
    }
}
