package org.seamlets.resource.tools;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;

import org.hibernate.Hibernate;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Install;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

@Name("org.seamlets.blobTool")
@AutoCreate
@Scope(ScopeType.STATELESS)
@Install(precedence = Install.BUILT_IN, classDependencies = "org.hibernate.Hibernate")
public class HibernateBlobTool implements BlobTool {

	@Override
	public Blob createBlob(InputStream in) throws IOException {
		return Hibernate.createBlob(in);
	}

}
