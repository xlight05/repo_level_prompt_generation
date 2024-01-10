package edu.spbsu.eshop;

import java.io.File;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import edu.spbsu.eshop.hibernate.HibernateUtil;
import edu.spbsu.eshop.storage.Storage;

public class EShopContextListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent event) {
	// init dir structure

	new File(Storage.UPLOAD_DIR).mkdirs();
	new File(Storage.UPLOAD_DIR + Storage.IMAGES_SUBDIR).mkdir();

	// Just call the static initializer of hibernate
	HibernateUtil.getEntityManager().close();
    }

    public void contextDestroyed(ServletContextEvent event) {
	HibernateUtil.close();
    }
}
