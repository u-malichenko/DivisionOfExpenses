package ru.division.of.expenses.app.service;

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
import ru.division.of.expenses.app.exceptionhandling.UserNotFoundException;
import ru.division.of.expenses.app.model.Event;
import ru.division.of.expenses.app.model.Role;
import ru.division.of.expenses.app.model.User;
import ru.division.of.expenses.app.repository.UserRepository;
import ru.division.of.expenses.app.util.MappingEventUtils;
import ru.division.of.expenses.app.util.MappingUserUtils;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
    private final MappingUserUtils mappingUserUtils;
    private final MappingEventUtils mappingEventUtils;

//    public ResponseEntity<?> createUser(UserRegistrationDto userRegistrationDto){
//        List<String> usernameList = userRepository.findAllUsername();
//        try {
//            if(usernameList.contains(userRegistrationDto.getUsername())){
//                throw new UserAlreadyExistsException("User " + userRegistrationDto.getUsername() + " is already exists");
//            }
//        }catch (UserAlreadyExistsException e) {
////            e.printStackTrace();
//            System.out.println(e);
//            return new ResponseEntity<EmptyJsonResponse>(new EmptyJsonResponse(), HttpStatus.OK);
//        }
//        User user = new User();
//        user.setFirstName(userRegistrationDto.getFirstname());
//        user.setUsername(userRegistrationDto.getUsername());
//        user.setLastName(userRegistrationDto.getLastname());
//        user.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));
//        return new ResponseEntity<User>(userRepository.save(user), HttpStatus.OK);
//    }

    public void deleteUser(Long userId) {
        User user = findUserByIdBasic(userId);
        userRepository.delete(user);
    }

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

//    public UserDto findUserById(Long id) throws UserNotFoundException {
//        User user = userRepository.findById(id)
//                .orElseThrow(
//                        () -> new UserNotFoundException("Event: " + id + " not found.")
//                );
//        return new UserDto(user);
//    }

    public Page<User> findUsersByName(
            String name,
            int page,
            int size
    ) {
        return userRepository.findUsersByName(name, PageRequest.of(page, size));
    }

    public Page<Event> findEventlistById(
            Long id,
            int page,
            int size
    ){
        return userRepository.findEventListById(id, PageRequest.of(page, size));
    }

    public Page<EventDto> findEventDtolistById(
            Long id,
            int page,
            int size
    ){
        return userRepository.findEventListById(id, PageRequest.of(page, size)).map(mappingEventUtils::mapToEventDto);
    }

    public User findUserByIdBasic(Long id){
        User user = new User();
        try {
            user = userRepository.findById(id)
                    .orElseThrow(
                            () -> new UserNotFoundException("User with id=" + id + " not found.")
                    );
        }catch (UserNotFoundException e) {
//            e.printStackTrace();
            System.out.println(e);
        }
        return user;
    }

    public User findByUsernameBasic(String username){
        User user = new User();
        try {
            user = userRepository.findByUsername(username)
                    .orElseThrow(
                            () -> new UsernameNotFoundException(String.format("User '%s' not found", username))
                    );
        }catch (UsernameNotFoundException e) {
//            e.printStackTrace();
            System.out.println(e);
        }
        return user;
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
