package framework.struts.processor;

/*
 * $Id: CommonsMultipartRequestHandler.java 54929 2004-10-16 16:38:42Z germuska $
 *
 * Copyright 1999-2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Hashtable;
//import java.util.InvalidPropertiesFormatException;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.net.URLDecoder;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.upload.FormFile;
import org.apache.struts.upload.MultipartRequestHandler;
import org.apache.struts.upload.MultipartRequestWrapper;

import be.telio.mediastore.ui.upload.MonitoredDiskFileItemFactory;
import be.telio.mediastore.ui.upload.UploadListener;

import framework.struts.util.MailSendUtil;
import framework.struts.form.MailForm;

 /**
  * This class implements the <code>MultipartRequestHandler</code> interface
  * by providing a wrapper around the Jakarta Commons FileUpload library.
  *
  * @version $Rev: 54929 $ $Date: 2004-10-16 17:38:42 +0100 (Sat, 16 Oct 2004) $
  * @since Struts 1.1
  */
public class CustomMultipartRequestHandler implements MultipartRequestHandler {


    // ----------------------------------------------------- Manifest Constants


    /**
     * The default value for the maximum allowable size, in bytes, of an
     * uploaded file. The default value is equivalent to 250MB.
     */
    public static long DEFAULT_SIZE_MAX;


    /**
     * The default value for the threshold which determines whether an uploaded
     * file will be written to disk or cached in memory. The default value is equivalent
     * to 256KB.
     */
    public static int DEFAULT_SIZE_THRESHOLD;

    // temp dir to upload
    public static String TEMP_UPLOAD_DIR;
    
    // external configuration file 
    private static final String CONFIG_XML="config.properties";
       
    // ----------------------------------------------------- Instance Variables


    /**
     * Commons Logging instance.
     */
    protected final Log log = LogFactory.getLog(this.getClass());


    /**
     * The combined text and file request parameters.
     */
    private Hashtable elementsAll;


    /**
     * The file request parameters.
     */
    private Hashtable elementsFile;


    /**
     * The text request parameters.
     */
    private Hashtable elementsText;


    /**
     * The action mapping  with which this handler is associated.
     */
    private ActionMapping mapping;


    /**
     * The servlet with which this handler is associated.
     */
    private ActionServlet servlet;


    // ---------------------------------------- MultipartRequestHandler Methods


    /**
     * Retrieves the servlet with which this handler is associated.
     *
     * @return The associated servlet.
     */
    public ActionServlet getServlet() {
        return this.servlet;
    }


    /**
     * Sets the servlet with which this handler is associated.
     *
     * @param servlet The associated servlet.
     */
    public void setServlet(ActionServlet servlet) {
        this.servlet = servlet;
    }


    /**
     * Retrieves the action mapping with which this handler is associated.
     *
     * @return The associated action mapping.
     */
    public ActionMapping getMapping() {
        return this.mapping;
    }


    /**
     * Sets the action mapping with which this handler is associated.
     *
     * @param mapping The associated action mapping.
     */
    public void setMapping(ActionMapping mapping) {
        this.mapping = mapping;
    }

    /**
     * Parses the input stream and partitions the parsed items into a set of
     * form fields and a set of file items. In the process, the parsed items
     * are translated from Commons FileUpload <code>FileItem</code> instances
     * to Struts <code>FormFile</code> instances.
     *
     * @param request The multipart request to be processed.
     *
     * @throws ServletException if an unrecoverable error occurs.
     */
    public void handleRequest(HttpServletRequest request)
            throws ServletException {
    	
    	// Initialize DEFAULT_SIZE_MAX, DEFAULT_SIZE_THRESHOLD, TEMP_UPLOAD_DIR
    	try {
    		log.debug("Loding upload setting from properties.xml");
    		loadConfigXML(request);
    		log.debug("DefaultSizeMax="+DEFAULT_SIZE_MAX+", SizeThreshold="+DEFAULT_SIZE_THRESHOLD);
		} catch (Exception e) {
			// TODO: handle exception
			throw new ServletException(e.getMessage());
			
		}
		    	
        // Get the app config for the current request.
        ModuleConfig ac = (ModuleConfig) request.getAttribute(
                Globals.MODULE_KEY);
        getRepositoryPath(ac);
        
        log.debug("MaxUploadSize ="+getSizeMax(ac)+", SizeThreshold="+getSizeThreshold(ac));
        log.debug("getMaxFileSize()="+ac.getControllerConfig().getMaxFileSize()+", getMemFileSize()="+ac.getControllerConfig().getMemFileSize());
        //----------------------------------------------------------
        //  Changed this section of code, that is it.
        //-----------------------------------------------------------
        log.debug("Handling the request..");
        // set delay time and get content-legnth of files
        UploadListener listener = new UploadListener(request, 1);
        // set listener to ServletContext
        ServletContext context = servlet.getServletContext();
        context.setAttribute("UploadListener", listener);
        
        log.debug("uploading file with optional monitoring..");
        // Create a factory for disk-based file items
        FileItemFactory factory = new MonitoredDiskFileItemFactory(listener);
        log.debug("got the factory now get the ServletFileUpload");
        ServletFileUpload upload = new ServletFileUpload(factory);
        log.debug("Should have the ServletFileUpload by now.");
        // The following line is to support an "EncodingFilter"
        // set request encoding to UTF-8
        upload.setHeaderEncoding(request.getCharacterEncoding());
        /* Set the maximum allowed size of a complete request before a FileUploadException will be thrown.
         * Change this code to set the max size with DEFAULT_SIZE_MAX : long
         * setFileSizeMax(long fileSizeMax) : Sets the maximum allowed size of a single uploaded file
         * 
         */
        upload.setSizeMax(DEFAULT_SIZE_MAX);
       //----------------------------------------------------------------
        // Create the hash tables to be populated.
        elementsText = new Hashtable();
        elementsFile = new Hashtable();
        elementsAll = new Hashtable();

        // Parse the request into file items.
        List items = null;
        
        try {
            items = upload.parseRequest(request);
            log.debug("inside parsing task : "+items.size());
        } catch (SizeLimitExceededException e) {
            // Special handling for uploads that are too big.
            request.setAttribute(
                    MultipartRequestHandler.ATTRIBUTE_MAX_LENGTH_EXCEEDED,
                    Boolean.TRUE);
            // alert error message to UploadListener
            listener.error("exceeded max upload length. Max upload size is "+convertSizeToHuman(DEFAULT_SIZE_MAX));
            // 
            log.error("exceeded max upload length", e);
                   
        	return;
        } catch (FileUploadException e) {
            log.error("Failed to parse multipart request", e);
            throw new ServletException(e);
            
        }
        //check the number of upload files from MailForm and FileItem
        HttpSession session = request.getSession(false);
        MailForm mailForm = (MailForm)session.getAttribute("attached.file.message");
        int fileNo = 0;
        if(mailForm!=null){
        	fileNo = mailForm.getFileNo();
        }
        
        // Partition the items into form fields and files.
        Iterator iter = items.iterator();
        int formFile=0;
        while (iter.hasNext()) {
            FileItem item = (FileItem) iter.next();

            if (item.isFormField()) {
            	log.debug("inside adding text parameter : "+item.getFieldName());
                addTextParameter(request, item);
            } else {
            	log.debug("inside adding file parameter : "+item.getName());
            	
            	if(item.getName()!="")
            		formFile++;
                addFileParameter(item);
            }//end if~else
        }//end while
        
        fileNo += formFile;
        if(log.isDebugEnabled()){
			log.debug("file number="+fileNo);
		}
        // save fileNo in session
        if(mailForm!=null){
        	mailForm.setFileNo(fileNo);
        	session.setAttribute("attached.file.message", mailForm);
        }
        
    }


    /**
     * Returns a hash table containing the text (that is, non-file) request
     * parameters.
     *
     * @return The text request parameters.
     */
    public Hashtable getTextElements() {
        return this.elementsText;
    }


    /**
     * Returns a hash table containing the file (that is, non-text) request
     * parameters.
     *
     * @return The file request parameters.
     */
    public Hashtable getFileElements() {
        return this.elementsFile;
    }


    /**
     * Returns a hash table containing both text and file request parameters.
     *
     * @return The text and file request parameters.
     */
    public Hashtable getAllElements() {
        return this.elementsAll;
    }


    /**
     * Cleans up when a problem occurs during request processing.
     */
    public void rollback() {
        Iterator iter = elementsFile.values().iterator();

        while (iter.hasNext()) {
            FormFile formFile = (FormFile) iter.next();

            formFile.destroy();
        }
    }


    /**
     * Cleans up at the end of a request.
     */
    public void finish() {
        rollback();
    }


    // -------------------------------------------------------- Support Methods


    /**
     * Returns the maximum allowable size, in bytes, of an uploaded file. The
     * value is obtained from the current module's controller configuration.
     * Notice = controller default value = 250M
     * 
     * @param mc The current module's configuration.
     *
     * @return The maximum allowable file size, in bytes.
     */
    protected long getSizeMax(ModuleConfig mc) {
        return convertSizeToBytes(
                mc.getControllerConfig().getMaxFileSize(),
                DEFAULT_SIZE_MAX);
    }


    /**
     * Returns the size threshold which determines whether an uploaded file
     * will be written to disk or cached in memory.
     *
     * @param mc The current module's configuration.
     *
     * @return The size threshold, in bytes.
     */
    protected long getSizeThreshold(ModuleConfig mc) {
        return convertSizeToBytes(
                mc.getControllerConfig().getMemFileSize(),
                DEFAULT_SIZE_THRESHOLD);
    }

    /**
     * Converts a size value from a string representation to its numeric value.
     * The string must be of the form nnnm, where nnn is an arbitrary decimal
     * value, and m is a multiplier. The multiplier must be one of 'K', 'M' and
     * 'G', representing kilobytes, megabytes and gigabytes respectively.
     *
     * If the size value cannot be converted, for example due to invalid syntax,
     * the supplied default is returned instead.
     *
     * @param sizeString  The string representation of the size to be converted.
     * @param defaultSize The value to be returned if the string is invalid.
     *
     * @return The actual size in bytes.
     * 
     * Modification : return defaultSize if exists, or return the default value from ControllerConfig.
     */
    protected long convertSizeToBytes(String sizeString, long defaultSize) {
        int multiplier = 1;
        long size = 0;
        
        if (sizeString.endsWith("K")) {
        	multiplier = 1024;
        } else if (sizeString.endsWith("M")) {
        	multiplier = 1024 * 1024;
        } else if (sizeString.endsWith("G")) {
        	multiplier = 1024 * 1024 * 1024;
        }
        if (multiplier != 1) {
        	sizeString = sizeString.substring(0, sizeString.length() - 1);
        }

        	
        try {
       		size = Long.parseLong(sizeString);
       	} catch (NumberFormatException nfe) {
        	log.warn("Invalid format for file size ('" + sizeString +
                   "'). Using default.");
        	size = defaultSize;
        	multiplier = 1;
       	}
        	
        
       	return (size * multiplier);
      }
    
    /* Added by kof4ever 
     * add a suffix to file size
	 * @param bytesize - file size 
	 * @return humanable file size ( K: M: G)
	 */	
	
	protected String convertSizeToHuman(long bytesize){
		String result="";
		
		if(bytesize>=1024 && bytesize< 1024*1024){
			result = Math.round(bytesize/1024)+"K";
		}else if(bytesize>=1024*1024 && bytesize < 1024*1024*1024){
			result = Math.round(bytesize/(1024*1024))+"M";
		
		}else if(bytesize>=1024*1024*1024 ){
			result = Math.round(bytesize/(1024*1024*1024))+"G";
		}else{
			result = bytesize + "byte";
		
		}
		
		return result;
	}

    /**
     * Returns the path to the temporary directory to be used for uploaded
     * files which are written to disk. The directory used is determined from
     * the first of the following to be non-empty.
     * <ol>
     * <li>A temp dir explicitly defined either using the <code>tempDir</code>
     *     servlet init param, or the <code>tempDir</code> attribute of the
     *     &lt;controller&gt; element in the Struts config file.</li>
     * <li>The container-specified temp dir, obtained from the
     *     <code>javax.servlet.context.tempdir</code> servlet context
     *     attribute.</li>
     * <li>The temp dir specified by the <code>java.io.tmpdir</code> system
     *     property.</li>
     * (/ol>
     *
     * @param mc The module config instance for which the path should be
     *           determined.
     *
     * @return The path to the directory to be used to store uploaded files.
     */
    protected String getRepositoryPath(ModuleConfig mc) {

        // First, look for an explicitly defined temp dir.
        String tempDir = mc.getControllerConfig().getTempDir();
                
        /*
         * if none, return a default temp directory from system properties.
         */
        if (tempDir == null || tempDir.length() == 0) {
        	
        	//	System File upload temp dir: C:\Tomcat 5.5\temp
        	tempDir = System.getProperty("java.io.tmpdir");
        	
        	
        	 // If none, look for a container specified temp dir.
            if (tempDir ==null || tempDir.length()==0) {
            	if (servlet != null) {
                    ServletContext context = servlet.getServletContext();
                    File tempDirFile = (File) context.getAttribute(
                            "javax.servlet.context.tempdir");
                    tempDir = tempDirFile.getAbsolutePath();
                    //Servlet File upload temp dir: C:\Tomcat 5.5\work\Catalina\localhost\bigmail
                }
           }
          
        }//end if
        if (log.isDebugEnabled()) {
            log.debug("File upload temp dir: " + tempDir);
        }
        return tempDir;
    }


    /**
     * Adds a regular text parameter to the set of text parameters for this
     * request and also to the list of all parameters. Handles the case of
     * multiple values for the same parameter by using an array for the
     * parameter value.
     *
     * @param request The request in which the parameter was specified.
     * @param item    The file item for the parameter to add.
     */
    protected void addTextParameter(HttpServletRequest request, FileItem item) {
        String name = item.getFieldName();
        String orgFileNames = request.getParameter("orgFileName");
        log.debug("orgFileName="+orgFileNames);
        try {
        	if(orgFileNames!=null){
        		log.debug("decoded FileName="+URLDecoder.decode(request.getParameter("orgFileName"), "UTF-8"));
        	}
        }catch(UnsupportedEncodingException e){
        	log.error("unsupported Encoding",e);
        }
        String value = null;
        boolean haveValue = false;
        String encoding = request.getCharacterEncoding();
        log.debug("item.getFieldName(): name="+name+", request character encoding="+encoding);
        if (encoding != null) {
            try {
                value = item.getString(encoding);
                haveValue = true;
            } catch (Exception e) {
                // Handled below, since haveValue is false.
            }
        }
        // set encoding with 'ISO-8859-1' if haveValue is false.
        if (!haveValue) {
            try {
                 value = item.getString("ISO-8859-1");
            } catch (java.io.UnsupportedEncodingException uee) {
                 value = item.getString();
            }
            haveValue = true;
        }

        if (request instanceof MultipartRequestWrapper) {
            MultipartRequestWrapper wrapper = (MultipartRequestWrapper) request;
            wrapper.setParameter(name, value);
        }

        String[] oldArray = (String[]) elementsText.get(name);
        String[] newArray;

        if (oldArray != null) {
            newArray = new String[oldArray.length + 1];
            System.arraycopy(oldArray, 0, newArray, 0, oldArray.length);
            newArray[oldArray.length] = value;
        } else {
            newArray = new String[] { value };
        }
        log.debug("addTextParameter: name="+name+", value="+value);
        elementsText.put(name, newArray);
        elementsAll.put(name, newArray);
    }


    /**
     * Adds a file parameter to the set of file parameters for this request
     * and also to the list of all parameters.
     *
     * @param item    The file item for the parameter to add.
     */
    protected void addFileParameter(FileItem item) {
        FormFile formFile = new CommonsFormFile(item);
        
        // Insert codes here for creating temp file with a name what you want. 
        elementsFile.put(item.getFieldName(), formFile);
        elementsAll.put(item.getFieldName(), formFile);
    }

    /**
     * load uploading settings from properties.xml
     * @param MultipartRequest.
     * @return null.
     */
    private void loadConfigXML(HttpServletRequest request) throws FileNotFoundException, IOException, NumberFormatException{
    	
    		Properties props = MailSendUtil.loadConfigXML(request, CONFIG_XML);
        	
        	DEFAULT_SIZE_MAX = Long.parseLong(props.getProperty("maxFileSize"));
        	
        	DEFAULT_SIZE_THRESHOLD = Integer.parseInt(props.getProperty("defaultSizeThreshold"));
        	
        	TEMP_UPLOAD_DIR = props.getProperty("tempFileDir");
        	/*ServletContext context = servlet.getServletContext();
            context.setAttribute("javax.servlet.context.tempdir",new File(TEMP_UPLOAD_DIR));*/
        	System.setProperty("java.io.tmpdir",TEMP_UPLOAD_DIR);
    }
    
    // ---------------------------------------------------------- Inner Classes
   
    /**
     * This class implements the Struts <code>FormFile</code> interface by
     * wrapping the Commons FileUpload <code>FileItem</code> interface. This
     * implementation is <i>read-only</i>; any attempt to modify an instance
     * of this class will result in an <code>UnsupportedOperationException</code>.
     */
    static class CommonsFormFile implements FormFile, Serializable {

        /**
         * The <code>FileItem</code> instance wrapped by this object.
         */
        FileItem fileItem;


        /**
         * Constructs an instance of this class which wraps the supplied
         * file item.
         *
         * @param fileItem The Commons file item to be wrapped.
         */
        public CommonsFormFile(FileItem fileItem) {
            this.fileItem = fileItem;
        }


        /**
         * Returns the content type for this file.
         *
         * @return A String representing content type.
         */
        public String getContentType() {
            return fileItem.getContentType();
        }


        /**
         * Sets the content type for this file.
         * <p>
         * NOTE: This method is not supported in this implementation.
         *
         * @param contentType A string representing the content type.
         */
        public void setContentType(String contentType) {
            throw new UnsupportedOperationException(
                    "The setContentType() method is not supported.");
        }


        /**
         * Returns the size, in bytes, of this file.
         *
         * @return The size of the file, in bytes.
         */
        public int getFileSize() {
            return (int)fileItem.getSize();
        }


        /**
         * Sets the size, in bytes, for this file.
         * <p>
         * NOTE: This method is not supported in this implementation.
         *
         * @param filesize The size of the file, in bytes.
         */
        public void setFileSize(int filesize) {
            throw new UnsupportedOperationException(
                    "The setFileSize() method is not supported.");
        }


        /**
         * Returns the (client-side) file name for this file.
         *	modified by kof4ever : filename --> full path name
         * @return The client-side file name(full-path).
         */
        public String getFileName() {
            return fileItem.getName();
        }
        
       /**
         * The class does not implement this method. 
         * Notice : this method cannot set the name of temp file.          
         * <p>
         *         
         * @param fileName : client-side original filename
         */
        public void setFileName(String fileName) {
            throw new UnsupportedOperationException("does not implement setFileName() method");
        }


        /**
         * Returns the data for this file as a byte array. Note that this may
         * result in excessive memory usage for large uploads. The use of the
         * {@link #getInputStream() getInputStream} method is encouraged
         * as an alternative.
         *
         * @return An array of bytes representing the data contained in this
         *         form file.
         *
         * @exception FileNotFoundException If some sort of file representation
         *                                  cannot be found for the FormFile
         * @exception IOException If there is some sort of IOException
         */
        public byte[] getFileData() throws FileNotFoundException, IOException {
            return fileItem.get();
        }


        /**
         * Get an InputStream that represents this file.  This is the preferred
         * method of getting file data.
         * @exception FileNotFoundException If some sort of file representation
         *                                  cannot be found for the FormFile
         * @exception IOException If there is some sort of IOException
         */
        public InputStream getInputStream() throws FileNotFoundException, IOException {
            return fileItem.getInputStream();
        }


        /**
         * Destroy all content for this form file.
         * Implementations should remove any temporary
         * files or any temporary file data stored somewhere
         */
        public void destroy() {
            fileItem.delete();
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
        protected String getBaseFileName(String filePath) {

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
         * Returns the (client-side) file name for this file.
         *
         * @return The client-size file name.
         */
        public String toString() {
            return getFileName();
        }
        
    }
}