package org.seamlets.resource.tools;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Blob;

public interface BlobTool extends Serializable {

	public Blob createBlob(InputStream in) throws IOException;
}
