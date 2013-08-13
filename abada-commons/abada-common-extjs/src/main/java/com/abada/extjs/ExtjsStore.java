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
import java.util.List;
/**
 * Usado para serializar los datos para carga un JsonStore de extjs
 * @author katsu
 */
@NoOfuscation
public class ExtjsStore<T> {
    /**
     * Cantidad total de elementos
     */
    private int total;
    /**
     * Lista de datos
     */
    private List<T> data;

    public ExtjsStore() {
        this.data = null;
        this.total = -1;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
        if (total < 0) {
            this.setTotal(data.size());
        }
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
