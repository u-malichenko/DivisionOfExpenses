package ru.division.of.expenses.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.division.of.expenses.app.model.Event;
import ru.division.of.expenses.app.model.Role;
import ru.division.of.expenses.app.model.User;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {

    private Long id;
    private String firstname;
    private String lastname;
    private String username;
    private List<String> roles;
    private List<String> events;

    public UserDto(User user) {

        this.id = user.getId();
        this.firstname = user.getFirstName();
        this.lastname = user.getLastName();
        this.username = user.getUsername();
//        if(this.id != null) {
            this.roles = user.getRoles().stream()
                    .map(Role::getName)
                    .collect(Collectors.toList());
            this.events = user.getEventList().stream()
                    .map(Event::getName)
                    .collect(Collectors.toList());
//        }

    }
}
