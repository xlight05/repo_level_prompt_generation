package com.xiaonei.api;

import java.util.HashMap;
import java.util.Map;

/**
 * 2008-6-16 下午04:58:22
 */
public enum ApplicationProperty {
    /**
     * 
     */
    APPLICATION_NAME("application_name", "string"),
    /**
     * 
     */
    CALLBACK_URL("callback_url", "string"),
    /**
     * 
     */
    POST_INSTALL_URL("post_install_url", "string"),
    /**
     * 
     */
    UNINSTALL_URL("uninstall_url", "string"),
    /**
     * 
     */
    IP_LIST("ip_list", "string"),
    /**
     * 
     */
    EMAIL("email", "string"),
    /**
     * 
     */
    DESCRIPTION("description", "string"),
    /**
     * 
     */
    USE_IFRAME("use_iframe", "bool"),
    /**
     * 
     */
    DESKTOP("desktop", "bool"),
    /**
     * 
     */
    IS_MOBILE("is_mobile", "bool"),
    /**
     * 
     */
    DEFAULT_FBML("default_fbml", "string"),
    /**
     * 
     */
    DEFAULT_COLUMN("default_column", "bool"),
    /**
     * 
     */
    MESSAGE_URL("message_url", "string"),
    /**
     * 
     */
    MESSAGE_ACTION("message_action", "string"),
    /**
     * 
     */
    ABOUT_URL("about_url", "string"),
    /**
     * 
     */
    PRIVATE_INSTALL("private_install", "bool"),
    /**
     * 
     */
    INSTALLABLE("installable", "bool"),
    /**
     * 
     */
    PRIVACY_URL("privacy_url", "string"),
    /**
     * 
     */
    HELP_URL("help_url", "string"),
    /**
     * 
     */
    SEE_ALL_URL("see_all_url", "string"),
    /**
     * 
     */
    TOS_URL("tos_url", "string"),
    /**
     * 
     */
    DEV_MODE("dev_mode", "bool"),
    /**
     * 
     */
    PRELOAD_FQL("preload_fql", "string");
    /**
     * 
     */
    protected static final Map<String,ApplicationProperty> PROP_TABLE;
    static {
        PROP_TABLE = new HashMap<String,ApplicationProperty>();
        for (ApplicationProperty prop: ApplicationProperty.values()) {
            PROP_TABLE.put(prop.getName(), prop);
        }
    }
    private String name;
    private String type;

    private ApplicationProperty(String name, String type) {
        this.name = name;
        this.type = type;
    }

    /**
     * 
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     * 
     * @param propName
     * @return
     */
    public static ApplicationProperty getPropertyForString(String propName) {
        return PROP_TABLE.get(propName);
    }

    public static ApplicationProperty getProperty(String name) {
        return getPropertyForString(name);
    }

    public String propertyName() {
        return this.getName();
    }

    public String toString() {
        return this.getName();
    }

    public boolean isBooleanProperty() {
        return "bool".equals(this.type);
    }

    public boolean isStringProperty() {
        return "string".equals(this.type);
    }

    /**
     * 
     * @param name
     * @return
     */
    public boolean isName(String name) {
        return toString().equals(name);
    }
}
