package ru.division.of.expenses.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.division.of.expenses.app.model.Role;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query(
            "SELECT r FROM Role r WHERE r.name = :name"
    )
    Optional<Role> findRoleByName(@Param("name") String name);
}
