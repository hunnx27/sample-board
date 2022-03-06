package com.twodollar.support.webutils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.view.AbstractView;

public class DownloadView extends AbstractView {
	public static final String DEFAULT_CONTENT_TYPE = MediaType.APPLICATION_OCTET_STREAM_VALUE;
	private static Logger log = LoggerFactory.getLogger(DownloadView.class);

	private File file;
	private String fileName;
	
	public DownloadView(File file){
		this(file, file.getName(), DEFAULT_CONTENT_TYPE);
	}

	public DownloadView(File file, String fileName){
		this(file, fileName, DEFAULT_CONTENT_TYPE);
	}

	public DownloadView(File file, String fileName, String contentType){
		this.file = file;
		this.fileName = fileName;
		
		setContentType(contentType);
	}
	
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String fileName = StringUtils.defaultIfEmpty(this.fileName, file.getName());
		String contentType = getContentType();
		int length = (int) file.length();
		response.setContentType("text/plain;charset=UTF-8");
		response.setContentLength((int) length);
		response.setHeader("Content-Disposition", "attachment; filename=\"" + new String(fileName.getBytes("euc-kr"), "8859_1") + "\"");
		
		response.setHeader("Content-Transfer-Encoding", "binary");

		if(log.isDebugEnabled()){
			Object args = new Object[]{ fileName, contentType, file.getCanonicalPath(), length };
			log.debug("Render to download file. [file-name={}, content-type={}, path={}, size={}]", args); 
		}
		
		// cache에 안 넣을 거다! 
		response.setHeader("Pargma", "no-cache");
		response.setHeader("Expires", "-1");
				
		OutputStream out = null;
		FileInputStream in = null;
		
		try{
			out = response.getOutputStream();
			in = new FileInputStream(file);

			IOUtils.copy(in, out);

		} catch(IOException e){
			e.printStackTrace(); //TODO: And so??
			throw e;
		} finally {
			IOUtils.closeQuietly(in);
		}

		out.flush();
	}
}
