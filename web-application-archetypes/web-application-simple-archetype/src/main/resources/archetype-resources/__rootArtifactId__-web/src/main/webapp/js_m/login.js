/*
 * #%L
 * Cleia
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
Ext.require([
    'Katsu.Ajax',
    'Katsu.Base64'
]);

Ext.setup({
    onReady: function() {
        Katsu.i18n.Bundle.bundle.on('loaded', function() {
            principal();
        });
        Katsu.i18n.Bundle.bundle.on('error', function() {
            Katsu.i18n.Bundle.bundle.language = Katsu.i18n.Bundle.bundle.defaultLanguage;
            Katsu.i18n.Bundle.bundle.load();
        });
        Katsu.i18n.Bundle.bundle.load();

        function principal() {
            function formSubmit() {
                authHeader = getBasicAuthentication();
                Katsu.Ajax.request({
                    url: App.urlServer + App.urlServerRoles,
                    headers: {
                        Authorization: authHeader
                    },
                    method: 'GET',
                    success: function(result, request) {
                        formSubmitPriv();
                    },
                    failure: function() {
                    }
                });
                //formSubmitPriv();
            }

            function getBasicAuthentication() {
                return 'Basic ' + Katsu.Base64.encode(login.getAt(0).getAt(0).getValue() + ':' + login.getAt(0).getAt(1).getValue());
            }

            function formSubmitPriv() {
                login.submit({
                    method: 'POST',
                    waitTitle: Katsu.i18n.Bundle.bundle.getMsg('login.connecting'),
                    waitMsg: Katsu.i18n.Bundle.bundle.getMsg('login.connectionMessage'),
                    failure: function(form, action) {
                    },
                    success: function() {
                        //window.location='main.htm';
                    }
                });
            }

            var login = Ext.create('Ext.form.Panel', {
                url: 'j_spring_security_check',
                fullscreen: true,
                standardSubmit: true,
                items: [
                    {
                        xtype: 'fieldset',
                        title: Katsu.i18n.Bundle.bundle.getMsg('login.title'),
                        defaults: {
                            required: true
                        },
                        items: [{
                                xtype: 'textfield',
                                label: Katsu.i18n.Bundle.bundle.getMsg('login.username'),
                                name: 'j_username',
                                id: 'j_username'
                            }, {
                                xtype: 'passwordfield',
                                label: Katsu.i18n.Bundle.bundle.getMsg('login.password'),
                                name: 'j_password',
                                id: 'j_password'
                            }]
                    },
                    {
                        xtype: 'toolbar',
                        docked: 'top',
                        height: 30,
                        items: [{
                                text: Katsu.i18n.Bundle.bundle.getMsg('login.button'),
                                ui: 'round',
                                id: 'blogin',
                                scope: this,
                                handler: formSubmit
                            }]
                    }]
            });

            Ext.Viewport.add(login);
        }
    }});