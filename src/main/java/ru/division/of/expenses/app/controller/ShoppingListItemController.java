package ru.division.of.expenses.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ru.division.of.expenses.app.model.ShoppingListItem;
import ru.division.of.expenses.app.service.ShoppingListItemService;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/shopListItem")
public class ShoppingListItemController {

    private final ShoppingListItemService shoppingListItemService;

    @GetMapping("/{id}")
    public Optional<ShoppingListItem> findById(@PathVariable Long id){
        return shoppingListItemService.findById(id);
    }

    @GetMapping
    public Page<ShoppingListItem> findAll(@RequestParam(required = false, defaultValue = "0") int page ,
                                          @RequestParam(required = false, defaultValue = "10") int size){
        return shoppingListItemService.findAll(page, size);
    }



}
