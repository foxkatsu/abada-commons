package com.abada.utils.bytes;

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

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jorge Pelaez
 */
public class ByteUtils {

    /**
     * Apply a padding to a List of Byte, filling list with Byte.MAX_VALUE
     * until length, if data is longer length this method no cut, return the
     * same list.
     * @param data
     * @param length
     * @return
     */
    public static List<Byte> padding(List<Byte> data, int length) {
        if (data.size() < length) {
            for (int i = data.size(); i < length; i++) {
                data.add(new Byte(Byte.MAX_VALUE));
            }
            return data;
        } else {
            return data;
        }
    }

    /**
     * Split a list of Byte in block of max length. This method no do padding.
     * @param data
     * @param max
     * @return
     */
    public static List<List<Byte>> split(List<Byte> data, int max) {
        List<List<Byte>> result = new ArrayList<List<Byte>>();
        if (data.size() <= max) {
            result.add(data);
        } else {
            List<Byte> aux = data.subList(0, max);
            result.add(aux);
            result.addAll(split(data.subList(max, data.size()), max));
        }
        return result;
    }

    /**
     * Return the index of first occuernce of token in data.
     * <br />
     * return -1 if token is no found.
     * @param data
     * @param token
     * @return
     */
    public static int indexOf(List<Byte> data, List<Byte> token){
        int result=-1;
        if (data.size()>token.size()){
            List<Byte> aux;
            for (int i=0;i<=data.size()-token.size();i++){
                aux=data.subList(i, i+token.size());
                if (aux.equals(token))
                    return i;
            }
        }
        return result;
    }

    /**
     * Split a list by a token represent by a list.
     * <br />
     * Any occurency of token is erase in result.
     * <br />
     * In result may have List<Byte> with 0 length
     * @param data
     * @param token
     * @return
     */
    public static List<List<Byte>> split(List<Byte> data, List<Byte> token) {
        List<List<Byte>> result = new ArrayList<List<Byte>>();
        if (data.size() < token.size()) {
            result.add(data);
            return result;
        } else {
            int i = indexOf(data,token);
            if (i < 0) {
                result.add(data);
                return result;
            } else {
                List<Byte> aux = data.subList(0, i);
                result.add(aux);
                if (i+token.size()<data.size())
                    result.addAll(split(data.subList(i + token.size(), data.size()), token));
                return result;
            }
        }
    }

    /**
     * array of byte to his represent in ascii
     * @param data
     * @return
     */
    public static StringBuilder encode(byte[] data) {
        StringBuilder result = new StringBuilder();
        for (byte b : data) {
            result.append(encode(b));
        }
        return result;
    }

    /**
     * byte to his represent in ascii
     * @param b
     * @return
     */
    private static StringBuilder encode(byte b) {
        byte ba = (byte) ((b & 0xF0) >> 4);
        byte bb = (byte) (b & 0x0F);
        return new StringBuilder().append(encode2(ba)).append(encode2(bb));
    }

    /**
     *
     * @param b
     * @return
     */
    private static char encode2(byte b) {
        switch (b) {
            case 0x00:
            case 0x01:
            case 0x02:
            case 0x03:
            case 0x04:
            case 0x05:
            case 0x06:
            case 0x07:
            case 0x08:
            case 0x09:
                return (char) ('0' + b);
            case 0x0A:
            case 0x0B:
            case 0x0C:
            case 0x0D:
            case 0x0E:
            case 0x0F:
                return (char) ('A' + (b - 0x0A));
        }
        return ' ';
    }

    /**
     * Clone of List<Byte>
     * @param data
     * @return
     */
    public static List<Byte> clone(List<Byte> data){
        List<Byte> result=new ArrayList<Byte>();
        for (Byte b:data)
            result.add(b);
        return result;
    }

    /**
     * Convert an String to List<Byte> in UTF8
     * @param data
     * @return
     */
    public static List<Byte> asList(String data) {
        return asList(data.getBytes());
    }

    /**
     * Convert an String to List<Byte> in UTF8
     * @param data
     * @return
     */
    public static List<Byte> asList(byte[] data) {
        List<Byte> result = null;
        if (data != null) {
            result = new ArrayList<Byte>();
            for (byte b : data) {
                result.add(new Byte(b));
            }
        }
        return result;
    }

    /**
     * Convert a List<Byte> to array of byte
     * @param data
     * @return
     */
    public static byte[] toArray(List<Byte> data) {
        if (data == null) {
            return null;
        }
        byte[] result = new byte[data.size()];
        for (int i = 0; i < data.size(); i++) {
            result[i] = data.get(i).byteValue();
        }
        return result;
    }

    /**
     * Append an arrays of bytes
     * @param array
     * @return
     */
    public static byte[] appendArray(byte[]... array) {
        List<Byte> result = null;
        if (array.length > 0) {
            result = new ArrayList<Byte>();
            for (byte[] a : array) {
                if (a.length > 0) {
                    result.addAll(asList(a));
                }
            }
        }
        return toArray(result);
    }
}
