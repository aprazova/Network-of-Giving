package com.finaltask.networkofgiving.contoller;

import com.finaltask.networkofgiving.data.CustomFile;
import com.finaltask.networkofgiving.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.NotAuthorizedException;
import java.io.FileNotFoundException;

import java.util.List;

@RestController
@RequestMapping("/api/images")
public class FileController {

    @Autowired
    private FileService fileService;

    @GetMapping()
    @CrossOrigin
    public ResponseEntity<List<CustomFile>> getAllFiles(){
        return new ResponseEntity<List<CustomFile>>(fileService.getAllFiles(), HttpStatus.OK);
    }

    @GetMapping("/{filename}")
    @CrossOrigin
    public ResponseEntity<CustomFile> getFile(@PathVariable String filename) throws FileNotFoundException {
        CustomFile file = fileService.getFile(filename);
        return new ResponseEntity<>(fileService.getFile(filename), HttpStatus.OK);
    }

    @ExceptionHandler({NullPointerException.class, IllegalArgumentException.class, FileNotFoundException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void errorHandler() {
    }


    @ExceptionHandler({NotAuthorizedException.class, ClassCastException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public void notAuthorizedExceptionHandler() {
    }

}
