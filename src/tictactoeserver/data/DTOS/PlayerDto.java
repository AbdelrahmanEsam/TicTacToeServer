/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeserver.data.DTOS;

/**
 *
 * @author ASUS
 */
public class PlayerDto {

    public PlayerDto() {
    }

    String playerName;
    String password;

    public PlayerDto(String playerName, String password) {
        this.playerName = playerName;
        this.password = password;
    }
    
    public PlayerDto(String playerName, String password, int score, int numberOfGameWins, int numberOfGameLose, int numberOfGameDraws) {
        this.playerName = playerName;
        this.password = password;
        this.score = score;
        this.numberOfGameWins = numberOfGameWins;
        this.numberOfGameLose = numberOfGameLose;
        this.numberOfGameDraws = numberOfGameDraws;
    }
    int score;
    int numberOfGameWins;
    int numberOfGameLose;
    int numberOfGameDraws;


    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getNumberOfGameWins() {
        return numberOfGameWins;
    }

    public void setNumberOfGameWins(int numberOfGameWins) {
        this.numberOfGameWins = numberOfGameWins;
    }

    public int getNumberOfGameLose() {
        return numberOfGameLose;
    }

    public void setNumberOfGameLose(int numberOfGameLose) {
        this.numberOfGameLose = numberOfGameLose;
    }

    public int getNumberOfGameDraws() {
        return numberOfGameDraws;
    }

    public void setNumberOfGameDraws(int numberOfGameDraws) {
        this.numberOfGameDraws = numberOfGameDraws;
    }

}
