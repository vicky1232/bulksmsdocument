package com.bulkSms.Controller;

import com.bulkSms.JwtAuthentication.JwtHelper;
import com.bulkSms.Model.CommonResponse;
import com.bulkSms.Model.JwtRequest;
import com.bulkSms.Model.JwtResponse;
import com.bulkSms.Model.RegistrationDetails;
import com.bulkSms.Service.Service;
import com.bulkSms.Utility.EncodingUtils;
import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/sms-service")
public class Login {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private Service service;
    @Autowired
    private EncodingUtils encodingUtils;

    @Autowired
    private JwtHelper helper;

    Logger logger = LoggerFactory.getLogger(Login.class);


    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {


        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmailId());

        this.doAuthenticate(request.getEmailId(), request.getPassword());

        String token = this.helper.generateToken(userDetails);

        JwtResponse response = JwtResponse.builder()
                .token(token)
                .emailId(userDetails.getUsername()).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void doAuthenticate(String email, String password) {

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            manager.authenticate(authentication);


        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(" Invalid Username or Password  !!");
        }

    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid  RegistrationDetails registerUserDetails, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        try {
            service.registerNewUser(registerUserDetails);
            return new ResponseEntity<>("User Registered successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

        }
    }

    @GetMapping("/download-pdf")
    public ResponseEntity<?> downloadPdfFile(@RequestParam(name = "loanNo")String loanNo){
        CommonResponse commonResponse = new CommonResponse();
        String loanNoDecoded = encodingUtils.decode(loanNo);
        System.out.println(loanNoDecoded);
        try {
            return service.fetchPdfFile(loanNoDecoded);
        }catch (Exception e){
            commonResponse.setMsg("Exception :" +e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
