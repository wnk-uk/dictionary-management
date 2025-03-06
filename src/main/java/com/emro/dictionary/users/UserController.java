package com.emro.dictionary.users;

import com.emro.dictionary.lang.LangDTO;
import com.emro.dictionary.lang.LangRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/lists")
    public List<User> findByReqList(@ModelAttribute UserRequest request) {
        return userService.findAll(request);
    }



}
