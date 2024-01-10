package framework.struts.servlet;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.zip.GZIPOutputStream;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import framework.struts.util.MailSendUtil;
import framework.struts.util.BoardUtils;


/**
 * @web.servlet
 *   name="Download"
 *   display-name="File Download Servlet"
 *   
 *  @web.servlet-mapping
 *    url-pattern="/Download"
 *
 */
public class DownloadServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	//logging
	final Log log = LogFactory.getLog(this.getClass());
	/*
	 *  ------------------------------------------------------------------------
	 *				Operating System dependent Charset				
	 *  ------------------------------------------------------------------------
	 *  	OS name		OS Language		Charset		Java Charset	IANA Charset
	 *  	Windows XP	Korean		x-windows-949	MS949
	 *  	Windows XP	Japanese	windows-31j		SJIS			Shift_JIS
	 */  
	    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doService(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doService(request, response);
	}
	
	private void doService(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		//Charset of Server-side	
	    String MY_ENCODING="MS949";
	    //Charset of Client-side
	    String CLIENT_ENCODING="MS949";
		
		
		// get operating system dependent charset
		//Charset defaultCharset = Charset.defaultCharset();
		// client-side locale
		String clientLang = request.getLocale().getLanguage();
		// server-side locale
		String serverLang = Locale.getDefault().getLanguage();
		
		if(log.isDebugEnabled()){
			log.debug("request locale language="+clientLang);
		}
		// set Server-side Java charset
		//if(defaultCharset.name().equalsIgnoreCase("windows-31j")&& serverLang.equalsIgnoreCase("ja"))
		if( serverLang.equalsIgnoreCase("ja")){
			MY_ENCODING = "SJIS";
		}
		// set Client-side Java charset
		if(clientLang.equalsIgnoreCase("ja")){
			CLIENT_ENCODING = "SJIS";
		}
		
		
		if(log.isDebugEnabled()){
			log.debug("server charset="+MY_ENCODING+", client charset="+CLIENT_ENCODING);
		}
		// get file name of download filename
		String tempFileName = request.getParameter("tempFileName");
		// read file's content-type
		String contentType=request.getParameter("content-type");
				
		try {
			//String rootPath = request.getRealPath("/");
			String filePath = MailSendUtil.fileUploadDir+File.separator+tempFileName;
			File tempFile = new File(filePath);
			
			String fileName=request.getParameter("fileName");
			
			
		    fileName = BoardUtils.ascToutf(fileName);
		    if(log.isDebugEnabled()){
				log.debug("asii to utf filename="+fileName);
			}
		    	     
		     
		    //fileName = new String(fileName.getBytes(MY_ENCODING),CLIENT_ENCODING);
		    fileName = new String(fileName.getBytes(CLIENT_ENCODING), "8859_1");
		    // remove [1]'s from download file name.
		    fileName= BoardUtils.replace("\\.", "%2e", fileName);
		    String header = "attachment; filename=" + fileName + ";";
			
			if(log.isDebugEnabled()){
				log.debug("MS949 to ascii name="+header);
			}
			
			
		    if (!fileName.equals(new String(fileName.getBytes(CLIENT_ENCODING),
		    		CLIENT_ENCODING))) {
		    	
		    	//fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
		    	// request를 이미 UTF-8 encoding 하므로 그대로 사용
		    	//remove [1]'s from download file name.
		    	fileName= BoardUtils.replace("\\.", "%2e", fileName);
		    	header = "=?UTF-8?Q?attachment; filename="+  fileName + ";?=";
		    	
		    	if(log.isInfoEnabled()){
					log.info("(RFC 2047)MS949 long file name="+header);
				}
		    }
		    
			//String fileExt = fileName.substring(fileName.lastIndexOf(".")+1);
			
			String agentType=request.getHeader("Accept-Encoding");
			if(log.isInfoEnabled()){
				log.info("Accept-Encoding="+agentType);
			}
			
			if (!tempFile.exists()||!tempFile.canRead()) {
				PrintWriter out = response.getWriter();
				out.println("<script>alert('File Not Found');" +
						"history.back();</script>");
				return;					
			}
			
			
			boolean flag=false;
			if (agentType!=null && agentType.indexOf("gzip")>=0){
				flag= true;
			}
						
			if (flag) {
				response.setHeader("Content-Encoding", "gzip");
				//body header
				response.setHeader("Content-disposition", header);
				response.setContentType(contentType);
				ServletOutputStream sos = response.getOutputStream();
				GZIPOutputStream gzipOutputStream = new GZIPOutputStream(sos);
				
				dumpFile(tempFile, gzipOutputStream);
				gzipOutputStream.close();
				sos.close();
				
			}else{
				response.setContentType("application/octet-stream");
				response.setHeader("Content-disposition", header);
				ServletOutputStream sos1 = response.getOutputStream();
				
				dumpFile(tempFile, sos1);
				sos1.flush();
				sos1.close();
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			log.error("cannot download a file.", e);			
		}
		return;
	}
	
	private void dumpFile(File realFile, OutputStream os){
		byte readByte[]= new byte[4096];
		
		try {
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(realFile));
			
			int i=0;
			while ((i=bis.read(readByte, 0, 4096))!=-1) {
				os.write(readByte, 0, i);
				
			}
			bis.close();
		} catch (Exception e) {
			// TODO: handle exception
			
		}
	
	}

}
