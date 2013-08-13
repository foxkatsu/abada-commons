/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.dwm;

/*
 * #%L
 * Abada DWM
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

/**
 *
 * @author katsu
 */
public enum LoaderConst {
    WEB_LOCATION("module.web"),
    CONTEXT_LOCATION("module.springContext"),
    MODULE_PROPERTIES("META-INF/com.abada.web.module.config.properties"),
    MODULE_NAME("module.name"),
    MODULE_JNDI("module.jndi");
    
    private final String value;
    LoaderConst(String value){
        this.value=value;
    }
    
    public String getValue(){
        return value;
    }
}
