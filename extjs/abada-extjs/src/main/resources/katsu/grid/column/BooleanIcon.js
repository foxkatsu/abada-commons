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
 *Columna para representar valores booleanos mediante una imagen
 *true=se muestra imagen que este en okImage
 *false= se muestra imagen que este en koImage
 *
 *salvo que se active el atributo inverse que cambia el orden de las imagenes
 */

Ext.define('Katsu.grid.column.BooleanIcon',{
    extend:'Ext.grid.column.Template',
    alias:'widget.booleaniconcolumn',
    config:{
        okIcon:undefined,
        koIcon:undefined,
        inverseLogic:false
    },
    constructor:function(config){
        this.initConfig(config);
        var tplTemplate;
        if (this.inverse){
            tplTemplate = '<tpl if="'+this.dataIndex+' == true" ><img src="'+this.koImage+'" alt="true"/></tpl>'+
            '<tpl if="'+this.dataIndex+' == false" ><img src="'+this.okImage+'" alt="false" /></tpl>';
        }else{
            tplTemplate = '<tpl if="'+this.dataIndex+' == true" ><img src="'+this.okImage+'" alt="true"/></tpl>'+
            '<tpl if="'+this.dataIndex+' == false" ><img src="'+this.koImage+'" alt="false" /></tpl>';
        }
        this.tpl=new Ext.XTemplate(tplTemplate);
        this.callParent([config]);
    }
});
    
/*BooleanColumn = Ext.extend(Ext.grid.TemplateColumn,{
    inverse:false,
    okImage:'',
    koImage:'',
    constructor:function(cfg){
        Ext.apply(this, cfg);
        var tplTemplate;
        if (this.inverse){
            tplTemplate = '<tpl if="'+this.dataIndex+' == true" ><img src="'+this.koImage+'" alt="true"/></tpl>'+
            '<tpl if="'+this.dataIndex+' == false" ><img src="'+this.okImage+'" alt="false" /></tpl>';
        }else{
            tplTemplate = '<tpl if="'+this.dataIndex+' == true" ><img src="'+this.okImage+'" alt="true"/></tpl>'+
            '<tpl if="'+this.dataIndex+' == false" ><img src="'+this.koImage+'" alt="false" /></tpl>';
        }
        this.tpl=new Ext.XTemplate(tplTemplate);
        BooleanColumn.superclass.constructor.call(this,cfg);
    }
});*/
