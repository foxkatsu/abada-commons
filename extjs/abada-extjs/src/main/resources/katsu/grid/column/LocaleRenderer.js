/*
 * #%L
 * Katsu ExtJs
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
/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 * Para simular locale en una columna
 */
Katsu.grid.column.LocaleRenderer.defaultRenderer=function(value){
    if(typeof value == "string"){
        if (value.length < 1){
            return " ";
        }else{
            //intento localizar el valor para ello busco un array que debe existir
            //con los valores de localización
            try{
                result=Katsu.grid.ColumnModel.LocaleRenderer.defaultLocale[value];
                if (result)
                    return result;
            }catch (e){
            }
        }
    }
    return value;
};
/**
 * Se debe sobreescribir en un javascript con las traducciones
 */
Katsu.grid.ColumnModel.LocaleRenderer.defaultLocale={};