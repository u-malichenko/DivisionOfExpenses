package ru.division.of.expenses.app.repositoryes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.division.of.expenses.app.models.Event;
import ru.division.of.expenses.app.models.User;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

}

