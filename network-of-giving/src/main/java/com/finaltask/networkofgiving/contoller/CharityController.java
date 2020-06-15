package com.finaltask.networkofgiving.contoller;

import com.finaltask.networkofgiving.dto.CharityDto;
import com.finaltask.networkofgiving.exception.CharityNotFoundException;
import com.finaltask.networkofgiving.service.CharityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.RollbackException;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.NotAuthorizedException;
import java.util.List;

@RestController
@RequestMapping("/api/charity")
public class CharityController {

    @Autowired
    private CharityService charityService;

    @PostMapping
    public ResponseEntity createCharity(@RequestBody CharityDto charityDto){
        charityService.createCharity(charityDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CharityDto>> showAllCharities(){
        return new ResponseEntity<>(charityService.showAllCharities(), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public  ResponseEntity<CharityDto> getSingleCharity(@PathVariable("id") Long id){
        return new ResponseEntity<>(charityService.readSingleCharity(id), HttpStatus.OK);
    }

    @GetMapping("/own")
    public ResponseEntity<List<CharityDto>> getAllUsersCharity(){
        return new ResponseEntity<>(charityService.getUsersCharity(), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteCharity(@PathVariable("id") Long id){
        charityService.deleteCharity(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<CharityDto> editCharity(@PathVariable("id") Long id, @RequestBody CharityDto charityDto){
        return new ResponseEntity<>(charityService.editCharity(id, charityDto), HttpStatus.OK);
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
}
