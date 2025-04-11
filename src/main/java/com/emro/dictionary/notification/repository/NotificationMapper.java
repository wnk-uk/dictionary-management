package com.emro.dictionary.notification.repository;

import com.emro.dictionary.notification.dto.NotificationDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface NotificationMapper {
	@Insert("""
		INSERT INTO notifications (user_id, req_id, dtl_id, type, message, is_read, created_at)
 		VALUES (#{userId}, #{reqId}, #{dtlId}, #{type}, #{message}, false, CURRENT_TIMESTAMP)
	""")
	void insertNotification(NotificationDTO notification);

	@Select("""
		 SELECT id, user_id, req_id, dtl_id, type, message, is_read, created_at
         FROM notifications
         WHERE user_id = #{userId}
         AND is_read = false
         ORDER BY created_at DESC
	""")
	List<NotificationDTO> findByUserId(Long userId);

	@Update("""
		UPDATE notifications
		SET is_read = true
		WHERE id = #{id}
	""")
	void updateNotificationIsRead(Long id);
}
