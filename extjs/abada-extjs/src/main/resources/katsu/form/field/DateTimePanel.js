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
 * Panel para seleccionar una fecha y hora 
 */
Ext.define('Katsu.form.field.DateTimePanel',{
     requires: ['Katsu.form.field.Date','Katsu.form.field.Time'],
    extend:'Ext.Panel',
    config:{
        fallowBlank:true,
        preventMark:false,
        plain:true,
        layout:'hbox'
    },
    initComponent:function(){
        this.tfDate=new Katsu.form.field.Date({
            width:100,
            allowBlank:this.allowBlank,
            preventMark:this.preventMark
        });
        this.tfTime=new Katsu.form.field.Time({
            width:100,
            allowBlank:this.allowBlank,
            preventMark:this.preventMark
        });
        this.items=[this.tfDate,this.tfTime];

        if (this.value){
            this.tfDate.setValue(this.value);
            
            this.tfTime.setValue(Ext.util.Format.date(this.value, 'H:i'));
        }

        this.callParent();
    },
    getValue:function(){
        function parseValue(date,stringtime){
            aux=stringtime.split(':');
            if (aux.length==2){
                date.setHours(parseInt(aux[0]), parseInt(aux[1]), 0, 0);
                return date;
            }else{
                return undefined;
            }
        }

        if (this.isValid()){
            return parseValue(this.tfDate.getValue(),this.tfTime.getValue());
        }
    },
    isValid:function(preventMark){
        return this.tfDate.isValid(preventMark) && this.tfTime.isValid(preventMark);
    }
});
/*
/**
 * Panel para seleccionar una fecha y hora 
 *//*
DateTimePanel=Ext.extend(Ext.Panel,{
    allowBlank:true,
    preventMark:false,
    plain:true,
    layout:'hbox',
    initComponent:function(){
        this.tfDate=new DateField({
            width:100,
            allowBlank:this.allowBlank,
            preventMark:this.preventMark
        });
        this.tfTime=new TimeField({
            width:100,
            allowBlank:this.allowBlank,
            preventMark:this.preventMark
        });
        this.items=[this.tfDate,this.tfTime];

        if (this.value){
            this.tfDate.setValue(this.value);
            
            this.tfTime.setValue(Ext.util.Format.date(this.value, 'H:i'));
        }

        DateTimePanel.superclass.initComponent.call(this);
    },
    getValue:function(){
        function parseValue(date,stringtime){
            aux=stringtime.split(':');
            if (aux.length==2){
                date.setHours(parseInt(aux[0]), parseInt(aux[1]), 0, 0);
                return date;
            }else{
                return undefined;
            }
        }

        if (this.isValid()){
            return parseValue(this.tfDate.getValue(),this.tfTime.getValue());
        }
    },
    isValid:function(preventMark){
        return this.tfDate.isValid(preventMark) && this.tfTime.isValid(preventMark);
    }
});*/