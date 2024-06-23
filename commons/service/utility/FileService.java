package com.lottewellfood.sfa.common.service.utility;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasoo.fcwpkg.packager.WorkPackager;
import com.fasterxml.jackson.databind.JsonNode;
import com.lottewellfood.sfa.common.code.DBConstants;
import com.lottewellfood.sfa.common.enums.LotteErrors;
import com.lottewellfood.sfa.common.exception.LotteException;
import com.lottewellfood.sfa.common.util.ConvertUtil;
import com.lottewellfood.sfa.common.util.LotteUtil;
import com.lottewellfood.sfa.common.vo.CommonFileVo;
import com.mcnc.bizmob.adapter.DBAdapter;
import com.mcnc.bizmob.common.config.SmartConfig;
import com.mcnc.bizmob.common.util.FileUtil;
import com.mcnc.bizmob.db.type.DBMap;
import com.mcnc.bizmob.hybrid.server.web.dao.LocalFileStorageAccessor;

@Service("fileService")
public class FileService {

	private static final Logger logger = LoggerFactory.getLogger(FileService.class);
	
	@Autowired
	private DBAdapter dbAdapter;
	
	@Autowired
	private LocalFileStorageAccessor uploadStorageAccessor;
	
	/**
	 * @title 파일업로드
	 * @method uploadFile
	 * @desc 파일 업로드(MOB file to File)
	 * @return CommonFileVo
	 * */
	public CommonFileVo uploadFile(String mobUID, File file, Object fileObj) throws LotteException {
		
		CommonFileVo fileVo = null;
		
		try {
			byte[] bytes = uploadStorageAccessor.load(mobUID);
			FileUtils.writeByteArrayToFile(file, bytes);
			
			fileVo = new CommonFileVo(file, ConvertUtil.objectToJsonNode(fileObj));
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new LotteException(LotteErrors.FILE_NOT_FOUND_ERROR, e);
		}
		
		return fileVo;
	}
	
	/**
	 * @title 공통 파일 테이블 조회
	 * @method getFileInfoForDownload
	 * @desc 파일 조회 
	 * @return CommonFileVo
	 * */
	public CommonFileVo getFileInfoForDownload(CommonFileVo reqFileVo) throws LotteException {
		try {
			return dbAdapter.selectOne(
					DBConstants.SFADS
					, DBConstants._MAPPER_NS_COMMON_FILE + DBConstants._SELECT_FILE_FOR_DOWNLOAD
					, reqFileVo
					, CommonFileVo.class);
			
		} catch(Exception e) {
			throw new LotteException(LotteErrors.DB_SELECT_ERROR, e);
		}
	}
	
	public DBMap getWssFileInfoForDownload(Map<String, Object> paramMap) throws LotteException {
		try {
			return dbAdapter.selectOne(DBConstants.SFADS, DBConstants._MAPPER_NS_APPLIENCE + "selectJtAtchmnFlList", paramMap, DBMap.class);
		} catch (Exception e) {
			throw new LotteException(LotteErrors.DB_SELECT_ERROR, e);
		}
	}
	
	/**
	 * @title 공통 파일 테이블 selectList
	 * @method getFileListByAtchFileIdxNo
	 * @desc 파일 목록 조회 
	 * @return List<CommonFileVo>
	 * */
	public List<CommonFileVo> getFileListByAtchFileIdxNo(String atchFileIdxNo) throws LotteException {
		try {
			return dbAdapter.selectList(
				DBConstants.SFADS
				, DBConstants._MAPPER_NS_COMMON_FILE + DBConstants._SELECT_FILE_LIST_BY_ATCH_FILE_IDX_NO
				, atchFileIdxNo
				, CommonFileVo.class);
			
		} catch(Exception e) {
			throw new LotteException(LotteErrors.DB_SELECT_ERROR, e);
		}
	}
	
	/**
	 * @title 공통 파일 테이블 selectList
	 * @method getFileListByAtchFileIdxNo
	 * @desc 파일 목록 조회 
	 * @return List<CommonFileVo>
	 * */
	public List<CommonFileVo> getFileListByAtchFileIdxNo(String atchFileIdxNo, SqlSession sqlSession) throws LotteException {
		
		try {
			return sqlSession.selectList(
					DBConstants._MAPPER_NS_COMMON_FILE + DBConstants._SELECT_FILE_LIST_BY_ATCH_FILE_IDX_NO
					, atchFileIdxNo);
			
		} catch(Exception e) {
			if (e instanceof LotteException) throw e;
			else throw new LotteException(LotteErrors.DB_SELECT_ERROR, e);
		}
	}
	
	/**
	 * @title 공통 파일 테이블 SELECT
	 * @method insertSfaAtchFileBscTb
	 * @desc SFA_ATCH_FILE_BSC_TB INSERT
	 * @return CommonFileVo
	 * */
	public String getCommonAtchFileIdxNo() throws LotteException {
		try {
			return dbAdapter.selectOne(
				DBConstants.SFADS
				, DBConstants._MAPPER_NS_COMMON_FILE + DBConstants._SELECT_ATTACH_FILE_SEQ
				, null
				, String.class);
			
		} catch(Exception e) {
			throw new LotteException(LotteErrors.DB_SELECT_ERROR, e);
		}
	}
	
	/**
	 * @title 공통 파일 테이블 insert
	 * @method insertSfaAtchFileBscTb
	 * @desc SFA_ATCH_FILE_BSC_TB INSERT
	 * @return CommonFileVo
	 * */
	public CommonFileVo insertSfaAtchFileBscTb(CommonFileVo fileVo, SqlSession sqlSession) throws LotteException {
		try {
			sqlSession.insert(
				DBConstants._MAPPER_NS_COMMON_FILE + DBConstants._INSERT_SFA_ATCH_FILE_BSC
				, fileVo);
			
			return fileVo;
			
		} catch(Exception e) {
			if (e instanceof LotteException) throw e;
			else throw new LotteException(LotteErrors.DB_INSERT_ERROR, e);
		}
	}
	
	/**
	 * @title 공통 파일 테이블 update
	 * @method updateSfaAtchFileBscTb
	 * @desc SFA_ATCH_FILE_BSC_TB INSERT
	 * @return CommonFileVo
	 * */
	public void updateSfaAtchFileBscTb(CommonFileVo fileVo, SqlSession sqlSession) throws LotteException {
		try {
			sqlSession.update(
					DBConstants._MAPPER_NS_COMMON_FILE + DBConstants._UPDATE_SFA_ATCH_FILE_BSC
					, fileVo);
		} catch(Exception e) {
			if (e instanceof LotteException) throw e;
			else throw new LotteException(LotteErrors.DB_UPDATE_ERROR, e);
		}
	}
	/**
	 * @title 공통 파일 테이블 delete 단건
	 * @method deleteSfaAtchFileBscTb
	 * @desc 파일 삭제 단건
	 * @return 
	 * */
	public void deleteSfaAtchFileBscTb(CommonFileVo fileVo, SqlSession sqlSession) throws LotteException {
		try {
			sqlSession.delete(
					DBConstants._MAPPER_NS_COMMON_FILE + DBConstants._DELETE_SFA_ATCH_FILE_BSC
					, fileVo);
		} catch(Exception e) {
			if (e instanceof LotteException) throw e;
			else throw new LotteException(LotteErrors.DB_DELETE_ERROR, e);
		}
	}
	
	/**
	 * @title 공통 파일 테이블 delete
	 * @method deleteSfaAtchFileBscTb
	 * @desc SFA_ATCH_FILE_BSC_TB DELETE 다건
	 * @return 
	 * */
	public void deleteSfaAtchFileBscTb(List<CommonFileVo> fileList, SqlSession sqlSession) throws LotteException {
		try {
			DBMap reqMap = new DBMap();
			reqMap.put("mdfrId", fileList.get(0).getMdfrId());
			reqMap.put("fileList", fileList);
			sqlSession.delete(
					DBConstants._MAPPER_NS_COMMON_FILE + DBConstants._DELETE_SFA_ATCH_FILE_BSC_LIST
					, reqMap);
		} catch(Exception e) {
			if (e instanceof LotteException) throw e;
			else throw new LotteException(LotteErrors.DB_DELETE_ERROR, e);
		}
	}
	
	/**
	 * @title	외부용 첨부파일 정보 가져오기
	 * @method 	
	 * */
	public JsonNode selectExternalFileInfo(Object param) throws LotteException {
		DBMap 				paramMap 		= null;
		try {
			paramMap = ConvertUtil.objectToMap(param);
			
			paramMap = dbAdapter.selectOne(
					DBConstants.SFADS
					, DBConstants._MAPPER_NS_COMMON_FILE + DBConstants._SELECT_EXTERNAL_FILE_INFO
					, paramMap
					, DBMap.class);
			
			return ConvertUtil.objectToJsonNode(paramMap);
			
		} catch(Exception e) {
			if (e instanceof LotteException) throw e;
			else throw new LotteException(LotteErrors.DB_SELECT_ERROR);
		}
	}	
	
	/**
	 * @title 파일업로드
	 * @method uploadFile
	 * @desc 파일 업로드(MOB file to File)
	 * @return File
	 * */
	public File uploadFile(String mobUID, File file) throws LotteException {
		
		try {
			byte[] bytes = uploadStorageAccessor.load(mobUID);
			FileUtils.writeByteArrayToFile(file, bytes);
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new LotteException(LotteErrors.FILE_NOT_FOUND_ERROR, e);
		}
		
		return file;
	}
	
	/**
	 * @title 파일삭제
	 * @method deleteFile
	 * @desc 파일 삭제
	 * */
	public void deleteFiles(List<CommonFileVo> fileList) {
		for(CommonFileVo atch : fileList) {
			try {
				new File(atch.getFileSvPth() + File.separator + atch.getSrvrStrgFileNm()).delete();
			} catch (Exception e) {
				logger.error(atch.getFileSvPth() + atch.getSrvrStrgFileNm() + " 경로 파일 삭제 중 오류 발생");
			}
		}
	}
	
	
	/**
	 * @title makeDirectory
	 * @desc make directory 
	 * */
	public void makeDirectory(String dirPath) {
		
		File dir	=	new File(dirPath);
		if(!dir.exists()) {
			dir.mkdirs();
		}
	}
	
	/**
	 * @throws IOException 
	 * @title makeDirectory
	 * @desc make directory 
	 * */
	public void clearDirectory(String dirPath) throws IOException {
		File dir	=	new File(dirPath);
		if(dir.exists()) {
			FileUtils.cleanDirectory(new File(dirPath));
		}
	}
	
	/**
	 * @title setPermission755
	 * @desc set Directory or File permission 755 
	 * */
	public void setPermission755(File target) {
		
		target.setReadable(true, false);
		target.setWritable(false, false);
		target.setWritable(true, true);
		target.setExecutable(true, false);
	}
	
	public String getExtByFileName(String fileName) throws LotteException {
		int index = fileName.lastIndexOf(".");
		if (index > 0) {  
			return fileName.substring(index + 1);   
		} else {
			throw new LotteException(LotteErrors.FILE_NO_EXT_ERROR);
		}
	}

	public String drmUnPacking(MultipartFile file1, String fileDownloadName) throws LotteException {
		
		String 	fileExt 			= FileUtil.getExtension(fileDownloadName); // 파일 확장자
		
		String 	strFsdinitPath 		= SmartConfig.getString("common.drm.fsdinitpath");
		String 	strOrgFilePath 		= SmartConfig.getString("common.drm.encfilepath") + fileDownloadName;
		String 	strTargetFilePath 	= SmartConfig.getString("common.drm.decfilepath") + fileDownloadName;
		String 	strCPID 			= SmartConfig.getString("common.drm.cpid");
		
		File 	fileList 			= null;

		String 	targetPath 			= strTargetFilePath;

		try {
			// 저장할 경로와 파일 이름 지정
			fileList = new File("", "unpack_"+ fileDownloadName);

			// 파일을 저장할 디렉토리가 존재하지 않으면 생성
//			FileHandlerUtil.makeDir(_encfilepath);
//			FileHandlerUtil.makeDir(_decfilepath);
			//TODO
			// MultipartFile을 File로 복사
			file1.transferTo(fileList);

			// 파일이 지정한 경로에 저장됨
		} catch (Exception e) {
			logger.info(e.getMessage());
			// 파일 저장 중 오류가 발생한 경우 예외 처리
		}

		if (fileList.exists()) {
			String strFileName = fileDownloadName + "." + fileExt; // 업로드된 파일명.확장자

			// DRM 적용
			int retVal = 0; // 파일타입 검사결과
			boolean iret = false;
			boolean bret = false;

			WorkPackager objWorkPackager = new WorkPackager();

			retVal = objWorkPackager.GetFileType(strOrgFilePath);
			logger.debug("excelFile retVal = {}", retVal);

			// 지원 확장자의 경우 복호화 진행
			if (retVal == 103) {
				logger.info("복호화 strTargetFilePath  >> " + strTargetFilePath);
				// 암호화 된 파일 복호화
				bret = objWorkPackager.DoExtract(strFsdinitPath, // fsdinit 폴더 FullPath 설정
						strCPID, // 고객사 Key(default)
						strOrgFilePath, // 복호화 대상 문서 FullPath + FileName
						strTargetFilePath + fileDownloadName // 복호화 된 문서 FullPath + FileName
				);

				logger.debug("복호화 결과값 : " + bret);
				logger.debug("복호화 문서 : " + objWorkPackager.getContainerFilePathName());
				logger.debug("오류코드 : " + objWorkPackager.getLastErrorNum());
				logger.debug("오류값 : " + objWorkPackager.getLastErrorStr());

				if (bret) {
					targetPath = strTargetFilePath;
				}
			} else if (retVal == 101) {
				logger.info("파일형태는 " + /*FileTypeStr(retVal) + */ "[" + retVal + "]" + " 입니다.");
				logger.info("MarkAny 파일은 복호화 불가능 합니다.");
			} else if (retVal == 29) {
				logger.info("파일형태는 " + /*FileTypeStr(retVal) + */ "[" + retVal + "]" + " 입니다.");
				logger.info("평문파일은 복호화 과정 불필요 합니다.");
				targetPath = strOrgFilePath;

			} else {
				logger.info("정상적인 암호화 파일이 아닌경우 복호화 불가능 합니다.[" + retVal + "]");
			}

			if (bret) {
				//TODO
				// 복호화된 파일있으면 삭제.
				// 불러온 파일 을 지우기위한 경로설정 먼저 SMART_HOME 기본경로로 설정한다.
				Path filePath1 = Paths.get(strTargetFilePath);
                try {
                    Files.deleteIfExists(filePath1);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else if (bret == false && retVal == 29) {
				// 평문파일 암호화 저장
				logger.info("strFsdinitPath  > " + strFsdinitPath);
				logger.info("strCPID  > " + strCPID);
				logger.info("strOrgFilePath  > " + strOrgFilePath);
				logger.info("strTargetFilePath  > " + strTargetFilePath);
				logger.info("strFileName  > " + strFileName);
				logger.info("strTargetFilePath  > " + strTargetFilePath);

				logger.info("암호화 결과값 : " + bret);
				logger.info("암호화 문서 : " + objWorkPackager.getContainerFilePathName());
				logger.info("오류코드 : " + objWorkPackager.getLastErrorNum());
				logger.info("오류값 : " + objWorkPackager.getLastErrorStr());

			}
		}
		return targetPath;
	}

	public String drmUnPacking(CommonFileVo fileVo) throws LotteException {
		
		String	originFileName			= LotteUtil.getLocalDateTimeString(LocalDateTime.now(), "yyyyMMddHHmmss") + "_" + fileVo.getActlFileNm();
		String	originFilePath			= fileVo.getFileSvFullPth();
		Path	originFile				= Paths.get(originFilePath);
		
		
		String 	strFsdinitPath 		= SmartConfig.getString("common.drm.fsdinitpath." + System.getProperty("spring.profiles.active"));
		String 	strSourceFilePath 	= SmartConfig.getString("common.drm.encfilepath." + System.getProperty("spring.profiles.active")) + originFileName;
		String 	strTargetFilePath 	= SmartConfig.getString("common.drm.decfilepath." + System.getProperty("spring.profiles.active")) + originFileName;
		String 	strCPID 			= SmartConfig.getString("common.drm.cpid");

		Path 	sourceFilePath 		= Paths.get(strSourceFilePath);
		String	returnFilePath		= originFilePath;
		try {
			
			
			//원본파일을 fasoo 소스파일 디렉토리로 복사
			Files.copy(originFile, sourceFilePath, StandardCopyOption.REPLACE_EXISTING);
			
			
			if (Files.exists(sourceFilePath)) {
	
				WorkPackager 	objWorkPackager 	= new WorkPackager();
				int 			retVal 				= objWorkPackager.GetFileType(strSourceFilePath);
				
				logger.debug("excelFile retVal = {}", retVal);
	
				// 지원 확장자의 경우 복호화 진행
				if (retVal == 103) {
					logger.info("복호화 strTargetFilePath  >> " + strTargetFilePath);
					
					// 암호화 된 파일 복호화
					boolean bret = objWorkPackager.DoExtract(strFsdinitPath, // fsdinit 폴더 FullPath 설정
							strCPID, // 고객사 Key(default)
							strSourceFilePath, // 복호화 대상 문서 FullPath + FileName
							strTargetFilePath // 복호화 된 문서 FullPath + FileName
					);
	
					logger.debug("복호화 결과값 : " + bret);
					logger.debug("복호화 문서 : " + objWorkPackager.getContainerFilePathName());
					logger.debug("오류코드 : " + objWorkPackager.getLastErrorNum());
					logger.debug("오류값 : " + objWorkPackager.getLastErrorStr());
	
					returnFilePath = strTargetFilePath;
					
				} else if (retVal == 101) {
					logger.info("파일형태는 " + /*FileTypeStr(retVal) + */ "[" + retVal + "]" + " 입니다.");
					logger.info("MarkAny 파일은 복호화 불가능 합니다.");
					throw new LotteException("DRM" + retVal, "MarkAny 파일은 복호화 불가능 합니다.", new RuntimeException());
					
				} else if (retVal == 29) {
					logger.info("파일형태는 " + /*FileTypeStr(retVal) + */ "[" + retVal + "]" + " 입니다.");
					logger.info("평문파일은 복호화 과정 불필요 합니다.");
					returnFilePath = originFilePath;
				} else {
					logger.info("정상적인 암호화 파일이 아닌경우 복호화 불가능 합니다.[" + retVal + "]");
					throw new LotteException("DRM" + retVal, "정상적인 암호화 파일이 아닌경우 복호화 불가능 합니다.", new RuntimeException());
				}
	
				
				return returnFilePath;
			} else {
				throw new LotteException("DRM" + "999", "파일을 복호화 하던 중 에러가 발생했습니다.", new RuntimeException());
			}
		} catch(Exception e) {
			throw new LotteException("DRM" + "999", "파일을 복호화 하던 중 에러가 발생했습니다.", e);
		} finally {
			 try {
                 Files.deleteIfExists(sourceFilePath);
             } catch (IOException e) {
             } 
		}
	}

	public String getFileWorkDvsCd(String atchFileIdxNo, String atchFileOrd) throws LotteException{

		DBMap 	paramMap 	= new DBMap();
		String 	workDvsCd 	= null;
		try{
			paramMap.put("atchFileIdxNo",atchFileIdxNo);
			paramMap.put("atchFileOrd",atchFileOrd);

			paramMap = dbAdapter.selectOne(
					DBConstants.SFADS
					, DBConstants._MAPPER_NS_COMMON_FILE + DBConstants._SELECT_FILE_WORKDVS_BY_ATCH_FILE_IDX_NO
					, paramMap
					, DBMap.class);
			workDvsCd = paramMap.get("workDvsCd").toString();
			return workDvsCd;
		} catch(Exception e) {
			throw new LotteException(LotteErrors.FILE_NO_EXT_ERROR);
		}
	}
}
