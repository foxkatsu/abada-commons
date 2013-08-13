/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.abada.utils.file;

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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

/**
 * Utils for Files
 *
 * @author Jorge Pelaez Hidalgo
 */
public class FileUtils {

    /**
     * Append an String in a File
     * @param path
     * @param data
     * @return
     */
    public static boolean addFile(URI path,String data){
        return addFile(path,data.getBytes());
    }
    /**
     * Append data in the end of the {@link File}
     *
     * @param path URI of file
     * @param data byte[] Data to append
     * @return
     */
    public static boolean addFile(URI path, byte [] data){
        try {
            File filecon = new File(path);
            // Always check whether the file or directory exists.
            // Create the file if it doesn't exist.
            if(!filecon.exists()) {
                filecon.createNewFile();
            }
            //filecon.setHidden(hide);

            FileOutputStream out=new FileOutputStream(filecon,true);
            out.write(data);
            out.close();
            return true;
        } catch(Exception ioe) {
            return false;
        }
    }
    /**
     * Delete a {@link File}
     * @param path URI of file
     * @return
     */
    public static boolean deleteFile(URI path){
        File file=new File(path);
        if (file.exists())
            return file.delete();
        return false;
    }

    /**
     * Load a file in a list of bytes
     * @param path
     * @return
     */
    public static List<Byte> loadFile(URI path){
        List<Byte> result=null;
        try{
            File filecon = new File(path);
            if (filecon.exists()){
                result=new ArrayList<Byte>();
                FileInputStream in=new FileInputStream(filecon);
                int data;
                while ((data=in.read())!=-1)
                    result.add(new Byte((byte)data));
                in.close();
            }
        }catch (Exception ex){}
        return result;
    }

    /**
     * Obtains All files from a directory
     * @param basedir the base directory where files are returned
     * @param filter only files than fullfills filter must be returned (default all files)
     * @param recursive if true subdirs will be inspected for new files (default true)
     * @param sorted if true the returned list will be sorted by absolute paths (default false)
     * @return the files that fullfill the file filter.
     */
    public static List<File> retrieveFilesFromDir(File basedir, final FileFilter filter, boolean recursive, boolean sorted) {
        List<File> arrayFiles = null;
        if(basedir.isDirectory() && basedir.canRead()){
            FilenameFilter fileFilter = new FilenameFilter(){
                            public boolean accept(File dir, String name) {
                                return filter.accept(new File(dir,name));
                            }
            };
            File[] filteredFiles = basedir.listFiles(fileFilter);

            List<File> arrayEntrys = Arrays.asList(filteredFiles);
            arrayFiles = new ArrayList<File>();
            for(File file:arrayEntrys){
                if(file.canRead()){
                    if(file.isFile()){
                        arrayFiles.add(file);
                    }
                    else if(file.isDirectory() && recursive){
                        recursiveRetrieveFilesFromDir(arrayFiles, file, fileFilter);
                    }
                }
            }
            if(sorted){
                Collections.sort(arrayEntrys);
            }
        }
        return arrayFiles;
    }

    private static void recursiveRetrieveFilesFromDir(List<File> files, File basedir, FilenameFilter filter){
        File[] directoryEntries = basedir.listFiles(filter);
        for(int i=0;i<directoryEntries.length;i++){
            if(directoryEntries[i].canRead()){
                if(directoryEntries[i].isFile()){
                    files.add(directoryEntries[i]);
                }
                else if(directoryEntries[i].isDirectory()){
                    recursiveRetrieveFilesFromDir(files, directoryEntries[i], filter);
                }
            }
        }
    }

    public static List<File> retrieveFilesFromDir(File basedir, boolean recursive) {
        return retrieveFilesFromDir(basedir, new JFileChooser().getAcceptAllFileFilter(),recursive, false);
    }

    public static List<File> retrieveFilesFromDir(File basedir, FileFilter filter) {
        return retrieveFilesFromDir(basedir, filter,true, false);
    }

    public static List<File> retrieveFilesFromDir(File basedir) {
        return retrieveFilesFromDir(basedir, new JFileChooser().getAcceptAllFileFilter(),true, false);
    }
}
