package com.emro.dictionary.users.api;

import com.emro.dictionary.users.entity.User;
import com.emro.dictionary.users.entity.UserRequest;
import com.emro.dictionary.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/lists")
    public List<User> findAll(@ModelAttribute UserRequest request) {
        return userService.findAll(request);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addUser(@RequestBody UserRequest user) {
        userService.addUser(user);
        return ResponseEntity.ok("User added");
    }

}
