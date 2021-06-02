package ru.division.of.expenses.app.repositoryes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.division.of.expenses.app.models.EventMember;

@Repository
public interface EventMemberRepository extends JpaRepository<EventMember, Long>, JpaSpecificationExecutor<EventMember> {
}
