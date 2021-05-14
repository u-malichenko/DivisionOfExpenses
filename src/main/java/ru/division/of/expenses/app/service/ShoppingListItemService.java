package ru.division.of.expenses.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.division.of.expenses.app.repository.ShoppingListItemRepository;

@Service
@RequiredArgsConstructor
public class ShoppingListItemService {

    private final ShoppingListItemRepository shoppingListItemRepository;

}
