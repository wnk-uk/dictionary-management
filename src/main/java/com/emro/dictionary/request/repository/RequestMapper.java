package com.emro.dictionary.request.repository;

import com.emro.dictionary.request.dto.MultLangDetailListDTO;
import com.emro.dictionary.request.dto.MultLangListDTO;
import com.emro.dictionary.request.dto.MultLangRequestDTO;
import com.emro.dictionary.request.dto.MultLangRequestDetailDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * DIC_REQ 및 DIC_REQ_DTL 테이블과의 데이터 액세스를 담당하는 MyBatis Mapper
 */
@Mapper
public interface RequestMapper {

	/**
	 * 요청 저장 (DIC_REQ 테이블에 데이터 삽입)
	 */
	@Insert("""
        INSERT INTO DIC_REQ (req_usr_nm, req_dttm, sts, acpt_sts, image_path, editor_content)
        VALUES (#{reqUsrNm}, NOW(), 'C', 'REQUEST', #{imagePath}, #{editorContent})
    """)
	@Options(useGeneratedKeys = true, keyProperty = "dicReqId")
	void insertRequest(MultLangRequestDTO request);

	/**
	 * 요청 상세 저장 (DIC_REQ_DTL 테이블에 데이터 삽입)
	 */
	@Insert("""
        INSERT INTO DIC_REQ_DTL (dic_req_id, existing_word, multlang_ccd, multlang_key, multlang_suggested_transl_cont, 
                                 multlang_transl_cont_abbr, multlang_typ, screen_path, 
                                 source_path, comment, reg_sts)
        VALUES (#{dicReqId}, #{detail.existingWord}, #{detail.multlangCcd}, #{detail.multlangKey}, #{detail.multlangSuggestedTranslCont}, 
                #{detail.multlangTranslContAbbr}, #{detail.multlangTyp}, #{detail.screenPath}, 
                #{detail.sourcePath}, #{detail.comment}, 'PENDING')
    """)
	void insertRequestDetail(@Param("dicReqId") Long dicReqId, @Param("detail") MultLangRequestDetailDTO detail);

	/**
	 * STS가 HOLDING이 아닌 데이터 조회 (requester 선택적)
	 */
	@Select("""
        SELECT * FROM DIC_REQ 
        WHERE acpt_sts <> 'HOLDING'
    	AND (#{requester} IS NULL OR req_usr_nm = #{requester}) 
        ORDER BY req_dttm DESC
    """)
	@Results({
			@Result(property = "dicReqId", column = "dic_req_id", id = true),
			@Result(property = "reqUsrNm", column = "req_usr_nm"),
			@Result(property = "acptSts", column = "acpt_sts"),
			@Result(property = "reqDttm", column = "req_dttm"),
			@Result(property = "imagePath", column = "image_path"),
			@Result(property = "editorContent", column = "editor_content"),
			@Result(property = "details", column = "dic_req_id",
					many = @Many(select = "findRequestDetails"))
	})
	List<MultLangListDTO> findAllRequestsExceptHOLDING(@Param("requester") String requester);

	/**
	 * 특정 ACPT_STS 값으로 데이터 조회 (requester 선택적)
	 */
	@Select("""
        SELECT * FROM DIC_REQ 
        WHERE acpt_sts = #{acptSts}
        AND (#{requester} IS NULL OR req_usr_nm = #{requester}) 
        ORDER BY req_dttm DESC
    """)
	@Results({
			@Result(property = "dicReqId", column = "dic_req_id", id = true),
			@Result(property = "reqUsrNm", column = "req_usr_nm"),
			@Result(property = "acptSts", column = "acpt_sts"),
			@Result(property = "reqDttm", column = "req_dttm"),
			@Result(property = "imagePath", column = "image_path"),
			@Result(property = "editorContent", column = "editor_content"),
			@Result(property = "details", column = "dic_req_id",
					many = @Many(select = "findRequestDetails"))
	})
	List<MultLangListDTO> findRequestsByAcptSts(@Param("acptSts") String acptSts, @Param("requester") String requester);

	/**
	 * 요청 상태에 따른 갯수 세기
	 */
	@Select("""
        SELECT COALESCE(COUNT(*), 0) FROM DIC_REQ
        WHERE acpt_sts = #{acptSts}
    """)
	Integer findByAcptStatusCount(String acptSts);

	/**
	 * 요청시간 상위 Top10 특정 요청 가져오기 (특정 acptSts)
	 */
	@Select("""
        SELECT req.dic_req_id, req.req_usr_nm, req.req_dttm, req.acpt_sts
        FROM dic_req req
        WHERE req.acpt_sts = #{acptSts}
        AND (#{requester} IS NULL OR req_usr_nm = #{requester}) 
        ORDER BY req.req_dttm DESC
        LIMIT 10
    """)
	@Results({
			@Result(property = "dicReqId", column = "dic_req_id", id = true),
			@Result(property = "reqUsrNm", column = "req_usr_nm"),
			@Result(property = "reqDttm", column = "req_dttm"),
			@Result(property = "acptSts", column = "acpt_sts"),
			@Result(property = "details", column = "dic_req_id",
					many = @Many(select = "findRequestDetailsByDicReqId"))
	})
	List<MultLangListDTO> findTop10RecentHOLDINGingRequests(@Param("acptSts") String acptSts,  @Param("requester") String requester);

	/**
	 * 선택된 요청(DIC_REQ)의 상태를 업데이트
	 */
	@Update("""
        UPDATE DIC_REQ
        SET acpt_sts = #{acptSts}
        WHERE dic_req_id = #{dicReqId}
    """)
	void updateRequestAcptSts(@Param("dicReqId") Long dicReqId, @Param("acptSts") String acptSts);

	/**
	 * 선택된 요청들(DIC_REQ_DTL)의 상태를 업데이트
	 */
	@Update("""
        UPDATE 
            DIC_REQ_DTL
        SET 
            reg_sts = #{regSts},
            multlang_transl_cont = #{multlangTranslCont}
        WHERE id = #{id}
    """)
	void updateRequestDetailRegSts(@Param("id") Long id, @Param("regSts") String regSts, @Param("multlangTranslCont") String multlangTranslCont);

	/**
	 * 해당 Request Id에 대한 Detail 상태값 조회
	 */
	@Select("""
        SELECT reg_sts 
        FROM DIC_REQ_DTL 
        WHERE dic_req_id = #{dicReqId}
    """)
	List<String> getDetailStatusesByDicReqId(@Param("dicReqId") Long dicReqId);
	
	/**
	 * RequestDetail 조회 (DIC_REQ_DTL 테이블)
	 */
	@Select("""
        SELECT * FROM DIC_REQ_DTL 
        WHERE dic_req_id = #{dicReqId}
    """)
	List<MultLangDetailListDTO> findRequestDetails(@Param("dicReqId") Long dicReqId);

	/**
	 * dicReqId 을 이용한 detail 조회(DIC_REQ)
	 */
	@Select("""
		SELECT * 
		FROM DIC_REQ 
		WHERE dic_req_id = #{dicReqId}
		""")
	@Results({
			@Result(property = "dicReqId", column = "dic_req_id", id = true),
			@Result(property = "reqUsrNm", column = "req_usr_nm"),
			@Result(property = "reqDttm", column = "req_dttm"),
			@Result(property = "acptSts", column = "acpt_sts"),
			@Result(property = "imagePath", column = "image_path"),
			@Result(property = "editorContent", column = "editor_content"),
			@Result(property = "details", column = "dic_req_id",
					many = @Many(select = "findRequestDetailsByDicReqId"))
	})
	MultLangListDTO findByDicReqId(@Param("dicReqId") String dicReqId);

	/**
	 * dicReqId 을 이용한 detail 조회(DIC_REQ_DTL)
	 */
	@Select("""
		SELECT * 
		FROM DIC_REQ_DTL 
		WHERE dic_req_id = #{dicReqId}
		""")
	@Results({
			@Result(property = "id", column = "id"),
			@Result(property = "existingWord", column = "existing_word"),
			@Result(property = "multlangKey", column = "multlang_key"),
			@Result(property = "multlangTranslCont", column = "multlang_transl_cont"),
			@Result(property = "multlangSuggestedTranslCont", column = "multlang_suggested_transl_cont"),
			@Result(property = "multlangTranslContAbbr", column = "multlang_transl_cont_abbr"),
			@Result(property = "multlangTyp", column = "multlang_typ"),
			@Result(property = "screenPath", column = "screen_path"),
			@Result(property = "sourcePath", column = "source_path"),
			@Result(property = "comment", column = "comment"),
			@Result(property = "regSts", column = "reg_sts")
	})
	List<MultLangRequestDetailDTO> findRequestDetailsByDicReqId(@Param("dicReqId") String dicReqId);
}