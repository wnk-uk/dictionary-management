package com.emro.dictionary.notification.repository;

import com.emro.dictionary.notification.dto.MessageDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MessageMapper {

	@Insert("""
		INSERT INTO messages (user_id, req_id, req_dtl_id, req_dtl_his_id, message, is_read, created_at)
        VALUES (#{userId}, #{reqId}, #{reqDtlId}, #{reqDtlHisId}, #{message}, false, CURRENT_TIMESTAMP)
	""")
	void insertMessage(MessageDTO message);

	@Select("""
		 SELECT id, user_id, req_id, req_dtl_id, req_dtl_his_id, message, is_read, created_at
         FROM messages
         WHERE user_id = #{userId}
         ORDER BY created_at DESC
	""")
	List<MessageDTO> findByUserId(Long userId);
}
