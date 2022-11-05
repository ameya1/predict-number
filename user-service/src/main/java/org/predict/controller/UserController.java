package org.predict.controller;

import lombok.extern.log4j.Log4j2;
import org.data.model.user.request.UserRequest;
import org.data.model.user.response.UserResponse;
import org.predict.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Log4j2
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> create(@RequestBody UserRequest userRequest) {
        try {
            return ResponseEntity.ok(userService.save(userRequest));
        } catch (Exception e) {
            UserResponse userResponse = UserResponse.builder().message( "Issue creating user " + userRequest.getFirstName() + " " + userRequest.getLastName()).build();
            return exceptionResponse(e, userResponse);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> get(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(userService.get(id));
        } catch (Exception e) {
            UserResponse userResponse = UserResponse.builder().message("Issue finding user with id " + id).build();
            return exceptionResponse(e, userResponse);
        }
    }

    public ResponseEntity<UserResponse> exceptionResponse(Exception e, UserResponse userResponse) {
        log.error("Exception : " + e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(userResponse);
    }
}
