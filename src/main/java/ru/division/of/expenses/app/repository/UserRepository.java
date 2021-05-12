package ru.division.of.expenses.app.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.division.of.expenses.app.model.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(
            value = "SELECT u FROM User u"
    )
    List<User> findAllUsers(Pageable pageable);


    @Query(
            value = "SELECT u FROM User u WHERE u.firstName like :name or u.lastName like :name"
    )
    List<User> findUsersByName(
            @Param("name") String name,
            PageRequest pageable
    );
}
