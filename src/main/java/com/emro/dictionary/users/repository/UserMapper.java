package com.emro.dictionary.users.repository;

import com.emro.dictionary.users.entity.User;
import com.emro.dictionary.users.entity.UserRequest;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM users WHERE username = #{username}")
    User findByUsername(@Param("username") String username);

    @Insert("INSERT INTO users (username, password, role, usr_nm, dept_nm) VALUES (#{username}, #{password}, #{role}, #{usrNm}, #{deptNm})")
    void save(UserRequest user);

    @Select("SELECT * FROM users")
    List<User> findAll(UserRequest request);

    @Update("UPDATE users SET role = #{role}, usr_nm = #{usrNm}, dept_nm = #{deptNm} WHERE username = #{username}")
    void update(UserRequest user);
}
