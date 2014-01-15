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

/***
 * ComboBox que se puede deseleccionar un valor
 */
Ext.define('Katsu.form.field.ComboBoxDeSelect',{
    requires: ['Katsu.data.ModelCombo','Katsu.Ajax','Katsu.data.JsonStore'],
    extend:'Ext.form.field.ComboBox',  
    alias:'widget.comboboxdeselect',
    config:{
        fieldLabel:'',
        editable:true,
        displayField:'value',
        valueField:'id',        
        readonly:false,
        noSelection: null,
        selectedValue:null
       
   
    },
    
    constructor:function(config){
        this.initConfig(config);
         this.emptyText=config.emptyText;     
        if (config.url){
            this.store=Ext.create('Katsu.data.JsonStore',{
                fields:[{
                    name:'id'
                },{
                    name:'value'
                }],
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
        this.callParent([config]);
        return this;
    },
    loadStore:function(){
        if (this.store)
            this.store.load();
    },
    initComponent : function(){
        this.store.on('load',function(){
            if(this.noSelection && this.store){
                var add = Ext.ModelManager.create({
                    id : ' ',
                    value  : this.noSelection
  
                }, 'Katsu.data.ModelCombo');
               
                if(!this.store.getById('')){
                    this.store.add(add);
                }
            }
            if (this.selectedValue)
                this.setValue(this.selectedValue);
        },this);
        this.callParent();
    }
});


/*


ComboBoxDeSelect = Ext.extend(Ext.form.ComboBox, {
    displayField:'value',
    valueField:'id',
    typeAhead: true,
    editable:false,
    mode: 'local',
    triggerAction: 'all',
    //anchor:'95%',
    selectOnFocus:true,
    noSelection:null,
    selectedValue:null,
    constructor:function(cfg){
        Ext.apply(this, cfg);
        ComboBoxDeSelect.superclass.constructor.call(this,cfg);
    },
    initComponent : function(){
        this.store.on('load',function(){
            if(this.noSelection && this.store){
                var Record = Ext.data.Record.create([ // creates a subclass of Ext.data.Record
                {
                    name: 'id'
                },{
                    name: 'value'
                }]);
                var NewRecord = new Record({
                    id: '',
                    value: this.noSelection
                });
                if(!this.getStore().getById('')){
                    this.getStore().addSorted(NewRecord);
                }
            }
            if (this.selectedValue)
                this.setValue(this.selectedValue);
        },this);
    }
});

*/