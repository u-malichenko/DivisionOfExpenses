package ru.division.of.expenses.app.exceptionhandling;

public class ExpenseNotFoundException extends Exception{
    public ExpenseNotFoundException(String message) {
        super(message);
    }
}
