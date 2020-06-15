package com.finaltask.networkofgiving.contoller;

import com.finaltask.networkofgiving.dto.UserDto;
import com.finaltask.networkofgiving.model.UsersTransaction;
import com.finaltask.networkofgiving.service.CustomUserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.persistence.RollbackException;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.NotAuthorizedException;
import java.util.Set;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private CustomUserServices customUserServices;

    @GetMapping()
    public ResponseEntity<UserDto> getUserInfo(){
        return new ResponseEntity(customUserServices.getUserInfo(), HttpStatus.OK);
    }

    @PutMapping("/edit")
    public ResponseEntity<UserDto> editInfo(@RequestBody UserDto userDto){
        return new ResponseEntity(customUserServices.editUserInformation(userDto), HttpStatus.OK);
    }

    @GetMapping("/transactions")
    public ResponseEntity<Set<UsersTransaction>> getUserTransactions(){
        return new ResponseEntity(customUserServices.getUserTransaction(), HttpStatus.OK);
    }

    @ExceptionHandler({ClassCastException.class, NotAuthorizedException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public void classCastExceptionHandler() {
    }

    @ExceptionHandler({UsernameNotFoundException.class, IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void badRequestHandler() {
    }

    @ExceptionHandler({ConstraintViolationException.class, RollbackException.class})
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public void constraintViolationExceptionHandler() {
    }
}
