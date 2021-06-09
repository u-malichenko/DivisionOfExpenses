package ru.division.of.expenses.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.division.of.expenses.app.model.Event;
import ru.division.of.expenses.app.model.Expense;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long>, JpaSpecificationExecutor<Expense> {

    @Query(
            value = "SELECT e.buyer.username FROM Expense e WHERE e.id =:expenseId"
    )
    String findBuyerUsernameByExpenseId(@Param("expenseId") Long expenseId);

    @Query(
            value = "SELECT e FROM Expense e WHERE e.event =:event"
    )
    List<Expense> findExpenseByEvent(@Param("event") Event event);
}
