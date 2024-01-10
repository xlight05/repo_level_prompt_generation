package br.com.dyad.infrastructure.file.upload;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.hibernate.Session;
import org.hibernate.tool.hbm2x.StringUtils;

import br.com.dyad.client.GenericService;
import br.com.dyad.client.system.SystemInfo;
import br.com.dyad.infrastructure.entity.DatabaseFile;
import br.com.dyad.infrastructure.persistence.PersistenceUtil;

public class UploadServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {		
		
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Create a factory for disk-based file items
		DiskFileItemFactory factory = new DiskFileItemFactory();
		
		String tempDir = SystemInfo.getInstance().getTempDir();
		//factory.setRepository(new File(tempDir));
		
		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);
		
		String database = (String)request.getSession().getAttribute(GenericService.GET_DATABASE_FILE);

		// Parse the request
		try {
			List /* FileItem */ items = upload.parseRequest(request);
			
			for (Object object : items) {
				FileItem file = (FileItem )object;
				if (!file.isFormField() && file.getFieldName().equalsIgnoreCase("file")) {					
					/*Long key = KeyGenerator.getInstance(database).generateKey(null);
					new File(tempDir + File.separator + database).mkdir();
					file.write(new File(tempDir + File.separator + database + File.separator + key.toString()));					
					response.getWriter().write(key.toString());*/
					
					Session session = PersistenceUtil.getSession(database);
					session.beginTransaction();
					try{
						DatabaseFile dbFile = new DatabaseFile();
						
						dbFile.createId(database);
						String[] parts = StringUtils.split(file.getName(), File.separator);
						dbFile.setFileName(parts[parts.length - 1]);
						dbFile.setData(file.get());
						session.save(dbFile);

						session.getTransaction().commit();
						response.getWriter().write(dbFile.getId().toString());
					} catch(Exception e) {
						session.getTransaction().rollback();
					}
				}
			}
		} catch (Exception e) {			
			throw new RuntimeException(e);
		}
	}

}
