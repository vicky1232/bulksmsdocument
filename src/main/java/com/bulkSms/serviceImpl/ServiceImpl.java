package com.bulkSms.serviceImpl;

import com.bulkSms.Entity.Role;
import com.bulkSms.Entity.UserDetail;
import com.bulkSms.Model.RegisterationDetails;
import com.bulkSms.Repository.UserDetailRepo;
import com.bulkSms.Service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@org.springframework.stereotype.Service
public class ServiceImpl implements Service {

    @Autowired
    private UserDetailRepo userDetailRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void registerNewUser(RegisterationDetails registerUserDetails) throws Exception {

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
