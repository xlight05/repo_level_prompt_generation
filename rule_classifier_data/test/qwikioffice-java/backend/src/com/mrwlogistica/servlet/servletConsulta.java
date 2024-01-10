/*
 * servletConsulta.java
 *
 * Created on 7 de febrero de 2008, 18:43
 */

package com.mrwlogistica.servlet;

import com.mrwlogistica.model.BEANConsulta;
import com.mrwlogistica.model.BEANConsultaIncidencia;
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.*;


import javax.servlet.*;
import javax.servlet.http.*;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author mrwlogistica
 * @version
 */
public class servletConsulta extends HttpServlet {
    
    
    /** Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException, JSONException, Exception {
        
       PrintWriter out = response.getWriter();
       boolean scriptTag = false;
       String cb = request.getParameter("callback");
       BEANConsulta _dato = new BEANConsulta();
       
       System.out.println("Entrando en el Servlet");
        Enumeration params = request.getParameterNames();
        if (params != null){
          while (params.hasMoreElements()){
            String _dataName = (String)params.nextElement();
            String _dataValue = request.getParameter(_dataName);
            System.out.println(_dataName+"\t"+_dataValue);
          }
        }
        
       long _limit=20;
       long _start=0;
       
       if (request.getParameter("limit")!=null)
              _limit = Long.valueOf(request.getParameter("limit"));
       if (request.getParameter("start")!=null)
             _start = Long.valueOf(request.getParameter("start"));  
       
       String _filtros = ((request.getParameter("filtro")==null)?"{}":request.getParameter("filtro"));
       String _tipocon = request.getParameter("_T");
       
       String _where  = "";
       boolean _first = true;
       
       Hashtable _params = null;
       
       JSONObject _objfil = new JSONObject(_filtros);
       JSONObject _typos = _dato.getTypes();
       JSONObject _tmp = null;
       
       Iterator _enum = _objfil.keys();
       Object      _data = null;
       
       int i = 1;
       
       while (_enum.hasNext()){
           String _key = (String)_enum.next();
           if(_first){
               _params = new Hashtable();
               _first = false;
           } else {
               _where = _where + " AND ";
           }
           
           _tmp = _typos.getJSONObject(_key.toUpperCase());
           
           if (_tmp != null){
             String _t = _tmp.getString("type");
             String _m = _tmp.getString("mask");
             String _o = _tmp.getString("oper");
             
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
             
             _where = _where + _key.toUpperCase()+" "+_o+" ?";
            
             _params.put(new Integer(i++),_data);
            
           }
       }
       if (!_first) _where = " WHERE " + _where ;
       System.out.println(_where);
       System.out.println(_params);
       
       
       
       
       try{
            
            
            //System.out.println(_tmp.getJSONObject("FECGRAB").keys().nextElement());
		if (cb != null) {
			scriptTag = true;
			response.setContentType("text/javascript");
		} else {
			response.setContentType("application/x-json");
		}
		if (scriptTag) {
			out.write(cb + "(");
		}
               
                out.print(_dato.loadData(_where,_params,_start,_limit));
                
		if (scriptTag) {
			out.write(");");
		}

        } catch (Exception e){e.printStackTrace();}
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
