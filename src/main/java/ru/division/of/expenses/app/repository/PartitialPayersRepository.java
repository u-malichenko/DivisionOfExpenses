package ru.division.of.expenses.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.division.of.expenses.app.model.DirectPayer;
import ru.division.of.expenses.app.model.Expense;
import ru.division.of.expenses.app.model.PartitialPayer;

import java.util.List;
import java.util.Optional;

@Repository
public interface PartitialPayersRepository extends JpaRepository<PartitialPayer, Long>, JpaSpecificationExecutor<PartitialPayer> {

    @Query(
            value = "SELECT pp FROM PartitialPayer pp WHERE pp.expense.id =: expenseId "
    )
    List<PartitialPayer> findPartitialPayerByExpenseId(@Param("expenseId") Long expenseId);

    Optional<List<PartitialPayer>> findByExpense(Expense expense);
}
