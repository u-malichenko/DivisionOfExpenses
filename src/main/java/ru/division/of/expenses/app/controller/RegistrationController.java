package ru.division.of.expenses.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.division.of.expenses.app.dto.UserRegistrationDto;
import ru.division.of.expenses.app.exceptionhandling.CheckingTicketNotFoundException;
import ru.division.of.expenses.app.model.RegistrationTicket;
import ru.division.of.expenses.app.service.RegistrationService;
import ru.division.of.expenses.app.service.RegistrationTicketService;
import ru.division.of.expenses.app.util.EmailSendingUtility;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

@RestController
@RequiredArgsConstructor
@RequestMapping("/registration")
public class RegistrationController {

    //    private final UserService userService;
    private final RegistrationService registrationService;
    private final RegistrationTicketService registrationTicketService;
    private final EmailSendingUtility emailSendingUtility;

    @PostMapping
    public ResponseEntity<?> registration(
            @RequestBody UserRegistrationDto userRegistrationDto,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<String>("User cant be registered", HttpStatus.NOT_ACCEPTABLE);
        }
        if (registrationService.check(userRegistrationDto)) {
            return new ResponseEntity<String>("User with this email or username is already exist", HttpStatus.NOT_ACCEPTABLE);
        } else {
            RegistrationTicket registrationTicket = registrationTicketService.save(userRegistrationDto);
            Calendar timeBefore = new GregorianCalendar();
            timeBefore.add(Calendar.HOUR, +1);
            String theme = "registration on Division of Expense";
            String message = "Welcome to Division of Expenses.\n Please, visit next link: http://localhost:8189/registration/" +
                    registrationTicket.getCheckingticket() +
                    " it will be active before " +
                    new SimpleDateFormat("yyyyMMdd_HHmmss").format(timeBefore.getTime());
            emailSendingUtility.sendEmail(registrationTicket.getEmail(), theme, message);
            return new ResponseEntity<String>("", HttpStatus.ACCEPTED);
        }

    }

    @GetMapping("/{checkingTicket}")
    public ResponseEntity<?> acceptingRegister(
            @PathVariable String checkingTicket
    ) {
        RegistrationTicket registrationTicket;
        try {
            registrationTicket = registrationTicketService.checkRegistrationTicket(checkingTicket);
            registrationService.createUser(new UserRegistrationDto(registrationTicket));
            registrationTicketService.delete(registrationTicket);

        } catch (CheckingTicketNotFoundException e) {
            return new ResponseEntity<String>("checking ticket " + checkingTicket + " not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>("User was successfully created", HttpStatus.CREATED);
    }

    @DeleteMapping("/{username}")
    public void removeRegistrationTicketByUsername(
            @PathVariable String username
    ){
        registrationTicketService.deleteByUsername(username);
    }

}
