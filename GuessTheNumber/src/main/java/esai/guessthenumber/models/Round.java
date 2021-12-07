package esai.guessthenumber.models;

import java.time.LocalDateTime;

/**
 *
 * @author Esai
 */
public class Round {

    private int roundID;
    private int gameID;
    private LocalDateTime guessTime;
    private String guess;
    private String result;
    
    public Round(){
        
    }
    
    public Round(int roundID, int gameID, LocalDateTime guessTime, String guess, String result) {
        this.roundID = roundID;
        this.gameID = gameID;
        this.guessTime = guessTime;
        this.guess = guess;
        this.result = result;
    }

    public int getRoundID() {
        return roundID;
    }

    public void setRoundID(int roundID) {
        this.roundID = roundID;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public LocalDateTime getGuessTime() {
        return guessTime;
    }

    public void setGuessTime(LocalDateTime guessTime) {
        this.guessTime = guessTime;
    }

    public String getGuess() {
        return guess;
    }

    public void setGuess(String guess) {
        this.guess = guess;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
