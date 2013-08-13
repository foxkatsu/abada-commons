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

import com.abada.dwm.reflection.JarUtils;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 *
 * @author katsu
 */
public class LoaderUtils {

    public static Map<File, Properties> findModules() {
        Map<File, Properties> result = new HashMap<File, Properties>();
        List<File> jars = JarUtils.getJarFiles();
        for (File f : jars) {
            InputStream is = JarUtils.getResourceAsStream(f, LoaderConst.MODULE_PROPERTIES.getValue());
            if (is != null) {
                try {
                    Properties prop = new Properties();
                    prop.load(is);
                    result.put(f, prop);
                } catch (IOException e) {
                    //FIXME logger.error("Incorrect Properties in " + f.getPath());
                }
            }
        }
        return result;
    }
}
