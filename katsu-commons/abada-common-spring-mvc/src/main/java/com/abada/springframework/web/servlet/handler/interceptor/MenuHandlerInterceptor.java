/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.abada.springframework.web.servlet.handler.interceptor;

/*
 * #%L
 * Abada Commons
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

import com.abada.web.component.factory.MenuEntryFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * Interceptor para el MVC de Spring usado para generar el menu
 * @author katsu
 */
public class MenuHandlerInterceptor extends HandlerInterceptorAdapter{
    private static final Log logger=LogFactory.getLog(MenuHandlerInterceptor.class);
    /**
     * Instancia de la factororia que va a generar el menu a partir de los datos
     * del usuario conectado
     */
    private MenuEntryFactory entryFactory;

    public void setEntryFactory(MenuEntryFactory entryFactory) {
        this.entryFactory = entryFactory;
    }
    /**
     * nombre bajo el que estan los datos del usuario en la sesion http
     */
    private String userAttribute;

    public void setUserAttribute(String userAttribute) {
        this.userAttribute = userAttribute;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);

        if (entryFactory!=null && userAttribute!=null && !"".equals(userAttribute)){
            logger.debug("Creating Menus Entrys");
            Object user=(Object) request.getSession().getAttribute(userAttribute);
            if (user!=null)
                entryFactory.createMenu(modelAndView, user);
        }
    }

}
