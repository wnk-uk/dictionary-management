package com.emro.dictionary.users;

import com.emro.dictionary.lang.LangDTO;
import com.emro.dictionary.lang.LangRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
