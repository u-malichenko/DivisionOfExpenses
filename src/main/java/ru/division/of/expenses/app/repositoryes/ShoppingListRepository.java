package ru.division.of.expenses.app.repositoryes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.division.of.expenses.app.models.ShoppingList;

@Repository
public interface ShoppingListRepository extends JpaRepository<ShoppingList, Long>, JpaSpecificationExecutor<ShoppingList> {
}
