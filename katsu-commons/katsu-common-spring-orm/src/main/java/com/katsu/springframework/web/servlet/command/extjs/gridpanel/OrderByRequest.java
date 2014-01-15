/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.katsu.springframework.web.servlet.command.extjs.gridpanel;

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
 *
 * @author katsu
 */
@NoOfuscation
public class OrderByRequest {
    private String property;
    private String direction;       

    public OrderByRequest() {
    }
    
    public OrderByRequest(String property, String direction) {
        this.property = property;
        this.direction = direction;
    }
    
    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }        
}
