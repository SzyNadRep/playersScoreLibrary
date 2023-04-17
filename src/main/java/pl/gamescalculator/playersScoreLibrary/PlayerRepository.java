package pl.gamescalculator.playersScoreLibrary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Repository
public class PlayerRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    //GET *************************************************************

    public List<Player> getAllPlayers() {
        return jdbcTemplate.query("SELECT idPlayer, playerName, playerScore, playerPredictedWinner, playerPredictedScore FROM player",
                BeanPropertyRowMapper.newInstance(Player.class));
    }

    public Player getPlayerById(int id) {
        return jdbcTemplate.queryForObject("SELECT idPlayer, playerName, playerScore FROM player WHERE id = ?", BeanPropertyRowMapper.newInstance(Player.class), id);
    }

    public Player getPlayerByPlayerName(String playerName) {
        return jdbcTemplate.queryForObject("SELECT idPlayer, playerName, playerScore, playerPredictedWinner, playerPredictedScore FROM player WHERE playerName = ?", BeanPropertyRowMapper.newInstance(Player.class), playerName);
    }

    //POST *************************************************************

    public int saveNewPlayer(List<Player> players) {
        players.forEach(player -> jdbcTemplate.update("INSERT INTO player(playerName, playerScore) VALUES(?, ?)",
                player.getPlayerName(), player.getPlayerScore()));
        return 1;
    }

    //PUT *************************************************************

    public int updatePlayerName(Player player) {
        return jdbcTemplate.update("UPDATE player SET playerName=? WHERE idPlayer=?"
                , player.getPlayerName(), player.getIdPlayer());
    }

    public int updatePlayerScore(Player player) {
        return jdbcTemplate.update("UPDATE player SET playerScore=? WHERE playerName=?"
                , player.getPlayerScore(), player.getPlayerName());
    }

    public int setPlayerBet(Player player) {
        return jdbcTemplate.update("UPDATE player SET playerPredictedWinner=?, playerPredictedScore=? WHERE idPlayer=?"
                , player.getPlayerPredictedWinner(), player.getPlayerPredictedScore(), player.getIdPlayer());
    }

    // DELETE *************************************************************

    public int deletePlayer(int id) {
        return jdbcTemplate.update("DELETE FROM player WHERE idPlayer=?", id);
    }

    public int getPlayerScore(int i) {
        int playerScore = jdbcTemplate.queryForObject("SELECT playerScore FROM player WHERE id = ?", Integer.class, i);
        return playerScore;
    }

    public List<Player> awardPointsForPlayers(Player player) {

        List<Player> players = new ArrayList<>();

        String gameFinalWinner = jdbcTemplate.queryForObject("SELECT gameFinalWinner FROM gamesLibrary WHERE idGame = ?", String.class, 1);
        String gameFinalScore = jdbcTemplate.queryForObject("SELECT gameFinalScore FROM gamesLibrary WHERE idGame = ?", String.class, 1);

        //Sciaganie listy graczy
        List<Player> list1 = jdbcTemplate.query("SELECT idPlayer, playerScore, playerPredictedWinner, playerPredictedScore FROM player", (rs, rowNum) -> {
            Player player1 = new Player();
            player1.setIdPlayer(rs.getInt("idPlayer"));
            player1.setPlayerScore(rs.getInt("playerScore"));
            player1.setPlayerPredictedWinner(rs.getString("playerPredictedWinner"));
            player1.setPlayerPredictedScore(rs.getString("playerPredictedScore"));
            return player1;
        });

        for (int i = 0; i < list1.size(); i++) {
            Player player1 = list1.get(i);

            String playerPredictedScore = player1.getPlayerPredictedScore();
            String playerPredictedWinner = player1.getPlayerPredictedWinner();

//
//            if ( Objects.equals(playerPredictedWinner, gameFinalWinner)) {
//                if (Objects.equals(playerPredictedScore, gameFinalScore)) {
//                    player1.setPlayerScore(player1.getPlayerScore() + 3);
//                } else {
//                    player1.setPlayerScore(player1.getPlayerScore() + 1);
//                }
//            }

            player1.setPlayerScore(player1.getPlayerScore() + pointsForPlayer(playerPredictedWinner, gameFinalWinner, playerPredictedScore, gameFinalScore));

            //playerAddScore();

            jdbcTemplate.update("UPDATE player SET playerScore=? WHERE idPlayer=?", player1.getPlayerScore(), player1.getIdPlayer());
            jdbcTemplate.update("UPDATE player SET playerPredictedWinner=?, playerPredictedScore=?  WHERE idPlayer=?", null, null, player1.getIdPlayer());

        }

        return list1;

    }

    public int pointsForPlayer(String playerPredictionWinner,String gameWinner, String playerPredictedScore, String gameFinalScore){
        if ( Objects.equals(playerPredictionWinner, gameWinner)) {
            if (Objects.equals(playerPredictedScore, gameFinalScore)) {
                return 3;
            } else {
                return 1;
            }
        }
        return 0;
    }



    public int takeTableSize(Player player) {
        return jdbcTemplate.queryForObject("SELECT MAX(idPlayer) FROM player", Integer.class);
    }


    public List<Player> playersByScore(Player player) {

        List<Player> players = jdbcTemplate.query("SELECT playerName, playerScore, playerPosInTable FROM player", BeanPropertyRowMapper.newInstance(Player.class));
        players.sort(Comparator.comparingInt(Player::getPlayerScore).reversed());
//        for (int i = 2; i < 12; i++) {
//            Player position = players.get(i);
//         //   jdbcTemplate.update("UPDATE player SET playerPosInTable WHERE id=?", "1" , 2);
//        }

        return players;
    }
}
