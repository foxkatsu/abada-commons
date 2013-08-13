/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.dwm.jndi.datasource;

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

import com.abada.dwm.jndi.xml.Resource;
import javax.sql.DataSource;
import org.apache.commons.dbcp.BasicDataSource;

/**
 *
 * @author katsu
 */
public class DataSourceFactory {
    public static DataSource getDataSource(Resource resource){
        BasicDataSource result=new BasicDataSource();
        result.setMaxActive(resource.getMaxActive());
        result.setMaxIdle(resource.getMaxIdle());
        result.setPassword(resource.getPassword());
        result.setUsername(resource.getUsername());
        result.setUrl(resource.getUrl());
        result.setDriverClassName(resource.getDriverClassName());        
        return result;
    }
}
