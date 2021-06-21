package ru.division.of.expenses.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.division.of.expenses.app.model.*;
import ru.division.of.expenses.app.repository.EventRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DivisionOfExpenseService {

    private final EventRepository eventRepository;

    public void calculateEvent(Event event) {
        event.setTotalEventSum(new BigDecimal("0.00"));
        Collection<EventMember> eventMembers = event.getEventMembers();
        resetEventMemberSaldo(eventMembers);
        for (Expense expense : event.getExpenseList()
        ) {
//            calculateExpense(expense, eventMembers);
//            event.setTotalEventSum(event.getTotalEventSum().add(expense.getTotalExpenseSum()));
            calculateExpense(expense);
        }
        eventRepository.save(event);
    }

//    public void calculateExpense(Expense expense, Collection<EventMember> eventMemberList) {
      public void calculateExpense(Expense expense) {
        Collection<EventMember> eventMemberList = expense.getEvent().getEventMembers();
        Event event = expense.getEvent();
        BigDecimal summa = expense.getTotalExpenseSum();
        event.setTotalEventSum(event.getTotalEventSum().add(expense.getTotalExpenseSum()));
        User buyer = expense.getBuyer();
        for (DirectPayer directPayer : expense.getDirectPayersList()) {
            summa = summa.subtract(directPayer.getSumma());
            for (EventMember eventMember : eventMemberList
            ) {
                if (eventMember.getUser().equals(directPayer.getUser()))
                    eventMember.setSaldo(eventMember.getSaldo().add(directPayer.getSumma()));
            }
        }
        List<PartitialPayer> partitialPayersList = expense.getPartitialPayersList();
        int partitialListSize = partitialPayersList.size();


        for (PartitialPayer partitialPayer : partitialPayersList
        ) {
            for (EventMember eventMember : eventMemberList
            ) {
                if (eventMember.getUser().equals(partitialPayer.getUser()))
//                    eventMember.setSaldo(eventMember.getSaldo().add(summa.divide(BigDecimal.valueOf(partitialListSize)).multiply(partitialPayer.getCoefficient())));
                      eventMember.setSaldo(eventMember.getSaldo().add(summa.divide(BigDecimal.valueOf(partitialListSize), 2, RoundingMode.HALF_UP).multiply(partitialPayer.getCoefficient())));
            }

        }

        for (EventMember eventMember : eventMemberList
        ) {
            if (eventMember.getUser().equals(buyer))
                eventMember.setSaldo(eventMember.getSaldo().subtract(expense.getTotalExpenseSum()));
        }
//        System.out.println("hi");

    }

    public void rollbackSaldoChangingExpenseBeforeUpdate(Expense oldExpense) {
        Collection<EventMember> eventMemberList = oldExpense.getEvent().getEventMembers();
        Event event = oldExpense.getEvent();
        BigDecimal summa = oldExpense.getTotalExpenseSum();
        User buyer = oldExpense.getBuyer();
        event.setTotalEventSum(event.getTotalEventSum().subtract(oldExpense.getTotalExpenseSum()));

        for (DirectPayer directPayer : oldExpense.getDirectPayersList()) {
            summa = summa.subtract(directPayer.getSumma());
            for (EventMember eventMember : eventMemberList
            ) {
                if (eventMember.getUser().equals(directPayer.getUser()))
                    eventMember.setSaldo(eventMember.getSaldo().subtract(directPayer.getSumma()));
            }
        }
        List<PartitialPayer> partitialPayersList = oldExpense.getPartitialPayersList();
        int partitialListSize = partitialPayersList.size();


        for (PartitialPayer partitialPayer : partitialPayersList
        ) {
            for (EventMember eventMember : eventMemberList
            ) {
                if (eventMember.getUser().equals(partitialPayer.getUser()))
                    eventMember.setSaldo(eventMember.getSaldo().subtract(summa.divide(BigDecimal.valueOf(partitialListSize), 2, RoundingMode.HALF_UP).multiply(partitialPayer.getCoefficient())));
            }

        }

        for (EventMember eventMember : eventMemberList
        ) {
            if (eventMember.getUser().equals(buyer))
                eventMember.setSaldo(eventMember.getSaldo().add(oldExpense.getTotalExpenseSum()));
        }
    }

    private void resetEventMemberSaldo(Collection<EventMember> eventMemberList) {
        for (EventMember eventMember : eventMemberList
        ) {
            eventMember.setSaldo(new BigDecimal("0.00"));
        }
    }

}
