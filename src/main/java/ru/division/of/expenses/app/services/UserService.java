package ru.division.of.expenses.app.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.division.of.expenses.app.dto.EventDto;
import ru.division.of.expenses.app.dto.UserDto;
import ru.division.of.expenses.app.models.Event;
import ru.division.of.expenses.app.models.Role;
import ru.division.of.expenses.app.models.User;
import ru.division.of.expenses.app.repositoryes.UserRepository;
import ru.division.of.expenses.app.utils.MappingUserUtils;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final MappingUserUtils mappingUserUtils;

    public Page<User> findAllUsers(
            int page,
            int size
    ) {
        return userRepository.findAllUsers(PageRequest.of(page, size));
    }

    public Page<UserDto> findAllUserDto(
            int page,
            int size
    ) {
        return userRepository.findAll(PageRequest.of(page, size)).map(mappingUserUtils::mapToUserDto);
    }

    public UserDto findUserDtoById(Long id){
        return mappingUserUtils.mapToUserDto(userRepository.findById(id).orElse(new User()));
    }

    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    public Page<User> findUsersByName(
            String name,
            int page,
            int size
    ) {
        return userRepository.findUsersByName(name, PageRequest.of(page, size));
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", username)));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

}
