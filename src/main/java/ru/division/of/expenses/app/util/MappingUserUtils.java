package ru.division.of.expenses.app.util;

import org.springframework.stereotype.Component;
import ru.division.of.expenses.app.dto.UserDto;
import ru.division.of.expenses.app.model.Event;
import ru.division.of.expenses.app.model.Role;
import ru.division.of.expenses.app.model.User;

import java.util.stream.Collectors;

@Component
public class MappingUserUtils {

    public UserDto mapToUserDto(User user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstname(user.getFirstName());
        userDto.setLastname(user.getLastName());
        userDto.setUsername(user.getUsername());
        userDto.setRoles(user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toList()));
        userDto.setEvents(user.getEventList().stream()
        .map(Event::getTitle)
        .collect(Collectors.toList()));
        return userDto;
    }
}
