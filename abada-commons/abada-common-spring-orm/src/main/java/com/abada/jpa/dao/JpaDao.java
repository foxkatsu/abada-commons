/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.jpa.dao;

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

import com.abada.extjs.ExtjsStore;
import com.abada.springframework.orm.jpa.support.JpaDaoUtils;
import com.abada.springframework.web.servlet.command.extjs.gridpanel.GridRequest;
import com.abada.springframework.web.servlet.command.extjs.gridpanel.factory.GridRequestFactory;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author katsu
 * @author aroldan
 */
public abstract class JpaDao<T,ID> extends JpaDaoUtils implements Dao<T, ID> {
    private static final String C_NEW = "new";
    private static final String GET = "get";
    private static final String ID = "id";
    private static final String IS = "is";
    private static final String OLD = "old";
    private static final String SQL_ENTITY_ALIAS_1 = " e ";
    private static final String SQL_ENTITY_ALIAS_2 = "e";
    private static final String SQL_SELECT = "select e from ";
    private static final String SQL_SELECT_COUNT = "select count(distinct e) from ";
    
    private static final Log logger = LogFactory.getLog(JpaDao.class);    
    
    protected EntityManager em;
    private DaoReflection jpaReflection;
    
    /**
     * Recupera la informacion del DAO obtenida por reflection
     * @return
     */
    protected DaoReflection getDAOReflection() {
        if (jpaReflection == null) {
            jpaReflection = DaoReflection.getDAOReflection(this);
        }
        return jpaReflection;
    }

    public abstract void setEntityManager(EntityManager em);
    
    /**
     * Recupera el entity manager
     * @return
     */
    protected EntityManager em() {
        return em;
    }

    @Override
    @Transactional
    public void insert(T o) {
        em.persist(o);
    }

    @Override
    @Transactional
    public T update(T o) {
        T updated = em.merge(o);
        return updated;
    }

    @Override
    @Transactional
    public void updateId(T o, ID id) {
        Query q = em.createQuery(getDAOReflection().getUpdateIdQuery());
        q.setParameter(OLD, (ID) getDAOReflection().getId(o));
        q.setParameter(C_NEW, id);
        q.executeUpdate();
        getDAOReflection().setId(o, id);
    }

    @Override
    @Transactional
    public void delete(T o) {
        if (em.contains(o)) {
            em.remove(o);
        } else {
            deleteById((ID) getDAOReflection().getId(o));
        }
    }

    @Override
    @Transactional
    public void deleteById(ID id) {
        Query q = em.createQuery(getDAOReflection().getDeleteByIdQuery());
        q.setParameter(ID, id);
        q.executeUpdate();
    }

    @Override
    @Transactional
    public T findById(ID id) {
        return (T) em.find(getDAOReflection().getEntityClass(), id);
    }

    @Override
    @Transactional
    public T findById(ID id, boolean allCollections) {
        T e = this.findById(id);
        if (allCollections) {
            loadCollections(e.getClass(), e);
        }
        return e;
    }

    @Override
    @Transactional
    public List<T> findAll() {
        String q = getDAOReflection().getFindAllQuery();
        return (List<T>) em.createQuery(q).getResultList();
    }

    @Override
    @Transactional(readOnly=true)
    public List<T> findAll(boolean allCollections) {
        List<T> list = this.findAll();
        if (allCollections) {
            for (T e : list) {
                loadCollections(e.getClass(), e);
            }
        }
        return list;
    }

    @Override
    @Transactional
    public long countAll() {
        String q = getDAOReflection().getCountAllQuery();
        return (Long) em.createQuery(q).getSingleResult();
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> findAllByFilter(GridRequest filters) {
        List<T> resultAux = this.find(em, SQL_SELECT + getDAOReflection().getEntityClass().getName() + SQL_ENTITY_ALIAS_1 + filters.getQL(SQL_ENTITY_ALIAS_2, true), filters.getParamsValues(), filters.getStart(), filters.getLimit());
        return resultAux;
    }

    @Override
    @Transactional(readOnly=true)
    public List<T> findAllByFilter(GridRequest filters, boolean allCollections) {
        List<T> list = this.findAllByFilter(filters);
        if (allCollections) {
            for (T e : list) {
                loadCollections(e.getClass(), e);
            }
        }
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public Long countAllByFilter(GridRequest filters) {
        List<Long> result = this.find(em, SQL_SELECT_COUNT + getDAOReflection().getEntityClass().getName() + SQL_ENTITY_ALIAS_1 + filters.getQL(SQL_ENTITY_ALIAS_2, true), filters.getParamsValues());
        return result.get(0);
    }

    @Override
    @Transactional(readOnly = true)
    public ExtjsStore<T> findAllByFilterStore(Integer limit, String sort, Integer start, String filter) {
        ExtjsStore<T> store = new ExtjsStore<T>();
        GridRequest gridrequest;
        try {
            gridrequest = GridRequestFactory.parse(sort, start, limit, filter);
            store.setData(this.findAllByFilter(gridrequest));
            store.setTotal(this.countAllByFilter(gridrequest).intValue());
        } catch (UnsupportedEncodingException ex) {
            logger.error(ex);
        }
        return store;
    }

    @Transactional(readOnly = true)
    @Override
    public ExtjsStore findAllByFilterStore(Integer limit, String sort, Integer start, String filter, boolean allCollections) {
        ExtjsStore store = this.findAllByFilterStore(limit, sort, start, filter);
        if (allCollections) {
            List<T> list = store.getData();
            for (T e : list) {
                loadCollections(e.getClass(), e);
            }
        }
        return store;
    }

    private void loadCollections(Class c, T e) {
        if (c.getSuperclass() != null) {
            loadCollections(c.getSuperclass(), e);
        }

        Method[] methods = c.getMethods();
        for (Method m : methods) {
            if (isOneToManyOrManyToManyMethod(m)) {
                try {
                    ((Collection) m.invoke(e)).size();
                    /*Collection<T> col = (Collection) m.invoke(e);
                    for(T entity: col){
                    loadCollections(entity.getClass(), entity);
                    }*/
                } catch (Exception ex) {
                    logger.error(ex);
                }
            }
            //Leo recursivamente las colecciones de la Tes realacionadas con el objeto a escanear
            /*if (isEntity(m)) {
            try {
            loadCollections(m.getReturnType(), (T) m.invoke(e));
            } catch (Exception ex) {
            logger.error(ex);
            }
            }*/
        }
        Field[] fields = c.getDeclaredFields();
        for (Field f : fields) {
            if (isOneToManyOrManyToManyField(f)) {
                try {
                    Method m = e.getClass().getMethod(GET + firstCharUpper(f.getName()));
                    ((Collection) m.invoke(e)).size();
                } catch (Exception ex) {
                    logger.error(ex);
                }
            }
        }
    }

    private boolean isOneToManyOrManyToManyMethod(Method m) {
        return (m.isAnnotationPresent(javax.persistence.OneToMany.class) || m.isAnnotationPresent(javax.persistence.ManyToMany.class))
                && (m.getName().startsWith(GET) || m.getName().startsWith(IS))
                && m.getParameterTypes().length == 0;
    }

    private boolean isOneToManyOrManyToManyField(Field f) {
        boolean result = (f.isAnnotationPresent(javax.persistence.OneToMany.class) || f.isAnnotationPresent(javax.persistence.ManyToMany.class));

        return result;
    }

    public boolean isEntity(Method m) {
        return m.getReturnType().isAnnotationPresent(javax.persistence.Entity.class);
    }

    private String firstCharUpper(String cad) {
        String first = cad.substring(0, 1).toUpperCase();
        String aux = cad.substring(1);
        return first + aux;
    }
}
