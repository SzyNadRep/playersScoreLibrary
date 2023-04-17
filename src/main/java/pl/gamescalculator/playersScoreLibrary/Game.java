package pl.gamescalculator.playersScoreLibrary;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Game {
    private int idGame;
    private String gameName;
    private String gameFinalWinner;
    private String gameFinalScore;

}

