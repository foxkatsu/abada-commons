
package com.katsu.utils.settings;

/*
 * #%L
 * Katsu Commons Utils
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

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import javax.swing.JButton;
import static com.katsu.utils.serializers.ObjectSerializer.*;

/**
 * Utilities for Save/restore application settings and hight level file utils
 * @author Angel R
 */
public class Settings {

    /**
     * Store all settings in a binary file compressed or uncompressed.
     * Note that File must end with the rigth extension
     * (see {@link FileExtensions} or nothing is done.
     * TODO Add cipher option to increase security
     */
    public static boolean saveSettingsToBinaryFile(ArrayList settings, File f, boolean compress) throws FileNotFoundException, IOException{
        boolean saved = false;
        if(settings != null && settings.size() > 0 && f != null && ((compress && f.getName().endsWith(FileExtensions.COMPRESS.getValue())) || (!compress && f.getName().endsWith(FileExtensions.BINARY.getValue())))){
            if(compress){
                ZipOutputStream zip = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(f)));
                for(int i = 0;i<settings.size();i++){
                    Object obj = settings.get(i);
                    zip.putNextEntry(new ZipEntry(obj.getClass().getName()+"_"+i));
                    byte[] bytes = object2Bytes(obj);
                    zip.write(bytes);
                    zip.closeEntry();
                }
                zip.close();
                saved = true;
            }
            else{
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(f));
                byte[] bytes = objectArray2Bytes(settings.toArray());
                out.write(bytes);
                out.close();
                saved = true;
            }
        }
        return saved;
    }

    /**
     * Store all settings in a xml file compressed or uncompressed.
     * Note that File must end with the rigth extension
     * (see {@link FileExtensions} or nothing is done. This is the preferred way
     * for store and retrieve settings.
     * TODO Add cipher option to increase security
     */
    public static boolean saveSettingsToXMLFile(ArrayList settings, File f, boolean compress) throws FileNotFoundException, IOException{
        boolean saved = false;
        if(settings != null && settings.size() > 0 && f != null && ((compress && f.getName().endsWith(FileExtensions.COMPRESS.getValue())) || (!compress && f.getName().endsWith(FileExtensions.XML.getValue())))){
            XMLEncoder enc = null;
            if(compress){
                ZipOutputStream zip = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(f)));
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                for(int i=0;i<settings.size();i++){
                    enc = new XMLEncoder(baos);
                    Object obj = settings.get(i);
                    zip.putNextEntry(new ZipEntry(obj.getClass().getName()+"_"+i));
                    enc.writeObject(obj);
                    enc.close();
                    byte[] bytes = baos.toByteArray();
                    baos.reset();
                    zip.write(bytes);
                    zip.closeEntry();
                }
                zip.close();
                saved = true;
            }
            else{
                enc = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(f)));
                for(Object obj:settings){
                   enc.writeObject(obj);
                }
                enc.close();
                saved = true;
            }    
        }
        return saved;
    }

    /**
     * Store all settings in several binary files compressed or uncompressed.
     * Note that several files will be created in fDir with correct extension
     * (see {@link FileExtensions}.
     * TODO Add cipher option to increase security
     */
    public static boolean saveSettingsToBinaryFiles(ArrayList settings, File fDir, boolean compress) throws FileNotFoundException, IOException{
        boolean saved = false;
        if(settings != null && settings.size() > 0 && fDir != null){
            fDir.mkdirs();
            File f = null;
            if(compress){
               ZipOutputStream zip = null;
               for(int i = 0;i<settings.size();i++){
                   Object obj = settings.get(i);
                   f = new File(fDir,obj.getClass().getName()+"_"+FileExtensions.COMPRESS.getValue());
                   zip = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(f)));
                   byte[] bytes = object2Bytes(obj);
                   zip.putNextEntry(new ZipEntry(obj.getClass().getName()+"_"+i));
                   zip.write(bytes);
                   zip.closeEntry();
                   zip.close();
               }
               saved = true;
            }
            else{
                BufferedOutputStream out = null;
                for(int i = 0;i<settings.size();i++){
                   Object obj = settings.get(i);
                   f = new File(fDir,obj.getClass().getName()+"_"+i+FileExtensions.BINARY.getValue());
                   out = new BufferedOutputStream(new FileOutputStream(f));
                   byte[] bytes = object2Bytes(obj);
                   out.write(bytes);
                   out.close();
                }
                saved = true;
            }
        }
        return saved;
    }

    /**
     * Store all settings in several xml files compressed or uncompressed.
     * Note that several files will be created in fDir with correct extension
     * (see {@link FileExtensions}.
     * TODO Add cipher option to increase security
     */
    public static boolean saveSettingsToXMLFiles(ArrayList settings, File fDir, boolean compress) throws FileNotFoundException, IOException{
        boolean saved = false;
        if(settings != null && settings.size() > 0 && fDir != null){
            fDir.mkdirs();
            XMLEncoder enc = null;
            File f = null;
            if(compress){
                ZipOutputStream zip = null;
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                for(int i = 0;i<settings.size();i++){
                    Object obj = settings.get(i);
                    f = new File(fDir,obj.getClass().getName()+"_"+i+FileExtensions.COMPRESS.getValue());
                    zip = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(f)));
                    enc = new XMLEncoder(baos);
                    zip.putNextEntry(new ZipEntry(obj.getClass().getName()+"_"+i));
                    enc.writeObject(obj);
                    enc.close();
                    byte[] bytes = baos.toByteArray();
                    baos.reset();
                    zip.write(bytes);
                    zip.closeEntry();
                    zip.close();
                }
                saved = true;
            }
            else{
                for(int i = 0;i<settings.size();i++){
                    Object obj = settings.get(i);
                    f = new File(fDir,obj.getClass().getName()+"_"+i+FileExtensions.XML.getValue());
                    enc = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(f)));
                    enc.writeObject(obj);
                    enc.close();
                }
                saved = true;
            }
        }
        return saved;
    }

    /**
     * Retrieve all settings from a binary file compressed or uncompressed.
     * Note that File must end with the rigth extension
     * (see {@link FileExtensions} or nothing is done.
     * TODO Add cipher option to increase security
     */
    public static ArrayList restoreSettingsFromBinaryFile(File f, boolean compresed) throws FileNotFoundException, IOException, ClassNotFoundException{
        ArrayList oArray = null;
        if(f != null && f.canRead() && f.isFile() && ((compresed && f.getName().endsWith(FileExtensions.COMPRESS.getValue())) || (!compresed && f.getName().endsWith(FileExtensions.BINARY.getValue())))){
            oArray = new ArrayList();
            if(compresed){
               ZipFile zip = new ZipFile(f);
               ZipEntry entry = null;
               BufferedInputStream bin = null;
               Enumeration<ZipEntry> entries = (Enumeration<ZipEntry>) zip.entries();
               while(entries.hasMoreElements()){
                   entry = entries.nextElement();
                   bin = new BufferedInputStream(zip.getInputStream(entry));
                   int size = 0;
                   while(bin.read() != -1){
                       size++;
                   }
                   bin.close();
                   bin = new BufferedInputStream(zip.getInputStream(entry));
                   byte[] bytes = new byte[size];
                   bin.read(bytes);
                   bin.close();
                   oArray.add(bytes2Object(bytes));
               }
               zip.close();

               /*ZipInputStream zip = new ZipInputStream(new BufferedInputStream(new FileInputStream(f)));
               ZipEntry entry = null;
               while((entry = zip.getNextEntry()) != null){
                   byte[] bytes = new byte[(int)entry.getSize()];
                   zip.read(bytes);
                   oArray.add(bytes2Object(bytes));
                   zip.closeEntry();
               }
               zip.close();*/
            }
            else{
                ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(f)));
                int nObjects = ois.readInt();
                for(int i=0;i<nObjects;i++){
                    oArray.add(ois.readObject());
                }
                ois.close();
            }
        }
        return oArray;
    }

    /**
     * Retrieve all settings from a xml file compressed or uncompressed.
     * Note that File must end with the rigth extension
     * (see {@link FileExtensions} or nothing is done.
     * TODO Add cipher option to increase security
     */
    public static ArrayList restoreSettingsFromXMLFile(File f, boolean compresed) throws FileNotFoundException, IOException, ClassNotFoundException{
        ArrayList oArray = null;
        if(f != null && f.canRead() && f.isFile() && ((compresed && f.getName().endsWith(FileExtensions.COMPRESS.getValue())) || (!compresed && f.getName().endsWith(FileExtensions.XML.getValue())))){
            oArray = new ArrayList();
            XMLDecoder dec = null;
            if(compresed){
                ZipFile zip = new ZipFile(f);
                ZipEntry entry = null;
                BufferedInputStream bin = null;
                Enumeration<ZipEntry> entries = (Enumeration<ZipEntry>) zip.entries();
                while(entries.hasMoreElements()){
                    entry = entries.nextElement();
                    bin = new BufferedInputStream(zip.getInputStream(entry));
                    int size = 0;
                    while(bin.read() != -1){
                        size++;
                    }
                    bin.close();
                    bin = new BufferedInputStream(zip.getInputStream(entry));
                    byte[] bytes = new byte[size];
                    bin.read(bytes);
                    bin.close();
                    dec = new XMLDecoder(new ByteArrayInputStream(bytes));
                    oArray.add(dec.readObject());
                }
                zip.close();

                /*ZipInputStream zip = new ZipInputStream(new BufferedInputStream(new FileInputStream(f)));
                ZipEntry entry = null;
                while((entry = zip.getNextEntry()) != null){
                    byte[] bytes = new byte[(int)entry.getSize()];
                    zip.read(bytes);
                    dec = new XMLDecoder(new ByteArrayInputStream(bytes));
                    oArray.add(dec.readObject());
                    zip.closeEntry();
                }
                zip.close();*/
            }
            else{
                dec = new XMLDecoder(new BufferedInputStream(new FileInputStream(f)));
                boolean moreObjects = true;
                while(moreObjects){
                    try{
                        oArray.add(dec.readObject());
                    }
                    catch(ArrayIndexOutOfBoundsException ex){
                        moreObjects = false;
                    }
                }
            }
            dec.close();
        }
        return oArray;
    }

    /**
     * Retrieve all settings from several binary files compressed or uncompressed.
     * Note that only files matching (ending with) the file extension will
     * be accesed (see {@link FileExtensions}.
     * TODO Add cipher option to increase security
     */
    public static ArrayList restoreSettingsFromBinaryFiles(File fDir, boolean compresed) throws FileNotFoundException, IOException, ClassNotFoundException{
        ArrayList oArray = null;
        if(fDir != null && fDir.canRead() && fDir.isDirectory()){
            oArray = new ArrayList();
            File[] dirList = fDir.listFiles();
            if(compresed){
                ZipInputStream zip = null;
                for(int i = 0;i<dirList.length;i++){
                    if(dirList[i].getName().endsWith(FileExtensions.COMPRESS.getValue())){
                        zip = new ZipInputStream(new BufferedInputStream(new FileInputStream(dirList[i])));
                        ZipEntry entry = zip.getNextEntry();
                        byte[] bytes = new byte[(int)entry.getSize()];
                        zip.read(bytes);
                        oArray.add(bytes2Object(bytes));
                        zip.closeEntry();
                        zip.close();
                    }
                }
            }
            else{
                for(int i = 0;i<dirList.length;i++){
                    if(dirList[i].getName().endsWith(FileExtensions.BINARY.getValue())){
                        ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(dirList[i])));
                        ois.readInt();
                        oArray.add(ois.readObject());
                        ois.close();
                    }
                }
            }
        }
        return oArray;
    }

    /**
     * Retrieve all settings from several xml files compressed or uncompressed.
     * Note that only files matching (ending with) the file extension will
     * be accesed (see {@link FileExtensions}.
     * TODO Add cipher option to increase security
     */
    public static ArrayList restoreSettingsFromXMLFiles(File fDir, boolean compresed) throws FileNotFoundException, IOException{
        ArrayList oArray = null;
        if(fDir != null && fDir.canWrite() && fDir.isDirectory()){
            oArray = new ArrayList();
            File[] dirList = fDir.listFiles();
            XMLDecoder dec = null;
            File f = null;
            if(compresed){
                ZipInputStream zip = null;
                for(int i = 0;i<dirList.length;i++){
                    if(dirList[i].getName().endsWith(FileExtensions.COMPRESS.getValue())){
                        zip = new ZipInputStream(new BufferedInputStream(new FileInputStream(dirList[i])));
                        ZipEntry entry = zip.getNextEntry();
                        byte[] bytes = new byte[(int)entry.getSize()];
                        zip.read(bytes);
                        dec = new XMLDecoder(new ByteArrayInputStream(bytes));
                        oArray.add(dec.readObject());
                        //oArray.add(bytes2Object(bytes));
                        zip.closeEntry();
                        zip.close();
                    }
                }
            }
            else{

                for(int i = 0;i<dirList.length;i++){
                    if(dirList[i].getName().endsWith(FileExtensions.XML.getValue())){
                        dec = new XMLDecoder(new BufferedInputStream(new FileInputStream(dirList[i])));
                        oArray.add(dec.readObject());
                    }
                }
            }
        }
        return oArray;
    }
    
    
    /**
     * Store all settings compressed or uncompressed. If compressed, path must
     * be a file path. Else must be a directory path.
     * TODO Add cipher option to increase security
     */
    public static void saveSettings(ArrayList settings, String path, boolean compress, boolean XMLFiles) throws IOException{
        if(settings != null && settings.size() > 0 && path != null){
            File f = new File(path);
            if(f.canWrite()){
                ByteArrayOutputStream baos = null;
                XMLEncoder enc = null;
                ZipOutputStream zip = null;
                if(XMLFiles){
                    baos = new ByteArrayOutputStream();
                    enc = new XMLEncoder(baos);
                }
                else{
                    zip = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(f)));
                }
                
                if(compress && f.isFile()){
                    zip = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(f)));
                    zip.setLevel(9);
                    if(XMLFiles){
                        for(Object obj:settings){
                            zip.putNextEntry(new ZipEntry(obj.toString()));
                            enc.writeObject(obj);
                            enc.flush();
                            baos.flush();
                            byte[] bytes = baos.toByteArray();
                            zip.write(bytes,0, bytes.length);
                            zip.closeEntry();
                        }
                    }
                    else{
                        for(Object obj:settings){
                            zip.putNextEntry(new ZipEntry(obj.toString()));
                            byte[] bytes = object2Bytes(obj);
                            zip.write(bytes,0, bytes.length);
                            zip.closeEntry();
                        }
                    }
                    
                    zip.close();
                    baos.close();
                    enc.close();
                }
                else if(!compress && f.isDirectory()){

                }
            }
        }
    }
}
