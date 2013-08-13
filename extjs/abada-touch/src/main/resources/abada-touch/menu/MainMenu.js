/*
 * #%L
 * Abada Sencha Touch
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
Ext.define('Abada.menu.MainMenu', {
    requires: ['Abada.Ajax'],
    extend: 'Ext.dataview.NestedList',
    xtype: 'abada.nestedlist',
    config: {
        url: undefined,
        method: 'POST',
        autoLoadData: true,
        fullscreen: true,
        toolbar: {
            docked: 'top',
            xtype: 'titlebar',
            ui: 'light',
            inline: true,
            height: 30
        },
        displayField: 'text'
    },
    getTitleTextTpl: function() {
        return '<div>' +
                '<tpl if="icon!=null"><div style=\"float:left;\"><img alt=\" \" src=\"{icon}\" style=\"height:20px;\" /></div></tpl>' +
                '<div style=\"float:left;\">{' + this.getDisplayField() + '}</div>' +
                '<div style=\"clear: both\" />' +
                '</div>';
    },
    getItemTextTpl: function() {
        return '<div>' +
                '<tpl if="icon!=null"><div style=\"float:left;\"><img alt=\" \" src=\"{icon}\" style=\"height:20px;\" /></div></tpl>' +
                '<div style=\"float:left;\">{' + this.getDisplayField() + '}</div>' +
                '<div style=\"clear: both\" />' +
                '</div>';
    },
    constructor: function(config) {
        this.callParent(arguments);
        Abada.Ajax.requestJsonData({
            url: config.url,
            scope: this,
            method: config.method,
            success: function(json) {
                Ext.define('Abada.menu.Model', {extend: 'Ext.data.Model',
                    config: {
                        fields: ['text', 'icon', 'url']
                    }});

                var store = {
                    model: 'Abada.menu.Model',
                    defaultRootProperty: 'items',
                    data: {
                        items: []
                    }
                };

                json.data.sort(function(a, b) {
                    return parseInt(a.order) - parseInt(b.order)
                });
                for (var y = 0; y < json.data.length; y++) {
                    if (json.data[y].childs) {
                        //this.addMainMenuParent(json.data[y], this);
                        this.convertData2Store(json.data[y], store.data.items);
                    }
                }
                var storeAux = Ext.create('Ext.data.TreeStore', store);
                this.setStore(storeAux);
                this.updateStore(storeAux);
            },
            failure: function() {
            }
        });
        this.addListener('leafitemtap',this.onMenuItemTab,this);
    },
    convertData2Store: function(item, items) {
        var result = {};
        result.text = item.text;
        result.icon = item.icon;
        if (item && item.childs && item.childs.length > 0) {
            result.items = [];
            for (var y = 0; y < item.childs.length; y++) {
                this.convertData2Store(item.childs[y], result.items);
            }
        } else {
            result.url = item.url;
            result.leaf = true;
        }
        items.push(result);
    },
    onMenuItemTab:function(me,list,index,target,record){
        window.location=record.data.url;
    }
});

