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


/***
 * Columna que concatena los valores de los nombres de las columnas que aparezca en dataIndex,
 * siendo dataIndex un array
 */
Ext.define('Katsu.grid.column.Append',{
    extend:'Ext.grid.column.Template',
    alias:'widget.appendcolumn',
    constructor:function(config){
        this.setConfig(config);
        var tplTemplate=this.generateTemplate(this.dataIndex);
        this.tpl=new Ext.XTemplate(tplTemplate);
        this.callParent([config]);
    },
    generateTemplate:function(array){
        var result='';
        for (i=0;i<array.length;i++){
            result+='<tpl if="'+array[i]+'" >{'+array[i]+'} </tpl>';
        }
        return result;
    }
});

/*AppendColumn = Ext.extend(Ext.grid.TemplateColumn,{
    constructor:function(cfg){
        Ext.apply(this, cfg);
        var tplTemplate=this.generateTemplate(this.dataIndex);
        this.tpl=new Ext.XTemplate(tplTemplate);
        AppendColumn.superclass.constructor.call(this,cfg);
    },
    generateTemplate:function(array){
        var result='';
        for (i=0;i<array.length;i++){
            result+='<tpl if="'+array[i]+'" >{'+array[i]+'} </tpl>';
        }
        return result;
    }
});*/