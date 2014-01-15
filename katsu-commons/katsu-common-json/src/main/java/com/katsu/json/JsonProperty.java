/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.katsu.json;

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

/**
 *
 * @author katsu
 */
public enum JsonProperty {
    DATE_FORMAT("json.date.format"),
    HTML_ESCAPE("json.string.html.escape");

    private final String value;
    private JsonProperty(String value) {
        this.value=value;
    }

    public String getValue() {
        return value;
    }        
}
