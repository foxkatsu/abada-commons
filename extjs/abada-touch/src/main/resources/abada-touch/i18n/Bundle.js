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
Ext.define('Abada.i18n.Bundle', {
    extend: 'Ext.util.Observable',
    requires: [
        'Abada.Ajax','Ext.String'
    ],
    //@private
    defaultLanguage: 'es_ES',
    config: {
        /**
         * @cfg bundle {String} bundle name for properties file. Default to message
         */
        bundle: 'message',
        /**
         * @cfg path {String} URI to properties files. Default to resources
         */
        path: 'resources',
        url: undefined,
        localePath: 'touch/locale',
        insertLocale: true

                /**
                 * @cfg lang {String} Language in the form xx-YY where:
                 *		xx: Language code (2 characters lowercase)
                 *      YY: Country code (2 characters upercase).
                 * Optional. Default to browser's language. If it cannot be determined default to en-US.
                 */

                /**
                 * @cfg noCache {boolean} whether or not to disable Proxy's cache. Optional. Defaults to true.
                 */
    },
    constructor: function(config) {
        config = config || {};

        var me = this,
                language = me.formatLanguageCode(config.lang || me.guessLanguage());

        me.language = language;
        me.bundle = config.bundle || me.bundle;
        me.localePath = config.localePath || me.localePath;
        me.path = config.path || me.path;
        me.url = config.url || me.url;
        me.insertLocale = config.insertLocale || me.insertLocale;

        delete config.lang;
        delete config.noCache;

        me.callParent([config]);

        if (me.insertLocale)
            this.insertScript(me.language);
    },
    /**
     * @private
     */
    findScript: function(head, language) {
        var i;
        for (i = 0; i < head.childNodes.length; i++) {
            if (head.childNodes[i].type && head.childNodes[i].type == 'text/javascript') {
                if (head.childNodes[i].src == this.buildLocaleURL(language))
                    return head.childNodes[i];
            }
        }
        return undefined;
    },
    /**
     * @private
     */
    insertScript: function(language) {
        var headID = document.getElementsByTagName('head')[0];
        if (headID) {
            var newScript = this.findScript(headID, language);

            if (!newScript) {
                //script callback de datos
                var newScript = document.createElement('script');
                newScript.type = 'text/javascript';
                newScript.src = this.buildLocaleURL(language);

                var scriptEl = Ext.get(newScript);

                var headIDExt = Ext.get(headID);

                headIDExt.appendChild(scriptEl);
            }
        }
    },
    /**
     * @private
     */
    guessLanguage: function() {
        return (navigator.language || navigator.browserLanguage || navigator.userLanguage || this.defaultLanguage);
    },
    /**
     * @method: getMsg
     * Returns the content associated with the bundle key or {bundle key}.undefined if it is not specified.
     * @param: key {String} Bundle key.
     * @return: {String} The bundle key content.
     */
    getMsg: function(key) {
        if (this.data) {
            var value = this.data[key];
            if (value) {
                var values = [].splice.call(arguments, 1);

                var decoded = Ext.util.Format.htmlDecode(value);
                if (values) {
                    var args = [decoded].concat(values);
                    decoded = Ext.String.format.apply(null, args);
                }
                return decoded;
            }
        }
        return key;
    },
    /**
     * @private
     */
    onProxyLoad: function(op) {
        if (op.getRecords()) {
            this.callParent(arguments);
        }
    },
    /**
     * @private
     */
    buildURL: function(language) {
        if (!this.url) {
            var url = '';
            if (this.path)
                url += this.path + '/';
            url += this.bundle;
            if (language)
                url += '-' + language;
            url += '.json';
            return url;
        } else {
            var url = this.url;
            if (language)
                url += '?language=' + language;
            return url;
        }
    },
    /**
     * @private
     */
    buildLocaleURL: function(language) {
        var url = '';
        if (this.localePath)
            url += this.localePath + '/';
        url += 'ext-lang'
        if (language)
            url += '-' + language;
        url += '.js';
        return url;
    },
    /**
     * @private
     */
    load: function() {
        this.loaded = false;
        Abada.Ajax.requestJsonObject({
            url: this.buildURL(this.language),
            method: 'GET',
            scope: this,
            success: function(data) {
                this.data = data;
                this.fireEvent('loaded', this);
            },
            failure: function(text) {
                this.data = undefined;
                this.fireEvent('error', this, text);
            }
        });
    },
    /**
     * @private
     */
    formatLanguageCode: function(lang) {
        var langCodes = lang.split('-'),
                primary, second;
        primary = (langCodes[0]) ? langCodes[0].toLowerCase() : '';
        second = (langCodes[1]) ? langCodes[1].toUpperCase() : '';

        return langCodes.length > 1 ? [primary, second].join('_') : primary;
    }
});