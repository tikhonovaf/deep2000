package ru.atikhonov.deep2000.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.atikhonov.deep2000.backend.model.Demo2000User;

import java.util.Optional;

public interface Demo2000UserRepository extends JpaRepository<Demo2000User, Long> {
    Optional<Demo2000User> findByLogin(String login);

}
