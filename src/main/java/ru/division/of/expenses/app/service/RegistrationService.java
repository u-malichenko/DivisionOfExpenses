package ru.division.of.expenses.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.division.of.expenses.app.dto.UserRegistrationDto;
import ru.division.of.expenses.app.exceptionhandling.UserAlreadyExistsException;
import ru.division.of.expenses.app.model.RegistrationTicket;
import ru.division.of.expenses.app.model.User;
import ru.division.of.expenses.app.repository.RegistrationTicketRepository;
import ru.division.of.expenses.app.repository.UserRepository;
import ru.division.of.expenses.app.util.EmptyJsonResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final UserRepository userRepository;
    private final RegistrationTicketRepository registrationTicketRepository;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<?> createUser(UserRegistrationDto userRegistrationDto) {
        List<String> usernameList = userRepository.findAllUsername();
        try {
            if (usernameList.contains(userRegistrationDto.getUsername())) {
                throw new UserAlreadyExistsException("User " + userRegistrationDto.getUsername() + " is already exists");
            }
        } catch (UserAlreadyExistsException e) {
//            e.printStackTrace();
            System.out.println(e);
            return new ResponseEntity<EmptyJsonResponse>(new EmptyJsonResponse(), HttpStatus.OK);
        }
        User user = new User();
        user.setFirstName(userRegistrationDto.getFirstname());
        user.setUsername(userRegistrationDto.getUsername());
        user.setLastName(userRegistrationDto.getLastname());
        user.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));
        user.setEmail(userRegistrationDto.getEmail());
        return new ResponseEntity<User>(userRepository.save(user), HttpStatus.OK);
    }

    public boolean check(UserRegistrationDto userRegistrationDto) {
        String username = userRegistrationDto.getUsername();
        String email = userRegistrationDto.getEmail();
        Boolean foundFlag = false;

        if (userRepository.findByUsername(username).isPresent() ||
                userRepository.findByEmail(email).isPresent() ||
                registrationTicketRepository.findByUsername(username).isPresent() ||
                registrationTicketRepository.findByEmail(email).isPresent())
            foundFlag = true;
        return foundFlag;
    }

}
