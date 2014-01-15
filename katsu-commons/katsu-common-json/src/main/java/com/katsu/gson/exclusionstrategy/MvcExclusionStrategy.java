/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.katsu.gson.exclusionstrategy;

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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

/**
 * Estrategia de exclusion para la libreria Gson para que no serialize
 * determinados campos. Eficaz para evitar recursion infinita o no serializar datos
 * innecesarios
 * @author katsu
 */
public class MvcExclusionStrategy implements ExclusionStrategy{

    @Override
    public boolean shouldSkipField(FieldAttributes fa) {
        boolean result=fa.getAnnotation(JsonIgnore.class) !=null;
        //System.out.println(fa.getDeclaringClass()+" "+fa.getDeclaredType()+" "+fa.getName()+"-->"+result);
        return result;
    }

    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        return false;
    }
}
