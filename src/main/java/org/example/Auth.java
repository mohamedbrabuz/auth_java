package org.example;

import java.io.Serializable;

public class Auth implements Serializable {
    String userName;
    String password;


    public Auth(String userName, String password) {
        this.userName=userName;
        this.password=password;
    }
}
