package se.openprocesslogger.proxy.graphviewer;

import java.io.Serializable;

public interface IGraphInfoCreator extends Serializable{

	public Batch getBatch(SingleData data, int index);

	SingleDataInfo getSingleDataInfo(SingleData data);

	public Serializable getAdditionalData(SingleData data);

}
