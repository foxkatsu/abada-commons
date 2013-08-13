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


/**
 * Columna para representar los valores de una lista
 */
Ext.define('Abada.grid.column.List',{
    extend:'Ext.grid.column.Template',
    alias:'widget.listcolumn',
    constructor:function(config){
        this.setConfig(config);
        tplTemplate = '<tpl if="'+this.dataIndex+'" >'+
        '<tpl for="'+this.dataIndex+'">'+
        '<p>{.}</p>'+
        '</tpl>'+
        '</tpl>';
        this.tpl=new Ext.XTemplate(tplTemplate);
        this.callParent([config]);
    }
});

/*ListColumn = Ext.extend(Ext.grid.TemplateColumn,{
    constructor:function(cfg){
        Ext.apply(this, cfg);
        tplTemplate = '<tpl if="'+this.dataIndex+'" >'+
        '<tpl for="'+this.dataIndex+'">'+
        '<p>{.}</p>'+
        '</tpl>'+
        '</tpl>';
        this.tpl=new Ext.XTemplate(tplTemplate);
        ListColumn.superclass.constructor.call(this,cfg);
    }
});*/