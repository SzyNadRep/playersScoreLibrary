package pl.gamescalculator.playersScoreLibrary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GameRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    //GET *************************************************************

    public List<Game> getAllGames(){
        return jdbcTemplate.query("SELECT idGame, gameName, gameFinalWinner, gameFinalScore FROM gamesLibrary",
                BeanPropertyRowMapper.newInstance(Game.class));
    }

    public Game getGameByName(String gameName){
        return jdbcTemplate.queryForObject("SELECT idGame, gameName, gameFinalWinner, gameFinalScore FROM gamesLibrary WHERE gameName = ?", BeanPropertyRowMapper.newInstance(Game.class), gameName);
    }

    public Game getGameById(int idGame){
        return jdbcTemplate.queryForObject("SELECT idGame, gameName, gameFinalWinner, gameFinalScore FROM gamesLibrary WHERE idGame = ?", BeanPropertyRowMapper.newInstance(Game.class), idGame);
    }

    public Game getGameResult(String gameName){
        return jdbcTemplate.queryForObject("SELECT gameFinalWinner, gameFinalScore FROM gamesLibrary WHERE gameName = ?", BeanPropertyRowMapper.newInstance(Game.class), gameName);
    }



    //POST *************************************************************

    public int addNewGame(String gameName, List<Game> games) {
        games.forEach(game -> jdbcTemplate.update("INSERT INTO gamesLibrary(gameName) VALUES(?)", gameName));
        return 1;
    }

    public int addNewGame2(String gameName) {
        jdbcTemplate.update("INSERT INTO gamesLibrary(gameName) VALUES(?)", gameName);
        return 1;
    }

    //PUT *************************************************************

    public int updateResultGame(Game game){
        return jdbcTemplate.update("UPDATE gamesLibrary SET gameFinalWinner=?, gameFinalScore=? WHERE gameName=?"
                , game.getGameFinalWinner(), game.getGameFinalScore(), game.getGameName());
    }

    //DELETE *************************************************************
    public int deleteGame(String gameName){
        return jdbcTemplate.update("DELETE FROM gamesLibrary WHERE gameName=?", gameName);
    }


}
