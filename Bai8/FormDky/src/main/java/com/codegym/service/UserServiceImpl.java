package com.codegym.service;

import com.codegym.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UserServiceImpl implements UserService {

    private static final List<User> userList = Collections.synchronizedList(new ArrayList<>());
    private static final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public List<User> findAll() {
        return new ArrayList<>(userList);
    }

    @Override
    public void save(User user) {
        if (user.getId() == null) {
            user.setId(idGenerator.getAndIncrement());
        }
        userList.add(user);
    }

    @Override
    public User findById(Long id) {
        if (id == null) return null;
        return userList.stream()
                .filter(u -> id.equals(u.getId()))
                .findFirst()
                .orElse(null);
    }
}
