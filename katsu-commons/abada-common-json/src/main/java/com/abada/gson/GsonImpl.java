/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.gson;

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

import com.abada.gson.exclusionstrategy.MvcExclusionStrategy;
import com.abada.json.Json;
import com.abada.json.JsonProperty;
import com.abada.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Properties;

/**
 *
 * @author katsu
 */
public class GsonImpl implements Json {

    private Gson gson;
    private Properties properties;

    public GsonImpl() {
        this(null);
    }

    public GsonImpl(Properties properties) {
        this.setProperties(properties);
    }

    @Override
    public String serialize(Object obj) {
        return gson.toJson(obj);
    }

    @Override
    public void serialize(Object obj, Writer writer) throws IOException {
        gson.toJson(obj, writer);
    }

    @Override
    public <T> T deserialize(String jsonObject,Type type) {
        return gson.fromJson(jsonObject,type);
    }

    @Override
    public <T> T deserialize(Reader reader,Type type) throws IOException {
        return gson.fromJson(reader, type);
    }

    @Override
    public void setProperties(Properties properties) {
        this.properties = properties;
        createGson();
    }
    
    
    @Override
    public <T> T deserialize(String jsonObject, Class<T> clazz) {
        return gson.fromJson(jsonObject, clazz);
    }

    @Override
    public <T> T deserialize(Reader reader, Class<T> clazz) throws IOException{
        return deserialize(reader, clazz);
    }

    private void createGson() {
        GsonBuilder gsonBuilder = new GsonBuilder().addSerializationExclusionStrategy(new MvcExclusionStrategy());                
        if (properties != null) {
            if (properties.containsKey(JsonProperty.DATE_FORMAT.getValue())) {
                gsonBuilder = gsonBuilder.setDateFormat(properties.getProperty(JsonProperty.DATE_FORMAT.getValue()));
            } else {
                gsonBuilder = gsonBuilder.setDateFormat(Constants.DATE_PATTERN);
            }
            if (!Constants.TRUE.equals(properties.getProperty(JsonProperty.HTML_ESCAPE.getValue()))) {
                gsonBuilder = gsonBuilder.disableHtmlEscaping();
            }
        }else{
            gsonBuilder = gsonBuilder.setDateFormat(Constants.DATE_PATTERN);
        }
        this.gson = gsonBuilder.create();
    }

    public void setJsonProperties(Map<JsonProperty, String> jsonProperties) {
        Properties properties = new Properties();
        if (jsonProperties != null) {
            for (JsonProperty key : jsonProperties.keySet()) {
                properties.setProperty(key.getValue(), jsonProperties.get(key));
            }
        }
        this.setProperties(properties);
    }

    @Override
    public void serialize(Object obj, Writer writer, Class view) throws IOException {
        serialize(obj, writer);
    }
}
