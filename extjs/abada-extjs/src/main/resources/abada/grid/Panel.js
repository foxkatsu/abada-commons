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

Ext.define('Abada.grid.Panel', {
    extend: 'Ext.grid.Panel',
    config: {
        i18n: undefined
    },
    constructor: function(config) {
        this.initConfig(config);
        this.setTitle(config.i18n.getMsg(this.title));
        this.translation(config.i18n);

        this.callParent([config]);
    },
    translation: function(translator) {
        var i;
        for (i = 0; i < this.columns.length; i++) {
            this.columns[i].header = translator.getMsg(this.columns[i].header);
        }
    }
});
