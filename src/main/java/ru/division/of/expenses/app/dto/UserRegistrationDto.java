package ru.division.of.expenses.app.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.division.of.expenses.app.model.RegistrationTicket;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRegistrationDto {

    @NotNull
    private String firstname;
    @NotNull
    private String lastname;
    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private String email;

    public UserRegistrationDto(RegistrationTicket registrationTicket) {
        this.firstname = registrationTicket.getFirstName();
        this.lastname = registrationTicket.getLastName();
        this.username = registrationTicket.getUsername();
        this.password = registrationTicket.getPassword();
        this.email = registrationTicket.getEmail();
    }
}
