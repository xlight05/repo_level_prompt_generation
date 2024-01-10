Ext.namespace('Ext.ux');

Ext.ux.PageSizePlugin = function(itemsPerPageText) {
  this.itemsPerPageText = itemsPerPageText || "items per page";
  Ext.ux.PageSizePlugin.superclass.constructor.call( this, {
  store: new Ext.data.SimpleStore({
                                   fields: ['text', 'value'],
                                   data: [['10', 10], ['20', 20], ['30', 30], ['50', 50], ['100', 100], ['120', 120], ['130', 130], ['150', 150], ['200', 200]]
                                 }),
                                 mode: 'local',
                                 displayField: 'text',
                                 valueField: 'value',
                                 editable: false,
                                 allowBlank: false,
                                 triggerAction: 'all',
                                 width: 40
                                 });
};

Ext.extend(Ext.ux.PageSizePlugin, Ext.form.ComboBox, { 
                                                       init: function(paging) {
                                                                                paging.on('render', this.onInitView, this);
                                                                                this.setWidth(60);
                                                             },

                                                       onInitView: function(paging) {
                                                                                paging.add('-',this,this.itemsPerPageText);
                                                                                this.setValue(paging.pageSize);
                                                                                this.on('select', this.onPageSizeChanged, paging);
                                                                   },

                                                       onPageSizeChanged: function(combo) {
                                                                                this.pageSize = parseInt(combo.getValue());
                                                                                this.doLoad(0);
                                                                    }
});
