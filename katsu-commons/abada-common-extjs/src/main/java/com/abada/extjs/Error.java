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

/**
 * Usado para serializar la estructura que espera extjs para saber si ha producido una
 * llamada Ajax correctamente o incorrectamente
 * @author katsu
 */
@NoOfuscation
public class Error {
    /**
     * Razon por la que ha fallado
     */
    private String reason;
    /**
     * Razon por la que ha fallado
     */
    private String typeError;

    public Error(){
        this("");
    }

    public Error(String reason, String typeError) {
        this.setReason(reason);
        this.setTypeError(typeError);
    }

    public Error(String reason) {
        this(reason,"");
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getTypeError() {
        return typeError;
    }

    public void setTypeError(String typeError) {
        this.typeError = typeError;
    }
}
