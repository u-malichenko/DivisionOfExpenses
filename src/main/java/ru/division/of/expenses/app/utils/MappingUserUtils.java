package ru.division.of.expenses.app.utils;

import org.springframework.stereotype.Component;
import ru.division.of.expenses.app.dto.UserDto;
import ru.division.of.expenses.app.models.Event;
import ru.division.of.expenses.app.models.Role;
import ru.division.of.expenses.app.models.User;

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
        .map(Event::getName)
        .collect(Collectors.toList()));
        return userDto;
    }
}
