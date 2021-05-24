package ru.division.of.expenses.app.exceptions_handling;

public class EventNotFoundException extends Exception{
    public EventNotFoundException(String message) {
        super(message);
    }
}
