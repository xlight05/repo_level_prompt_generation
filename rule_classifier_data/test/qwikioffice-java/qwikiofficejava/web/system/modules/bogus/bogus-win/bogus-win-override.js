Ext.override(QoDesk.BogusWindow, {
	
	detailModule : null,
	
	init : function(){
		this.createWindow();
	},
	
	createWindow : function(){
		var desktop = this.app.getDesktop();
		var win = desktop.getWindow('bogus-win');
		
		if(!win){
            win = desktop.createWindow({
                autoScroll: true,
                id: 'bogus-win',
                title: 'Informes',
                width:640,
                height:480,
                iconCls: 'bogus-icon',
                layout: 'fit',
                items: new Ext.Panel({heigth: '100%',html: '<iframe src="http://mrw.endor.stratebi.es/mrw/adhoc/waqr.html"  width="100%" height="100%" scrolling="yes"/>'}),
                shim:false,
                animCollapse:false,
                constrainHeader:true,
                taskbuttonTooltip: '<b>Informes</b><br />A bogus window'
            });
        }
        
        win.show();
    }
});



