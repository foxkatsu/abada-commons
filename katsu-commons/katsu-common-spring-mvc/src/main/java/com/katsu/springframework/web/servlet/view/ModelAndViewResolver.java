/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.katsu.springframework.web.servlet.view;

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

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.annotation.ModelAndViewResolver;

/**
 *
 * @author katsu
 */
public class ModelAndViewResolver extends ApplicationObjectSupport implements ModelAndViewResolver {

    @Override
    public ModelAndView resolveModelAndView(Method handlerMethod, Class handlerType, Object returnValue, ExtendedModelMap implicitModel, NativeWebRequest webRequest) {
        if (returnValue instanceof String || returnValue instanceof View
                || returnValue instanceof ModelAndView || returnValue instanceof InputStream
                || returnValue instanceof OutputStream) {
            return UNRESOLVED;
        }
        if (returnValue instanceof File) {
            File file = (File) returnValue;
            return new ModelAndView(new FileView(file, file.getName()));
        }
        JsonView view = getJsonView();
        if (returnValue == null && implicitModel != null && !implicitModel.isEmpty()) {
            view.setModel(implicitModel);
        } else {
            view.setModel(returnValue);
        }
        return new ModelAndView(view);
    }

    protected JsonView getJsonView() {
        try {
            return this.getApplicationContext().getBean(JsonView.class);
        } catch (Exception e) {
            logger.warn("No JsonView defined. Taking default definition.");
        }
        return new JsonView();
    }
}
