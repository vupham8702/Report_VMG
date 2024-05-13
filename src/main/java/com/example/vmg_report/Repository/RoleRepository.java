package com.example.vmg_report.Repository;

import com.example.vmg_report.model.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Set<Role> findByCode(String User);
}
