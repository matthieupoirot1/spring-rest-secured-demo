package com.comvous.demo.data.repositories;

import com.comvous.demo.data.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    public Role findByName(String name);
}
