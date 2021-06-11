package ru.division.of.expenses.app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.division.of.expenses.app.model.DirectPayer;
import ru.division.of.expenses.app.model.Event;
import ru.division.of.expenses.app.model.Expense;
import ru.division.of.expenses.app.model.PartitialPayer;

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

    @Query(
            value = "SELECT ed.user.username FROM Expense e JOIN e.directPayersList ed WHERE e.id =:expenseId"
    )
    List<String> findDirectPayerUsernameList(@Param("expenseId") Long expenseId);

    @Query(
            value = "SELECT ep.user.username FROM Expense e JOIN e.partitialPayersList ep WHERE e.id =:expenseId"
    )
    List<String> findPartitialPayerUsernameList(@Param("expenseId") Long expenseId);

    @Query(
            value = "SELECT e FROM Expense e WHERE e.buyer.username =:username"
    )
    List<Expense> findExpenseByBuyerUsername(@Param("username") String username);

    @Query(
            value = "SELECT e FROM Expense e JOIN e.directPayersList ed WHERE ed.user.username =:username"
    )
    List<Expense> findExpenseByDirectPayersListByUsername(@Param("username") String username);

    @Query(
            value = "SELECT e FROM Expense e JOIN e.partitialPayersList ep WHERE ep.user.username =:username"
    )
    List<Expense> findExpenseByPartitialPayersListByUsername(@Param("username") String username);


}
