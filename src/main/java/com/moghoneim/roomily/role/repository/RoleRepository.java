package com.moghoneim.roomily.role.repository;

import com.moghoneim.roomily.role.entity.Role;
import com.moghoneim.roomily.security.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleType name);
}
