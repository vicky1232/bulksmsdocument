package com.bulkSms.Service;

import com.bulkSms.Model.CommonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface Service {
    ResponseEntity<?> fetchPdf(String pdfUrl) throws IOException;
}
