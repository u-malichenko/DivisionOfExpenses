package ru.division.of.expenses.app.repositoryes;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.division.of.expenses.app.models.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {//, JpaSpecificationExecutor<User>

    @Query(
            value = "SELECT u FROM User u"
    )
    Page<User> findAllUsers(Pageable pageable);

    Page<User> findAll(Pageable pageable);


    @Query(
            value = "SELECT u FROM User u WHERE u.firstName like :name or u.lastName like :name"
    )
    Page<User> findUsersByName(
            @Param("name") String name,
            Pageable pageable
    );

    Optional<User> findByUsername(String username);
}
