/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.katsu.springframework.web.servlet.command.extjs.gridpanel.filtertype;

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
import com.katsu.springframework.web.servlet.command.extjs.gridpanel.FilterRequest;
import com.katsu.springframework.web.servlet.command.extjs.gridpanel.factory.ComparisonType;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementa el filtro de tipo boolean
 * @author katsu
 */
public class BooleanFilter extends FilterRequest{
    private Boolean value;

    public BooleanFilter(Boolean value,String fieldName,int index) {
        super(ComparisonType.EQUAL, fieldName,index);
        this.value = value;
    }

    @Override
    public String getQL(String entityName,MappedDataGrid ...mapping) {
        String result = this.getMappedFieldName(entityName,mapping);
        result+=" = :";
        result+=this.generateParamName(0);
        return result;
    }

    @Override
    public Map<String, Object> getValue() {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put(this.generateParamName(0), value);
        return result;
    }

}
