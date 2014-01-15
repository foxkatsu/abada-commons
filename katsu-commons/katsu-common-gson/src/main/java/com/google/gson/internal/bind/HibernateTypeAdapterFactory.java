/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.gson.internal.bind;

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

import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.$Gson$Types;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.Excluder;
import com.google.gson.internal.ObjectConstructor;
import com.google.gson.internal.Primitives;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;

public class HibernateTypeAdapterFactory implements TypeAdapterFactory {
    public static final String GET = "get";
    private static final String IS = "is";
    private static final String PATTERN_HIBERNATE_CLASS = "_javassist_";
    private final static String[] BLACK_FIELDS = new String[]{"handler","hibernateLazyInitializer", "persistentClass", "implementation", "entityName", "class", "identifier"};

    private final ConstructorConstructor constructorConstructor;
    private final FieldNamingStrategy fieldNamingPolicy;
    private final Excluder excluder;

    public HibernateTypeAdapterFactory(ConstructorConstructor constructorConstructor,
            FieldNamingStrategy fieldNamingPolicy, Excluder excluder) {
        this.constructorConstructor = constructorConstructor;
        this.fieldNamingPolicy = fieldNamingPolicy;
        this.excluder = excluder;
    }
    private boolean isBlock(String key) {
        for (String s : BLACK_FIELDS) {
            if (s.equals(key)) {
                return true;
            }
        }
        return false;
    }
    
    private String getGetterMethodName(String fieldName, boolean is) {
        if (is) {
            if (fieldName.startsWith(IS))                
                fieldName=fieldName.substring(2, fieldName.length());
            return IS + firstCharUpper(fieldName);

        } else {
            return GET + firstCharUpper(fieldName);

        }
    }

    private String firstCharUpper(String cad) {
        String first = cad.substring(0, 1).toUpperCase();
        String aux = cad.substring(1);
        return first + aux;
    }

    public boolean excludeField(Field f, boolean serialize) {
        if (isBlock(f.getName())) return false;
        return !excluder.excludeClass(f.getType(), serialize) && !excluder.excludeField(f, serialize);
    }

    private String getFieldName(Field f) {
        SerializedName serializedName = f.getAnnotation(SerializedName.class);
        return serializedName == null ? fieldNamingPolicy.translateName(f) : serializedName.value();
    }

    public <T> TypeAdapter<T> create(Gson gson, final TypeToken<T> type) {
        Class<? super T> raw = type.getRawType();
        if (raw.getName().contains(PATTERN_HIBERNATE_CLASS)) {
            ObjectConstructor<T> constructor = constructorConstructor.get(type);
            return new Adapter<T>(constructor, getBoundFields(gson, type, raw));
        }
        return null;
    }

    private HibernateTypeAdapterFactory.BoundField createBoundField(
            final Gson context, final Field field, final String name,
            final TypeToken<?> fieldType, boolean serialize, boolean deserialize) {
        final boolean isPrimitive = Primitives.isPrimitive(fieldType.getRawType());

        // special casing primitives here saves ~5% on Android...
        return new HibernateTypeAdapterFactory.BoundField(name, serialize, deserialize) {

            final TypeAdapter<?> typeAdapter = context.getAdapter(fieldType);

            @SuppressWarnings({"unchecked", "rawtypes"}) // the type adapter and field type always agree
            @Override
            void write(JsonWriter writer, Object value)
                    throws IOException, IllegalAccessException,NoSuchMethodException,InvocationTargetException {
                Method method=value.getClass().getMethod(getGetterMethodName(field.getName(), field.getGenericType().equals(Boolean.class)), null);
                Object fieldValue = method.invoke(value);
                TypeAdapter t =
                        new TypeAdapterRuntimeTypeWrapper(context, this.typeAdapter, fieldType.getType());
                t.write(writer, fieldValue);
            }

            @Override
            void read(JsonReader reader, Object value)
                    throws IOException, IllegalAccessException {
                Object fieldValue = typeAdapter.read(reader);
                if (fieldValue != null || !isPrimitive) {
                    field.set(value, fieldValue);
                }
            }
        };
    }

    private Map<String, BoundField> getBoundFields(Gson context, TypeToken<?> type, Class<?> raw) {
        Map<String, BoundField> result = new LinkedHashMap<String, BoundField>();
        if (raw.isInterface()) {
            return result;
        }

        Type declaredType = type.getType();
        while (raw != Object.class) {
            Field[] fields = raw.getDeclaredFields();
            for (Field field : fields) {
                boolean serialize = excludeField(field, true);
                boolean deserialize = excludeField(field, false);
                if (!serialize && !deserialize) {
                    continue;
                }
                field.setAccessible(true);
                Type fieldType = $Gson$Types.resolve(type.getType(), raw, field.getGenericType());
                BoundField boundField = createBoundField(context, field, getFieldName(field),
                        TypeToken.get(fieldType), serialize, deserialize);
                BoundField previous = result.put(boundField.name, boundField);
                if (previous != null) {
                    throw new IllegalArgumentException(declaredType
                            + " declares multiple JSON fields named " + previous.name);
                }
            }
            type = TypeToken.get($Gson$Types.resolve(type.getType(), raw, raw.getGenericSuperclass()));
            raw = type.getRawType();
        }
        return result;
    }

    static abstract class BoundField {

        final String name;
        final boolean serialized;
        final boolean deserialized;

        protected BoundField(String name, boolean serialized, boolean deserialized) {
            this.name = name;
            this.serialized = serialized;
            this.deserialized = deserialized;
        }

        abstract void write(JsonWriter writer, Object value) throws IOException, IllegalAccessException,NoSuchMethodException,InvocationTargetException;

        abstract void read(JsonReader reader, Object value) throws IOException, IllegalAccessException;
    }

    public final class Adapter<T> extends TypeAdapter<T> {

        private final ObjectConstructor<T> constructor;
        private final Map<String, BoundField> boundFields;

        private Adapter(ObjectConstructor<T> constructor, Map<String, BoundField> boundFields) {
            this.constructor = constructor;
            this.boundFields = boundFields;
        }

        @Override
        public T read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return null;
            }

            T instance = constructor.construct();

            // TODO: null out the other fields?

            try {
                in.beginObject();
                while (in.hasNext()) {
                    String name = in.nextName();
                    BoundField field = boundFields.get(name);
                    if (field == null || !field.deserialized) {
                        // TODO: define a better policy
                        in.skipValue();
                    } else {
                        field.read(in, instance);
                    }
                }
            } catch (IllegalStateException e) {
                throw new JsonSyntaxException(e);
            } catch (IllegalAccessException e) {
                throw new AssertionError(e);
            }
            in.endObject();
            return instance;
        }

        @Override
        public void write(JsonWriter out, T value) throws IOException {
            if (value == null) {
                out.nullValue(); // TODO: better policy here?
                return;
            }

            out.beginObject();
            try {
                for (BoundField boundField : boundFields.values()) {
                    if (boundField.serialized) {
                        out.name(boundField.name);
                        boundField.write(out, value);
                    }
                }
            } catch (IllegalAccessException e) {
                throw new AssertionError();
            } catch (NoSuchMethodException e){
                throw new AssertionError();
            } catch (InvocationTargetException e){
                throw new AssertionError();
            }
            out.endObject();
        }
    }
}
