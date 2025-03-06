package com.emro.dictionary.users;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM users WHERE username = #{username}")
    User findByUsername(@Param("username") String username);

    @Insert("INSERT INTO users (username, password, role) VALUES (#{username}, #{password}, #{role})")
    void insertUser(User user);

    @Select("SELECT * FROM users")
    List<User> findAll(UserRequest request);
}
