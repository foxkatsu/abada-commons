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


Ext.define('Abada.view.GroupingList', {
    extend: 'Ext.view.View',
    alias: 'widget.groupinlist',
    requires: ['Ext.layout.component.BoundList', 'Ext.toolbar.Paging'],
	
    pageSize: 0,
	
    autoScroll: true,
    baseCls: Ext.baseCSSPrefix + 'boundlist',
    listItemCls: '',
    shadow: false,
    trackOver: true,
    refreshed: 0,

    ariaRole: 'listbox',

    componentLayout: 'boundlist',

    renderTpl: ['<div class="list-ct"></div>'],

    initComponent: function() {
        var me = this,
        baseCls = me.baseCls,
        itemCls = baseCls + '-item';
        me.itemCls = itemCls;
        me.selectedItemCls = baseCls + '-selected';
        me.overItemCls = baseCls + '-item-over';
        me.itemSelector = "." + itemCls;

        if (me.floating) {
            me.addCls(baseCls + '-floating');
        }
		
        var tpl = [
        '<ul>',
        '<tpl for=".">'
        ];
        
        var padding = 1;
        
        if (Ext.isArray(me.groupField)) {        	
            padding = me.groupField.length;        	
            for (var i = 0; i < me.groupField.length; i++) {
                var checking = '';
                for(var j=i-1;j>=0;j--) {
                    checking += ' || parent[xindex - 2][\'' + me.groupField[j] + '\'] != values[\'' + me.groupField[j] + '\']';
                }
                tpl.push(
                    '<tpl if="xindex == 1 || parent[xindex - 2][\'' + me.groupField[i] + '\'] != values[\'' + me.groupField[i] + '\']'+checking+'">',
                    '<li class="x-combo-list-group" style="padding-left:' + (i * 16) + 'px;">{[values["' + me.groupField[i] + '"]]}</li>',
                    '</tpl>'
                    );
            }
        }        
        else {        	
            tpl.push(
                '<tpl if="xindex == 1 || parent[xindex - 2][\'' + me.groupField + '\'] != values[\'' + me.groupField + '\']">',
                '<li class="x-combo-list-group">{[values["' + me.groupField + '"]]}</li>',
                '</tpl>'
                );
        }        
        tpl.push(
            '<li role="option" class="' + itemCls + '" style="padding-left:' + (padding * 16) + 'px;">{[values["' + me.displayField + '"]]}</li>',
            '</tpl>',
            '</ul>'
            );
        
        me.tpl = Ext.create('Ext.XTemplate', tpl);

        if (me.pageSize) {
            me.pagingToolbar = me.createPagingToolbar();
        }

        me.callParent();

        Ext.applyIf(me.renderSelectors, {
            listEl: '.list-ct'
        });
    },

    createPagingToolbar: function() {
        return Ext.widget('pagingtoolbar', {
            pageSize: this.pageSize,
            store: this.store,
            border: false
        });
    },

    onRender: function() {
        var me = this,
        toolbar = me.pagingToolbar;
        me.callParent(arguments);
        if (toolbar) {
            toolbar.render(me.el);
        }
    },

    bindStore : function(store, initial) {
        var me = this,
        toolbar = me.pagingToolbar;
        me.callParent(arguments);
        if (toolbar) {
            toolbar.bindStore(store, initial);
        }
    },

    getTargetEl: function() {
        return this.listEl || this.el;
    },

    getInnerTpl: function(displayField) {
        return '{' + displayField + '}';
    },

    refresh: function() {
        var me = this;
        me.callParent();
        if (me.isVisible()) {
            me.refreshed++;
            me.doComponentLayout();
            me.refreshed--;
        }
    },
    
    initAria: function() {
        this.callParent();
        
        var selModel = this.getSelectionModel(),
        mode     = selModel.getSelectionMode(),
        actionEl = this.getActionEl();
        
        if (mode !== 'SINGLE') {
            actionEl.dom.setAttribute('aria-multiselectable', true);
        }
    },

    onDestroy: function() {
        Ext.destroyMembers(this, 'pagingToolbar', 'listEl');
        this.callParent();
    }
});