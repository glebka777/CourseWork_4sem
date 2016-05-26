package com.entities;

abstract class Person {

    Integer id;
    private String firstName;
    private String lastName;
    private String number;
    private String address;
    private String email;
    private String login;
    private String password;

    Person(String firstName, String lastName, String number, String address, String email, String login, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.number = number;
        this.address = address;
        this.email = email;
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {

        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getId() {

        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
