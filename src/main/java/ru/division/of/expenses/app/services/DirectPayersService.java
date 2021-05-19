package ru.division.of.expenses.app.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.division.of.expenses.app.models.DirectPayer;
import ru.division.of.expenses.app.models.Expense;
import ru.division.of.expenses.app.repositoryes.DirectPayersRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DirectPayersService {

    private final DirectPayersRepository directPayersRepository;

    public Optional<DirectPayer> findById(Long id) {
        return directPayersRepository.findById(id);
    }

    public Page<DirectPayer> findAll(
            int page,
            int size
    ) {
        return directPayersRepository.findAll(PageRequest.of(page, size));
    }

    public Optional<List<DirectPayer>> findAllByOrders(Expense order) {
        return directPayersRepository.findByOrder(order);
    }

}
