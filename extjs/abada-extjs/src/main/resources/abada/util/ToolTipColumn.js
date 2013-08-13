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
var n=new Array();
Ext.define('Abada.util.ToolTipColumn',{
    extend:'Ext.util.Observable',  
    constructor:function(config){
        Ext.apply(this, config);
        this.columnsNumber=config.columnsNumber;
        
        this.callParent([config]);  
        return this;      
    },
    init:function(grid){
        this.grid=grid;        
        this.grid.on('itemmouseenter', this.onMouseOver,this);
    },
    onMouseOver:function(t,r,i,x,e){   
       
        e.stopEvent(e);
        // var row = t.getTarget('.x-grid-row');
        var col = e.getTarget('.x-grid-cell');
        
        if(col)
        {
            if(this.contains(col.cellIndex))
            {
                data=r;
                //    data=this.grid.getView().getCell(row.rowIndex,col.cellIndex);
                if (data){
                    // this.showMessage(data.textContent);
                    this.showMessage(data.getId());
                }
            }
        }
    },
    contains:function(value){
        if (this.columnsNumber){
            for (i=0;i<this.columnsNumber.length;i++){
                if (value == this.columnsNumber[i])
                    return true;
            }
            return false;
        }
        return true;
    },
    showMessage:function(htmlData){        
        if(n.length>3){
            n[0].close();
            n.splice(0);
        }
        this.notificationBox=Ext.create('Abada.window.Notification', {
            autoDestroy:true,
            hideDelay:10000
        });
        this.notificationBox.on('hide', this.onDestroyToolTip,this);
        n.push(this.notificationBox);
        this.notificationBox.setMessage(htmlData);
        this.notificationBox.show(n[n.length-1]);
    },
    onDestroyToolTip:function(){
        n[0].close();
        n.splice(0);
    }
});

