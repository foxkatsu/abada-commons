/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.katsu.springframework.web.servlet.command.extjs.gridpanel.impl;

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

import com.katsu.extjs.grid.mapping.FieldInformation;
import com.katsu.extjs.grid.mapping.MappedDataGrid;
import com.katsu.springframework.web.servlet.command.extjs.gridpanel.FilterRequest;
import com.katsu.springframework.web.servlet.command.extjs.gridpanel.GridRequest;
import com.katsu.springframework.web.servlet.command.extjs.gridpanel.OrderByRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Clase usada para crear de manera dinámica la clausula WHERE de los JPQL.<br/>
 * Usada para representar la peticion de filtros de los grid panel de extjs
 * @author katsu
 */
public class GridRequestImpl implements GridRequest {
    private static final String ESPACE=" ";
    private static final String WHERE="WHERE ";
    private static final String AND="AND ";
    private static final String ORDER=" ORDER BY ";
    /**
     * posicion desde la que se muestran los resultados de la busqueda
     */
    private Integer start;
    /**
     * maxima logitud de los resultados
     */
    private Integer limit;
    /**
     * Campo para ordenar
     */
    private OrderByRequest [] sorts;
    /**
     * Lista de filtros, uno por cada campo a filtrar
     */
    private List<FilterRequest> filters;   

    public GridRequestImpl() {
        start=Integer.MIN_VALUE;
    }

    
    /**
     * Añade un filtro
     * @param filterRequest
     */
    @Override
    public void addFilterRequest(FilterRequest filterRequest) {
        if (filters == null) {
            filters = new ArrayList<FilterRequest>();
        }
        if (filterRequest != null) {
            filters.add(filterRequest);
        }
    }

    /**
     * Devuelve la clausula WHERE de la query JPQL
     * @param entityName Nombre de la entidad que se han de crear los filtros
     * @param withWhere si se añade o no la clausula WHERE, util cuando la sentencia
     * que se va a concatenar ya posee el WHERE
     * @param mapping La ruta de la variable si se usaron objectos {@link MappedDataGrid}
     * @return
     */
    public String getQL(String entityName, boolean withWhere,MappedDataGrid ...mapping) {
        StringBuilder result=new StringBuilder(ESPACE);
        if (filters != null) {
            if (withWhere && filters.size() > 0) {
                result.append(WHERE);
            } else if (!withWhere && filters.size() > 0) {
                result.append(AND);
            }
            
            for (FilterRequest aux : filters) {
                result.append(aux.getQL(entityName));
                result.append(ESPACE);
                result.append(AND);                
            }
            if (filters.size() > 0) {
                result=result.delete(result.length() - 5,result.length());
            }
        }
        if (sorts !=null && sorts.length>0) {
            result.append(ORDER);
            int i=0;
            for (OrderByRequest obn:sorts){
                i++;
                result.append(this.getMappedFieldName(entityName, obn.getProperty(),mapping));
                result.append(ESPACE);
                result.append(obn.getDirection());
                if (i<sorts.length)
                    result.append(',');
                
            }            
        }

        return result.toString();
    }

    private String getMappedFieldName(String entityName,String sort, MappedDataGrid... mapping) {
        return entityName + "." + getMappedFieldName(sort,mapping);
    }

    private String getMappedFieldName(String sort,MappedDataGrid... mapping) {
        if (mapping != null && mapping.length == 1) {
            FieldInformation fi = (FieldInformation) mapping[0].getTargetInformation().get(sort);
            if (fi != null) {
                return fi.getMapping();
            }
        }
        return sort.replace('_', '.');
    }

    /**
     * Devuelve los valores para sustituirlos en el JPQL
     * @return
     */
    @Override
    public Map<String, Object> getParamsValues() {
        return this.getNamedParametersObjects(null);
    }

    private Map<String, Object> getNamedParametersObjects(Map<String, Object> map) {
        Map<String, Object> result = new HashMap<String, Object>();
        if (map != null) {
            result.putAll(map);
        }
        if (filters != null) {
            for (FilterRequest f : filters) {
                result.putAll(f.getValue());
            }
        }
        return result;
    }

    public OrderByRequest[] getSorts() {
        return sorts;
    }

    public void setSorts(OrderByRequest[] sorts) {
        this.sorts = sorts;
    }    
    
    @Override
    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    /**
     * Posicion de comienzo de los resultados de la busqueda, util para paginacion
     * @return
     */
    @Override
    public Integer getStart() {
        return start;
    }

    /**
     * Maxima cantidad de resultado en la busqueda, util para paginacion
     * @return
     */
    public void setStart(Integer start) {
        this.start = start;
    }

    @Override
    public int countFilters() {
        if (this.filters!=null)
            return this.filters.size();
        return 0;
    }
}
