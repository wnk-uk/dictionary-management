package com.emro.dictionary.request.repository;

import com.emro.dictionary.request.dto.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * REQ 및 REQ_DTL 테이블과의 데이터 액세스를 담당하는 MyBatis Mapper
 */
@Mapper
public interface RequestMapper {

	/**
	 * 요청 저장 (REQ 테이블에 데이터 삽입)
	 */
	@Insert("""
		INSERT INTO req (req_user_id, req_dttm, sts, acpt_sts, image_path, editor_content)
		VALUES (#{reqUserId}, NOW(), 'C', 'REQUEST', #{imagePath}, #{editorContent})
    """)
	@Options(useGeneratedKeys = true, keyProperty = "reqId")
	void insertRequest(MultLangRequestDTO request);

	/**
	 * 요청 상세 저장 (REQ_DTL 테이블에 데이터 삽입)
	 */
	@Insert("""
        INSERT INTO req_dtl (req_id, existing_word, multlang_ccd, multlang_key, multlang_suggested_transl_cont, 
                                 multlang_transl_cont_abbr, multlang_typ, screen_path, 
                                 source_path, comment, reg_sts)
        VALUES (#{reqId}, #{detail.existingWord}, #{detail.multlangCcd}, #{detail.multlangKey}, #{detail.multlangSuggestedTranslCont}, 
                #{detail.multlangTranslContAbbr}, #{detail.multlangTyp}, #{detail.screenPath}, 
                #{detail.sourcePath}, #{detail.comment}, 'PENDING')
    """)
	@Options(useGeneratedKeys = true, keyProperty = "detail.dtlId")
	void insertRequestDetail(@Param("reqId") Long reqId, @Param("detail") MultLangRequestDetailDTO detail);

	/**
	 * STS가 HOLDING이 아닌 데이터 조회 (해당 user의 것만)
	 */
	@Select("""
		SELECT r.*, u.usr_nm AS req_usr_nm
		FROM req r
		LEFT JOIN users u ON r.req_user_id = u.id
		WHERE r.acpt_sts <> 'HOLDING'
		AND (CAST(#{userId} AS TEXT) IS NULL OR r.req_user_id = #{userId})
		ORDER BY r.req_dttm DESC
    """)
	@Results({
			@Result(property = "reqId", column = "req_id", id = true),
			@Result(property = "reqUserNm", column = "req_usr_nm"),
			@Result(property = "acptSts", column = "acpt_sts"),
			@Result(property = "reqDttm", column = "req_dttm"),
			@Result(property = "imagePath", column = "image_path"),
			@Result(property = "editorContent", column = "editor_content"),
			@Result(property = "details", column = "req_id",
					many = @Many(select = "findRequestDetails"))
	})
	List<MultLangListDTO> findAllRequestsExceptHOLDING(@Param("userId") Long userId);

	/**
	 * 특정 ACPT_STS 값으로 데이터 조회 (해당 user의 것만)
	 */
	@Select("""
		SELECT r.*, u.usr_nm AS req_usr_nm
		FROM req r
		LEFT JOIN users u ON r.req_user_id = u.id
		WHERE r.acpt_sts = #{acptSts}
		AND (CAST(#{userId} AS TEXT) IS NULL OR r.req_user_id = #{userId})
		ORDER BY r.req_dttm DESC
    """)
	@Results({
			@Result(property = "reqId", column = "req_id", id = true),
			@Result(property = "reqUserNm", column = "req_usr_nm"),
			@Result(property = "acptSts", column = "acpt_sts"),
			@Result(property = "reqDttm", column = "req_dttm"),
			@Result(property = "imagePath", column = "image_path"),
			@Result(property = "editorContent", column = "editor_content"),
			@Result(property = "details", column = "req_id",
					many = @Many(select = "findRequestDetails"))
	})
	List<MultLangListDTO> findRequestsByAcptSts(@Param("acptSts") String acptSts, @Param("userId") Long userId);

	/**
	 * 요청 상태에 따른 갯수 세기
	 */
	@Select("""
        SELECT COALESCE(COUNT(*), 0) FROM REQ
        WHERE acpt_sts = #{acptSts}
    """)
	Integer findByAcptStatusCount(String acptSts);

	/**
	 * 요청시간 상위 Top10 특정 요청 가져오기 (특정 acptSts)
	 */
	@Select("""
		SELECT r.req_id, r.req_dttm, r.acpt_sts, u.usr_nm AS req_usr_nm
		FROM req r
		LEFT JOIN users u ON r.req_user_id = u.id
		WHERE r.acpt_sts = #{acptSts}
		 AND (r.req_user_id = #{userId} OR CAST(#{userId} AS TEXT) IS NULL)
		ORDER BY r.req_dttm DESC
		LIMIT 10
    """)
	@Results({
			@Result(property = "reqId", column = "req_id", id = true),
			@Result(property = "reqUserNm", column = "req_usr_nm"),
			@Result(property = "reqDttm", column = "req_dttm"),
			@Result(property = "acptSts", column = "acpt_sts"),
			@Result(property = "details", column = "req_id",
					many = @Many(select = "findRequestDetailsByReqId"))
	})
	List<MultLangListDTO> findTop10RecentHOLDINGingRequests(@Param("acptSts") String acptSts,  @Param("userId") Long userId);

	/**
	 * 선택된 요청(REQ)의 상태를 업데이트
	 */
	@Update("""
        UPDATE req
        SET acpt_sts = #{acptSts}
        WHERE req_id = #{reqId}
    """)
	void updateRequestAcptSts(@Param("reqId") Long reqId, @Param("acptSts") String acptSts);

	/**
	 * 선택된 요청들(REQ_DTL)의 상태를 업데이트
	 */
	@Update("""
        UPDATE 
            req_dtl
        SET 
            reg_sts = #{regSts},
            multlang_transl_cont = #{multlangTranslCont}
        WHERE dtl_id = #{dtlId}
    """)
	void updateRequestDetailRegSts(@Param("dtlId") Long dtlId, @Param("regSts") String regSts, @Param("multlangTranslCont") String multlangTranslCont);

	/**
	 * 해당 Request Id에 대한 Detail 상태값 조회
	 */
	@Select("""
        SELECT reg_sts 
        FROM req_dtl 
        WHERE req_id = #{reqId}
    """)
	List<String> getDetailStatusesByReqId(@Param("reqId") Long reqId);
	
	/**
	 * RequestDetail 조회 (REQ_DTL 테이블)
	 */
	@Select("""
        SELECT * FROM req_dtl 
        WHERE req_id = #{reqId}
    """)
	List<MultLangDetailDTO> findRequestDetails(@Param("reqId") Long reqId);

	/**
	 * reqId 을 이용한 req 및 detail 조회(REQ)
	 */
	@Select("""
		SELECT r.*, u.usr_nm AS req_usr_nm
		FROM req r
		LEFT JOIN users u ON r.req_user_id = u.id
		WHERE r.req_id = #{reqId}
		""")
	@Results({
			@Result(property = "reqId", column = "req_id", id = true),
			@Result(property = "reqUserNm", column = "req_usr_nm"),
			@Result(property = "reqDttm", column = "req_dttm"),
			@Result(property = "acptSts", column = "acpt_sts"),
			@Result(property = "imagePath", column = "image_path"),
			@Result(property = "editorContent", column = "editor_content"),
			@Result(property = "details", column = "req_id",
					many = @Many(select = "findRequestDetailsByReqId"))
	})
	MultLangListDTO getRequestByReqId(@Param("reqId") Long reqId);

	/**
	 * reqId 을 이용한 detail List 조회(REQ_DTL)
	 * findByDicReqId , findTop10RecentHOLDINGingRequests 내 사용
	 */
	@Select("""
		SELECT * 
		FROM req_dtl 
		WHERE req_id = #{reqId}
		""")
	@Results({
			@Result(property = "dtlId", column = "dtl_id"),
			@Result(property = "existingWord", column = "existing_word"),
			@Result(property = "multlangCcd", column = "multlang_ccd"),
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
	List<MultLangRequestDetailDTO> findRequestDetailsByReqId(@Param("reqId") Long reqId);

	/**
	 * dtl_id 을 이용한 detail 조회(REQ_DTL)
	 */
	@Select("""
		SELECT * 
		FROM req_dtl 
		WHERE dtl_id = #{dtlId}
		""")
	@Results({
			@Result(property = "dtlId", column = "dtl_id"),
			@Result(property = "reqId", column = "req_id"),
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
	MultLangRequestDetailDTO  findRequestDetailByDtlId(@Param("dtlId") Long dtlId);

	/**
	 * 선택된 요청(REQ) 내용 업데이트
	 */
	@Update("""
		UPDATE 
            req_dtl
        SET 
            existing_word = #{existingWord},
            multlang_ccd = #{multlangCcd},
            multlang_key = #{multlangKey},
            multlang_transl_cont = #{multlangTranslCont},
            multlang_suggested_transl_cont = #{multlangSuggestedTranslCont},
            multlang_transl_cont_abbr = #{multlangTranslContAbbr},
            multlang_typ = #{multlangTyp}
        WHERE dtl_id = #{dtlId}
	""")
	void updateRequestDetail(UpdateRequestDetailDTO dto);

	/**
	 * reqId 에 따른 UserId 조회
	 */
	@Select("""
	SELECT req_user_id
	FROM req
	WHERE req_id = #{reqId}
	""")
	Long findUserIdByReqId(Long reqId);
}