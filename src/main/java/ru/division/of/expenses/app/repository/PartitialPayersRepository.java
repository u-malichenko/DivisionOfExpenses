package ru.division.of.expenses.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.division.of.expenses.app.model.PartitialPayer;

@Repository
public interface PartitialPayersRepository extends JpaRepository<PartitialPayer, Long>, JpaSpecificationExecutor<PartitialPayer> {
}
