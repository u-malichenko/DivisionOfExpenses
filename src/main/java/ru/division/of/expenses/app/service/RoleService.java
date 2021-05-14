package ru.division.of.expenses.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.division.of.expenses.app.model.Role;
import ru.division.of.expenses.app.repository.RoleRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Optional<List<Role>> findRoleByName(String name) {
        return roleRepository.findByName(name);
    }

}
