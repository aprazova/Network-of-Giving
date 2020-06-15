package com.finaltask.networkofgiving.service.interfaces;

import com.finaltask.networkofgiving.data.AuthenticationResponse;
import com.finaltask.networkofgiving.dto.LoginRequest;
import com.finaltask.networkofgiving.dto.RegisterRequest;

public interface IAuthService {

    void signup(RegisterRequest registerRequest);
    void logout();
    AuthenticationResponse login(LoginRequest loginRequest);
}
