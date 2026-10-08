package org.test.myfirstspringbootweb.testing;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.test.myfirstspringbootweb.model.GameDto;
import org.test.myfirstspringbootweb.repository.GameDao;
import org.test.myfirstspringbootweb.repository.GameEntity;
import org.test.myfirstspringbootweb.service.GameServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameUnitTest {

    @Mock
    private GameDao gameDao;

    @InjectMocks
    private GameServiceImpl gameService;

    @Test
    void createNewGame_shouldSaveGame() {

        GameDto gameDto = new GameDto();
        gameDto.setTitle("Dota 2");
        gameDto.setGenre("Strategy");
        gameDto.setRating(8.5);

        GameEntity savedGame = new GameEntity(
                "Dota 2",
                "Strategy",
                8.5
        );

        when(gameDao.save(any(GameEntity.class))).thenReturn(savedGame);

        GameEntity result = gameService.createNewGame(gameDto);

        assertEquals("Dota 2", result.getTitle());
        assertEquals("Strategy", result.getGenre());
        assertEquals(8.5, result.getRating());

        verify(gameDao, times(1)).save(any(GameEntity.class));
    }

    @Test
    void getGames_shouldReturnAllGames() {

        GameEntity game1 = new GameEntity(

                "Dota 2",
                "Strategy",
                8.5
        );

        GameEntity game2 = new GameEntity(
                "Minecraft",
                "Sandbox",
                9.0
        );

        when(gameDao.findAll()).thenReturn(List.of(game1, game2));

        List<GameEntity> result = gameService.getGames();

        assertEquals(2, result.size());
        assertEquals("Dota 2", result.get(0).getTitle());
        assertEquals("Minecraft", result.get(1).getTitle());

        verify(gameDao, times(1)).findAll();

    }

    @Test
    void updateGame_shouldUpdateExistingGame() {

        GameEntity existingGame = new GameEntity(
                "Dota 1",
                "MOBA",
                7.0
        );

        GameDto updateGame = new GameDto();
        updateGame.setTitle("Dota 2");
        updateGame.setGenre("Strategy");
        updateGame.setRating(8.5);

        when(gameDao.findById(1L)).thenReturn(Optional.of(existingGame));
        when(gameDao.save(existingGame)).thenReturn(existingGame);

        GameEntity result = gameService.updateGame(1L, updateGame);

        assertEquals("Dota 2", result.getTitle());
        assertEquals("Strategy", result.getGenre());
        assertEquals(8.5, result.getRating());

        verify(gameDao, times(1)).findById(1L);
        verify(gameDao, times(1)).save(existingGame);
    }

    @Test
    void deleteGame_shouldDeleteGame(){

        gameService.deleteGame(1L);

        verify(gameDao, times(1)).deleteById(1L);

    }
}