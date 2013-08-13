/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.dwm.jndi;

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
import com.abada.dwm.jndi.datasource.DataSourceFactory;
import com.abada.dwm.jndi.xml.Resource;
import com.abada.dwm.jndi.xml.ResourceParser;
import com.abada.dwm.reflection.JarUtils;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import javax.naming.NamingException;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jndi.JndiTemplate;
import org.xml.sax.SAXException;

/**
 *
 * @author katsu
 */
public class Loader {
    private static Log logger=LogFactory.getLog(Loader.class);

    public void load(File jar, Properties prop) throws ParserConfigurationException, SAXException, IOException {
        String jndi=prop.getProperty(LoaderConst.MODULE_JNDI.getValue());
        if (jndi!=null){      
            InputStream is=JarUtils.getResourceAsStream(jar, jndi);
            if (is!=null){
                List<Resource> resources=ResourceParser.parse(is);
                JndiTemplate jndiTemplate=new JndiTemplate();
                for (Resource res:resources){
                    Object ds=DataSourceFactory.getDataSource(res);
                    try{
                        jndiTemplate.bind("java:comp/env/"+res.getName(), ds);
                    }catch (NamingException e){
                        logger.warn("DataSource is not registered: "+e);
                    }
                    logger.trace("Registered JNDI DataSource java:comp/env/"+res.getName());
                }            
            }
        }
    }        
}
