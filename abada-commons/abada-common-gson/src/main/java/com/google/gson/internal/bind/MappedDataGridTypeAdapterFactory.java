/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.gson.internal.bind;

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

import com.abada.extjs.grid.mapping.MappedDataGrid;
import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.Excluder;
import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.Map;

/**
 *
 * @author katsu
 */
public class MappedDataGridTypeAdapterFactory implements TypeAdapterFactory {

    @Override
    public TypeAdapter create(Gson gson, TypeToken type) {
        if (type.getRawType().equals(MappedDataGrid.class)) {
            MappedDataGridTypeAdapter result = new MappedDataGridTypeAdapter(adapterFactory.create(gson, new TypeToken<Map>() {
            }));
            return result;
        }
        return null;
    }

    public final class MappedDataGridTypeAdapter extends TypeAdapter {

        private TypeAdapter typeAdapter;

        public MappedDataGridTypeAdapter(TypeAdapter typeAdapter) {
            this.typeAdapter = typeAdapter;
        }

        @Override
        public void write(JsonWriter out, Object value) throws IOException {
            typeAdapter.write(out, ((MappedDataGrid) value).getValues());
        }

        @Override
        public Object read(JsonReader in) throws IOException {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
    private ReflectiveTypeAdapterFactory adapterFactory;

    public MappedDataGridTypeAdapterFactory(ConstructorConstructor constructorConstructor,
            FieldNamingStrategy fieldNamingPolicy, Excluder excluder) {
        adapterFactory = new ReflectiveTypeAdapterFactory(constructorConstructor, fieldNamingPolicy, excluder);
    }
}
