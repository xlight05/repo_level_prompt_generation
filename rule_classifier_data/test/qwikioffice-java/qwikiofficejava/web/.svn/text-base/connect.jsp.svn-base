<%--
    Document   : index
    Created on : 03-mar-2010, 20:24:26
    Author     : rcp
--%>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="java.io.*,java.net.*, java.util.*,rcp.json.core.*"%>
<%

/*
 * Acciones que se ejecutan desde el módulo de preferencias.
 */

System.out.println("*************** connect.jsp ******************");
Enumeration params = request.getParameterNames();
        if (params != null){
          while (params.hasMoreElements()){

            String _dataName = (String)params.nextElement();
            String _dataValue="";

            
            _dataValue = request.getParameter(_dataName);
            System.out.println(_dataName+"\t"+_dataValue);
            

          }
        }

  String moduleId       = request.getParameter("moduleId");
  String contextPath    = "http://localhost:8080/escritorio";
  String action         = request.getParameter("action");
  String fileURL        ="";
  URL uc                = null;
  URLConnection cnx     = null;
  String line           ="";

  //action viewThemes
  //moduleId	qo-preferences
  if (null != moduleId){
    
    if (null!= action){
    
        if (moduleId.equals("qo-preferences")){
            
            if (action.equals("viewThemes")){
                fileURL = contextPath+"/system/modules/qo-preferences/staticresponse/viewThemes.qo";
            }
            if (action.equals("viewWallpapers")){
               fileURL = contextPath+"/system/modules/qo-preferences/staticresponse/viewWallpapers.qo";
            }
            if (action.equals("saveAppearance")){
                /*action	saveAppearance
backgroundcolor	ffffff
fontcolor	0B0D7F
moduleId	qo-preferences
theme	3
transparency	70
wallpaper	1
wallpaperposition	center
 * */
               fileURL = contextPath+"/system/modules/qo-preferences/staticresponse/responseOk.qo";
            }
            if (action.equals("saveBackground")){
                /*action	saveBackground
backgroundcolor	ffffff
fontcolor	0B0D7F
moduleId	qo-preferences
theme	3
transparency	70
wallpaper	1
wallpaperposition	center
 * */
               fileURL = contextPath+"/system/modules/qo-preferences/staticresponse/responseOk.qo";
            }
            
            if (action.equals("saveShortcut")){
                /*action	saveShortcut
ids	["demo-layout","demo-bogus","demo-tabs"]
moduleId	qo-preferences
 * */
                fileURL = contextPath+"/system/modules/qo-preferences/staticresponse/responseOk.qo";
            }
            if (action.equals("saveAutorun")){
                /*action	saveAutorun
ids	["demo-grid","qo-preferences","demo-acc"]
moduleId	qo-preferences
 * */
                fileURL = contextPath+"/system/modules/qo-preferences/staticresponse/responseOk.qo";
            }
            if (action.equals("saveQuickstart")){
                /*action	saveQuickstart
ids	["qo-preferences","demo-acc"]
moduleId	qo-preferences
 * */
                fileURL = contextPath+"/system/modules/qo-preferences/staticresponse/responseOk.qo";
            }

            if (fileURL != null){

               uc = new URL(fileURL);
               cnx = uc.openConnection();


               BufferedReader a  = new BufferedReader(new InputStreamReader(cnx.getInputStream(),"UTF-8"));


               while ((line=a.readLine())!=null)
                 out.println(line);
  }
            
        }
    }
  
  }
  %>
