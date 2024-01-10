/*
 * servletAccionesxCupon.java
 *
 * Created on 21 de febrero de 2008, 8:43
 */

package com.mrwlogistica.servlet;

import com.mrwlogistica.model.BEANAccxCup;
import java.io.*;
import java.net.*;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 * @author mrwlogistica
 * @version
 */
public class servletAccionesxCupon extends HttpServlet {
    
    /** Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        PrintWriter out = response.getWriter();
       boolean scriptTag = false;
      String cb = request.getParameter("callback");
       BEANAccxCup _dato = new BEANAccxCup();
       
       System.out.println("Entrando en el Servlet");
        Enumeration params = request.getParameterNames();
        if (params != null){
          while (params.hasMoreElements()){
            String _dataName = (String)params.nextElement();
            String _dataValue = request.getParameter(_dataName);
            System.out.println(_dataName+"\t"+_dataValue);
          }
        }
        
       long _limit = Long.valueOf(request.getParameter("limit"));
       long _start = Long.valueOf(request.getParameter("start"));
       Double _cup_codnum = new Double(request.getParameter("_cupon"));
       String _where =" WHERE CUP_CODNUM_ORI=?";
       Hashtable _params = new Hashtable();
       _params.put(new Integer("1"),_cup_codnum);
       
       try{
            
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
        processRequest(request, response);
    }
    
    /** Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
    
    /** Returns a short description of the servlet.
     */
    public String getServletInfo() {
        return "Short description";
    }
    // </editor-fold>
}
