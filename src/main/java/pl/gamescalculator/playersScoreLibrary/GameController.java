package pl.gamescalculator.playersScoreLibrary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    GameRepository gameRepository;

    //GET *************************************************************

    @GetMapping("")
    public List<Game> getAll(){
        return gameRepository.getAllGames();
    }

    @GetMapping("/{gameName}")
    public Game getByGameName(@PathVariable("gameName") String gameName){
        return gameRepository.getGameByName(gameName);
    }

    @GetMapping("/idGame={idGame}")
    public Game getByGameId(@PathVariable("idGame") int idGame){
        return gameRepository.getGameById(idGame);
    }

    //POST *************************************************************

    @PostMapping("/add={gameName}")
    public int addGame(@PathVariable("gameName") String gameName, @RequestBody List<Game> games){
        return gameRepository.addNewGame(gameName, games);
    }


    //PUT *************************************************************

    @PutMapping("/idGame{idGame}/update_gameName")

    public int updateGameName(){
        return 1;
    }
    @PutMapping("/{gameName}")
    public int updateResultToGame(@PathVariable("gameName") String gameName, @RequestBody Game updatedGame) {
        Game game = gameRepository.getGameByName(gameName);

        if (game != null) {
            game.setGameFinalWinner((updatedGame.getGameFinalWinner()));
            game.setGameFinalScore(updatedGame.getGameFinalScore());

            gameRepository.updateResultGame(game);
            return 1;
        } else {
            return -1;
        }
    }

    //DELETE *************************************************************

    @DeleteMapping("/{gameName}")
    public int deleteGame(@PathVariable("gameName") String gameName){
        return gameRepository.deleteGame(gameName);
    }

}
