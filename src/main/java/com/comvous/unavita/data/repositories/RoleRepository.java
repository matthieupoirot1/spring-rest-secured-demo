package com.comvous.unavita.data.repositories;

import com.comvous.unavita.data.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    public Role findByName(String name);
}
