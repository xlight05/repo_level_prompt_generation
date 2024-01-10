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