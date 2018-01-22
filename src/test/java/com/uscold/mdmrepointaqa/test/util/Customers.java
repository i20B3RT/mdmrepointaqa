package com.uscold.mdmrepointaqa.test.util;

public enum Customers {
    SUPERUSER("superuser","superuser");

    private String login;
    private String password;

    Customers(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}