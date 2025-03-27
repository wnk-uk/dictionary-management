package com.emro.dictionary.history.service;

import com.emro.dictionary.history.dto.RequestDetailHistoryDTO;
import com.emro.dictionary.history.repository.HistoryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class HistoryService {

	private final HistoryMapper historyMapper;

	public List<RequestDetailHistoryDTO> getHistoryByDtlId(Long dtlId) {
		return historyMapper.findHistoryByDtlId(dtlId);
	}

	public void addHistory(Long dtlId, String commentText, String imagePath, String writerNm) {
		RequestDetailHistoryDTO history = new RequestDetailHistoryDTO();
		history.setDtlId(dtlId);
		history.setCommentText(commentText);
		history.setImagePath(imagePath);
		history.setWriterNm(writerNm);
		historyMapper.insertHistory(history);
	}
}
