package com.emro.dictionary.notification.service;

import com.emro.dictionary.history.repository.HistoryMapper;
import com.emro.dictionary.notification.dto.MessageDTO;
import com.emro.dictionary.notification.repository.MessageMapper;
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
public class MessageService {
	private final MessageMapper messageMapper;
	private final UserService userService;
	private final SecurityUtil securityUtil;
	private final RequestMapper requestMapper;


	// 댓글 작성 시 유저와 어드민에게 메시지
	public void notifyOnCommentAdded(Long reqDtlHisId, Long dtlId, String commentText, Long reqUserId) {
		MultLangRequestDetailDTO reqDtl = requestMapper.findRequestDetailByDtlId(dtlId);
		Long reqId = reqDtl.getReqId();

		Long currentUserId = securityUtil.getUserId();
		if (reqUserId == null) {
			throw new IllegalStateException("Request not found for reqId: " + reqId);
		}

		// 유저에게 메시지 (자신이 작성한 req_dtl에 대해서만, 본인이 아닌 경우에만)
		if (!currentUserId.equals(reqUserId)) {
			MessageDTO dto = new MessageDTO();
			dto.setUserId(currentUserId);
			dto.setReqId(reqId);
			dto.setReqDtlId(dtlId);
			dto.setReqDtlHisId(reqDtlHisId);
			dto.setMessage("요청 상세에 새로운 댓글이 달렸습니다: " + " (요청 ID: " + reqId + ", 상세 ID: " + dtlId + ")");
//			dto.setMessage("요청 상세에 새로운 댓글이 달렸습니다: " + commentText + " (요청 ID: " + reqId + ", 상세 ID: " + dtlId + ")");
			messageMapper.insertMessage(dto);

		}

		// 어드민들에게 메시지 (모든 댓글에 대해, 본인 제외)
		List<Long> adminIds = userService.findAdminIds();
		for (Long adminId : adminIds) {
			if (!adminId.equals(currentUserId)) {
				MessageDTO dto = new MessageDTO();
				dto.setUserId(adminId);
				dto.setReqId(reqId);
				dto.setReqDtlId(dtlId);
				dto.setReqDtlHisId(reqDtlHisId);
				dto.setMessage("요청 상세에 새로운 댓글이 달렸습니다: " + commentText + " (요청 ID: " + reqId + ", 상세 ID: " + dtlId + ")");
				messageMapper.insertMessage(dto);
			}
		}
	}

	// 유저의 메시지 조회
	public List<MessageDTO> getMessagesByUserId(Long userId) {
		return messageMapper.findByUserId(userId);
	}
}