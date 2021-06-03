package ru.division.of.expenses.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ru.division.of.expenses.app.model.ShoppingList;
import ru.division.of.expenses.app.service.ShoppingListService;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/shopList")
public class ShoppingListController {

    private final ShoppingListService shoppingListService;

    @GetMapping("/{id}")
    public Optional<ShoppingList> findById(@PathVariable Long id){
        return shoppingListService.findById(id);
    }

    @GetMapping
    public Page<ShoppingList> findAll(@RequestParam(required = false, defaultValue = "0") int page ,
                                      @RequestParam(required = false, defaultValue = "10") int size){
        return shoppingListService.findAll(page, size);
    }



}
