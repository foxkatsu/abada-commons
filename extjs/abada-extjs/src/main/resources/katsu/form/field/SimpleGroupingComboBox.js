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


Ext.define('Katsu.form.field.SimpleGroupingComboBox', {
    extend: 'Katsu.form.field.GroupingComboBox',    
    alias: ['widget.simplegroupingcombobox', 'widget.simplegroupingcombo'],
    config:{
        displayField:'value',
        valueField:'id',        
        editable:false,
        mode: 'local',
        triggerAction: 'all',
        groupField:['groupingField']
    },
    constructor:function(config){
        this.initConfig(config);
                
        if (config.url){
            this.store=Ext.create('Katsu.data.JsonStore',{
                fields:[{
                    name:'id'
                },{
                    name:'value'
                },{
                    name:'groupingField'
                }],
                url:config.url,
                root:'data',
                idProperty:'id',                
                scope:this,
                sorters:[{
                    property:'groupingField',
                    direction:'ASC'
                }],                
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