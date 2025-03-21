package com.emro.dictionary.multLang.service;

import com.emro.dictionary.multLang.dto.MultLangDTO;
import com.emro.dictionary.multLang.repository.MultLangMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MultLangService {

	private final MultLangMapper multLangMapper;

	public List<MultLangDTO> getAllMultlangs() {
		return multLangMapper.findAll();
	}

	public List<String> searchExistingWord(String request) {
		return multLangMapper.findExistingWords(request);
	}
	

}
