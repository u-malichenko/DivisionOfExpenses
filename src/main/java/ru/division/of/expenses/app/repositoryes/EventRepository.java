package ru.division.of.expenses.app.repositoryes;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.division.of.expenses.app.models.Event;
import ru.division.of.expenses.app.models.User;

import java.util.Collection;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {

    @Query(
            value = "SELECT em.username FROM Event e JOIN e.eventManager em WHERE e.id = :id"
    )
    String findUsernameEventManagerById(@Param("id") Long id);


    @Query(
          value = "SELECT e.eventUserLIst FROM Event e WHERE e.id = :id"
    )
    Page<User> findEventUserlistById(Long id, Pageable pageable);

}
