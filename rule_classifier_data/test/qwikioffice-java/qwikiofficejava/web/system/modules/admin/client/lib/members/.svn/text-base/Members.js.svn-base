/*
 * qWikiOffice Desktop 1.0
 * Copyright(c) 2007-2008, Integrated Technologies, Inc.
 * licensing@qwikioffice.com
 * 
 * http://www.qwikioffice.com/license
 */

QoDesk.QoAdmin.Members = Ext.extend(Ext.Panel, {
   ownerModule: null

   , initComponent : function(){
      Ext.apply(this, {
         border: false
         , iconCls: 'qo-admin-member'
         , id: 'qo-admin-members'
         , items: new QoDesk.QoAdmin.MembersGrid({ ownerModule: this.ownerModule })
         , layout: 'fit'
         , title: 'Members'
      });

      QoDesk.QoAdmin.Members.superclass.initComponent.call(this);
   }
});