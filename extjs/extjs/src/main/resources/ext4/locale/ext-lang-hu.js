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
 *
 * Hungarian Translations (utf-8 encoded)
 * by Amon <amon@theba.hu> (27 Apr 2008)
 * encoding fixed by Vili (17 Feb 2009)
 */
Ext.onReady(function() {

    if (Ext.Date) {
        Ext.Date.monthNames = ["Janu&aacute;r", "Febru&aacute;r", "M&aacute;rcius", "&aacute;prilis", "M&aacute;jus", "J&uacute;nius", "J&uacute;lius", "Augusztus", "Szeptember", "Okt&oacute;ber", "November", "December"];

        Ext.Date.getShortMonthName = function(month) {
            return Ext.Date.monthNames[month].substring(0, 3);
        };

        Ext.Date.monthNumbers = {
            'Jan': 0,
            'Feb': 1,
            'M&aacute;r': 2,
            '&aacute;pr': 3,
            'M&aacute;j': 4,
            'J&uacute;n': 5,
            'J&uacute;l': 6,
            'Aug': 7,
            'Sze': 8,
            'Okt': 9,
            'Nov': 10,
            'Dec': 11
        };

        Ext.Date.getMonthNumber = function(name) {
            return Ext.Date.monthNumbers[name.substring(0, 1).toUpperCase() + name.substring(1, 3).toLowerCase()];
        };

        Ext.Date.dayNames = ["Vas&aacute;rnap", "H&eacute;tfő", "Kedd", "Szerda", "Csütörtök", "P&eacute;ntek", "Szombat"];

        Ext.Date.getShortDayName = function(day) {
            return Ext.Date.dayNames[day].substring(0, 3);
        };
    }
    if (Ext.util && Ext.util.Format) {
        Ext.apply(Ext.util.Format, {
            thousandSeparator: '.',
            decimalSeparator: ',',
            currencySign: 'Ft',
            // Hungarian Forint
            dateFormat: 'Y m d'
        });
    }
});

Ext.define("Ext.locale.hu.view.View", {
    override: "Ext.view.View",
    emptyText: ""
});

Ext.define("Ext.locale.hu.grid.plugin.DragDrop", {
    override: "Ext.grid.plugin.DragDrop",
    dragText: "{0} kiv&aacute;lasztott sor"
});

Ext.define("Ext.locale.hu.tab.Tab", {
    override: "Ext.tab.Tab",
    closeText: "Fül bez&aacute;r&aacute;sa"
});

Ext.define("Ext.locale.hu.form.field.Base", {
    override: "Ext.form.field.Base",
    invalidText: "Hib&aacute;s &eacute;rt&eacute;k!"
});

// changing the msg text below will affect the LoadMask
Ext.define("Ext.locale.hu.view.AbstractView", {
    override: "Ext.view.AbstractView",
    loadingText: "Betölt&eacute;s..."
});

Ext.define("Ext.locale.hu.picker.Date", {
    override: "Ext.picker.Date",
    todayText: "Mai nap",
    minText: "A d&aacute;tum kor&aacute;bbi a megengedettn&eacute;l",
    maxText: "A d&aacute;tum k&eacute;sőbbi a megengedettn&eacute;l",
    disabledDaysText: "",
    disabledDatesText: "",
    nextText: 'Köv. h&oacute;nap (CTRL+Jobbra)',
    prevText: 'Előző h&oacute;nap (CTRL+Balra)',
    monthYearText: 'V&aacute;lassz h&oacute;napot (&eacute;vv&aacute;laszt&aacute;s: CTRL+Fel/Le)',
    todayTip: "{0} (Sz&oacute;köz)",
    format: "y-m-d",
    startDay: 0
});

Ext.define("Ext.locale.hu.picker.Month", {
    override: "Ext.picker.Month",
    okText: "&#160;OK&#160;",
    cancelText: "M&eacute;gsem"
});

Ext.define("Ext.locale.hu.toolbar.Paging", {
    override: "Ext.PagingToolbar",
    beforePageText: "Oldal",
    afterPageText: "a {0}-b&oacute;l/ből",
    firstText: "Első oldal",
    prevText: "Előző oldal",
    nextText: "Következő oldal",
    lastText: "Utols&oacute; oldal",
    refreshText: "Friss&iacute;t&eacute;s",
    displayMsg: "{0} - {1} sorok l&aacute;that&oacute;k a {2}-b&oacute;l/ből",
    emptyMsg: 'Nincs megjelen&iacute;thető adat'
});

Ext.define("Ext.locale.hu.form.field.Text", {
    override: "Ext.form.field.Text",
    minLengthText: "A mező tartalma legal&aacute;bb {0} hossz&uacute; kell legyen",
    maxLengthText: "A mező tartalma legfeljebb {0} hossz&uacute; lehet",
    blankText: "Kötelezően kitöltendő mező",
    regexText: "",
    emptyText: null
});

Ext.define("Ext.locale.hu.form.field.Number", {
    override: "Ext.form.field.Number",
    minText: "A mező tartalma nem lehet kissebb, mint {0}",
    maxText: "A mező tartalma nem lehet nagyobb, mint {0}",
    nanText: "{0} nem sz&aacute;m"
});

Ext.define("Ext.locale.hu.form.field.Date", {
    override: "Ext.form.field.Date",
    disabledDaysText: "Nem v&aacute;laszthat&oacute;",
    disabledDatesText: "Nem v&aacute;laszthat&oacute;",
    minText: "A d&aacute;tum nem lehet kor&aacute;bbi, mint {0}",
    maxText: "A d&aacute;tum nem lehet k&eacute;sőbbi, mint {0}",
    invalidText: "{0} nem megfelelő d&aacute;tum - a helyes form&aacute;tum: {1}",
    format: "Y m d",
    altFormats: "Y-m-d|y-m-d|y/m/d|m/d|m-d|md|ymd|Ymd|d"
});

Ext.define("Ext.locale.hu.form.field.ComboBox", {
    override: "Ext.form.field.ComboBox",
    valueNotFoundText: undefined
}, function() {
    Ext.apply(Ext.form.field.ComboBox.prototype.defaultListConfig, {
        loadingText: "Betölt&eacute;s..."
    });
});

Ext.define("Ext.locale.hu.form.field.VTypes", {
    override: "Ext.form.field.VTypes",
    emailText: 'A mező email c&iacute;met tartalmazhat, melynek form&aacute;tuma "felhaszn&aacute;l&oacute;@szolg&aacute;ltat&oacute;.hu"',
    urlText: 'A mező webc&iacute;met tartalmazhat, melynek form&aacute;tuma "http:/' + '/www.weboldal.hu"',
    alphaText: 'A mező csak betűket &eacute;s al&aacute;h&uacute;z&aacute;st (_) tartalmazhat',
    alphanumText: 'A mező csak betűket, sz&aacute;mokat &eacute;s al&aacute;h&uacute;z&aacute;st (_) tartalmazhat'
});
    

Ext.define("Ext.locale.hu.form.field.HtmlEditor", {
    override: "Ext.form.field.HtmlEditor",
    createLinkText: 'Add meg a webc&iacute;met:'
}, function() {
    Ext.apply(Ext.form.field.HtmlEditor.prototype, {
        buttonTips: {
            bold: {
                title: 'F&eacute;lköv&eacute;r (Ctrl+B)',
                text: 'F&eacute;lköv&eacute;rr&eacute; teszi a kijelölt szöveget.',
                cls: Ext.baseCSSPrefix + 'html-editor-tip'
            },
            italic: {
                title: 'Dőlt (Ctrl+I)',
                text: 'Dőlt&eacute; teszi a kijelölt szöveget.',
                cls: Ext.baseCSSPrefix + 'html-editor-tip'
            },
            underline: {
                title: 'Al&aacute;h&uacute;z&aacute;s (Ctrl+U)',
                text: 'Al&aacute;h&uacute;zza a kijelölt szöveget.',
                cls: Ext.baseCSSPrefix + 'html-editor-tip'
            },
            increasefontsize: {
                title: 'Szöveg nagy&iacute;t&aacute;s',
                text: 'Növeli a szövegm&eacute;retet.',
                cls: Ext.baseCSSPrefix + 'html-editor-tip'
            },
            decreasefontsize: {
                title: 'Szöveg kicsiny&iacute;t&eacute;s',
                text: 'Csökkenti a szövegm&eacute;retet.',
                cls: Ext.baseCSSPrefix + 'html-editor-tip'
            },
            backcolor: {
                title: 'H&aacute;tt&eacute;rsz&iacute;n',
                text: 'A kijelölt szöveg h&aacute;tt&eacute;rsz&iacute;n&eacute;t m&oacute;dos&iacute;tja.',
                cls: Ext.baseCSSPrefix + 'html-editor-tip'
            },
            forecolor: {
                title: 'Szövegsz&iacute;n',
                text: 'A kijelölt szöveg sz&iacute;n&eacute;t m&oacute;dos&iacute;tja.',
                cls: Ext.baseCSSPrefix + 'html-editor-tip'
            },
            justifyleft: {
                title: 'Balra z&aacute;rt',
                text: 'Balra z&aacute;rja a szöveget.',
                cls: Ext.baseCSSPrefix + 'html-editor-tip'
            },
            justifycenter: {
                title: 'Köz&eacute;pre z&aacute;rt',
                text: 'Köz&eacute;pre z&aacute;rja a szöveget.',
                cls: Ext.baseCSSPrefix + 'html-editor-tip'
            },
            justifyright: {
                title: 'Jobbra z&aacute;rt',
                text: 'Jobbra z&aacute;rja a szöveget.',
                cls: Ext.baseCSSPrefix + 'html-editor-tip'
            },
            insertunorderedlist: {
                title: 'Felsorol&aacute;s',
                text: 'Felsorol&aacute;st kezd.',
                cls: Ext.baseCSSPrefix + 'html-editor-tip'
            },
            insertorderedlist: {
                title: 'Sz&aacute;moz&aacute;s',
                text: 'Sz&aacute;mozott list&aacute;t kezd.',
                cls: Ext.baseCSSPrefix + 'html-editor-tip'
            },
            createlink: {
                title: 'Hiperlink',
                text: 'A kijelölt szöveget linkk&eacute; teszi.',
                cls: Ext.baseCSSPrefix + 'html-editor-tip'
            },
            sourceedit: {
                title: 'Forr&aacute;s n&eacute;zet',
                text: 'Forr&aacute;s n&eacute;zetbe kapcsol.',
                cls: Ext.baseCSSPrefix + 'html-editor-tip'
            }
        }
    });
});

Ext.define("Ext.locale.hu.grid.header.Container", {
    override: "Ext.grid.header.Container",
    sortAscText: "Növekvő rendez&eacute;s",
    sortDescText: "Csökkenő rendez&eacute;s",
    lockText: "Oszlop z&aacute;rol&aacute;s",
    unlockText: "Oszlop felold&aacute;s",
    columnsText: "Oszlopok"
});

Ext.define("Ext.locale.hu.grid.GroupingFeature", {
    override: "Ext.grid.GroupingFeature",
    emptyGroupText: '(Nincs)',
    groupByText: 'Oszlop szerint csoportos&iacute;t&aacute;s',
    showGroupsText: 'Csoportos n&eacute;zet'
});

Ext.define("Ext.locale.hu.grid.PropertyColumnModel", {
    override: "Ext.grid.PropertyColumnModel",
    nameText: "N&eacute;v",
    valueText: "&eacute;rt&eacute;k",
    dateFormat: "Y m j"
});

Ext.define("Ext.locale.hu.window.MessageBox", {
    override: "Ext.window.MessageBox",
    buttonText: {
        ok: "OK",
        cancel: "M&eacute;gsem",
        yes: "Igen",
        no: "Nem"
    }    
});

// This is needed until we can refactor all of the locales into individual files
Ext.define("Ext.locale.hu.Component", {	
    override: "Ext.Component"
});

