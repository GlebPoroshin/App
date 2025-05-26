package com.ortin.weblinux.controller;

import com.ortin.weblinux.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final List<User> users = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong();

    public UserController() {
        addTestUsers();
    }

    private void addTestUsers() {
        User u1 = new User();
        u1.setId(counter.incrementAndGet());
        u1.setUsername("user1");
        u1.setEmail("user1@example.com");
        u1.setFullName("User One");
        users.add(u1);

        User u2 = new User();
        u2.setId(counter.incrementAndGet());
        u2.setUsername("user2");
        u2.setEmail("user2@example.com");
        u2.setFullName("User Two");
        users.add(u2);

        User u3 = new User();
        u3.setId(counter.incrementAndGet());
        u3.setUsername("user3");
        u3.setEmail("user3@example.com");
        u3.setFullName("User Three");
        users.add(u3);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return users;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        user.setId(counter.incrementAndGet());
        users.add(user);

        return user;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        boolean removed = users.removeIf(user -> user.getId().equals(id));
        return removed ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
} 