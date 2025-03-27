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

	@Select("""
	SELECT *
	FROM Multlang
	WHERE multlang_key=#{multlangKey}
	""")
	MultLangDTO findByMultlangKey(@Param("multlangCcd") String multlangCcd, @Param("multlangKey") String multlangKey);

	@Update("""
        UPDATE MULTLANG
        SET multlang_transl_cont = #{multlangTranslCont},
            multlang_transl_cont_abbr = #{multlangTranslContAbbr},
            multlang_typ = #{multlangTyp},
            modr_id = #{modrId},
            mod_dttm = #{modDttm},
            multlang_mod_dttm = #{multlangModDttm}
        WHERE multlang_ccd = #{multlangCcd}
        AND multlang_key = #{multlangKey}
    """)
	void updateMultlang(MultLangDTO multLangDTO);

	@Insert("""
        INSERT INTO MULTLANG (
            multlang_ccd, multlang_key, multlang_transl_cont, multlang_transl_cont_abbr, 
            multlang_typ, regr_id, reg_dttm, multlang_mod_dttm, sts, multlang_abbr_use_yn
        ) VALUES (
            #{multlangCcd}, #{multlangKey}, #{multlangTranslCont}, #{multlangTranslContAbbr},
            #{multlangTyp}, #{regrId}, #{regDttm}, #{multlangModDttm}, #{sts}, #{multlangAbbrUseYn}
        )
    """)
	void insertMultlang(MultLangDTO multLangDTO);
}

