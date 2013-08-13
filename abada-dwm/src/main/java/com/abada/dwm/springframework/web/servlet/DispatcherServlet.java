/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.dwm.springframework.web.servlet;

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

import com.abada.dwm.LoaderConst;
import com.abada.dwm.LoaderUtils;
import java.io.File;
import java.util.Map;
import java.util.Properties;

/**
 *
 * @author katsu
 */
public class DispatcherServlet extends org.springframework.web.servlet.DispatcherServlet {
    @Override
    public String getContextConfigLocation() {
        String result="";
        Map<File,Properties> map=LoaderUtils.findModules();
        for (File f:map.keySet()){
            result+=map.get(f).getProperty(LoaderConst.CONTEXT_LOCATION.getValue())+",";
        }
        return result+super.getContextConfigLocation();
    }        
}
