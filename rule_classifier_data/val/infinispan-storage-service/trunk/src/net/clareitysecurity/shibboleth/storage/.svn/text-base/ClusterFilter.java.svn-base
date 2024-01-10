/*
 * Copyright 2012 Clareity Security, LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.clareitysecurity.shibboleth.storage;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.opensaml.util.storage.StorageService;

import edu.internet2.middleware.shibboleth.idp.session.Session;
import edu.internet2.middleware.shibboleth.idp.session.impl.SessionManagerEntry;
import edu.internet2.middleware.shibboleth.idp.util.HttpServletHelper;

/**
 * @author Paul Hethmon
 *
 */
public class ClusterFilter implements Filter {


	/** Class logger. */
	private final Logger log = LoggerFactory.getLogger(ClusterFilter.class);
	private static String version = "Version 1.0.3 (2012-07-03)";
    private static ServletContext context;
    private static InfinispanStorageService<String, Object> storageService;

	@SuppressWarnings("unchecked")
	public void init(FilterConfig filterConfig) throws ServletException {
		
        String endofline = System.getProperty("line.separator");
        log.info(endofline + "=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=" + endofline + endofline);
		log.info(version);
		log.info(endofline);
		
		// get a pointer to the storage service
		context = filterConfig.getServletContext();
        StorageService<String, Object> ss = (StorageService<String, Object>) HttpServletHelper.getStorageService(context);
        
        // we only support Infinispan
        if (!(ss instanceof InfinispanStorageService<?, ?>)) {
        	log.error("StorageService is of type [{}]. Only InfinispanStorageService is supported.", ss.getClass().toString());
        	throw new ServletException("This filter only supports InfinispanStorageService");
        }
        // keep our reference to the storage service
        storageService = (InfinispanStorageService<String, Object>) ss;
	}

	/**
	 * Since we don't have a shutdown hook in the storage service, we hack the shutdown of the Infinispan
	 * based storage service to the filter shutting down. If we don't, the process will not stop because of
	 * open network resources.
	 */
	public void destroy() {
		//add code to release any resource
		log.info("Closing storage service infinispan engine.");
		storageService.cleanup();
	}

	/**
	 * If we find a session in the request, touch the underlying record in the storage
	 * service so that it will replicate to all nodes.
	 */
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException, RuntimeException {

		if (!(req instanceof HttpServletRequest)) {
			throw new RuntimeException("Not supported operation...");
		}
		
		// process any other filters first
		chain.doFilter(req, res);

		// cast to something useful for us
		HttpServletRequest request = ((HttpServletRequest) req);
		
		try {
			// look for the idp session
	        Session idpSession = HttpServletHelper.getUserSession(request);
	
	        // if found, touch the record again
			if (idpSession != null) {
				log.debug("idp session = [{}]  principal = [{}]", idpSession.getSessionID(), idpSession.getPrincipalName());
	
				// pull the entry out of storage and put it back in
	        	SessionManagerEntry sessionEntry = (SessionManagerEntry) storageService.get("session", idpSession.getSessionID());
	        	if (sessionEntry != null) {
	        		storageService.put("session", sessionEntry.getSessionId(), sessionEntry);
	        	}
			}
		} catch (Exception e) {
			log.error("Exception while updating cluster storage service.");
			log.error("Stack trace:", e);
		}
	}

}
