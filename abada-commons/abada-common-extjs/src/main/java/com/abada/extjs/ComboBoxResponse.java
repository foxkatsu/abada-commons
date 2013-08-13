package com.abada.extjs;

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

import com.abada.ofuscation.annotation.NoOfuscation;

/**
 * Usado para inicializar con valores los comboBox de Extjs
 * @author katsu
 */
@NoOfuscation
public class ComboBoxResponse<T extends Object> {

    /**
     * Id que nos devolvera si se selecciona este valor
     */
    private String id;
    /**
     * Texto que se va a mostrar en le ComboBox
     */
    private T value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        try {
            ComboBoxResponse aux = (ComboBoxResponse) obj;
            if (!aux.getId().equals(this.getId())) {
                return false;
            }
            if (!aux.getValue().equals(this.getValue())) {
                return false;
            }
            return true;
        } catch (Exception e) {
        }
        return false;
    }
}
