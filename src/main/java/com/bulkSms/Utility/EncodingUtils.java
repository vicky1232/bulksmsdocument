package com.bulkSms.Utility;

import org.springframework.stereotype.Component;

import java.util.Base64;
@Component
public class EncodingUtils {

    public String encode(String input) {
        return Base64.getEncoder().encodeToString(input.getBytes());
    }

    public String decode(String input) {
        return new String(Base64.getDecoder().decode(input));
    }
}
