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
            calculateExpense(expense, eventMembers);
            event.setTotalEventSum(event.getTotalEventSum().add(expense.getTotalExpenseSum()));
        }
        eventRepository.save(event);
    }

    public void calculateExpense(Expense expense, Collection<EventMember> eventMemberList) {

        BigDecimal summa = expense.getTotalExpenseSum();
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
                    eventMember.setSaldo(eventMember.getSaldo().add(summa.divide(BigDecimal.valueOf(partitialListSize), 2, RoundingMode.HALF_UP).multiply(partitialPayer.getCoefficient())));
            }

        }

        for (EventMember eventMember : eventMemberList
        ) {
            if (eventMember.getUser().equals(buyer))
                eventMember.setSaldo(eventMember.getSaldo().subtract(expense.getTotalExpenseSum()));
        }
        System.out.println("hi");

    }

    private void resetEventMemberSaldo(Collection<EventMember> eventMemberList) {
        for (EventMember eventMember : eventMemberList
        ) {
            eventMember.setSaldo(new BigDecimal("0.00"));
        }
    }

}
