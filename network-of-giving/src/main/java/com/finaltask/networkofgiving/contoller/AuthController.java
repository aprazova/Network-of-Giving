package com.finaltask.networkofgiving.contoller;

import com.finaltask.networkofgiving.data.AuthenticationResponse;
import com.finaltask.networkofgiving.dto.LoginRequest;
import com.finaltask.networkofgiving.dto.RegisterRequest;
import com.finaltask.networkofgiving.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.RollbackException;
import javax.validation.ConstraintViolationException;
import java.sql.SQLException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody RegisterRequest registerRequest){
        authService.signup(registerRequest);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest){
        return authService.login(loginRequest);
    }

    @GetMapping("/logout")
    public ResponseEntity logout(){
        authService.logout();
        return new ResponseEntity(HttpStatus.OK);
    }

    @ExceptionHandler({IllegalArgumentException.class, SQLException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void illegalArgumentExceptionHandler() {
    }

    @ExceptionHandler({RollbackException.class, ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public void blankDataExceptionHandler() {
    }

}
