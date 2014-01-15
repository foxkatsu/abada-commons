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

import com.katsu.extjs.grid.mapping.MappedDataGrid;
import com.katsu.json.JsonProperty;
import java.util.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Factoria que devuelve el serializador correcto para cada tipo de objecto
 * @author katsu
 */
public class SerializerFactory {

    private static final Log logger = LogFactory.getLog(SerializerFactory.class); 
    private static List<Serializer> serializers;
    
    static{
        serializers=new LinkedList<Serializer>();
        serializers.add(new NullSerializer());//0
        serializers.add(new GenericSerializer());//1        
        serializers.add(new MapSerializer());//2
        serializers.add(new MappedDataGridSerializer());//3
        serializers.add(new BooleanSerializer());//4
        serializers.add(new NumberSerializer());//5
        serializers.add(new StringSerializer());//6
        serializers.add(new DateSerializer());//7
        serializers.add(new CollectionSerializer());//8
        serializers.add(new ArraySerializer());//9
        serializers.add(new HibernateSerializer());//10
    }
    
    private static String getProperty(JsonProperty jprop, Properties properties){
        if (properties!=null){
            return properties.getProperty(jprop.getValue());
        }
        return null;
    }
    
    public static Serializer getSerializer(Object obj) {
        if (obj == null) return serializers.get(0);
        else if (obj instanceof String) return serializers.get(6);
        else if (obj instanceof Boolean || boolean.class.isInstance(obj)) return serializers.get(4);
        else if (obj instanceof Number || obj.getClass().isPrimitive()) return serializers.get(5);
        else if (obj instanceof Date) return serializers.get(7);
        else if (obj instanceof Iterable) return serializers.get(8);
        else if (obj.getClass().isArray()) return serializers.get(9);
        else if (obj instanceof Map) return serializers.get(2);
        else if (obj instanceof MappedDataGrid) return serializers.get(3);
        else if (obj.getClass().getName().contains("javassist")) return serializers.get(10);        
        return serializers.get(1);
    }
    
    /**
     * has to remove a double marks when is a simple object
     * @param obj
     * @return 
     */
    public static boolean hasRemoveDoubleMarks(Object obj){
        /*if (boolean.class.isInstance(obj) || Boolean.class.isInstance(obj) || 
                String.class.isInstance(obj) || obj.getClass().isEnum()) {            
            return true;
        }*/
        return false;
    }
}
