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

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.core.annotation.AnnotationUtils;

/**
 * Clase con metodos usados por los serializadores para acceder por reflexion a
 * la estructura de los objetos
 * @author katsu
 */
public class ReflectionUtils {

    /**
     *
     * @param obj
     * @return
     */
    public static Map<String, Object> getFieldsByMethod(Object obj) {
        Map<String, Object> result = new HashMap<String, Object>();
        Field f;
        Object aux;
        if (!Class.class.isInstance(obj)) {
            for (Method m : obj.getClass().getMethods()) {
                try {
                    f = hasValidField(obj, m);
                    if ((m.getName().startsWith("is") || m.getName().startsWith("get")) && m.getParameterTypes().length == 0) {
                        try {
                            aux = m.invoke(obj);
                            if (aux != null) {
                                if (f != null) {
                                    result.put(f.getName(), aux);
                                } else {
                                    result.put(getFieldName(m.getName()), aux);
                                }
                            }
                        } catch (Exception e) {
                        }
                    }
                } catch (HasExcludeAnnotationException e) {
                }
            }
        }
        return result;
    }

    /**
     * 
     * @param obj
     * @return
     */
    public static Map<String, Object> getFields(Object obj) {
        Map<String, Object> result = new HashMap<String, Object>();
        for (Field f : getAllFields(obj.getClass())) {
            Method m = isValidField(obj, f);
            if (m != null) {
                try {
                    Object aux = m.invoke(obj);
                    if (aux != null) {
                        result.put(f.getName(), aux);
                    }
                } catch (Exception e) {
                }
            }
        }
        return result;
    }

    /**
     * 
     * @param obj
     * @return
     */
    public static Field getField(Object obj, String fieldName) {
        try {
            return obj.getClass().getField(fieldName);
        } catch (NoSuchFieldException e) {
        } catch (SecurityException e) {
        }
        return null;
    }

    public static List<Method> getMethodsWithAnnotation(Object obj, Class annotation) {
        Class clazz = isProxy(obj);
        List<Method> result = new ArrayList<Method>();
        for (Method m : clazz.getMethods()) {
            if (AnnotationUtils.getAnnotation(m, annotation) != null) {
                result.add(m);
            }
        }
        return result;
    }

    private static Class isProxy(Object bean) {
        try {
            Method m = bean.getClass().getMethod("getTargetClass");
            Class result = (Class) m.invoke(bean);
            return result;
        } catch (Exception e) {
        }
        return bean.getClass();
    }

// <editor-fold defaultstate="collapsed" desc="comment">
    private static List<Field> getAllFields(Class clazz) {
        List<Field> result = new ArrayList<Field>();
        //primero los suyos
        for (Field f : clazz.getDeclaredFields()) {
            result.add(f);
        }
        //sus superclases
        Class c = clazz.getSuperclass();
        if (c != null) {
            result.addAll(getAllFields(c));
        }
        return result;
    }

    /***
     * Test if have a valid getter, is no final and no have GsonExclude annotation.
     * @param f
     * @return
     */
    private static Method isValidField(Object obj, Field f) {
        if (!f.isAnnotationPresent(JsonIgnore.class)) {
            return getGetterFor(obj, f, obj.getClass());
        }
        return null;
    }

    private static Method getGetterFor(Object obj, Field field, Class objClass) {
        for (Method m : objClass.getDeclaredMethods()) {
            if (m.getName().equals(getGetterMethodName(field.getName(), false)) || m.getName().equals(getGetterMethodName(field.getName(), true))) {
                if (m.getGenericParameterTypes().length == 0) {
                    return m;
                }
            }
        }
        //Su super class
        Class c = objClass.getSuperclass();
        if (c != null) {
            return getGetterFor(obj, field, c);
        }
        return null;
    }

    private static String getGetterMethodName(String fieldName, boolean is) {
        if (is) {
            if (fieldName.startsWith("is"))                
                fieldName=fieldName.substring(2, fieldName.length());
            return "is" + firstCharUpper(fieldName);

        } else {
            return "get" + firstCharUpper(fieldName);

        }
    }

    private static String firstCharUpper(String cad) {
        String first = cad.substring(0, 1).toUpperCase();
        String aux = cad.substring(1);
        return first + aux;
    }

    private static String getFieldName(String methodName) {
        if (methodName.startsWith("get")) {
            methodName = methodName.substring(3);
            String first = methodName.substring(0, 1).toLowerCase();
            return first + methodName.substring(1);
        }
        return null;
    }

    private static Field hasValidField(Object obj, Method m) throws HasExcludeAnnotationException {
        String fieldName = getFieldName(m.getName());
        Field result = getDeclaredField(obj, fieldName);
        if (result != null) {
            if (result.isAnnotationPresent(JsonIgnore.class)) {
                throw new HasExcludeAnnotationException();
            }
        }

        return result;
    }

    private static Field getDeclaredField(Object obj, String name) {
        for (Field f : getAllFields(obj.getClass())) {
            //System.out.println(f.getName());
            if (f.getName().equals(name)) {
                return f;
            }
        }
        return null;
    }// </editor-fold>
}
