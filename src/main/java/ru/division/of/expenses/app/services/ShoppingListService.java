package ru.division.of.expenses.app.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.division.of.expenses.app.models.ShoppingList;
import ru.division.of.expenses.app.repositoryes.ShoppingListRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShoppingListService {

    private final ShoppingListRepository shoppingListRepository;

    public Optional<ShoppingList> findById(Long id) {
        return shoppingListRepository.findById(id);
    }

    public Page<ShoppingList> findAll(
            int page,
            int size
    ) {
        return shoppingListRepository.findAll(PageRequest.of(page, size));
    }

}
