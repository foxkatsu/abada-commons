/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.katsu.dwm;

/*
 * #%L
 * Katsu DWM
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

import java.io.File;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author katsu
 */
public class Loader{
    private static final Log logger = LogFactory.getLog(Loader.class);

    private com.katsu.dwm.web.Loader webLoader;
    private com.katsu.dwm.jndi.Loader jndiLoader;

    public com.katsu.dwm.web.Loader getWebLoader() {
        if (webLoader == null) {
            webLoader = new com.katsu.dwm.web.Loader();
        }
        return webLoader;
    }

    public com.katsu.dwm.jndi.Loader getJndiLoader() {
        if (jndiLoader == null) {
            jndiLoader = new com.katsu.dwm.jndi.Loader();
        }
        return jndiLoader;
    }        

    public void load() {
        //Look for properties in jars
        Map<File, Properties> map = LoaderUtils.findModules();
        for (File f : map.keySet()) {
            try {
                this.getWebLoader().load(f, map.get(f));
                this.getJndiLoader().load(f, map.get(f));                    
                logger.debug("Loaded dynamic web module " + f.getPath());
            } catch (Exception e) {
                logger.error("Error loading dynamic web module. " + f.getPath() + " " + e);
            }
        }

    }
}
