package com.tads.mhsf.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class Customer implements PropertyChangeListener {
    private Long id;
    private String name;
    private String email;
    private String password;
    private final List<Book> wishList;
    private final List<String> notifications;

    public Customer(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.wishList = new ArrayList<>();
        notifications = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Book> getWishList() {
        return wishList;
    }

    public List<String> getNotifications() {
        return notifications;
    }

    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
        Book book = (Book) propertyChangeEvent.getSource();
        notifications.add(propertyChangeEvent.getPropertyName());
    }
}
