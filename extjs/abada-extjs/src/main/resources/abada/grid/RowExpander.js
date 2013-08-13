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


/****
 * Expander que hace una llamada Ajax a una url, espera una respuesta
 * igual que la que espera un jsonstore
 */

Ext.define('Abada.grid.RowExpander',{
    requires: ['Abada.Ajax','Ext.JSON','Ext.grid.feature.RowBody','Ext.grid.feature.RowWrap'],
    alias:'plugin.abada.rowexpander',
    extend:'Ext.grid.plugin.RowExpander',
    config:{
        url:undefined,
        method:'GET',
        searchFields:[]
    },
    constructor:function(config){  
        this.initConfig(config);
        this.callParent(arguments);
        this.tpl=Ext.create('Ext.XTemplate', this.rowBodyTpl);
    },
    getURLData:function(row,nextBd,record,me,isCollapsed,view,addOrRemoveCls,rowIdx){
        Abada.Ajax.requestJsonObject({
            url:getRelativeServerURI(this.url,this.generateURLParams(record)),
            scope:this,
            method:this.method,
            params:{
                search:Ext.JSON.encode(this.generateSearchParam(record))
            },
            failure:function(){                
            },
            success:function(data){
                if (data){
                    var xgb=nextBd.first().first();
                    var html=this.tpl.applyTemplate(data);
                    xgb.update(html);
                    me.onEndToggleRow(row,nextBd,record,me,isCollapsed,view,addOrRemoveCls,rowIdx);
                }
            }
        });        
    },
    onEndToggleRow:function(row,nextBd,record,me,isCollapsed,view,addOrRemoveCls,rowIdx){
//        row.removeCls(this.rowCollapsedCls);
//        nextBd.removeCls(this.rowBodyHiddenCls);
//        this.recordsExpanded[record.internalId] = true;
//        this.view.fireEvent('expandbody', rowNode, record, nextBd.dom);
        
        var fireView,ownerLock,rowHeight;
        
        // Suspend layouts because of possible TWO views having their height change
        Ext.suspendLayouts();
        row[addOrRemoveCls](me.rowCollapsedCls);
        Ext.fly(nextBd)[addOrRemoveCls](me.rowBodyHiddenCls);
        me.recordsExpanded[record.internalId] = isCollapsed;
        view.refreshSize();

        // Sync the height and class of the row on the locked side
        if (me.grid.ownerLockable) {
            ownerLock = me.grid.ownerLockable;
            fireView = ownerLock.getView();
            view = ownerLock.lockedGrid.view;
            rowHeight = row.getHeight();
            row = Ext.fly(view.getNode(rowIdx), '_rowExpander');
            row.setHeight(rowHeight);
            row[addOrRemoveCls](me.rowCollapsedCls);
            view.refreshSize();
        } else {
            fireView = view;
        }
        fireView.fireEvent(isCollapsed ? 'expandbody' : 'collapsebody', row.dom, record, nextBd);
        // Coalesce laying out due to view size changes
        Ext.resumeLayouts(true);
    },
    toggleRow: function(rowIdx,record) {
//        var rowNode = this.view.getNode(rowIdx),
//        row = Ext.get(rowNode),
//        nextBd = Ext.get(row).down(this.rowBodyTrSelector),
//        record = this.view.getRecord(rowNode);
//        
//        if (row.hasCls(this.rowCollapsedCls)) {
//            if (this.url)
//                this.getURLData(row,nextBd,record,rowNode);            
//            else
//                this.onSuccessURLData(row,nextBd,record,rowNode);
//        } else {
//            row.addCls(this.rowCollapsedCls);
//            nextBd.addCls(this.rowBodyHiddenCls);
//            this.recordsExpanded[record.internalId] = false;
//            this.view.fireEvent('collapsebody', rowNode, record, nextBd.dom);
//        }
//        this.view.up('gridpanel').invalidateScroller();
        var me = this,
            view = me.view,
            rowNode = view.getNode(rowIdx),
            row = Ext.fly(rowNode, '_rowExpander'),
            nextBd = Ext.get(row).down(this.rowBodyTrSelector)
            isCollapsed = row.hasCls(me.rowCollapsedCls),
            addOrRemoveCls = isCollapsed ? 'removeCls' : 'addCls';  
    
        if (isCollapsed){
            if (this.url){
                me.getURLData(row,nextBd,record,me,isCollapsed,view,addOrRemoveCls,rowIdx);                
            }else{
                me.onEndToggleRow(row,nextBd,record,me,isCollapsed,view,addOrRemoveCls,rowIdx);
            }                
        }else{
            me.onEndToggleRow(row,nextBd,record,me,isCollapsed,view,addOrRemoveCls,rowIdx);
        }
    },
    generateSearchParam:function(record){
        var result=[];
        for (var i=0;i<this.searchFields.length;i++){
            result.push({
                value:record.get(this.searchFields[i]),
                key:this.searchFields[i]
            });
        }
        return result;
    },
    generateURLParams:function(record){
        var result={};
        var sf,data;
        for (var i=0;i<this.searchFields.length;i++){
            sf=this.searchFields[i];
            data=record.get(this.searchFields[i]);
            result[sf]=data;
        }
        return result;
    } ,
    collapseAll:function(){
        var n= this.view.getNodes();
        for(i=0;i<n.length;i++){
            rowNode = n[i],
            row = Ext.get(rowNode),
            nextBd = Ext.get(row).down(this.rowBodyTrSelector),
            record = this.view.getRecord(rowNode);
            row.addCls(this.rowCollapsedCls);
            nextBd.addCls(this.rowBodyHiddenCls);
            this.recordsExpanded[record.internalId] = false;
            this.view.fireEvent('collapsebody', rowNode, record, nextBd.dom);
        }
        this.view.up('gridpanel').invalidateScroller();
        
    }
});