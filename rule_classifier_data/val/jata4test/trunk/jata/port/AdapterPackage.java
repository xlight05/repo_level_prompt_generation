package jata.port;

import java.io.Serializable;

public class AdapterPackage implements Serializable {

	private static final long serialVersionUID = 1L;
	public String streamType;
    public String MessageType;
    public Object stream;
    
    public String portName;
    public AdapterPackage (Object stream){
		this.stream = stream;
		streamType = stream.getClass().getName();
	}
    public AdapterPackage(){
    	
    }
}
