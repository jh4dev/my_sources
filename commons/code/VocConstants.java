package com.lottewellfood.sfa.common.code;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface VocConstants {

	/**
	 * VOC COMMON CODE
	 * */
	public static final String	_VOC_RESULT_STATUS_FIELD				= "rstCd";
	public static final String	_VOC_RESULT_MESSAGE_FIELD				= "rstMsg";
	public static final String	_VOC_RESULT_DATA						= "rstData";
	public static final String	_VOC_RESULT_SUCCESS_CODE				= "10";
	public static final String	_VOC_RESULT_FAIL_CODE					= "91"; 		//필수값 누락
	
	public static final String	_COMMON_REQ_SYSTEM_CODE					= "SFA";		//요청 시스템 코드
	public static final String	_COMMON_FILE_CONTS_CODE					= "VOCFILE"; 	//접수내용 첨부
	public static final String	_COMMON_TRANS_CHANGE_DVN				= "02"; 		//이관 담당 -> 이전 담당자
	public static final String	_COMMON_PAGE_SIZE_AND_NO				= "0";
	public static final String	_COMMON_SEARCH_PERIOD_DVN				= "01";			//접수일
	public static final String	_COMMON_DEAL_DVN						= "21";			//처리 구분 (방문 결과)
	public static final String	_COMMON_VOC_DVN_CODE					= "02";			//상담 구분
	public static final String	_COMMON_RECE_WAY_CODE					= "04";			//접수 경로
	public static final String	_COMMON_PROC_STAT_CODE					= "10";			//진행 상태

	
	public static final String	_COMMON_DEV_USER						= "DEV00";		//테스트 유저
	
	public static final String  _COMMON_REG_RECE_CNTN					= "SFA 반품 등록에 의한 VOC 접수\n" + "■ 반품사유 : "; // voc 등록 접수내용
	public static final String  _AUART_QUALITY_RETURN					= "ZRQ"; 		// 반품 유형 [품질 반품]
	public static final String  _COMMON_APRV_ST_CD						= "1"; 			// voc 승인 상태 코드
	public static final String  _COMMON_EMPTY_FILED						= ""; 			// VOC 빈값 공통
	public static final String _COMMON_STATUS_PROGRESS					= "20"; 	// VOC 상태 값 "20" : 처리중
	public static final String _COMMON_STATUS_FINISH					= "80"; 	// VOC 상태 값 "80" : 처리 완료
	public static final List<String> _COMMON_STATUS_LIST				= Arrays.asList(_COMMON_STATUS_PROGRESS, _COMMON_STATUS_FINISH); 	// VOC 상태 값 (처리 단계 "20" : 처리중, "80" : 처리 완료)
	public static final String _COMMON_SEARCH_DEAL						= "schDealUserId"; 	// 처리 담당자 조회
	public static final String _COMMON_SEARCH_RECE						= "schReceUserId"; 	// 내용 작성자 조회

	public static final List<String> _COMMON_SEARCH_REQ					= Arrays.asList(_COMMON_SEARCH_DEAL, _COMMON_SEARCH_RECE); 	// VOC 상태 값 (처리 단계 "20" : 처리중, "80" : 처리 완료)

	public static final String	_COMMON_SEARCH_START_DATE				= "00000101";	//조회 시작 일자
	public static final String	_COMMON_SEARCH_END_DATE					= "99991231";	//조회 종료 일자
}
