package com.example.ebanking.model;

public class ClientModel {
    private Integer id;
    private String lastName;
    private String firstName;
    private String role;

    public Integer getId() { return id; }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getRole() { return role; }

    public void setRole(String role) { this.role = role; }
}

