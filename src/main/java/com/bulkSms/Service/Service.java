package com.bulkSms.Service;

import com.bulkSms.Model.RegisterationDetails;

public interface Service {

    void registerNewUser(RegisterationDetails registerUserDetails) throws Exception;
}
