package ru.division.of.expenses.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.division.of.expenses.app.dto.EventDto1;
import ru.division.of.expenses.app.dto.UserRegistrationDto;
import ru.division.of.expenses.app.exceptionhandling.CheckingTicketNotFoundException;
import ru.division.of.expenses.app.model.RegistrationTicket;
import ru.division.of.expenses.app.service.RegistrationService;
import ru.division.of.expenses.app.service.RegistrationTicketService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/registration")
public class RegistrationController {

//    private final UserService userService;
    private final RegistrationService registrationService;
    private final RegistrationTicketService registrationTicketService;

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

    @GetMapping("/{checkingTicket}")
    public ResponseEntity<?> acceptingRegister(
            @PathVariable String checkingTicket
    ){
        RegistrationTicket registrationTicket;
        try {
            registrationTicket=registrationTicketService.checkRegistrationTicket(checkingTicket);
            registrationService.createUser(new UserRegistrationDto(registrationTicket));
            registrationTicketService.delete(registrationTicket);

        } catch (CheckingTicketNotFoundException e) {
            return new ResponseEntity<String>("checking ticket "+ checkingTicket + " not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>("User was successfully created", HttpStatus.CREATED);
    }

}
