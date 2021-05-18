package ru.division.of.expenses.app.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class JwtRequest {
    private String username;
    private String password;
    private UUID cartId;
}
