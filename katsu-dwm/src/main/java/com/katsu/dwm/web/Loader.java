/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.katsu.dwm.web;

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

import com.katsu.dwm.LoaderConst;
import com.katsu.dwm.reflection.JarUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author katsu
 */
public class Loader {
    
    private Log logger = LogFactory.getLog(Loader.class);    
    
    public void load(File f, Properties prop) {
        String path = prop.getProperty(LoaderConst.WEB_LOCATION.getValue());        
        if (path != null) {
            createPath(path);
            List<JarEntry> jarEntries = JarUtils.getJarContent(f, path);
            if (jarEntries != null) {
                for (JarEntry je : jarEntries) {
                    copyContent(f, je);
                }
            }
        }
    }
    
    private File createPath(String path) {
        File root = JarUtils.getRootApplicationContentPath();
        File result = new File(root,path.replace('.', File.separatorChar));
        if (result.exists()){
            result.delete();
        }
        if (result.mkdirs()) {
            logger.trace("Create path: " + result.getPath());
            return result;
        }
        return null;
    }
    
    private void copyContent(File f, JarEntry je) {
        File rootPath = JarUtils.getRootApplicationContentPath();
        File temp = new File(rootPath, je.getName());
        if (je.isDirectory()) {            
            logger.debug("Create path: " + temp.mkdirs() + " " + temp.getPath());
        } else {
            InputStream is = null;
            FileOutputStream fos = null;
            try {
                JarFile jf = new JarFile(f);
                is = jf.getInputStream(je);
                if (!temp.exists()) {
                    temp.createNewFile();
                }
                fos = new FileOutputStream(temp);
                byte[] array = new byte[1024];
                int readed;
                while ((readed = is.read(array)) != -1) {
                    fos.write(array, 0, readed);
                }
            } catch (Exception e) {
                logger.error(e);
            } finally {
                try {
                    if (is != null) {
                        is.close();
                    }
                } catch (IOException ex) {
                }
                try {
                    if (fos != null) {
                        fos.close();
                    }
                } catch (IOException ex) {
                }
            }
        }
    }
}
