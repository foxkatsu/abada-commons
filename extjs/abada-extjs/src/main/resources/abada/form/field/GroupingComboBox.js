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


Ext.define('Abada.form.field.GroupingComboBox', {
    extend: 'Ext.form.field.ComboBox',
    requires: ['Abada.view.GroupingList'],
    alias: ['widget.groupingcombobox', 'widget.groupingcombo'],
    
    initComponent: function() {
        var me = this;
        if (!me.displayTpl) {
            var display = [],
            tpl = '<tpl for=".">{0}</tpl>';
            if (Ext.isArray(me.groupField)) {
                for (var i = 0; i < me.groupField.length; i++) {
                    display.push('{[values["' + me.groupField[i] + '"]]}');
                }
            }
            else {
                display.push('{[values["' + me.groupField + '"]]}');
            }
            display.push('{[values["' + me.displayField + '"]]}');
            me.displayTpl = Ext.String.format(tpl, display.join(this.displaySeparator || ' '));
        }
        me.callParent();
    },
    
    createPicker: function() {
        var me = this,
        picker,
        menuCls = Ext.baseCSSPrefix + 'menu',
        opts = Ext.apply({
            selModel: {
                mode: me.multiSelect ? 'SIMPLE' : 'SINGLE'
            },
            floating: true,
            hidden: true,
            ownerCt: me.ownerCt,
            cls: me.el.up('.' + menuCls) ? menuCls : '',
            store: me.store,
            groupField: me.groupField,
            displayField: me.displayField,
            focusOnToFront: false,
            pageSize: me.pageSize
        }, me.listConfig, me.defaultListConfig);
		
        //picker = me.picker = Ext.create('Ext.view.BoundList', opts);
        picker = me.picker = Ext.create('Abada.view.GroupingList', opts);

        me.mon(picker, {
            itemclick: me.onItemClick,
            refresh: me.onListRefresh,
            scope: me
        });

        me.mon(picker.getSelectionModel(), 'selectionchange', me.onListSelectionChange, me);

        return picker;
    }
});