/*
 * #%L
 * Katsu ExtJs
 * %%
 * Copyright (C) 2013 Katsu
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the 
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public 
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Notification Window
 */
NotificationMgr = {
    positions: []
};

Ext.define('Katsu.window.Notification',{
    extend:'Ext.window.Window',    
    initComponent: function(){
        Ext.apply(this, {
            iconCls: this.iconCls || 'x-icon-information',
            cls: 'x-notification',
            width:200,
            autoHeight: true,
            plain: false,
            draggable: false,
            shadow:false,
            bodyStyle: 'text-align:center'
        });
        this.label=Ext.create('Ext.form.Label',{});

        this.items=[this.label];

        if(this.autoDestroy) {
            this.task = new Ext.util.DelayedTask(this.hide, this);
        } else {
            this.closable = true;
        }
        this.callParent();
        return this;
    },
    setMessage: function(msg){
        this.label.setText(msg);
    },
    setTitle: function(title, iconCls){
        this.superclass.setTitle.call(this, title, iconCls||this.iconCls);  
    },
    onDestroy: function(){
        NotificationMgr.positions.splice(this.pos);
        this.superclass.onDestroy.call(this);
    },
    cancelHiding: function(){
        this.addClass('fixed');
        if(this.autoDestroy) {
            this.task.cancel();
        }
        this.animHide();
    },
    afterShow: function(){          
        Ext.fly(this.body.dom).on('click', this.cancelHiding, this);
        if(this.autoDestroy) {
            this.task.delay(this.hideDelay || 2000);
        }           
        this.pos = 0;      
        while(NotificationMgr.positions.indexOf(this.pos)>-1)
            this.pos++;
        NotificationMgr.positions.push(this.pos);
        this.setSize(200,100);
        this.el.alignTo(document, "br-br", [ -20, -20-((this.getHeight()+10)*this.pos) ]);
        this.el.slideIn();
        this.superclass.afterShow.call(this);  
    },
    animHide: function(){
        NotificationMgr.positions.splice(this.pos);
        this.el.slideOut(); 
    },
    focus: Ext.emptyFn

});
