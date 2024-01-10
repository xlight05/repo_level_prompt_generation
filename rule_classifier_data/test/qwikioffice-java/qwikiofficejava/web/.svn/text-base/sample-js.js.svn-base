

Ext.namespace('QoDesk');
/*
 * qWikiOffice Desktop 0.8.1
 * Copyright(c) 2007-2008, Integrated Technologies, Inc.
 * licensing@qwikioffice.comzzz
 *
 * http://www.qwikioffice.com/license
 */

QoDesk.QoPreferences = Ext.extend(Ext.app.Module, {
	moduleType : 'system/preferences',
	moduleId : 'qo-preferences',
	menuPath : 'ToolMenu',
	launcher : {
        iconCls: 'pref-icon',
        shortcutIconCls: 'pref-shortcut-icon',
        text: 'Preferencias',
        tooltip: '<b>Preferencias</b><br />Modifica tu escritorio'
    }
});

/* This code defines the module and will be loaded at start up.
 *
 * When the user selects to open this module, the override code will
 * be loaded to provide the functionality.
 *
 * Allows for 'Module on Demand'.
 */

QoDesk.GridWindow = Ext.extend(Ext.app.Module, {
	moduleType : 'demo',
    moduleId : 'demo-grid',
    menuPath : 'StartMenu',
	launcher : {
        iconCls: 'grid-icon',
        shortcutIconCls: 'demo-grid-shortcut',
        text: 'Rejilla',
        tooltip: '<b>Rejilla</b><br />Ventana con Rejilla'
    }
});

/* This code defines the module and will be loaded at start up.
 *
 * When the user selects to open this module, the override code will
 * be loaded to provide the functionality.
 *
 * Allows for 'Module on Demand'.
 */

QoDesk.TabWindow = Ext.extend(Ext.app.Module, {
	moduleType : 'demo',
    moduleId : 'demo-tabs',
    menuPath : 'StartMenu',
	launcher : {
        iconCls: 'tab-icon',
        shortcutIconCls: 'demo-tab-shortcut',
        text: 'Cuadros de Mando',
        tooltip: '<b>Cuadros de Mando</b><br />Ventana con cuadros de mando'
    }
});

/* This code defines the module and will be loaded at start up.
 *
 * When the user selects to open this module, the override code will
 * be loaded to provide the functionality.
 *
 * Allows for 'Module on Demand'.
 */

QoDesk.AccordionWindow = Ext.extend(Ext.app.Module, {
	moduleType : 'demo',
	moduleId : 'demo-acc',
	menuPath : 'StartMenu',
	launcher : {
		iconCls: 'acc-icon',
		shortcutIconCls: 'demo-acc-shortcut',
		text: 'Lienzo en acordeon',
		tooltip: '<b>Lienzo en acordeon</b><br />Ventana con un lienzo en acordeon'
	}
});

/* This code will be loaded at start up.
 *
 * When the user selects to open this module, it's override code will
 * be loaded to provide the functionality.
 *
 * Allows for 'Module on Demand'.
 */

QoDesk.LayoutWindow = Ext.extend(Ext.app.Module, {
	moduleType : 'demo',
	moduleId : 'demo-layout',
	menuPath : 'StartMenu',
	launcher : {
		iconCls: 'layout-icon',
		shortcutIconCls: 'demo-layout-shortcut',
		text: 'Lienzo Bordes',
		tooltip: '<b>Lienzo Bordes</b><br />Ventana con partición'
	}
});

/* This code defines the module and will be loaded at start up.
 *
 * When the user selects to open this module, the override code will
 * be loaded to provide the functionality.
 *
 * Allows for 'Module on Demand'.
 */

QoDesk.BogusWindow = Ext.extend(Ext.app.Module, {
	moduleType : 'demo',
	moduleId : 'demo-bogus',
	menuPath : 'StartMenu/Informes',
	launcher : {
		iconCls: 'bogus-icon',
		shortcutIconCls: 'demo-bogus-shortcut',
		text: 'Informe a medida',
		tooltip: '<b>Ad-hoc</b><br />Informes a medida por usuario'
	}
});






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

return {"saveShortcut":["qo-preferences"],"saveQuickstart":["qo-preferences"],"saveAppearance":["qo-preferences"],"saveBackground":["qo-preferences"],"viewWallpapers":["qo-preferences"],"viewThemes":["qo-preferences"],"saveAutorun":["qo-preferences"],"loadModule":["qo-preferences","demo-layout","demo-grid","demo-bogus","demo-tabs","demo-acc"]};


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

return [ new QoDesk.QoPreferences(),new QoDesk.GridWindow(),new QoDesk.TabWindow(),new QoDesk.AccordionWindow(),new QoDesk.LayoutWindow(),new QoDesk.BogusWindow()];


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

return {"shortcut":["demo-layout","demo-bogus","demo-tabs"],"quickstart":["qo-preferences"],"contextmenu":["qo-preferences"],"autorun":[],};


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

return {"backgroundcolor":"ffffff","fontcolor":"0B0D7F","transparency":"70","theme":{"id":"3","name":"Vista Glass","pathtofile":"resources\/themes\/xtheme-vistaglass\/css\/xtheme-vistaglass.css"},"wallpaper":{"id":"13","name":"Blank","pathtofile":"resources\/wallpapers\/blank.gif"},"wallpaperposition":"center"};


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
        	// iconCls: 'user',
            // title: get_cookie('memberName'),
			toolPanelWidth: 115
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