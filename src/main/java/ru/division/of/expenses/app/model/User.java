package ru.division.of.expenses.app.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "users")
@Data
public class User extends AbstractEntity {

//    @Column(name = "first_name")
//    private String firstName;
//
//    @Column(name = "last_name")
//    private String lastName;
//
//    @Column(name = "username")
//    private String username;
//
//    @Column(name = "password")
//    private String password;
//
//    @ManyToMany
//    @JoinTable(
//            name = "users_roles")
//    private Collection<Role> roles = new ArrayList<>();
//
//
//    @ManyToMany
//    @JoinTable(
//            name = "events_users",
//            joinColumns = @JoinColumn(name = "event_id"),
//            inverseJoinColumns = @JoinColumn(name = "user_id"))
//    private Collection<Event> eventList = new ArrayList<>();
//
//
//    //    @ManyToMany
////    @JoinTable(
////            name = "invitedEvents_users",
////            joinColumns = @JoinColumn(name = "user_id"),
////            inverseJoinColumns = @JoinColumn(name = "event_id"))
////    private Collection<Event> invitedEventList = new ArrayList<>();

    /////////////////////////////////////////////////////////////////

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password")
    private String password;

    @ManyToMany
    @JoinTable(
            name = "users_roles")
    private Collection<Role> roles = new ArrayList<>();


    @ManyToMany
    @JoinTable(
            name = "events_users",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Collection<Event> eventList = new ArrayList<>();


    //    @ManyToMany
//    @JoinTable(
//            name = "invitedEvents_users",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "event_id"))
//    private Collection<Event> invitedEventList = new ArrayList<>();


}
