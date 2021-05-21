package ru.division.of.expenses.app.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.division.of.expenses.app.dto.EventDto;
import ru.division.of.expenses.app.dto.UserDto;
import ru.division.of.expenses.app.models.Event;
import ru.division.of.expenses.app.models.User;
import ru.division.of.expenses.app.services.UserService;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public Optional<User> findUserBYId(@PathVariable Long id){
        return userService.findUserById(id);
    }

    @GetMapping("/dto/{id}")
    public UserDto findUserDtoById(@PathVariable Long id){
        return userService.findUserDtoById(id);
    }

    @GetMapping
    public Page<User> findAllUsers(@RequestParam(required = false, defaultValue = "0") int page ,
                              @RequestParam(required = false, defaultValue = "10") int size){
        return userService.findAllUsers(page, size);
    }

    @GetMapping("/dto")
    public Page<UserDto> findAllUserDto(@RequestParam(required = false, defaultValue = "0") int page ,
                                        @RequestParam(required = false, defaultValue = "10") int size){
        return userService.findAllUserDto(page, size);
    }

    ///////////////////////////////////////////////////////////
    @GetMapping("/event/{id}")
    public Page<Event> findEventlistById(@PathVariable Long id,
                                         @RequestParam(required = false, defaultValue = "0") int page ,
                                         @RequestParam(required = false, defaultValue = "10") int size){
        return userService.findEventlistById(id, page, size);
    }

    @GetMapping("/event/dto/{id}")
    public Page<EventDto> findEventDtolistById(@PathVariable Long id,
                                              @RequestParam(required = false, defaultValue = "0") int page ,
                                              @RequestParam(required = false, defaultValue = "10") int size){
        return userService.findEventDtolistById(id, page, size);
    }
    ///////////////////////////////////////////////


    @GetMapping("/name/{name}")
    public Page<User> findUserByName(@PathVariable String name,
                                     @RequestParam(required = false, defaultValue = "0") int page ,
                                     @RequestParam(required = false, defaultValue = "10") int size){
        return userService.findUsersByName(name, page, size);
    }

    @GetMapping("/username/{username}")
    public Optional<User> findByUsername(@PathVariable String username){
        return userService.findByUsername(username);
    }


}
