
Ext.onReady(function(){

    Ext.QuickTips.init();
	var fp = new Ext.FormPanel({        
        fileUpload: true,
        width: 320,
        frame: true,    
			//height:170,
		autoheight:true,
        bodyStyle: 'padding: 10px 10px 0 10px;',
        labelWidth: 60,
        defaults: {
            anchor: '95%',
            //allowBlank: false,
            msgTarget: 'side'
        },
        items: [
		
		{
            xtype: 'textfield',
			name: 'Filename',
            fieldLabel: 'File Name '
        },{
            xtype: 'fileuploadfield',
            id: 'form-file',
            emptyText: 'Select an file',
            fieldLabel: 'File',
            name: 'Fileconten',
            buttonCfg: {
                text: '',
                iconCls: 'upload-icon'
            }
        },
		{
            xtype: 'fileuploadfield',
            id: 'form-file1',
            emptyText: 'Select an image',
            fieldLabel: 'Image',
            name: 'photo',
            buttonCfg: {
                text: '',
                iconCls: 'upload-icon'
            }
        },
		{
            xtype: 'datefield',
            fieldLabel: 'Date',			
			anchor: '95%',
			name: 'date',
			format: 'm/d/Y'
        }
		],
        buttons: [{
            text: 'Save',
            handler: function(){
                if(fp.getForm().isValid()){
	                fp.getForm().submit({
	                    url: 'phpupload.php',
	                    waitMsg: 'Uploading your photo...',
	                    success: function(fp, o){
	                        Ext.MessageBox.alert('Creation OK','Tambah Laporan Berhasil..');
							
	                    },
						failure: function(fp, o){							 
							  Ext.MessageBox.alert('Warning','Tambah Laporan Gagal...');    
							} 
	                });
                }
            }
        },{
            text: 'Reset',
            handler: function(){
                fp.getForm().reset();
            }
        }]
    });
	
	var createwindow = new Ext.Window({
			
			title:'Form Upload',
			width:315,
			height:190,
			closable: false,		
			items: fp
			});
		createwindow.show(); 
			
    
});


