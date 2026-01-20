package com.computerseekho.api.repository;

import com.computerseekho.api.entity.ERole;
import com.computerseekho.api.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
    Boolean existsByName(ERole name);
}
