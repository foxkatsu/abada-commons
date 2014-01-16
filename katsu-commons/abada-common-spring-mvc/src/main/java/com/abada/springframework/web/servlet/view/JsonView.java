package com.abada.springframework.web.servlet.view;

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

import com.abada.json.Json;
import com.abada.json.JsonFactory;
import com.abada.json.JsonProperty;
import com.abada.json.JsonType;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.validation.BeanPropertyBindingResult;

import org.springframework.web.servlet.view.AbstractView;

/**
 * Vista que devuelve al navegador del cliente un objeto serializado en json
 *
 * @author katsu
 */
public class JsonView extends AbstractView {

    private static final String contentType = "application/json";
    public static final String JSON_VIEW_CLASS = "json_view_class";
    public static final String JSON_VIEW_RESULT = "json_view_result";
    /**
     * Objeto a serializar
     */
    private Object model;
    /**
     * Si esta a true (opción por defecto) mirará dentro del model y si solo hay
     * un objeto procesará solo ese objeto y no el model.< br/>Si está a false
     * intentará procesar el model(Map).
     */
    private boolean excludeBodyModel;

    public boolean isExcludeBodyModel() {
        return excludeBodyModel;
    }

    public void setExcludeBodyModel(boolean excludeBodyModel) {
        this.excludeBodyModel = excludeBodyModel;
    }
    /**
     * Si esta a true(por defecto) cualquier bean que extienda de
     * {@link  BeanPropertyBindingResult}
     *
     */
    private boolean excludeBeansValidation;

    public boolean isExcludeBeansValidation() {
        return excludeBeansValidation;
    }

    public void setExcludeBeansValidation(boolean excludeBeansValidation) {
        this.excludeBeansValidation = excludeBeansValidation;
    }
    private Json json;

    public Object getModel() {
        return model;
    }

    public void setModel(Object model) {
        this.model = model;
    }

    public void setJsonProperties(Map<JsonProperty, String> jsonProperties) {
        Properties properties = new Properties();
        if (jsonProperties != null) {
            for (JsonProperty key : jsonProperties.keySet()) {
                properties.setProperty(key.getValue(), jsonProperties.get(key));
            }
        }
        this.json.setProperties(properties);
    }

    public JsonView() {
        super();
        this.setContentType(contentType);
        json = JsonFactory.getInstance().getInstance(JsonType.DEFAULT);
        this.excludeBodyModel = false;
        excludeBeansValidation = true;
    }

    public JsonView(Object model) {
        this();
        this.model = model;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void renderMergedOutputModel(Map model, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        //primero miro si tenemos un modelo pasado en el constructor
        //si lo hay envio ese y no el model del metodo
        //response.setCharacterEncoding("iso-8859-1");
        response.setCharacterEncoding("UTF-8");
        //response.setContentType("text/html");application/json
        response.setContentType(contentType);

        Object oToJson = null;

        if (this.model != null) {
            //Si me han pasado el objeto a serializar
            oToJson = this.model;
        } else {
            if (model == null || model.isEmpty()) {
                //Si no tengo ni objecto ninguno que serializar
                oToJson = new Object();
            } else {
                oToJson = model;
            }
        }

        if (this.excludeBeansValidation && oToJson instanceof Map) {
            //limpio de objetos que extiendan de BeanPropertyBindingResult
            oToJson = this.deleteBeanValidation((Map) oToJson);
        }
        if (this.excludeBodyModel && oToJson instanceof Map && ((Map) oToJson).size() == 1) {
            //Excluyo el cuerpo del model
            oToJson = ((Map) oToJson).values().toArray()[0];
        }

        if (oToJson != null && oToJson instanceof Map && ((Map)oToJson).containsKey(JSON_VIEW_CLASS) && ((Map)oToJson).containsKey(JSON_VIEW_RESULT)) {
            json.serialize(((Map)oToJson).get(JSON_VIEW_RESULT), response.getWriter(),(Class)((Map)oToJson).get(JSON_VIEW_CLASS));
        } else {
            json.serialize(oToJson, response.getWriter());
        }
        response.getWriter().flush();
    }

    private boolean isBeanPropertyBindingResult(Object obj) {
        return BeanPropertyBindingResult.class.isInstance(obj);
    }

    private Object deleteBeanValidation(Map model) {
        Map result = new HashMap();
        Entry<Object, Object> entry;
        for (Object entryTemp : model.entrySet()) {
            entry = (Entry<Object, Object>) entryTemp;
            if (!this.isBeanPropertyBindingResult(entry.getValue())) {
                result.put(entry.getKey(), entry.getValue());
            }
        }
        return result;
    }
}
