package com.emro.dictionary.multLang.repository;

import com.emro.dictionary.multLang.dto.MultLangDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MultLangMapper {

	@Select("SELECT * FROM Multlang")
	List<MultLangDTO> findAll();

	@Select("SELECT DISTINCT multlang_key FROM MULTLANG WHERE LOWER(multlang_key) LIKE LOWER(CONCAT('%', #{request}, '%'))")
	List<String> findExistingWords(String request);


}

