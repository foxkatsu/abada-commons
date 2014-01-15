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

Ext.define('Katsu.data.JsonStore',{
    requires: ['Ext.data.proxy.Ajax','Ext.data.reader.Json'],
    extend:'Ext.data.Store',
    alias:'store.jsonstore',
    config:{
        root:'data',
        idProperty:'id',
        extraParams:undefined,
        proxyConfigs:undefined
    },
    constructor:function(config){
        //if (config){
        this.setConfig(config);
        var aux={
            url:config.url,
            reader: {
                type:'json',
                root:config.root,
                idProperty:config.idProperty
            },
            extraParams:config.extraParams
        };
        aux=Ext.apply(aux,config.proxyConfigs);
        this.proxy=new Ext.data.proxy.Ajax(aux);
        this.callParent([config]);
        //}        
        return this;
    },
    addParameters:function(params){
        Ext.apply(this.proxy.extraParams,params);
    }
});
