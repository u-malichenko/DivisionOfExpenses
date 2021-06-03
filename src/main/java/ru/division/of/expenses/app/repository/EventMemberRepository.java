package ru.division.of.expenses.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.division.of.expenses.app.model.EventMember;

@Repository
public interface EventMemberRepository extends JpaRepository<EventMember, Long>, JpaSpecificationExecutor<EventMember> {
}
