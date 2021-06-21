package ru.division.of.expenses.app.exceptionhandling;

public class EventNotFoundException extends Exception{
    public EventNotFoundException(String message) {
        super(message);
    }
}
