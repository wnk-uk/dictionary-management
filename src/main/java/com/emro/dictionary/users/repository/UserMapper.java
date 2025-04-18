package com.emro.dictionary.users.repository;

import com.emro.dictionary.users.dto.UserDTO;
import com.emro.dictionary.users.entity.SignUpForm;
import com.emro.dictionary.users.entity.User;
import com.emro.dictionary.users.entity.UserRequest;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM users WHERE username = #{username}")
    User findByUsername(@Param("username") String username);

    @Insert("INSERT INTO users (username, password, role, usr_nm, dept_nm, dept_cd) VALUES (#{username}, #{password}, #{role}, #{usrNm}, #{deptNm}, #{deptCd})")
    void save(UserRequest user);

    @Select("SELECT * FROM users")
    List<User> findAll(UserRequest request);

    @Update("UPDATE users SET role = #{role}, usr_nm = #{usrNm}, dept_nm = #{deptNm}, use_yn = #{useYn} WHERE username = #{username}")
    void update(UserRequest user);

	@Select("""
	SELECT id 
	FROM users
	WHERE role <> 'USER'
	""")
	List<Long> findAdminIds();

    @Select("""
	SELECT COUNT(*) 
	FROM users
	WHERE username = #{usrId}
	""")
    int isDuplicate(SignUpForm user);

    @Insert("""
        INSERT INTO users (username, password, role, usr_nm, dept_nm, dept_cd, token) 
        VALUES (#{usrId}, #{password}, #{role}, #{usrNm}, #{deptNm}, #{deptCd}, #{token})
    """)
    void signUp(SignUpForm user);


}
