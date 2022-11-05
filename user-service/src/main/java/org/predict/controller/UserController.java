package org.predict.controller;

import lombok.extern.log4j.Log4j2;
import org.data.model.request.UserRequest;
import org.data.model.response.UserResponse;
import org.predict.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            return exceptionResponse(e, userRequest);
        }
    }

    public ResponseEntity<UserResponse> exceptionResponse(Exception e, UserRequest userRequest) {
        log.error("Exception : " + e.getMessage(), e);
        UserResponse userResponse = userService.userResponse(userRequest, "Issue creating user " + userRequest.getFirstName() + " " + userRequest.getLastName());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(userResponse);
    }
}
