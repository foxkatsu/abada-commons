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
Ext.define('Katsu.toolbar.ToolbarInsertUpdateDelete', {
    requires: ['Ext.button.Button'],
    extend:'Ext.toolbar.Toolbar',
    config:{
       
    },
   
    initComponent : function(config){

        this.items=[Ext.create('Ext.button.Button',{
            text: 'Insertar',
            id:'insertar',
            icon:getRelativeURI('images/custom/add.gif'),
            scope:this,
            handler : this.submitInsert
        }),Ext.create('Ext.button.Button',{
            text:'Modificar',
            id:'modificar',
            icon:getRelativeURI('images/custom/changestatus.png'),
            scope:this,
            handler: this.submitUpdate
        }),Ext.create('Ext.button.Button',{
            text:'Borrar',
            id:'Borrar',
            icon:getRelativeURI('images/custom/delete.gif'),
            scope:this,
            handler: this.submitDelete
        })];

        this.addEvents('submitInsert','submitUpdate','submitDelete');

        this.callParent([config]);

    },/*Evento que dispara submitInsert*/
    submitInsert:function(){
        this.fireEvent('submitInsert');
    },/*Evento que dispara submitUpdate*/
    submitUpdate:function(){
        this.fireEvent('submitUpdate');
    },/*Evento que dispara submitDelete*/
    submitDelete:function(){
        this.fireEvent('submitDelete');
    }



});

