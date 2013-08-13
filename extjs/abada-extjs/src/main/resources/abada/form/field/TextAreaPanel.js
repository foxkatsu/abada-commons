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
 * Date que se instancia el dia de hoy
 */
Ext.define('Abada.form.field.TextAreaPanel',{
    requires: ['Ext.form.field.TextArea'],
    extend:'Ext.form.Panel',
    config:{
        collapsible:true,
        titleCollapse: true,
        plain:true,
        border:false,
        collapsed:true,
        allowBlank:true,
        preventMark:false,
        maxLength:1204
    },
    constructor:function(config){
        this.initConfig(config);
        this.callParent([config]);
      
    },
    initComponent:function(){                
        this.taText=new Ext.form.TextArea({            
            allowBlank:this.allowBlank,
            preventMark:this.preventMark,
            width:this.width,
            maxLength:this.maxLength
        });
        this.items=[this.taText];

        this.callParent();
    },
    getValue:function(){
        return this.taText.getValue();
    },
    setValue:function(text){
        this.taText.setValue(text);
    },
    isValid:function(preventMark){
        return this.taText.isValid(preventMark);
    }
});


/*/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * TextAreaPanel, panel con un textarea en un interior
 *//*
TextAreaPanel=Ext.extend(Ext.Panel,{
    collapsible:true,
    titleCollapse: true,
    plain:true,
    border:false,
    collapsed:true,
    allowBlank:true,
    preventMark:false,
    maxLength:1204,
    initComponent:function(){                
        this.taText=new Ext.form.TextArea({            
            allowBlank:this.allowBlank,
            preventMark:this.preventMark,
            width:this.width,
            maxLength:this.maxLength
        });
        this.items=[this.taText];

        TextAreaPanel.superclass.initComponent.call(this);
    },
    getValue:function(){
        return this.taText.getValue();
    },
    setValue:function(text){
        this.taText.setValue(text);
    },
    isValid:function(preventMark){
        return this.taText.isValid(preventMark);
    }
});

;*/