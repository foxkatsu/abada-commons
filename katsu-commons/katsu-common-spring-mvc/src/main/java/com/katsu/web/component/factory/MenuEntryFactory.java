/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.katsu.web.component.factory;

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

import org.springframework.web.servlet.ModelAndView;

/**
 * Interfaz que debe implementar la clase que cree las entradas del menu de la pagina Web
 * @author katsu
 */
public interface MenuEntryFactory {
    public static final String USER_MENU="usermenu";
    public static final String ADMIN_MENU="adminmenu";
    public static final String USERNAME="username";
    public static final String NAME="name";
    public static final String ROLE="role";

    /**
     * @param modelAndView
     * @param user Objeto con los datos del usuario
     */
    public void createMenu(ModelAndView modelAndView,Object user);
}
