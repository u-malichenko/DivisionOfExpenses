package ru.division.of.expenses.app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.division.of.expenses.app.model.Event;
import ru.division.of.expenses.app.model.Expense;

import java.util.List;

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
    Page<Expense> findExpenseByEventId(@Param("id") Long id, Pageable pageable);

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


    @Query(
            value = "SELECT em.username FROM Event e JOIN e.eventUserList em WHERE e.id = :eventId"
    )
    List<String> findEventUserUsernameById(@Param("eventId") Long eventId);


    @Query(
            value = "SELECT em.user.username FROM Event e JOIN e.eventMembers em WHERE e.id = :eventId"
    )
    List<String> findEventMemberUsernameById(Long eventId);

    @Query(
            value = "SELECT e.eventManager.username FROM Event e JOIN e.expenseList ee WHERE ee.id =:expenseId"
    )
    String findEventManagerUsernameByExpenseId(@Param("expenseId") Long expenseId);

//    @Query(
//            value = "SELECT e FROM Event e WHERE e.id =:id"
//    )
//    Optional<Event> findEventById(@Param("id") Long id);
}
