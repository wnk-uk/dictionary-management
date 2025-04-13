package com.emro.dictionary.users.service;

import com.emro.dictionary.users.dto.UserDTO;
import com.emro.dictionary.users.entity.SignUpForm;
import com.emro.dictionary.users.entity.User;
import com.emro.dictionary.users.entity.UserRequest;
import com.emro.dictionary.users.repository.UserMapper;
import com.emro.dictionary.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

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
            String rawPassword = "1111";
            String encodedPassword = encoder.encode(rawPassword);
            user.setPassword(encodedPassword);
            userMapper.save(user);
        }
    }

	public List<Long> findAdminIds() {
		return userMapper.findAdminIds();
	}

    public User signUp(SignUpForm user) {
        if (userMapper.isDuplicate(user) > 0) {
            throw new IllegalArgumentException("유저아이디가 중복되었습니다.");
        }
        JwtUtil jwtUtil = new JwtUtil();
        user.setToken(jwtUtil.generateSSOToken(user));
        user.setPassword(encoder.encode(user.getPassword()));
        userMapper.signUp(user);

        return userMapper.findByUsername(user.getUsrId());
    }

    public void modifyUser(List<UserRequest> users) {
        for (UserRequest user : users) {
            userMapper.update(user);
        }
    }
}
