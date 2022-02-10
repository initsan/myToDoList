package com.initsan.myToDoList.repository;

import com.initsan.myToDoList.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
