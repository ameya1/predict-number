package org.predict.service;

import lombok.extern.log4j.Log4j2;
import org.data.dao.RedisDao;
import org.data.model.user.entity.User;
import org.data.model.user.request.UserRequest;
import org.data.model.user.response.UserResponse;
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

    @Autowired
    private RedisDao<User> redisDao;

    public UserResponse save(UserRequest userRequest) {
        User user = create(userRequest);
        userDao.save(user);
        log.info("User created " + user + " User Redis Save : " + redisDao.save("user:" + user.getUserId(), user));
        return userResponse(user, "User " + user.getFirstName() + " " + user.getLastName() + " created Successfully.");
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

    public UserResponse get(Long id) {
        User user = userDao.get(id);
        redisDao.save("user:" + user.getUserId(), user);
        return userResponse(user, "User Available");
    }

    public UserResponse userResponse(User user, String message) {
        return UserResponse.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .createdAt(user.getCreatedAt())
                .message(message)
                .build();
    }
}
