package edu.spbsu.eshop.storage;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

public class ImageSender extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void processRequest(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {
	try {
	    String url = request.getRequestURL().toString();
	    String filename = url.substring(url.lastIndexOf('/') + 1);

	    response.setContentType("image/"
		    + FilenameUtils.getExtension(filename));
	    InputStream in = FileUtils.openInputStream(new File(
		    Storage.UPLOAD_DIR + Storage.IMAGES_SUBDIR
			    + File.pathSeparator + filename));
	    OutputStream out = response.getOutputStream();

	    /*
	     * @author Vladimir Vasilev Here is the sample version of resizing
	     * images in our servlet. May be it'll be better to upload only
	     * images that satisfy some requirements.... So this work will be
	     * senseless =)
	     */
	    BufferedImage img = null;
	    try {
		img = ImageIO.read(in);
		BufferedImage scaledImg = new BufferedImage(100, 100,
			BufferedImage.TYPE_INT_RGB);
		Graphics2D g = scaledImg.createGraphics();
		g.setComposite(AlphaComposite.Src);
		if (img.getWidth() >= 100 || img.getHeight() >= 100) {
		    g.drawImage(img, 0, 0, 100, 100, null);
		} else {
		    g.drawImage(img, 0, 0, null);
		}
		g.dispose();
		ImageIO.write(scaledImg, "JPEG", out);
	    } catch (IOException e) {
		throw e;
	    }
	    in.close();
	    out.close();
	} catch (Throwable e) {
	    throw new RuntimeException(e.getMessage());
	}

    }

    protected void doGet(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {
	processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {
	processRequest(request, response);
    }
}
