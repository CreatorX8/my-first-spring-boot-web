package org.test.myfirstspringbootweb.testing;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = {
        "app.security.google-client-id=test-client-id",
        "app.security.admin-email=admin@test.com"
})
@AutoConfigureMockMvc
class SecurityAuthorizationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void userCannotDeleteGame() throws Exception {
        mockMvc.perform(delete("/api/games/1")
                        .with(jwt().authorities(() -> "ROLE_USER")))
                .andExpect(status().isForbidden());
    }

    @Test
    void adminCanAttemptToDeleteGame() throws Exception {
        mockMvc.perform(delete("/api/games/1")
                        .with(jwt().authorities(() -> "ROLE_ADMIN")))
                .andExpect(status().isOk());
    }

    @Test
    void userCanGetGames() throws Exception {
        mockMvc.perform(get("/api/games")
                        .with(jwt().authorities(() -> "ROLE_USER")))
                .andExpect(status().isOk());
    }

    @Test
    void adminCanGetGames() throws Exception {
        mockMvc.perform(get("/api/games")
                        .with(jwt().authorities(() -> "ROLE_ADMIN")))
                .andExpect(status().isOk());
    }

    @Test
    void userCanCreateGame() throws Exception {
        mockMvc.perform(post("/api/games")
                        .with(jwt().authorities(() -> "ROLE_USER"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "title": "Test Game",
                                    "genre": "Adventure",
                                    "rating": 8.5
                                }
                                """))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void adminCanCreateGame() throws Exception {
        mockMvc.perform(post("/api/games")
                        .with(jwt().authorities(() -> "ROLE_ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "title": "Admin Test Game",
                                    "genre": "Adventure",
                                    "rating": 9.0
                                }
                                """))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void userCanUpdateGame() throws Exception {
        String response = mockMvc.perform(post("/api/games")
                        .with(jwt().authorities(() -> "ROLE_USER"))
                        .contentType("application/json")
                        .content("""
                            {
                                "title": "Original User Game",
                                "genre": "Adventure",
                                "rating": 7.0
                            }
                            """))
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();

        long gameId = Long.parseLong(
                com.jayway.jsonpath.JsonPath.read(response, "$.id").toString()
        );

        mockMvc.perform(put("/api/games/" + gameId)
                        .with(jwt().authorities(() -> "ROLE_USER"))
                        .contentType("application/json")
                        .content("""
                            {
                                "title": "Updated User Game",
                                "genre": "Adventure",
                                "rating": 8.5
                            }
                            """))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void adminCanUpdateGame() throws Exception {
        String response = mockMvc.perform(post("/api/games")
                        .with(jwt().authorities(() -> "ROLE_ADMIN"))
                        .contentType("application/json")
                        .content("""
                            {
                                "title": "Original Admin Game",
                                "genre": "Adventure",
                                "rating": 7.0
                            }
                            """))
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();

        long gameId = Long.parseLong(
                com.jayway.jsonpath.JsonPath.read(response, "$.id").toString()
        );

        mockMvc.perform(put("/api/games/" + gameId)
                        .with(jwt().authorities(() -> "ROLE_ADMIN"))
                        .contentType("application/json")
                        .content("""
                            {
                                "title": "Updated Admin Game",
                                "genre": "Adventure",
                                "rating": 9.0
                            }
                            """))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void unauthenticatedUserCannotGetGames() throws Exception {
        mockMvc.perform(get("/api/games"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void unauthenticatedUserCannotDeleteGame() throws Exception {
        mockMvc.perform(delete("/api/games/1"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void invalidGoogleTokenIsRejected() throws Exception {
        mockMvc.perform(get("/api/games")
                        .header("Authorization", "Bearer invalid-token"))
                .andExpect(status().isUnauthorized());
    }
}
