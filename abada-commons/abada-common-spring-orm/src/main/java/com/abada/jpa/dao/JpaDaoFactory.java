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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javassist.*;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.StringMemberValue;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author katsu
 */
public class JpaDaoFactory {

    private static final String JAVASSIT = "$$javassit";
    private static final String TRANSACTION_ANNOTATION_TYPE = "org.springframework.transaction.annotation.Transactional";
    private static final String VALUE = "value";
    private Map<String, Class> cache;

    public JpaDaoFactory() {
        cache = new HashMap<String, Class>();
    }

    //TODO cache para clases y nombre de la clase principal que incluya transactionManager
    public <T> T createInstance(Class<T> clazz, String transactionManagerName) throws NotFoundException, InstantiationException, IllegalAccessException, CannotCompileException, IOException {
        String newNameClass = this.getNewNameClass(clazz, transactionManagerName);
        if (cache.containsKey(newNameClass)) {
            return (T) cache.get(newNameClass).newInstance();
        } else {
            Class newClass = this.getModifiedClass(clazz, transactionManagerName, newNameClass);
            cache.put(newNameClass, newClass);
            return (T) newClass.newInstance();
        }
    }

    private <T> String getNewNameClass(Class<T> clazz, String transactionManagerName) {
        return clazz.getName() + transactionManagerName.replace("-", "") + JAVASSIT;
    }

    private <T> Class<T> getModifiedClass(Class<T> clazz, String transactionManagerName, String newNameClass) throws NotFoundException, CannotCompileException, IOException {
        //pool creation
        ClassPool cp = ClassPool.getDefault();
        cp.insertClassPath(new ClassClassPath((this.getClass())));
        //extracting the class
        CtClass cc = cp.get(clazz.getName());
        //creating the new one
        cc.setName(newNameClass);
        cc.setModifiers(Modifier.PUBLIC);
        //modifing the JpaDao superclass, persistence unit and method with Transactional
        CtClass superClass = cc.getSuperclass();
        ConstPool constPool = superClass.getClassFile().getConstPool();

        //Transactional                        
        for (CtMethod method : superClass.getMethods()) {
            if (method.hasAnnotation(Transactional.class)) {
                setTransactionManager(constPool, method, transactionManagerName);
            }
        }

        //creating new one JpaDao with new name
        superClass.setName(superClass.getName() + cc.getSimpleName() + JAVASSIT);
        superClass.setModifiers(Modifier.PUBLIC);
        //setting class T like superclass to the new on JpaDao, to allow casting in java code of the new class
        superClass.setSuperclass(cp.getCtClass(clazz.getName()));
        //loading new class JpaDao
        superClass.toClass();

        //setting new JpaDao as superclass
        cc.setSuperclass(superClass);
        //loading new class of T
        return cc.toClass();
    }

    private void setTransactionManager(ConstPool cp, CtMethod m, String transactionManagerName) throws NotFoundException {

        AnnotationsAttribute attr = (AnnotationsAttribute) m.getMethodInfo().getAttribute(AnnotationsAttribute.visibleTag);
        Annotation annotation = attr.getAnnotation(TRANSACTION_ANNOTATION_TYPE);
        //annotation.addMemberValue(VALUE, new StringMemberValue(transactionManagerName, cp));         

        AnnotationsAttribute newAttr = new AnnotationsAttribute(cp, AnnotationsAttribute.visibleTag);
        for (Annotation aux : attr.getAnnotations()) {
            if (!aux.getTypeName().equals(TRANSACTION_ANNOTATION_TYPE)) {
                newAttr.addAnnotation(aux);
            }
        }

        Annotation newAnnotation = new Annotation(TRANSACTION_ANNOTATION_TYPE, cp);

        if (annotation.getMemberNames() != null) {
            for (Object s : annotation.getMemberNames()) {
                newAnnotation.addMemberValue(s.toString(), annotation.getMemberValue(s.toString()));
            }
        }

        newAnnotation.addMemberValue(VALUE, new StringMemberValue(transactionManagerName, cp));
        newAttr.addAnnotation(newAnnotation);
        m.getMethodInfo().addAttribute(newAttr);

    }
}
