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
 * ComboBox
 */
Ext.define('Katsu.form.field.ComboBox',{
    requires: ['Katsu.Ajax','Katsu.data.JsonStore'],
    extend:'Ext.form.field.ComboBox',    
    config:{
        displayField:'value',
        valueField:'id',        
        editable:false,
        mode: 'local',
        triggerAction: 'all'        
    },
    
    constructor:function(config){
        this.initConfig(config);
                
        if (config.url){
            this.store=Ext.create('Katsu.data.JsonStore',{
                fields:[{name:'id'},{name:'value'}],
                url:config.url,
                root:'data',
                idProperty:'id',                
                scope:this,
                listeners:{
                    load:function(store,records, successful,operation){
                        store.scope.fireEvent('load',store.scope,records, successful, operation);
                    }
                }
            });
        }
        this.addEvents('load');
        this.callParent([config]);//ComboBox.superclass.constructor.call(this,cfg);
        return this;
    },
    loadStore:function(){
        if (this.store)
            this.store.load();
    },
    getStore:function(){
        return this.store;
    }
});

/*ComboBox = Ext.extend(Ext.form.ComboBox,{
    displayField:'value',
    valueField:'id',
    typeAhead: true,
    editable:false,
    mode: 'local',
    triggerAction: 'all',
    //anchor:'95%',
    selectOnFocus:true,
    constructor:function(cfg){
        Ext.apply(this, cfg);

        if (this.url){
            this.store=new Ext.data.JsonStore({
                url:this.url,
                root:'data',
                total:'total',
                autoDestroy: true,
                idProperty:'id',
                scope:this,
                fields:[
                {
                    name:'id'
                },
                {
                    name:'value'
                }],
                listeners:{
                    load:function(store,records, options){
                        store.scope.fireEvent('load',store.scope,records, options);
                    }
                }
            });
        }
        this.addEvents('load');
        ComboBox.superclass.constructor.call(this,cfg);
    },
    load:function(){
        if (this.store)
            this.store.load();
    }
});*/
