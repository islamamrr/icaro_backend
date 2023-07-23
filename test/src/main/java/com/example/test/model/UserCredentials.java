package com.example.test.model;

import lombok.Data;

@Data
public class UserCredentials {
    private String username;
    private String password;
    private String role = "User";
    private String siteNo = "1";
}

