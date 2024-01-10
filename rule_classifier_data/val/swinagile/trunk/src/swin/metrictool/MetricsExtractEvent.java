package swin.metrictool;

import java.util.EventObject;

/**
 * @author Asiri
 * @version
 */
public class MetricsExtractEvent extends EventObject
{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param source
	 */
	public MetricsExtractEvent(ExtractedClassData source)
	{
		super(source);
	}

}
