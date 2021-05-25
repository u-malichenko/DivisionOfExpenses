package ru.division.of.expenses.app.exceptions_handling;

public class UserNotFoundException extends Exception{

    public UserNotFoundException(String message) {
        super(message);
    }
}
