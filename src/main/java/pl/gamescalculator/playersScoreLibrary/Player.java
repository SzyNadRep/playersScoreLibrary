package pl.gamescalculator.playersScoreLibrary;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Player {
    private int idPlayer;
    private String playerName;
    private int playerScore;
    private String playerPredictedWinner;
    private String playerPredictedScore;

    private String playerPosInTable;

}
