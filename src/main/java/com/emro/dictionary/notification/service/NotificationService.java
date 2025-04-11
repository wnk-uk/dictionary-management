package com.emro.dictionary.notification.service;

import com.emro.dictionary.notification.dto.NotificationDTO;
import com.emro.dictionary.notification.repository.NotificationMapper;
import com.emro.dictionary.request.dto.MultLangRequestDetailDTO;
import com.emro.dictionary.request.repository.RequestMapper;
import com.emro.dictionary.security.SecurityUtil;
import com.emro.dictionary.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class NotificationService {
	private final NotificationMapper notificationMapper;
	private final UserService userService;
	private final SecurityUtil securityUtil;
	private final RequestMapper requestMapper;

	// 요청 생성 시 어드민에게 알림
	public void notifyAdminsOnRequestCreated(Long reqId, String reqUserName) {
		List<Long> adminIds = userService.findAdminIds();
		for (Long adminId : adminIds) {
			NotificationDTO notification = new NotificationDTO();
			notification.setUserId(adminId);
			notification.setReqId(reqId);
			notification.setReqId(reqId);
			notification.setType("REQUEST_CREATED");
			String message = "새로운 요청이 등록되었습니다. (요청 ID: " + reqId + ")<br>요청자: " + reqUserName;
			notification.setMessage(message);
			notificationMapper.insertNotification(notification);
		}
	}

	// 요청 상태 변경 시 유저에게 알림 (SecurityUtil 사용)
	public void notifyUserOnStatusChange(Long reqId, String acptSts, Long reqUserId) {
		Long currentUserId = securityUtil.getUserId();
		if (!currentUserId.equals(reqUserId)) {
			NotificationDTO notification = new NotificationDTO();
			notification.setUserId(reqUserId);
			notification.setReqId(reqId);
			notification.setType("STATUS_CHANGED");
			notification.setMessage("요청 상태가 " + acptSts + "로 변경되었습니다.<br>(요청 ID: " + reqId + ")");
			notificationMapper.insertNotification(notification);
		}
	}


	// 요청 상세 상태 변경 시 유저에게 알림 (SecurityUtil 사용)
	public void notifyUserOnReqDtlStatusChange(Long dtlId, String regSts, Long reqUserId) {
		Long currentUserId = securityUtil.getUserId();
		if (!currentUserId.equals(reqUserId)) {
			MultLangRequestDetailDTO reqDtl = requestMapper.findRequestDetailByDtlId(dtlId);
			NotificationDTO notification = new NotificationDTO();
			notification.setUserId(reqUserId);
			notification.setReqId(reqDtl.getReqId());
			notification.setDtlId(dtlId);
			notification.setType("REQ_DTL_STATUS_CHANGED");
			notification.setMessage("요청 상세 상태가 " + regSts + "로 변경되었습니다.<br>(요청 ID: " + reqDtl.getReqId() + ", 상세 ID: " + dtlId + ")");
			notificationMapper.insertNotification(notification);
		}
	}


	// 유저의 알림 조회
	public List<NotificationDTO> getNotificationsByUserId(Long userId) {
		return notificationMapper.findByUserId(userId);
	}

	// 알림 조회 처리
	public void updateNotificationIsRead(Long id) {
		notificationMapper.updateNotificationIsRead(id);
	}
}