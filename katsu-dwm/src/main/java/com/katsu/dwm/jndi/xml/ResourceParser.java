/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.katsu.dwm.jndi.xml;

/*
 * #%L
 * Katsu DWM
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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author katsu
 */
public class ResourceParser {

    public static List<Resource> parse(InputStream inputStream) throws ParserConfigurationException, SAXException, IOException{
        List<Resource> result=new ArrayList<Resource>();        
        
        DocumentBuilderFactory documentBuilderFactory=DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder=documentBuilderFactory.newDocumentBuilder();
        Document doc=documentBuilder.parse(inputStream);
        
        Element root=doc.getDocumentElement();
        NodeList resources=root.getElementsByTagName("Resource");
        
        for (int i=0;i<resources.getLength();i++){
            Resource res=parse(resources.item(i));
            if (res!=null)
                result.add(res);
        }
        return result;
    }
    
    private static Resource parse(Node node){
        Resource result=new Resource();
        NamedNodeMap attributes =node.getAttributes();
        //auth
        Node aux=attributes.getNamedItem("auth");
        if (aux!=null)
            result.setAuth(aux.getNodeValue());
        //driverClassName
        aux=attributes.getNamedItem("driverClassName");
        if (aux!=null)
            result.setDriverClassName(aux.getNodeValue());
        //name
        aux=attributes.getNamedItem("name");
        if (aux!=null)
            result.setName(aux.getNodeValue());
        //password
        aux=attributes.getNamedItem("password");
        if (aux!=null)
            result.setPassword(aux.getNodeValue());
        //type
        aux=attributes.getNamedItem("type");
        if (aux!=null)
            result.setType(aux.getNodeValue());
        //url
        aux=attributes.getNamedItem("url");
        if (aux!=null)
            result.setUrl(aux.getNodeValue());
        //username
        aux=attributes.getNamedItem("username");
        if (aux!=null)
            result.setUsername(aux.getNodeValue());
        //maxActive
        aux=attributes.getNamedItem("maxActive");
        if (aux!=null)
            result.setMaxActive(Integer.parseInt(aux.getNodeValue()));
        //minIdle
        aux=attributes.getNamedItem("maxIdle");
        if (aux!=null)
            result.setMaxIdle(Integer.parseInt(aux.getNodeValue()));        
        return result;
    }
}
