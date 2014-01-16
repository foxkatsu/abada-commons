/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.springframework.orm.jpa.support;

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

import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.springframework.orm.jpa.JpaCallback;

/**
 * Añade funcionalidad para ejecutar queries JPQL
 * @author katsu
 */
@Deprecated
public abstract class JpaDaoSupport extends org.springframework.orm.jpa.support.JpaDaoSupport {

    /**
     * Ejecutar una query JPQL
     * @param queryJPQL Query JPQL
     * @param namedParam Mapa con las sustituciones de las variables de la JPQL con sus valores
     * @return
     */
    protected List find(final String queryJPQL, final Map<String, Object> namedParam) {
        return find(queryJPQL, namedParam, -1, Integer.MIN_VALUE);
    }

    /**
     * Ejecutar una query JPQL y permite limitar el tamaño de la query
     * @param queryJPQL Query JPQL
     * @param namedParam Mapa con las sustituciones de las variables de la JPQL con sus valores
     * @param start Posicion de inicio respecto de los resultados
     * @param max Longitud maxima del resultado a partir del start
     * @return
     */
    protected List find(final String queryJPQL, final Map<String, Object> namedParam, final int start, final int max) {
        //this.logger.debug(queryJPQL);
        return (List) this.getJpaTemplate().execute(new JpaCallback() {

            public Object doInJpa(EntityManager em) throws PersistenceException {
                //creo una query vacia
                Query query = em.createQuery(queryJPQL);
                //Añade parametros
                if (namedParam != null) {
                    for (String key : namedParam.keySet()) {
                        //System.out.println(key+" "+namedParam.get(key));
                        query.setParameter(key, namedParam.get(key));
                    }
                }
                //pongo los limites
                if (start > -1) {
                    query.setFirstResult(start);
                    query.setMaxResults(max);
                }

                return query.getResultList();
            }
        });
    }
}
