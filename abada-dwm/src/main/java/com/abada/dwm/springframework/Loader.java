/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.dwm.springframework;

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
import java.io.File;
import java.net.MalformedURLException;
import java.util.Properties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.SourceFilteringListener;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

/**
 *
 * @author katsu
 */
@Deprecated
public class Loader {

    private static final Log logger = LogFactory.getLog(Loader.class);
    private ApplicationContext applicationContext;

    public Loader(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void load(File jar, Properties properties) throws MalformedURLException {
        ConfigurableWebApplicationContext wac = (ConfigurableWebApplicationContext) BeanUtils.instantiateClass(XmlWebApplicationContext.class);
        //FIXME
        wac.setId("test-context");
        wac.setParent(applicationContext);
        wac.setServletContext(((XmlWebApplicationContext) applicationContext).getServletContext());
        //wac.setServletConfig(((XmlWebApplicationContext) applicationContext).getServletConfig());
        //wac.setNamespace(((XmlWebApplicationContext) applicationContext).getNamespace());
        wac.setConfigLocation(properties.getProperty(LoaderConst.CONTEXT_LOCATION.getValue()));
        //wac.addApplicationListener(new SourceFilteringListener(wac, new ContextRefreshListener()));
        wac.refresh();
        
        wac.getServletContext().setAttribute("test-context", wac);
    }

    /**
     * ApplicationListener endpoint that receives events from this servlet's WebApplicationContext
     * only, delegating to <code>onApplicationEvent</code> on the FrameworkServlet instance.
     */
    private class ContextRefreshListener implements ApplicationListener<ContextRefreshedEvent> {

        public void onApplicationEvent(ContextRefreshedEvent event) {           
            //FrameworkServlet.this.onApplicationEvent(event);
        }
    }
}
