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
 * List compiled by mystix on the extjs.com forums.
 * Thank you Mystix!
 * Slovak Translation by Michal Thomka
 * 14 April 2007
 */
Ext.onReady(function() {

    if (Ext.Date) {
        Ext.Date.monthNames = ["Janu&aacute;r", "Febru&aacute;r", "Marec", "Apr&iacute;l", "M&aacute;j", "J&uacute;n", "J&uacute;l", "August", "September", "Okt&oacute;ber", "November", "December"];

        Ext.Date.dayNames = ["Nedeľa", "Pondelok", "Utorok", "Streda", "Štvrtok", "Piatok", "Sobota"];
    }

    if (Ext.util && Ext.util.Format) {
        Ext.apply(Ext.util.Format, {
            thousandSeparator: '.',
            decimalSeparator: ',',
            currencySign: '\u20ac',
            // Slovakian Euro
            dateFormat: 'd.m.Y'
        });
    }
});

Ext.define("Ext.locale.sk.view.View", {
    override: "Ext.view.View",
    emptyText: ""
});

Ext.define("Ext.locale.sk.grid.plugin.DragDrop", {
    override: "Ext.grid.plugin.DragDrop",
    dragText: "{0} označených riadkov"
});

Ext.define("Ext.locale.sk.tab.Tab", {
    override: "Ext.tab.Tab",
    closeText: "Zavrieť t&uacute;to z&aacute;ložku"
});

Ext.define("Ext.locale.sk.form.field.Base", {
    override: "Ext.form.field.Base",
    invalidText: "Hodnota v tomto poli je nespr&aacute;vna"
});

// changing the msg text below will affect the LoadMask
Ext.define("Ext.locale.sk.view.AbstractView", {
    override: "Ext.view.AbstractView",
    loadingText: "Nahr&aacute;vam..."
});

Ext.define("Ext.locale.sk.picker.Date", {
    override: "Ext.picker.Date",
    todayText: "Dnes",
    minText: "Tento d&aacute;tum je menš&iacute; ako minim&aacute;lny možný d&aacute;tum",
    maxText: "Tento d&aacute;tum je väčš&iacute; ako maxim&aacute;lny možný d&aacute;tum",
    disabledDaysText: "",
    disabledDatesText: "",
    nextText: 'Ďalš&iacute; mesiac (Control+Doprava)',
    prevText: 'Predch&aacute;dzaj&uacute;ci mesiac (Control+Doľava)',
    monthYearText: 'Vyberte mesiac (Control+Hore/Dole pre posun rokov)',
    todayTip: "{0} (Medzern&iacute;k)",
    format: "d.m.Y"
});

Ext.define("Ext.locale.sk.toolbar.Paging", {
    override: "Ext.PagingToolbar",
    beforePageText: "Strana",
    afterPageText: "z {0}",
    firstText: 'Prv&aacute; strana',
    prevText: 'Predch&aacute;dzaj&uacute;ca strana',
    nextText: 'Ďalšia strana',
    lastText: "Posledn&aacute; strana",
    refreshText: "Obnoviť",
    displayMsg: "Zobrazujem {0} - {1} z {2}",
    emptyMsg: 'Žiadne d&aacute;ta'
});

Ext.define("Ext.locale.sk.form.field.Text", {
    override: "Ext.form.field.Text",
    minLengthText: "Minim&aacute;lna dĺžka pre toto pole je {0}",
    maxLengthText: "Maxim&aacute;lna dĺžka pre toto pole je {0}",
    blankText: "Toto pole je povinn&eacute;",
    regexText: "",
    emptyText: null
});

Ext.define("Ext.locale.sk.form.field.Number", {
    override: "Ext.form.field.Number",
    minText: "Minim&aacute;lna hodnota pre toto pole je {0}",
    maxText: "Maxim&aacute;lna hodnota pre toto pole je {0}",
    nanText: "{0} je nespr&aacute;vne č&iacute;slo"
});

Ext.define("Ext.locale.sk.form.field.Date", {
    override: "Ext.form.field.Date",
    disabledDaysText: "Zablokovan&eacute;",
    disabledDatesText: "Zablokovan&eacute;",
    minText: "D&aacute;tum v tomto poli mus&iacute; byť až po {0}",
    maxText: "D&aacute;tum v tomto poli mus&iacute; byť pred {0}",
    invalidText: "{0} nie je spr&aacute;vny d&aacute;tum - mus&iacute; byť vo form&aacute;te {1}",
    format: "d.m.Y"
});

Ext.define("Ext.locale.sk.form.field.ComboBox", {
    override: "Ext.form.field.ComboBox",
    valueNotFoundText: undefined
}, function() {
    Ext.apply(Ext.form.field.ComboBox.prototype.defaultListConfig, {
        loadingText: "Nahr&aacute;vam..."
    });
});

Ext.define("Ext.locale.sk.form.field.VTypes", {
    override: "Ext.form.field.VTypes",
    emailText: 'Toto pole mus&iacute; byť e-mailov&aacute; adresa vo form&aacute;te "user@example.com"',
    urlText: 'Toto pole mus&iacute; byť URL vo form&aacute;te "http:/' + '/www.example.com"',
    alphaText: 'Toto pole može obsahovať iba p&iacute;smen&aacute; a znak _',
    alphanumText: 'Toto pole može obsahovať iba p&iacute;smen&aacute;, č&iacute;sla a znak _'
});

Ext.define("Ext.locale.sk.grid.header.Container", {
    override: "Ext.grid.header.Container",
    sortAscText: "Zoradiť vzostupne",
    sortDescText: "Zoradiť zostupne",
    lockText: 'Zamkn&uacute;ť stĺpec',
    unlockText: 'Odomkn&uacute;ť stĺpec',
    columnsText: 'Stĺpce'
});

Ext.define("Ext.locale.sk.grid.PropertyColumnModel", {
    override: "Ext.grid.PropertyColumnModel",
    nameText: "N&aacute;zov",
    valueText: "Hodnota",
    dateFormat: "d.m.Y"
});

Ext.define("Ext.locale.sk.window.MessageBox", {
    override: "Ext.window.MessageBox",
    buttonText: {
        ok: "OK",
        cancel: "Zrušiť",
        yes: "&aacute;no",
        no: "Nie"
    }    
});

// This is needed until we can refactor all of the locales into individual files
Ext.define("Ext.locale.sk.Component", {	
    override: "Ext.Component"
});

