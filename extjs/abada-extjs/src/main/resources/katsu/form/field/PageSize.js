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

/** 
* Katsu.form.field.PageSize
*/  
Ext.define('Katsu.form.field.PageSize', {  
    extend      : 'Ext.form.field.ComboBox',  
    alias       : 'plugin.pagesize',  
    beforeText  : 'Nº',  
    afterText   : '',  
    mode        : 'local',  
    displayField: 'text',  
    valueField  : 'value',  
    allowBlank  : false,  
    triggerAction: 'all',  
    width       : 50, 
    maskRe      : /[0-9]/,      
    /** 
    * initialize the paging combo after the pagebar is randered 
    */  
    init: function(paging) {  
        paging.on('afterrender', this.onInitView, this);  
    },  
    /** 
    * create a local store for availabe range of pages 
    */  
    store: new Ext.data.SimpleStore({ 
        //pageSize: 10,
        fields: ['text', 'value'],  
        data: [['5', 5], ['10', 10], ['20', 20], ['50', 50], ['100', 100], ['250', 250]]  
    }),      
    /** 
    * assing the select and specialkey events for the combobox  
    * after the pagebar is rendered. 
    */  
    onInitView: function(paging) {  
        this.setValue(paging.store.pageSize);
        //this.setValue(10);
        paging.add('-', this.beforeText, this, this.afterText);  
        this.on('select', this.onPageSizeChanged, paging);  
        this.on('specialkey', function(combo, e) {  
            if(13 === e.getKey()) {  
                this.onPageSizeChanged.call(paging, this);          
            }  
        });  
    },  
    /** 
    * refresh the page when the value is changed 
    */  
    onPageSizeChanged: function(combo) {  
        this.store.pageSize = parseInt(combo.getRawValue());  
        this.doRefresh();  
    }  
});