/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.katsu.web.component;

/*
 * #%L
 * Katsu Commons
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

import com.katsu.ofuscation.annotation.NoOfuscation;

/**
 * Clase usada para representar la informacion de cada entrada del menu de la pagina
 * Web
 * @author katsu
 */
@NoOfuscation
public class MenuEntry {
    /**
     * Texto a mostrar
     */
    private String text;
    /**
     * Url a la que debe redirigirte si se pincha en el menu
     */
    private String action;
    /**
     * Y clase del CSS que se le aplica a la entrada de menu. Usado para ponerle una imagen
     */
    private String iconCls;
    /**
     * Texto que aparecera cuando el raton este sobre la entrada de menú
     */
    private String tooltip;

    public MenuEntry() {
        this.setAction("");
        this.setIconCls("");
        this.setText("");
        this.setTooltip("");
    }

    public String getTooltip() {
        return tooltip;
    }

    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }
}
