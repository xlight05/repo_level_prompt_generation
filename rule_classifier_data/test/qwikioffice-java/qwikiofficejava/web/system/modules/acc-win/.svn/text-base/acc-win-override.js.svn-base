/* Override the module code here.
 * This code will be Loaded on Demand.
 */

Ext.override(QoDesk.AccordionWindow, {
	
    createWindow : function(){
    	var desktop = this.app.getDesktop();
        var win = desktop.getWindow('acc-win');
        if(!win){
            win = desktop.createWindow({
                id: 'acc-win',
                title: 'Lienzo en Acordeon',
                width:250,
                height:400,
                iconCls: 'acc-icon',
                shim:false,
                animCollapse:false,
                constrainHeader:true,
                maximizable: false,
                taskbuttonTooltip: '<b>Lienzo en Acordeon</b><br />Ventana con un lienzo en acordeon',

                tbar:[{
                    tooltip:'<b>Tooltips enriquecidos</b><br />Muestra a tus usuarios lo que pueden hacer!',
                    iconCls:'demo-acc-connect'
                },'-',{
                    tooltip:'Nuevo usuario',
                    iconCls:'demo-acc-user-add'
                },' ',{
                    tooltip:'Borra el usuario actual',
                    iconCls:'demo-acc-user-delete'
                }],

                layout: 'accordion',
                layoutConfig: {
                    animate:false
                },

                items: [
                    new Ext.tree.TreePanel({
                        id:'im-tree',
                        title: 'Usuarios OnLine',
                        loader: new Ext.tree.TreeLoader(),
                        rootVisible:false,
                        lines:false,
                        autoScroll:true,
                        useArrows: true,
                        tools:[{
                            id:'refresh',
                            on:{
                                click: function(){
                                    var tree = Ext.getCmp('im-tree');
                                    tree.body.mask('Cargando...', 'x-mask-loading');
                                    tree.root.reload();
                                    tree.root.collapse(true, false);
                                    setTimeout(function(){ // mimic a server call
                                        tree.body.unmask();
                                        tree.root.expand(true, true);
                                    }, 1000);
                                }
                            }
                        }],
                        root: new Ext.tree.AsyncTreeNode({
                            text:'Online',
                            children:[{
                                text:'Contactos',
                                expanded:true,
                                children:[{
                                    text:'Jose',
                                    iconCls:'user',
                                    leaf:true
                                },{
                                    text:'Juan',
                                    iconCls:'user',
                                    leaf:true
                                },{
                                    text:'Fernando',
                                    iconCls:'user',
                                    leaf:true
                                },{
                                    text:'Luis',
                                    iconCls:'user',
                                    leaf:true
                                },{
                                    text:'Lorenzo',
                                    iconCls:'user',
                                    leaf:true
                                }]
                            },{
                                text:'Family',
                                expanded:true,
                                children:[{
                                    text:'Marta',
                                    iconCls:'user-girl',
                                    leaf:true
                                },{
                                    text:'Sara',
                                    iconCls:'user-girl',
                                    leaf:true
                                },{
                                    text:'Ion',
                                    iconCls:'user-kid',
                                    leaf:true
                                },{
                                    text:'Pedro',
                                    iconCls:'user-kid',
                                    leaf:true
                                }]
                            }]
                        })
                    }), {
                        title: 'Configuración',
                        html:'<p>Texto.</p>',
                        autoScroll:true
                    },{
                        title: 'Empleados',
                        html : '<p>Texto en este contenedor.</p>'
                    },{
                        title: 'Cacharros',
                        html : '<p>Texto.</p>'
                    }
                ]
            });
        }
        win.show();
    }
});