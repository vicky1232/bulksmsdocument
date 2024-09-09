package com.bulkSms.Service;

import com.bulkSms.Model.RegistrationDetails;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface Service {
    ResponseEntity<?> fetchPdf(String pdfUrl) throws IOException;

    void registerNewUser(@Valid RegistrationDetails registerUserDetails) throws Exception;
}
