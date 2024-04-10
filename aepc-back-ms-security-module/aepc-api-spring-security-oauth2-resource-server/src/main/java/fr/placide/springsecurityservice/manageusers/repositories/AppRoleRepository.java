package fr.placide.springsecurityservice.manageusers.repositories;

import fr.placide.springsecurityservice.manageusers.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AppRoleRepository extends JpaRepository<AppRole, Long> {
    AppRole findByRole(String roleName);
}
