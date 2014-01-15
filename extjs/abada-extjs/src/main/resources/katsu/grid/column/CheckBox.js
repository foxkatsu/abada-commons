/*
 * #%L
 * Katsu ExtJs
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
Ext.define('Katsu.grid.column.CheckBox', {
  extend: 'Ext.grid.column.Template',
  alias: 'widget.checkboxcolumn',
  constructor: function(cfg) {
    var me = this;
    me.tpl = ['<tpl for=".">','placeholder','</tpl>']; // shouldn't
    // ever be used. Ignore it.
    var tplChecked = Ext.create('Ext.XTemplate', [
      '<tpl for=".">',
      '<div class="x-field x-form-item x-field-default x-form-cb-checked">',
      '<div class="x-form-item-body x-form-cb-wrap" role="presentation">',
      '<input class="x-form-field x-form-checkbox" type="button" hidefocus="true" autocomplete="off" aria-checked="false" aria-invalid="false" role="checkbox" data-errorqtip="">',
      '</div>',
      '</div>',
      '</tpl>'
    ]);
    var tplUnchecked = Ext.create('Ext.XTemplate', [
      '<tpl for=".">',
      '<div class="x-field x-form-item x-field-default">',
      '<div class="x-form-item-body x-form-cb-wrap" role="presentation">',
      '<input class="x-form-field x-form-checkbox" type="button" hidefocus="true" autocomplete="off" aria-checked="false" aria-invalid="false" role="checkbox" data-errorqtip="">',
      '</div>',
      '</div>',
      '</tpl>'
    ]);

    me.callParent(arguments);
    me.renderer = function(value, p, record) {
      var data = Ext.apply({}, record.data, record.getAssociatedData());
      if (data[me.dataIndex]) {
        return tplChecked.apply(data);
      } else {
        return tplUnchecked.apply(data);
      }
    };

  }/*,
    processEvent : function(type, view, cell, recordIndex, cellIndex, e){
      var me = this;
      if (type == 'click') {
        var rec = view.getStore().getAt(recordIndex);
        rec.set(this.dataIndex, (rec.get(this.dataIndex))?false:true);
      } else if (type == 'mousedown') {
        return false;
      }
      return me.callParent(arguments);
    }*/
});  