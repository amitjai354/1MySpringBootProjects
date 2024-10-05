package org.example.service;

import org.example.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {
private List<User> userList = new ArrayList<>();
public UserService(){
    userList.add(new User(UUID.randomUUID().toString(), "abc1", "abc1@g.in"));
    userList.add(new User(UUID.randomUUID().toString(), "abc2", "abc2@g.in"));
    userList.add(new User(UUID.randomUUID().toString(), "abc3", "abc3@g.in"));
    userList.add(new User(UUID.randomUUID().toString(), "abc4", "abc4@g.in"));
}

public List<User> getUserList(){
    return userList;
}
}
