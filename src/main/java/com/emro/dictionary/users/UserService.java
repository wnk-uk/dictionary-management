package com.emro.dictionary.users;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final PasswordEncoder encoder;

    public List<User> findAll(UserRequest request) {
        return userMapper.findAll(request);
    }

    public void addUser(UserRequest user) {
        User findUser = userMapper.findByUsername(user.getUsername());
        if (findUser != null) {
            userMapper.update(user);
        } else {
            String rawPassword = "admin123";
            String encodedPassword = encoder.encode(rawPassword);
            user.setPassword(encodedPassword);
            userMapper.save(user);
        }
    }
}
