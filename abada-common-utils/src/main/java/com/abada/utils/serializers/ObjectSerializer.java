/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.abada.utils.serializers;

/*
 * #%L
 * Abada Commons Utils
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

import java.beans.XMLDecoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.util.ArrayList;


/**
 * Usefull methods to deal with Object serialization and save/restore settings
 * by the easy way.
 * @author Angel R
 */
public class ObjectSerializer {

    public static byte[] object2Bytes(Object o) throws IOException {
        return objectArray2Bytes(new Object[]{o});
    }

    public static Object bytes2Object(byte raw[]) throws IOException, ClassNotFoundException {
        Object[] oArray = bytes2ObjectArray(raw);
        return ((oArray != null && oArray.length > 0)? oArray[0]:null);
    }

    public static byte[] objectArray2Bytes(Object[] oArray) throws IOException{
        byte[] result = null;
        if(oArray != null){
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream( baos );
            oos.writeInt(oArray.length);
            for(Object o:oArray){
                oos.writeObject(o);
            }
            result = baos.toByteArray();
            baos.close();
            oos.close();
        }
        return result;
    }

    public static Object[] bytes2ObjectArray(byte raw[]) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bais = new ByteArrayInputStream( raw );
        ObjectInputStream ois = new ObjectInputStream( bais );
        int nObjects = ois.readInt();
        Object[] oArray = new Object[nObjects];
        for(int i=0;i<nObjects;i++){
            oArray[i] = ois.readObject();
        }
        ois.close();
        return oArray;
    }


    /**
     * WARNING Serialized objects depends on current implementation, that is,
     * serialized objects can be incompatibles between diferents JVM.
     * The current serialization support is appropriate for short term storage
     * or RMI between applications running the same version of JVM.
     * As of 1.4, support for long term storage of all JavaBeansTM  has been
     * added to the java.beans package. Please see {@link XMLEncoder}.
     * @param o
     * @param f
     * @return number of writed Objects, -1 if oArray is null or file is
     * not writable
     * @throws java.io.IOException
     */
    public static int objectArray2BinaryFile(Object[] oArray, File f) throws IOException {
        int i = -1;
        if(oArray != null && f != null && f.canWrite()){
            ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(f)));
            for(i=0;i<oArray.length;i++){
                oos.writeObject(oArray[i]);
            }
            oos.close();
        }
        return i;
    }

    public static boolean object2BinaryFile(Object o, File f) throws IOException {
        return (objectArray2BinaryFile(new Object[]{o}, f) > 0 ? true : false);
    }

    /**
     * Read nObjects from File. Files read with this method MUST BEEN created
     * by {@link objectArray2BinaryFile} so nObjects can be the same value
     * returned by.
     * @param f
     * @param nObjects
     * @return
     * @throws java.io.IOException
     * @throws java.lang.ClassNotFoundException
     */
    public static Object[] binaryFile2ObjectArray(File f, int nObjects) throws IOException, ClassNotFoundException{
        Object[] result = null;
        if(nObjects >0 && f != null && f.canWrite()){
            ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(f)));
            result = new Object[nObjects];
            for(int i=0;i<nObjects;i++){
                result[i] = ois.readObject();
            }
            ois.close();
        }
        return result;
    }

    public static Object binaryFile2Object(File f) throws IOException, ClassNotFoundException {
        Object [] result = binaryFile2ObjectArray(f,1);
        return ((result != null && result.length>0)? result[0]:null);
    }

    public static int objectArray2XmlFile(Object[] oArray, File f) throws FileNotFoundException {
        int i = -1;
        if(oArray != null && f != null && f.canWrite()){
            XMLEncoder e = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(f)));
            for(i=0;i<oArray.length;i++){
                e.writeObject(oArray[i]);
            }
            e.close();
        }
        return i;
    }

    public static boolean object2XmlFile(Object o, File f) throws FileNotFoundException {
        return (objectArray2XmlFile(new Object[]{o}, f) > 0 ? true : false);
    }

    public static Object[] xmlFile2ObjectArray(File f) throws FileNotFoundException{
        XMLDecoder d = new XMLDecoder(new BufferedInputStream(new FileInputStream(f)));
        ArrayList result = new ArrayList();
        boolean moreObjects = true;
        while(moreObjects){
            try{
                result.add(d.readObject());
            }
            catch(ArrayIndexOutOfBoundsException ex){
                moreObjects = false;
            }
        }
        d.close();
        return result.toArray();
    }

    public static Object xmlFile2Object(File f) throws FileNotFoundException{
        Object[] result = xmlFile2ObjectArray(f);
        return result[0];
    }



}
