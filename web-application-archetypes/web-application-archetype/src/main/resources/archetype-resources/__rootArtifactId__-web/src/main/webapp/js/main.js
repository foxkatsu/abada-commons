/*
 * #%L
 * Web Archetype
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
    'Ext.container.Viewport',
    'Ext.container.Container',
    'Ext.layout.container.Border',
    'Ext.ux.layout.Center',
    'Katsu.menu.HorizontalMainMenu'
]);

Ext.onReady(function() {
    Katsu.i18n.Bundle.bundle.on('loaded', function() {
        principal();
    });
    Katsu.i18n.Bundle.bundle.on('error', function() {
        Katsu.i18n.Bundle.bundle.language = Katsu.i18n.Bundle.bundle.defaultLanguage;
        Katsu.i18n.Bundle.bundle.load();
    });
    Katsu.i18n.Bundle.bundle.load();

    function principal() {
        var menu = Ext.create('Katsu.menu.HorizontalMainMenu', {
            url: getRelativeURI('mainmenu.do'),
            autoLoadData: true
        });

        var container = Ext.create('Ext.container.Container', {
            region: 'center',
            margins: '10 75 10 75',
            items: [
                {
                    height: 150,
                    style: {
                        borderTopLeftRadius: '5px',
                        borderTopRightRadius: '5px',
                        border: '0px'
                    },
                    items: [{
                            html: '<div style=\"float:left;padding:10px;\"><img alt=\" \" src=\"' + getRelativeURI('/images/logos/katsu.png') + '\" style=\"height:60px;\" /></div>'
                                    //+'<div style=\"clear: both\" />'
                                    ,
                            border: false,
                            height: 93,
                            margins: '0 0 5 0'
                        }, menu]
                }, {
                    layout: 'fit',
                    id: 'centralPanel'
                }]
        });

        var view = Ext.create('Ext.container.Viewport', {
            cls: ['body-katsu'],
            autoScroll: true,
            layout: {
                type: 'border'
            },
            items: [
                container
            ]
        });
    }
});

