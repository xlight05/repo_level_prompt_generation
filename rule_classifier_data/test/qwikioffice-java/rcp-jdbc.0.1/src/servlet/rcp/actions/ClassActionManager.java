/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servlet.rcp.actions;

import net.rcp.dao.driver.controler.InterBaseDatos;
import net.rcp.dao.driver.cnx.Conexion;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;

import org.json.JSONObject;

/**
 *
 * @author rcrespo
 */
public class ClassActionManager implements InterActionManager{

   private ResourceBundle _res;

   private final String _DBCLASS_UUID="DB_UUID";
   private final String _FORM_UUID="FM_UUID";
   private final String _RESOURCES_PACKAGE="com.mrwlogistica.model.";

   public ClassActionManager(){
       
   }
    
   public JSONObject runAction(Hashtable _param) throws Exception{

       JSONObject _output = new JSONObject("{success:false,errors:{reason:'Acción no ejecutada.'}}");
       //Parámetros obligatorios
       String     _action      =(String)_param.get("_action");
       String     _db_class    ="com.mrwlogistica.base."+(String)_param.get(_DBCLASS_UUID);
       String     _ud_class    =(String)_param.get(_FORM_UUID);
       String     _json_params = (String)((_param.get("_json_params")==null)?"{}":_param.get("_json_params"));

       //Opcionales
       long       _limit =20;
       long       _start =0;
       Connection _cnx = null;

       if (_param.get("limit")!=null)
              _limit = Long.valueOf((String)_param.get("limit"));
       if (_param.get("start")!=null)
             _start = Long.valueOf((String)_param.get("start"));

       //verifica parametros obligatorios&&
       if (_action == null && _ud_class == null && _db_class == null)
           throw new Exception("Parámetros insuficientes.");
       //recupera objeto para tratamiento

       InterBaseDatos _object_action = (InterBaseDatos) Class.forName(_db_class).newInstance();

       _res=ResourceBundle.getBundle(_RESOURCES_PACKAGE+_ud_class+"-"+_action);

       //acción a ejecutar
       if (_action.toUpperCase().startsWith("SELECT")) {
          //SELECT   < sentencia _sql >
          String _where  = "";
          boolean _first = true;

          Hashtable _params = null;

          JSONObject _objfil = new JSONObject(_json_params);
          JSONObject _typos =  new JSONObject(_res.getString("_DML_COLUMNS"));
          JSONObject _tmp = null;
          

         Iterator _enum = _objfil.keys();
         Object      _data = null;

          int i = 1;

          while (_enum.hasNext()){
              String _key = (String)_enum.next();
              

              _tmp = _typos.getJSONObject(_key.toUpperCase());

              if (_tmp != null && _objfil.get(_key) !=null){

                String _t = _tmp.getString("type");
                String _m = _tmp.getString("mask");
                String _o = _tmp.getString("oper");
                String _cn = _tmp.getString("colname");
                

               
                  if (_t.equals("Double")){
                    _data = new Double(_objfil.getDouble(_key));
                  } else if (_t.equals("String")){
                    _data = _objfil.getString(_key);
                  } else if (_t.equals("Date")){
                    SimpleDateFormat _fmt = new SimpleDateFormat(_m,Locale.US);
                    _data = _fmt.parse(_objfil.getString(_key).replaceAll("GMT","CET"));
                  } else if (_t.equals("Long")){
                    _data = new Long(_objfil.getLong(_key));
                  } else if (_t.equals("Integer")){
                    _data = new Integer(_objfil.getInt(_key));
                  }
                

                if(_first){
                  _params = new Hashtable();
                  _first = false;
                } else {
                 
                    _where = _where + " AND ";

                }
              
                  _where = _where + _cn.toUpperCase()+" "+_o+" ?";
                

                _params.put(new Integer(i++),_data);

              }

          }

          if (!_first ) 
              _where = " WHERE " + _where ;

             _cnx = Conexion.getAccessDBConnection(null);

             _output = _object_action.runQuery(_res.getString("_DML")+_where, _params, _start, _limit, _cnx);
             try {
               if (_cnx !=null)
                 Conexion.freeConnection(_cnx);
             } catch (Exception e){e.printStackTrace();}

       } else if (_action.toUpperCase().startsWith("DELETE") || _action.toUpperCase().startsWith("INSERT") || _action.toUpperCase().startsWith("UPDATE")){
          boolean _first = true;
          int     _idx_retval =0;
          Hashtable _params = null;

          JSONObject _objfil = new JSONObject(_json_params);
          JSONObject _typos =  new JSONObject(_res.getString("_DML_COLUMNS"));
          JSONObject _tmp = null;


         Iterator _enum = _objfil.keys();
         Object      _data = null;

         
          while (_enum.hasNext()){
              String _key = (String)_enum.next();


              _tmp = _typos.getJSONObject(_key.toUpperCase());

              if (_tmp != null){

                String _t = _tmp.getString("type");
                String _m = _tmp.getString("mask");
                int    _p = _tmp.getInt("position");
                String _cn = _tmp.getString("colname");
                String _ty = _tmp.getString("ptype");

                if (_ty.equals("OUT")) _idx_retval = _p;

                  if (_t.equals("Double")){
                    _data = new Double(_objfil.getDouble(_key));
                  } else if (_t.equals("String")){
                    _data = _objfil.getString(_key);
                  } else if (_t.equals("Date")){
                    SimpleDateFormat _fmt = new SimpleDateFormat(_m,Locale.US);
                    _data = _fmt.parse(_objfil.getString(_key).replaceAll("GMT","CET"));
                  } else if (_t.equals("Long")){
                    _data = new Long(_objfil.getLong(_key));
                  } else if (_t.equals("Integer")){
                    _data = new Integer(_objfil.getInt(_key));
                  }


                if(_first){
                  _params = new Hashtable();
                  _first = false;
                }




                _params.put(new Integer(_p),_data);

              }

          }

             _first=false;
             _cnx = Conexion.getAccessDBConnection(null);
             _cnx.setAutoCommit(false);
             if (_idx_retval>0) _first=true;
             _output = _object_action.runUpdate(_res.getString("_DML"), _params, _first, _idx_retval, _cnx);
             try {
               if (_cnx !=null)
                 Conexion.freeConnection(_cnx);
             } catch (Exception e){e.printStackTrace();}

           
       }
       else {

       }

            
       return _output;

   }
}
