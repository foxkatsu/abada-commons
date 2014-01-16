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

/**
 * Genera queries estandar para un DAO y su entidad asociada mediante la api
 * de reflection.
 * 
 * @author katsu
 */
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

@SuppressWarnings({"rawtypes", "unchecked"})
public final class DaoReflection {

    private static final String FIND_ALL = "FROM {0}";
    private static final String COUNT_ALL = "SELECT count(*) FROM {0}";
    private static final String DELETE_BY_ID = "DELETE FROM {0} obj WHERE obj.{1}=:id";
    private static final String UPDATE_ID = "UPDATE {0} obj SET obj.{1}=:new WHERE obj.{1}=:old";
    private static final String FIND_BY_ID = "FROM {0} obj WHERE obj.{1} = :id";
    private static final String PREFIX_GETTER = "get";
    private static final int PREFIX_GETTER_LENGTH = PREFIX_GETTER.length();
    private static final String PREFIX_SETTER = "set";
    private static Map<Class<? extends Dao<?, ?>>, DaoReflection> cache = new HashMap<Class<? extends Dao<?, ?>>, DaoReflection>();

    /**
     * Recupera la informacion de reflection para el dao
     * 
     * @param dao
     * @return
     */
    public static DaoReflection getDAOReflection(Dao<?, ?> dao) {
        if (cache.containsKey(dao.getClass())) {
            return cache.get(dao.getClass());
        }
        Class<? extends Dao<?, ?>> daoClass = (Class<? extends Dao<?, ?>>) dao.getClass();
        DaoReflection instance = new DaoReflection(daoClass);
        cache.put(daoClass, instance);
        return instance;
    }

    /**
     * Recupera la clase de entidad asociada al dao
     * 
     * @param clazz
     *            clase dao
     * @return
     */
    private static Class<?> getEntityClass(Class<? extends Dao<?, ?>> clazz) {
        Class<?> daoClass = clazz;
        if (ClassUtils.isCglibProxyClass(daoClass)) {
            daoClass = daoClass.getSuperclass();
        }
        ParameterizedType genericSuperclass = (ParameterizedType) daoClass.getGenericSuperclass();
        Class<?> entityClass = (Class<?>) genericSuperclass.getActualTypeArguments()[0];
        return entityClass;
    }

    /**
     * Recupera el {@link Field} o {@link Method} anotado como identificador
     * para la clase entidad
     * 
     * @param clazz
     *            clase entidad
     * @return
     */
    private static AccessibleObject getAccessibleId(Class<?> clazz) {
        Method[] methods = clazz.getMethods();
        for (Method m : methods) {
            if (isIdMethod(m)) {
                return m;
            }
        }
        return getAccesibleFieldId(clazz);
    }

    private static AccessibleObject getAccesibleFieldId(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field f : fields) {
            if (isIdField(f)) {
                return f;
            }
        }
        if (clazz.getSuperclass() != null) {
            //Busco en la clase padre hasta que encuentro el id.
            //Si ningún padre tiene id, saltará la excepción.
            return getAccessibleId(clazz.getSuperclass());
        }
        return null;
    }

    /**
     * Comprueba que un {@link Field} este anotado como identificador
     * 
     * @param f
     * @return
     */
    private static boolean isIdField(Field f) {
        return f.isAnnotationPresent(javax.persistence.Id.class)
                || f.isAnnotationPresent(javax.persistence.EmbeddedId.class);
    }

    /**
     * Comprueba que un {@link Method} este anotado como identificador
     * 
     * @param m
     * @return
     */
    private static boolean isIdMethod(Method m) {
        return (m.isAnnotationPresent(javax.persistence.Id.class) || m.isAnnotationPresent(javax.persistence.EmbeddedId.class))
                && m.getName().startsWith(PREFIX_GETTER)
                && m.getParameterTypes().length == 0;
    }

    private static Method setter(Class clazz, String pname, Class ptype) {
        try {
            return clazz.getMethod(PREFIX_SETTER + StringUtils.capitalize(pname), new Class[]{ptype});
        } catch (NoSuchMethodException e) {
        }
        return null;
    }

    /**
     * Recupera el metodo getter asociado al {@link Field}
     * 
     * @param f
     * @return
     */
    private static Method getter(Field f) {
        Class<?> clazz = f.getDeclaringClass();
        try {
            return clazz.getMethod(PREFIX_GETTER
                    + StringUtils.capitalize(f.getName()), new Class[0]);
        } catch (NoSuchMethodException e) {
        }
        return null;
    }

    /**
     * Recupera el nombre de la propiedad asociado al {@link Method}
     * 
     * @param m
     * @return
     */
    private static String property(Method m) {
        return StringUtils.uncapitalize(m.getName().substring(
                PREFIX_GETTER_LENGTH));
    }
    private final Class<?> entityClass;
    private final Class<?> idClass;
    private final String idProperty;
    private final Method idGetter;
    private final Method idSetter;
    private final String countAllQuery;
    private final String findAllQuery;
    private final String deleteByIdQuery;
    private final String findByIdQuery;
    private final String updateIdQuery;

    private DaoReflection(Class<? extends Dao<?, ?>> daoClass) {
        this.entityClass = getEntityClass(daoClass);
        if (!entityClass.isAnnotationPresent(javax.persistence.Entity.class)) {
            throw new InvalidDataAccessApiUsageException(MessageFormat.format(
                    "Not an entity class `{0}`", this.entityClass.getName()));
        }
        AccessibleObject accessible = getAccessibleId(this.entityClass);
        if (accessible instanceof Field) {
            Field f = (Field) accessible;
            this.idProperty = f.getName();
            this.idGetter = getter(f);
            this.idClass = f.getType();

        } else if (accessible instanceof Method) {
            Method m = (Method) accessible;
            this.idGetter = m;
            this.idProperty = property(m);
            this.idClass = m.getReturnType();
        } else {
            throw new InvalidDataAccessApiUsageException(MessageFormat.format(
                    "Unable to find id property for entity class `{0}`",
                    this.entityClass.getName()));
        }
        this.idSetter = setter(entityClass, idProperty, idClass);
        this.countAllQuery = MessageFormat.format(COUNT_ALL,
                this.entityClass.getName());
        this.findAllQuery = MessageFormat.format(FIND_ALL, this.entityClass.getName());
        this.findByIdQuery = MessageFormat.format(FIND_BY_ID, this.entityClass.getName(), this.idProperty);
        this.deleteByIdQuery = MessageFormat.format(DELETE_BY_ID, this.entityClass.getName(), this.idProperty);
        this.updateIdQuery = MessageFormat.format(UPDATE_ID, this.entityClass.getName(), this.idProperty);
    }

    /**
     * Recupera el valor de la propiedad identificador para la instancia del
     * objeto de la clase entidad
     * 
     * @param o
     * @return
     */
    public Object getId(Object o) {
        try {
            return idGetter.invoke(o, new Object[0]);
        } catch (Exception e) {
            throw new InvalidDataAccessApiUsageException(MessageFormat.format(
                    "Unable to retrieve id for entity `{0}`", entityClass.getName()), e);
        }
    }

    /**
     * Establece el valor de la propiedad identificador para la instancia del
     * objeto de la clase entidad
     * 
     * @param o
     * @return
     */
    public void setId(Object o, Object id) {
        try {
            idSetter.invoke(o, new Object[]{id});
        } catch (Exception e) {
            throw new InvalidDataAccessApiUsageException(MessageFormat.format(
                    "Unable to set id for entity `{0}`", entityClass.getName()), e);
        }
    }

    /**
     * Recupera query JPQL para realizar cambio de identificador
     * @return
     */
    public String getUpdateIdQuery() {
        return updateIdQuery;
    }

    /**
     * Recupera query JPQL para realizar busqueda por identificador
     * @return
     */
    public String getFindByIdQuery() {
        return findByIdQuery;
    }

    /**
     * Recupera query JPQL para realizar delete por identificador
     * @return
     */
    public String getDeleteByIdQuery() {
        return deleteByIdQuery;
    }

    /**
     * Recupera query JPQL para realizar la cuenta de registros
     * @return
     */
    public String getCountAllQuery() {
        return countAllQuery;
    }

    /**
     * Recupera query JPQL para realizar la busqueda de registros
     * @return
     */
    public String getFindAllQuery() {
        return findAllQuery;
    }

    /**
     * Recupera clase entidad
     * @return
     */
    public Class<?> getEntityClass() {
        return entityClass;
    }

    /**
     * Recupera clase id
     * @return
     */
    public Class<?> getIdClass() {
        return idClass;
    }

    /**
     * Recupera metodo getter para el identificador
     * @return
     */
    public Method getIdGetter() {
        return idGetter;
    }

    /**
     * Recupera nombre de propiedad para el identificador
     * @return
     */
    public String getIdProperty() {
        return idProperty;
    }
}
