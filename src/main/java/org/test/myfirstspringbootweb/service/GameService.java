package org.test.myfirstspringbootweb.service;

import org.test.myfirstspringbootweb.model.GameDto;
import org.test.myfirstspringbootweb.repository.GameEntity;

import java.util.List;

public interface GameService {

    GameEntity createNewGame(GameDto gameEntity);

    List<GameEntity> getGames();

    GameEntity updateGame(Long id, GameDto game);

    void deleteGame(Long id);
}