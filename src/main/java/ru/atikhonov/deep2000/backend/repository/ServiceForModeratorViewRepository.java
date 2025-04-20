package ru.atikhonov.deep2000.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.atikhonov.deep2000.backend.model.ServiceForModeratorView;

public interface ServiceForModeratorViewRepository extends JpaRepository<ServiceForModeratorView, Long> {
}
