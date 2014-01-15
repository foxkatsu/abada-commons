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
Ext.define('Katsu.menu.HorizontalMainMenu', {
    requires: ['Katsu.Ajax', 'Ext.menu.*', 'Ext.button.Button', 'Ext.button.Split'],
    extend: 'Ext.toolbar.Toolbar',
    url: undefined,
    method: 'POST',
    autoLoadData: false,
    initComponent: function() {
        this.callParent();
        if (this.autoLoadData)
            this.loadData();
    },
    loadData: function() {
        this.removeAll();
        Katsu.Ajax.requestJsonData({
            url: this.url,
            scope: this,
            method: this.method,
            success: function(json) {
                json.data.sort(function(a, b) {
                    return parseInt(a.order) - parseInt(b.order)
                });
                for (var y = 0; y < json.data.length; y++) {
                    if (json.data[y].childs) {
                        this.addMainMenuParent(json.data[y], this);
                    }
                }
            },
            failure: function() {
            }
        });
    },
    addMainMenuParent: function(parent, container) {
        var menu = this.setSubMenuChilds(parent);
        container.add(Ext.create('Ext.button.Button', {
            menu: menu,
            text: parent.text,
            icon: parent.icon
        }));
    },
    addSubMenuParent: function(parent, container) {
        var menu = this.setSubMenuChilds(parent);
        if (menu) {
            container.add(Ext.create('Ext.menu.Item', {
                menu: menu,
                text: parent.text,
                icon: parent.icon
            }));
        } else {
            container.add(Ext.create('Ext.menu.Item', {
                text: parent.text,
                icon: parent.icon,
                href:parent.url
            }));
        }
    },
    setSubMenuChilds: function(parent) {
        if (parent.childs && parent.childs.length > 0) {
            var result = Ext.create('Ext.menu.Menu');
            parent.childs.sort(function(a, b) {
                return parseInt(a.order) - parseInt(b.order)
            });

            for (var x = 0; x < parent.childs.length; x++) {
                this.addSubMenuParent(parent.childs[x], result);
            }
            return result;
        }
        return undefined;
    }
});

