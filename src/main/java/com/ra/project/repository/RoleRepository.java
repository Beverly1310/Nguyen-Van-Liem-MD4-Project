package com.ra.project.repository;

import com.ra.project.model.cons.RoleName;
import com.ra.project.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findRoleByRoleName(RoleName name);
}
