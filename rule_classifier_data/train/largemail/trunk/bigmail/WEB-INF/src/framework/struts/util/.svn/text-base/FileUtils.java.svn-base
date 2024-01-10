package framework.struts.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileReader;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.upload.FormFile;


public class FileUtils {
	//	logging
	final static Log log = LogFactory.getLog(FileUtils.class);
	//或るフォルダーの一番最近のファイル名をリターン�ӽ���� ������ ���� �
	public static String lastModified(String path) {
		// 
		String lastFile = null;
		File[] list = null;
		File files = new File(path);
		
		list = files.listFiles();
				
		long lastModified =0;
		// 比べられる更新時間(millisecondsでリターン)
		long tmpModified=0;
		for (int i = 0; i < list.length; i++) {
			File file = list[i];
			String fileName = file.getName();
			
			if(fileName.indexOf("tmp")!=-1){
				
				tmpModified = file.lastModified();
				
				if(tmpModified>lastModified){
					lastFile = fileName;
					lastModified = tmpModified;
				}else{
					// do nothing
				}
			}
			
		}//end for
				
		return lastFile;
	}
	
	// InputStreamから temp fileを読めるかどうかチィックする
	public static boolean canReadTempFile(FormFile file) throws IOException{
		boolean canRead=false;
		
		InputStream is = file.getInputStream();
				
		int bytesRead=0;
		byte[] buffer = new byte[8192];
		if((bytesRead = is.read(buffer,0,8192))!=-1){
			canRead = true;
		}
		return canRead;
		
	}
	/* method for removing a file 
	 * @param fileName: upload file name (FileInfo.getTempFileName())
	 * @return isDeleted : result of deleting task
	 */	
	public static boolean delete(String fileName) throws FileNotFoundException{
		boolean isDeleted=false;
		
		File file = new File(fileName);
		
		if(file.isFile()){
			isDeleted = file.delete();
		}else{
			throw new FileNotFoundException("File has not been founded.");
		}
		
		return isDeleted;
	}
	/*
	 *  method for removing files in a directoryϾ�
	 *  @param path: temp file directory
	 *  @return result : number of temp files deleted
	 */
	public static int tempFiles=0;
	public static int deleteFiles(File infile) {
		
		boolean isDeleted=false;
						
		if  ( infile.isDirectory (  )  )   {  
            String [  ]  files = infile.list (  ) ; 
             
            for  ( int i = 0; i  <  files.length; i++ )   {  
                deleteFiles ( new File ( infile, files [ i ]  )  ) ; 
             }  
             
         }else{  
        	 isDeleted = infile.delete();
        	 if(log.isDebugEnabled()){
     			log.debug("Deleting "+infile.getName());
     		}
        	 tempFiles++;
         }
		return tempFiles;
	}

	// ファイル削除の記録を残すファイル
	// 生成位置：アップロードディレクトリーの下
	//　ファイル名：日付.txt
	// ファイル内容：[削除時間] ファイルプルパス
	public static OutputStreamWriter createClearLogFile(File filepath) throws IOException {
		OutputStreamWriter osw = null;
				
		if(filepath.length()==0){
			filepath.createNewFile();
		}
		// append = true
		OutputStream os = new FileOutputStream(filepath,true);
		osw = new OutputStreamWriter(os,"UTF-8");	
		
		return osw;
	}
	
	/**
	 * 削除ログファイルの内容をストリングで読み取るメソッド
	 * 
	 * @author kof4ever
	 * @param  File filepath (include filename)
	 * @return File contents in string
	 */
	public static String readFile(File filepath) throws FileNotFoundException, IOException{
		String result="";
		int b=0;
		if(filepath.isFile()){
			if(filepath.canRead()){
				Reader reader = new FileReader(filepath);
				BufferedReader br = new BufferedReader(reader);
				
				while((b=br.read())!=-1){
					result +=(char)b; 
				}
			}
		}else{
			throw new FileNotFoundException("cannot read from a directory");
		}
		
		return result;
	}
	
	/**
     * Returns the base file name from the supplied file path. On the surface,
     * this would appear to be a trivial task. Apparently, however, some Linux
     * JDKs do not implement <code>File.getName()</code> correctly for Windows
     * paths, so we attempt to take care of that here.
     *
     * @param filePath The full path to the file.
     *
     * @return The base file name, from the end of the path.
     */
    public static String getBaseFileName(String filePath) {

        // First, ask the JDK for the base file name.
        String fileName = new File(filePath).getName();

        // Now check for a Windows file name parsed incorrectly.
        int colonIndex = fileName.indexOf(":");
        if (colonIndex == -1) {
            // Check for a Windows SMB file path.
            colonIndex = fileName.indexOf("\\\\");
        }
        int backslashIndex = fileName.lastIndexOf("\\");

        if (colonIndex > -1 && backslashIndex > -1) {
            // Consider this filename to be a full Windows path, and parse it
            // accordingly to retrieve just the base file name.
            fileName = fileName.substring(backslashIndex + 1);
        }

        return fileName;
    }
    
    /**
     * Returns the total size of files from the supplied file path. 
     * If the size is over indicated limit from 'properties.xml',
     * aumatically a mail is to be posted to an administrator.
     *
     * @param directory Path. 
     *
     * @return the size of files existed in a given path.
     */
    public static long getSizeOfDir(File filepath){
    	long totalSize=0;
    		
    	if(filepath.isDirectory()){
    		String files[] = filepath.list();
    		for(int i=0;i<files.length;i++){
    			File infile = new File(filepath, files[i]);
    			totalSize += infile.length();
    		}
    		if(log.isDebugEnabled()){
    			log.debug("Inside Check a size of upload dir: file="+files.length+", totalSize="+totalSize);
    		}
    	}else{
    		totalSize = filepath.length();
    	}
    	
    	return totalSize;
    }
	
}
