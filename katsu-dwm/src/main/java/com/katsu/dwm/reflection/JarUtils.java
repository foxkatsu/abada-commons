/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.katsu.dwm.reflection;

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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author katsu
 */
public class JarUtils {

    private static final Log logger = LogFactory.getLog(JarUtils.class);

    /**
     * Return a list of resources inside the jar file that their URL start with path
     * @param jar
     * @param path
     * @return 
     */
    public static List<JarEntry> getJarContent(File jar, String path) {
        try {
            JarFile jf=new JarFile(jar);
            Enumeration<JarEntry> enu=jf.entries();
            List<JarEntry> result=new ArrayList<JarEntry>();
            while (enu.hasMoreElements()){
                JarEntry je=enu.nextElement();                
                if (je.getName().startsWith(path))
                    result.add(je);
            }
            return result;
        } catch (Exception e) {
            logger.error(e);
        }
        return null;
    }

    public static File getRootApplicationContentPath(){
        URLClassLoader sysloader = (URLClassLoader) JarUtils.class.getClassLoader();
        URL[] urls = sysloader.getURLs();
        String classesPath="WEB-INF/classes/";
        if (urls != null) {
            for (URL url : urls) {
                try {                    
                    if (url.getPath().endsWith(classesPath)) {                                                                                                         
                        return new File(url.toURI()).getParentFile().getParentFile();
                    }
                } catch (Exception e) {
                    logger.error(e);
                }
            }
        }
        return null;
    }
    
    /**
     * Retrieve all jar files in directoy
     * @return array of jar {@link File Files}
     */
    public static List<File> getJarFiles() {
        URLClassLoader sysloader = (URLClassLoader) JarUtils.class.getClassLoader();
        URL[] urls = sysloader.getURLs();
        List<File> result = new ArrayList<File>();
        if (urls != null) {
            for (URL url : urls) {
                try {
                    logger.trace("URL detected: " + url);
                    if (url.getPath().toLowerCase().endsWith(".jar")) {
                        result.add(new File(url.toURI()));
                    }
                } catch (Exception e) {
                    logger.error(e);
                }
            }
        }
        return result;
    }

    /**
     * Return as stream a resource in a jar
     * @param jar
     * @param resource 
     */
    public static InputStream getResourceAsStream(File jar, String resource) {
        try {
            URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{jar.toURI().toURL()}, null);
            return urlClassLoader.getResourceAsStream(resource);
        } catch (Exception e) {
            logger.error(e);
        }
        return null;
    }

    /**
     * Return as stream a resource in a jar
     * @param jar
     * @param resource 
     */
    public static InputStream getResourceAsStreamFromJar(File jar, String resource) {
        JarInputStream jis = null;
        try {
            jis = new JarInputStream(new FileInputStream(jar));
            JarEntry je;
            while ((je = jis.getNextJarEntry()) != null) {
                logger.trace(jar.getName() + " " + je.getName());
                if (je.getName().equals(resource)) {
                    return jis;
                }
            }
        } catch (Exception ex) {
            logger.error(ex + " " + jar.getPath());
        } finally {
            if (jis != null) {
                try {
                    jis.close();
                } catch (IOException e) {
                    logger.error(e);
                }
            }
        }
        return null;
    }
}
