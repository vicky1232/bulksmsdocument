package com.bulkSms.ServiceImpl;

import com.bulkSms.Entity.Role;
import com.bulkSms.Entity.UserDetail;
import com.bulkSms.Model.CommonResponse;
import com.bulkSms.Model.RegistrationDetails;
import com.bulkSms.Repository.UserDetailRepo;
import com.bulkSms.Service.Service;
import com.bulkSms.Utility.EncodingUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@org.springframework.stereotype.Service
public class ServiceImpl implements Service {

    @Autowired
    private EncodingUtils encodingUtils;
    @Value("${project.save.path}")
    private String projectSavePath;

    @Autowired
    private UserDetailRepo userDetailRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseEntity<CommonResponse> fetchPdf( String folderPath) {
        CommonResponse commonResponse = new CommonResponse();
        File sourceFolder = new File(folderPath);

        if (!sourceFolder.exists() || !sourceFolder.isDirectory()) {
            commonResponse.setMsg("Source folder does not exist or is not a valid directory.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(commonResponse);
        }
        File[] files = sourceFolder.listFiles((dir, name) -> name.toLowerCase().endsWith(".pdf"));

        if (files == null || files.length == 0) {
            commonResponse.setMsg("No PDF files found in the specified directory.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(commonResponse);
        }

        for (File sourceFile : files) {
            if (!sourceFile.exists() || !sourceFile.isFile()) {
                commonResponse.setMsg("File " + sourceFile.getName() + " does not exist or is not a valid file.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(commonResponse);
            }

            String encodedName = encodingUtils.encode(sourceFile.getName());
            System.out.println("Encoded Name: " + encodedName +" ,Decoded Name: " +encodingUtils.decode(encodedName));

            Path sourcePath = sourceFile.toPath();
            Path targetPath = Path.of(projectSavePath, sourcePath.getFileName().toString());

            try {
                Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                commonResponse.setMsg("An error occurred while copying the file " + sourceFile.getName() + ": " + e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(commonResponse);
            }
        }
        commonResponse.setMsg("All PDF files copied successfully with encoded names.");
        return ResponseEntity.ok(commonResponse);
    }

    @Override
    public void registerNewUser(@Valid RegistrationDetails registerUserDetails) throws Exception {

        if(userDetailRepo.findByEmailId(registerUserDetails.getEmailId()).isPresent()){
            throw new Exception("EmailID already exist");
        }
        UserDetail userDetails = new UserDetail();
        userDetails.setFirstname(registerUserDetails.getFirstName());
        userDetails.setLastName(registerUserDetails.getLastName());
        userDetails.setEmailId(registerUserDetails.getEmailId());
        userDetails.setMobileNo(registerUserDetails.getMobileNo());
        userDetails.setPassword(passwordEncoder.encode(registerUserDetails.getPassword()));

        Role role = new Role();
        role.setRole("ROLE_USER");
        role.setUserMaster(userDetails);

        userDetails.setRoleMaster(role);

        userDetailRepo.save(userDetails);

        registerUserDetails.setRole(role.getRole());
    }
}