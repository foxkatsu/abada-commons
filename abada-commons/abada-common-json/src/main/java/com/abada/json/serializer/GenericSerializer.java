/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.json.serializer;

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
import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import java.util.Properties;

/**
 * Serializador Json<br/> Usado para todos aquellos tipos de objectos que no se
 * incluyen en ningun otro serializador json
 *
 * @author katsu
 */
class GenericSerializer implements Serializer {

    @Override
    public void parse(Object obj, Properties properties, Writer writer) throws IOException {
        Serializer s;
        Object o;
        //Caracter inicio
        writer.append(Constants.OPEN_CURLY_BRACKET);

        Map<String, Object> fields = ReflectionUtils.getFields(obj);
        int i = 0;
        for (String key : fields.keySet()) {
            o = fields.get(key);
            s = SerializerFactory.getSerializer(o);

            writer.append(Constants.DOUBLE_MARK);
            writer.append(key);
            writer.append(Constants.DOUBLE_MARK);
            writer.append(Constants.COLON);
            s.parse(o, properties, writer);
            if (i < fields.size()-1) {
                writer.append(Constants.COMMA);
            }
            i++;
        }
        //Caracter fin
        writer.append(Constants.CLOSE_CURLY_BRACKET);

    }
}
