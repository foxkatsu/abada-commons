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
 * Vietnamese translation
 * By bpmtri
 * 12-April-2007 04:06PM
 */
Ext.onReady(function() {

    if (Ext.Date) {
        Ext.Date.monthNames = ["Th&aacute;ng 1", "Th&aacute;ng 2", "Th&aacute;ng 3", "Th&aacute;ng 4", "Th&aacute;ng 5", "Th&aacute;ng 6", "Th&aacute;ng 7", "Th&aacute;ng 8", "Th&aacute;ng 9", "Th&aacute;ng 10", "Th&aacute;ng 11", "Th&aacute;ng 12"];

        Ext.Date.dayNames = ["Chủ nhật", "Thứ hai", "Thứ ba", "Thứ tư", "Thứ năm", "Thứ s&aacute;u", "Thứ bảy"];
        
        Ext.Date.monthNumbers = {
            "Th&aacute;ng 1": 0,
            "Th&aacute;ng 2": 1,
            "Th&aacute;ng 3": 2,
            "Th&aacute;ng 4": 3,
            "Th&aacute;ng 5": 4,
            "Th&aacute;ng 6": 5,
            "Th&aacute;ng 7": 6,
            "Th&aacute;ng 8": 7,
            "Th&aacute;ng 9": 8,
            "Th&aacute;ng 10": 9,
            "Th&aacute;ng 11": 10,
            "Th&aacute;ng 12": 11,
        };
        
        Ext.Date.getShortMonthName = function(month){
            return Ext.Date.monthNames[month];
        };
        
        Ext.Date.getMonthNumber = function(name){
            return Ext.Date.monthNumbers[name];    
        };
        
        Ext.Date.getShortDayName = function(day) {
            return Ext.Date.dayNames[day];
        }
    }

    if (Ext.util && Ext.util.Format) {
        Ext.apply(Ext.util.Format, {
            thousandSeparator: '.',
            decimalSeparator: ',',
            currencySign: '\u20ab',
            // Vietnamese Dong
            dateFormat: 'd/m/Y'
        });
    }
});

Ext.define("Ext.locale.vn.view.View", {
    override: "Ext.view.View",
    emptyText: ""
});

Ext.define("Ext.locale.vn.grid.plugin.DragDrop", {
    override: "Ext.grid.plugin.DragDrop",
    dragText: "{0} dòng được chọn"
});

Ext.define("Ext.locale.vn.tab.Tab", {
    override: "Ext.tab.Tab",
    closeText: "Đ&oacute;ng thẻ này"
});

Ext.define("Ext.locale.vn.form.field.Base", {
    override: "Ext.form.field.Base",
    invalidText: "Gi&aacute; trị của ô này không hợp lệ."
});

// changing the msg text below will affect the LoadMask
Ext.define("Ext.locale.vn.view.AbstractView", {
    override: "Ext.view.AbstractView",
    loadingText: "Đang tải..."
});

Ext.define("Ext.locale.vn.picker.Date", {
    override: "Ext.picker.Date",
    todayText: "Hôm nay",
    minText: "Ngày này nhỏ hơn ngày nhỏ nhất",
    maxText: "Ngày này lớn hơn ngày lớn nhất",
    disabledDaysText: "",
    disabledDatesText: "",
    nextText: 'Th&aacute;ng sau (Control+Right)',
    prevText: 'Th&aacute;ng trước (Control+Left)',
    monthYearText: 'Chọn một th&aacute;ng (Control+Up/Down để thay đổi năm)',
    todayTip: "{0} (Spacebar - Ph&iacute;m trắng)",
    format: "d/m/y"
});

Ext.define("Ext.locale.vn.toolbar.Paging", {
    override: "Ext.PagingToolbar",
    beforePageText: "Trang",
    afterPageText: "of {0}",
    firstText: "Trang đầu",
    prevText: "Trang trước",
    nextText: "Trang sau",
    lastText: "Trang cuối",
    refreshText: "Tải lại",
    displayMsg: "Hiển thị {0} - {1} của {2}",
    emptyMsg: 'Không c&oacute; dữ liệu để hiển thị'
});

Ext.define("Ext.locale.vn.form.field.Text", {
    override: "Ext.form.field.Text",
    minLengthText: "Chiều dài tối thiểu của ô này là {0}",
    maxLengthText: "Chiều dài tối đa của ô này là {0}",
    blankText: "Ô này cần phải nhập gi&aacute; trị",
    regexText: "",
    emptyText: null
});

Ext.define("Ext.locale.vn.form.field.Number", {
    override: "Ext.form.field.Number",
    minText: "Gi&aacute; trị nhỏ nhất của ô này là {0}",
    maxText: "Gi&aacute; trị lớn nhất của ô này là  {0}",
    nanText: "{0} hông phải là một số hợp lệ"
});

Ext.define("Ext.locale.vn.form.field.Date", {
    override: "Ext.form.field.Date",
    disabledDaysText: "Vô hiệu",
    disabledDatesText: "Vô hiệu",
    minText: "Ngày nhập trong ô này phải sau ngày {0}",
    maxText: "Ngày nhập trong ô này phải trước ngày {0}",
    invalidText: "{0} không phải là một ngày hợp lệ - phải c&oacute; dạng {1}",
    format: "d/m/y"
});

Ext.define("Ext.locale.vn.form.field.ComboBox", {
    override: "Ext.form.field.ComboBox",
    valueNotFoundText: undefined
}, function() {
    Ext.apply(Ext.form.field.ComboBox.prototype.defaultListConfig, {
        loadingText: "Đang tải..."
    });
});

Ext.define("Ext.locale.vn.form.field.VTypes", {
    override: "Ext.form.field.VTypes",
    emailText: 'Gi&aacute; trị của ô này phải là một địa chỉ email c&oacute; dạng như "ten@abc.com"',
    urlText: 'Gi&aacute; trị của ô này phải là một địa chỉ web(URL) hợp lệ, c&oacute; dạng như "http:/' + '/www.example.com"',
    alphaText: 'Ô này chỉ được nhập c&aacute;c k&iacute; tự và gạch dưới(_)',
    alphanumText: 'Ô này chỉ được nhập c&aacute;c k&iacute; tự, số và gạch dưới(_)'
});

Ext.define("Ext.locale.vn.grid.header.Container", {
    override: "Ext.grid.header.Container",
    sortAscText: "Tăng dần",
    sortDescText: "Giảm dần",
    lockText: "Kh&oacute;a cột",
    unlockText: "Bỏ kh&oacute;a cột",
    columnsText: "C&aacute;c cột"
});

Ext.define("Ext.locale.vn.grid.PropertyColumnModel", {
    override: "Ext.grid.PropertyColumnModel",
    nameText: "Tên",
    valueText: "Gi&aacute; trị",
    dateFormat: "j/m/Y"
});

Ext.define("Ext.locale.vn.window.MessageBox", {
    override: "Ext.window.MessageBox",
    buttonText: {
        ok: "Đồng ý",
        cancel: "Hủy bỏ",
        yes: "C&oacute;",
        no: "Không"
    }    
});

// This is needed until we can refactor all of the locales into individual files
Ext.define("Ext.locale.vn.Component", {	
    override: "Ext.Component"
});

