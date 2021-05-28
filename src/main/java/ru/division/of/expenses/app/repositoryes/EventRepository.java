package ru.division.of.expenses.app.repositoryes;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.division.of.expenses.app.dto.EventDto;
import ru.division.of.expenses.app.models.Event;
import ru.division.of.expenses.app.models.Expense;

import java.util.Collection;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {
    @Query(
            value = "SELECT * FROM event WHERE user_id = :userId",
            nativeQuery = true
    )
    Page<Event> findEventsByManagerId(
            @Param("userId") Long id,
            Pageable pageable
    );

    @Query(
            value = "SELECT e FROM Event e JOIN e.eventUserList eu WHERE eu.id = :participantId "
    )
    Page<Event> findEventByParticipantId(
           @Param("participantId") Long id,
           Pageable pageable
    );

    @Query(
            value = "SELECT e.expenseList FROM Event e WHERE e.id = :id"
    )
    Page<Expense> findExpenseById(@Param("id") Long id, Pageable pageable);

    @Query(
            value = "SELECT e FROM Event e WHERE e.eventManager.username =:username"
    )
    Page<Event> findEventsByManagerUsername(
            @Param("username") String username,
            Pageable pageable
    );

    @Query(
            value = "SELECT e FROM Event e JOIN e.eventUserList eu WHERE eu.username =:username"
    )
    Page<Event> findEventsByParticipantUsername(
            @Param("username") String username,
            Pageable pageable
    );

    @Query(
            value = "SELECT em.username FROM Event e JOIN e.eventManager em WHERE e.id =:id"
    )
    String findEventManagerUsernameById(@Param("id") Long id);
}
