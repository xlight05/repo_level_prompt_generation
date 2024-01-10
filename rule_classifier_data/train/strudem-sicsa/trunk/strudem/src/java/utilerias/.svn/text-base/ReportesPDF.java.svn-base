/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilerias;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import com.opensymphony.xwork2.ActionSupport;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import javax.servlet.ServletContext;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author Mauricio
 */
public class ReportesPDF extends ActionSupport {

    @Override
    public String execute() throws Exception {
        Document doc = new Document();
        ServletContext servletcontext = ServletActionContext.getServletContext();
        String path = servletcontext.getRealPath("/");
        String filepath = path + "images" + "/ejemploConImagen.pdf";
        String imagePath = path + "images" + "/login_side.jpg";
        System.out.println(path + "images" + "/login_side.jpg");
        PdfWriter.getInstance(doc, new FileOutputStream(filepath));
        Image image = Image.getInstance(imagePath);
        doc.open();
        //**Aquí se agrega todos los obtejos que se van a incrustar a la pagina del documento**
        //doc.add(new Paragraph(string));
        doc.add(image);
        doc.close();
        
        addActionMessage("Se ha creado correctamente el archivo pdf de pase de lista vacio en: " + servletcontext.getContextPath() + "/images/ejemploConImagen.pdf" );
        return SUCCESS;
    }
    
     private static String leerPagina(URL url) throws Exception {

        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(url.toURI());
        HttpResponse response = client.execute(request);

        Reader reader = null;
        try {
            reader = new InputStreamReader(response.getEntity().getContent());

            StringBuilder sb = new StringBuilder();
            {
                int read;
                char[] cbuf = new char[1024];
                while ((read = reader.read(cbuf)) != -1)
                    sb.append(cbuf, 0, read);
            }

            return sb.toString();

        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                }
            }
        }
    }

  //  @Override
   // public Object getModel() {
        //obMessage = new MessageInPDF();

// TODO Auto-generated method stub

     //   return obMessage;
    //}
}
