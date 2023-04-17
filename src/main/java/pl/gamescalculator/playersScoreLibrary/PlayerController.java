package pl.gamescalculator.playersScoreLibrary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/players")
public class PlayerController {

    @Autowired
    PlayerRepository playerRepository;

    //GET *************************************************************

    @GetMapping("")
    public List<Player> getAll(){
        return playerRepository.getAllPlayers();
    }

//    @GetMapping("/{id}")
//    public Player getById(@PathVariable("id") int id){
//        return playerRepository.getPlayerById(id);
//    }

//    @GetMapping("/{playerName}")
//    public Player getByPlayerName(@PathVariable("playerName") String playerName){
//        return playerRepository.getPlayerByPlayerName(playerName);
//    }

    @GetMapping("/test")
    public List<Player> awardPointsAll(Player player){
        return playerRepository.awardPointsForPlayers(player);
    }

    @GetMapping("/score")
    public List<Player> sortPlayerByScore(Player player){
        return playerRepository.playersByScore(player);
    }


    //POST *************************************************************

    @PostMapping("")
    public int add(@RequestBody List<Player> players){
        return playerRepository.saveNewPlayer(players);
    }

    //PUT *************************************************************

    @PutMapping("/{id}")
    public int update(@PathVariable("id") int id, @RequestBody Player updatedPlayer){
        Player player = playerRepository.getPlayerById(id);

        if(player != null){
            player.setPlayerName(updatedPlayer.getPlayerName());
            player.setPlayerScore(updatedPlayer.getPlayerScore());

            playerRepository.updatePlayerName(player);

            return 1;
        } else {
            return -1;
        }


    }

    @PutMapping("/{id}/bet")
    public int updatePrediction(@PathVariable("id") int id,@RequestBody Player updatedPlayer){
        Player player = playerRepository.getPlayerById(id);

        if(player != null){
            player.setPlayerPredictedWinner(updatedPlayer.getPlayerPredictedWinner());
            player.setPlayerPredictedScore(updatedPlayer.getPlayerPredictedScore());

            playerRepository.setPlayerBet(player);

            return 1;
        } else {
            return -1;
        }
    }

    @PutMapping("/{id}/update")
    public int awardPointsAfterMatch(@PathVariable("id") int id){
//        playerRepository.awardPointsForPlayers(id);
        return 1;
    }

    //PATCH *************************************************************
    @PatchMapping("/{id}")
    public int partiallyUpdate(@PathVariable("id") int id, @RequestBody Player updatedPlayer){
        Player player = playerRepository.getPlayerById(id);

        if(player != null){
            if(updatedPlayer.getPlayerName() != null){
                player.setPlayerName(updatedPlayer.getPlayerName());
            }
            if(updatedPlayer.getPlayerScore() > 0){
                player.setPlayerScore(updatedPlayer.getPlayerScore());
            }

            playerRepository.updatePlayerName(player);
            return 1;
        }else {
            return -1;
        }

    }

    //DELETE *************************************************************

    //TODO
    //Usuwanie gracza nie zmienia ID pozostałych graczy?
    @DeleteMapping("/{id}")
    public int delete(@PathVariable("id") int id){
        return playerRepository.deletePlayer(id);
    }



}
