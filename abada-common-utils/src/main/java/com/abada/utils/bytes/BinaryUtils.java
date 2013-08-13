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
 * Binary Methods Utilities
 *
 * @author Jorge Pelaez Hidalgo
 */
public class BinaryUtils {
    /**
     * Convert a byte to List of Booleans
     * @param b
     * @return
     */
    public static List<Boolean> parse(byte b){
        List<Boolean> result=new ArrayList<Boolean>();
        for (int i=0;i<8;i++){
            if (((b>>i)&(byte)0x01)==0)
                result.add(new Boolean(false));
            else
                result.add(new Boolean(true));
        }
        return result;
    }

    /**
     * Convert a list of booleans in a byte
     * @param binary
     * @return
     * @throws java.lang.Exception
     */
    public static byte parse(List<Boolean> binary) throws Exception{
        if (binary.size()!=8) throw new Exception("Incorrect Length");
        byte result=0;
        Boolean b;
        for (int i=7;i>=0;i--){
            b=binary.get(i);
            if (b.booleanValue()){
                result++;
            }
            if (i>0)
                result=(byte)(result<<1);
        }
        return result;
    }

    /**
     * return an List of 8 booleans init to false
     * @return
     */
    public static List<Boolean> initListBinaryByte(){
        List<Boolean> result=new ArrayList<Boolean>(8);
        for (int i=0;i<8;i++)
            result.add(new Boolean(false));
        return result;
    }
}
