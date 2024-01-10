/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rcp.jos.servlets;

import java.io.*;

//import java.net.*;

import java.util.Enumeration;
import java.util.ResourceBundle;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import rcp.jos.modules.InterModuleGeneric;

//import rcp.jos.modules.preferences.PreferencesModuleActions;
import rcp.json.core.*;

/**
 *
 * @author rcrespo
 */
public class ServletActionPreferences extends HttpServlet {

     private ResourceBundle _res;

     private String _key_member = "_member_property";
     private String _key_group ="_group_property";

     private final String _RESOURCES_FILE="rcp.jos.core.escritorio";

     public ServletActionPreferences(){
         _res=ResourceBundle.getBundle(_RESOURCES_FILE);
     }
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException,JSONException,Exception {
        
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        JSONObject _params = null;
        JSONObject _response = null;
        String _action = request.getParameter("action");
        String moduleId       = request.getParameter("moduleId");
        String _classInstance = request.getParameter("classInstance");
        String member_id      = (String)request.getSession().getAttribute(_res.getString(_key_member));
        String group_id      = (String)request.getSession().getAttribute(_res.getString(_key_group));


        if (request.getParameter("ids")!=null)
            _params = new JSONObject(request.getParameter("ids"));

        /*
         * Acciones que se ejecutan desde el módulo de preferencias.
         */

        Enumeration params = request.getParameterNames();
        if (params != null){
            while (params.hasMoreElements()){

                String _dataName = (String)params.nextElement();
                String _dataValue="";
                //System.out.println(request.getParameter(_dataName));
                if (_dataName.equals("ids")){
                    _params = new JSONObject(request.getParameter(_dataName));

                //System.out.println( _params.getClass().toString());
            }
           
            _dataValue = request.getParameter(_dataName);
            //System.out.println(_dataName+"\t"+_dataValue);
          }
        }

        if (_params !=null){
            if (_params.getJSONArray("data").length()>0){
                _params.getJSONArray("data").getJSONObject(0).put(_res.getString(_key_member),member_id);
                _params.getJSONArray("data").getJSONObject(0).put(_res.getString(_key_group),group_id);
            } else {
                JSONObject _tmp = new JSONObject("{"+_res.getString(_key_member)+":"+member_id+","+_res.getString(_key_group)+":"+group_id+"}");
                _params.getJSONArray("data").put(_tmp);
            }
        }
  //System.out.println((new PreferencesModuleActions()).runAction(_params,_action).toString());
  
 
 

        InterModuleGeneric _object_action = (InterModuleGeneric) Class.forName(_classInstance).newInstance();
        _response = _object_action.runAction(_params, _action);

        out.println(_response.toString());

        out.close();

    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.sendError(500);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        try{
           processRequest(request, response);
        } catch (Exception e){e.printStackTrace();response.sendError(500);}
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
