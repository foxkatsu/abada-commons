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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author mmartin
 */
public class OutputStreamView extends InputStreamView {
    
    public OutputStreamView(ByteArrayOutputStream outputStream, String name) {
        this(outputStream, name, "application/pdf");
    }

    public OutputStreamView(ByteArrayOutputStream outputStream, String name, String type) {        
        this(outputStream, type, new HashMap<String, String>());        
        this.getHeaders().put("Cache-Control", "no-cache");
        this.getHeaders().put("Pragma", "no-cache");
        this.getHeaders().put("Content-Disposition", "attachment; filename=\"" + name + "\"");
    }

    public OutputStreamView(ByteArrayOutputStream outputStream, String type, Map<String, String> headers) {
        super(null,type,headers);        
        this.setData(parseTo(outputStream));
    }

    private ByteArrayInputStream parseTo(ByteArrayOutputStream outputStream) {        
        return new ByteArrayInputStream(outputStream.toByteArray());        
    }
}
