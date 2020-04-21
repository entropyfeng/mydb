package com.github.entropyfeng.mydb.util;

import com.google.common.base.Charsets;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author entropyfeng
 * @date 2020/3/4 14:42
 */
public class CommonUtil {



    /**
     * 抛硬币法获取层数
     * @return 1=<int<=32
     */
    public static int getLevel() {

        final int maxLevel=32;
        int res = 1;
        while (ThreadLocalRandom.current().nextBoolean()&&res<maxLevel) {
            res++;
        }
        return res;
    }

    public static int hashing(Object o){
        if (o instanceof String){
            return Hashing.murmur3_32().hashString((String) o, Charsets.UTF_8).asInt();
        }else if(o instanceof Integer){
            return Hashing.murmur3_32().hashInt((Integer) o).asInt();
        }else if (o instanceof Double){
            return Hashing.murmur3_32().newHasher().putDouble((Double)o).hash().asInt();
        }else if(o instanceof Float){
            return Hashing.murmur3_32().newHasher().putFloat((Float) o).hash().asInt();
        }else if(o instanceof Long){
            return Hashing.murmur3_32().hashLong((Long)o).asInt();
        }else {
            return o.hashCode();
        }
    }

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

    public static String builderLongString(long length){
        StringBuilder stringBuilder=new StringBuilder();
        for (int i=0;i<length;i++){
            stringBuilder.append('一');
        }
        return stringBuilder.toString();
    }

}
