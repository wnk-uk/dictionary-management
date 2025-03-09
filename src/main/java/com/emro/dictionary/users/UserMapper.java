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

    @Insert("INSERT INTO users (username, password, role, dept_nm, usr_nm) VALUES (#{username}, #{password}, #{role}, #{userNm}, #{deptNm})")
    void save(User user);

    @Select("SELECT users.*, users.usr_nm as usrNm, users.dept_nm as deptNm FROM users")
    List<User> findAll(UserRequest request);
}
