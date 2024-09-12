package com.bulkSms.Controller;

import com.bulkSms.Service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.bulkSms.Model.CommonResponse;
import com.bulkSms.Service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/admin")
public class Admin {
    @Autowired
    private Service service;

    @PostMapping("/message")
    public ResponseEntity<String> postMessage() {
        return ResponseEntity.ok("Message received by admin successfully!");
    }

    @PostMapping("/csvUpload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        CommonResponse commonResponse = new CommonResponse();
        try {
            return ResponseEntity.ok(service.save(file).getBody());
        } catch (Exception e) {
            commonResponse.setMsg("Technical issue : " + e.getMessage());
            return new ResponseEntity<>(commonResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/fetch-pdf")
    public ResponseEntity<?> pdfFetcherFromLocation(@RequestParam(name = "pdfUrl") String pdfUrl) throws IOException {
        return service.fetchPdf(pdfUrl);
    }
}
