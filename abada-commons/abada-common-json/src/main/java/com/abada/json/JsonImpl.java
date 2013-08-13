/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.json;

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

import com.abada.utils.Constants;
import com.abada.json.serializer.Serializer;
import com.abada.json.serializer.SerializerFactory;
import java.io.*;
import java.lang.reflect.Type;
import java.util.Properties;

/**
 * Implementa la interfaz {@link Json}.
 *
 * @author katsu
 */
@Deprecated
public class JsonImpl implements Json {

    private Properties properties;

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public JsonImpl() {
        this(null);
    }

    public JsonImpl(Properties properties) {
        this.properties = properties;
    }

    /**
     * Devuelve un string con la representacin en Json
     *
     * @param obj Objeto a serializar
     * @return
     */
    @Override
    public String serialize(Object obj) {        
        StringWriter writer = new StringWriter();
        try {
            serialize(obj, writer);
            writer.flush();
            return writer.toString();
        } catch (IOException e) {
        }
        return Constants.CURLY_BRACKETS;
    }

    @Override
    public void serialize(Object obj, Writer writer) throws IOException {
        if (writer != null) {
            //Busca el serializador corrector para el objeto
            Serializer serializer = SerializerFactory.getSerializer(obj);
            if (serializer != null) {
                //Y lo serializa
                serializer.parse(obj, properties, writer);
            }
        }
    }

    @Override
    public <T> T deserialize(String jsonObject,Type type) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <T> T deserialize(Reader jsonObject,Type type) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <T> T deserialize(String jsonObject, Class<T> clazz) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <T> T deserialize(Reader reader, Class<T> clazz) throws IOException{
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void serialize(Object obj, Writer writer, Class view) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
