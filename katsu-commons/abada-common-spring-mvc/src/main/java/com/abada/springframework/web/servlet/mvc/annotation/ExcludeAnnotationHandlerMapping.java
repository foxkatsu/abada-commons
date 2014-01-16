/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.abada.springframework.web.servlet.mvc.annotation;

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

import java.util.Set;
import org.springframework.web.servlet.mvc.annotation.DefaultAnnotationHandlerMapping;

/**
 * Manejador de llamadas url para el MVC de Spring.<br/>
 * Tiene la caracteristica que acepta un listado de url que no debe manejar, lista negra.
 * Usado para porder aplicar distintos interceptores de llamadas de url mediante el uso de
 * un manejador normal de Spring y de varios {@link ExcludeAnnotationHandlerMapping}
 * @author katsu
 */
public class ExcludeAnnotationHandlerMapping extends DefaultAnnotationHandlerMapping{
    private Set<String> exclude;

    public void setExclude(Set<String> exclude) {
        this.exclude = exclude;
    }

    @Override
    protected void addUrlsForPath(Set<String> urls, String path) {
        if (exclude!=null && exclude.size()>0){
            if (!exclude.contains(path))
                super.addUrlsForPath(urls, path);
        }else{
            super.addUrlsForPath(urls, path);
        }
    }

}
