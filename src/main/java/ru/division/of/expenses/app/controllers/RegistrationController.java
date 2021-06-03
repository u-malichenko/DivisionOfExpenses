package ru.division.of.expenses.app.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.division.of.expenses.app.dto.UserRegistrationDto;
import ru.division.of.expenses.app.services.RegistrationService;
import ru.division.of.expenses.app.services.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/registration")
public class RegistrationController {

//    private final UserService userService;
    private final RegistrationService registrationService;

    @PostMapping
    public void registration(
            @RequestBody UserRegistrationDto userRegistrationDto,
            BindingResult bindingResult
    ){
        if (bindingResult.hasErrors()) {
            return;
        }
//        userService.createUser(userRegistrationDto);
        registrationService.createUser(userRegistrationDto);
    }

}
