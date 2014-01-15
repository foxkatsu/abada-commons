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
 * Serializador Json<br/> Usado para objetos que son entidades que se han
 * cargado con Hibernate.<br/> Se excluye de la serializacion todos los campos
 * que añade Hibernate para carga de entidades dependientes (Lazy)
 *
 * @author katsu
 */
class HibernateSerializer implements Serializer {

    private final static String[] blackKeys = new String[]{"handler","hibernateLazyInitializer", "persistentClass", "implementation", "entityName", "class", "identifier"};

    private boolean isBlock(String key) {
        for (String s : blackKeys) {
            if (s.equals(key)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void parse(Object obj, Properties properties, Writer writer) throws IOException {
        Serializer s;
        Object o;
        //Caracter inicio
        writer.append(Constants.OPEN_CURLY_BRACKET);

        Map<String, Object> fields = ReflectionUtils.getFieldsByMethod(obj);
        int i = 0;
        for (String key : fields.keySet()) {
            //System.out.println(key);
            if (!isBlock(key)) {
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
            }
            i++;
        }

        //Caracter fin
        writer.append(Constants.CLOSE_CURLY_BRACKET);
    }
}
