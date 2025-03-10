package com.emro.dictionary.lang;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LangMapper {

    @Select("SELECT dic_req_id AS dicReqId, req_usr_nm AS reqUsrNm, req_dttm AS reqDttm FROM DIC_REQ")
    List<LangDTO> findByReqListAll(LangRequest request);

    @Select("SELECT * FROM DIC_REQ_DTL")
    LangDTO findByReqDetailListAll();

    @Select("SELECT * FROM Multlang")
    List<MultLangDTO> findAll();

	@Select("SELECT DISTINCT multlang_key FROM MULTLANG WHERE multlang_key LIKE '%' || #{request} || '%'")
	List<String> findExistingWords(String request);

    // 요청 저장 (DIC_REQ 테이블에 데이터 삽입)
    @Insert("""
        INSERT INTO DIC_REQ (req_usr_nm, req_dttm, sts, acpt_sts)
        VALUES (#{reqUsrNm}, NOW(), 'C', 'REQ')
    """)
    @Options(useGeneratedKeys = true, keyProperty = "dicReqId")
    void insertRequest(MultLangRequestDTO request);

    // 요청 상세 저장 (DIC_REQ_DTL 테이블에 데이터 삽입)
    @Insert("""
        INSERT INTO DIC_REQ_DTL (dic_req_id, existing_word, multlang_ccd, multlang_key, multlang_transl_cont, 
                                 multlang_transl_cont_abbr, multlang_typ, screen_path, 
                                 source_path, comment, reg_sts)
        VALUES (#{dicReqId}, #{detail.existingWord}, #{detail.multlangCcd}, #{detail.multlangKey}, #{detail.multlangTranslCont}, 
                #{detail.multlangTranslContAbbr}, #{detail.multlangTyp}, #{detail.screenPath}, 
                #{detail.sourcePath}, #{detail.comment}, 'PENDING')
    """)
    void insertRequestDetail(@Param("dicReqId") Long dicReqId, @Param("detail") MultLangDetailDTO detail);
}
