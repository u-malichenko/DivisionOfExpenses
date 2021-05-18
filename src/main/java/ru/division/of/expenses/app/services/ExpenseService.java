package ru.division.of.expenses.app.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.division.of.expenses.app.models.Expense;
import ru.division.of.expenses.app.repositoryes.ExpenseRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    public Optional<Expense> findById(Long id) {
        return expenseRepository.findById(id);
    }

    public Page<Expense> findAll(
            int page,
            int size
    ) {
        return expenseRepository.findAll(PageRequest.of(page, size));
    }

}
