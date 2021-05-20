package ru.division.of.expenses.app.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.division.of.expenses.app.models.PartitialPayer;
import ru.division.of.expenses.app.repositoryes.PartitialPayersRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PartitialPayersService {

    private final PartitialPayersRepository partitialPayersRepository;

    public Optional<PartitialPayer> findById(Long id) {
        return partitialPayersRepository.findById(id);
    }

    public Page<PartitialPayer> findAll(
            int page,
            int size
    ) {
        return partitialPayersRepository.findAll(PageRequest.of(page, size));
    }

}
