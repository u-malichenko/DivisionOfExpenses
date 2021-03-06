package ru.division.of.expenses.app.repositoryes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.division.of.expenses.app.models.DirectPayer;
import ru.division.of.expenses.app.models.Expense;

import java.util.List;
import java.util.Optional;

@Repository
public interface DirectPayersRepository extends JpaRepository<DirectPayer, Long>, JpaSpecificationExecutor<DirectPayer> {

    Optional<List<DirectPayer>> findByExpense(Expense expense);

}
