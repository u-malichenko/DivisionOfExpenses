package ru.division.of.expenses.app.services;

import ru.division.of.expenses.app.models.DirectPayer;
import ru.division.of.expenses.app.models.Event;
import ru.division.of.expenses.app.models.EventMember;
import ru.division.of.expenses.app.models.Expense;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

public class DivisionOfExpenseService {

    public void calculateEvent(Event event) {
        event.setTotalEventSum(new BigDecimal("0.00"));
        for (Expense expense : event.getExpenseList()
        ) {
            calculateExpense(expense, event.getEventMembers());
            event.setTotalEventSum(event.getTotalEventSum().add(expense.getTotalExpenseSum()));
        }
    }

    public void calculateExpense(Expense expense, Collection<EventMember> eventMemberList) {

        BigDecimal summa = expense.getTotalExpenseSum();
        for (DirectPayer directPayer : expense.getDirectPayersList()) {
            summa = summa.subtract(directPayer.getSumma());
            for (EventMember eventMember : eventMemberList
            ) {
                if (eventMember.getUser().equals(directPayer.getUser()))
                    eventMember.setSaldo(eventMember.getSaldo().add(directPayer.getSumma()));
            }
        }


        expense.getPartitialPayersList();

    }

}
