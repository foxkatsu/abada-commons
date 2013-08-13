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

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.Properties;

/**
 * Api que implementa el serializador de Json.
 * @author katsu
 */
public interface Json {
    /**
     * Devuelve un string con la representacin en Json
     * @param obj Objeto a serializar
     * @return
     */
    public String serialize(Object obj);
    /**
     * Devuelve un string con la representacin en Json
     * @param obj Objeto a serializar
     * @return
     */
    public void serialize(Object obj,Writer writer) throws IOException;
    
    public void serialize(Object obj,Writer writer,Class view) throws IOException;
    
    public <T> T deserialize(String jsonObject,Type type);
    
    public <T> T deserialize(Reader reader,Type type) throws IOException;
    
    public <T> T deserialize(String jsonObject,Class<T> clazz);
    
    public <T> T deserialize(Reader reader,Class<T> clazz) throws IOException;        
    
    public void setProperties(Properties properties);
}
