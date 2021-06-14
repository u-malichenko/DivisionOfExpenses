package ru.division.of.expenses.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.division.of.expenses.app.model.DirectPayer;
import ru.division.of.expenses.app.model.Expense;
import ru.division.of.expenses.app.model.PartitialPayer;
import ru.division.of.expenses.app.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface DirectPayersRepository extends JpaRepository<DirectPayer, Long>, JpaSpecificationExecutor<DirectPayer> {

    Optional<List<DirectPayer>> findByExpense(Expense expense);

    @Query(
            value = "SELECT dp FROM DirectPayer dp WHERE dp.expense =:expense AND dp.user =:user"
    )
    Optional<DirectPayer> findDirectPayerByExpenseAndUser(@Param("expense") Expense expense, @Param("user") User user);

}
