package com.katsu.utils.serializers;

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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import static com.katsu.utils.bytes.ByteUtils.asList;
import static com.katsu.utils.bytes.ByteUtils.toArray;
import java.util.List;

/**
 *Clase con una coleccion de metodos utilizados para diferentes tareas con cadenas, arrays, etc
 * @author Jorge Pelaez
 */
public class Serializer{
    public static boolean byte2boolean(List<Byte> raw){
        if (raw.get(0).byteValue()==0)
            return Boolean.FALSE;
        else
            return Boolean.TRUE;
    }

    public static List<Byte> boolean2byte(boolean field){
        List<Byte> result=new ArrayList<Byte>();
        if (field)
            result.add(new Byte((byte)1));
        else
            result.add(new Byte((byte)0));
        return result;
    }

    public static List<Byte> string2byteNDEF(String cadena){
        return string2byteNDEF(new StringBuilder(cadena));
    }

    public static List<Byte> string2byteNDEF(StringBuilder cadena){
        List<Byte> result=int2byte(cadena.length(), 1);
        result.addAll(asList(cadena.toString()));
        return result;
    }

    public static String byte2stringNDEF(List<Byte> data,int offset){
        int l=byte2int(data.subList(offset, offset+1));
        return new String(toArray(data.subList(offset+1, offset+1+l)));
    }

    /**
     *Metodo que convierte un array de byte en un int
     */
    public static int byte2int(List<Byte> intByte) {
        int fromByte = 0;
        for (int i = 0; i < intByte.size(); i++) {
            int n = (intByte.get(i).byteValue() < 0 ? (int) intByte.get(i).byteValue() + 256 : (int) intByte.get(i).byteValue()) << (8 * i);
            //System.out.println(n);
            fromByte += n;
        }
        return fromByte;
    }

    /**
     *Metodo que conviente un int en un array de n (longitudBytes) bytes
     */
    public static List<Byte> int2byte(int number, int longitudBytes) {
        List<Byte> intByte = new ArrayList<Byte>(longitudBytes);
        for (int i = 0; i < longitudBytes; i++) {
            intByte.add(new Byte((byte) (number >> (8 * i))));
        }
        return intByte;
    }


    /**
     *Metodo que convierte un array de byte en un int
     */
    public static long byte2long(List<Byte> intByte) {
        long fromByte = 0;
        for (int i = 0; i < intByte.size(); i++) {
            long n = (intByte.get(i).byteValue() < 0 ? (long) intByte.get(i).byteValue() + 256 : (long) intByte.get(i).byteValue()) << (8 * i);
            //System.out.println(n);
            fromByte += n;
        }
        return fromByte;
    }

    /**
     *Metodo que conviente un int en un array de n (longitudBytes) bytes
     */
    public static List<Byte> long2byte(long number) {
        int longitudBytes=8;
        List<Byte> intByte = new ArrayList<Byte>(longitudBytes);
        for (int i = 0; i < longitudBytes; i++) {
            intByte.add(new Byte((byte) (number >> (8 * i))));
        }
        return intByte;
    }

     /**
     *Metodo que convierte un array de byte en un double
     */
    public static double byte2double(List<Byte> intByte) {
        return Double.longBitsToDouble(byte2long(intByte));
    }

    /**
     *Metodo que conviente un double en un array de n
     */
    public static List<Byte> double2byte(double number) {
        return long2byte(Double.doubleToLongBits(number));
    }

    /**
     * Desserializar un array de byte. Los 7 primeros bytes
     *
     * @param data
     * @return
     */
    public static Calendar byte2calendar(List<Byte> data){
        if (data.size()>=6 && data.size()<=7){
            Calendar result=Calendar.getInstance();
            result.set(Calendar.DATE, byte2int(data.subList(0, 1)));
            result.set(Calendar.MONTH, byte2int(data.subList(1, 2)));
            result.set(Calendar.YEAR, byte2int(data.subList(2, 4)));
            result.set(Calendar.HOUR_OF_DAY, byte2int(data.subList(4, 5)));
            result.set(Calendar.MINUTE, byte2int(data.subList(5, 6)));
            if (data.size()==7)
                result.set(Calendar.SECOND, byte2int(data.subList(6, 7)));
            else
                result.set(Calendar.SECOND, 0);
            return result;
        }
        return null;
    }

    public static List<Byte> calendar2byte(Calendar calendar){
        return calendar2byte(calendar,true);
    }
    /**
     * Return list with 7 bytes that represents Calendar date.
     * @param calendar
     * @return
     */
    public static List<Byte> calendar2byte(Calendar calendar,boolean seconds){
        List<Byte> result=new ArrayList<Byte>();
        if (calendar!=null){
            result.addAll(int2byte(calendar.get(Calendar.DATE),1));
            result.addAll(int2byte(calendar.get(Calendar.MONTH),1));
            result.addAll(int2byte(calendar.get(Calendar.YEAR),2));
            result.addAll(int2byte(calendar.get(Calendar.HOUR_OF_DAY),1));
            result.addAll(int2byte(calendar.get(Calendar.MINUTE),1));
            if (seconds)
                result.addAll(int2byte(calendar.get(Calendar.SECOND),1));
        }
        return result;
    }

    /**
     *Devuelve un string con la fecha representada por calendar
     * dd-MM-yyyy HH:mm:ss
     */
    public static String calendar2string(Calendar fecha,boolean segundos) {
        StringBuilder result = new StringBuilder();
        result.append(to2Caracteres(fecha.get(Calendar.DATE)) + "-");
        result.append(to2Caracteres((fecha.get(Calendar.MONTH)+1)) + "-");
        result.append(to2Caracteres(fecha.get(Calendar.YEAR)) + " ");
        result.append(to2Caracteres(fecha.get(Calendar.HOUR_OF_DAY)) + ":");
        result.append(to2Caracteres(fecha.get(Calendar.MINUTE)));
        if (segundos)
            result.append(":"+to2Caracteres(fecha.get(Calendar.SECOND)));
        return result.toString();
    }

    /**
     * Convierte una cadena en una fecha</BR>
     * Formato de entrada de la cadena dd-MM-yyyy HH:mm:ss
     *
     * @param fecha
     * @return
     */
    public static Calendar string2Calendar(String fecha) {
        /*List<Byte> fechaResult=new ArrayList<Byte>();
        //separacion fecha de hora
        String[] aux = fecha.split(" ");
        if (aux.length == 2) {
            try {
                //proceso fecha
                String[] aux2 = aux[0].split("-");
                if (aux2.length == 3) {
                    fechaResult.add((byte)Integer.parseInt(aux2[0].trim()));
                    fechaResult.add((byte)Integer.parseInt(aux2[1].trim()));
                    fechaResult.addAll(int2byte(Integer.parseInt(aux2[2].trim()),2));
                    //proceso hora
                    aux2 = aux[1].split(":");
                    if (aux2.length == 3) {
                        fechaResult.add((byte)Integer.parseInt(aux2[0].trim()));
                        fechaResult.add((byte)Integer.parseInt(aux2[1].trim()));
                        fechaResult.add((byte)Integer.parseInt(aux2[2].trim()));
                        return byte2calendar(fechaResult);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;*/
        try{
            SimpleDateFormat format=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Calendar result=Calendar.getInstance();
            result.setTime(format.parse(fecha));
            return result;
        }catch (Exception e){
            return null;
        }
    }

    private static String to2Caracteres(int v){
        if (v<=9)
            return "0"+v;
        else
            return v+"";
    }
}
