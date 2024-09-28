package com.Ecommerce.ecom.services.auth;

import com.Ecommerce.ecom.dto.SignupRequest;
import com.Ecommerce.ecom.dto.UserDto;

public interface AuthService {

    UserDto createUser(SignupRequest signupRequest);
    Boolean hasUserWithEmail(String email);
}
