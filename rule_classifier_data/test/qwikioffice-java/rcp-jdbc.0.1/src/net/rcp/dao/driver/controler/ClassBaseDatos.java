/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.rcp.dao.driver.controler;


import java.sql.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.ResourceBundle;
import java.util.Vector;

import org.json.*;


/**
 *
 * @author rcrespo
 */
public class ClassBaseDatos implements InterBaseDatos{
/*
     *{"total":"1","datos":[{"apellidos":"Crespo Panizo","password":"crespo","alta":"06/02/2008","usuario":"rcrespo","nombre":"Roberto","id":"1"}]}
     */
    private Vector<JSONObject> _arrayData= null;
    
    

    private final String _ID_COUNT_QUERY ="_BEANBASE_count_query";
    private final String _ID_PAGED_QUERY ="_BEANBASE_paged_query";
    private ResourceBundle _res;
    private final String _resourceBase="com.mrwlogistica.model.UD-SELECT";


    /** Creates a new instance of BEANBase */
    public ClassBaseDatos() {
        _arrayData = new Vector<JSONObject>();
        _res=ResourceBundle.getBundle(_resourceBase);
    }
    public String formatDate(Date _param,String _mask){
       String _return ="";
       SimpleDateFormat _fmt = new SimpleDateFormat(_mask);

       _return = _fmt.format(_param);

       return _return;
    }
    public String formatTimestamp(Timestamp _param,String _mask){
        String _return="";
        SimpleDateFormat _fmt = new SimpleDateFormat(_mask);

        _return = _fmt.format(_param);

        return _return;
    }
    public String formatToNumber(Object _param){
        String _return="";

        if (_param instanceof Double){
            _return = Long.toString(((Double)_param).longValue());
        } else if (_param instanceof Float){
            _return = Long.toString(((Float)_param).longValue());
        } else
            _return = _param.toString();

        return _return;
    }
    public String formatToDecimal(Object _param, String _mask){
        String _return="";
        DecimalFormat _fmt = new DecimalFormat(_mask);

        _return = _fmt.format(_param);

        return _return;
    }
    public void setRecord(Hashtable _record) throws Exception {
      _arrayData.add(new JSONObject(_record));
    }
    public JSONObject getRecord(int _index) throws Exception{
        return _arrayData.elementAt(_index);
    }
    public JSONArray getJSONArray() throws Exception {
        return new JSONArray(_arrayData);
    }
   
    public JSONObject runQuery(String _query,Hashtable _params,long _from,long _size,Connection _cnx) throws Exception{

        //variables locales de funci�n
        PreparedStatement _pstm=null;
        PreparedStatement _total=null;
        Statement         _stm= null;
        ResultSet         _rs = null;
        JSONObject        _output = null;
        String            _queryTot_res=_res.getString(_ID_COUNT_QUERY);
        String            _queryData_res = _res.getString(_ID_PAGED_QUERY);
        String            _queryTot=_res.getString(_ID_COUNT_QUERY);
        String            _queryData = _res.getString(_ID_PAGED_QUERY);

        //Bloque de excepciones principal
        try {


            _queryTot = _queryTot_res.replaceAll("#query",_query);
            //_queryData = "SELECT * FROM ("+_query+") WHERE _IDX >="+_from+" AND _IDX <"+(_from+_size);
            _queryData = _queryData_res.replaceAll("#query",_query).replaceAll("#inirow",Long.toString(_from)).replaceAll("#endrow",Long.toString(_from+_size));

            //_cnx = Conexion.getAccessDBConnection("C:\\Documents and Settings\\rcrespo\\proyectos\\logistica.xls");

            _pstm = _cnx.prepareStatement(_queryData);
            _total = _cnx.prepareStatement(_queryTot);

            //Hashtable _params contiene los valores de los par�metros especificados
            //por la cadena _where_clause. Colocamos los par�metros.
            if (_params !=null){


            Enumeration _enum = _params.keys();

            while (_enum.hasMoreElements()){

                Integer _key=(Integer)_enum.nextElement();
                Object  _dat=_params.get(_key);

                if (_dat instanceof Long){
                    _pstm.setLong(_key.intValue(),(Long)_dat);
                    _total.setLong(_key.intValue(),(Long)_dat);
                } else if (_dat instanceof Double){
                    _pstm.setDouble(_key.intValue(),(Double)_dat);
                    _total.setDouble(_key.intValue(),(Double)_dat);
                } else if (_dat instanceof Float){
                    _pstm.setFloat(_key.intValue(),(Float)_dat);
                    _total.setFloat(_key.intValue(),(Float)_dat);
                } else if (_dat instanceof Integer){
                    _pstm.setInt(_key.intValue(),(Integer)_dat);
                    _total.setInt(_key.intValue(),(Integer)_dat);
                } else if (_dat instanceof java.sql.Date ){
                    _pstm.setDate(_key.intValue(),(Date)_dat);
                    _total.setDate(_key.intValue(),(Date)_dat);
                } else if (_dat instanceof java.util.Date ){
                    _pstm.setDate(_key.intValue(),new Date(((java.util.Date)_dat).getTime()) );
                    _total.setDate(_key.intValue(),new Date(((java.util.Date)_dat).getTime()) );
                }else {
                    _pstm.setString(_key.intValue(),(String)_dat);
                    _total.setString(_key.intValue(),(String)_dat);
                }

            }
            }
            // Ejecutamos la consulta
            _rs = _pstm.executeQuery();
            ResultSetMetaData _mtd = _rs.getMetaData();
            //Preparamos los datos de salida
            while (_rs.next()){

                Hashtable _tempData = new Hashtable();

                for (int i=1; i<=_mtd.getColumnCount();i++){
                    //System.out.println(_mtd.getColumnName(i)+" "+_rs.getString(i));

                   Object _value=_rs.getObject(i);
                   _value = ((_value==null)?"":_value);
                   String _data = null;

                   if (_value instanceof Long){
                       _data = ((Long)_value).toString();
                   } else if (_value instanceof Double){
                       _data = this.formatToDecimal(_value,"###.##");
                   } else if (_value instanceof Float){
                       _data = this.formatToDecimal(_value,"###.##");
                   } else if (_value instanceof Date){
                       _data = this.formatDate((Date)_value,"dd/MM/yyyy");
                   } else if (_value instanceof Timestamp){
                       _data = this.formatTimestamp((Timestamp)_value,"dd/MM/yyyy hh:mm:ss");
                   } else if (_value instanceof Integer){
                       _data = ((Integer)_value).toString();
                   } else
                       _data = (String)_value;

                    //String ooo = new String(_data.getBytes("UTF-8"));
                    //_data = _data.getBytes("UTF-8"));
                    _tempData.put(_mtd.getColumnName(i).toLowerCase(),_data);

                }
                this.setRecord(_tempData);
            }

            _rs.close();
            _rs = _total.executeQuery();
            _rs.next();

            long _totalr = _rs.getLong(1);

            _output = new JSONObject();
            _output.put("data",getJSONArray());
            _output.put("total",Long.toString(_totalr));

            //System.out.println(_output.toString());

        } catch (Exception e){_output=new JSONObject("{success:false,errors:{reason:'"+e.getMessage().replaceAll("[']", "").replace("\n","<br>")+"'}}");}

        //Liberamos el ResultSet
        try {
            if (_rs !=null)
              _rs.close();
        } catch (Exception e){e.printStackTrace();}
        //Liberamos el Statement
        try {
            if (_stm !=null)
              _stm.close();
        } catch (Exception e){e.printStackTrace();}
        //Liberamos el PreparedStatement

        try {
            if (_total !=null)
              _total.close();
        } catch (Exception e){e.printStackTrace();}
        //Liberamos la conexi�n a BBDD
        try {
            if (_pstm !=null)
              _pstm.close();
        } catch (Exception e){e.printStackTrace();}
        
        //retornamos el resultado
        return _output;
    }
    public JSONObject runUpdate(String _query,Hashtable _params,boolean _returnClause,int _Returning,Connection _cnx) throws Exception{

         //variables locales de funci�n
        CallableStatement _cstm=_cnx.prepareCall(_query);
        JSONObject        _output = new JSONObject("{success:false}");
        

         //Bloque de excepciones principal
        try {

            if (_params !=null){

               // valores y filtros
               Enumeration _enum = _params.keys();

               while (_enum.hasMoreElements()){

                  Integer _key=(Integer)_enum.nextElement();
                  Object  _dat=_params.get(_key);

                  if (_dat.equals("NULL")){
                      _dat = null;
                  }
                  //System.out.println("Object "+_dat);
                  
                  if (_key.intValue() == _Returning) {
                     _cstm.registerOutParameter(_Returning,Types.VARCHAR);
                  } else {
                    if (_dat instanceof Long){
                      _cstm.setLong(_key.intValue(),(Long)_dat);

                    } else if (_dat instanceof Double){
                      _cstm.setDouble(_key.intValue(),(Double)_dat);

                    } else if (_dat instanceof Float){
                      _cstm.setFloat(_key.intValue(),(Float)_dat);

                    } else if (_dat instanceof Integer){
                      _cstm.setInt(_key.intValue(),(Integer)_dat);

                    } else if (_dat instanceof java.sql.Date ){
                      _cstm.setDate(_key.intValue(),(Date)_dat);

                    } else if (_dat instanceof java.util.Date ){
                      _cstm.setDate(_key.intValue(),new Date(((java.util.Date)_dat).getTime()) );

                    }else {
                      _cstm.setString(_key.intValue(),(String)_dat);

                    }
                  }
               }

               
               }

               int _retorno = _cstm.executeUpdate();

               if (_retorno > 0 || _retorno==-1) {
                   if (_returnClause) {
                      _output.put("id", _cstm.getString(_Returning));
                      _output.put("success",true);
                   }

               }

               _cnx.commit();
               _output.put("success",true);
        } catch(Exception e){_output=new JSONObject("{success:false,errors:{reason:'"+e.getMessage().replaceAll("[']", "").replace("\n","<br>")+"'}}");e.printStackTrace(); _cnx.rollback();}

         
        //Liberamos la conexi�n a BBDD
        try {
            if (_cstm !=null)
              _cstm.close();
        } catch (Exception e){_output=new JSONObject("{success:false,errors:{reason:'"+e.getMessage().replaceAll("[']", "").replace("\n","<br>")+"'}}");}
        
        //retornamos el resultado

        return _output;
    }
}

  