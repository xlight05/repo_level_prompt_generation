/*
 * ServletActionManager.java
 *
 * Created on 7 de febrero de 2009, 18:43
 */

package com.mrwlogistica.servlet;

import servlet.rcp.actions.InterActionManager;
import java.io.*;
import java.util.*;


import javax.servlet.*;
import javax.servlet.http.*;
import org.json.JSONException;
import org.json.JSONObject;


/**
 *
 * @author rcrespo
 * @version
 */
public class ServletActionManager extends HttpServlet {
    private final String _ACTION_MANAGER_UUID="AM_UUID";
    private String _CHARACTER_ENCODING = "ISO-8859-1";

    /** Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @see 
     *     Los Los parámetros mínimos a recibir por el servlet serán los siguientes<br>
     *    &nbsp;&nbsp;AM_UUID : Identifica el controlador de acciones que se debe instanciar.<br>
     *    &nbsp;&nbsp;DB_UUID : Identifica el controlador que el ActionManager debe instanciar.<br>
     *    &nbsp;&nbsp;FM_UUID : Identifica el nombre del formulario o la unidad de negocio llamante.<br>
     *    &nbsp;&nbsp;_action : Acción a ejecutar.<br>
     *    &nbsp;&nbsp;_json_params : Parametros para la acción a ejecutar en formato JSON.<br><br><br>
     *
     * <b>Sample</b><br>
     * /backend/exec.mrwl?_action=SELECT-01&AM_UUID=ClassActionManager&DB_UUID=ClassBaseDatos&FM_UUID=UD0001<br>
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException, JSONException, Exception {
    
       response.setCharacterEncoding("UTF-8");

       PrintWriter out = response.getWriter();
       Hashtable   _parametros = new Hashtable();
       String     _amgr_class    ="com.mrwlogistica.actions."+(String)request.getParameter(_ACTION_MANAGER_UUID);
       JSONObject _response = new JSONObject("{success:false,errors:{reason:'Acción no ejecutada.'}}");
       boolean scriptTag = false;
       String cb = request.getParameter("callback");
       
       //System.setProperty("file.encoding", this._CHARACTER_ENCODING);
       //System.setProperty("sun.jnu.encoding", this._CHARACTER_ENCODING);
        System.out.println("Entrando en el Servlet");
       //System.out.println("CharacterEncoding "+request.getCharacterEncoding());
       //System.out.println(System.getProperty("charset"));
       //Properties prop = System.getProperties();

       
       //Enumeration a = prop.keys();
       //Object dd=null;
       //while(a.hasMoreElements()){
       //    dd=a.nextElement();
       //    System.out.println(dd+"\t"+prop.get(dd));
       //}
       //if (request.getCharacterEncoding()!=null)
       //    _CHARACTER_ENCODING=request.getCharacterEncoding();

        Enumeration params = request.getParameterNames();
        if (params != null){
          while (params.hasMoreElements()){

            String _dataName = (String)params.nextElement();
            String _dataValue="";

            if (_dataName.equals("_json_params"))
              _dataValue = new String(request.getParameter(_dataName).getBytes(_CHARACTER_ENCODING));
            else
              _dataValue = request.getParameter(_dataName);
            System.out.println(_dataName+"\t"+_dataValue);
            _parametros.put(_dataName, _dataValue);

          }
        }

       try {
         InterActionManager _object_action = (InterActionManager) Class.forName(_amgr_class).newInstance();
         _response = _object_action.runAction(_parametros);
       } catch(Exception e) {
          _response = new JSONObject("{success:false,errors:{reason:'"+e.toString().replaceAll("[']", "").replaceAll("\n", "<br>")+"'}}");
       }
       
            //System.out.println(_tmp.getJSONObject("FECGRAB").keys().nextElement());
        //out.print("{\"total\":\"7\",\"data\":[{\"codigo\":\"ES\",\"descripcion\":\"ÑñÁáÉéÍíÓóÚúÄäËëÏïÖöÜüçÇ\"}]}");
        //return;
        System.out.println(_response.toString());
		if (cb != null) {
			scriptTag = true;
			response.setContentType("text/javascript");
            //response.setHeader("Content-type", "text/javascript;charset=UTF-8");
		} else {
			response.setContentType("application/x-json");
            //response.setHeader("Content-type", "application/x-json;charset=UTF-8");
		}
		if (scriptTag) {
			out.print(cb + "(");
		}
               //System.out.println(new String(_response.toString().getBytes("UTF-8")));
                out.print(_response.toString());
                
		if (scriptTag) {
			out.print(");");
		}

       
         System.out.println("Saliendo del Servlet");
    }
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        try {
           processRequest(request, response);
        } catch (Exception e){e.printStackTrace();}
    }
    
    /** Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception e){e.printStackTrace();}
        
    }
    
    /** Returns a short description of the servlet.
     */
    public String getServletInfo() {
        return "Short description";
    }
    // </editor-fold>
}
