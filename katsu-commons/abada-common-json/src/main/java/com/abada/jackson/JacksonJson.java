/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.jackson;

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

import com.abada.json.Json;
import com.abada.json.JsonProperty;
import com.abada.utils.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Properties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author katsu
 */
public class JacksonJson implements Json {

    private static final Log logger = LogFactory.getLog(JacksonJson.class);
    private ObjectMapper objectMapper;
    private Properties properties;

    public JacksonJson(Properties properties) {
        this.setProperties(properties);
    }

    public JacksonJson() {
        this(null);
    }
    
    @Override
    public String serialize(Object obj) {
        if (obj==null) return null;
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public void serialize(Object obj, Writer writer) throws IOException {
        if (obj==null) return;
        try {
            objectMapper.writeValue(writer, obj);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public <T> T deserialize(String jsonObject, Type type) {
        if (jsonObject==null) return null;
        try {
            return (T) objectMapper.readValue(jsonObject, objectMapper.constructType(type));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public <T> T deserialize(Reader reader, Type type) throws IOException {        
        try {
            return (T) objectMapper.readValue(reader, objectMapper.constructType(type));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public <T> T deserialize(String jsonObject, Class<T> clazz) {
        if (jsonObject==null) return null;
        try {
            return (T) objectMapper.readValue(jsonObject, clazz);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public <T> T deserialize(Reader reader, Class<T> clazz) throws IOException {
        try {
            return (T) objectMapper.readValue(reader, clazz);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public void setProperties(Properties properties) {
        this.properties = properties;
        createJackson();
    }

    private void createJackson() {
        this.objectMapper=new ObjectMapper();
        if (properties != null) {
            if (properties.containsKey(JsonProperty.DATE_FORMAT.getValue())) {
                objectMapper.setDateFormat(new SimpleDateFormat(properties.getProperty(JsonProperty.DATE_FORMAT.getValue())));
            } else {
                objectMapper.setDateFormat(new SimpleDateFormat(Constants.DATE_PATTERN));
            }
        }else{
            objectMapper.setDateFormat(new SimpleDateFormat(Constants.DATE_PATTERN));
        }              
    }

    @Override
    public void serialize(Object obj, Writer writer, Class view) throws IOException {
       objectMapper.writerWithView(view).writeValue(writer, obj);
    }
}
