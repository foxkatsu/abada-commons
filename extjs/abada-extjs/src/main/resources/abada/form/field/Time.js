/*
 * #%L
 * Abada ExtJs
 * %%
 * Copyright (C) 2013 Abada Servicios Desarrollo (investigacion@abadasoft.com)
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

Ext.define('Abada.form.field.Time',{
    extend:'Ext.form.field.Time',
    config:{
        format:'H:i',
        hideLabel:false,
        editable:false,
        allowBlank:false,
        increment: 60
    },
    constructor:function(config){
        this.initConfig(config);
        this.callParent([config]);
    }
});

/*TimeField=Ext.extend(Ext.form.TimeField,{
    format:'H:i',
    hideLabel:false,
    editable:false,
    allowBlank:false,
    increment: 60,
    constructor:function(cfg){
        Ext.apply(this, cfg);
        TimeField.superclass.constructor.call(this,cfg);
        //pongo hora actual
        this.onLoadedStore(this.getStore());
    },
    onLoadedStore:function(store){
        data={            
            field1: '23:59'
        };
        record=new store.recordType(data);
        store.add(record);

        this.setValue(new Date());
    }
});*/