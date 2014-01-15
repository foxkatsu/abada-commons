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


/**
 * Date que se instancia el dia de hoy
 */
Ext.define('Katsu.form.field.Date',{
    extend:'Ext.form.field.Date',
    config:{
        format:'d/m/Y',
        hideLabel:false,
        editable:false,
        allowBlank:false
    },
    constructor:function(config){
        this.initConfig(config);
        this.callParent([config]);
        if (!this.value)
            this.setValue(new Date());
    }
});

/*DateField=Ext.extend(Ext.form.DateField,{
    format:'d/m/Y',
    hideLabel:false,
    editable:false,
    allowBlank:false,
    constructor:function(cfg){
        Ext.apply(this, cfg);
        DateField.superclass.constructor.call(this,cfg);
        if (!this.value)
            this.setValue(new Date());
    }
});*/