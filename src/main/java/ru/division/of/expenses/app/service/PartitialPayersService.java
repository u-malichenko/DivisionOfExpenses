package ru.division.of.expenses.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.division.of.expenses.app.model.PartitialPayer;
import ru.division.of.expenses.app.repository.PartitialPayersRepository;

@Service
@RequiredArgsConstructor
public class PartitialPayersService {

    private final PartitialPayersRepository partitialPayersRepository;

    public Page<PartitialPayer> findAll(Pageable pageable) {
        return partitialPayersRepository.findAll(pageable);
    }

}
