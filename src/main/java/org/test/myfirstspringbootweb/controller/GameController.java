package org.test.myfirstspringbootweb.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.test.myfirstspringbootweb.service.GameService;
import org.test.myfirstspringbootweb.model.GameDto;
import org.test.myfirstspringbootweb.repository.GameEntity;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/games")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping
    public List<GameEntity> getGames() {
        return gameService.getGames();
    }

    @PostMapping
    public GameEntity addGame(@RequestBody @Valid GameDto gameEntity) {
        return gameService.createNewGame(gameEntity);
    }

    @PutMapping("/{id}")
    public GameEntity updateGame(
            @PathVariable Long id,
            @Valid @RequestBody GameDto gameDto) {
        return gameService.updateGame(id, gameDto);
    }

    @DeleteMapping("/{id}")
    public String deleteGame(@PathVariable Long id) {
        gameService.deleteGame(id);
        return "Game Deleted!";
    }
}