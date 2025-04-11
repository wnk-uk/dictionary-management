package com.emro.dictionary.history.service;

import com.emro.dictionary.history.dto.MultlLangHistoryDTO;
import com.emro.dictionary.history.dto.RequestDetailHistoryDTO;
import com.emro.dictionary.history.repository.HistoryMapper;
import com.emro.dictionary.notification.service.MessageService;
import com.emro.dictionary.request.repository.RequestMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class HistoryService {

	private final HistoryMapper historyMapper;
	private final MessageService messageService;
	private final RequestMapper requestMapper;

	public List<RequestDetailHistoryDTO> getHistoryByDtlId(Long dtlId) {
		return historyMapper.findHistoryByDtlId(dtlId);
	}

	public void addHistory(Long dtlId, String commentText, String imagePath, Long writerId) {
		RequestDetailHistoryDTO history = new RequestDetailHistoryDTO();
		history.setDtlId(dtlId);
		history.setCommentText(commentText);
		history.setImagePath(imagePath);
		history.setWriterId(writerId);
		historyMapper.insertHistory(history);

		Long reqDtlHisId = history.getDtlHisId();

		if (reqDtlHisId != null && commentText != null && !commentText.isEmpty()) {
			Long reqId = requestMapper.findRequestDetailByDtlId(dtlId).getReqId();
			Long reqUserId = requestMapper.findUserIdByReqId(reqId);
			messageService.notifyOnCommentAdded(reqDtlHisId, dtlId, commentText, reqUserId);
		}
	}

	public List<MultlLangHistoryDTO> getRequestHistoryByMultlangKey(String multlangKey) {
		return historyMapper.findByMultlangKey(multlangKey);
	}
}
