package com.github.entropyfeng.mydb.util;

import java.util.Arrays;

/**
 * @author entropyfeng
 * @date 2020/3/4 14:42
 */
public class CommonUtil {



    public static <T> T[]merge(T[] first,T[] second){

        T[]res=Arrays.copyOf(first,first.length+second.length);
        System.arraycopy(second,0,res,first.length,second.length);
        return res;
    }
    public static  byte[] mergeBytes(byte[] first,byte[] second){
        byte[]res=Arrays.copyOf(first,first.length+second.length);
        System.arraycopy(second,0,res,first.length,second.length);
        return res;
    }

    public static <T> T[] mergeAll(T[] first,T [] ...rest){
        int totalLength=first.length;
        for (T[]array:rest){
            totalLength+=array.length;
        }
        T[]res=Arrays.copyOf(first,totalLength);
        int offset=first.length;
        for (T[]array:rest){
         System.arraycopy(array,0,res,offset,array.length);
         offset+=array.length;
        }
        return res;

    }

}
