package edu.spbsu.eshop.admin.pages;

import java.util.HashMap;
import java.util.Map;

import com.ocpsoft.pretty.PrettyContext;

public abstract class AbstractOnPageRequestAction implements
	OnPageRequestAction {
    private Map<String, String> params;

    @Override
    public void pageRequested() {
	String idParam = getParam("id");
	try {
	    long id = Long.parseLong(idParam);
	    setObject(getObjectById(id));
	} catch (NumberFormatException e) {
	    idIsNotLong(idParam);
	}

    }

    protected abstract Object getObjectById(Long id);

    protected abstract void setObject(Object object);

    protected void idIsNotLong(String idValue) {
	throw new RuntimeException("error");
	// TODO throw error
    };

    protected String getParam(String name) {
	return getParamsMap().get(name);
    }

    private Map<String, String> getParamsMap() {
	if (params == null) {
	    // TODO check request for errors?
	    Map<String, String> result = new HashMap<String, String>();
	    String uri = PrettyContext.getCurrentInstance()
		    .getOriginalRequestUrl();
	    if (uri.indexOf('?') != -1) {
		String query = uri.substring(uri.indexOf('?') + 1);
		String params[] = query.split("&");
		for (String p : params) {
		    String param[] = p.split("=");
		    result.put(param[0], param[1]);
		}
	    }
	    return result;
	} else {
	    return params;
	}
    }

}
