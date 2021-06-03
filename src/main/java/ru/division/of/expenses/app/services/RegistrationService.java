package ru.division.of.expenses.app.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.division.of.expenses.app.dto.UserRegistrationDto;
import ru.division.of.expenses.app.exceptions_handling.UserAlreadyExistsException;
import ru.division.of.expenses.app.models.User;
import ru.division.of.expenses.app.repositoryes.UserRepository;
import ru.division.of.expenses.app.utils.EmptyJsonResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<?> createUser(UserRegistrationDto userRegistrationDto){
        List<String> usernameList = userRepository.findAllUsername();
        try {
            if(usernameList.contains(userRegistrationDto.getUsername())){
                throw new UserAlreadyExistsException("User " + userRegistrationDto.getUsername() + " is already exists");
            }
        }catch (UserAlreadyExistsException e) {
//            e.printStackTrace();
            System.out.println(e);
            return new ResponseEntity<EmptyJsonResponse>(new EmptyJsonResponse(), HttpStatus.OK);
        }
        User user = new User();
        user.setFirstName(userRegistrationDto.getFirstname());
        user.setUsername(userRegistrationDto.getUsername());
        user.setLastName(userRegistrationDto.getLastname());
        user.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));
        return new ResponseEntity<User>(userRepository.save(user), HttpStatus.OK);
    }
}
