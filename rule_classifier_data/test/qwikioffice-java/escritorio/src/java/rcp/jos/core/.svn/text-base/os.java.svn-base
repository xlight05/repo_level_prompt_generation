/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rcp.jos.core;


import java.io.*;
import java.sql.*;
import java.net.*;
import rcp.json.core.*;

/**
 *
 *
 * @author rcrespo
 *
 *
 */
public class os {

    public static String THEMES_DIR="resources/themes/";
    public static String MODULES_DIR="system/modules/";
    public static String WALLPAPERS_DIR="resources/wallpapers/";
    public static String HOST_URL="http://localhost:8080/escritorio/";

    //
    // $os->module->load_all();
    // $os->privilege->get_all();
    // $os->module->get_all();
    // $os->launcher->get_all();
    // $os->preference->get_styles();
    //

    public static void loadModule(PrintWriter out,String module_id){

        Connection conn= null;
        Statement _stm = null;
        ResultSet _rst = null;



        try {
            conn= db.lookupConnection(db.poolName);


            String sql = "SELECT MF.directory,MF.file,MF.class_name FROM qo_modules_files MF "+
                         "INNER JOIN qo_modules M on M.id=MF.qo_modules_id "+
                         "WHERE MF.is_client_module=0 and MF.is_server_module=0 and MF.is_stylesheet=0 AND M.active=1 AND M.module_id='"+module_id+"'";

             System.out.println(sql);

			 _stm = conn.createStatement();
             _rst = _stm.executeQuery(sql);

             while (_rst.next()){
                  loadModule(out, HOST_URL, _rst.getString(1)+_rst.getString(2));
             }

        } catch (Exception e){e.printStackTrace();}

        try {
          db.killConnection(conn);
        } catch (Exception e){e.printStackTrace();}

    }

    public static void loadModule(PrintWriter out, String server_url,String module_file){

        String fileURL        ="";
        URL uc                = null;
        URLConnection cnx     = null;
        String line           ="";

        fileURL=server_url+MODULES_DIR+module_file;
        try {

            uc = new URL(fileURL);
            cnx = uc.openConnection();


            BufferedReader a  = new BufferedReader(new InputStreamReader(cnx.getInputStream(),"UTF-8"));


             while ((line=a.readLine())!=null)
                 out.println(line);
             out.print("\n");
        } catch (Exception e){e.printStackTrace();}
    }
    public static void loadAllModules(PrintWriter out){

        Connection conn= null;
        Statement _stm = null;
        ResultSet _rst = null;
       


        try {
            conn= db.lookupConnection(db.poolName);


            String sql = "SELECT MF.directory,MF.file,MF.class_name FROM qo_modules_files MF "+
                         "INNER JOIN qo_modules M on M.id=MF.qo_modules_id "+
                         "WHERE MF.is_client_module=1 AND M.active=1";

             System.out.println(sql);

			 _stm = conn.createStatement();
             _rst = _stm.executeQuery(sql);

             while (_rst.next()){
                  loadModule(out, HOST_URL, _rst.getString(1)+_rst.getString(2));
             }
            
        } catch (Exception e){e.printStackTrace();}

        try {
          db.killConnection(conn);
        } catch (Exception e){e.printStackTrace();}


    }
    public static String getAllStylesCSS(String member_id,String group_id){

        Connection conn= null;
        Statement _stm = null;
        ResultSet _rst = null;
        String _return="";

        try {
            conn= db.lookupConnection(db.poolName);


            String sql = "SELECT MF.directory,MF.file,MF.class_name FROM qo_modules_files MF "+
                         "INNER JOIN qo_modules M on M.id=MF.qo_modules_id "+
                         "WHERE MF.is_stylesheet=1 AND M.active=1";

             System.out.println(sql);

			 _stm = conn.createStatement();
             _rst = _stm.executeQuery(sql);

             while (_rst.next()){

                    _return+="<link rel=\"stylesheet\" type=\"text/css\" href=\""+MODULES_DIR+_rst.getString(1)+_rst.getString(2)+"\" />";

             }
           
        } catch (Exception e){e.printStackTrace();}

        try {
          db.killConnection(conn);
        } catch (Exception e){e.printStackTrace();}
        
        return  _return;
    }
    
    public static String getStylesPreferences(String member_id,String group_id){

        String _return="{}";
        Connection conn= null;
        Statement _stm = null;
        ResultSet _rst = null;

        try {
            conn= db.lookupConnection(db.poolName);


            String sql = "SELECT backgroundcolor,fontcolor,transparency,T.id AS themeid,T.name AS themename, "+
                	     "T.path_to_file AS themefile,W.id AS wallpaperid,W.name AS wallpapername,W.path_to_file AS wallpaperfile,"+
                         "wallpaperposition	FROM qo_styles S "+
                         "INNER JOIN qo_themes AS T ON T.id = S.qo_themes_id "+
                		 "INNER JOIN qo_wallpapers AS W ON W.id = S.qo_wallpapers_id "+
                         "WHERE "+
                         "qo_members_id = "+member_id+
                         " AND "+
        			     "qo_groups_id = "+group_id;

             System.out.println(sql);

			 _stm = conn.createStatement();
             _rst = _stm.executeQuery(sql);
             while (_rst.next()){
                   _return = "{\"backgroundcolor\":\""+_rst.getString(1)+"\""+
                             ",\"fontcolor\":\""+_rst.getString(2)+"\""+
                             ",\"transparency\":\""+_rst.getString(3)+"\""+
                             ",\"theme\":{\"id\":\""+_rst.getString(4)+"\""+
                             ",\"name\":\""+_rst.getString(5)+"\""+
                             ",\"pathtofile\":\""+THEMES_DIR+_rst.getString(6)+"\""+
                             "},"+
                             "\"wallpaper\":{\"id\":\""+_rst.getString(7)+"\""+
                             ",\"name\":\""+_rst.getString(8)+"\""+
                             ",\"pathtofile\":\""+WALLPAPERS_DIR+_rst.getString(9)+"\""+
                             "}"+
                             ",\"wallpaperposition\":\""+_rst.getString(10)+"\"}";
             }
        } catch (Exception e){e.printStackTrace();}

        try {
          db.killConnection(conn);
        } catch (Exception e){e.printStackTrace();}

        return _return;//_return.replace("/", "\\/");
    }

    public static String getAllPrivileges(String member_id,String group_id) {

        Connection conn= null;
        Statement _stm = null;
        ResultSet _rst = null;
        JSONObject _json = new JSONObject();
        JSONArray  _jsonArr = null;


        


        
        String _action=" ";

        try {
            conn= db.lookupConnection(db.poolName);


            String sql = "SELECT "+
				" is_allowed, "+
				" P.is_singular AS is_privilege_singular, "+
				" A.name AS action, "+
				" D.is_singular AS is_domain_singular, "+
				" M.id AS module_id, "+
				" M.module_id AS moduleId, "+
				" G.importance "+
				" FROM qo_groups_has_domain_privileges AS GDP "+					
					" INNER JOIN qo_privileges AS P ON P.id = GDP.qo_privileges_id  "+
					" INNER JOIN qo_privileges_has_module_actions AS PA ON PA.qo_privileges_id = P.id "+
					" INNER JOIN qo_modules_actions AS A ON A.id = PA.qo_modules_actions_id "+					
					" INNER JOIN qo_domains AS D ON D.id = GDP.qo_domains_id "+
					" INNER JOIN qo_domains_has_modules AS DM ON DM.qo_domains_id = D.id "+
					" INNER JOIN qo_modules AS M ON M.id = DM.qo_modules_id "+					
					" INNER JOIN qo_groups AS G ON G.id = GDP.qo_groups_id "+
					" INNER JOIN qo_groups_has_members AS MG ON MG.qo_groups_id = G.id "+
				" WHERE "+
				" qo_members_id = "+member_id+
				" AND "+
				" G.id = "+group_id+
				" ORDER BY "+
				" A.name, G.importance DESC";

             System.out.println(sql);

			 _stm = conn.createStatement();
             _rst = _stm.executeQuery(sql);

             while (_rst.next()){

                   if (!_action.equals(_rst.getString(3))) {
                       if (!_rst.isFirst()) {
                           _json.put(_action, _jsonArr);
                       }
                       _action=_rst.getString(3);
                       _jsonArr = new JSONArray();
                   }
                   _jsonArr.put(_rst.getString(6));

             }
             if (_jsonArr!= null)
                  _json.put(_action, _jsonArr);
        } catch (Exception e){e.printStackTrace();}

        try {
          db.killConnection(conn);
        } catch (Exception e){e.printStackTrace();}

        return _json.toString();
    }

    public static String getAllModules(String member_id,String group_id){
        Connection conn= null;
        Statement _stm = null;
        ResultSet _rst = null;
        String _return="[ ";
       

        try {
            conn= db.lookupConnection(db.poolName);


            String sql = "SELECT MF.directory,MF.file,MF.class_name FROM qo_modules_files MF "+
                         "INNER JOIN qo_modules M on M.id=MF.qo_modules_id "+
                         "WHERE MF.is_client_module=1 AND M.active=1";

             System.out.println(sql);

			 _stm = conn.createStatement();
             _rst = _stm.executeQuery(sql);

             while (_rst.next()){
                   if (!_rst.isFirst())
                       _return+=",";
                    _return+="new "+_rst.getString(3)+"()";

             }
            _return +="]";
        } catch (Exception e){e.printStackTrace();}

        try {
          db.killConnection(conn);
        } catch (Exception e){e.printStackTrace();}

        return  _return;
    }
    

    public static String getAllLaunchers(String member_id,String group_id){
        Connection conn= null;
        Statement _stm = null;
        ResultSet _rst = null;
        JSONObject _json = new JSONObject();
        JSONArray  _jsonArr = null;

        String _action=" ";

        try {
            conn= db.lookupConnection(db.poolName);


            String sql = "SELECT L.name,M.module_id as moduleId "+
				"FROM "+
				" qo_members_has_module_launchers ML "+
                " INNER JOIN qo_modules AS M ON M.id = ML.qo_modules_id "+
                " INNER JOIN qo_launchers AS L ON ML.qo_launchers_id = L.id "+
                " WHERE (qo_members_id=0 or qo_members_id = "+member_id+
				" ) AND ( qo_groups_id=0 or "+
				" qo_groups_id = "+group_id+
				" ) ORDER BY  L.name,ML.sort_order asc";

             System.out.println(sql);

			 _stm = conn.createStatement();
             _rst = _stm.executeQuery(sql);

             while (_rst.next()){

                   if (!_action.equals(_rst.getString(1))) {
                       if (!_rst.isFirst()) {
                           _json.put(_action, _jsonArr);
                       }
                       _action=_rst.getString(1);
                       _jsonArr = new JSONArray();
                   }
                   _jsonArr.put(_rst.getString(2));

             }
             if (_jsonArr!= null)
                  _json.put(_action, _jsonArr);
        } catch (Exception e){e.printStackTrace();}

        try {
          db.killConnection(conn);
        } catch (Exception e){e.printStackTrace();}

        return _json.toString();
    }

   


	public os(){

	
	}

	

}
