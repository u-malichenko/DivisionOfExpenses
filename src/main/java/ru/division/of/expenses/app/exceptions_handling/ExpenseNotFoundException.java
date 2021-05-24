package ru.division.of.expenses.app.exceptions_handling;

public class ExpenseNotFoundException extends Exception{
    public ExpenseNotFoundException(String message) {
        super(message);
    }
}
