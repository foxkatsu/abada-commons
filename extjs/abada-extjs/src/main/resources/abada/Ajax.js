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


/***
 *Usado para llamadas Ajax y espera la respuesta en Json
 */
Ext.define('Abada.Ajax',{
    extend:'Ext.data.Connection',
    requires:['Ext.MessageBox'],
    singleton: true,
    autoAbort: false,
    constructor:function(config){
        this.setConfig(config);
        this.callParent([config]);
    },
    autoInitConfigRequest:function(args,o){        
        var result=Ext.applyIf(args,o);
        var aux={
            method:'POST'
        };
        result=Ext.applyIf(result,aux);
        return result;
    },
    requestJson: function(o){                
        //Sacar mensaje de wait
        if (o.waitTitle || o.waitMsg){
            if (!o.waitTitle) o.waitTitle='Esperando...';
            if (!o.waitMsg) o.waitMsg='Esperando...';
            o.waitMessageBox=Ext.MessageBox.wait(o.waitMsg,o.waitTitle);
        }

        var args={};
        args.success=function(result,request) {
            if (o.waitMessageBox)
                o.waitMessageBox.hide();
            var requestJson=Ext.decode(result.responseText,true);
            if (requestJson && requestJson!=null && requestJson.success){
                if (requestJson.errors){
                    Ext.callback(o.success,o.scope,[requestJson.errors]);
                    //o.success(requestJson.errors);
                }else{
                    Ext.callback(o.success,o.scope);
                    //o.success();
                }
            }
            else{
                if (o.failure){
                    if (requestJson && requestJson!=null && requestJson.errors){
                        Ext.callback(o.failure,o.scope,[requestJson.errors]);
                        //o.failure(requestJson.errors);
                    }
                    else{
                        Ext.callback(o.failure,o.scope,['Error']);
                        //o.failure('Error');
                    }
                }
            }
        };
        args.failure=function(result,request){
            if (o.waitMessageBox)
                o.waitMessageBox.hide();
            if (o.failure){
                Ext.callback(o.failure,o.scope,[result.responseText]);
                //o.failure(result.responseText);
            }
        };
        args=this.autoInitConfigRequest(args,o);
        
        this.request(args);
    },
    requestJsonData: function(o){
        var args={};
        args.success=function(result,request) {
            var requestJson=Ext.decode(result.responseText,true);
            if (requestJson && requestJson!=null && requestJson.total && requestJson.total>=0 && requestJson.data){
                Ext.callback(o.success,o.scope,[requestJson]);
                //o.success(requestJson);
            }else{
                Ext.callback(o.failure,o.scope,['Error']);
                //o.failure('Error');
            }
        };
        args.failure=function(result,request){
            if (o.failure){
                if (result.responseText){
                    Ext.callback(o.failure,o.scope,[result.responseText]);
                    //o.failure(result.responseText);
                }else if (result.statusText){
                    Ext.callback(o.failure,o.scope,[result.statusText]);
                    //o.failure(result.statusText);
                }else{
                    Ext.callback(o.failure,o.scope,['']);
                    //o.failure('');
                }
            }
        };
        args=this.autoInitConfigRequest(args,o);
        this.request(args);
    },    
    requestJsonObject: function(o){
        var args={};
        args.success=function(result,request) {
            var requestJson=Ext.decode(result.responseText,true);
            if (requestJson && requestJson!=null){
                Ext.callback(o.success,o.scope,[requestJson]);
                //o.success(requestJson);
            }else{
                Ext.callback(o.failure,o.scope,['Error']);
                //o.failure('Error');
            }
        };
        args.failure=function(result,request){
            if (o.failure){
                if (result.responseText){
                    Ext.callback(o.failure,o.scope,[result.responseText]);
                    //o.failure(result.responseText);
                }else if (result.statusText){
                    Ext.callback(o.failure,o.scope,[result.statusText]);
                    //o.failure(result.statusText);
                }else{
                    Ext.callback(o.failure,o.scope,['']);
                    //o.failure('');
                }
            }
        };        
        args=this.autoInitConfigRequest(args,o);
        this.request(args);
    }
});

/*Ajax =new Ext.data.Connection({
    autoAbort : false,
    serializeForm : function(form){
        return Ext.lib.Ajax.serializeForm(form);
    },
    requestJson: function(o){
        //Sacar mensaje de wait
        if (o.waitTitle || o.waitMsg){
            if (!o.waitTitle) o.waitTitle='Esperando...';
            if (!o.waitMsg) o.waitMsg='Esperando...';
            o.waitMessageBox=Ext.MessageBox.wait(o.waitMsg,o.waitTitle);
        }

        this.request({
            url: o.url,
            //Parametros
            params: o.params,
            success: function(result,request) {
                if (o.waitMessageBox)
                    o.waitMessageBox.hide();
                var requestJson=Ext.decode(result.responseText);
                if (requestJson.success){
                    if (requestJson.errors){
                        o.success(requestJson.errors);
                    }else{
                        o.success();
                    }
                }
                else{
                    if (o.failure){
                        if (requestJson.errors){
                            o.failure(requestJson.errors);
                        }
                        else{
                            o.failure('Error');
                        }
                    }
                }
            },
            failure:function(result,request){
                if (o.waitMessageBox)
                    o.waitMessageBox.hide();
                if (o.failure){
                    o.failure(result.responseText);
                }
            }
        }
        );
    }
});*/
