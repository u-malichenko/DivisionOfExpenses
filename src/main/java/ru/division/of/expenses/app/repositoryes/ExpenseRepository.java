package ru.division.of.expenses.app.repositoryes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.division.of.expenses.app.models.Expense;
import ru.division.of.expenses.app.models.User;

import java.util.Optional;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long>, JpaSpecificationExecutor<Expense> {

    @Query(
            value = "SELECT e.buyer.username FROM Expense e WHERE e.id =:expenseId"
    )
    String findBuyerUsernameByExpenseId(@Param("expenseId") Long expenseId);
}
