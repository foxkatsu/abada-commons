/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.katsu.springframework.web.servlet.handler.interceptor;

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

import com.katsu.springframework.web.servlet.handler.interceptor.annotation.SessionAttributesRequired;
import com.katsu.springframework.web.servlet.handler.interceptor.annotation.SessionRequired;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * Handler interceptor para controlar diversas cosas en la session<br/>
 * <ul>
 * <li>Si necesita que en la session apararezcan algunos parametros {@link SessionAttributesRequired}</li>
 * <li>Si necesita que exista session {@link SessionRequired}</li>
 * <li>Chequeo personalizado si se asigna un chequeador {@link SessionChecker}</li>*
 * </ul>
 * @author katsu
 */
public class SessionInterceptor extends HandlerInterceptorAdapter {
    private final static Log logger = LogFactory.getLog(SessionInterceptor.class);
    /**
     * URL a la que te redirige cuando no se cumple que tienes el role correcto,
     * suele implicar que te destruyen la session
     */
    private String urlError;
    private SessionChecker sessionChecker;

    public void setSessionChecker(SessionChecker sessionChecker) {
        this.sessionChecker = sessionChecker;
    }

    public void setUrlError(String urlError) {
        this.urlError = urlError;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {        
        if (!super.preHandle(request, response, handler)) return false;

        //Is session required
        SessionRequired sessionRequired = handler.getClass().getAnnotation(SessionRequired.class);
        SessionAttributesRequired sessionAttributesRequired =handler.getClass().getAnnotation(SessionAttributesRequired.class);
        if ((sessionRequired!=null||sessionAttributesRequired!=null) && request.getSession(false)==null) return doError(response, "Sesion Requerida.");

        //Is attributes in a session required
        if (sessionAttributesRequired!=null){
            if (sessionAttributesRequired.attributes().length>0){
                HttpSession httpSession=request.getSession(false);
                for (String att:sessionAttributesRequired.attributes()){
                    if (httpSession.getAttribute(att)==null)
                        return doError(response,"No se encuentra disponible el atributo requerdio "+att);
                }
            }
        }

        //Custom check for attributes
        if (this.sessionChecker!=null){
            try{
                if (!this.sessionChecker.isCorrectSession(request.getSession(false))) doError(response, "Sesion invalida.");
            }catch (Exception e){
                doError(response,e.getMessage());
            }
        }

        return true;
    }

    private boolean doError(HttpServletResponse response,String error) throws IOException{
        logger.error(error);
        //response.sendRedirect(urlError);
        return false;
    }
}
