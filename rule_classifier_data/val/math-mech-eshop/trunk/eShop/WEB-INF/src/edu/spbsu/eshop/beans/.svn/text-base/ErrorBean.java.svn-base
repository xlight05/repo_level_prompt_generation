package edu.spbsu.eshop.beans;

import javax.faces.context.FacesContext;

public class ErrorBean {
    private String message = "";

    public static ErrorBean getCurrentInstance() {
	FacesContext fctx = FacesContext.getCurrentInstance();
	return (ErrorBean) fctx.getApplication().evaluateExpressionGet(fctx,
		"#{error}", ErrorBean.class);

    }

    public static void setErrorMessage(String message) {
	getCurrentInstance().setMessage(message);
    }

    public void setMessage(String message) {
	this.message = message;

    }

    public String getMessage() {
	return message;
    }
}
