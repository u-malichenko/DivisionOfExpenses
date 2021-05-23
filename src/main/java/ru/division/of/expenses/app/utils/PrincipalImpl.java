package ru.division.of.expenses.app.utils;

import ru.division.of.expenses.app.models.User;

import java.security.Principal;

public class PrincipalImpl implements Principal {

    private String name;

    public PrincipalImpl(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
