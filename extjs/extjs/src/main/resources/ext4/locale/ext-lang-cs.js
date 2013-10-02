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
 * Czech Translations
 * Translated by Tom&aacute;š Korč&aacute;k (72)
 * 2008/02/08 18:02, Ext-2.0.1
 */
Ext.onReady(function() {

    if (Ext.Date) {
        Ext.Date.monthNames = ["Leden", "&uacute;nor", "Březen", "Duben", "Květen", "Červen", "Červenec", "Srpen", "Z&aacute;ř&iacute;", "Ř&iacute;jen", "Listopad", "Prosinec"];

        Ext.Date.shortMonthNames = {
            "Leden": "Led",
            "&uacute;nor": "&uacute;no",
            "Březen": "Bře",
            "Duben": "Dub",
            "Květen": "Kvě",
            "Červen": "Čer",
            "Červenec": "Čvc",
            "Srpen": "Srp",
            "Z&aacute;ř&iacute;": "Z&aacute;ř",
            "Ř&iacute;jen": "Ř&iacute;j",
            "Listopad": "Lis",
            "Prosinec": "Pro"
        };

        Ext.Date.getShortMonthName = function(month) {
            return Ext.Date.shortMonthNames[Ext.Date.monthNames[month]];
        };

        Ext.Date.monthNumbers = {
            "Leden": 0,
            "&uacute;nor": 1,
            "Březen": 2,
            "Duben": 3,
            "Květen": 4,
            "Červen": 5,
            "Červenec": 6,
            "Srpen": 7,
            "Z&aacute;ř&iacute;": 8,
            "Ř&iacute;jen": 9,
            "Listopad": 10,
            "Prosinec": 11
        };

        Ext.Date.getMonthNumber = function(name) {
            return Ext.Date.monthNumbers[name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase()];
        };

        Ext.Date.dayNames = ["Neděle", "Ponděl&iacute;", "&uacute;terý", "Středa", "Čtvrtek", "P&aacute;tek", "Sobota"];

        Ext.Date.getShortDayName = function(day) {
            return Ext.Date.dayNames[day].substring(0, 3);
        };
    }

    if (Ext.util && Ext.util.Format) {
        Ext.apply(Ext.util.Format, {
            thousandSeparator: '.',
            decimalSeparator: ',',
            currencySign: '\u004b\u010d',
            // Czech Koruny
            dateFormat: 'd.m.Y'
        });
    }
});

Ext.define("Ext.locale.cs.view.View", {
    override: "Ext.view.View",
    emptyText: ""
});

Ext.define("Ext.locale.cs.grid.plugin.DragDrop", {
    override: "Ext.grid.plugin.DragDrop",
    dragText: "{0} vybraných ř&aacute;dků"
});

Ext.define("Ext.locale.cs.tab.Tab", {
    override: "Ext.tab.Tab",
    closeText: "Zavř&iacute;t z&aacute;ložku"
});

Ext.define("Ext.locale.cs.form.field.Base", {
    override: "Ext.form.field.Base",
    invalidText: "Hodnota v tomto poli je neplatn&aacute;"
});

// changing the msg text below will affect the LoadMask
Ext.define("Ext.locale.cs.view.AbstractView", {
    override: "Ext.view.AbstractView",
    loadingText: "Pros&iacute;m čekejte..."
});

Ext.define("Ext.locale.cs.picker.Date", {
    override: "Ext.picker.Date",
    todayText: "Dnes",
    minText: "Datum nesm&iacute; být starš&iacute; než je minim&aacute;ln&iacute;",
    maxText: "Datum nesm&iacute; být dř&iacute;vějš&iacute; než je maxim&aacute;ln&iacute;",
    disabledDaysText: "",
    disabledDatesText: "",
    nextText: 'N&aacute;sleduj&iacute;c&iacute; měs&iacute;c (Control+Right)',
    prevText: 'Předch&aacute;zej&iacute;c&iacute; měs&iacute;c (Control+Left)',
    monthYearText: 'Zvolte měs&iacute;c (ke změně let použijte Control+Up/Down)',
    todayTip: "{0} (Spacebar)",
    format: "d.m.Y",
    startDay: 1
});

Ext.define("Ext.locale.cs.picker.Month", {
    override: "Ext.picker.Month",
    okText: "&#160;OK&#160;",
    cancelText: "Storno"
});

Ext.define("Ext.locale.cs.toolbar.Paging", {
    override: "Ext.PagingToolbar",
    beforePageText: "Strana",
    afterPageText: "z {0}",
    firstText: "Prvn&iacute; strana",
    prevText: "Přech&aacute;zej&iacute;c&iacute; strana",
    nextText: "N&aacute;sleduj&iacute;c&iacute; strana",
    lastText: "Posledn&iacute; strana",
    refreshText: "Aktualizovat",
    displayMsg: "Zobrazeno {0} - {1} z celkových {2}",
    emptyMsg: 'Ž&aacute;dn&eacute; z&aacute;znamy nebyly nalezeny'
});

Ext.define("Ext.locale.cs.form.field.Text", {
    override: "Ext.form.field.Text",
    minLengthText: "Pole nesm&iacute; m&iacute;t m&eacute;ně {0} znaků",
    maxLengthText: "Pole nesm&iacute; být delš&iacute; než {0} znaků",
    blankText: "Povinn&eacute; pole",
    regexText: "",
    emptyText: null
});

Ext.define("Ext.locale.cs.form.field.Number", {
    override: "Ext.form.field.Number",
    minText: "Hodnota v tomto poli nesm&iacute; být menš&iacute; než {0}",
    maxText: "Hodnota v tomto poli nesm&iacute; být větš&iacute; než {0}",
    nanText: "{0} nen&iacute; platn&eacute; č&iacute;slo"
});

Ext.define("Ext.locale.cs.form.field.Date", {
    override: "Ext.form.field.Date",
    disabledDaysText: "Neaktivn&iacute;",
    disabledDatesText: "Neaktivn&iacute;",
    minText: "Datum v tomto poli nesm&iacute; být starš&iacute; než {0}",
    maxText: "Datum v tomto poli nesm&iacute; být novějš&iacute; než {0}",
    invalidText: "{0} nen&iacute; platným datem - zkontrolujte zda-li je ve form&aacute;tu {1}",
    format: "d.m.Y",
    altFormats: "d/m/Y|d-m-y|d-m-Y|d/m|d-m|dm|dmy|dmY|d|Y-m-d"
});

Ext.define("Ext.locale.cs.form.field.ComboBox", {
    override: "Ext.form.field.ComboBox",
    valueNotFoundText: undefined
}, function() {
    Ext.apply(Ext.form.field.ComboBox.prototype.defaultListConfig, {
        loadingText: "Pros&iacute;m čekejte..."
    });
});

Ext.define("Ext.locale.cs.form.field.VTypes", {
    override: "Ext.form.field.VTypes",
    emailText: 'V tomto poli může být vyplněna pouze emailov&aacute; adresa ve form&aacute;tu "uživatel@dom&eacute;na.cz"',
    urlText: 'V tomto poli může být vyplněna pouze URL (adresa internetov&eacute; str&aacute;nky) ve form&aacute;tu "http:/' + '/www.dom&eacute;na.cz"',
    alphaText: 'Toto pole může obsahovat pouze p&iacute;smena abecedy a znak _',
    alphanumText: 'Toto pole může obsahovat pouze p&iacute;smena abecedy, č&iacute;sla a znak _'
});

Ext.define("Ext.locale.cs.form.field.HtmlEditor", {
    override: "Ext.form.field.HtmlEditor",
    createLinkText: 'Zadejte URL adresu odkazu:'
}, function() {
    Ext.apply(Ext.form.field.HtmlEditor.prototype, {
        buttonTips: {
            bold: {
                title: 'Tučn&eacute; (Ctrl+B)',
                text: 'Označ&iacute; vybraný text tučně.',
                cls: Ext.baseCSSPrefix + 'html-editor-tip'
            },
            italic: {
                title: 'Kurz&iacute;va (Ctrl+I)',
                text: 'Označ&iacute; vybraný text kurz&iacute;vou.',
                cls: Ext.baseCSSPrefix + 'html-editor-tip'
            },
            underline: {
                title: 'Podtržen&iacute; (Ctrl+U)',
                text: 'Podtrhne vybraný text.',
                cls: Ext.baseCSSPrefix + 'html-editor-tip'
            },
            increasefontsize: {
                title: 'Zvětšit p&iacute;smo',
                text: 'Zvětš&iacute; velikost p&iacute;sma.',
                cls: Ext.baseCSSPrefix + 'html-editor-tip'
            },
            decreasefontsize: {
                title: 'Z&uacute;žit p&iacute;smo',
                text: 'Zmenš&iacute; velikost p&iacute;sma.',
                cls: Ext.baseCSSPrefix + 'html-editor-tip'
            },
            backcolor: {
                title: 'Barva zvýrazněn&iacute; textu',
                text: 'Označ&iacute; vybraný text tak, aby vypadal jako označený zvýrazňovačem.',
                cls: Ext.baseCSSPrefix + 'html-editor-tip'
            },
            forecolor: {
                title: 'Barva p&iacute;sma',
                text: 'Změn&iacute; barvu textu.',
                cls: Ext.baseCSSPrefix + 'html-editor-tip'
            },
            justifyleft: {
                title: 'Zarovnat text vlevo',
                text: 'Zarovn&aacute; text doleva.',
                cls: Ext.baseCSSPrefix + 'html-editor-tip'
            },
            justifycenter: {
                title: 'Zarovnat na střed',
                text: 'Zarovn&aacute; text na střed.',
                cls: Ext.baseCSSPrefix + 'html-editor-tip'
            },
            justifyright: {
                title: 'Zarovnat text vpravo',
                text: 'Zarovn&aacute; text doprava.',
                cls: Ext.baseCSSPrefix + 'html-editor-tip'
            },
            insertunorderedlist: {
                title: 'Odr&aacute;žky',
                text: 'Začne seznam s odr&aacute;žkami.',
                cls: Ext.baseCSSPrefix + 'html-editor-tip'
            },
            insertorderedlist: {
                title: 'Č&iacute;slov&aacute;n&iacute;',
                text: 'Začne č&iacute;slovaný seznam.',
                cls: Ext.baseCSSPrefix + 'html-editor-tip'
            },
            createlink: {
                title: 'Internetový odkaz',
                text: 'Z vybran&eacute;ho textu vytvoř&iacute; internetový odkaz.',
                cls: Ext.baseCSSPrefix + 'html-editor-tip'
            },
            sourceedit: {
                title: 'Zdrojový k&oacute;d',
                text: 'Přepne do m&oacute;du &uacute;pravy zdrojov&eacute;ho k&oacute;du.',
                cls: Ext.baseCSSPrefix + 'html-editor-tip'
            }
        }
    });
});

Ext.define("Ext.locale.cs.grid.header.Container", {
    override: "Ext.grid.header.Container",
    sortAscText: "Řadit vzestupně",
    sortDescText: "Řadit sestupně",
    lockText: "Ukotvit sloupec",
    unlockText: "Uvolnit sloupec",
    columnsText: "Sloupce"
});

Ext.define("Ext.locale.cs.grid.GroupingFeature", {
    override: "Ext.grid.GroupingFeature",
    emptyGroupText: '(Ž&aacute;dn&aacute; data)',
    groupByText: 'Seskupit dle tohoto pole',
    showGroupsText: 'Zobrazit ve skupině'
});

Ext.define("Ext.locale.cs.grid.PropertyColumnModel", {
    override: "Ext.grid.PropertyColumnModel",
    nameText: "N&aacute;zev",
    valueText: "Hodnota",
    dateFormat: "j.m.Y"
});

Ext.define("Ext.locale.cs.window.MessageBox", {
    override: "Ext.window.MessageBox",
    buttonText: {
        ok: "OK",
        cancel: "Storno",
        yes: "Ano",
        no: "Ne"
    }    
});

// This is needed until we can refactor all of the locales into individual files
Ext.define("Ext.locale.cs.Component", {	
    override: "Ext.Component"
});

