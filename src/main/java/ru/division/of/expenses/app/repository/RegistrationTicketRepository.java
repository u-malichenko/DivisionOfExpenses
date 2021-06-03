package ru.division.of.expenses.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.division.of.expenses.app.model.RegistrationTicket;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Repository
public interface
RegistrationTicketRepository extends JpaRepository<RegistrationTicket, Long>, JpaSpecificationExecutor<RegistrationTicket> {

    @Query(
            value = "SELECT r FROM RegistrationTicket r WHERE r.createDate < :oneHourAgo"
    )
    List<RegistrationTicket> findOldTickets(@Param("oneHourAgo")Calendar oneHourAgo);

    Optional<RegistrationTicket> findByCheckingticket(String checkingTicket);
}
