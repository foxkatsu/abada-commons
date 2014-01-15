/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.katsu.jpa.dao;

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

import com.katsu.extjs.ExtjsStore;
import com.katsu.springframework.web.servlet.command.extjs.gridpanel.GridRequest;
import java.util.List;

/**
 *
 * @author katsu
 */
public interface Dao<ENTIDAD,ID> {

	/**
	 * Insercion de entidad
	 * @param o
	 */
	void insert(ENTIDAD o);
	/**
	 * Actualizacion de entidad
	 * @param o
	 * @return
	 */
	ENTIDAD update(ENTIDAD o);	
	/**
	 * Actualizacion de identificador de entidad
	 * @param o
	 * @param id
	 */
	void updateId(ENTIDAD o,ID id);
	/**
	 * Eliminacion de entidad
	 * @param o
	 */
	void delete(ENTIDAD o);
	/**
	 * Eliminacion de entidad apartir de su identificador
	 * @param id
	 */
	void deleteById(ID id);
	/**
	 * Recuperacion de entidad apartir de su identificador
	 * @param id
	 * @return
	 */
	ENTIDAD findById(ID id);
        /**
	 * Recuperacion de entidad apartir de su identificador
	 * @param id
         * @param allCollections true para recuperar todas las colecciones (oneToMany)
	 * @return
	 */
	ENTIDAD findById(ID id,boolean allCollections);
        
	/**
	 * Cuenta el numero total de entidades
	 * @return
	 */
	long countAll();
	/**
	 * Recupera el listado total de entidades
	 * @return
	 */
	List<ENTIDAD> findAll();
        /**
	 * Recupera el listado total de entidades
         * @param allCollections true para recuperar todas las colecciones (oneToMany)
	 * @return
	 */
	List<ENTIDAD> findAll(boolean allCollections);
        /**
	 * Recupera el listado de entidades que cumplen un criterio
         * @param GridRequest
	 * @return List<ENTIDAD>
	 */
        List<ENTIDAD> findAllByFilter(GridRequest filters);
        /**
	 * Recupera el listado de entidades que cumplen un criterio
         * @param GridRequest
         * @param allCollections true para recuperar todas las colecciones (oneToMany)
	 * @return List<ENTIDAD>
	 */
        List<ENTIDAD> findAllByFilter(GridRequest filters, boolean allCollections);
        /**
	 * Recupera el numero total de entidades que cumplen un criterio
         * @param GridRequest
	 * @return Long
	 */
        Long countAllByFilter(GridRequest filters);
        /**
	 * Recupera el Store con las entidades que cumplen el criterio
         * @param GridRequest
	 * @return ExtjsStore
	 */
        ExtjsStore<ENTIDAD> findAllByFilterStore(Integer limit, String sort, Integer start, String filter);
        /**
	 * Recupera el Store con las entidades que cumplen el criterio
         * @param GridRequest
	 * @return ExtjsStore
         * @param allCollections true para recuperar todas las colecciones (oneToMany)
	 */
        ExtjsStore<ENTIDAD> findAllByFilterStore(Integer limit, String sort, Integer start, String filter,boolean allCollections);	        
}
