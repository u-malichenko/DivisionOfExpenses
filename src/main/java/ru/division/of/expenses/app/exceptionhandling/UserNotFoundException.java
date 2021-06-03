package ru.division.of.expenses.app.exceptionhandling;

public class UserNotFoundException extends Exception{

    public UserNotFoundException(String message) {
        super(message);
    }
}
