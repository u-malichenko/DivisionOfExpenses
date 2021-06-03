package ru.division.of.expenses.app.service;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.division.of.expenses.app.exceptionhandling.CheckingTicketNotFoundException;
import ru.division.of.expenses.app.model.RegistrationTicket;
import ru.division.of.expenses.app.repository.RegistrationTicketRepository;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegistrationTicketService {

    private final RegistrationTicketRepository registrationTicketRepository;

    public void save(RegistrationTicket registrationTicket){
        registrationTicket.setCheckingticket(UUID.randomUUID().toString());
        registrationTicketRepository.save(registrationTicket);
    }

    public void checkAndRemoveOldTickets(){
        Calendar calendar=new GregorianCalendar();
        calendar.add(Calendar.HOUR, -1);
        List<RegistrationTicket> oldTicketList=registrationTicketRepository.findOldTickets(calendar);
        registrationTicketRepository.deleteAll(oldTicketList);
    }

    public RegistrationTicket checkRegistrationTicket(String checkingTicket) throws CheckingTicketNotFoundException {
        checkAndRemoveOldTickets();
        return registrationTicketRepository.findByCheckingticket(checkingTicket).orElseThrow(() -> new CheckingTicketNotFoundException(String.format("User '%s' not found", checkingTicket)));
    }
}
