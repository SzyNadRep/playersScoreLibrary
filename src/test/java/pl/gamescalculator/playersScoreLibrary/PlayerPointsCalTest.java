package pl.gamescalculator.playersScoreLibrary;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerPointsCalTest {

    @Test
    public void test1(){
        PlayerRepository playerRepository = new PlayerRepository();

        int point = playerRepository.pointsForPlayer("France", "France", "1:0", "3:0");
//        assertEquals("1 point expected",3, point);

        assertEquals(3, point, "Some message");
    }

}
