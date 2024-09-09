package com.bulkSms.Model;

import lombok.Data;

@Data
public class JwtRequest {
    private String emailId;
    private String password;
}
