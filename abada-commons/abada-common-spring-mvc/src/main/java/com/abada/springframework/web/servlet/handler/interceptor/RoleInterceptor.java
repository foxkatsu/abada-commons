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

import com.abada.springframework.web.servlet.handler.interceptor.annotation.Role;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * Interceptor para el MVC de Spring con el que se controla el acceso a las paginas
 * dependiendo del role que tenga el usuario y el rolo que haga falta para acceder
 * @author katsu
 */
public class RoleInterceptor extends HandlerInterceptorAdapter {

    private final static Log logger = LogFactory.getLog(RoleInterceptor.class);
    /**
     * Nonmbre bajo el que estan los datos del role en la sesion http
     */
    private String nameRoleAttribute;
    /**
     * URL a la que te redirige cuando no se cumple que tienes el role correcto,
     * suele implicar que te destruyen la session
     */
    private String urlError;

    public void setNameRoleAttribute(String nameRoleAttribute) {
        this.nameRoleAttribute = nameRoleAttribute;
    }

    public void setUrlError(String urlError) {
        this.urlError = urlError;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean result = super.preHandle(request, response, handler);
        if (!result) {
            return result;
        }
        //buscar la anotacion Role y ver que role se necesita para arrancar
        //primero se busca a el role a nivel de clase
        Role role = handler.getClass().getAnnotation(Role.class);
        //TODO hacer la comprobacion de role a nivel de metodo
        if (role != null && role.value() != null && role.value().length > 0) {
            result = false;
            logger.debug(handler + " need roles: " + toString(role.value()));
            String[] roles = role.value();
            String roleSession = (String) request.getSession().getAttribute(nameRoleAttribute);
            if (roleSession != null) {
                for (String r : roles) {
                    if (r.equalsIgnoreCase(roleSession)) {
                        result = true;
                    }
                }
                if (!result) {
                    logger.debug("User with incorrect role.");
                }
            } else {
                result = false;
                logger.error("User without role.");
            }
        } else {
            logger.debug(handler + " don't has roles");
        }
        if (!result && urlError != null) {
            response.sendRedirect(urlError);
        }
        return result;
    }

    private String toString(String[] value) {
        StringBuilder result = new StringBuilder("");
        for (String a : value) {
            result.append(a);
            result.append(",");
        }
        return result.toString();
    }
}
