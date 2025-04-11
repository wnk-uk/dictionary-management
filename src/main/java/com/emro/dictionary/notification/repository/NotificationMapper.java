package com.emro.dictionary.notification.repository;

import com.emro.dictionary.notification.dto.NotificationDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface NotificationMapper {
	@Insert("""
		INSERT INTO notifications (user_id, req_id, req_dtl_id, type, message, is_read, created_at)
 		VALUES (#{userId}, #{reqId}, #{reqDtlId}, #{type}, #{message}, false, CURRENT_TIMESTAMP)
	""")
	void insertNotification(NotificationDTO notification);

	@Select("""
		 SELECT id, user_id, req_id, req_dtl_id, type, message, is_read, created_at
         FROM notifications
         WHERE user_id = #{userId}
         ORDER BY created_at DESC
	""")
	List<NotificationDTO> findByUserId(Long userId);
}
