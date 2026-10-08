package org.test.myfirstspringbootweb.service;

import org.test.myfirstspringbootweb.model.GameDto;
import org.test.myfirstspringbootweb.repository.GameEntity;
import org.test.myfirstspringbootweb.repository.GameDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    private GameDao gameDao;

    @Override
    @Transactional
    public GameEntity createNewGame(GameDto game) {
        var gameEntity = new GameEntity(
                game.getTitle(),
                game.getGenre(),
                game.getRating()
        );

        return gameDao.save(gameEntity);
    }
    @Override
    public List<GameEntity> getGames() {
        return gameDao.findAll();
    }

    @Override
    @Transactional
    public GameEntity updateGame(Long id, GameDto game) {

        GameEntity existingGameEntity = gameDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Game not found"));

        existingGameEntity.setTitle(game.getTitle());
        existingGameEntity.setGenre(game.getGenre());
        existingGameEntity.setRating(game.getRating());

        return gameDao.save(existingGameEntity);
    }

    @Override
    @Transactional
    public void deleteGame(Long id) {
        gameDao.deleteById(id);
    }
}