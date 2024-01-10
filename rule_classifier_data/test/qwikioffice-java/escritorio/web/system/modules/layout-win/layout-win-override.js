/*
 * qWikiOffice Desktop 0.7
 * Copyright(c) 2007-2008, Integrated Technologies, Inc.
 * licensing@qwikioffice.com
 * 
 * http://www.qwikioffice.com/license
 */

/* Override the module code here.
 * This code will be Loaded on Demand.
 */

Ext.override(QoDesk.LayoutWindow, {
	
	createWindow : function(){
		var desktop = this.app.getDesktop();
		var win = desktop.getWindow('layout-win');
		if(!win){
			var winWidth = desktop.getWinWidth() / 1.1;
			var winHeight = desktop.getWinHeight() / 1.1;
			
			win = desktop.createWindow({
				id: 'layout-win',
				title:'Lienzo Bordes',
				width:winWidth,
				height:winHeight,
				x:desktop.getWinX(winWidth),
				y:desktop.getWinY(winHeight),
				iconCls: 'layout-icon',
				shim:false,
				animCollapse:false,
				constrainHeader:true,
				minimizable:true,
    			maximizable:true,

				layout: 'border',
				tbar:[{
					text: 'Botón 1'
				},{
					text: 'Botón 2'
				}],
				items:[/*{
					region:'north',
					border:false,
					elements:'body',
					height:30
				},{
					region:'west',
					autoScroll:true,
					collapsible:true,
					cmargins:'0 0 0 0',
					margins:'0 0 0 0',
					split:true,
					title:'Panel 1',
					width:parseFloat(winWidth*0.3) < 201 ? parseFloat(winWidth*0.3) : 200
				},*/{
					region:'center',
					border:false,
					layout:'border',
					margins:'0 0 0 0',
					items:[{
						region:'north',
						elements:'body',
						title:'Vista de Análisis <b>OLAP</b>',
                        html: '<iframe src="http://mrw.endor.stratebi.es/mrw/ViewAction?solution=mrw&path=&action=vista_total.xaction"  width="100%" height="100%" scrolling="yes"/>',
						height:winHeight*0.3,
						split:true
					},{
						autoScroll:true,
						elements:'body',
						region:'center',
						
						title:'Cuadro de mando <b>General</b>',
                        html: '<iframe src="http://mrw.endor.stratebi.es/mrw/MRW/cuadro_mando/cuadro_mando_mrw.jsp"  width="100%" height="100%" scrolling="no"/>'
					}]
				}/*,{
					region:'south',
					border:false,
					elements:'body',
					height:25
				}*/],
				taskbuttonTooltip: '<b>Lienzo</b><br />Ventana con partición'
			});
		}
		win.show();
	}
});