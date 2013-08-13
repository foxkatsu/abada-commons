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
Ext.define('Abada.menu.MainMenu',{
    requires:['Abada.Ajax','Ext.menu.Item'],
    extend:'Ext.panel.Panel',    
    frame:true,    
    url:undefined,
    method:'POST',   
    autoLoadData:false,
    initComponent:function(){                                        
        this.callParent();
        if (this.autoLoadData)
            this.loadData();
    },
    loadData:function(){
        this.removeAll();
        Abada.Ajax.requestJsonData({
            url: this.url,
            scope:this,
            method:this.method,
            success: function(json) {                                 
                for(var y=0;y<json.data.length;y++){
                    if(json.data[y].childs){
                        this.add(this.setMainMenuParent(json.data[y]));
                    }                    
                }                                
            },
            failure:function(){                
            }
        });
    },
    setMainMenuParent:function(parent){       
        return Ext.create('Ext.panel.Panel',{            
            title:parent.text,
            icon:parent.icon,
            frame:true,
            collapsible:true,
            titleCollapse: true,
            items:this.setMainMenuChilds(parent.childs)
        });        
    },
    setMainMenuChilds:function(childs){
        var childsArray=new Array();
        if(childs){
            childs.sort(function(a,b) {
                return parseInt(a.order) - parseInt(b.order)
            } );
         
            for(var x=0; x<childs.length;x++){            
                childsArray[x]= Ext.create('Ext.menu.Item',{
                    text:childs[x].text,
                    style: {
                        marginBottom: '9px'
                    },
                    icon:childs[x].icon,
                    href:childs[x].url
                });
            }
        }
        return childsArray;
    }
});

