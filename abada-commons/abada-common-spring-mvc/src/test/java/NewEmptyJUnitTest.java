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

import com.abada.json.JsonProperty;
import com.abada.springframework.http.converter.json.JsonHttpMessageConverter;
import com.abada.springframework.web.client.RestTemplate;
import com.abada.springframework.web.client.RestTemplateFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequestWrapper;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;

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
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testRestTemplate(){
        //Inicio la factoria de restTemplates, y uso la factoria para pedir todas las instancias de resttemplate que quiera
        //una por llamada simultanea
        RestTemplateFactory restTemplateFactory=new RestTemplateFactory();
        
        JsonHttpMessageConverter jsonHttpMessageConverter=new JsonHttpMessageConverter();
        Map<JsonProperty, String> prop=new HashMap<JsonProperty, String>();
        prop.put(JsonProperty.DATE_FORMAT, "yyyy-MM-dd HH:mm:ss");
        jsonHttpMessageConverter.setJsonProperties(prop);
        
        StringHttpMessageConverter stringHttpMessageConverter=new StringHttpMessageConverter();
        List<MediaType> values=new ArrayList<MediaType>();
        values.add(new MediaType("text/plain;charset=UTF-8"));
        stringHttpMessageConverter.setSupportedMediaTypes(values);
                
        List mc=new ArrayList();
        mc.add(jsonHttpMessageConverter);
        mc.add(stringHttpMessageConverter);
        restTemplateFactory.setMessageConverters(mc);
                
        //
        RestTemplate restTemplate=restTemplateFactory.createInstance();
        try{
            //se pasa el servlet request, usuario, password
            restTemplate.setRequestFactory(new HttpServletRequestWrapper(null), "user","password");
            //restTemplate.la operacion q sea
        }catch (Exception e){}
        
    }
}
