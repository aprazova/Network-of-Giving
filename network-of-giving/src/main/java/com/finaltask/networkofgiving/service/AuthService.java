package com.finaltask.networkofgiving.service;

import com.finaltask.networkofgiving.data.AuthenticationResponse;
import com.finaltask.networkofgiving.dto.LoginRequest;
import com.finaltask.networkofgiving.dto.RegisterRequest;
import com.finaltask.networkofgiving.model.User;
import com.finaltask.networkofgiving.repository.UserRepository;
import com.finaltask.networkofgiving.security.JwtProvider;
import com.finaltask.networkofgiving.service.interfaces.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService implements IAuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    public void signup(RegisterRequest registerRequest) {
        if(userRepository.existsByUsername(registerRequest.getUsername())){
            throw new IllegalArgumentException("User with this username already exist.");
        }

        User user = new User();
        user.setName(registerRequest.getName());
        user.setUsername(registerRequest.getUsername());
        user.setPassword(encodePassword(registerRequest.getPassword()));
        user.setAge(registerRequest.getAge());
        user.setGender(registerRequest.getGender());
        user.setLocation(registerRequest.getLocation());
        userRepository.save(user);
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public AuthenticationResponse login(LoginRequest loginRequest) {
        if(!userRepository.existsByUsername(loginRequest.getUsername())){
            throw new IllegalArgumentException("User with this username doesn't exist.");
        }

        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String authenticationToken = jwtProvider.generateToken(authenticate);

        return new AuthenticationResponse(authenticationToken, loginRequest.getUsername());
    }

    @Override
    public void logout() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    public Optional<org.springframework.security.core.userdetails.User> getCurrentUser() {
        org.springframework.security.core.userdetails.User principal =
                (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Optional.of(principal);
    }

}
