package com.app.management_service.doa;

import com.app.management_service.model.ManagerDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRepository extends JpaRepository<ManagerDetails, Long> {
    boolean existsByManagerEmail(String managerEmail);
}