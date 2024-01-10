<%@ page import="org.apache.commons.fileupload.*, org.apache.commons.fileupload.servlet.ServletFileUpload, org.apache.commons.fileupload.disk.DiskFileItemFactory, org.apache.commons.io.FilenameUtils, java.util.*, java.io.File, java.lang.Exception" %>
<%
System.out.println("Entrando en archivo_1.jsp");
if (ServletFileUpload.isMultipartContent(request)){

   ServletFileUpload servletFileUpload = new ServletFileUpload(new DiskFileItemFactory());
   List fileItemsList = servletFileUpload.parseRequest(request);
   String optionalFileName = "";
   FileItem fileItem = null;

   Iterator it = fileItemsList.iterator();
   while (it.hasNext()){
     FileItem fileItemTemp = (FileItem)it.next();
     if (fileItemTemp.isFormField()){
       if (fileItemTemp.getFieldName().equals("Fileconten"))
          optionalFileName = fileItemTemp.getString();
     }
     else
       fileItem = fileItemTemp;
     }


   String fileName = fileItem.getName();

   /* Save the uploaded file if its size is greater than 0. */
   if (fileItem.getSize() > 0){
    if (optionalFileName.trim().equals(""))
      fileName = FilenameUtils.getName(fileName);
    else
      fileName = optionalFileName;

    String dirName = "/tmp/";

    String _info = "<b>Uploaded File Info:</b><br/>"+
                   "Content type: "+fileItem.getContentType() +"<br/>"+
                   "Field name: "+fileItem.getFieldName()+"<br/>"+
                   "File name: "+fileName+"<br/>"+
                   "File size: "+ fileItem.getSize()+ "<br/><br/>";

    File saveTo = new File(dirName + fileName);
    try {
      fileItem.write(saveTo);
      out.println("{success:true,info:{reason:'"+_info+"'}}");
    }
     catch (Exception e){
      out.println("{success:false,errors:{reason:'"+e.getMessage()+"'}}");
     }
   }
 }
System.out.println("Saliendo de archivo_1.jsp");
%>
