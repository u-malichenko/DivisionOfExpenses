package ru.division.of.expenses.app.service;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.division.of.expenses.app.dto.UserRegistrationDto;
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

    public RegistrationTicket save(RegistrationTicket registrationTicket){
        checkAndRemoveOldTickets();
        registrationTicket.setCheckingticket(UUID.randomUUID().toString());
        return registrationTicketRepository.save(registrationTicket);
    }

    private void checkAndRemoveOldTickets(){
        Calendar calendar=new GregorianCalendar();
        calendar.add(Calendar.HOUR, -1);
        List<RegistrationTicket> oldTicketList=registrationTicketRepository.findOldTickets(calendar);
        registrationTicketRepository.deleteAll(oldTicketList);
    }

    public RegistrationTicket checkRegistrationTicket(String checkingTicket) throws CheckingTicketNotFoundException {
        checkAndRemoveOldTickets();
        return registrationTicketRepository.findByCheckingticket(checkingTicket).orElseThrow(() -> new CheckingTicketNotFoundException(String.format("User '%s' not found", checkingTicket)));
    }

    public void delete(RegistrationTicket registrationTicket) {
        registrationTicketRepository.delete(registrationTicket);
    }

    public void deleteByUsername(String username){
        RegistrationTicket registrationTicket = registrationTicketRepository.findByUsername(username).get();
        registrationTicketRepository.delete(registrationTicket);
    }

    public RegistrationTicket save(UserRegistrationDto userRegistrationDto) {
        RegistrationTicket registrationTicket=new RegistrationTicket();
        registrationTicket.setUsername(userRegistrationDto.getUsername());
        registrationTicket.setFirstName(userRegistrationDto.getFirstname());
        registrationTicket.setLastName(userRegistrationDto.getLastname());
        registrationTicket.setPassword(userRegistrationDto.getPassword());
        registrationTicket.setEmail(userRegistrationDto.getEmail());
        return save(registrationTicket);
    }
}
