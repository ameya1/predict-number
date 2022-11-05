package org.predict.service;

import lombok.extern.log4j.Log4j2;
import org.data.model.entity.User;
import org.data.model.request.UserRequest;
import org.data.model.response.UserResponse;
import org.predict.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Log4j2
public class UserService {

    @Autowired
    private UserDao userDao;

    public UserResponse save(UserRequest userRequest) {
        User user = create(userRequest);
        userDao.save(user);
        log.info("User created " + user);
        return userResponse(userRequest, "User " + userRequest.getFirstName() + " " + userRequest.getLastName() + " created Successfully.");
    }

    public User create(UserRequest userRequest) {
        return User.builder()
                .userId(UUID.randomUUID())
                .email(userRequest.getEmail())
                .username(userRequest.getUsername())
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public UserResponse userResponse(UserRequest userRequest) {
        return userResponse(userRequest, null);
    }

    public UserResponse userResponse(UserRequest userRequest, String message) {
        return UserResponse.builder()
                .email(userRequest.getEmail())
                .username(userRequest.getUsername())
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .message(message)
                .build();
    }
}
