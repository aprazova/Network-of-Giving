package com.finaltask.networkofgiving.contoller;

import com.finaltask.networkofgiving.dto.CharityDto;
import com.finaltask.networkofgiving.dto.TransactionDto;
import com.finaltask.networkofgiving.exception.CharityNotFoundException;
import com.finaltask.networkofgiving.exception.IsAlreadyVolunteer;
import com.finaltask.networkofgiving.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.RollbackException;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.NotAuthorizedException;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PutMapping
    public ResponseEntity<CharityDto> addTransaction(@RequestBody TransactionDto transactionDto){

        return new ResponseEntity<CharityDto>(transactionService.addTransaction(transactionDto),HttpStatus.OK);
    }

    @ExceptionHandler({CharityNotFoundException.class, IllegalArgumentException.class, NullPointerException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void errorHandler() {
    }

    @ExceptionHandler({ClassCastException.class, NotAuthorizedException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public void classCastExceptionHandler() {
    }

    @ExceptionHandler({ConstraintViolationException.class, RollbackException.class})
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public void constraintViolationExceptionHandler() {
    }

    @ExceptionHandler(IsAlreadyVolunteer.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public void isAlreadyVolunteerExceptionHandler() {
    }
}
