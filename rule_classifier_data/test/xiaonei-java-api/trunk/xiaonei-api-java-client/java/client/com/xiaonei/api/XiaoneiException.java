package com.xiaonei.api;


public class XiaoneiException
    extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = -1170936813153338795L;
	private int _code;


    public XiaoneiException(int code, String msg) {
        super(msg);
        _code = code;
    }


    public int getCode() {
        return _code;
    }
}
