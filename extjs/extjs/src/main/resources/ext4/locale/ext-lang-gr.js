/*
This file is part of Ext JS 4.2

Copyright (c) 2011-2013 Sencha Inc

Contact:  http://www.sencha.com/contact

GNU General Public License Usage
This file may be used under the terms of the GNU General Public License version 3.0 as
published by the Free Software Foundation and appearing in the file LICENSE included in the
packaging of this file.

Please review the following information to ensure the GNU General Public License version 3.0
requirements will be met: http://www.gnu.org/copyleft/gpl.html.

If you are unsure which license is appropriate for your use, please contact the sales department
at http://www.sencha.com/contact.

Build date: 2013-05-16 14:36:50 (f9be68accb407158ba2b1be2c226a6ce1f649314)
*/
/**
 * Greek (Old Version) Translations by Vagelis
 * 03-June-2007
 */
Ext.onReady(function() {

    if (Ext.Date) {
        Ext.Date.monthNames = ["&eacute;&aacute;&iacute;ïõÜñ&eacute;ïò", "ÖåâñïõÜñ&eacute;ïò", "ÌÜñô&eacute;ïò", "&aacute;ðñßë&eacute;ïò", "ÌÜ&eacute;ïò", "&eacute;ïý&iacute;&eacute;ïò", "&eacute;ïýë&eacute;ïò", "&aacute;ýãïõ&oacute;ôïò", "&oacute;åðôÝìâñ&eacute;ïò", "Ïêôþâñ&eacute;ïò", "&iacute;ïÝìâñ&eacute;ïò", "ÄåêÝìâñ&eacute;ïò"];

        Ext.Date.dayNames = ["Êõñ&eacute;&aacute;êÞ", "ÄåõôÝñ&aacute;", "Ôñßôç", "ÔåôÜñôç", "ÐÝìðôç", "Ð&aacute;ñ&aacute;&oacute;êåõÞ", "&oacute;Üââ&aacute;ôï"];
    }

    if (Ext.util && Ext.util.Format) {
        Ext.apply(Ext.util.Format, {
            thousandSeparator: '.',
            decimalSeparator: ',',
            currencySign: '\u20ac',
            // Greek Euro
            dateFormat: 'ì/ç/Å'
        });
    }
});

Ext.define("Ext.locale.gr.view.View", {
    override: "Ext.view.View",
    emptyText: ""
});

Ext.define("Ext.locale.gr.grid.plugin.DragDrop", {
    override: "Ext.grid.plugin.DragDrop",
    dragText: "{0} åð&eacute;ëåãìÝ&iacute;ç(åò) ãñ&aacute;ììÞ(Ýò)"
});

Ext.define("Ext.locale.gr.tab.Tab", {
    override: "Ext.tab.Tab",
    closeText: "Êëåß&oacute;ôå &aacute;õôÞ ôç&iacute; ê&aacute;ñôÝë&aacute;"
});

Ext.define("Ext.locale.gr.form.field.Base", {
    override: "Ext.form.field.Base",
    invalidText: "Ç ô&eacute;ìÞ &oacute;ôï ðåäßï äå&iacute; åß&iacute;&aacute;&eacute; Ýãêõñç"
});

// changing the msg text below will affect the LoadMask
Ext.define("Ext.locale.gr.view.AbstractView", {
    override: "Ext.view.AbstractView",
    loadingText: "Öüñôù&oacute;ç..."
});

Ext.define("Ext.locale.gr.picker.Date", {
    override: "Ext.picker.Date",
    todayText: "&oacute;Þìåñ&aacute;",
    minText: "Ç çìåñïìç&iacute;ß&aacute; &aacute;õôÞ åß&iacute;&aacute;&eacute; ðñ&eacute;&iacute; ôç&iacute; ì&eacute;êñüôåñç çìåñïìç&iacute;ß&aacute;",
    maxText: "Ç çìåñïìç&iacute;ß&aacute; &aacute;õôÞ åß&iacute;&aacute;&eacute; ìåôÜ ôç&iacute; ìåã&aacute;ëýôåñç çìåñïìç&iacute;ß&aacute;",
    disabledDaysText: "",
    disabledDatesText: "",
    nextText: 'Åðüìå&iacute;ïò ÌÞ&iacute;&aacute;ò (Control+Right)',
    prevText: 'Ðñïçãïýìå&iacute;ïò ÌÞ&iacute;&aacute;ò (Control+Left)',
    monthYearText: 'Åð&eacute;ëÝîôå ÌÞ&iacute;&aacute; (Control+Up/Down ã&eacute;&aacute; ìåô&aacute;êß&iacute;ç&oacute;ç &oacute;ô&aacute; Ýôç)',
    todayTip: "{0} (Spacebar)",
    format: "ì/ç/Å"
});

Ext.define("Ext.locale.gr.toolbar.Paging", {
    override: "Ext.PagingToolbar",
    beforePageText: "&oacute;åëßä&aacute;",
    afterPageText: "&aacute;ðü {0}",
    firstText: "Ðñþôç &oacute;åëßä&aacute;",
    prevText: "Ðñïçãïýìå&iacute;ç &oacute;åëßä&aacute;",
    nextText: "Åðüìå&iacute;ç &oacute;åëßä&aacute;",
    lastText: "Ôåëåõô&aacute;ß&aacute; &oacute;åëßä&aacute;",
    refreshText: "&aacute;&iacute;&aacute;&iacute;Ýù&oacute;ç",
    displayMsg: "ÅìöÜ&iacute;&eacute;&oacute;ç {0} - {1} &aacute;ðü {2}",
    emptyMsg: 'Äå&iacute; âñÝèçê&aacute;&iacute; åããñ&aacute;öÝò ã&eacute;&aacute; åìöÜ&iacute;&eacute;&oacute;ç'
});

Ext.define("Ext.locale.gr.form.field.Text", {
    override: "Ext.form.field.Text",
    minLengthText: "Ôï åëÜ÷&eacute;&oacute;ôï ìÝãåèïò ã&eacute;&aacute; &aacute;õôü ôï ðåäßï åß&iacute;&aacute;&eacute; {0}",
    maxLengthText: "Ôï ìÝã&eacute;&oacute;ôï ìÝãåèïò ã&eacute;&aacute; &aacute;õôü ôï ðåäßï åß&iacute;&aacute;&eacute; {0}",
    blankText: "Ôï ðåäßï &aacute;õôü åß&iacute;&aacute;&eacute; õðï÷ñåùôïêü",
    regexText: "",
    emptyText: null
});

Ext.define("Ext.locale.gr.form.field.Number", {
    override: "Ext.form.field.Number",
    minText: "Ç åëÜ÷&eacute;&oacute;ôç ô&eacute;ìÞ ã&eacute;&aacute; &aacute;õôü ôï ðåäßï åß&iacute;&aacute;&eacute; {0}",
    maxText: "Ç ìÝã&eacute;&oacute;ôç ô&eacute;ìÞ ã&eacute;&aacute; &aacute;õôü ôï ðåäßï åß&iacute;&aacute;&eacute; {0}",
    nanText: "{0} äå&iacute; åß&iacute;&aacute;&eacute; Ýãêõñïò &aacute;ñ&eacute;èìüò"
});

Ext.define("Ext.locale.gr.form.field.Date", {
    override: "Ext.form.field.Date",
    disabledDaysText: "&aacute;ðå&iacute;åñãïðï&eacute;çìÝ&iacute;ï",
    disabledDatesText: "&aacute;ðå&iacute;åñãïðï&eacute;çìÝ&iacute;ï",
    minText: "Ç çìåñïìç&iacute;ß&aacute; &oacute;' &aacute;õôü ôï ðåäßï ðñÝðå&eacute; &iacute;&aacute; åß&iacute;&aacute;&eacute; ìåôÜ &aacute;ðü {0}",
    maxText: "Ç çìåñïìç&iacute;ß&aacute; &oacute;' &aacute;õôü ôï ðåäßï ðñÝðå&eacute; &iacute;&aacute; åß&iacute;&aacute;&eacute; ðñ&eacute;&iacute; &aacute;ðü {0}",
    invalidText: "{0} äå&iacute; åß&iacute;&aacute;&eacute; Ýãêõñç çìåñïìç&iacute;ß&aacute; - ðñÝðå&eacute; &iacute;&aacute; åß&iacute;&aacute;&eacute; ôçò ìïñöÞò {1}",
    format: "ì/ç/Å"
});

Ext.define("Ext.locale.gr.form.field.ComboBox", {
    override: "Ext.form.field.ComboBox",
    valueNotFoundText: undefined
}, function() {
    Ext.apply(Ext.form.field.ComboBox.prototype.defaultListConfig, {
        loadingText: "Öüñôù&oacute;ç..."
    });
});

Ext.define("Ext.locale.gr.form.field.VTypes", {
    override: "Ext.form.field.VTypes",
    emailText: '&aacute;õôü ôï ðåäßï ðñÝðå&eacute; &iacute;&aacute; åß&iacute;&aacute;&eacute; e-mail address ôçò ìïñöÞò "user@example.com"',
    urlText: '&aacute;õôü ôï ðåäßï ðñÝðå&eacute; &iacute;&aacute; åß&iacute;&aacute;&eacute; ì&eacute;&aacute; ä&eacute;åýèõ&iacute;&oacute;ç URL ôçò ìïñöÞò "http:/' + '/www.example.com"',
    alphaText: '&aacute;õôü ôï ðåäßï ðñÝðå&eacute; &iacute;&aacute; ðåñ&eacute;Ý÷å&eacute; ãñÜìì&aacute;ô&aacute; ê&aacute;&eacute; _',
    alphanumText: '&aacute;õôü ôï ðåäßï ðñÝðå&eacute; &iacute;&aacute; ðåñ&eacute;Ý÷å&eacute; ãñÜìì&aacute;ô&aacute;, &aacute;ñ&eacute;èìïýò ê&aacute;&eacute; _'
});

Ext.define("Ext.locale.gr.grid.header.Container", {
    override: "Ext.grid.header.Container",
    sortAscText: "&aacute;ýîïõ&oacute;&aacute; Ô&aacute;î&eacute;&iacute;üìç&oacute;ç",
    sortDescText: "Öèß&iacute;ïõ&oacute;&aacute; Ô&aacute;î&eacute;&iacute;üìç&oacute;ç",
    lockText: "Êëåßäùì&aacute; &oacute;ôÞëçò",
    unlockText: "Îåêëåßäùì&aacute; &oacute;ôÞëçò",
    columnsText: "&oacute;ôÞëåò"
});

Ext.define("Ext.locale.gr.grid.PropertyColumnModel", {
    override: "Ext.grid.PropertyColumnModel",
    nameText: "¼&iacute;ïì&aacute;",
    valueText: "Ô&eacute;ìÞ",
    dateFormat: "ì/ç/Å"
});

Ext.define("Ext.locale.gr.window.MessageBox", {
    override: "Ext.window.MessageBox",
    buttonText: {
        ok: "Å&iacute;ôÜîå&eacute;",
        cancel: "&aacute;êýñù&oacute;ç",
        yes: "&iacute;&aacute;&eacute;",
        no: "¼÷&eacute;"
    }    
});

// This is needed until we can refactor all of the locales into individual files
Ext.define("Ext.locale.gr.Component", {	
    override: "Ext.Component"
});

