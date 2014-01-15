/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.katsu.springframework.web.servlet.menu;

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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Add this annotation in the method that has {@link org.springframework.web.bind.annotation.RequestMapping}
 * and you want to show in main menu as an entry
 * @author katsu
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface MenuEntry {
    /**
     * URL for icon
     * @return
     */
    String icon() default "";    
    /**
     * Order in menu group
     * @return
     */
    int order() default 0;
    /**
     * Text for menu entry, if in message Properties are a localice text for text then this will show it instead this
     * @return
     */
    String text();
    /**
     * For group menu entries
     * @return
     */
    String menuGroup() default "";
    /**
     * Order for menu group in the global menu
     * @return
     */
    int orderGroup() default -1;
    /**
     * For group menu entries
     * @return
     */
    String iconGroup() default "";
    /**
     * Devices thar must see this entry
     * @return 
     */
    Device [] devices() default {Device.DESKTOP};
    
}
