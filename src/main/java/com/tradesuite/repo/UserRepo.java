package com.tradesuite.repo;

import com.tradesuite.model.AppUser;
import com.tradesuite.model.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<AppUser, Long> {
    AppUser findByUsername(String username);

    List<AppUser> findAllByRole(Role role);
}
