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
public class Success {
    /**
     * Representa el estado del resultado de la llamada<br/>
     * Correcto o no
     */
    private Boolean success;
    /**
     * Representa la razon por la que no se ha ejecutado correctamente
     */
    private Error errors;
    /**
     * Representa la razon por la que no se ha ejecutado correctamente
     */
    private String msg;

    public Success(){
        this(false);
    }

    public Success(Boolean success) {
        this(success, "");
    }

    public Success(Boolean success, String reason) {
        this(success, new Error(reason));
    }

    private Success(Boolean success, Error errors) {
        this.success = success;
        if (!success) {
            this.errors = errors;
        } else {
            this.errors = null;
        }
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Error getErrors() {
        return errors;
    }

    public void setErrors(Error errors) {
        this.errors = errors;
        this.msg=errors.getReason();
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
        this.errors=new Error(msg);
    }
        
}
