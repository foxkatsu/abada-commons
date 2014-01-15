/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.katsu.json.serializer;

/*
 * #%L
 * Katsu Commons
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

import com.katsu.utils.Constants;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import java.util.Properties;

/**
 * Serializador Json<br/>
 * Usado para objectos {@link Map} y todos aquellos que hereden de ella
 * @author katsu
 */
class MapSerializer implements Serializer {

    @Override
    public void parse(Object obj,Properties properties,Writer writer) throws IOException{        
        Object o;
        //Caracter inicio
        writer.append(Constants.OPEN_CURLY_BRACKET);
        Map c = (Map) obj;
        int i=0;
        for (Object k : c.keySet()) {
            //sk = SerializerFactory.getSerializer(k);
            o = c.get(k);            
            writer.append(Constants.DOUBLE_MARK);
            SerializerFactory.getSerializer(k).parse(k, properties, writer);
            writer.append(Constants.DOUBLE_MARK);
            writer.append(Constants.COLON);
            SerializerFactory.getSerializer(o).parse(o, properties, writer);
            if (i < c.size()-1) {
                writer.append(Constants.COMMA);
            }  
        }
        writer.append(Constants.CLOSE_CURLY_BRACKET);
    }
}
