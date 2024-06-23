package com.lottewellfood.sfa.common.code;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface Constants {

	public static final String	_TRANSACTION_INSERT 				= 	"I";
	public static final String	_TRANSACTION_UPDATE 				= 	"U";
	public static final String	_TRANSACTION_DELETE 				= 	"D";
	
	public static final String	_FLAG_VAL_Y							=	"Y";
	public static final String	_FLAG_VAL_N							=	"N";
		
	public static final String 	_ATCH_WORK_DVS_CD_COMMUUNITY_PST	= 	"001";
	public static final String 	_ATCH_WORK_DVS_CD_COMMUUNITY_CMNT	= 	"002";
	public static final String 	_ATCH_WORK_DVS_CD_ISP				= 	"003";
	public static final String 	_ATCH_WORK_DVS_CD_SHLEF				= 	"004";
	public static final String 	_ATCH_WORK_DVS_CD_SWEET_MARKET		= 	"005";
	public static final String 	_ATCH_WORK_DVS_CD_MATNR_DETAIL		= 	"006";
	public static final String 	_ATCH_WORK_DVS_CD_ACTV_CHKLIST		= 	"007";
	public static final String 	_ATCH_WORK_DVS_CD_NOTICE			= 	"009";
	public static final String 	_ATCH_WORK_DVS_CD_ACTV_PROMOTION	= 	"010";
	public static final String 	_ATCH_WORK_DVS_CD_QLT_RETURN_PRD	= 	"012";
	public static final String 	_ATCH_WORK_DVS_CD_QLT_RETURN_EXPIRE	= 	"013";
	public static final String 	_ATCH_WORK_DVS_CD_BRANCH_SHELF		= 	"014";
	public static final List<String> _ALLOW_UNPACK_DRM_WORK_DVS_LIST	= Arrays.asList(_ATCH_WORK_DVS_CD_COMMUUNITY_PST, _ATCH_WORK_DVS_CD_COMMUUNITY_CMNT, _ATCH_WORK_DVS_CD_NOTICE);

	public static final int		_SINGLE_NODE						=	0;
	
	public static final String _PPLNTW_DUTY_CD_CHILSEONG_MANAGER	=	"CM"; //피플 칠성관리자
	public static final String _PPLNTW_DUTY_CD_BRNC_MANAGER			=	"04"; //피플네트워크 지사장 직무코드(직책)
	public static final String _PPLNTW_DUTY_CD_BIZ_MANAGER			=	"06"; //피플네트워크 사업소장 직무코드(직책)
	public static final String _PPLNTW_DUTY_CD_FAMEMP				=	"37"; //피플네트워크 패밀리사원 직무코드(직책)
	public static final String _PPLNTW_DUTY_CD_PROMOTION_COACH		=	"38"; //피플네트워크 판촉코치 직무코드(직책)
	
	public static final String _PPLNTW_TEAM_NM_CHILSEONG_MANAGER	=	"칠성관리자"; //피플 칠성관리자
	
	public static final String 	_ENV_DEV							=	"dev";
	public static final String	_ENV_PROD							=	"prod";
	
	public static final int		_SFA_NO_PERMISSION_USER				=	-1;
	public static final int		_SFA_DUTY_EXECUTIVES				=	0;
	public static final int		_SFA_DUTY_BRANCH_MANAGER			=	1;
	public static final int 	_SFA_DUTY_SALES_MANAGER				=	2;
	public static final int 	_SFA_DUTY_SALES_EMP					=	3;
	
	public static DateTimeFormatter _DEFAULT_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
	public static DateTimeFormatter _DEFAULT_TIME_FORMATTER = DateTimeFormatter.ofPattern("HHmmss");
	
	public static final int		_LOG_MAXIMUM_SIZE					=	2000;
	
	public static final String	_HR_CHECK_PASS_ZDUTY_G				=	"G";	// 진열사원
	public static final String	_HR_CHECK_PASS_ZDUTY_I				=	"I";	// 창고장
	public static final String	_HR_SHIFT_CODE_OFF					=	"OFF";	// HR 근무시간 OFF
	
	public static final String	_SP_RESULT_STATUS_FIELD				= "E_RETURN";
	public static final String	_SP_RESULT_MESSAGE_FIELD				= "E_MESSAGE";
	public static final String	_SP_RESULT_SUCCESS_CODE				= "S";
	public static final String	_SP_RESULT_FAIL_CODE					= "F";
	public static final String	_COMMON_EMPTY_FIELD					= "";

	public static final long 	_STARTWORK_MAX_MEMORYSIZE 			= 3145728;

	public static final String 	_USER_DEVICE_TABLET					= "SFATABLET";
	public static final String 	_USER_DEVICE_MOBLIE					= "SFAHTT";
	public static final String 	_USER_DEVICE_PPN_MOBILE				= "SFAPEOPLENETWORKS";
	public static final List<String> _USER_DEVICE_LIST				= Arrays.asList(_USER_DEVICE_TABLET, _USER_DEVICE_MOBLIE, _USER_DEVICE_PPN_MOBILE);

}
