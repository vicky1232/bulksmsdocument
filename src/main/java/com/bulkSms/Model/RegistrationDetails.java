package com.bulkSms.Model;

import lombok.Data;

@Data
public class RegistrationDetails {

    private String firstName;

    private String lastName;

    private String emailId;

    private String password;

    private String mobileNo;

    private String role;
}