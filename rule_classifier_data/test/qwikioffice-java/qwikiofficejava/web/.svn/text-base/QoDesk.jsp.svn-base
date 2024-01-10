<%--
    Document   : index
    Created on : 30-may-2010, 18:13:54
    Author     : rcp
--%>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="rcp.jos.core.os"%>

<%
String _userID = (String)request.getSession().getAttribute("_userID");
String _groupID= (String)request.getSession().getAttribute("_groupID");

if (_userID==null)
    response.sendRedirect("login.html");

response.getWriter().println("Ext.namespace('QoDesk');");
os.loadAllModules(response.getWriter());
%>

/*
 * qWikiOffice Desktop 0.8.1
 * Copyright(c) 2007-2008, Integrated Technologies, Inc.
 * licensing@qwikioffice.com
 *
 * http://www.qwikioffice.com/license
 *
 * NOTE:
 * This code is based on code from the original Ext JS desktop demo.
 * I have made many modifications/additions.
 *
 * The Ext JS licensing can be viewed here:
 *
 * Ext JS Library 2.0 Beta 2
 * Copyright(c) 2006-2007, Ext JS, LLC.
 * licensing@extjs.com
 *
 * http://extjs.com/license
 *
 */

QoDesk.App = new Ext.app.App({

	init : function(){
		Ext.QuickTips.init();
	},

	/**
     * Returns the privileges object.
     *
     * Example
     *
     * {
     *		saveAppearence: [
     *			'qo-preferences'
     *		],
     *		saveAutorun: [
     *			'qo-preferences'
     *		]
     *		...
     * }
     */
	getPrivileges : function(){
     
<%
    out.println("return "+os.getAllPrivileges(_userID, _groupID)+";");
%>
      
	},

	/**
	 * Returns an array of the module instances.
	 *
	 * Example:
	 *
	 * [
	 * 		new QoDesk.GridWindow(),
	 *		...
	 * ]
	 *
	 */
    getModules : function(){
     
<%
   out.println( "return "+os.getAllModules(_userID, _groupID)+";");
%>
       
    },

    /**
	 * Returns the launchers object.
	 * Contains the moduleId's of the modules to add to each launcher.
	 *
	 * Example:
	 *
	 * {
	 *		autorun: [
	 *
	 *		],
	 *		contextmenu: [
	 *			'qo-preferences'
	 *		],
	 *		quickstart: [
	 *			'qo-preferences'
	 *		],
	 *		shortcut: [
	 *			'qo-preferences'
	 *		]
	 *	}
	 */
    getLaunchers : function(){
       
<%
     out.println("return "+os.getAllLaunchers(_userID, _groupID)+";");
%>
       
    },

    /**
	 * Returns the Styles object.
	 *
	 * Example
	 *
	 * {
	 *		backgroundcolor: '575757',
	 *		fontcolor: 'FFFFFF',
	 *		transparency: 100,
	 *		theme: {
	 *			id: 2,
	 *			name: 'Vista Black',
	 *			pathtofile: 'resources/themes/xtheme-vistablack/css/xtheme-vistablack.css'
	 *		},
	 *		wallpaper: {
	 *			id: 10,
	 *			name: 'Blue Swirl',
	 *			pathtofile: 'resources/wallpapers/blue-swirl.jpg'
	 *		},
	 *		wallpaperposition: 'tile'
	 *	}
	 */
    getStyles : function(){
    
<%
    out.println("return "+os.getStylesPreferences(_userID, _groupID)+";");
%>
       
    },

	/**
	 * Returns the Start Menu's logout button configuration
	 */
	getLogoutConfig : function(){
		return {
			text: 'Salir',
			iconCls: 'logout',
			handler: function(){ window.location = "logout.php"; },
			scope: this
		};
	},

	/**
	 * Returns the Start Menu configuration
	 */
    getStartConfig : function(){
    	return {
        	 iconCls: 'user',
             //title: get_cookie('memberName'),
			toolPanelWidth: 115,
        };
    },

    /**
	 * Function that handles sorting of the Start Menu
	 * Return true to swap a and b
	 */
	startMenuSortFn : function(a, b){
	    // Sort in ASC alphabetical order
		// if( b.text < a.text ){
		//		return true;
		// }

		// Sort in ASC alphabetical order with menus at the bottom
		// if( (b.text < a.text) && !b.menu ){
		//		return true;
		// }

		// Sort in ASC alphabetical order with menus at the top
		if( ( ( b.menu && a.menu ) && ( b.text < a.text ) ) || ( b.menu && !a.menu ) || ( (b.text < a.text) && !a.menu ) ){
			return true;
		}

		return false;
	}
});