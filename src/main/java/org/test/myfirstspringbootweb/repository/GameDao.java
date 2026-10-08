package org.test.myfirstspringbootweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GameDao extends JpaRepository<GameEntity, Long> {
}
