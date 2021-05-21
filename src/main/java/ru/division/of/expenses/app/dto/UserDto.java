package ru.division.of.expenses.app.dto;

import lombok.Data;

import java.util.Collection;
import java.util.List;

@Data
public class UserDto {

    private String firstname;
    private String lastname;
    private String username;
    private List<String> roles;
    private List<String> events;
}
