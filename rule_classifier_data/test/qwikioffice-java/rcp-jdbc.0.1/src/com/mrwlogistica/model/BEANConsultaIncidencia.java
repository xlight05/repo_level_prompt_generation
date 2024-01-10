/*
 * BEANConsulta.java
 *
 * Created on 6 de febrero de 2008, 17:27
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.mrwlogistica.model;

import net.rcp.dao.driver.cnx.Conexion;

import java.sql.*;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.ResourceBundle;
import java.util.Vector;
import org.json.JSONObject;

/**
 *
 * @author mrwlogistica
 */
public class BEANConsultaIncidencia extends BEANBase{
    
    public final String _QUERY_DATA_ID = "_query_incidencias";
    public final String _QUERY_FILTER_ID = "_query_ftypes";
    public final String _resource="com.mrwlogistica.model.data";
    
    private ResourceBundle _res;
    
    /** Creates a new instance of BEANConsulta */
    public BEANConsultaIncidencia() {
        super();
        _res=ResourceBundle.getBundle(_resource);
    }
    
    public JSONObject loadData(String _where_clause,Hashtable _params,long _from,long _size) throws Exception{
        String _query = null;
        _query=_res.getString(_QUERY_DATA_ID);
        
        if (_where_clause !=null)
            _query+=_where_clause;
        
        return (this.dataToPagedJSONObject(_query,_params,_from,_size));
    }
    public JSONObject getTypes() throws Exception{
        return new JSONObject(_res.getString(_QUERY_FILTER_ID));
    }
}

