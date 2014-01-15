/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.katsu.springframework.web.servlet.command.extjs.gridpanel.factory;

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

import com.katsu.json.Json;
import com.katsu.json.JsonFactory;
import com.katsu.json.JsonType;
import com.katsu.springframework.web.servlet.command.extjs.gridpanel.FilterRequest;
import com.katsu.springframework.web.servlet.command.extjs.gridpanel.GridRequest;
import com.katsu.springframework.web.servlet.command.extjs.gridpanel.OrderByRequest;
import com.katsu.springframework.web.servlet.command.extjs.gridpanel.filtertype.*;
import com.katsu.springframework.web.servlet.command.extjs.gridpanel.impl.GridRequestImpl;
import com.katsu.utils.Constants;
import com.google.gson.reflect.TypeToken;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

/**
 * Factoria con metodos utiles para la creacion de {@link GridRequest}
 *
 * @author katsu
 */
public class GridRequestFactory {

    private static final String ABOOLEAN = "boolean";
    private static final String AFLOAT = "float";
    private static final String DATE = "date";
    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private static final String DECIMAL = "decimal";
    private static final String GT = "gt";
    private static final String INTEGER = "integer";
    private static final String LIST = "list";
    private static final String LT = "lt";
    private static final String DISTINCT = "DISTINCT";
    private static final String NUMERIC = "numeric";
    private static final String STRING = "string";
    private static final String ENUM = "enum";
    private static final Json json = JsonFactory.getInstance().getInstance(JsonType.DEFAULT);

    /**
     * Devuelve el {@link GridRequest} que se representa con los valore que
     * envia el grid panel de extjs
     *
     * @param sorts objetos con el orden de la busqueda
     * @param start posicion desde la que se muestran los resultados
     * @param limit longitud maxima de los resultados
     * @param jsonFilter Json que representa los filtros de los campos para el
     * JPQL
     * @return
     */
    public static GridRequest parse(OrderByRequest[] sorts, Integer start, Integer limit, String jsonFilter) throws UnsupportedEncodingException {
        GridRequestImpl result = new GridRequestImpl();
        result.setSorts(sorts);
        result.setLimit(limit);
        result.setStart(start);

        if (jsonFilter != null && !"".equals(jsonFilter)) {
            jsonFilter = URLDecoder.decode(jsonFilter, Constants.UTF8);
            List<FilterRequestPriv> filters = json.deserialize(jsonFilter, new TypeToken<List<FilterRequestPriv>>() {
            }.getType());
            if (filters != null) {
                for (FilterRequestPriv filterRequestPriv : filters) {
                    result.addFilterRequest(createFilterRequest(filterRequestPriv, result.countFilters()));
                }
            }
        }
        return result;
    }

    /**
     * Devuelve el {@link GridRequest} que se representa con los valore que
     * envia el grid panel de extjs
     *
     * @param sort nombre del campo por el que se quiere ordenar
     * @param dir direccion del orden ASC DESC
     * @param start posicion desde la que se muestran los resultados
     * @param limit longitud maxima de los resultados
     * @param jsonFilter Json que representa los filtros de los campos para el
     * JPQL
     * @return
     */
    public static GridRequest parse(String sort, Integer start, Integer limit, String jsonFilter) throws UnsupportedEncodingException {
        if (sort != null && !sort.isEmpty()) {
            sort = URLDecoder.decode(sort, Constants.UTF8);
        }
        return parse(json.deserialize(sort, OrderByRequest[].class), start, limit, jsonFilter);
    }

    /**
     * Devuelve el {@link GridRequest} que se representa con los valore que
     * envia el grid panel de extjs
     *
     * @param sort nombre del campo por el que se quiere ordenar
     * @param dir direccion del orden ASC DESC
     * @param start posicion desde la que se muestran los resultados
     * @param limit longitud maxima de los resultados
     * @param jsonFilter Json que representa los filtros de los campos para el
     * JPQL
     * @return
     */
    public static GridRequest parse(String sort, String dir, Integer start, Integer limit, String jsonFilter) throws UnsupportedEncodingException {
        return parse(new OrderByRequest[]{new OrderByRequest(sort, dir)}, start, limit, jsonFilter);
    }

    /**
     * Metodo utilizado para añadir un filtro por un campo a mano
     *
     * @param gridRequest
     * @param comparison
     * @param type
     * @param value
     * @param fieldName
     * @param enumType
     */
    public static void addFilterRequest(GridRequest gridRequest, String comparison, String type, String value, String fieldName, String enumType) {
        FilterRequestPriv fr = new FilterRequestPriv();
        fr.setComparison(comparison);
        fr.setType(type);
        fr.setValue(value);
        fr.setField(fieldName);
        fr.setEnumType(enumType);
        gridRequest.addFilterRequest(createFilterRequest(fr, gridRequest.countFilters()));
    }

    private static FilterRequest createFilterRequest(FilterRequestPriv filterRequestPriv, int index) {
        FilterRequest result = null;
        if (filterRequestPriv.getType().equalsIgnoreCase(NUMERIC)) {
            Number value = null;
            if (filterRequestPriv.getValue().contains(String.valueOf(Constants.PERIOD))) {
                value = new Double(filterRequestPriv.getValue().replace(String.valueOf(Constants.PERIOD), String.valueOf(Constants.COMMA)));
            } else {
                value = new Long(filterRequestPriv.getValue());
            }
            if (value != null) {
                result = new NumericFilter(value, filterRequestPriv.getField(), getComparisonType(filterRequestPriv.getComparison()), index);
            }
        } else if (filterRequestPriv.getType().equalsIgnoreCase(INTEGER)) {
            result = new NumericFilter(new Integer(filterRequestPriv.getValue()), filterRequestPriv.getField(), getComparisonType(filterRequestPriv.getComparison()), index);
        } else if (filterRequestPriv.getType().equalsIgnoreCase(AFLOAT)) {
            result = new NumericFilter(new Float(filterRequestPriv.getValue()), filterRequestPriv.getField(), getComparisonType(filterRequestPriv.getComparison()), index);
        } else if (filterRequestPriv.getType().equalsIgnoreCase(DECIMAL)) {
            result = new NumericFilter(new BigDecimal(filterRequestPriv.getValue()), filterRequestPriv.getField(), getComparisonType(filterRequestPriv.getComparison()), index);
        } else if (filterRequestPriv.getType().equalsIgnoreCase(STRING)) {
            result = new StringFilter(filterRequestPriv.getValue(), filterRequestPriv.getField(), index);
        } else if (filterRequestPriv.getType().equalsIgnoreCase(LIST)) {
            String[] value = filterRequestPriv.getValue().split(String.valueOf(Constants.COMMA));
            result = new ListFilter(Arrays.asList(value), filterRequestPriv.getField(), getEnumTypeClass(filterRequestPriv.getEnumType()), index);
        } else if (filterRequestPriv.getType().equalsIgnoreCase(ENUM)) {
            String[] value = filterRequestPriv.getValue().split(String.valueOf(Constants.COMMA));
            result = new ListFilter(Arrays.asList(value), filterRequestPriv.getField(), getEnumTypeClass(filterRequestPriv.getEnumType()), index);
        } else if (filterRequestPriv.getType().equalsIgnoreCase(ABOOLEAN)) {
            result = new BooleanFilter(Boolean.parseBoolean(filterRequestPriv.getValue()), filterRequestPriv.getField(), index);
        } else if (filterRequestPriv.getType().equalsIgnoreCase(DATE)) {
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            try {
                result = new DateFilter(sdf.parse(filterRequestPriv.getValue()), filterRequestPriv.getField(), getComparisonType(filterRequestPriv.getComparison()), index);
            } catch (ParseException e) {
            }
        }
        return result;
    }

    private static ComparisonType getComparisonType(String comparison) {
        if (comparison.equalsIgnoreCase(GT)) {
            return ComparisonType.AFTER;
        } else if (comparison.equalsIgnoreCase(LT)) {
            return ComparisonType.BEFORE;
        } else if (comparison.equalsIgnoreCase(DISTINCT)) {
            return ComparisonType.DISTINCT;
        }
        return ComparisonType.EQUAL;
    }

    private static Class getEnumTypeClass(String enumType) {
        try {
            return Class.forName(enumType);
        } catch (ClassNotFoundException e) {
        }
        return null;
    }
}
