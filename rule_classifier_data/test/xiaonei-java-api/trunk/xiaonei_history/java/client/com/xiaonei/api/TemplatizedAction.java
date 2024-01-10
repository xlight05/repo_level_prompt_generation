
package com.xiaonei.api;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Utility class to assist in creating a templatized action for publishing to the 
 * minifeed/newsfeed, because the API call Facebook decided to add in order to do 
 * it is ridiculously complex.
 */
public class TemplatizedAction {   
    /**
     * 模板的Id 是 一个 1 至 10 的其中的一个整数
     */
    private Integer templateId;
    private JSONObject titleParams;
    private JSONObject bodyParams;
    /**
     * 资源Id，发表Blog时要分发Feed，则此参数可以传入此Blog的id
     */
    private Integer resourceId;
    private TemplatizedAction() {
        //empty constructor not allowed, at a minimum the titleTemplate parameter is needed
    }
    
    /**
     * Constructor
     * 
     * @param titleTemplate the title-template to set.
     */
    public TemplatizedAction(Integer templateId) {
    	this.setTemplateId(templateId);
        this.titleParams = new JSONObject();
        this.bodyParams = new JSONObject();
    }
    
    
    /**
     * Add a parameter value for the title template.  It will be used to replace the corresponding token when 
     * the feed entry is rendered.
     * 
     * @param key the name of the parameter/token.
     * @param value the value to set it to.
     */
    public void addTitleParam(String key, String value) {
        addParam(titleParams, key, value);
    }
    
    /**
     * Add a parameter value for the body template.  It will be used to replace the corresponding token when 
     * the feed entry is rendered.
     * 
     * @param key the name of the parameter/token.
     * @param value the value to set it to.
     */
    public void addBodyParam(String key, String value) {
        addParam(bodyParams, key, value);
    }
    
    private void addParam(JSONObject map, String key, String value) {
        if (("actor".equals(key)) || ("target".equals(key))) {
            throw new RuntimeException(key + " is a reserved token name, you cannot set it yourself!");
        }
        try {
            map.put(key, value);
        }
        catch (JSONException e) {
        	if (XiaoneiRestClient.DEBUG) {
        		System.out.println("JSONException for key=" + key + ", value=" + value + "!");
            	e.printStackTrace();
        	}
        }
    }
    
    /**
     * Get the title params as a JSON-formatted string.
     * 
     * @return the parameters for the templatized title tokens.
     */
    public String getTitleParams() {
        return getJsonParams(titleParams);
    }
    
    /**
     * Get the body params as a JSON-formatted string.
     * 
     * @return the parameters for the templatized body tokens.
     */
    public String getBodyParams() {
        return getJsonParams(bodyParams);
    }
    
    private String getJsonParams(JSONObject params) {
        if (params.length() == 0) {
            return null;
        }
        return params.toString();
    }
    
    
	public Integer getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}

	public Integer getResourceId() {
		return resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

}
