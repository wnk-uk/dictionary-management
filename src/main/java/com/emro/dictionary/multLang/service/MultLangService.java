package com.emro.dictionary.multLang.service;

import com.emro.dictionary.multLang.dto.MultLangDTO;
import com.emro.dictionary.multLang.repository.MultLangMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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

	public MultLangDTO findByMultlangKey(String multlangCcd, String multlangKey) {
		return multLangMapper.findByMultlangKey(multlangCcd, multlangKey);
	}


	public void saveOrUpdateMultlang(String multlangCcd, String multlangKey, String multlangTranslCont,
	                                 String multlangTranslContAbbr, String multlangTyp, String username) {
		MultLangDTO existingMultlang = findByMultlangKey(multlangCcd, multlangKey);

		if (existingMultlang != null) {
			// 기존 데이터가 있으면 UPDATE
			existingMultlang.setMultlangTranslCont(multlangTranslCont);
			existingMultlang.setMultlangTranslContAbbr(multlangTranslContAbbr);
			existingMultlang.setMultlangTyp(multlangTyp);
			existingMultlang.setModrId(username);
			existingMultlang.setModDttm(LocalDateTime.now());

			multLangMapper.updateMultlang(existingMultlang);
		} else {
			// 기존 데이터가 없으면 INSERT
			MultLangDTO newMultlang = new MultLangDTO();
			newMultlang.setMultlangCcd(multlangCcd);
			newMultlang.setMultlangKey(multlangKey);
			newMultlang.setMultlangTranslCont(multlangTranslCont);
			newMultlang.setMultlangTranslContAbbr(multlangTranslContAbbr);
			newMultlang.setMultlangTyp(multlangTyp);
			newMultlang.setRegrId(username);
			newMultlang.setRegDttm(LocalDateTime.now());
			newMultlang.setMultlangModDttm(LocalDateTime.now());
			newMultlang.setSts("C");
			newMultlang.setMultlangAbbrUseYn("N"); // 기본값 설정

			multLangMapper.insertMultlang(newMultlang);
		}
	}

}
