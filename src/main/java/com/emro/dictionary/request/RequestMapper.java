package com.emro.dictionary.request;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RequestMapper {

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
	void insertRequestDetail(@Param("dicReqId") Long dicReqId, @Param("detail") MultLangRequestDetailDTO detail);
}
