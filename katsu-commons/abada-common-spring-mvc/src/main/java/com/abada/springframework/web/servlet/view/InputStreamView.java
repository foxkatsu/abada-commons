/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.view.AbstractView;

/**
 *
 * @author katsu
 */
public class InputStreamView extends AbstractView {
    private static final int MAX_LENGTH=1024;
    /**
     * Fichero descargar
     */
    private InputStream data;
    /**
     * Type of file
     */
    private String type;
    /***
     * Headers atributes
     */
    private Map<String, String> headers;

    public InputStream getData() {
        return data;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getType() {
        return type;
    }

    public void setData(InputStream data) {
        this.data = data;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public void setType(String type) {
        this.type = type;
    }        
    
    public void addHeaderPropertyName(String name){
        if (this.headers==null)
            this.headers=new HashMap<String, String>();
        this.headers.put("Content-Disposition", "attachment; filename=\"" + name + "\"");
    }
    
    public InputStreamView(InputStream is, String type, Map<String, String> headers) {
        this.data = is;        
        this.type = type;
        this.headers = headers;
    }        

    @Override
    protected void renderMergedOutputModel(Map model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (data != null) {
            response.setCharacterEncoding("UTF-8");
            if (headers!=null){
                for (String key:headers.keySet()){
                    response.setHeader(key, headers.get(key));
                }
            }            
            response.setDateHeader("Expires", 0);
            response.setContentType(this.type);                        
            /*response.setBufferSize(this.data.available());            
            response.setContentLength(this.data.available());*/
            
            
            int readed;
            byte [] dataOut=new byte[MAX_LENGTH];
            while ((readed=this.data.read(dataOut))!=-1){
                response.getOutputStream().write(dataOut, 0, readed);
            }
            response.getOutputStream().flush();
            response.getOutputStream().close();
        }
    }
}
