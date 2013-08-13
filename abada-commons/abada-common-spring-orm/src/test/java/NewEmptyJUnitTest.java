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
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import com.abada.jpa.dao.JpaDaoFactory;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import javassist.CannotCompileException;
import javassist.NotFoundException;
import javax.persistence.PersistenceContext;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author katsu
 */
public class NewEmptyJUnitTest {

    public NewEmptyJUnitTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void test() {
        try {
            DaoTest dt = new JpaDaoFactory().createInstance(DaoTest.class, "afafafadf");

            Field[] fields = dt.getClass().getFields();
            for (Field m : fields) {
                if (m.isAnnotationPresent(PersistenceContext.class)) {
                    PersistenceContext t = m.getAnnotation(PersistenceContext.class);
                    System.out.println(t.unitName());
                }
            }
            
            Method[] methods = dt.getClass().getMethods();
            for (Method m : methods) {
                if (m.isAnnotationPresent(Transactional.class)) {
                    Transactional t = m.getAnnotation(Transactional.class);
                    System.out.println(t.value()+" "+t.readOnly());
                }
            }


            System.out.println("");
        } catch (Exception ex) {
            Logger.getLogger(NewEmptyJUnitTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
