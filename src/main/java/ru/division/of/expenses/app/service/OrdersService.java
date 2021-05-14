package ru.division.of.expenses.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.division.of.expenses.app.model.Orders;
import ru.division.of.expenses.app.repository.OrdersRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrdersService {

    private final OrdersRepository ordersRepository;

    public Optional<Orders> findById(Long id) {
        return ordersRepository.findById(id);
    }

    public Page<Orders> findAll(
            int page,
            int size
    ) {
        return ordersRepository.findAll(PageRequest.of(page, size));
    }

}
