package com.rnd.democompletetablefuture.controller;

import com.rnd.democompletetablefuture.entity.Users;
import com.rnd.democompletetablefuture.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RequestMapping(value = "/users")
@RestController
public class UsersController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/save",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = "application/json")
    public ResponseEntity save(@RequestParam(value = "files") MultipartFile[] files) throws Exception {
        for (MultipartFile file : files){
            userService.saveUsers(file);
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(value = "/list", produces = "application/json")
    public CompletableFuture<ResponseEntity> list(){
       return userService.findAllUsers()
                .thenApply(ResponseEntity::ok);
    }

    @GetMapping(value = "/mutiple-thread-users", produces = "application/json")
    public ResponseEntity getMultipleThreadUsers(){
        CompletableFuture<List<Users>> users1 = userService.findAllUsers();
        CompletableFuture<List<Users>> users2 = userService.findAllUsers();
        CompletableFuture<List<Users>> users3 = userService.findAllUsers();
        CompletableFuture.allOf(users1, users2, users3).join();
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
