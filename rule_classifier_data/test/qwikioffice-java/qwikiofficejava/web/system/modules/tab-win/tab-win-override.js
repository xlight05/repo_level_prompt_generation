/* Override the module code here.
 * This code will be Loaded on Demand.
 */

Ext.override(QoDesk.TabWindow, {
	
	createWindow : function(){
        var desktop = this.app.getDesktop();
        var win = desktop.getWindow(this.moduleId);
        
        if(!win){
        	var winWidth = desktop.getWinWidth() / 1.1;
			var winHeight = desktop.getWinHeight() / 1.1;
			
            win = desktop.createWindow({
                id: this.moduleId,
                title: 'Cuadros de Mando',
                width: winWidth,
                height: winHeight,
                iconCls: 'tab-icon',
                shim: false,
                constrainHeader: true,
                layout: 'fit',
                items:
                    new Ext.TabPanel({
                        activeTab:0,
                        items: [{
                        	autoScroll: true,
                            title: 'Cuadro de mando <b>GMAP</b>',
                            header: false,
                            html: '<iframe src="http://mrw.endor.stratebi.es/mrw/MRW/mapa/Map.jsp"  width="100%" height="100%" scrolling="no"/>',
                			border: false
                        },{
                            title: 'Cuadro de mando <b>General</b>',
                            header:false,
                            html: '<iframe src="http://mrw.endor.stratebi.es/mrw/MRW/cuadro_mando/cuadro_mando_mrw.jsp"  width="100%" height="100%" scrolling="yes"/>',
                            border:false
                        },
                        {
                            title: 'Vista de Análisis <b>OLAP</b>',
                            header:false,
                            html: '<iframe src="http://mrw.endor.stratebi.es/mrw/ViewAction?solution=mrw&path=&action=vista_total.xaction"  width="100%" height="100%" scrolling="yes"/>',
                            border: false
                        },{
                            title: 'Vista de Análisis <b>Cliente</b>',
                            header:false,
                            html: '<iframe src="http://mrw.endor.stratebi.es/mrw/ViewAction?solution=mrw&path=&action=vista_cliente1.xaction"  width="100%" height="100%" scrolling="yes"/>',
                            border:false
                        }]
                    }),
                    taskbuttonTooltip: '<b>Cuadro Mandos</b><br />Ventana de Cuadro de Mandos'
            });
        }
        win.show();
    }
});