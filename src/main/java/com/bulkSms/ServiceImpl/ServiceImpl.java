package com.bulkSms.ServiceImpl;

import com.bulkSms.Model.CommonResponse;
import com.bulkSms.Service.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;

import java.io.*;

@org.springframework.stereotype.Service
public class ServiceImpl implements Service {

    private final Logger logger = LoggerFactory.getLogger(ServiceImpl.class);

    @Value("${project.save.path}")
    private String projectSavePath;

    @Override
    public ResponseEntity<?> fetchPdf(String pdfUrl) throws IOException {

        CommonResponse commonResponse = new CommonResponse();
        File sourceFile = new File(pdfUrl);
        if (!sourceFile.exists()) {
            commonResponse.setMsg("Source file does not exist or is not a valid file.");
        }

        File targetFile = new File(projectSavePath, sourceFile.getName());
        try (InputStream inputStream = new FileInputStream(sourceFile);
             OutputStream outputStream = new FileOutputStream(targetFile)) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
        return ResponseEntity.ok(commonResponse);
    }

//    public ResponseEntity<CommonResponse> fetchPdf(String pdfUrl) {
//        CommonResponse commonResponse = new CommonResponse();
//        File sourceFile = new File(pdfUrl);
//        File targetFile = new File(projectSavePath, sourceFile.getName());
//
//        // Check if the source file exists
//        if (!sourceFile.exists()) {
//            commonResponse.setMsg("Source file does not exist or is not a valid file.");
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(commonResponse);
//        }
//
//        // Check if the source file is readable
//        if (!sourceFile.canRead()) {
//            commonResponse.setMsg("Access denied: Cannot read the source file.");
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(commonResponse);
//        }
//
//        if (!targetFile.getParentFile().canWrite()) {
//            commonResponse.setMsg("Access denied: Cannot write to the target directory.");
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(commonResponse);
//        }
//
//        try (InputStream inputStream = new FileInputStream(sourceFile);
//             OutputStream outputStream = new FileOutputStream(targetFile)) {
//
//            byte[] buffer = new byte[1024];
//            int bytesRead;
//            while ((bytesRead = inputStream.read(buffer)) != -1) {
//                outputStream.write(buffer, 0, bytesRead);
//            }
//        } catch (IOException e) {
//            if (e.getMessage().contains("Access is denied")) {
//                commonResponse.setMsg("Access is denied: " + e.getMessage());
//                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(commonResponse);
//            } else {
//                commonResponse.setMsg("An error occurred while copying the file: " + e.getMessage());
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(commonResponse);
//            }
//        }
//
//        commonResponse.setMsg("File copied successfully.");
//        return ResponseEntity.ok(commonResponse);
//    }

//    public ResponseEntity<CommonResponse> fetchPdf(String pdfUrl) {
//        CommonResponse commonResponse = new CommonResponse();
//        File sourceFile = new File(pdfUrl);
//
//        if (!sourceFile.exists()) {
//            commonResponse.setMsg("Source file does not exist or is not a valid file.");
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(commonResponse);
//        }
//
//        if (!sourceFile.canRead()) {
//            commonResponse.setMsg("Access denied: Cannot read the source file.");
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(commonResponse);
//        }
//
//        try (InputStream inputStream = new FileInputStream(sourceFile)) {
//            // Print the contents of the file to the console
//            byte[] buffer = new byte[1024];
//            int bytesRead;
//            StringBuilder fileContents = new StringBuilder();
//
//            while ((bytesRead = inputStream.read(buffer)) != -1) {
//                fileContents.append(new String(buffer, 0, bytesRead));
//            }
//
//            // Print the contents of the file to the console
//            System.out.println("File Contents:");
//            System.out.println(fileContents.toString());
//
//            commonResponse.setMsg("File read successfully. Check the console for contents.");
//            return ResponseEntity.ok(commonResponse);
//        } catch (IOException e) {
//            commonResponse.setMsg("An error occurred while reading the file: " + e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(commonResponse);
//        }
//    }

}