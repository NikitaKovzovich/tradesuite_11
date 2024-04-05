package com.tradesuite.repo;

import com.tradesuite.model.Application;
import com.tradesuite.model.enums.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepo extends JpaRepository<Application, Long> {
    List<Application> findAllByStatus(ApplicationStatus status);
}
