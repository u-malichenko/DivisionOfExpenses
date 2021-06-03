package ru.division.of.expenses.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.division.of.expenses.app.model.ShoppingListItem;
import ru.division.of.expenses.app.repository.ShoppingListItemRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShoppingListItemService {

    private final ShoppingListItemRepository shoppingListItemRepository;

    public Optional<ShoppingListItem> findById(Long id) {
        return shoppingListItemRepository.findById(id);
    }

    public Page<ShoppingListItem> findAll(
            int page,
            int size
    ) {
        return shoppingListItemRepository.findAll(PageRequest.of(page, size));
    }

}
