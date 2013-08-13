/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.springframework.http.converter.json;

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
import com.abada.json.JsonFactory;
import com.abada.json.JsonProperty;
import com.abada.json.JsonType;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

/**
 *
 * @author katsu
 */
public class JsonHttpMessageConverter extends AbstractHttpMessageConverter<Object> {   

    private static final Log logger= LogFactory.getLog(JsonHttpMessageConverter.class);
    
    private Json json;       

    public JsonHttpMessageConverter() {
        super(MediaType.APPLICATION_JSON);
        json=JsonFactory.getInstance().getInstance(JsonType.DEFAULT);
    }
    
    public void setJsonProperties(Map<JsonProperty, String> jsonProperties) {
        Properties properties = new Properties();
        if (jsonProperties != null) {
            for (JsonProperty key : jsonProperties.keySet()) {
                properties.setProperty(key.getValue(), jsonProperties.get(key));
            }
        }
        this.json.setProperties(properties);
    }

    public Object deserialize(InputStream is,Type type){
        //Deserialize
        logger.trace("Deserialize "+type);
        try {            
            return json.deserialize(new InputStreamReader(is), type);
        } catch (IOException ex) {            
            logger.error(ex);
        }
        return null;
    }
    
    @Override
    protected boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    protected Object readInternal(Class<? extends Object> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        return deserialize(inputMessage.getBody(),clazz);
    }

    private String readAll(InputStream is){
        try{
            StringBuilder sb=new StringBuilder();
            byte [] aux=new byte[1024];
            int read;
            while ((read=is.read(aux))!=-1){
                sb.append(new String(aux,0,read));
            }
            return sb.toString();
        }catch (Exception e){
            logger.error(e);
        }finally{
            if (is!=null)
                try{is.close();}catch(Exception e){logger.error(e);}
        }
        return null;
    }
    
    @Override
    protected void writeInternal(Object t, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        //Serialize
        outputMessage.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        
        String aux=json.serialize(t);
        logger.trace("Serialized "+ aux);
        outputMessage.getBody().write(aux.getBytes());
        outputMessage.getBody().flush();
    }
}
