/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rcp.jos.modules.preferences;

import java.sql.*;
import java.util.ResourceBundle;
import rcp.jos.core.*;
import rcp.json.core.*;
import rcp.jos.modules.InterModuleGeneric;
import rcp.json.core.JSONObject;

/**
 *
 * @author rcrespo
 */
public class PreferencesModuleActions implements InterModuleGeneric{

     private ResourceBundle _res;

     private String _key_member = "_member_property";
     private String _key_group ="_group_property";

     private final String _RESOURCES_FILE="rcp.jos.core.escritorio";

     public PreferencesModuleActions(){
        _res=ResourceBundle.getBundle(_RESOURCES_FILE);
     }
     
     /*
     * 
     * 
     */
    private JSONObject viewThemes()  throws Exception{

        JSONObject _return = new JSONObject();
        Connection conn= null;
        Statement _stm = null;
        ResultSet _rst = null;
        JSONObject _json = null;
        JSONArray  _jsonArr = null;

       
        try {
            conn= db.lookupConnection(db.poolName);



            String sql = "SELECT id,name,path_to_thumbnail as pathtothumbnail,path_to_file as pathtofile "+
				"FROM "+
				" qo_themes "+
                " order by name ";

             //System.out.println(sql);

			 _stm = conn.createStatement();
             _rst = _stm.executeQuery(sql);
             _jsonArr = new JSONArray();
             _return.put("images", _jsonArr);


             while (_rst.next()){
                _json = new JSONObject();
                _json.put("id", _rst.getString(1));
                _json.put("name", _rst.getString(2));
                _json.put("pathtothumbnail", os.THEMES_DIR+_rst.getString(3));
                _json.put("pathtofile",os.THEMES_DIR+ _rst.getString(4));
                _jsonArr.put(_json);
             }
        } catch (Exception e){e.printStackTrace();}

        try {
          db.killConnection(conn);
        } catch (Exception e){e.printStackTrace();}

        return _return;

    }
     /*
     *
     *
     */
    private JSONObject viewWallpapers()  throws Exception{
        JSONObject _return = new JSONObject();
        Connection conn= null;
        Statement _stm = null;
        ResultSet _rst = null;
        JSONObject _json = null;
        JSONArray  _jsonArr = null;


        try {
            conn= db.lookupConnection(db.poolName);



            String sql = "SELECT id,name,path_to_thumbnail as pathtothumbnail,path_to_file as pathtofile "+
				"FROM "+
				" qo_wallpapers "+
                " order by name ";

             //System.out.println(sql);

			 _stm = conn.createStatement();
             _rst = _stm.executeQuery(sql);
             _jsonArr = new JSONArray();
             _return.put("images", _jsonArr);


             while (_rst.next()){
                _json = new JSONObject();
                _json.put("id", _rst.getString(1));
                _json.put("name", _rst.getString(2));
                _json.put("pathtothumbnail", os.WALLPAPERS_DIR+_rst.getString(3));
                _json.put("pathtofile",os.WALLPAPERS_DIR+ _rst.getString(4));
                _jsonArr.put(_json);
             }
        } catch (Exception e){e.printStackTrace();}

        try {
          db.killConnection(conn);
        } catch (Exception e){e.printStackTrace();}

        return _return;
    }
     /*
     *
     *
     */
    private JSONObject saveAppearance(JSONObject _params)  throws Exception{
        JSONObject _return = new JSONObject("{success:false,errors:{reason:'Acción no ejecutada.'}}");
        //ids        {"data":[{"backgroundcolor":"ffffff","fontcolor":"0B0D7F","theme":"3","transparency":70,"wallpaper":"13","wallpaperposition":"center"}]}
        JSONObject _datos = null;
        JSONArray _arr = _params.getJSONArray("data");
        _datos = _arr.getJSONObject(0);

        Connection conn= null;
        Statement _stm = null;

        try {
            conn= db.lookupConnection(db.poolName);



            String sql = "UPDATE qo_styles set backgroundcolor='"+_datos.getString("backgroundcolor")+"'"+
                          ",fontcolor='"+_datos.getString("fontcolor")+"'"+
                          ",qo_themes_id="+_datos.getString("theme")+
                          ",transparency="+_datos.getString("transparency")+
                          ",qo_wallpapers_id="+_datos.getString("wallpaper")+
                          ",wallpaperposition='"+_datos.getString("wallpaperposition")+"'"+
                          " where qo_members_id="+_datos.getString(_res.getString(_key_member))+
                          " and qo_groups_id="+_datos.getString(_res.getString(_key_group));

             //System.out.println(sql);

			 _stm = conn.createStatement();
             int i  = _stm.executeUpdate(sql);
             if (i>0){
                 _return = new JSONObject("{success:true}");
             } else
                 _return = new JSONObject("{success:false,errors:{reason:'Preferencias no dadas de alta.'}}");
        } catch (Exception e){e.printStackTrace();}

        try {
          db.killConnection(conn);
        } catch (Exception e){e.printStackTrace();}

        return _return;
    }
     /*
     *
     *
     */
    private JSONObject saveBackground(JSONObject _params)  throws Exception{
        JSONObject _return = new JSONObject("{success:false,errors:{reason:'Acción no ejecutada.'}}");
        //ids        {"data":[{"backgroundcolor":"ffffff","fontcolor":"0B0D7F","theme":"3","transparency":70,"wallpaper":"13","wallpaperposition":"center"}]}
        JSONObject _datos = null;
        JSONArray _arr = _params.getJSONArray("data");
        _datos = _arr.getJSONObject(0);

        Connection conn= null;
        Statement _stm = null;

        try {
            conn= db.lookupConnection(db.poolName);



            String sql = "UPDATE qo_styles set backgroundcolor='"+_datos.getString("backgroundcolor")+"'"+
                          ",fontcolor='"+_datos.getString("fontcolor")+"'"+
                          ",qo_themes_id="+_datos.getString("theme")+
                          ",transparency="+_datos.getString("transparency")+
                          ",qo_wallpapers_id="+_datos.getString("wallpaper")+
                          ",wallpaperposition='"+_datos.getString("wallpaperposition")+"'"+
                          " where qo_members_id="+_datos.getString(_res.getString(_key_member))+
                          " and qo_groups_id="+_datos.getString(_res.getString(_key_group));

             //System.out.println(sql);

			 _stm = conn.createStatement();
             int i  = _stm.executeUpdate(sql);
             if (i>0){
                 _return = new JSONObject("{success:true}");
             } else
                 _return = new JSONObject("{success:false,errors:{reason:'Preferencias no dadas de alta.'}}");
        } catch (Exception e){e.printStackTrace();}

        try {
          db.killConnection(conn);
        } catch (Exception e){e.printStackTrace();}

        return _return;
    }
     /*
     *
     *
     */
    private JSONObject saveShortcut(JSONObject _params)  throws Exception{
        JSONObject _return = new JSONObject("{success:false,errors:{reason:'Acción no ejecutada.'}}");
        //ids        {"data":[{"id":"demo-acc"},{"id":"demo-layout"},{"id":"demo-bogus"},{"id":"demo-tabs"}]}
        JSONObject _datos = null;
        JSONArray _arr = _params.getJSONArray("data");
        _datos = _arr.getJSONObject(0);

        Connection conn= null;
        PreparedStatement _pstm = null;

        try {
            conn= db.lookupConnection(db.poolName);

            String _member_id = _datos.getString(_res.getString(_key_member));
            String _group_id = _datos.getString(_res.getString(_key_group));

            conn.createStatement().execute("delete from qo_members_has_module_launchers where qo_launchers_id =(select id from qo_launchers where name='shortcut') and qo_members_id="+_datos.getString(_res.getString(_key_member))+" and qo_groups_id="+_datos.getString(_res.getString(_key_group)));

             String sql = "INSERT INTO qo_members_has_module_launchers (qo_members_id,"+
							"qo_groups_id,"+
							"qo_modules_id,"+
							"qo_launchers_id,"+
							"sort_order)"+
							"VALUES"+
                            "(?,?,(select id  from qo_modules where module_id=?),(select id from qo_launchers where name=?),?)";             //System.out.println(sql);

			 _pstm = conn.prepareStatement(sql);
             int i=0;

             for (int mm=0;mm<_arr.length();mm++){

                 _datos = _arr.getJSONObject(mm);
                 _pstm.clearParameters();
                 _pstm.setString(1, _member_id);
                 _pstm.setString(2, _group_id);
                 _pstm.setString(3, _datos.getString("id"));
                 _pstm.setString(4, "shortcut");
                 _pstm.setInt(5, mm);
                 i= _pstm.executeUpdate();
                 if (i==0){
                   _return = new JSONObject("{success:false,errors:{reason:'Preferencias no dadas de alta.'}}");
                   break;
                 }
             }

             if (i>0){
                 _return = new JSONObject("{success:true}");
                 //db.commitTransaction(conn);
             }

             
        } catch (Exception e){e.printStackTrace();}

        try {
          db.killConnection(conn);
        } catch (Exception e){e.printStackTrace();}

        return _return;
    }
     /*
     *
     *
     */
    private JSONObject saveAutorun(JSONObject _params)  throws Exception{
        JSONObject _return = new JSONObject("{success:false,errors:{reason:'Acción no ejecutada.'}}");
        //ids        {"data":[{"id":"demo-acc"},{"id":"demo-layout"},{"id":"demo-bogus"},{"id":"demo-tabs"}]}
        JSONObject _datos = null;
        JSONArray _arr = _params.getJSONArray("data");
        _datos = _arr.getJSONObject(0);

        Connection conn= null;
        PreparedStatement _pstm = null;

        try {
            conn= db.lookupConnection(db.poolName);

            String _member_id = _datos.getString(_res.getString(_key_member));
            String _group_id = _datos.getString(_res.getString(_key_group));

            conn.createStatement().execute("delete from qo_members_has_module_launchers where qo_launchers_id =(select id from qo_launchers where name='autorun') and qo_members_id="+_datos.getString(_res.getString(_key_member))+" and qo_groups_id="+_datos.getString(_res.getString(_key_group)));

             String sql = "INSERT INTO qo_members_has_module_launchers (qo_members_id,"+
							"qo_groups_id,"+
							"qo_modules_id,"+
							"qo_launchers_id,"+
							"sort_order)"+
							"VALUES"+
                            "(?,?,(select id  from qo_modules where module_id=?),(select id from qo_launchers where name=?),?)";             //System.out.println(sql);

			 _pstm = conn.prepareStatement(sql);
             int i=0;

             for (int mm=0;mm<_arr.length();mm++){

                 _datos = _arr.getJSONObject(mm);
                 _pstm.clearParameters();
                 _pstm.setString(1, _member_id);
                 _pstm.setString(2, _group_id);
                 _pstm.setString(3, _datos.getString("id"));
                 _pstm.setString(4, "autorun");
                 _pstm.setInt(5, mm);
                 i= _pstm.executeUpdate();
                 if (i==0){
                   _return = new JSONObject("{success:false,errors:{reason:'Preferencias no dadas de alta.'}}");
                   break;
                 }
             }

             if (i>0){
                 _return = new JSONObject("{success:true}");
                 //db.commitTransaction(conn);
             }

             
        } catch (Exception e){e.printStackTrace();}

        try {
          db.killConnection(conn);
        } catch (Exception e){e.printStackTrace();}
        return _return;
    }
     /*
     *
     *
     */
    private JSONObject saveQuickstart(JSONObject _params)  throws Exception{
        JSONObject _return = new JSONObject("{success:false,errors:{reason:'Acción no ejecutada.'}}");
        JSONObject _datos = null;
        JSONArray _arr = _params.getJSONArray("data");
        _datos = _arr.getJSONObject(0);

        Connection conn= null;
        PreparedStatement _pstm = null;

        try {
            conn= db.lookupConnection(db.poolName);

            String _member_id = _datos.getString(_res.getString(_key_member));
            String _group_id = _datos.getString(_res.getString(_key_group));

            conn.createStatement().execute("delete from qo_members_has_module_launchers where qo_launchers_id =(select id from qo_launchers where name='quickstart') and qo_members_id="+_datos.getString(_res.getString(_key_member))+" and qo_groups_id="+_datos.getString(_res.getString(_key_group)));

             String sql = "INSERT INTO qo_members_has_module_launchers (qo_members_id,"+
							"qo_groups_id,"+
							"qo_modules_id,"+
							"qo_launchers_id,"+
							"sort_order)"+
							"VALUES"+
                            "(?,?,(select id  from qo_modules where module_id=?),(select id from qo_launchers where name=?),?)";             //System.out.println(sql);

			 _pstm = conn.prepareStatement(sql);
             int i=0;

             for (int mm=0;mm<_arr.length();mm++){

                 _datos = _arr.getJSONObject(mm);
                 _pstm.clearParameters();
                 _pstm.setString(1, _member_id);
                 _pstm.setString(2, _group_id);
                 _pstm.setString(3, _datos.getString("id"));
                 _pstm.setString(4, "quickstart");
                 _pstm.setInt(5, mm);
                 i= _pstm.executeUpdate();
                 if (i==0){
                   _return = new JSONObject("{success:false,errors:{reason:'Preferencias no dadas de alta.'}}");
                   break;
                 }
             }

             if (i>0){
                 _return = new JSONObject("{success:true}");
                 //db.commitTransaction(conn);
             }

             
        } catch (Exception e){e.printStackTrace();}

        try {
          db.killConnection(conn);
        } catch (Exception e){e.printStackTrace();}
        return _return;
    }
    /*
     *
     *
     */
    @Override
    public JSONObject runAction(JSONObject _params,String _action) throws Exception{

        JSONObject _return = new JSONObject("{success:false,errors:{reason:'Acción no ejecutada.'}}");

        if (null!=_action && _action.equals("viewThemes")){
           _return = this.viewThemes();
        }
        else if (null!=_action && _action.equals("viewWallpapers")){
           _return = this.viewWallpapers();
        }
         else if (null!=_action && _action.equals("saveAppearance")){
           _return = this.saveAppearance(_params);
        }
         else if (null!=_action && _action.equals("saveBackground")){
           _return = this.saveBackground(_params);
        }
         else if (null!=_action && _action.equals("saveShortcut")){
           _return = this.saveShortcut(_params);
        }
         else if (null!=_action && _action.equals("saveAutorun")){
           _return = this.saveAutorun(_params);
        }
        else if (null!=_action && _action.equals("saveQuickstart")){
           _return = this.saveQuickstart(_params);
        }

        return _return;
    }
}
