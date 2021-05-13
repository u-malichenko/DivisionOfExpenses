package ru.division.of.expenses.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.division.of.expenses.app.model.User;
import ru.division.of.expenses.app.repository.UserRepository;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> findAllUsers(
            int page,
            int size
    ) {
        return userRepository.findAllUsers(PageRequest.of(page, size));
    }

    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> findUsersByName(
            String name,
            int page,
            int size
    ) {
        return userRepository.findUsersByName(name, PageRequest.of(page, size));
    }
}
