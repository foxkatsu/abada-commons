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

import java.io.File;
import java.io.FileInputStream;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.view.AbstractView;

/**
 * Vista para el MVC de Spring que permite provocar la descarga de un fichero al navegador del cliente
 * @author katsu
 */
public class FileView extends AbstractView{
    /**
     * Fichero descargar
     */
    private File file;
    /**
     * Nombre con el que se quiere que se descargue
     */
    private String name;

    public FileView(File file,String name){
        this.file=file;
        this.name=name;
    }

    @Override
    protected void renderMergedOutputModel(Map model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (file!=null && file.exists()){
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", 0);
            response.setHeader("Content-Disposition", "attachment; filename=\"" + name + "\"");
            response.setContentType(this.getContenType(file));
            response.setBufferSize((int)file.length());
            response.setContentLength((int)file.length());

            byte [] data=this.getData(file);
            response.getOutputStream().write(data);
            response.getOutputStream().flush();
        }
    }

    private String getContenType(File file){
        try{
            return file.toURI().toURL().openConnection().getContentType();
        }catch (Exception e){}
        return null;
    }

    public byte [] getData(File file){
        if (file.exists()){
            FileInputStream in=null;
            byte [] result=new byte[(int)file.length()];
            try{               
                in =new FileInputStream(file);
                in.read(result);                
            }catch (Exception ex){
                result=null;
            }finally{
                try{
                    in.close();
                }catch (Exception e){}
            }
            return result;
        }
        return null;
    }
}
