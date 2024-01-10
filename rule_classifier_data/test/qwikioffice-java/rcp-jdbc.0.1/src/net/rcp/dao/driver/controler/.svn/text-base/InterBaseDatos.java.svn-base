/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.rcp.dao.driver.controler;


import java.sql.*;
import java.util.Hashtable;
import org.json.*;

/**
 *
 * @author rcrespo
 */
public interface InterBaseDatos {

    public String formatDate(Date _param,String _mask);
    public String formatTimestamp(Timestamp _param,String _mask);
    public String formatToNumber(Object _param);
    public String formatToDecimal(Object _param, String _mask);
    public void setRecord(Hashtable _record) throws Exception;
    public JSONObject getRecord(int _index) throws Exception;
    public JSONArray getJSONArray() throws Exception;
    public JSONObject runQuery(String _query,Hashtable _params,long _from,long _size,Connection _cnx) throws Exception;
    public JSONObject runUpdate(String _query,Hashtable _params,boolean _returnClause,int _Returning,Connection _cnx) throws Exception;
    

}
