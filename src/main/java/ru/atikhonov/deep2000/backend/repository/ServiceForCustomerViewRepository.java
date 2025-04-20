package ru.atikhonov.deep2000.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.atikhonov.deep2000.backend.model.ServiceForCustomerView;


public interface ServiceForCustomerViewRepository extends JpaRepository<ServiceForCustomerView, Long> {
}
