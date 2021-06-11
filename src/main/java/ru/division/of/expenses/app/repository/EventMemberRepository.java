package ru.division.of.expenses.app.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.division.of.expenses.app.model.Event;
import ru.division.of.expenses.app.model.EventMember;
import ru.division.of.expenses.app.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventMemberRepository extends JpaRepository<EventMember, Long>, JpaSpecificationExecutor<EventMember> {

    @Query(
            value = "SELECT em FROM EventMember em WHERE em.event =:event AND em.user =:user"
    )
    Optional<EventMember> findEventMemberByEventAndUser(@Param("event") Event event, @Param("user") User user);
}
