package esai.guessthenumber.controller;

import esai.guessthenumber.models.Game;
import esai.guessthenumber.models.Round;
import esai.guessthenumber.service.GuessTheNumberServiceLayer;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Esai
 */
@RestController
@RequestMapping("/api")
public class GuessTheNumberController {

    @Autowired
    GuessTheNumberServiceLayer service;

    // Starts a game, generates an answer, and sets the correct status.
    // Should return a 201 CREATED message as well as the created gameId.
    @PostMapping("/begin")
    @ResponseStatus(HttpStatus.CREATED)
    public int create() {
        return service.startGame();
    }

    // Makes a guess by passing the guess and gameId in as JSON. 
    // The program must calculate the results of the guess and mark the game finished if the guess is correct.
    // It returns the Round object with the results filled in.
    @PostMapping("/guess")
    public Round guess(@RequestBody Round round) { // @RequestBody tells Spring MVC to expect the data fully serialized in the HTTP request body
        //return round;
        return service.makeGuess(round);
    }

    // Returns a list of all games. Be sure in-progress games do not display their answer.
    @GetMapping("/game")
    public List<Game> getAllGames() {
        return service.getListGames();
    }

    // Returns a specific game based on ID. Be sure in-progress games do not display their answer.
    //@PathVariable tells Spring MVC to find the parameter in the URL
    @GetMapping("/game/{gameID}")
    public Game getGameByGameID(@PathVariable("gameID") int gameID) {
        return service.getGameByGameID(gameID);
    }

    // Returns a list of rounds for the specified game sorted by time.
    //@PathVariable tells Spring MVC to find the parameter in the URL
    @GetMapping("/rounds/{gameID}")
    public List<Round> getRoundByGameID(@PathVariable("gameID") int gameID) {
        return service.getRoundByGameID(gameID);
    }

}
