package web.custom.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("file")
public class FileDownloadController2 {

	private Logger logger = LoggerFactory.getLogger(FileDownloadController.class);
	
	/**	
	 * 파일 다운로드
	 * @return 파일
	 */	
	
	@RequestMapping(value = { "/{fileKey}/download.do" }, method = {RequestMethod.GET })
	public void nasImageDownload( HttpServletResponse resp,
			@PathVariable("type") String type, @PathVariable("atchFileSn") String atchFileSn) {
		
		logger.info(String.format("========Start ImageDownload from %s, %s=", type, atchFileSn));
		
		File file = null;
		try {
			//fileKey를 통하여 파일 정보 추출
			file = new File("");
			
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
		
		flush(resp, file.getAbsolutePath(), file.getName());
	}
	

	private void flush(HttpServletResponse resp, String fileAccessPath, String fileDownloadName) {
		
		BufferedInputStream	in	=	null;
		OutputStream outputStream = null;
		try {
			File	dFile	=	new	File(fileAccessPath); 
			int		fSize	=	(int) dFile.length();
			
			if(fSize > 0) {
				String	encodedFileName	=	"attachment; filename*=" + "UTF-8" + "''" + URLEncoder.encode(fileDownloadName, "UTF-8");
				resp.setContentType("application/octet-stream; charset=utf-8");
				resp.setHeader("Content-Disposition",  encodedFileName);
				resp.setContentLength(fSize);
				in = new BufferedInputStream(new FileInputStream(dFile));
				outputStream	=	new	BufferedOutputStream(resp.getOutputStream());
				try {
					final byte data[] = new byte[1024];
					int count;
					while ((count = in.read(data, 0, 1024)) != -1) {
						outputStream.write(data, 0, count);
					}
					
					outputStream.flush();
				} finally {
					in.close();
					outputStream.close();
				}
			} else {
				throw new FileNotFoundException("파일을 찾을 수 없습니다.");
			}
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (Exception e) {
			logger.error("", e);
			throw new RuntimeException(e);
		} finally {
			try {
				if (outputStream != null) {	outputStream.close(); }
				if (in != null) { in.close(); }
				resp.flushBuffer();
			} catch (IOException e) {
				logger.error("", e);
			}
		}
	}
	
	
}
