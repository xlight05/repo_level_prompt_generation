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
public class ServletConfigManager extends HttpServlet {
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
       String     _amgr_class    ="com.mrwlogistica.actions.ClassActionManager";
       JSONObject _response = new JSONObject("{success:false,errors:{reason:'Acción no ejecutada.'}}");
       boolean scriptTag = false;
       String cb = request.getParameter("callback");

       System.out.println("Entrando en el Servlet");
      
        _parametros.put("_action", "SELECT-01");
        _parametros.put("DB_UUID", "ClassBaseDatos");
        _parametros.put("FM_UUID", "CF0001");

       try {
         InterActionManager _object_action = (InterActionManager) Class.forName(_amgr_class).newInstance();
         _response = _object_action.runAction(_parametros);

         request.getSession().setAttribute("_menu", _response);
         
         _response = new JSONObject("{success:true}");
       } catch(Exception e) {
          _response = new JSONObject("{success:false,errors:{reason:'"+e.toString().replaceAll("[']", "").replaceAll("\n", "<br>")+"'}}");
       }
       
     
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
