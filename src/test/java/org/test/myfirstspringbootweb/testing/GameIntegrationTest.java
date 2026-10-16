package org.test.myfirstspringbootweb.testing;

import org.springframework.test.context.TestPropertySource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.test.myfirstspringbootweb.model.GameDto;
import org.test.myfirstspringbootweb.repository.GameDao;
import org.test.myfirstspringbootweb.repository.GameEntity;
import org.test.myfirstspringbootweb.service.GameService;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestPropertySource(properties = {
        "app.security.admin-email=bryanxavier8@gmail.com"
})
class GameIntegrationTest {

    @Autowired
    private GameService gameService;

    @Autowired
    private GameDao gameDao;

    @BeforeEach
    void cleanDatabase() {
        gameDao.deleteAll();
    }

    @Test
    void createAndGetGame_shouldWorkWithH2() {

        GameDto gameDto = new GameDto();
        gameDto.setTitle("Dota 2");
        gameDto.setGenre("Strategy");
        gameDto.setRating(8.5);

        GameEntity savedGame = gameService.createNewGame(gameDto);

        assertEquals("Dota 2", savedGame.getTitle());
        assertEquals("Strategy", savedGame.getGenre());
        assertEquals(8.5, savedGame.getRating());

        var games = gameService.getGames();

        assertEquals(1, games.size());
        assertEquals("Dota 2", games.get(0).getTitle());
    }

    @Test
    void updateGame_shouldWorkWithH2() {

        GameDto gameDto = new GameDto();
        gameDto.setTitle("Dota 1");
        gameDto.setGenre("Strategy");
        gameDto.setRating(7.0);

        GameEntity savedGame = gameService.createNewGame(gameDto);

        GameDto updateData = new GameDto();
        updateData.setTitle("Dota 2");
        updateData.setGenre("Strategy");
        updateData.setRating(8.5);

        GameEntity updateGame = gameService.updateGame(
                savedGame.getId(),
                updateData
        );

        assertEquals("Dota 2", updateGame.getTitle());
        assertEquals("Strategy", updateGame.getGenre());
        assertEquals(8.5, updateGame.getRating());
    }
}
